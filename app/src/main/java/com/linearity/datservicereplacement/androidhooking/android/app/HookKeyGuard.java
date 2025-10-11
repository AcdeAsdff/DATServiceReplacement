package com.linearity.datservicereplacement.androidhooking.android.app;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.isHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.registerServiceHook_map;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.content.Context;

import java.util.HashSet;
import java.util.Set;

public class HookKeyGuard {

    public static void doHook() {
         hookPublishBinderService();
    }

    public static void hookPublishBinderService(){
        registerServiceHook_map.put(Context.KEYGUARD_SERVICE,c -> {
            hookIKeyguardService(c);
            return null;
        });
    }


    public static final Set<Class<?>> IKeyguardServiceHookedPool = new HashSet<>();
    public static void hookIKeyguardService(Class<?> hookClass){
        if (isHookedPoolRegistered(hookClass,IKeyguardServiceHookedPool)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"setOccluded",null);
        hookAllMethodsWithCache_Auto(hookClass,"addStateMonitorCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"verifyUnlock",null);
        hookAllMethodsWithCache_Auto(hookClass,"dismiss",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDreamingStarted",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDreamingStopped",null);
        hookAllMethodsWithCache_Auto(hookClass,"onStartedGoingToSleep",null);
        hookAllMethodsWithCache_Auto(hookClass,"onFinishedGoingToSleep",null);
        hookAllMethodsWithCache_Auto(hookClass,"onStartedWakingUp",null);
        hookAllMethodsWithCache_Auto(hookClass,"onFinishedWakingUp",null);
        hookAllMethodsWithCache_Auto(hookClass,"onScreenTurningOn",null);
        hookAllMethodsWithCache_Auto(hookClass,"onScreenTurnedOn",null);
        hookAllMethodsWithCache_Auto(hookClass,"onScreenTurningOff",null);
        hookAllMethodsWithCache_Auto(hookClass,"onScreenTurnedOff",null);
        hookAllMethodsWithCache_Auto(hookClass,"setKeyguardEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSystemReady",null);
        hookAllMethodsWithCache_Auto(hookClass,"doKeyguardTimeout",null);
        hookAllMethodsWithCache_Auto(hookClass,"setSwitchingUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"setCurrentUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBootCompleted",null);
        hookAllMethodsWithCache_Auto(hookClass,"startKeyguardExitAnimation",null);
        hookAllMethodsWithCache_Auto(hookClass,"onShortPowerPressedGoHome",null);
        hookAllMethodsWithCache_Auto(hookClass,"dismissKeyguardToLaunch",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSystemKeyPressed",null);
        hookAllMethodsWithCache_Auto(hookClass,"showDismissibleKeyguard",null);
    }


}
