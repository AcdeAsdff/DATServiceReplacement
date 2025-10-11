package com.linearity.datservicereplacement.androidhooking.com.android.server.am;

import static android.app.WindowConfiguration.ACTIVITY_TYPE_STANDARD;
import static android.app.WindowConfiguration.WINDOWING_MODE_FULLSCREEN;
import static android.content.res.Configuration.NAVIGATIONHIDDEN_YES;
import static com.linearity.utils.AndroidFakes.ActivityManagerService.BroadcastRecordUtils.getSystemChecker_BroadcastRecordAt;
import static com.linearity.utils.AndroidFakes.ActivityManagerService.BroadcastRecordUtils.initBroadcastRecordUtils;
import static com.linearity.utils.AndroidFakes.ActivityManagerService.BroadcastRecordUtils.outputInformation;
import static com.linearity.utils.AndroidFakes.ActivityManagerService.Consts.DEFAULT_INTENT_ACTION_SET;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.getPackageName;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.hookPackageManager.isSystemApplicationInfo;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findArgByClassInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexAndObjectInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.wm.HookWindowManagerService.modifyConfigurationResult;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.RandomValues.MCCAndMNCFromUid;
import static com.linearity.utils.RandomValues.colorModeFromUid;
import static com.linearity.utils.RandomValues.grammaticalGenderFromUid;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE_AND_AFTER;

import android.app.ActivityManager;
import android.app.PictureInPictureParams;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.LocaleList;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.linearity.utils.AndroidFakes.ActivityManagerService.BroadcastRecordUtils;
import com.linearity.utils.AndroidFakes.ActivityManagerService.Consts;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;
import com.linearity.utils.SystemAppChecker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.robv.android.xposed.XposedHelpers;

public class HookIActivityManager {

    public static int PROCESS_STATE_TOP = 3;

    public static void doHook(){
        PROCESS_STATE_TOP = XposedHelpers.getStaticIntField(android.app.ActivityManager.class,"PROCESS_STATE_TOP");
        classesAndHooks.put("com.android.server.am.ActivityManagerService", HookIActivityManager::hookIActivityManager);
        classesAndHooks.put("com.android.server.am.ActivityManagerService$PermissionController", HookIActivityManager::hookIPermissionController);
        classesAndHooks.put("com.android.server.am.BroadcastQueueImpl", HookIActivityManager::hookBroadcastQueue);
        classesAndHooks.put("com.android.server.am.BroadcastQueueModernImpl", HookIActivityManager::hookBroadcastQueue);
        classesAndHooks.put("com.android.server.wm.ActivityRecord", HookIActivityManager::hookActivityRecord);
        classesAndHooks.put("com.android.server.wm.ActivityTaskManagerService$LocalService", HookIActivityManager::hookATMSInternal);

        classesAndHooks.put("com.android.server.wm.DisplayContent", HookIActivityManager::hookDisplayContent);
        classesAndHooks.put("com.android.server.wm.WindowProcessController", HookIActivityManager::hookWindowProcessController);
//        hookClass = XposedHelpers.findClassIfExists("android.app.LoadedApk$ReceiverDispatcher", classLoader);
//        if (hookClass != null){
//            hookReceiverDispatcher(hookClass);
//        }
//        hookClass = XposedHelpers.findClassIfExists("com.android.server.am.PendingIntentRecord", classLoader);
//        if (hookClass != null){
//            hookPendingIntentRecord(hookClass);
//        }
//        hookClass = XposedHelpers.findClassIfExists("com.android.server.am.PendingIntentController", classLoader);
//        if (hookClass != null){
//            hookPendingIntentController(hookClass);
//        }

//        hookActivityManager(ActivityManager.class);
    }

