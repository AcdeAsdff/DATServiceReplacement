package com.linearity.datservicereplacement.Phone;

import static com.linearity.datservicereplacement.Phone.GsmCdmaPhoneConstructor.DEVICE_ID;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.publicSeed;

import com.linearity.datservicereplacement.AppGlobal.HookAppGlobal;
import com.linearity.utils.ExtendedRandom;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookGsmCdmaPhone {


    public static void doHook(){

        classesAndHooks.put("com.android.internal.telephony.GsmCdmaPhone", HookGsmCdmaPhone::hookGsmCdmaPhone);
    }

    public static void hookGsmCdmaPhone(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceId",DEVICE_ID,noSystemChecker);
    }
}
