package com.linearity.datservicereplacement.AppOps;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.MODE_ERRORED;
import static android.app.AppOpsManager.MODE_FOREGROUND;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.isSystemApp;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE;

import android.app.AppOpsManager;
import android.app.SyncNotedAppOp;
import android.content.AttributionSource;
import android.content.pm.PackageManager;
import android.os.Binder;

import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;
import com.linearity.utils.SystemAppChecker;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

@NotFinished
public class HookAppOpsService {

    public static Class<?> msgSamplingCOnfigClass;
    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){
        Class<?> hookClass = XposedHelpers.findClassIfExists("com.android.server.appop.AppOpsService",lpparam.classLoader);
        if (hookClass != null){
            msgSamplingCOnfigClass = XposedHelpers.findClassIfExists("com.android.internal.app.MessageSamplingConfig",lpparam.classLoader);
            hookIAppOpsService(hookClass);
        }
    }

    //if asked,just tell lie that they have the permission
    public static void hookIAppOpsService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"checkOperation",MODE_ALLOWED);

//        SyncNotedAppOp noteOperation(int code, int uid, String packageName, @nullable String attributionTag,
//        boolean shouldCollectAsyncNotedOp, String message, boolean shouldCollectMessage);
        hookAllMethodsWithCache_Auto(hookClass,"noteOperation",new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},int opMode,int opCode,
//            null,String packageName);
            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},
                    MODE_ALLOWED,param.args[0],
            null,param.args[2]);
            param.setResult(ret);
        }), param -> isSystemApp(Binder.getCallingUid()) && isSystemApp((int) param.args[1]));
//    hookAllMethodsWithCache_Auto(hookClass,"noteOperationForDevice",SyncNotedAppOp);
        hookAllMethodsWithCache_Auto(hookClass,"noteOperationForDevice",new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},int opMode,int opCode,
//            null,String packageName);
            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},
                    MODE_ALLOWED,param.args[0],
                    null,param.args[2]);
            param.setResult(ret);
        }), param -> isSystemApp(Binder.getCallingUid()) && isSystemApp((int) param.args[1]));

//        SyncNotedAppOp startOperation(IBinder clientId, int code, int uid, String packageName,
//                @nullable String attributionTag, boolean startIfModeDefault,
//        boolean shouldCollectAsyncNotedOp, String message, boolean shouldCollectMessage,
//        int attributionFlags, int attributionChainId);
        hookAllMethodsWithCache_Auto(hookClass, "startOperation",
                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},
//            int opMode,int opCode,
//            null,String packageName);
            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class, new Class<?>[]{int.class, int.class, String.class, String.class},
                    MODE_ALLOWED, param.args[1],
                    null, param.args[3]);
            param.setResult(ret);
        }),
                param -> isSystemApp(Binder.getCallingUid()) && isSystemApp((int) param.args[2])
        );
//    hookAllMethodsWithCache_Auto(hookClass,"startOperationForDevice",SyncNotedAppOp);
        hookAllMethodsWithCache_Auto(hookClass, "startOperationForDevice",
                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},
//            int opMode,int opCode,
//            null,String packageName);
                    Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class, new Class<?>[]{int.class, int.class, String.class, String.class},
                            MODE_ALLOWED, param.args[1],
                            null, param.args[3]);
                    param.setResult(ret);
                }),
                param -> isSystemApp(Binder.getCallingUid()) && isSystemApp((int) param.args[2])
        );


        hookAllMethodsWithCache_Auto(hookClass,"finishOperation",null);
        hookAllMethodsWithCache_Auto(hookClass,"startWatchingMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopWatchingMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"permissionToOpCode",0);
        hookAllMethodsWithCache_Auto(hookClass,"checkAudioOperation",MODE_ALLOWED);
        hookAllMethodsWithCache_Auto(hookClass,"shouldCollectNotes",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCameraAudioRestriction",null);
        hookAllMethodsWithCache_Auto(hookClass,"startWatchingModeWithFlags",null);

//        SyncNotedAppOp noteProxyOperation(int code, in AttributionSource attributionSource,
//        boolean shouldCollectAsyncNotedOp, String message, boolean shouldCollectMessage,
//        boolean skipProxyOperation);
        hookAllMethodsWithCache_Auto(hookClass, "noteProxyOperation",
                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},
//            int opMode,int opCode,
//            null,String packageName);
                    AttributionSource attributionSource = (AttributionSource) param.args[1];
                    Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class, new Class<?>[]{int.class, int.class, String.class, String.class},
                            MODE_ALLOWED, param.args[0],
                            null, attributionSource.getPackageName());
                    param.setResult(ret);
                })
        );
