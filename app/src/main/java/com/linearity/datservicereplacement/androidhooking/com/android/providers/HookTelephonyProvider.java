package com.linearity.datservicereplacement.androidhooking.com.android.providers;

import static com.linearity.datservicereplacement.ReturnIfNonSys.contentProviderSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import com.linearity.utils.NotFinished;

@NotFinished
public class HookTelephonyProvider {


    public static void doHook(){
        classesAndHooks.put("com.android.providers.telephony.TelephonyProvider", HookTelephonyProvider::hookTelephonyProvider);
    }

    public static void hookTelephonyProvider(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"query",showBefore, contentProviderSystemChecker);

    }
}
