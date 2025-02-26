package com.linearity.datservicereplacement.ActivityManagerService;

import static com.linearity.datservicereplacement.ActivityManagerService.HookIActivityManager.modifyConfigurationExecutor;
import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.getPackageName;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findArgByClassInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexAndObjectInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.util.Pair;

import com.linearity.utils.SimpleExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HookIActivityTaskManager {

    public static void doHook(){
        classesAndHooks.put("com.android.server.wm.ActivityTaskManagerService", HookIActivityTaskManager::hookIActivityTaskManager);
    }
    public static boolean startActivityIntentFilter(Intent i){
        if (i == null){
            return false;
        }
        String action = i.getAction();
        ComponentName componentName = i.getComponent();
        if (action == null && componentName == null){
            return false;
        }
        if (action != null){
            if (action.startsWith("android.content.pm.action.REQUEST_PERMISSIONS")){
                return true;
            }
        }
        return false;
    }
    public static boolean isStartActivityInPackage(Intent i,String callingPackage){
        if (i == null){
            return false;
        }
        String action = i.getAction();
        ComponentName componentName = i.getComponent();
        if (componentName == null){
            return false;
        }
        if (Objects.equals(componentName.getPackageName(),callingPackage)){
            return true;
        }
        return false;
    }

    public static final SimpleExecutor startActivityReturnBoolean = param -> {
        Intent intent = findArgByClassInArgs(param.args,Intent.class);
        if (startActivityIntentFilter(intent)){
            param.setResult(true);
            LoggerLog(new Exception("startActivity_disallowed:"+ Arrays.deepToString(param.args)));
            return;
        }
        LoggerLog(new Exception("startActivity_allowed:"+ Arrays.deepToString(param.args)));
    };
    public static final SimpleExecutor startActivityReturnInt = param -> {
        Intent intent = findArgByClassInArgs(param.args,Intent.class);
        if (startActivityIntentFilter(intent)){
            param.setResult(0);
            LoggerLog(new Exception("startActivity_disallowed:"+ Arrays.deepToString(param.args)));
            return;
        }
        if (!isStartActivityInPackage(intent,getPackageName(Binder.getCallingUid()))){
            LoggerLog(new Exception("startActivity_allowed:" + Arrays.deepToString(param.args)));
        }
    };
    public static final SimpleExecutor startActivityReturnNull = param -> {
        Intent intent = findArgByClassInArgs(param.args,Intent.class);
        if (startActivityIntentFilter(intent)){
            param.setResult(null);
            LoggerLog(new Exception("startActivity_disallowed:"+ Arrays.deepToString(param.args)));
            return;
        }
        LoggerLog(new Exception("startActivity_allowed:"+ Arrays.deepToString(param.args)));
    };
    public static final SimpleExecutor startActivitiesByIntentArray = param -> {
        Pair<Integer,Intent[]> pair = findClassIndexAndObjectInArgs(param.args,Intent[].class);
        if (pair.second == null){return;}
        List<Intent> intentList = Arrays.asList(pair.second);
        intentList.removeIf(HookIActivityTaskManager::startActivityIntentFilter);
        param.args[pair.first] = intentList.toArray();
        LoggerLog(new Exception("startActivities_allowed:"+ Arrays.deepToString(param.args)));
    };
    public static void hookIActivityTaskManager(Class<?> hookClass){

        hookAllMethodsWithCache_Auto(hookClass,"startActivity",startActivityReturnInt,getSystemChecker_PackageNameAt(1));
//        hookAllMethodsWithCache_Auto(hookClass,"startActivityFromRecents",0);
        hookAllMethodsWithCache_Auto(hookClass,"startActivityAsCaller",startActivityReturnInt,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startActivities",startActivitiesByIntentArray,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startActivityAsUser",startActivityReturnInt,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startNextMatchingActivity",startActivityReturnBoolean);
        hookAllMethodsWithCache_Auto(hookClass,"startActivityIntentSender",startActivityReturnInt);
//        hookAllMethodsWithCache_Auto(hookClass,"startActivityAndWait",WaitResult,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startActivityWithConfig",startActivityReturnInt,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startVoiceActivity",startActivityReturnInt,getSystemChecker_PackageNameAt(0));
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getVoiceInteractorPackageName","");
        hookAllMethodsWithCache_Auto(hookClass,"startAssistantActivity",startActivityReturnInt,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"startActivityFromGameSession",startActivityReturnInt,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startRecentsActivity",startActivityReturnNull);
        hookAllMethodsWithCache_Auto(hookClass,"isActivityStartAllowedOnDisplay",true);
//        hookAllMethodsWithCache_Auto(hookClass,"unhandledBack",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getActivityClientController",IActivityClientController);
//        hookAllMethodsWithCache_Auto(hookClass,"getFrontActivityScreenCompatMode",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setFrontActivityScreenCompatMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setFocusedTask",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeTask",true);
//        hookAllMethodsWithCache_Auto(hookClass,"removeAllVisibleRecentTasks",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getTasks",EMPTY_ARRAYLIST);
//        hookAllMethodsWithCache_Auto(hookClass,"moveTaskToFront",null,getSystemChecker_PackageNameAt(1));
////        hookAllMethodsWithCache_Auto(hookClass,"getRecentTasks",ParceledListSlice<ActivityManager.RecentTaskInfo>);
//        hookAllMethodsWithCache_Auto(hookClass,"isTopActivityImmersive",true);
//        hookAllMethodsWithCache_Auto(hookClass,"reportAssistContextExtras",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setFocusedRootTask",null);
////        hookAllMethodsWithCache_Auto(hookClass,"getFocusedRootTaskInfo",ActivityTaskManager.RootTaskInfo);
////        hookAllMethodsWithCache_Auto(hookClass,"getTaskBounds",Rect);
//        hookAllMethodsWithCache_Auto(hookClass,"focusTopTask",null);
//        hookAllMethodsWithCache_Auto(hookClass,"cancelRecentsAnimation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updateLockTaskPackages",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isInLockTaskMode",true);
//        hookAllMethodsWithCache_Auto(hookClass,"getLockTaskModeState",0);
//        hookAllMethodsWithCache_Auto(hookClass,"getAppTasks",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"startSystemLockTaskMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopSystemLockTaskMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"finishVoiceTask",null);
//        hookAllMethodsWithCache_Auto(hookClass,"addAppTask",0);
////        hookAllMethodsWithCache_Auto(hookClass,"getAppTaskThumbnailSize",Point);
//        hookAllMethodsWithCache_Auto(hookClass,"releaseSomeActivities",null);
////        hookAllMethodsWithCache_Auto(hookClass,"getTaskDescriptionIcon",Bitmap);
//        hookAllMethodsWithCache_Auto(hookClass,"registerTaskStackListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterTaskStackListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setTaskResizeable",null);
//        hookAllMethodsWithCache_Auto(hookClass,"resizeTask",null);
//        hookAllMethodsWithCache_Auto(hookClass,"moveRootTaskToDisplay",null);
//        hookAllMethodsWithCache_Auto(hookClass,"moveTaskToRootTask",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeRootTasksInWindowingModes",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeRootTasksWithActivityTypes",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getAllRootTaskInfos",EMPTY_ARRAYLIST);
////        hookAllMethodsWithCache_Auto(hookClass,"getRootTaskInfo",ActivityTaskManager.RootTaskInfo);
//        hookAllMethodsWithCache_Auto(hookClass,"getAllRootTaskInfosOnDisplay",EMPTY_ARRAYLIST);
////        hookAllMethodsWithCache_Auto(hookClass,"getRootTaskInfoOnDisplay",ActivityTaskManager.RootTaskInfo);
        hookAllMethodsWithCache_Auto(hookClass,"setLockScreenShown",null);
////        hookAllMethodsWithCache_Auto(hookClass,"getAssistContextExtras",Bundle);
//        hookAllMethodsWithCache_Auto(hookClass,"requestAssistContextExtras",true);
//        hookAllMethodsWithCache_Auto(hookClass,"requestAutofillData",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isAssistDataAllowed",true);
//        hookAllMethodsWithCache_Auto(hookClass,"requestAssistDataForTask",true,getSystemChecker_PackageNameAt(2));
//        hookAllMethodsWithCache_Auto(hookClass,"keyguardGoingAway",null);
//        hookAllMethodsWithCache_Auto(hookClass,"suppressResizeConfigChanges",null);
////        hookAllMethodsWithCache_Auto(hookClass,"getWindowOrganizerController",IWindowOrganizerController);
//        hookAllMethodsWithCache_Auto(hookClass,"supportsLocalVoiceInteraction",true);
//        hookAllMethodsWithCache_Auto(hookClass,"getDeviceConfigurationInfo", new SimpleExecutorWithMode(MODE_AFTER,param -> {
//            ConfigurationInfo configurationInfo = (ConfigurationInfo) param.getResult();
//            if (configurationInfo == null){return;}
//            configurationInfo.
//        }));
//        hookAllMethodsWithCache_Auto(hookClass,"cancelTaskWindowTransition",null);
////        hookAllMethodsWithCache_Auto(hookClass,"getTaskSnapshot",android.window.TaskSnapshot);
////        hookAllMethodsWithCache_Auto(hookClass,"takeTaskSnapshot",android.window.TaskSnapshot);
//        hookAllMethodsWithCache_Auto(hookClass,"getLastResumedActivityUserId",0);
        hookAllMethodsWithCache_Auto(hookClass,"updateConfiguration",modifyConfigurationExecutor);
//        hookAllMethodsWithCache_Auto(hookClass,"updateLockTaskFeatures",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerRemoteAnimationForNextActivityStart",null,getSystemChecker_PackageNameAt(0));
//        hookAllMethodsWithCache_Auto(hookClass,"registerRemoteAnimationsForDisplay",null);
//        hookAllMethodsWithCache_Auto(hookClass,"alwaysShowUnsupportedCompileSdkWarning",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setVrThread",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setPersistentVrThread",null);
//        hookAllMethodsWithCache_Auto(hookClass,"stopAppSwitches",null);
//        hookAllMethodsWithCache_Auto(hookClass,"resumeAppSwitches",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setActivityController",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setVoiceKeepAwake",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPackageScreenCompatMode",0,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setPackageScreenCompatMode",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getPackageAskScreenCompat",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setPackageAskScreenCompat",null,getSystemChecker_PackageNameAt(0));
//        hookAllMethodsWithCache_Auto(hookClass,"clearLaunchParamsForPackages",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onSplashScreenViewCopyFinished",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onPictureInPictureUiStateChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"detachNavigationBarFromApp",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setRunningRemoteTransitionDelegate",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startBackNavigation",android.window.BackNavigationInfo);
        hookAllMethodsWithCache_Auto(hookClass,"registerScreenCaptureObserver",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterScreenCaptureObserver",null);
    }
}
