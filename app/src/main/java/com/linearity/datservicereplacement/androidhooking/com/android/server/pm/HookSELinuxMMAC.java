package com.linearity.datservicereplacement.androidhooking.com.android.server.pm;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import com.linearity.utils.SimpleExecutor;

import de.robv.android.xposed.XposedHelpers;

public class HookSELinuxMMAC {

    public static void doHook(){
        classesAndHooks.put("com.android.server.pm.selinux.SELinuxMMAC",HookSELinuxMMAC::hookSELinuxMMAC);
    }

    private static void hookSELinuxMMAC(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getSeInfo",(SimpleExecutor)param -> {
            String packageName = (String) XposedHelpers.callMethod(param.args[0], "getPackageName");
            if (SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES.contains(packageName)) {
                param.setResult("linearity_trusted_app");
            }
        },noSystemChecker);
    }
}
