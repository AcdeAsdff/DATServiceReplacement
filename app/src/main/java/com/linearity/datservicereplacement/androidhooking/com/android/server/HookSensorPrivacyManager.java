package com.linearity.datservicereplacement.androidhooking.com.android.server;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

public class HookSensorPrivacyManager {

    public static void doHook(){
        classesAndHooks.put("com.android.server.SensorPrivacyService$SensorPrivacyServiceImpl", HookSensorPrivacyManager::hookISensorPrivacyManager);
    }


    public static void hookISensorPrivacyManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"supportsSensorToggle",true);
        hookAllMethodsWithCache_Auto(hookClass,"addSensorPrivacyListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"addToggleSensorPrivacyListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeSensorPrivacyListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeToggleSensorPrivacyListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"isSensorPrivacyEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isCombinedToggleSensorPrivacyEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isToggleSensorPrivacyEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSensorPrivacy",null);
        hookAllMethodsWithCache_Auto(hookClass,"setToggleSensorPrivacy",null);
        hookAllMethodsWithCache_Auto(hookClass,"setToggleSensorPrivacyForProfileGroup",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCameraPrivacyAllowlist",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getToggleSensorPrivacyState",0);
        hookAllMethodsWithCache_Auto(hookClass,"setToggleSensorPrivacyState",null);
        hookAllMethodsWithCache_Auto(hookClass,"setToggleSensorPrivacyStateForProfileGroup",null);
        hookAllMethodsWithCache_Auto(hookClass,"isCameraPrivacyEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCameraPrivacyAllowlist",null);
        hookAllMethodsWithCache_Auto(hookClass,"suppressToggleSensorPrivacyReminders",null);
        hookAllMethodsWithCache_Auto(hookClass,"requiresAuthentication",true);
        hookAllMethodsWithCache_Auto(hookClass,"showSensorUseDialog",null);
    }
}
