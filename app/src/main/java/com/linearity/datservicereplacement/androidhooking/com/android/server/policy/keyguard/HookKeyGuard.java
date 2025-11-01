package com.linearity.datservicereplacement.androidhooking.com.android.server.policy.keyguard;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.isHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.newWeakSet;
import static com.linearity.datservicereplacement.StartHook.registerServiceHook_map;

import android.content.Context;

import com.linearity.utils.NotFinished;

import java.util.HashSet;
import java.util.Set;

import de.robv.android.xposed.XposedHelpers;

@NotFinished
public class HookKeyGuard {

    public static void doHook() {
        hookPublishBinderService();
        classesAndHooks.put("com.android.internal.policy.KeyguardDismissCallback", HookKeyGuard::hookIKeyguardDismissCallback);
        classesAndHooks.put("com.android.server.policy.keyguard.KeyguardServiceDelegate$KeyguardShowDelegate",HookKeyGuard::hookIKeyguardDrawnCallback);
        classesAndHooks.put("com.android.server.policy.keyguard.KeyguardServiceDelegate$KeyguardExitDelegate",HookKeyGuard::hookIKeyguardExitCallback);
        classesAndHooks.put("com.android.server.policy.keyguard.KeyguardStateMonitor",HookKeyGuard::hookIKeyguardStateCallback);
        classesAndHooks.put("com.android.server.policy.keyguard.KeyguardServiceWrapper",HookKeyGuard::hookIKeyguardService);
    }

    public static void hookPublishBinderService(){
        registerServiceHook_map.put(Context.KEYGUARD_SERVICE,c -> {
            hookIKeyguardService(c);
            if (XposedHelpers.findFieldIfExists(c,"mBinder") != null){
                Object stub = XposedHelpers.getObjectField(c,"mBinder");
                if (stub != null){
                    hookIKeyguardService(stub.getClass());
                }
            }
            return null;
        });
    }



    public static void hookIKeyguardStateCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onShowingStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSimSecureStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onInputRestrictedStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onTrustedChanged",null);
    }

    public static void hookIKeyguardLockedStateListener(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onKeyguardLockedStateChanged",null);
    }

    public static void hookIKeyguardExitCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onKeyguardExitResult",null);
    }

    public static void hookIKeyguardDrawnCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onDrawn",null);
    }

    public static void hookIKeyguardDismissCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onDismissError",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDismissSucceeded",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDismissCancelled",null);
    }

    public static void hookIDeviceLockedStateListener(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onDeviceLockedStateChanged",null);
    }
    public static final Set<Class<?>> IKeyguardServiceHookedPool = newWeakSet();
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
