package com.linearity.datservicereplacement.androidhooking.com.android.internal.telephony;

import static com.linearity.utils.Phone.GsmCdmaPhoneConstructor.DEVICE_ID;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

public class HookGsmCdmaPhone {


    public static void doHook(){

        classesAndHooks.put("com.android.internal.telephony.GsmCdmaPhone", HookGsmCdmaPhone::hookGsmCdmaPhone);
    }

    public static void hookGsmCdmaPhone(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceId",DEVICE_ID,noSystemChecker);
    }
}
