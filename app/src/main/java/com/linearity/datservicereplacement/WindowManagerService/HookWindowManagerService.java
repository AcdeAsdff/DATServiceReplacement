package com.linearity.datservicereplacement.WindowManagerService;

import static com.linearity.datservicereplacement.ActivityManagerService.HookIActivityManager.isActivityRecordSystem;
import static com.linearity.datservicereplacement.ActivityManagerService.HookIActivityManager.modifyConfiguration;
import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexAndObjectInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_BooleanAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.isPublicHookedPoolRegistered;
import static com.linearity.utils.HookUtils.listenClass;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Binder;
import android.util.Base64;
import android.util.Pair;
import android.view.WindowManager;

import com.linearity.datservicereplacement.AppOps.HookAppOpsService;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;
import com.linearity.utils.SystemAppChecker;

import java.lang.reflect.Field;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookWindowManagerService {
    public static final byte[] EMPTY_BITMAP_BYTEARRAY = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAA1JREFUGFdjeP/+/XsACVsDvYxGA5oAAAAASUVORK5CYII=", Base64.DEFAULT);
    public static final Bitmap EMPTY_BITMAP = BitmapFactory.decodeByteArray(EMPTY_BITMAP_BYTEARRAY, 0, EMPTY_BITMAP_BYTEARRAY.length);
    public static void doHook(){
        classesAndHooks.put("com.android.server.wm.WindowManagerService", HookWindowManagerService::hookIWindowManager);
        classesAndHooks.put("com.android.server.wm.Session", HookWindowManagerService::hookIWindowSession);
        classesAndHooks.put("com.android.server.wm.WindowState", HookWindowManagerService::hookWindowState);
        classesAndHooks.put("com.android.server.wm.RootWindowContainer", HookWindowManagerService::hookRootWindowContainer);
    }
    public static void hookIWindowManager(Class<?> hookClass){
        if (isPublicHookedPoolRegistered(hookClass)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"startViewServer",true);
        hookAllMethodsWithCache_Auto(hookClass,"stopViewServer",true);
        hookAllMethodsWithCache_Auto(hookClass,"isViewServerRunning",true);
//        hookAllMethodsWithCache_Auto(hookClass,"openSession",IWindowSession);
        SimpleExecutor modifyPoint = param -> {
            Pair<Integer, Point> pair = findClassIndexAndObjectInArgs(param.args,Point.class);
            if (pair.second == null){return;}
            ExtendedRandom extendedRandom = new ExtendedRandom(Binder.getCallingUid() ^ param.method.getName().hashCode());
            pair.second.x -= extendedRandom.nextInt(12);
            pair.second.y -= extendedRandom.nextInt(12);
            param.setResult(null);
        };
        hookAllMethodsWithCache_Auto(hookClass,"getInitialDisplaySize",modifyPoint);
        hookAllMethodsWithCache_Auto(hookClass,"getBaseDisplaySize",modifyPoint);
        hookAllMethodsWithCache_Auto(hookClass,"setForcedDisplaySize",null);
        hookAllMethodsWithCache_Auto(hookClass,"clearForcedDisplaySize",null);
        hookAllMethodsWithCache_Auto(hookClass,"getInitialDisplayDensity",0);
        hookAllMethodsWithCache_Auto(hookClass,"getBaseDisplayDensity",0);
        hookAllMethodsWithCache_Auto(hookClass,"getDisplayIdByUniqueId",0);
        hookAllMethodsWithCache_Auto(hookClass,"setForcedDisplayDensityForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"clearForcedDisplayDensityForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"setForcedDisplayScalingMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"setEventDispatching",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isWindowToken",true);
//        hookAllMethodsWithCache_Auto(hookClass,"addWindowToken",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeWindowToken",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setDisplayChangeWindowController",null);
////    hookAllMethodsWithCache_Auto(hookClass,"addShellRoot",SurfaceControl);
//        hookAllMethodsWithCache_Auto(hookClass,"setShellRootAccessibilityWindow",null);
//        hookAllMethodsWithCache_Auto(hookClass,"overridePendingAppTransitionMultiThumbFuture",null);
//        hookAllMethodsWithCache_Auto(hookClass,"overridePendingAppTransitionRemote",null);
//        hookAllMethodsWithCache_Auto(hookClass,"endProlongedAnimations",null);
        hookAllMethodsWithCache_Auto(hookClass,"startFreezingScreen",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopFreezingScreen",null);
//        hookAllMethodsWithCache_Auto(hookClass,"disableKeyguard",null);
//        hookAllMethodsWithCache_Auto(hookClass,"reenableKeyguard",null);
//        hookAllMethodsWithCache_Auto(hookClass,"exitKeyguardSecurely",null);
        hookAllMethodsWithCache_Auto(hookClass,"isKeyguardLocked",false);
        hookAllMethodsWithCache_Auto(hookClass,"isKeyguardSecure",false);
//        hookAllMethodsWithCache_Auto(hookClass,"dismissKeyguard",null);
//        hookAllMethodsWithCache_Auto(hookClass,"addKeyguardLockedStateListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeKeyguardLockedStateListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setSwitchingUser",null);
//        hookAllMethodsWithCache_Auto(hookClass,"closeSystemDialogs",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getAnimationScale",0.f);
//        hookAllMethodsWithCache_Auto(hookClass,"getAnimationScales",float[]);
//        hookAllMethodsWithCache_Auto(hookClass,"setAnimationScale",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setAnimationScales",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getCurrentAnimatorScale",0.f);
//        hookAllMethodsWithCache_Auto(hookClass,"setInTouchMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setInTouchModeOnAllDisplays",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isInTouchMode",true);
//        hookAllMethodsWithCache_Auto(hookClass,"showStrictModeViolation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setStrictModeVisualIndicatorPreference",null);
//        hookAllMethodsWithCache_Auto(hookClass,"refreshScreenCaptureDisabled",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getDefaultDisplayRotation",0);
//        hookAllMethodsWithCache_Auto(hookClass,"getDisplayUserRotation",0);
//        hookAllMethodsWithCache_Auto(hookClass,"watchRotation",0);
//        hookAllMethodsWithCache_Auto(hookClass,"removeRotationWatcher",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerProposedRotationListener",0);
//        hookAllMethodsWithCache_Auto(hookClass,"getPreferredOptionsPanelGravity",0);
//        hookAllMethodsWithCache_Auto(hookClass,"freezeRotation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"thawRotation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isRotationFrozen",true);
//        hookAllMethodsWithCache_Auto(hookClass,"freezeDisplayRotation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"thawDisplayRotation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isDisplayRotationFrozen",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setFixedToUserRotation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setIgnoreOrientationRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"screenshotWallpaper",EMPTY_BITMAP);

////    hookAllMethodsWithCache_Auto(hookClass,"mirrorWallpaperSurface",SurfaceControl);
        hookAllMethodsWithCache_Auto(hookClass,"registerWallpaperVisibilityListener",true);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterWallpaperVisibilityListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerSystemGestureExclusionListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSystemGestureExclusionListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestAssistScreenshot",true);
//        hookAllMethodsWithCache_Auto(hookClass,"hideTransientBars",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setRecentsVisibility",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updateStaticPrivacyIndicatorBounds",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setNavBarVirtualKeyHapticFeedbackEnabled",null);
//        hookAllMethodsWithCache_Auto(hookClass,"hasNavigationBar",true);
//        hookAllMethodsWithCache_Auto(hookClass,"lockNow",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isSafeModeEnabled",true);
//        hookAllMethodsWithCache_Auto(hookClass,"clearWindowContentFrameStats",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getWindowContentFrameStats",WindowContentFrameStats);
//        hookAllMethodsWithCache_Auto(hookClass,"getDockedStackSide",0);
//        hookAllMethodsWithCache_Auto(hookClass,"registerPinnedTaskListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestAppKeyboardShortcuts",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestImeKeyboardShortcuts",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getStableInsets",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerShortcutKey",null);
//        hookAllMethodsWithCache_Auto(hookClass,"createInputConsumer",null);
//        hookAllMethodsWithCache_Auto(hookClass,"destroyInputConsumer",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getCurrentImeTouchRegion",Region);
//        hookAllMethodsWithCache_Auto(hookClass,"registerDisplayFoldListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterDisplayFoldListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerDisplayWindowListener",EmptyArrays.EMPTY_INT_ARRAY);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterDisplayWindowListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startWindowTrace",null);
//        hookAllMethodsWithCache_Auto(hookClass,"stopWindowTrace",null);
//        hookAllMethodsWithCache_Auto(hookClass,"saveWindowTraceToFile",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isWindowTraceEnabled",true);
//        hookAllMethodsWithCache_Auto(hookClass,"startTransitionTrace",null);
//        hookAllMethodsWithCache_Auto(hookClass,"stopTransitionTrace",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isTransitionTraceEnabled",true);
//        hookAllMethodsWithCache_Auto(hookClass,"getWindowingMode",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setWindowingMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getRemoveContentMode",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setRemoveContentMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"shouldShowWithInsecureKeyguard",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setShouldShowWithInsecureKeyguard",null);
//        hookAllMethodsWithCache_Auto(hookClass,"shouldShowSystemDecors",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setShouldShowSystemDecors",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getDisplayImePolicy",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setDisplayImePolicy",null);
//        hookAllMethodsWithCache_Auto(hookClass,"syncInputTransactions",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isLayerTracing",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setLayerTracing",null);
//        hookAllMethodsWithCache_Auto(hookClass,"mirrorDisplay",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setDisplayWindowInsetsController",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updateDisplayWindowRequestedVisibleTypes",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getWindowInsets",true);
//        hookAllMethodsWithCache_Auto(hookClass,"getPossibleDisplayInfo",EMPTY_ARRAYLIST);
//        hookAllMethodsWithCache_Auto(hookClass,"showGlobalActions",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setLayerTracingFlags",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setActiveTransactionTracing",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestScrollCapture",null);
//        hookAllMethodsWithCache_Auto(hookClass,"holdLock",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getSupportedDisplayHashAlgorithms",EmptyArrays.EMPTY_STRING_ARRAY);
////    hookAllMethodsWithCache_Auto(hookClass,"verifyDisplayHash",VerifiedDisplayHash);
//        hookAllMethodsWithCache_Auto(hookClass,"setDisplayHashThrottlingEnabled",null);
////    hookAllMethodsWithCache_Auto(hookClass,"attachWindowContextToDisplayArea",WindowContextInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"attachWindowContextToWindowToken",WindowContextInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"attachWindowContextToDisplayContent",WindowContextInfo);
//        hookAllMethodsWithCache_Auto(hookClass,"detachWindowContext",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerCrossWindowBlurEnabledListener",true);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterCrossWindowBlurEnabledListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isTaskSnapshotSupported",true);
//        hookAllMethodsWithCache_Auto(hookClass,"getImeDisplayId",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setTaskSnapshotEnabled",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerTaskFpsCallback",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterTaskFpsCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"snapshotTaskForRecents",EMPTY_BITMAP);
//        hookAllMethodsWithCache_Auto(hookClass,"setRecentsAppBehindSystemBars",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getLetterboxBackgroundColorInArgb",0);
//        hookAllMethodsWithCache_Auto(hookClass,"isLetterboxBackgroundMultiColored",true);
//        hookAllMethodsWithCache_Auto(hookClass,"captureDisplay",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isGlobalKey",true);
//        hookAllMethodsWithCache_Auto(hookClass,"addToSurfaceSyncGroup",true);
//        hookAllMethodsWithCache_Auto(hookClass,"markSurfaceSyncGroupReady",null);
//        hookAllMethodsWithCache_Auto(hookClass,"notifyScreenshotListeners",EMPTY_ARRAYLIST);
//        hookAllMethodsWithCache_Auto(hookClass,"replaceContentOnDisplay",true);
//        hookAllMethodsWithCache_Auto(hookClass,"registerDecorViewGestureListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterDecorViewGestureListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerTrustedPresentationListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterTrustedPresentationListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerScreenRecordingCallback",true);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterScreenRecordingCallback",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setGlobalDragListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"transferTouchGesture",true);
    }

    public static void hookIWindow(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"executeCommand",null);
//        hookAllMethodsWithCache_Auto(hookClass,"resized",null);
//        hookAllMethodsWithCache_Auto(hookClass,"insetsControlChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"showInsets",null);
//        hookAllMethodsWithCache_Auto(hookClass,"hideInsets",null);
//        hookAllMethodsWithCache_Auto(hookClass,"moved",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dispatchAppVisibility",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dispatchGetNewSurface",null);
//        hookAllMethodsWithCache_Auto(hookClass,"closeSystemDialogs",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dispatchWallpaperOffsets",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dispatchWallpaperCommand",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dispatchDragEvent",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dispatchWindowShown",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestAppKeyboardShortcuts",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestScrollCapture",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dumpWindow",null);
    }

    public static void hookIWindowSession(Class<?> hookClass){
//        listenClass(hookClass);
        if (isPublicHookedPoolRegistered(hookClass)){return;}
        SimpleExecutor executeAdd2Display = param -> {
            Exception stackShower = new Exception("addToDisplay:" + (Arrays.deepToString(param.args)));
//            LoggerLog();
            if (stackShower.toString().contains("APPLICATION_OVERLAY")){
                param.setResult(0);
            }
        };
        hookAllMethodsWithCache_Auto(hookClass,"addToDisplay",executeAdd2Display);
        hookAllMethodsWithCache_Auto(hookClass,"addToDisplayAsUser",executeAdd2Display);
        hookAllMethodsWithCache_Auto(hookClass,"addToDisplayWithoutInputChannel",executeAdd2Display);
//        hookAllMethodsWithCache_Auto(hookClass,"remove",null);
//        hookAllMethodsWithCache_Auto(hookClass,"relayoutLegacy",showAfter);
//        hookAllMethodsWithCache_Auto(hookClass,"relayout",showAfter);
//        hookAllMethodsWithCache_Auto(hookClass,"relayoutAsync",showAfter);
        hookAllMethodsWithCache_Auto(hookClass,"outOfMemory",false);
//        hookAllMethodsWithCache_Auto(hookClass,"setInsets",null);
//        hookAllMethodsWithCache_Auto(hookClass,"finishDrawing",null);
//        hookAllMethodsWithCache_Auto(hookClass,"performHapticFeedback",true);
//        hookAllMethodsWithCache_Auto(hookClass,"performHapticFeedbackAsync",null);
////    hookAllMethodsWithCache_Auto(hookClass,"performDrag",IBinder);
//        hookAllMethodsWithCache_Auto(hookClass,"dropForAccessibility",true);
//        hookAllMethodsWithCache_Auto(hookClass,"reportDropResult",null);
//        hookAllMethodsWithCache_Auto(hookClass,"cancelDragAndDrop",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dragRecipientEntered",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dragRecipientExited",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setWallpaperPosition",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setWallpaperZoomOut",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setShouldZoomOutWallpaper",null);
//        hookAllMethodsWithCache_Auto(hookClass,"wallpaperOffsetsComplete",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setWallpaperDisplayOffset",null);
//        hookAllMethodsWithCache_Auto(hookClass,"sendWallpaperCommand",null);
//        hookAllMethodsWithCache_Auto(hookClass,"wallpaperCommandComplete",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onRectangleOnScreenRequested",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getWindowId",IWindowId);
//        hookAllMethodsWithCache_Auto(hookClass,"pokeDrawLock",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startMovingTask",true);
//        hookAllMethodsWithCache_Auto(hookClass,"finishMovingTask",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updateTapExcludeRegion",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updateRequestedVisibleTypes",null);
//        hookAllMethodsWithCache_Auto(hookClass,"reportSystemGestureExclusionChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"reportDecorViewGestureInterceptionChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"reportKeepClearAreasChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"grantInputChannel",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updateInputChannel",null);
//        hookAllMethodsWithCache_Auto(hookClass,"grantEmbeddedWindowFocus",null);
//        hookAllMethodsWithCache_Auto(hookClass,"generateDisplayHash",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setOnBackInvokedCallbackInfo",null);
//        hookAllMethodsWithCache_Auto(hookClass,"clearTouchableRegion",null);
//        hookAllMethodsWithCache_Auto(hookClass,"cancelDraw",true);
//        hookAllMethodsWithCache_Auto(hookClass,"moveFocusToAdjacentWindow",true);
    }
    public static void hookPhoneWindow(Class<?> hookClass){

        hookAllMethodsWithCache_Auto(hookClass,"setAttributes",showBefore);

    }

    public static void hookRootWindowContainer(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"handleNotObscuredLocked",(SimpleExecutor)param -> {
            Object /*WindowState*/ windowState = param.args[0];
            WindowManager.LayoutParams mAttrs = (WindowManager.LayoutParams) XposedHelpers.getObjectField(windowState,"mAttrs");
            mAttrs.screenBrightness = Float.NaN;
        },getSystemChecker_BooleanAt(-1));//傻逼了吧(对微信)
    }

    private static Field WindowState_mActivityRecord = null;
    private static Field WindowState_mOwnerUid = null;
    public static final SystemAppChecker windowStateSysAppChecker = param -> isWindowStateSystem(param.thisObject);
    public static boolean isWindowStateSystem(Object windowState){
        if (windowState == null){return true;}
        if (WindowState_mActivityRecord == null){
            WindowState_mActivityRecord = XposedHelpers.findField(windowState.getClass(),"mActivityRecord");
        }
        if (WindowState_mOwnerUid == null){
            WindowState_mOwnerUid = XposedHelpers.findField(windowState.getClass(),"mOwnerUid");
        }
        try {
            Object activityRecord = WindowState_mActivityRecord.get(windowState);
            if (activityRecord != null && !isActivityRecordSystem(activityRecord)){
                return false;
            }
            int mOwnerUid = (int) WindowState_mOwnerUid.get(windowState);
            return isSystemApp(mOwnerUid);
        }catch (Exception e){
            LoggerLog(e);
            return true;
        }
    }

    public static final SimpleExecutor modifyConfigurationResult = param -> {
        Configuration configuration = modifyConfiguration((Configuration) param.getResult());
        if (configuration != null){
            param.setResult(configuration);
        }
    };
    public static void hookWindowState(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getProcessGlobalConfiguration",new SimpleExecutorWithMode(MODE_AFTER,modifyConfigurationResult));
        hookAllMethodsWithCache_Auto(hookClass,"getLastReportedConfiguration",new SimpleExecutorWithMode(MODE_AFTER,modifyConfigurationResult));
        hookAllMethodsWithCache_Auto(hookClass,"getConfiguration",new SimpleExecutorWithMode(MODE_AFTER,modifyConfigurationResult));

    }

}
