package com.linearity.datservicereplacement.androidhooking.com.android.server.devicelock;

import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

public class HookDeviceLock {

    public static void doHook(){
        classesAndHooks.put("com.android.server.devicelock.DeviceLockServiceImpl", HookDeviceLock::hookIDeviceLockService);
    }

    public static void hookIDeviceLockService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"lockDevice",null);
        hookAllMethodsWithCache_Auto(hookClass,"unlockDevice",null);
        hookAllMethodsWithCache_Auto(hookClass,"isDeviceLocked",null);
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceId",null);
        hookAllMethodsWithCache_Auto(hookClass,"getKioskApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"addFinancedDeviceKioskRole",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"removeFinancedDeviceKioskRole",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setCallerExemptFromActivityBgStartRestrictionState",null);
        hookAllMethodsWithCache_Auto(hookClass,"setCallerAllowedToSendUndismissibleNotifications",null);
        hookAllMethodsWithCache_Auto(hookClass,"setUidExemptFromRestrictionsState",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableKioskKeepalive",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"disableKioskKeepalive",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableControllerKeepalive",null);
        hookAllMethodsWithCache_Auto(hookClass,"disableControllerKeepalive",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceFinalized",null);
        hookAllMethodsWithCache_Auto(hookClass,"setPostNotificationsSystemFixed",null);
    }

}
