package com.linearity.datservicereplacement.DeviceIdentifiersPolicy;

import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.FakeInfo.FakeProcInfoGenerator.infoMap;

import com.linearity.datservicereplacement.Context.HookContextImpl;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookDeviceIdentifiersPolicy {

    public static void doHook(){
        classesAndHooks.put("com.android.server.os.DeviceIdentifiersPolicyService$DeviceIdentifiersPolicy", HookDeviceIdentifiersPolicy::hookIDeviceIdentifiersPolicyService);
    }

    public static void hookIDeviceIdentifiersPolicyService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getSerial",infoMap.get("ro.serialno"));
        hookAllMethodsWithCache_Auto(hookClass,"getSerialForPackage",infoMap.get("ro.serialno"),getSystemChecker_PackageNameAt(0));
    }
}