//    hookAllMethodsWithCache_Auto(hookClass,"noteProxyOperationWithState",SyncNotedAppOp);
        hookAllMethodsWithCache_Auto(hookClass, "noteProxyOperationWithState",
                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},
//            int opMode,int opCode,
//            null,String packageName);
                    AttributionSource attributionSource = (AttributionSource) param.args[1];
                    Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class, new Class<?>[]{int.class, int.class, String.class, String.class},
                            MODE_ALLOWED, param.args[0],
                            null, attributionSource.getPackageName());
                    param.setResult(ret);
                })
        );

//        SyncNotedAppOp startProxyOperation(IBinder clientId, int code,
//        in AttributionSource attributionSource, boolean startIfModeDefault,
//        boolean shouldCollectAsyncNotedOp, String message, boolean shouldCollectMessage,
//        boolean skipProxyOperation, int proxyAttributionFlags, int proxiedAttributionFlags,
//        int attributionChainId);
        hookAllMethodsWithCache_Auto(hookClass, "startProxyOperation",
                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},
//            int opMode,int opCode,
//            null,String packageName);
                    AttributionSource attributionSource = (AttributionSource) param.args[2];
                    Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class, new Class<?>[]{int.class, int.class, String.class, String.class},
                            MODE_ALLOWED, param.args[1],
                            null, attributionSource.getPackageName());
                    param.setResult(ret);
                })
        );
//    hookAllMethodsWithCache_Auto(hookClass,"startProxyOperationWithState",SyncNotedAppOp);
        hookAllMethodsWithCache_Auto(hookClass, "startProxyOperationWithState",
                new SimpleExecutorWithMode(MODE_BEFORE, param -> {
//            Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class,new Class<?>[]{int.class,int.class,String.class,String.class},
//            int opMode,int opCode,
//            null,String packageName);
                    AttributionSource attributionSource = (AttributionSource) param.args[2];
                    Object ret = XposedHelpers.newInstance(SyncNotedAppOp.class, new Class<?>[]{int.class, int.class, String.class, String.class},
                            MODE_ALLOWED, param.args[1],
                            null, attributionSource.getPackageName());
                    param.setResult(ret);
                })
        );

        hookAllMethodsWithCache_Auto(hookClass,"finishProxyOperation",null);

        hookAllMethodsWithCache_Auto(hookClass,"checkPackage",
                MODE_ALLOWED,
                param -> isSystemApp(Binder.getCallingUid()) && isSystemApp((int)param.args[0]));
        hookAllMethodsWithCache_Auto(hookClass,"collectRuntimeAppOpAccessMessage",null);

        hookAllMethodsWithCache_Auto(hookClass,"reportRuntimeAppOpAccessMessageAndGetConfig",new SimpleExecutorWithMode(MODE_BEFORE, param -> {
            if (msgSamplingCOnfigClass == null){return;}
            Object ret = XposedHelpers.newInstance(msgSamplingCOnfigClass,-1,0,
                    Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli());
            param.setResult(ret);
        }));
        hookAllMethodsWithCache_Auto(hookClass,"getPackagesForOps",new ArrayList<>());
        hookAllMethodsWithCache_Auto(hookClass,"getOpsForPackage",new ArrayList<>());
        hookAllMethodsWithCache_Auto(hookClass,"getHistoricalOps",null);
        hookAllMethodsWithCache_Auto(hookClass,"getHistoricalOpsFromDiskRaw",null);
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
        hookAllMethodsWithCache_Auto(hookClass,"checkOperationRaw",MODE_ALLOWED);
        hookAllMethodsWithCache_Auto(hookClass,"reloadNonHistoricalState",null);
        hookAllMethodsWithCache_Auto(hookClass,"collectNoteOpCallsForValidation",null);
        hookAllMethodsWithCache_Auto(hookClass,"finishProxyOperationWithState",null);
        hookAllMethodsWithCache_Auto(hookClass,"checkOperationRawForDevice",MODE_ALLOWED);
        hookAllMethodsWithCache_Auto(hookClass,"checkOperationForDevice",MODE_ALLOWED);
        hookAllMethodsWithCache_Auto(hookClass,"finishOperationForDevice",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPackagesForOpsForDevice",new ArrayList<>());
    }
}