    private static Class<?> preBindInfoClass = null;
    private static Field preBindInfo_Configuration = null;
    public static void hookATMSInternal(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"preBindApplication",new SimpleExecutorWithMode(MODE_AFTER,param ->{
            Object result = param.getResult();
            if (result == null){return;}
            try {
                ApplicationInfo applicationInfo = (ApplicationInfo) param.args[param.args.length - 1];
                int uid = applicationInfo.uid;
                if (preBindInfoClass == null){
                    preBindInfoClass = result.getClass();
                }
                if (preBindInfo_Configuration == null){
                    preBindInfo_Configuration = XposedHelpers.findField(preBindInfoClass,"configuration");
                }
                Configuration configuration = (Configuration) preBindInfo_Configuration.get(param.thisObject);
                configuration = modifyConfigurationWithUid(configuration,uid);
                preBindInfo_Configuration.set(param.thisObject,configuration);
            }catch (Exception e){
                LoggerLog(e);
            }

        }),getSystemChecker_WindowProcessControllerAt(-1));
    }

    /**
     * see checkPermissionForDevice,this is used to execute calling directly from app
     */
    public static final String[] stackStringHeads = new String[]{
        "LSPHooker_.",
                "android.app.IActivityManager$Stub.onTransact(IActivityManager.java:",
                "com.android.server.am.ActivityManagerService.onTransact(ActivityManagerService.java:",
                "android.os.Binder.execTransactInternal(Binder.java:",
                "android.os.Binder.execTransact(Binder.java:"
    };
    public static final List<Class<?>> hookedActivityManagerInternal = new ArrayList<>();

    public static void hookDisplayContent(Class<?> hookClass){
//        listenClass(hookClass);
//        hookAllMethodsWithCache_Auto(hookClass,"computeScreenConfiguration",showBefore,noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"computeScreenAppConfiguration",showBefore,noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"onConfigurationChanged",showBefore,noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"updateDisplayOverrideConfigurationLocked",showBefore,noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"performDisplayOverrideConfigUpdate",showBefore,noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"onRequestedOverrideConfigurationChanged",showBefore,noSystemChecker);
    }

    private static Class<?> windowProcessControllerClass;
    private static Field windowProcessController_mInfo = null;
    private static Field windowProcessController_mUid = null;

    public static final String WindowProcessControllerClassName = "com.android.server.wm.WindowProcessController";
    public static final SystemAppChecker windowProcessControllerSystemChecker = param -> isWindowProcessControllerSystem(param.thisObject);
    public static final HashMap<Integer, SystemAppChecker> getSystemChecker_WindowProcessControllerAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_WindowProcessControllerAt(int index){
        if (getSystemChecker_WindowProcessControllerAt_map.containsKey(index)){
            return getSystemChecker_WindowProcessControllerAt_map.get(index);
        }
        SystemAppChecker ret = param -> {

            try {
                int tempIndex = index;
                if (tempIndex < 0) {
                    tempIndex += param.args.length;
                }
                return isSystemApp(Binder.getCallingUid())
                        && isWindowProcessControllerSystem(param.args[tempIndex]);
            }catch (Exception e){
                showAfter.simpleExecutor.execute(param);
                return isSystemApp(Binder.getCallingUid());
            }
        };
        getSystemChecker_WindowProcessControllerAt_map.put(index,ret);
        return ret;
    }
    public static boolean isWindowProcessControllerSystem(Object windowProcessController){
        try {
            if (!windowProcessController.getClass().getName().equals(WindowProcessControllerClassName)){
                return true;
            }
            if (windowProcessController_mInfo == null){
                windowProcessController_mInfo = XposedHelpers.findField(windowProcessController.getClass(),"mInfo");
            }
            if (windowProcessController_mUid == null){
                windowProcessController_mUid = XposedHelpers.findField(windowProcessController.getClass(),"mUid");
            }
            ApplicationInfo applicationInfo = (ApplicationInfo) windowProcessController_mInfo.get(windowProcessController);
            if (applicationInfo != null && !isSystemApplicationInfo(applicationInfo)){return false;}
            int uid = (int) windowProcessController_mUid.get(windowProcessController);
            return isSystemApp(uid);
        }catch (Exception e){
            LoggerLog(e);
            return true;
        }
    }
    public static void hookWindowProcessController(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getConfiguration",modifyConfigurationResult,windowProcessControllerSystemChecker);
        Class<?> superclass = hookClass.getSuperclass();
        if (superclass != null){
            if (superclass != Object.class){
                hookWindowProcessController(superclass);
            }
        }
    }

    public static void hookActivityManagerInternal(Class<?> hookClass){
        if (hookedActivityManagerInternal.contains(hookClass)){
            return;
        }
        hookedActivityManagerInternal.add(hookClass);

        hookAllMethodsWithCache_Auto(hookClass,"broadcastIntentInPackage",(SimpleExecutor)param -> {
            showBefore.simpleExecutor.execute(param);
        },getSystemChecker_PackageNameAt(0));
    }
    public static void hookPendingIntentController(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onActivityManagerInternalAdded",new SimpleExecutorWithMode(MODE_AFTER, param -> {
            Object mAmInternal = XposedHelpers.getObjectField(param.thisObject,"mAmInternal");
            hookActivityManagerInternal(mAmInternal.getClass());
        }),noSystemChecker);
    }
    public static final List<Class<?>> hookedIIntentReceiver = new ArrayList<>();
    public static void hookIIntentReceiver(Class<?> hookClass){
        if (hookedIIntentReceiver.contains(hookClass)){
            return;
        }
        hookedIIntentReceiver.add(hookClass);
        hookAllMethodsWithCache_Auto(hookClass,
                "performReceive",
                (SimpleExecutor) param -> {
                    Intent intent = (Intent) param.args[0];
                    LoggerLog(new Exception(getPackageName(Binder.getCallingUid()) + " " + intent));
                },
                noSystemChecker);
    }
    public static void hookPendingIntentRecord(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"sendInner",(SimpleExecutor)param -> {
            if (!(param.args[2] instanceof Intent)){
                return;
            }
//            Intent intent = (Intent) param.args[2];
//            Object key = XposedHelpers.getObjectField(param.thisObject,"key");
//            LoggerLog(new Exception(intent
//                    + "\n-> " + XposedHelpers.getObjectField(key,"packageName")
//            ));
            Object receiver = param.args[5];
            if (receiver == null){
                return;
            }
            hookIIntentReceiver(receiver.getClass());
        },param -> {
            Object key = XposedHelpers.getObjectField(param.thisObject,"key");
            return isSystemApp((String) XposedHelpers.getObjectField(key,"packageName"));
        });
    }
//    public static void hookBroadcastQueueImpl(Class<?> hookClass){
//    }
    public static void hookReceiverDispatcher(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"performReceive",(SimpleExecutor)param -> {
            LoggerLog(Arrays.toString(param.args));
        },getSystemChecker_PackageNameAt(9));
    }

    public static void hookActivityManager(Class<ActivityManager> hookClass) {

//        hookAllMethodsWithCache_Auto(hookClass,"checkComponentPermission",(SimpleExecutor) param -> {
//            Exception stackShower = new Exception();
//            StackTraceElement[] stacks = stackShower.getStackTrace();
//            String receiverStack = String.valueOf(stacks[stacks.length-3]);
//            if (receiverStack.startsWith("com.android.server.am.ActivityManagerService")){
//                param.setResult(PackageManager.PERMISSION_GRANTED);
//                return;
//            }
//            LoggerLog(new Exception("checkComponentPermission:" + Arrays.deepToString(param.args)
//                    + "\nPackageName:" + getPackageName(Binder.getCallingUid())));
//        });
    }

    public static void hookIActivityManager(Class<?> hookClass){

//    hookAllMethodsWithCache_Auto(hookClass,"openContentUri",ParcelFileDescriptor);

//        hookAllMethodsWithCache_Auto(hookClass,"registerUidObserver",null,getSystemChecker_PackageNameAt(3));
//        hookAllMethodsWithCache_Auto(hookClass,"registerUidObserverForUids",/*IBinder*/null,getSystemChecker_PackageNameAt(3));
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterUidObserver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"addUidToObserver",null,findStrAndUidInArgs);
//        hookAllMethodsWithCache_Auto(hookClass,"removeUidFromObserver",null,findStrAndUidInArgs);
//        hookAllMethodsWithCache_Auto(hookClass,"isUidActive",true,findStrAndUidInArgs);
//        hookAllMethodsWithCache_Auto(hookClass,"getUidProcessState",PROCESS_STATE_TOP,findStrAndUidInArgs);
//        hookAllMethodsWithCache_Auto(hookClass, "checkPermission", checkPermissionShow);//not called,idk
        hookAllMethodsWithCache_Auto(hookClass, "logFgsApiBegin", null);
        hookAllMethodsWithCache_Auto(hookClass,"logFgsApiEnd",null);
        hookAllMethodsWithCache_Auto(hookClass,"logFgsApiStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"handleApplicationCrash",null);
        hookAllMethodsWithCache_Auto(hookClass,"startActivity",
                (SimpleExecutor) param -> {
            Intent intent = (Intent) param.args[2];
            if (intent == null){return;}
            ComponentName mComponent = intent.getComponent();
            if (mComponent == null){
                return;
            }
            String pkgName = mComponent.getPackageName().toLowerCase();
            String usedClassName = mComponent.getClassName().toLowerCase();
            String callingName = getPackageName(Binder.getCallingUid());
            if (callingName != null && callingName.equals(pkgName)){
                return;
            }
            if (usedClassName.contains(pkgName)){
                return;
            }
            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
                return;
            }
            showBefore.simpleExecutor.execute(param);
//            param.setResult(0);
        },
                getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startActivityWithFeature",
                (SimpleExecutor) param -> {
            Intent intent = (Intent) param.args[3];
            if (intent == null){return;}
            ComponentName mComponent = intent.getComponent();
            if (mComponent == null){
                return;
            }
            String pkgName = mComponent.getPackageName().toLowerCase();
            String usedClassName = mComponent.getClassName().toLowerCase();
            String callingName = getPackageName(Binder.getCallingUid());
            if (callingName != null && callingName.equals(pkgName)){
                return;
            }
            if (usedClassName.contains(pkgName)){
                return;
            }
            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
                return;
            }
            showBefore.simpleExecutor.execute(param);
//            param.setResult(0);
        },
                getSystemChecker_PackageNameAt(1));
