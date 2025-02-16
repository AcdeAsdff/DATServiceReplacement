package com.linearity.datservicereplacement.Display;

import static com.linearity.datservicereplacement.Display.DisplayInfoConstructor.modifyDisplayInfoAfterCalled;
import static com.linearity.datservicereplacement.Display.DisplayInfoConstructor.modifyDisplayModeAfterCalled;
import static com.linearity.datservicereplacement.Display.DisplayInfoConstructor.modifyPointAfterCalled;
import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.getPackageName;
import static com.linearity.datservicereplacement.ReturnIfNonSys.ParceledListSliceGen;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.isPublicHookedPoolRegistered;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.os.Binder;

import com.linearity.datservicereplacement.Battery.HookIBattery;
import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;

import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookDisplay {

    public static void doHook(){
        classesAndHooks.put("com.android.server.display.DisplayManagerService$BinderService", HookDisplay::hookIDisplayManager);
    }
    public static void hookIDisplayManager(Class<?> hookClass){
        if (isPublicHookedPoolRegistered(hookClass)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"getDisplayInfo",modifyDisplayInfoAfterCalled);//DisplayInfo
        hookAllMethodsWithCache_Auto(hookClass,"getDisplayIds",new int[]{0});
        hookAllMethodsWithCache_Auto(hookClass,"isUidPresentOnDisplay",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerCallbackWithEventMask",null);
        hookAllMethodsWithCache_Auto(hookClass,"startWifiDisplayScan",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopWifiDisplayScan",null);
        hookAllMethodsWithCache_Auto(hookClass,"connectWifiDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"disconnectWifiDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"renameWifiDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"forgetWifiDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"pauseWifiDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"resumeWifiDisplay",null);
        //TODO:Randomize it👇.(construct WifiDisplayStatus)
        hookAllMethodsWithCache_Auto(hookClass,"getWifiDisplayStatus",null);//WifiDisplayStatus
        hookAllMethodsWithCache_Auto(hookClass,"setUserDisabledHdrTypes",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAreUserDisabledHdrTypesAllowed",null);
        hookAllMethodsWithCache_Auto(hookClass,"areUserDisabledHdrTypesAllowed",true);
        hookAllMethodsWithCache_Auto(hookClass,"getUserDisabledHdrTypes",showAfter);
        hookAllMethodsWithCache_Auto(hookClass,"overrideHdrTypes",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestColorMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"createVirtualDisplay",0,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"resizeVirtualDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"setVirtualDisplaySurface",null);
        hookAllMethodsWithCache_Auto(hookClass,"releaseVirtualDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"setVirtualDisplayState",null);
        hookAllMethodsWithCache_Auto(hookClass,"getStableDisplaySize",modifyPointAfterCalled);
        hookAllMethodsWithCache_Auto(hookClass,"getBrightnessEvents",ParceledListSliceGen,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getAmbientBrightnessStats",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"setBrightnessConfigurationForUser",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"setBrightnessConfigurationForDisplay",null,getSystemChecker_PackageNameAt(3));
        //TODO:Randomize it👇.(construct BrightnessConfiguration)
        hookAllMethodsWithCache_Auto(hookClass,"getBrightnessConfigurationForDisplay",null);//BrightnessConfiguration
        hookAllMethodsWithCache_Auto(hookClass,"getBrightnessConfigurationForUser",null);//BrightnessConfiguration
        hookAllMethodsWithCache_Auto(hookClass,"getDefaultBrightnessConfiguration",null);//BrightnessConfiguration

        hookAllMethodsWithCache_Auto(hookClass,"isMinimalPostProcessingRequested",true);
        hookAllMethodsWithCache_Auto(hookClass,"setTemporaryBrightness",null);
        hookAllMethodsWithCache_Auto(hookClass,"setBrightness",null);
        hookAllMethodsWithCache_Auto(hookClass,"getBrightness",0.f);
        hookAllMethodsWithCache_Auto(hookClass,"setTemporaryAutoBrightnessAdjustment",null);
        //TODO:Randomize it👇.(construct Curve)
        hookAllMethodsWithCache_Auto(hookClass,"getMinimumBrightnessCurve",null);//Curve
        //TODO:Randomize it👇.(construct BrightnessInfo)
        hookAllMethodsWithCache_Auto(hookClass,"getBrightnessInfo",null);//BrightnessInfo
        hookAllMethodsWithCache_Auto(hookClass,"getPreferredWideGamutColorSpaceId",0);
        hookAllMethodsWithCache_Auto(hookClass,"setUserPreferredDisplayMode",null);

        hookAllMethodsWithCache_Auto(hookClass,"getUserPreferredDisplayMode",modifyDisplayModeAfterCalled);
        hookAllMethodsWithCache_Auto(hookClass,"getSystemPreferredDisplayMode",modifyDisplayModeAfterCalled);
        hookAllMethodsWithCache_Auto(hookClass,"setHdrConversionMode",null);
        //TODO:Randomize it👇.(construct HdrConversionMode)
//        hookAllMethodsWithCache_Auto(hookClass,"getHdrConversionModeSetting",HdrConversionMode);
//        hookAllMethodsWithCache_Auto(hookClass,"getHdrConversionMode",HdrConversionMode);
        hookAllMethodsWithCache_Auto(hookClass,"getSupportedHdrOutputTypes", EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"setShouldAlwaysRespectAppRequestedMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"shouldAlwaysRespectAppRequestedMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"setRefreshRateSwitchingType",null);
        hookAllMethodsWithCache_Auto(hookClass,"getRefreshRateSwitchingType",0);
        //TODO:Randomize it👇.(construct DisplayDecorationSupport)
//        hookAllMethodsWithCache_Auto(hookClass,"getDisplayDecorationSupport",DisplayDecorationSupport);
        hookAllMethodsWithCache_Auto(hookClass,"setDisplayIdToMirror",null);

        //TODO:Randomize it(if needed)👇.(construct OverlayProperties)
        //if null,causes crash when launching app.
        // there seems no important messages.
//        hookAllMethodsWithCache_Auto(hookClass,"getOverlaySupport", null));//OverlayProperties

        hookAllMethodsWithCache_Auto(hookClass,"enableConnectedDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"disableConnectedDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestDisplayPower",true);
        hookAllMethodsWithCache_Auto(hookClass,"requestDisplayModes",null);
    }

}
