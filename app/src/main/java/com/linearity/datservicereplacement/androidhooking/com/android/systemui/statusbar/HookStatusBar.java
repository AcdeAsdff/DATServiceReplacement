package com.linearity.datservicereplacement.androidhooking.com.android.systemui.statusbar;

import static android.app.StatusBarManager.NAV_BAR_MODE_DEFAULT;
import static com.linearity.datservicereplacement.ReturnIfNonSys.CONSTRUCTOR_METHOD_STRING;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findArgByClassInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.HookUtils.listenClass;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.view.View;

import com.linearity.datservicereplacement.androidhooking.com.android.server.wm.HookWindowManagerService;
import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;

import de.robv.android.xposed.XposedHelpers;

@NotFinished
public class HookStatusBar {
    public static void doHook(){
        classesAndHooks.put("com.android.server.statusbar.StatusBarManagerService", HookStatusBar::hookIStatusBarService);
        classesAndHooks.put("com.android.systemui.statusbar.CommandQueue",HookStatusBar::hookIStatusBar);
        classesAndHooks.put("com.android.server.notification.NotificationManagerService$StatusBarNotificationHolder",HookStatusBar::hookIStatusBarNotificationHolder);
//        classesAndHooks.put("com.android.systemui.statusbar.StatusBarStateControllerImpl", HookStatusBar::hookStatusBarStateController);
//        classesAndHooks.put("android.app.StatusBarManager$UndoCallback",HookStatusBar::hookIUndoMediaTransferCallback);
        classesAndHooks.put("com.android.systemui.statusbar.window.StatusBarWindowController",HookStatusBar::hookStatusBarWindowController);


    }
    public static void hookStatusBarStateController(Class<?> hookClass){
//        Set<String> toAvoid = new HashSet<>();
//        toAvoid.add("setState");
//        listenClass(hookClass,toAvoid);
    }
    public static void hookStatusBarWindowController(Class<?> hookClass){
        SimpleExecutor paramArg0False= param -> param.args[0]=false;
        hookAllMethodsWithCache_Auto(hookClass,"setForceStatusBarVisible",paramArg0False,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"setOngoingProcessRequiresStatusBarVisible",paramArg0False,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"setLaunchAnimationRunning",paramArg0False,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"getBackgroundView",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            View v = (View) param.getResult();
            if (v != null){
                v.setVisibility(View.INVISIBLE);
            }
        }),noSystemChecker);
        SimpleExecutor modifyStateParamArg0 = param -> {
            Object state = param.args[0];
            XposedHelpers.setBooleanField(state,"mForceStatusBarVisible",false);
            XposedHelpers.setBooleanField(state,"mOngoingProcessRequiresStatusBarVisible",false);
        };
        hookAllMethodsWithCache_Auto(hookClass,"applyForceStatusBarVisibleFlag",modifyStateParamArg0,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"apply",modifyStateParamArg0,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"applyHeight",modifyStateParamArg0,noSystemChecker);
        SimpleExecutor invisibleViewInArgs = param -> {
            for (Object o:param.args){
                if (o instanceof View v){
                    v.setAlpha(0f);//this works
                }
            }
        };
        hookAllMethodsWithCache_Auto(hookClass,"addViewToWindow",invisibleViewInArgs,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,CONSTRUCTOR_METHOD_STRING,invisibleViewInArgs,noSystemChecker);

