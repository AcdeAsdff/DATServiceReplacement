package com.linearity.datservicereplacement.androidhooking.com.android.server.appop;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_UidAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.app.SyncNotedAppOp;
import android.os.Binder;

import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;

@NotFinished
public class HookAppOpsService {
    public static final String[] stackStringHeads_IAppOps = new String[]{
            "LSPHooker_.",
            "com.android.internal.app.IAppOpsService$Stub.onTransact(IAppOpsService.java:",
            "android.os.Binder.execTransactInternal(Binder.java:",
            "android.os.Binder.execTransact(Binder.java:"
    };
    public static SimpleExecutor grantOperationForDirectCall = param -> {
        Exception stackShower = new Exception("requesting permission:" + Arrays.deepToString(param.args) + Binder.getCallingUid());
        StackTraceElement[] stacks = stackShower.getStackTrace();
        for (StackTraceElement s: stacks){
            if ((String.valueOf(s)).contains("ContextImpl")){
                return;
            }
        }
        if (stacks.length >= 4){
            for (int i = 0; i< stackStringHeads_IAppOps.length; i++){
                if (!stacks[stacks.length - stackStringHeads_IAppOps.length + i].toString().startsWith(stackStringHeads_IAppOps[i])){
                    return;
                }
            }
        }
        param.setResult(MODE_ALLOWED);
        LoggerLog(new Exception("checking operation:" + Arrays.deepToString(param.args) + Binder.getCallingUid()));
    };
    public static SimpleExecutor grantOperationForDirectCall_noLog = param -> {
        Exception stackShower = new Exception("requesting permission:" + Arrays.deepToString(param.args) + Binder.getCallingUid());
        StackTraceElement[] stacks = stackShower.getStackTrace();
        for (StackTraceElement s: stacks){
            if ((String.valueOf(s)).contains("ContextImpl")){
                return;
            }
        }
        if (stacks.length >= 4){
            for (int i = 0; i< stackStringHeads_IAppOps.length; i++){
                if (!stacks[stacks.length - stackStringHeads_IAppOps.length + i].toString().startsWith(stackStringHeads_IAppOps[i])){
                    return;
                }
            }
        }
        param.setResult(MODE_ALLOWED);
    };

    public static void doHook(){
        classesAndHooks.put("com.android.server.appop.AppOpsService", HookAppOpsService::hookIAppOpsService);
    }

    //if asked,just tell lie that they have the permission
    public static void hookIAppOpsService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"checkOperation",
                MODE_ALLOWED
        ,getSystemChecker_UidAt(1));//won't given out,can cheat

//        hookAllMethodsWithCache_Auto(hookClass,"noteOperation",new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            param.setResult(generateSyncNotedAppOp((Integer) param.args[0],String.valueOf(param.args[2])));
//        }), getSystemChecker_UidAt(1)
//        );
//        hookAllMethodsWithCache_Auto(hookClass,"noteOperationForDevice",new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            param.setResult(generateSyncNotedAppOp((Integer) param.args[0],String.valueOf(param.args[2])));
//        }), getSystemChecker_UidAt(1)
//        );
//
//        hookAllMethodsWithCache_Auto(hookClass, "startOperation",
//                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//                    param.setResult(generateSyncNotedAppOp((Integer) param.args[1],String.valueOf(param.args[3])));
//        }),
//                param -> isSystemApp(Binder.getCallingUid()) && isSystemApp((int) param.args[2])
//        );
//        hookAllMethodsWithCache_Auto(hookClass, "startOperationForDevice",
//                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//                    param.setResult(generateSyncNotedAppOp((Integer) param.args[1],String.valueOf(param.args[3])));
//                }),
//                param -> isSystemApp(Binder.getCallingUid()) && isSystemApp((int) param.args[2])
//        );
//
//
        hookAllMethodsWithCache_Auto(hookClass,"finishOperation",null,getSystemChecker_UidAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"startWatchingMode",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"stopWatchingMode",null);
