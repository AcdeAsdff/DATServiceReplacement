package com.linearity.datservicereplacement.androidhooking.android.app;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.isHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.registerServiceHook_map;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import java.util.HashSet;
import java.util.Set;

public class HookTrust {
    public static void doHook() {
        hookPublishBinderService();
    }
    public static void hookPublishBinderService(){
        registerServiceHook_map.put("trust",c -> {
            hookITrustManager(c);
            return null;
        });
    }
    public static final Set<Class<?>> ITrustManagerHookedPool = new HashSet<>();
    public static void hookITrustManager(Class<?> hookClass){
        if(isHookedPoolRegistered(hookClass,ITrustManagerHookedPool)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"reportUnlockAttempt",null);
        hookAllMethodsWithCache_Auto(hookClass,"reportUserRequestedUnlock",null);
        hookAllMethodsWithCache_Auto(hookClass,"reportUserMayRequestUnlock",null);
        hookAllMethodsWithCache_Auto(hookClass,"reportUnlockLockout",null);
        hookAllMethodsWithCache_Auto(hookClass,"reportEnabledTrustAgentsChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerTrustListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterTrustListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"reportKeyguardShowingChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceLockedForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"isDeviceLocked",false);
        hookAllMethodsWithCache_Auto(hookClass,"isDeviceSecure",false);
        hookAllMethodsWithCache_Auto(hookClass,"isTrustUsuallyManaged",true);
        hookAllMethodsWithCache_Auto(hookClass,"unlockedByBiometricForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"clearAllBiometricRecognized",null);
        hookAllMethodsWithCache_Auto(hookClass,"isActiveUnlockRunning",true);
        hookAllMethodsWithCache_Auto(hookClass,"isInSignificantPlace",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerDeviceLockedStateListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterDeviceLockedStateListener",null);
    }

}