//        LoggerLog("listening class:" + hookClass);
//        listenClass(hookClass);
//        Class<?> statusBarWindowViewClass = XposedHelpers.findClassIfExists("com.android.systemui.statusbar.window.StatusBarWindowView",hookClass.getClassLoader());
//        if (statusBarWindowViewClass != null){
//            LoggerLog("listening class:" + statusBarWindowViewClass);
//            listenClass(statusBarWindowViewClass);
//        }
//        Class<?> statusBarWindowStateControllerClass = XposedHelpers.findClassIfExists("com.android.systemui.statusbar.window.StatusBarWindowStateController",hookClass.getClassLoader());
//        if (statusBarWindowStateControllerClass != null){
//            LoggerLog("listening class:" + statusBarWindowStateControllerClass);
//            listenClass(statusBarWindowStateControllerClass);
//        }
    }
    public static void hookIStatusBarNotificationHolder(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"get",null);//StatusBarNotification
    }
    public static void hookIStatusBarService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"expandNotificationsPanel",null);
        hookAllMethodsWithCache_Auto(hookClass,"collapsePanels",null);
        hookAllMethodsWithCache_Auto(hookClass,"togglePanel",null);
        hookAllMethodsWithCache_Auto(hookClass,"disable",null);
        hookAllMethodsWithCache_Auto(hookClass,"disableForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"disable2",null);
        hookAllMethodsWithCache_Auto(hookClass,"disable2ForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"getDisableFlags", EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"setIcon",null);
        hookAllMethodsWithCache_Auto(hookClass,"setIconVisibility",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeIcon",null);
        hookAllMethodsWithCache_Auto(hookClass,"setImeWindowStatus",null);
        hookAllMethodsWithCache_Auto(hookClass,"expandSettingsPanel",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerStatusBar",null);//RegisterStatusBarResult
        hookAllMethodsWithCache_Auto(hookClass,"onPanelRevealed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPanelHidden",null);
        hookAllMethodsWithCache_Auto(hookClass,"clearNotificationEffects",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationClick",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationActionClick",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationError",null);
        hookAllMethodsWithCache_Auto(hookClass,"onClearAllNotifications",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationClear",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationVisibilityChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationExpansionChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationDirectReplied",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationSmartSuggestionsAdded",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationSmartReplySent",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationSettingsViewed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationBubbleChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBubbleMetadataFlagChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"hideCurrentInputMethodForBubbles",null);
        hookAllMethodsWithCache_Auto(hookClass,"grantInlineReplyUriPermission",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"clearInlineReplyUriPermissions",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationFeedbackReceived",null);
        hookAllMethodsWithCache_Auto(hookClass,"onGlobalActionsShown",null);
        hookAllMethodsWithCache_Auto(hookClass,"onGlobalActionsHidden",null);
        hookAllMethodsWithCache_Auto(hookClass,"shutdown",null);
        hookAllMethodsWithCache_Auto(hookClass,"reboot",null);
        hookAllMethodsWithCache_Auto(hookClass,"restart",null);
        hookAllMethodsWithCache_Auto(hookClass,"addTile",null);
        hookAllMethodsWithCache_Auto(hookClass,"remTile",null);
        hookAllMethodsWithCache_Auto(hookClass,"clickTile",null);
        hookAllMethodsWithCache_Auto(hookClass,"handleSystemKey",null);
        hookAllMethodsWithCache_Auto(hookClass,"getLastSystemKey",0);
        hookAllMethodsWithCache_Auto(hookClass,"showPinningEnterExitToast",null);
        hookAllMethodsWithCache_Auto(hookClass,"showPinningEscapeToast",null);
        hookAllMethodsWithCache_Auto(hookClass,"showAuthenticationDialog",null,getSystemChecker_PackageNameAt(7));
        hookAllMethodsWithCache_Auto(hookClass,"onBiometricAuthenticated",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBiometricHelp",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBiometricError",null);
        hookAllMethodsWithCache_Auto(hookClass,"hideAuthenticationDialog",null);
        hookAllMethodsWithCache_Auto(hookClass,"setBiometicContextListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"setUdfpsRefreshRateCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"showInattentiveSleepWarning",null);
        hookAllMethodsWithCache_Auto(hookClass,"dismissInattentiveSleepWarning",null);
        hookAllMethodsWithCache_Auto(hookClass,"startTracing",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopTracing",null);
        hookAllMethodsWithCache_Auto(hookClass,"isTracing",true);
        hookAllMethodsWithCache_Auto(hookClass,"suppressAmbientDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestTileServiceListeningState",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestAddTile",null);
        hookAllMethodsWithCache_Auto(hookClass,"cancelRequestAddTile",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setNavBarMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"getNavBarMode",NAV_BAR_MODE_DEFAULT);
        hookAllMethodsWithCache_Auto(hookClass,"registerSessionListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSessionListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSessionStarted",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSessionEnded",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateMediaTapToTransferSenderDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateMediaTapToTransferReceiverDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerNearbyMediaDevicesProvider",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterNearbyMediaDevicesProvider",null);
        hookAllMethodsWithCache_Auto(hookClass,"showRearDisplayDialog",null);
    }
    public static void hookIStatusBar(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"setIcon",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeIcon",null);
        hookAllMethodsWithCache_Auto(hookClass,"disable",null);
        hookAllMethodsWithCache_Auto(hookClass,"animateExpandNotificationsPanel",null);
        hookAllMethodsWithCache_Auto(hookClass,"animateExpandSettingsPanel",null);
        hookAllMethodsWithCache_Auto(hookClass,"animateCollapsePanels",null);
        hookAllMethodsWithCache_Auto(hookClass,"toggleNotificationsPanel",null);
        hookAllMethodsWithCache_Auto(hookClass,"showWirelessChargingAnimation",null);
        hookAllMethodsWithCache_Auto(hookClass,"setImeWindowStatus",null);
        hookAllMethodsWithCache_Auto(hookClass,"setWindowState",null);
        hookAllMethodsWithCache_Auto(hookClass,"showRecentApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"hideRecentApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"toggleRecentApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"toggleTaskbar",null);
        hookAllMethodsWithCache_Auto(hookClass,"toggleSplitScreen",null);
        hookAllMethodsWithCache_Auto(hookClass,"preloadRecentApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"cancelPreloadRecentApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"showScreenPinningRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"confirmImmersivePrompt",null);
        hookAllMethodsWithCache_Auto(hookClass,"immersiveModeChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"dismissKeyboardShortcutsMenu",null);
        hookAllMethodsWithCache_Auto(hookClass,"toggleKeyboardShortcutsMenu",null);
        hookAllMethodsWithCache_Auto(hookClass,"appTransitionPending",null);
        hookAllMethodsWithCache_Auto(hookClass,"appTransitionCancelled",null);
        hookAllMethodsWithCache_Auto(hookClass,"appTransitionStarting",null);
        hookAllMethodsWithCache_Auto(hookClass,"appTransitionFinished",null);
        hookAllMethodsWithCache_Auto(hookClass,"showAssistDisclosure",null);
        hookAllMethodsWithCache_Auto(hookClass,"startAssist",null);
        hookAllMethodsWithCache_Auto(hookClass,"onCameraLaunchGestureDetected",null);
        hookAllMethodsWithCache_Auto(hookClass,"onEmergencyActionLaunchGestureDetected",null);
        hookAllMethodsWithCache_Auto(hookClass,"showPictureInPictureMenu",null);
        hookAllMethodsWithCache_Auto(hookClass,"showGlobalActionsMenu",null);
        hookAllMethodsWithCache_Auto(hookClass,"onProposedRotationChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"setTopAppHidesStatusBar",null);
        hookAllMethodsWithCache_Auto(hookClass,"addQsTile",null);
        hookAllMethodsWithCache_Auto(hookClass,"addQsTileToFrontOrEnd",null);
        hookAllMethodsWithCache_Auto(hookClass,"remQsTile",null);
        hookAllMethodsWithCache_Auto(hookClass,"setQsTiles",null);
        hookAllMethodsWithCache_Auto(hookClass,"clickQsTile",null);
        hookAllMethodsWithCache_Auto(hookClass,"handleSystemKey",null);
        hookAllMethodsWithCache_Auto(hookClass,"showPinningEnterExitToast",null);
        hookAllMethodsWithCache_Auto(hookClass,"showPinningEscapeToast",null);
        hookAllMethodsWithCache_Auto(hookClass,"showShutdownUi",null);
        hookAllMethodsWithCache_Auto(hookClass,"showAuthenticationDialog",null,getSystemChecker_PackageNameAt(7));
        hookAllMethodsWithCache_Auto(hookClass,"onBiometricAuthenticated",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBiometricHelp",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBiometricError",null);
        hookAllMethodsWithCache_Auto(hookClass,"hideAuthenticationDialog",null);
        hookAllMethodsWithCache_Auto(hookClass,"setBiometicContextListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"setUdfpsRefreshRateCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDisplayReady",null);
        hookAllMethodsWithCache_Auto(hookClass,"onRecentsAnimationStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSystemBarAttributesChanged",null,getSystemChecker_PackageNameAt(6));
        hookAllMethodsWithCache_Auto(hookClass,"showTransient",null);
        hookAllMethodsWithCache_Auto(hookClass,"abortTransient",null);
        hookAllMethodsWithCache_Auto(hookClass,"showInattentiveSleepWarning",null);
        hookAllMethodsWithCache_Auto(hookClass,"dismissInattentiveSleepWarning",null);
        hookAllMethodsWithCache_Auto(hookClass,"showToast",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"hideToast",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"startTracing",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopTracing",null);
        hookAllMethodsWithCache_Auto(hookClass,"suppressAmbientDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestMagnificationConnection",null);
        hookAllMethodsWithCache_Auto(hookClass,"passThroughShellCommand",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNavigationBarLumaSamplingEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"runGcForTest",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestTileServiceListeningState",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestAddTile",null);
        hookAllMethodsWithCache_Auto(hookClass,"cancelRequestAddTile",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"updateMediaTapToTransferSenderDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateMediaTapToTransferReceiverDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerNearbyMediaDevicesProvider",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterNearbyMediaDevicesProvider",null);
        hookAllMethodsWithCache_Auto(hookClass,"dumpProto",null);
        hookAllMethodsWithCache_Auto(hookClass,"showRearDisplayDialog",null);
        hookAllMethodsWithCache_Auto(hookClass,"moveFocusedTaskToFullscreen",null);
        hookAllMethodsWithCache_Auto(hookClass,"moveFocusedTaskToStageSplit",null);
        hookAllMethodsWithCache_Auto(hookClass,"setSplitscreenFocus",null);
        hookAllMethodsWithCache_Auto(hookClass,"showMediaOutputSwitcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"moveFocusedTaskToDesktop",null);
    }
    public static void hookIUndoMediaTransferCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onUndoTriggered",null);
    }

}