////        hookAllMethodsWithCache_Auto(hookClass,"permissionToOpCode",0);
////        hookAllMethodsWithCache_Auto(hookClass,"checkAudioOperation",grantOperationForDirectCall);
        hookAllMethodsWithCache_Auto(hookClass,"shouldCollectNotes",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCameraAudioRestriction",null);
        hookAllMethodsWithCache_Auto(hookClass,"startWatchingModeWithFlags",null,getSystemChecker_PackageNameAt(1));

//        hookAllMethodsWithCache_Auto(hookClass, "noteProxyOperation",
//                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//                    AttributionSource attributionSource = (AttributionSource) param.args[1];
//                    param.setResult(generateSyncNotedAppOp((Integer) param.args[0],attributionSource.getPackageName()));
//                })
//        );
////    hookAllMethodsWithCache_Auto(hookClass,"noteProxyOperationWithState",SyncNotedAppOp);
//        hookAllMethodsWithCache_Auto(hookClass, "noteProxyOperationWithState",
//                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//                    AttributionSource attributionSource = (AttributionSource) param.args[1];
//                    param.setResult(generateSyncNotedAppOp((Integer) param.args[0],attributionSource.getPackageName()));
//                })
//        );
//        hookAllMethodsWithCache_Auto(hookClass, "startProxyOperation",
//                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//                    AttributionSource attributionSource = (AttributionSource) param.args[2];
//                    param.setResult(generateSyncNotedAppOp((Integer) param.args[1],attributionSource.getPackageName()));
//                })
//        );
////    hookAllMethodsWithCache_Auto(hookClass,"startProxyOperationWithState",SyncNotedAppOp);
//        hookAllMethodsWithCache_Auto(hookClass, "startProxyOperationWithState",
//                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
////            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},
////            int opMode,int opCode,
////            null,String packageName);
//                    AttributionSource attributionSource = (AttributionSource) param.args[2];
//                    param.setResult(generateSyncNotedAppOp((Integer) param.args[1],attributionSource.getPackageName()));
//                })
//        );

//        hookAllMethodsWithCache_Auto(hookClass,"finishProxyOperation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"checkPackage",grantOperationForDirectCall_noLog);//not called
//        hookAllMethodsWithCache_Auto(hookClass,"collectRuntimeAppOpAccessMessage",null);
//
//        hookAllMethodsWithCache_Auto(hookClass,"reportRuntimeAppOpAccessMessageAndGetConfig",new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            if (SomeClasses.msgSamplingCOnfigClass == null){return;}
//            Object ret = XposedHelpers.newInstance(SomeClasses.msgSamplingCOnfigClass,-1,0,
//                    Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli());
//            param.setResult(ret);
//        }));
        hookAllMethodsWithCache_Auto(hookClass,"getPackagesForOps",new ArrayList<>());
        hookAllMethodsWithCache_Auto(hookClass,"getOpsForPackage",new ArrayList<>(),getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getHistoricalOps",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getHistoricalOpsFromDiskRaw",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"offsetHistory",null);
        hookAllMethodsWithCache_Auto(hookClass,"setHistoryParameters",null);
        hookAllMethodsWithCache_Auto(hookClass,"addHistoricalOps",null);
        hookAllMethodsWithCache_Auto(hookClass,"resetHistoryParameters",null);
        hookAllMethodsWithCache_Auto(hookClass,"resetPackageOpsNoHistory",null);
        hookAllMethodsWithCache_Auto(hookClass,"clearHistory",null);
        hookAllMethodsWithCache_Auto(hookClass,"rebootHistory",null);
        hookAllMethodsWithCache_Auto(hookClass,"getUidOps",new ArrayList<>());
        hookAllMethodsWithCache_Auto(hookClass,"setUidMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"setMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"resetAllModes",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAudioRestriction",null);
        hookAllMethodsWithCache_Auto(hookClass,"setUserRestrictions",null);
        hookAllMethodsWithCache_Auto(hookClass,"setUserRestriction",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"startWatchingActive",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopWatchingActive",null);
        hookAllMethodsWithCache_Auto(hookClass,"isOperationActive",true);
        hookAllMethodsWithCache_Auto(hookClass,"isProxying",true);
        hookAllMethodsWithCache_Auto(hookClass,"startWatchingStarted",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopWatchingStarted",null);
        hookAllMethodsWithCache_Auto(hookClass,"startWatchingNoted",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopWatchingNoted",null);
        hookAllMethodsWithCache_Auto(hookClass,"startWatchingAsyncNoted",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopWatchingAsyncNoted",null);
        hookAllMethodsWithCache_Auto(hookClass,"extractAsyncOps",new ArrayList<>());
//        hookAllMethodsWithCache_Auto(hookClass,"checkOperationRaw",grantOperationForDirectCall_noLog);
        hookAllMethodsWithCache_Auto(hookClass,"reloadNonHistoricalState",null);
        hookAllMethodsWithCache_Auto(hookClass,"collectNoteOpCallsForValidation",null);
        hookAllMethodsWithCache_Auto(hookClass,"finishProxyOperationWithState",null);
//        hookAllMethodsWithCache_Auto(hookClass,"checkOperationRawForDevice",grantOperationForDirectCall_noLog);
//        hookAllMethodsWithCache_Auto(hookClass,"checkOperationForDevice",grantOperationForDirectCall_noLog);
        hookAllMethodsWithCache_Auto(hookClass,"finishOperationForDevice",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPackagesForOpsForDevice",new ArrayList<>());
    }


    public static SyncNotedAppOp generateSyncNotedAppOp(int opCode,String packageName){
        return (SyncNotedAppOp) XposedHelpers.newInstance(SyncNotedAppOp.class, new Class<?>[]{int.class, int.class, String.class, String.class},
                MODE_ALLOWED, opCode,
                null, packageName);
    }
}