//        hookAllMethodsWithCache_Auto(hookClass,"unhandledBack",null);

//        hookAllMethodsWithCache_Auto(hookClass,"finishActivity",true);

        SimpleExecutor replaceIntentFilter = param -> {
//            LoggerLog(new Exception("replacing IntentFilter:" + Arrays.deepToString(param.args)));
            IntentFilter filter = findArgByClassInArgs(param.args,IntentFilter.class);
            if (filter == null){
                return;
            }
            int index = findClassIndexInArgs(param.args,IntentFilter.class);
            IntentFilter replaceFilter = new IntentFilter();
//            Object IIntentReceiverInstance = param.args[4];
//            if (IIntentReceiverInstance != null){
//                hookAllMethodsWithCache_Auto(IIntentReceiverInstance.getClass(),"performReceive",showBefore,noSystemChecker);
//            }
            for (Iterator<String> it = filter.actionsIterator(); it.hasNext(); ) {
                String action = it.next();
                if (Consts.BLACKLIST_INTENT_ACTION_SET.contains(action)){
                    continue;
                }
                String actionLower = action.toLowerCase();
                if (actionLower.contains("background") || actionLower.contains("foreground")){
                    continue;
                }
                if (actionLower.contains("*")){
                    continue;
                }
//                if (!DEFAULT_INTENT_ACTION_SET.contains(action)){
//                    if (!Objects.equals(getPackageName(Binder.getCallingUid()), "")){
//                        action = getPackageName(Binder.getCallingUid()) + "|" + action;
//                    }
//                }
                replaceFilter.addAction(action);
//                LoggerLog(param.args[1] + " registeringIntentFilter: " + getPackageName(Binder.getCallingUid()) + " -> " + action);
            }
            cloneIntentFilter(filter,replaceFilter);
            param.args[index] = replaceFilter;
        };
        hookAllMethodsWithCache_Auto(hookClass,"registerReceiver",replaceIntentFilter,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"registerReceiverWithFeature",replaceIntentFilter,getSystemChecker_PackageNameAt(1));
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterReceiver",null);

        SimpleExecutor showBroadcastIntent = param -> {
            int uid = Binder.getCallingUid();
            String packageName = getPackageName(uid);
            int intentIndex = findClassIndexInArgs(param.args,Intent.class);
            if (intentIndex == -1){
                return;
            }
            Intent intent = (Intent) param.args[intentIndex];
            if (intent != null){
                String action = intent.getAction();
                if (action != null){
                    if (!DEFAULT_INTENT_ACTION_SET.contains(action)){
                        intent = (Intent) intent.clone();
                        intent.setPackage(packageName);

                        param.args[intentIndex] = intent;
                    }
                }
            }
            showBefore.simpleExecutor.execute(param);
        };
        hookAllMethodsWithCache_Auto(hookClass,"broadcastIntent",showBroadcastIntent);
        hookAllMethodsWithCache_Auto(hookClass,"broadcastIntentWithFeature",showBroadcastIntent);

//        hookAllMethodsWithCache_Auto(hookClass,"unbroadcastIntent",null);
//        hookAllMethodsWithCache_Auto(hookClass,"finishReceiver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"attachApplication",null);
//        hookAllMethodsWithCache_Auto(hookClass,"finishAttachApplication",null);
        hookAllMethodsWithCache_Auto(hookClass,"getTasks",new SimpleExecutorWithMode(MODE_AFTER, param -> {
            List<ActivityManager.RunningTaskInfo> result = (List<ActivityManager.RunningTaskInfo>) param.getResult();
            if (result == null){return;}
            int uid = Binder.getCallingUid();
            if(isSystemApp(uid)){return;}
            String packageName = getPackageName(uid);
            if (packageName == null){return;}
            result.removeIf(runningTaskInfo -> !runningTaskInfo.baseIntent.getPackage().equals(packageName));
            List<ActivityManager.RunningTaskInfo> ret = new ArrayList<>();
            for (ActivityManager.RunningTaskInfo runningTaskInfo:result){
                ActivityManager.RunningTaskInfo clone = copyRunningTaskInfo(runningTaskInfo);
                modifyRunningTaskInfo(clone);
                ret.add(clone);
            }
            param.setResult(ret);
//            ret.forEach(runningTaskInfo -> runningTaskInfo.configuration.);
        }));
        hookAllMethodsWithCache_Auto(hookClass,"moveTaskToFront",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getTaskForActivity",(SimpleExecutor) param -> {});
//        hookAllMethodsWithCache_Auto(hookClass,"getContentProvider",ContentProviderHolder);
//        hookAllMethodsWithCache_Auto(hookClass,"publishContentProviders",null);
//        hookAllMethodsWithCache_Auto(hookClass,"refContentProvider",true);
        hookAllMethodsWithCache_Auto(hookClass,"getRunningServiceControlPanel",(SimpleExecutor) param -> {

            ComponentName mComponent = (ComponentName) param.args[0];
            if (mComponent == null){
                return;
            }
            String pkgName = mComponent.getPackageName().toLowerCase();
            String callingName = getPackageName(Binder.getCallingUid());
            if (callingName != null && callingName.equals(pkgName)){
                return;
            }
            String usedClassName = mComponent.getClassName().toLowerCase();
            if (usedClassName.contains(pkgName)){
                return;
            }
            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
                return;
            }
            param.setResult(null);
        });

