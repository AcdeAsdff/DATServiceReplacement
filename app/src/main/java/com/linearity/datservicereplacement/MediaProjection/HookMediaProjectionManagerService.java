package com.linearity.datservicereplacement.MediaProjection;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import com.linearity.datservicereplacement.DeviceLock.HookDeviceLock;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMediaProjectionManagerService {

    public static void doHook(){
        classesAndHooks.put("com.android.server.media.projection.MediaProjectionManagerService$BinderService", HookMediaProjectionManagerService::hookIMediaProjectionManager);
    }

    public static void hookIMediaProjectionManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"hasProjectionPermission",true);
        hookAllMethodsWithCache_Auto(hookClass,"createProjection",null);//IMediaProjection
        hookAllMethodsWithCache_Auto(hookClass,"getProjection",null);//IMediaProjection
        hookAllMethodsWithCache_Auto(hookClass,"isCurrentProjection",true);
        hookAllMethodsWithCache_Auto(hookClass,"requestConsentForInvalidProjection",null);
        hookAllMethodsWithCache_Auto(hookClass,"getActiveProjectionInfo",null);//MediaProjectionInfo
//        hookAllMethodsWithCache_Auto(hookClass,"stopActiveProjection",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyActiveProjectionCapturedContentResized",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyActiveProjectionCapturedContentVisibilityChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"addCallback",null);//MediaProjectionInfo
//        hookAllMethodsWithCache_Auto(hookClass,"removeCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"setContentRecordingSession",true);
        hookAllMethodsWithCache_Auto(hookClass,"setUserReviewGrantedConsentResult",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyPermissionRequestInitiated",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyPermissionRequestDisplayed",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyPermissionRequestCancelled",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyAppSelectorDisplayed",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyWindowingModeChanged",null);
    }
//    public static void hookMediaProjectionManagerService(Class<?> hookClass){
//
//    }

}
