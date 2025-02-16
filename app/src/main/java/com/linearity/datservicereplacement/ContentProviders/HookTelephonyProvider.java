package com.linearity.datservicereplacement.ContentProviders;

import static com.linearity.datservicereplacement.ReturnIfNonSys.contentProviderSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import com.linearity.datservicereplacement.Telephony.HookTelephony;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookTelephonyProvider {


    public static void doHook(){
        classesAndHooks.put("com.android.providers.telephony.TelephonyProvider", HookTelephonyProvider::hookTelephonyProvider);
    }

    public static void hookTelephonyProvider(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"query",showBefore, contentProviderSystemChecker);

    }
}