//        hookAllMethodsWithCache_Auto(hookClass,"startService",(SimpleExecutor) param -> {
//
//            Intent intent = (Intent) param.args[1];
//            if (intent == null){return;}
//            ComponentName mComponent = intent.getComponent();
//            if (mComponent == null){
//                return;
//            }
//            String pkgName = mComponent.getPackageName().toLowerCase();
//            String callingName = getPackageName(Binder.getCallingUid());
//            if (callingName != null && callingName.equals(pkgName)){
//                return;
//            }
//            String usedClassName = mComponent.getClassName().toLowerCase();
//            if (usedClassName.contains(pkgName)){
//                return;
//            }
//            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
//                return;
//            }
//            param.setResult(null);
//        });
//        hookAllMethodsWithCache_Auto(hookClass,"stopService",0);
//        SimpleExecutor bindService = param -> {
//
//            Intent intent = (Intent) param.args[2];
//            if (intent == null){return;}
//            ComponentName mComponent = intent.getComponent();
//            if (mComponent == null){
//                return;
//            }
//            String pkgName = mComponent.getPackageName().toLowerCase();
//            String callingName = getPackageName(Binder.getCallingUid());
//            if (callingName != null && callingName.equals(pkgName)){
//                return;
//            }
//            String usedClassName = mComponent.getClassName().toLowerCase();
//            if (usedClassName.contains(pkgName)){
//                return;
//            }
//
//            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
//                return;
//            }
//            showBefore.simpleExecutor.execute(param);
////            param.setResult(0);
//        };
//        hookAllMethodsWithCache_Auto(hookClass,"bindService",bindService);
//        hookAllMethodsWithCache_Auto(hookClass,"bindServiceInstance",bindService);

        hookAllMethodsWithCache_Auto(hookClass,"updateServiceGroup",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unbindService",true);
//        hookAllMethodsWithCache_Auto(hookClass,"publishService",(SimpleExecutor) param -> {
//            Intent intent = (Intent) param.args[1];
//            if (intent == null){return;}
//            ComponentName mComponent = intent.getComponent();
//            if (mComponent == null){
//                return;
//            }
//            String pkgName = mComponent.getPackageName().toLowerCase();
//            String callingName = getPackageName(Binder.getCallingUid());
//            if (callingName != null && callingName.equals(pkgName)){
//                return;
//            }
//            String usedClassName = mComponent.getClassName().toLowerCase();
//            if (usedClassName.contains(pkgName)){
//                return;
//            }
//            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
//                return;
//            }
//            param.setResult(null);
//        });
//        hookAllMethodsWithCache_Auto(hookClass,"setDebugApp",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setAgentApp",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setAlwaysFinish",null);
////        hookAllMethodsWithCache_Auto(hookClass,"startInstrumentation",true);
////        hookAllMethodsWithCache_Auto(hookClass,"addInstrumentationResults",null);
////        hookAllMethodsWithCache_Auto(hookClass,"finishInstrumentation",null);
//
        hookAllMethodsWithCache_Auto(hookClass,"getConfiguration",modifyConfigurationResult);
        hookAllMethodsWithCache_Auto(hookClass,"updateConfiguration",
                (SimpleExecutor)param -> param.args[0]=modifyConfiguration((Configuration) param.args[0]));
//        hookAllMethodsWithCache_Auto(hookClass,"updateMccMncConfiguration",true);
//        hookAllMethodsWithCache_Auto(hookClass,"stopServiceToken",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setProcessLimit",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getProcessLimit",0);
//        hookAllMethodsWithCache_Auto(hookClass,"checkUriPermission",grantPermissionForDirectCall);
//        hookAllMethodsWithCache_Auto(hookClass,"checkContentUriPermissionFull",grantPermissionForDirectCall);
//        hookAllMethodsWithCache_Auto(hookClass,"checkUriPermissions",grantPermissionForDirectCall);
//        hookAllMethodsWithCache_Auto(hookClass,"grantUriPermission",null);
//        hookAllMethodsWithCache_Auto(hookClass,"revokeUriPermission",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setActivityController",null);
//        hookAllMethodsWithCache_Auto(hookClass,"showWaitingForDebugger",null);
//        hookAllMethodsWithCache_Auto(hookClass,"signalPersistentProcesses",null);
//    hookAllMethodsWithCache_Auto(hookClass,"getRecentTasks",null);
//        hookAllMethodsWithCache_Auto(hookClass,"serviceDoneExecuting",null);
        hookAllMethodsWithCache_Auto(hookClass,"getIntentSender",(SimpleExecutor)param -> {
            String packageName = (String) param.args[1];
            Intent[] intents = (Intent[]) param.args[5];
            if (intents == null){
                return;
            }
            if (intents.length == 0){
                return;
            }
            Intent[] copyOf = new Intent[intents.length];
            for (int i=0;i<intents.length;i++){
                Intent singleCopy = new Intent(intents[i]);
                singleCopy.setPackage(packageName);
                copyOf[i] = singleCopy;
            }
            param.args[5] = copyOf;
        },getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getIntentSenderWithFeature",
                new SimpleExecutorWithMode(MODE_BEFORE_AND_AFTER,param -> {
            String packageName = (String) param.args[1];
            Intent[] intents = (Intent[]) param.args[6];
            if (intents == null){
                return;
            }
            if (intents.length == 0){
                return;
            }
            Intent[] copyOf = new Intent[intents.length];
            for (int i=0;i<intents.length;i++){
                Intent singleCopy = new Intent(intents[i]);
                singleCopy.setPackage(packageName);
                copyOf[i] = singleCopy;
            }
            param.args[6] = copyOf;
//            Object result = param.getResult();
//            if (result != null){
//                LoggerLog(result);
//                hookAllMethodsWithCache_Auto(result.getClass(),"send",showBefore,noSystemChecker);
//            }
        }) ,
                getSystemChecker_PackageNameAt(1));//IIntentSender
//        hookAllMethodsWithCache_Auto(hookClass,"sendIntentSender",(SimpleExecutor)param ->{
//            Intent intent = (Intent) param.args[4];
//            LoggerLog(intent);
//        });
//        hookAllMethodsWithCache_Auto(hookClass,"cancelIntentSender",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getInfoForIntentSender",ActivityManager);
//        hookAllMethodsWithCache_Auto(hookClass,"registerIntentSenderCancelListenerEx",true);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterIntentSenderCancelListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"enterSafeMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"noteWakeupAlarm",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeContentProvider",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setRequestedOrientation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unbindFinished",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setProcessImportant",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setServiceForeground",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getForegroundServiceType",0);
//        hookAllMethodsWithCache_Auto(hookClass,"moveActivityTaskToBack",true);
//        hookAllMethodsWithCache_Auto(hookClass,"getMemoryInfo",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getProcessesInErrorState",List);
//        hookAllMethodsWithCache_Auto(hookClass,"clearApplicationUserData",true);
//        hookAllMethodsWithCache_Auto(hookClass,"stopAppForUser",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerForegroundServiceObserver",true);
//        hookAllMethodsWithCache_Auto(hookClass,"forceStopPackage",null);
//        hookAllMethodsWithCache_Auto(hookClass,"forceStopPackageEvenWhenStopping",null);
//        hookAllMethodsWithCache_Auto(hookClass,"killPids",true);

////    hookAllMethodsWithCache_Auto(hookClass,"peekService",IBinder);
//        hookAllMethodsWithCache_Auto(hookClass,"profileControl",true);
//        hookAllMethodsWithCache_Auto(hookClass,"shutdown",true);
//        hookAllMethodsWithCache_Auto(hookClass,"stopAppSwitches",null);
//        hookAllMethodsWithCache_Auto(hookClass,"resumeAppSwitches",null);
//        hookAllMethodsWithCache_Auto(hookClass,"bindBackupAgent",true);
//        hookAllMethodsWithCache_Auto(hookClass,"backupAgentCreated",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unbindBackupAgent",null);
//        hookAllMethodsWithCache_Auto(hookClass,"handleIncomingUser",0);
//        hookAllMethodsWithCache_Auto(hookClass,"addPackageDependency",null);
//        hookAllMethodsWithCache_Auto(hookClass,"killApplication",null);
//        hookAllMethodsWithCache_Auto(hookClass,"closeSystemDialogs",null);
            hookAllMethodsWithCache_Auto(hookClass,"getProcessMemoryInfo",null);//Debug
//        hookAllMethodsWithCache_Auto(hookClass,"killApplicationProcess",null);
//        hookAllMethodsWithCache_Auto(hookClass,"handleApplicationWtf",true);
//        hookAllMethodsWithCache_Auto(hookClass,"killBackgroundProcesses",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isUserAMonkey",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getRunningExternalApplications",List);
//        hookAllMethodsWithCache_Auto(hookClass,"finishHeavyWeightApp",null);
//        hookAllMethodsWithCache_Auto(hookClass,"handleApplicationStrictModeViolation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerStrictModeCallback",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isTopActivityImmersive",true);
//        hookAllMethodsWithCache_Auto(hookClass,"crashApplicationWithType",null);
//        hookAllMethodsWithCache_Auto(hookClass,"crashApplicationWithTypeWithExtras",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getMimeTypeFilterAsync",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dumpHeap",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isUserRunning",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setPackageScreenCompatMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"switchUser",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getSwitchingFromUserMessage",String);
////    hookAllMethodsWithCache_Auto(hookClass,"getSwitchingToUserMessage",String);
//        hookAllMethodsWithCache_Auto(hookClass,"setStopUserOnSwitch",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeTask",true);
//        hookAllMethodsWithCache_Auto(hookClass,"registerProcessObserver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterProcessObserver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isIntentSenderTargetedToPackage",true);
//        hookAllMethodsWithCache_Auto(hookClass,"updatePersistentConfiguration",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updatePersistentConfigurationWithAttribution",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getProcessPss",long);
//        hookAllMethodsWithCache_Auto(hookClass,"showBootMessage",null);
//        hookAllMethodsWithCache_Auto(hookClass,"killAllBackgroundProcesses",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getContentProviderExternal",ContentProviderHolder);
//        hookAllMethodsWithCache_Auto(hookClass,"removeContentProviderExternal",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeContentProviderExternalAsUser",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getMyMemoryState",null);
//        hookAllMethodsWithCache_Auto(hookClass,"killProcessesBelowForeground",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getCurrentUser",UserInfo);
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentUserId",0);
//        hookAllMethodsWithCache_Auto(hookClass,"getLaunchedFromUid",0);
//        hookAllMethodsWithCache_Auto(hookClass,"unstableProviderDied",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isIntentSenderAnActivity",true);
//        hookAllMethodsWithCache_Auto(hookClass,"startActivityAsUser",0);
//        hookAllMethodsWithCache_Auto(hookClass,"startActivityAsUserWithFeature",0);
//        hookAllMethodsWithCache_Auto(hookClass,"stopUser",0);
//        hookAllMethodsWithCache_Auto(hookClass,"stopUserWithCallback",0);
//        hookAllMethodsWithCache_Auto(hookClass,"stopUserExceptCertainProfiles",0);
//        hookAllMethodsWithCache_Auto(hookClass,"stopUserWithDelayedLocking",0);
//        hookAllMethodsWithCache_Auto(hookClass,"registerUserSwitchObserver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterUserSwitchObserver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getRunningUserIds",0);
//        hookAllMethodsWithCache_Auto(hookClass,"requestSystemServerHeapDump",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestBugReportWithDescription",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestTelephonyBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestWifiBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestInteractiveBugReportWithDescription",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestInteractiveBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestBugReportWithExtraAttachment",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestFullBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestRemoteBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"launchBugReportHandlerApp",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getBugreportWhitelistedPackages",List);
////    hookAllMethodsWithCache_Auto(hookClass,"getIntentForIntentSender",Intent);
////    hookAllMethodsWithCache_Auto(hookClass,"getLaunchedFromPackage",String);
        hookAllMethodsWithCache_Auto(hookClass,"killUid",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setUserIsMonkey",null);
//        hookAllMethodsWithCache_Auto(hookClass,"hang",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getAllRootTaskInfos",List);
//        hookAllMethodsWithCache_Auto(hookClass,"moveTaskToRootTask",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setFocusedRootTask",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getFocusedRootTaskInfo",ActivityTaskManager);
//        hookAllMethodsWithCache_Auto(hookClass,"restart",null);
//        hookAllMethodsWithCache_Auto(hookClass,"performIdleMaintenance",null);
//        hookAllMethodsWithCache_Auto(hookClass,"appNotRespondingViaProvider",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getTaskBounds",Rect);
//        hookAllMethodsWithCache_Auto(hookClass,"setProcessMemoryTrimLevel",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getTagForIntentSender",String);
//        hookAllMethodsWithCache_Auto(hookClass,"startUserInBackground",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isInLockTaskMode",true);
//        hookAllMethodsWithCache_Auto(hookClass,"startActivityFromRecents",0);
//        hookAllMethodsWithCache_Auto(hookClass,"startSystemLockTaskMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isTopOfTask",true);
//        hookAllMethodsWithCache_Auto(hookClass,"bootAnimationComplete",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setThemeOverlayReady",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerTaskStackListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterTaskStackListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"notifyCleartextNetwork",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setTaskResizeable",null);
//        hookAllMethodsWithCache_Auto(hookClass,"resizeTask",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getLockTaskModeState",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setDumpHeapDebugLimit",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dumpHeapFinished",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updateLockTaskPackages",null);
//        hookAllMethodsWithCache_Auto(hookClass,"noteAlarmStart",null);
//        hookAllMethodsWithCache_Auto(hookClass,"noteAlarmFinish",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getPackageProcessState",0);
//        hookAllMethodsWithCache_Auto(hookClass,"startBinderTracking",true);
//        hookAllMethodsWithCache_Auto(hookClass,"stopBinderTrackingAndDump",true);
//        hookAllMethodsWithCache_Auto(hookClass,"suppressResizeConfigChanges",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unlockUser",true);
//        hookAllMethodsWithCache_Auto(hookClass,"unlockUser2",true);
//        hookAllMethodsWithCache_Auto(hookClass,"killPackageDependents",null);
//        hookAllMethodsWithCache_Auto(hookClass,"makePackageIdle",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setDeterministicUidIdle",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getMemoryTrimLevel",0);
//        hookAllMethodsWithCache_Auto(hookClass,"isVrModePackageEnabled",true);
//        hookAllMethodsWithCache_Auto(hookClass,"notifyLockedProfile",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startConfirmDeviceCredentialIntent",null);
//        hookAllMethodsWithCache_Auto(hookClass,"sendIdleJobTrigger",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isBackgroundRestricted",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setRenderThread",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setHasTopUi",null);
//        hookAllMethodsWithCache_Auto(hookClass,"cancelTaskWindowTransition",null);
//        hookAllMethodsWithCache_Auto(hookClass,"scheduleApplicationInfoChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setPersistentVrThread",null);
//        hookAllMethodsWithCache_Auto(hookClass,"waitForNetworkStateUpdate",null);
//        hookAllMethodsWithCache_Auto(hookClass,"backgroundAllowlistUid",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startUserInBackgroundWithListener",true);
//        hookAllMethodsWithCache_Auto(hookClass,"startDelegateShellPermissionIdentity",null);
//        hookAllMethodsWithCache_Auto(hookClass,"stopDelegateShellPermissionIdentity",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getDelegatedShellPermissions",List);
////    hookAllMethodsWithCache_Auto(hookClass,"getLifeMonitor",ParcelFileDescriptor);
//        hookAllMethodsWithCache_Auto(hookClass,"startUserInForegroundWithListener",true);
//        hookAllMethodsWithCache_Auto(hookClass,"appNotResponding",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getHistoricalProcessStartReasons",ParceledListSlice);
//        hookAllMethodsWithCache_Auto(hookClass,"addApplicationStartInfoCompleteListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeApplicationStartInfoCompleteListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"addStartInfoTimestamp",null);
//        hookAllMethodsWithCache_Auto(hookClass,"reportStartInfoViewTimestamps",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getHistoricalProcessExitReasons",ParceledListSlice);
//        hookAllMethodsWithCache_Auto(hookClass,"killProcessesWhenImperceptible",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setActivityLocusContext",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setProcessStateSummary",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isAppFreezerSupported",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isAppFreezerEnabled",true);
//        hookAllMethodsWithCache_Auto(hookClass,"killUidForPermissionChange",null);
//        hookAllMethodsWithCache_Auto(hookClass,"resetAppErrors",null);
//        hookAllMethodsWithCache_Auto(hookClass,"enableAppFreezer",true);
//        hookAllMethodsWithCache_Auto(hookClass,"enableFgsNotificationRateLimit",true);
//        hookAllMethodsWithCache_Auto(hookClass,"holdLock",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startProfile",true);
//        hookAllMethodsWithCache_Auto(hookClass,"stopProfile",true);
////    hookAllMethodsWithCache_Auto(hookClass,"queryIntentComponentsForIntentSender",ParceledListSlice);
//        hookAllMethodsWithCache_Auto(hookClass,"getUidProcessCapabilities",0);
//        hookAllMethodsWithCache_Auto(hookClass,"waitForBroadcastIdle",null);
//        hookAllMethodsWithCache_Auto(hookClass,"waitForBroadcastBarrier",null);
//        hookAllMethodsWithCache_Auto(hookClass,"forceDelayBroadcastDelivery",null);
        hookAllMethodsWithCache_Auto(hookClass,"isModernBroadcastQueueEnabled",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isProcessFrozen",false);
//        hookAllMethodsWithCache_Auto(hookClass,"getBackgroundRestrictionExemptionReason",0);
//        hookAllMethodsWithCache_Auto(hookClass,"startUserInBackgroundVisibleOnDisplay",true);
//        hookAllMethodsWithCache_Auto(hookClass,"startProfileWithListener",true);
//        hookAllMethodsWithCache_Auto(hookClass,"restartUserInBackground",0);
//        hookAllMethodsWithCache_Auto(hookClass,"getDisplayIdsForStartingVisibleBackgroundUsers",0);
//        hookAllMethodsWithCache_Auto(hookClass,"shouldServiceTimeOut",true);
//        hookAllMethodsWithCache_Auto(hookClass,"hasServiceTimeLimitExceeded",true);
//        hookAllMethodsWithCache_Auto(hookClass,"registerUidFrozenStateChangedCallback",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterUidFrozenStateChangedCallback",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getUidFrozenState",0);


        hookAllMethodsWithCache_Auto(hookClass,"checkPermissionForDevice", (SimpleExecutor)param -> {
            Exception stackShower = new Exception();
            StackTraceElement[] stacks = stackShower.getStackTrace();

            if (stacks.length >= 5){
                for (int i=0;i<stackStringHeads.length;i++){
                    if (!stacks[stacks.length - stackStringHeads.length + i].toString().startsWith(stackStringHeads[i])){
                        return;
                    }
                }
//                showBefore.simpleExecutor.execute(param);
//                param.setResult(PERMISSION_GRANTED);
            }
        });//really given out!idk why.

//        hookAllMethodsWithCache_Auto(hookClass,"frozenBinderTransactionDetected",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getBindingUidProcessState",0);
////    hookAllMethodsWithCache_Auto(hookClass,"getUidLastIdleElapsedTime",long);
//        hookAllMethodsWithCache_Auto(hookClass,"addOverridePermissionState",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeOverridePermissionState",null);
//        hookAllMethodsWithCache_Auto(hookClass,"clearOverridePermissionStates",null);
//        hookAllMethodsWithCache_Auto(hookClass,"clearAllOverridePermissionStates",null);
//        hookAllMethodsWithCache_Auto(hookClass,"noteAppRestrictionEnabled",null);

        //system already limited.
//        hookAllMethodsWithCache_Auto(hookClass,"getServices",showAfter);//EMPTY_ARRAYLIST
//        hookAllMethodsWithCache_Auto(hookClass,"getRunningAppProcesses",showAfter);//EMPTY_ARRAYLIST
    }

    public static void cloneIntentFilter(IntentFilter fromObj, IntentFilter toObj){
        XposedHelpers.setIntField(toObj,"mPriority",XposedHelpers.getIntField(fromObj,"mPriority"));
        XposedHelpers.setIntField(toObj,"mOrder",XposedHelpers.getIntField(fromObj,"mOrder"));
//        XposedHelpers.setObjectField(toObj,"mActions",XposedHelpers.getObjectField(fromObj,"mActions"));
        XposedHelpers.setObjectField(toObj,"mCategories",XposedHelpers.getObjectField(fromObj,"mCategories"));
        XposedHelpers.setObjectField(toObj,"mDataSchemes",XposedHelpers.getObjectField(fromObj,"mDataSchemes"));
        XposedHelpers.setObjectField(toObj,"mDataSchemeSpecificParts",XposedHelpers.getObjectField(fromObj,"mDataSchemeSpecificParts"));
        XposedHelpers.setObjectField(toObj,"mDataAuthorities",XposedHelpers.getObjectField(fromObj,"mDataAuthorities"));
        XposedHelpers.setObjectField(toObj,"mDataPaths",XposedHelpers.getObjectField(fromObj,"mDataPaths"));
        XposedHelpers.setObjectField(toObj,"mStaticDataTypes",XposedHelpers.getObjectField(fromObj,"mStaticDataTypes"));
        XposedHelpers.setObjectField(toObj,"mDataTypes",XposedHelpers.getObjectField(fromObj,"mDataTypes"));
        XposedHelpers.setObjectField(toObj,"mMimeGroups",XposedHelpers.getObjectField(fromObj,"mMimeGroups"));
        XposedHelpers.setBooleanField(toObj,"mHasStaticPartialTypes",XposedHelpers.getBooleanField(fromObj,"mHasStaticPartialTypes"));
        XposedHelpers.setBooleanField(toObj,"mHasDynamicPartialTypes",XposedHelpers.getBooleanField(fromObj,"mHasDynamicPartialTypes"));
        XposedHelpers.setIntField(toObj,"mVerifyState",XposedHelpers.getIntField(fromObj,"mVerifyState"));
        XposedHelpers.setIntField(toObj,"mInstantAppVisibility",XposedHelpers.getIntField(fromObj,"mInstantAppVisibility"));
    }
    public static void hookIPermissionController(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"checkPermission",(SimpleExecutor)param -> {
            Exception stackShower = new Exception("requesting permission:" + Arrays.deepToString(param.args) + Binder.getCallingUid());
            for (StackTraceElement s: stackShower.getStackTrace()){
                if ((String.valueOf(s)).contains("ContextImpl")){
                    return;
                }
            }
//            param.setResult(true);
//            LoggerLog(new Exception("requesting permission:" + Arrays.deepToString(param.args) + Binder.getCallingUid()));
        });
        hookAllMethodsWithCache_Auto(hookClass,"revokeRuntimePermissions",null);
        hookAllMethodsWithCache_Auto(hookClass,"getRuntimePermissionBackup",null);
        hookAllMethodsWithCache_Auto(hookClass,"stageAndApplyRuntimePermissionsBackup",null);
        hookAllMethodsWithCache_Auto(hookClass,"applyStagedRuntimePermissionBackup",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAppPermissions",null);
        hookAllMethodsWithCache_Auto(hookClass,"revokeRuntimePermission",null);
        hookAllMethodsWithCache_Auto(hookClass,"countPermissionApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPermissionUsages",null);
        hookAllMethodsWithCache_Auto(hookClass,"setRuntimePermissionGrantStateByDeviceAdminFromParams",null);
        hookAllMethodsWithCache_Auto(hookClass,"grantOrUpgradeDefaultRuntimePermissions",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyOneTimePermissionSessionTimeout",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateUserSensitiveForApp",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPrivilegesDescriptionStringForProfile",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPlatformPermissionsForGroup",null);
        hookAllMethodsWithCache_Auto(hookClass,"getGroupOfPlatformPermission",null);
        hookAllMethodsWithCache_Auto(hookClass,"getUnusedAppCount",null);
        hookAllMethodsWithCache_Auto(hookClass,"getHibernationEligibility",null);
        hookAllMethodsWithCache_Auto(hookClass,"revokeSelfPermissionsOnKill",null);
    }

    public static void hookActivityManagerService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"broadcastIntentLockedTraced",(SimpleExecutor) param -> {

        },getSystemChecker_PackageNameAt(1));
    }

    public static void hookBroadcastQueue(@NonNull Class<?> hookClass){
        initBroadcastRecordUtils(hookClass.getClassLoader());
        hookAllMethodsWithCache_Auto(hookClass,"enqueueBroadcastLocked",(SimpleExecutor)param -> {
            if (param.args[0] == null){return;}
            BroadcastRecordUtils.BroadcastRecordInformation info = outputInformation(param.args[0]);
            if (info.infoA == null){return;}//no sender cast?
            if (info.infoB == null){
                LoggerLog("no receiver broadcast:" + info.intent + "|" + info.infoA);
                return;
            }
            if (info.infoA.uid == info.infoB.uid){return;}
            if (info.infoA.appInfo!= null && info.infoB.appInfo != null){
                if (info.infoA.appInfo.isSystemApp() && info.infoB.appInfo.isSystemApp()){
                    return;//system to system,don't care
                }
                if (Objects.equals(info.infoA.appInfo.uid, info.infoB.appInfo.uid)){
                    return;//uid calling self
                }
                if (Objects.equals(info.infoA.appInfo.packageName, info.infoB.appInfo.packageName)
                        && info.infoB.appInfo.packageName != null){
                    return;//package calling self
                }
            }
            LoggerLog("broadcast:" + info.intent + "|" + info.infoA + "|" + info.infoB);
        },getSystemChecker_BroadcastRecordAt(0));
    }

    private static Field activityRecord_packageName = null;
    private static Field activityRecord_info = null;
    public static boolean isActivityRecordSystem(Object activityRecord){
        try {
            if (activityRecord == null){return true;}
            if (activityRecord_packageName == null){
                activityRecord_packageName = XposedHelpers.findField(activityRecord.getClass(),"packageName");
            }
            if (activityRecord_info == null){
                activityRecord_info = XposedHelpers.findField(activityRecord.getClass(),"info");
            }
            String packageName = (String) activityRecord_packageName.get(activityRecord);
            ActivityInfo activityInfo = (ActivityInfo) activityRecord_info.get(activityRecord);
            if (activityInfo != null && !isSystemApplicationInfo(activityInfo.applicationInfo)){return false;}
            return isSystemApp(packageName);
        }catch (Exception e){
            LoggerLog(e);
            return true;
        }
    }
    public static final SystemAppChecker activityRecordSysChecker = param -> isActivityRecordSystem(param.thisObject) && isSystemApp(Binder.getCallingUid());
    public static final LocaleList ZH_CN_LOCALE = new LocaleList(Locale.SIMPLIFIED_CHINESE);



    //TODO:Finish(especially mcc and mnc)

    public static Configuration modifyConfiguration(Configuration toModify){
        int uid = Binder.getCallingUid();
        if (!isSystemApp(uid)){
            return modifyConfigurationWithUid(toModify,uid);
        }
        return modifyConfigurationInternal(toModify);
    }
    private static Configuration modifyConfigurationInternal(Configuration toModify){
        if (toModify == null){return null;}
        Configuration configuration = new Configuration(toModify);
        XposedHelpers.setObjectField(configuration, "windowConfiguration", modifyWindowConfiguration(configuration.windowConfiguration));
        configuration.setLocales(ZH_CN_LOCALE);
        configuration.userSetLocale = false;
        configuration.navigationHidden = NAVIGATIONHIDDEN_YES;

        return configuration;
    }
    public static Configuration modifyConfigurationWithUid(Configuration toModify,int uid){
        if (toModify == null){return null;}
        Configuration configuration = modifyConfigurationInternal(toModify);

        int[] MCCAndMNC = MCCAndMNCFromUid(uid);
        configuration.mcc = MCCAndMNC[0];
        configuration.mnc = MCCAndMNC[1];
        configuration.setGrammaticalGender(grammaticalGenderFromUid(uid));
        configuration.colorMode = colorModeFromUid(uid);
//        configuration.screenLayout
//        configuration.fontWeightAdjustment

        return configuration;
    }
    public static WindowConfiguration modifyWindowConfiguration(WindowConfiguration toModify){
        if (toModify == null){return null;}
        WindowConfiguration windowConfiguration = new WindowConfiguration(toModify);
        windowConfiguration.setActivityType(ACTIVITY_TYPE_STANDARD);
        windowConfiguration.setDisplayWindowingMode(WINDOWING_MODE_FULLSCREEN);
        windowConfiguration.setRotation(0);
        windowConfiguration.setDisplayRotation(0);
        windowConfiguration.setWindowingMode(WINDOWING_MODE_FULLSCREEN);
        return windowConfiguration;
    }
    public static final SimpleExecutor modifyConfigurationExecutor = param -> {
        Pair<Integer, Configuration> configurationPair = findClassIndexAndObjectInArgs(param.args, Configuration.class);
        if (configurationPair.second != null){
            param.args[configurationPair.first] = modifyConfiguration(configurationPair.second);
        }
    };
    public static void hookActivityRecord(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"scheduleConfigurationChanged", modifyConfigurationExecutor,activityRecordSysChecker);
        hookAllMethodsWithCache_Auto(hookClass,"scheduleActivityMovedToDisplay", modifyConfigurationExecutor,activityRecordSysChecker);
        hookAllMethodsWithCache_Auto(hookClass,"scheduleTopResumedActivityChanged", (SimpleExecutor)param -> {
            if (Objects.equals(false,param.args[0])){param.setResult(true);return;}
        },activityRecordSysChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"updateReportedConfigurationAndSend",
//                (SimpleExecutor)param -> {
//
//                },
//                activityRecordSysChecker);
    }

    public static ActivityManager.RunningTaskInfo copyRunningTaskInfo(ActivityManager.RunningTaskInfo source){
        ActivityManager.RunningTaskInfo clone = new ActivityManager.RunningTaskInfo();
        clone.userId = source.userId;
        clone.taskId = source.taskId;
        clone.isRunning = source.isRunning;
        clone.baseIntent = source.baseIntent == null?null:new Intent(source.baseIntent);

        clone.baseActivity = source.baseActivity;
        clone.topActivity = source.topActivity;
        clone.origActivity = source.origActivity;
        clone.realActivity = source.realActivity;

        clone.numActivities = source.numActivities;
        clone.lastActiveTime = source.lastActiveTime;
        clone.displayId = source.displayId;
        clone.displayAreaFeatureId = source.displayAreaFeatureId;
        clone.taskDescription = source.taskDescription == null?null:new ActivityManager.TaskDescription(source.taskDescription);
        clone.mTopActivityLocusId = source.mTopActivityLocusId;
        clone.supportsMultiWindow = source.supportsMultiWindow;
        clone.resizeMode = source.resizeMode;

        clone.configuration.setTo(source.configuration);
        clone.token = source.token;
        clone.pictureInPictureParams = source.pictureInPictureParams == null?null:new PictureInPictureParams(source.pictureInPictureParams);

        clone.shouldDockBigOverlays = source.shouldDockBigOverlays;
        clone.launchIntoPipHostTaskId = source.launchIntoPipHostTaskId;
        clone.lastParentTaskIdBeforePip = source.lastParentTaskIdBeforePip;
        clone.displayCutoutInsets = source.displayCutoutInsets == null?null:new Rect(source.displayCutoutInsets);
        clone.topActivityType = source.topActivityType;
        clone.topActivityInfo = source.topActivityInfo == null?null:new ActivityInfo(source.topActivityInfo);
        clone.topActivityInSizeCompat = source.topActivityInSizeCompat;
        clone.topActivityEligibleForLetterboxEducation = source.topActivityEligibleForLetterboxEducation;
        clone.isLetterboxDoubleTapEnabled = source.isLetterboxDoubleTapEnabled;
        clone.isFromLetterboxDoubleTap = source.isFromLetterboxDoubleTap;
        clone.topActivityLetterboxVerticalPosition = source.topActivityLetterboxVerticalPosition;
        clone.topActivityLetterboxHorizontalPosition = source.topActivityLetterboxHorizontalPosition;
        clone.topActivityLetterboxWidth = source.topActivityLetterboxWidth;
        clone.topActivityLetterboxHeight = source.topActivityLetterboxHeight;
        clone.isResizeable = source.isResizeable;
        clone.minWidth = source.minWidth;
        clone.minHeight = source.minHeight;
        clone.defaultMinSize = source.defaultMinSize;
        clone.positionInParent = source.positionInParent == null?null:new Point(source.positionInParent);
        clone.launchCookies = source.launchCookies;
        clone.parentTaskId = source.parentTaskId;
        clone.isFocused = source.isFocused;
        clone.isVisible = source.isVisible;
        clone.isSleeping = source.isSleeping;
        clone.cameraCompatControlState = source.cameraCompatControlState;
        return clone;
    }

    //TODO:Finish
    public static void modifyRunningTaskInfo(ActivityManager.RunningTaskInfo source){
        source.configuration.setTo(modifyConfiguration(source.configuration));

    }
}
