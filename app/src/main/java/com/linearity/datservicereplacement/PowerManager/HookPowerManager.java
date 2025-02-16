package com.linearity.datservicereplacement.PowerManager;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookPowerManager {

    public static void doHook(){
        classesAndHooks.put("com.android.server.power.PowerManagerService$BinderService", HookPowerManager::hookIPowerManager);
    }

    public static void hookIPowerManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"acquireWakeLock",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"acquireWakeLockWithUid",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"releaseWakeLock",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateWakeLockUids",null);
        hookAllMethodsWithCache_Auto(hookClass,"setPowerBoost",null);
        hookAllMethodsWithCache_Auto(hookClass,"setPowerMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"setPowerModeChecked",true);
        hookAllMethodsWithCache_Auto(hookClass,"updateWakeLockWorkSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateWakeLockCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"isWakeLockLevelSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"userActivity",null);
        hookAllMethodsWithCache_Auto(hookClass,"wakeUp",null);
        hookAllMethodsWithCache_Auto(hookClass,"goToSleep",null);
        hookAllMethodsWithCache_Auto(hookClass,"goToSleepWithDisplayId",null);
        hookAllMethodsWithCache_Auto(hookClass,"nap",null);
        hookAllMethodsWithCache_Auto(hookClass,"getBrightnessConstraint",0.f);
        hookAllMethodsWithCache_Auto(hookClass,"isInteractive",true);
        hookAllMethodsWithCache_Auto(hookClass,"isDisplayInteractive",true);
        hookAllMethodsWithCache_Auto(hookClass,"areAutoPowerSaveModesEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isPowerSaveMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"getPowerSaveState",null);//PowerSaveState
        hookAllMethodsWithCache_Auto(hookClass,"setPowerSaveModeEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isBatterySaverSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"getFullPowerSavePolicy",null);//BatterySaverPolicyConfig
        hookAllMethodsWithCache_Auto(hookClass,"setFullPowerSavePolicy",true);
        hookAllMethodsWithCache_Auto(hookClass,"setDynamicPowerSaveHint",true);
        hookAllMethodsWithCache_Auto(hookClass,"setAdaptivePowerSavePolicy",true);
        hookAllMethodsWithCache_Auto(hookClass,"setAdaptivePowerSaveEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"getPowerSaveModeTrigger",0);
        hookAllMethodsWithCache_Auto(hookClass,"setBatteryDischargePrediction",null);
        hookAllMethodsWithCache_Auto(hookClass,"getBatteryDischargePrediction",null);//ParcelDuration
        hookAllMethodsWithCache_Auto(hookClass,"isBatteryDischargePredictionPersonalized",true);
        hookAllMethodsWithCache_Auto(hookClass,"isDeviceIdleMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"isLightDeviceIdleMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"isLowPowerStandbySupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"isLowPowerStandbyEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setLowPowerStandbyEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"setLowPowerStandbyActiveDuringMaintenance",null);
        hookAllMethodsWithCache_Auto(hookClass,"forceLowPowerStandbyActive",null);
        hookAllMethodsWithCache_Auto(hookClass,"setLowPowerStandbyPolicy",null);
        hookAllMethodsWithCache_Auto(hookClass,"getLowPowerStandbyPolicy",null);//LowPowerStandbyPolicy
        hookAllMethodsWithCache_Auto(hookClass,"isExemptFromLowPowerStandby",true);
        hookAllMethodsWithCache_Auto(hookClass,"isReasonAllowedInLowPowerStandby",true);
        hookAllMethodsWithCache_Auto(hookClass,"isFeatureAllowedInLowPowerStandby",true);
        hookAllMethodsWithCache_Auto(hookClass,"acquireLowPowerStandbyPorts",null);
        hookAllMethodsWithCache_Auto(hookClass,"releaseLowPowerStandbyPorts",null);
        hookAllMethodsWithCache_Auto(hookClass,"getActiveLowPowerStandbyPorts",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"reboot",null);
        hookAllMethodsWithCache_Auto(hookClass,"rebootSafeMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"shutdown",null);
        hookAllMethodsWithCache_Auto(hookClass,"crash",null);
        hookAllMethodsWithCache_Auto(hookClass,"getLastShutdownReason",5);
        hookAllMethodsWithCache_Auto(hookClass,"getLastSleepReason",6);
        hookAllMethodsWithCache_Auto(hookClass,"setStayOnSetting",null);
        hookAllMethodsWithCache_Auto(hookClass,"boostScreenBrightness",null);
        hookAllMethodsWithCache_Auto(hookClass,"acquireWakeLockAsync",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"releaseWakeLockAsync",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateWakeLockUidsAsync",null);
        hookAllMethodsWithCache_Auto(hookClass,"isScreenBrightnessBoosted",true);
        hookAllMethodsWithCache_Auto(hookClass,"setAttentionLight",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDozeAfterScreenOff",null);
        hookAllMethodsWithCache_Auto(hookClass,"isAmbientDisplayAvailable",true);
        hookAllMethodsWithCache_Auto(hookClass,"suppressAmbientDisplay",null);
        hookAllMethodsWithCache_Auto(hookClass,"isAmbientDisplaySuppressedForToken",true);
        hookAllMethodsWithCache_Auto(hookClass,"isAmbientDisplaySuppressed",true);
        hookAllMethodsWithCache_Auto(hookClass,"isAmbientDisplaySuppressedForTokenByApp",true);
        hookAllMethodsWithCache_Auto(hookClass,"forceSuspend",true);
    }

}
