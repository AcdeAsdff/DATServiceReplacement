package com.linearity.datservicereplacement.Accessibility;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.ParceledListSliceGen;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookAccessibility {

    public static void doHook(){
        classesAndHooks.put("com.android.server.accessibility.AccessibilityManagerService", HookAccessibility::hookIAccessibilityManager);

    }

    public static void hookIAccessibilityManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"interrupt",null);
        hookAllMethodsWithCache_Auto(hookClass,"sendAccessibilityEvent",null);
        hookAllMethodsWithCache_Auto(hookClass,"addClient",0L);
        hookAllMethodsWithCache_Auto(hookClass,"removeClient",true);
        hookAllMethodsWithCache_Auto(hookClass,"getInstalledAccessibilityServiceList",ParceledListSliceGen);//ParceledListSlice<AccessibilityServiceInfo>
        hookAllMethodsWithCache_Auto(hookClass,"getEnabledAccessibilityServiceList",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"addAccessibilityInteractionConnection",0,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"removeAccessibilityInteractionConnection",null);
        hookAllMethodsWithCache_Auto(hookClass,"setPictureInPictureActionReplacingConnection",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerUiTestAutomationService",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterUiTestAutomationService",null);
        hookAllMethodsWithCache_Auto(hookClass,"getWindowToken",null);//IBinder
        hookAllMethodsWithCache_Auto(hookClass,"notifyAccessibilityButtonClicked",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyAccessibilityButtonVisibilityChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"performAccessibilityShortcut",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAccessibilityShortcutTargets",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"sendFingerprintGesture",true);
        hookAllMethodsWithCache_Auto(hookClass,"getAccessibilityWindowId",0);
        hookAllMethodsWithCache_Auto(hookClass,"getRecommendedTimeoutMillis",0L);
        hookAllMethodsWithCache_Auto(hookClass,"registerSystemAction",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSystemAction",null);
        hookAllMethodsWithCache_Auto(hookClass,"setMagnificationConnection",null);
        hookAllMethodsWithCache_Auto(hookClass,"associateEmbeddedHierarchy",null);
        hookAllMethodsWithCache_Auto(hookClass,"disassociateEmbeddedHierarchy",null);
        hookAllMethodsWithCache_Auto(hookClass,"getFocusStrokeWidth",0);
        hookAllMethodsWithCache_Auto(hookClass,"getFocusColor",0);
        hookAllMethodsWithCache_Auto(hookClass,"isAudioDescriptionByDefaultEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSystemAudioCaptioningEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isSystemAudioCaptioningUiEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSystemAudioCaptioningUiEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAccessibilityWindowAttributes",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerProxyForDisplay",true);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterProxyForDisplay",true);
        hookAllMethodsWithCache_Auto(hookClass,"injectInputEventToInputFilter",null);
        hookAllMethodsWithCache_Auto(hookClass,"startFlashNotificationSequence",true);
        hookAllMethodsWithCache_Auto(hookClass,"stopFlashNotificationSequence",true);
        hookAllMethodsWithCache_Auto(hookClass,"startFlashNotificationEvent",true);
        hookAllMethodsWithCache_Auto(hookClass,"isAccessibilityTargetAllowed",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"sendRestrictedDialogIntent",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isAccessibilityServiceWarningRequired",true);
        hookAllMethodsWithCache_Auto(hookClass,"getWindowTransformationSpec",null);//WindowTransformationSpec
        hookAllMethodsWithCache_Auto(hookClass,"attachAccessibilityOverlayToDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyQuickSettingsTilesChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableShortcutsForTargets",null);
        hookAllMethodsWithCache_Auto(hookClass,"getA11yFeatureToTileMap",null);//Bundle
    }

}
