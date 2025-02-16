package com.linearity.datservicereplacement.AppGlobal;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import com.linearity.datservicereplacement.Health.HookHealth;
import com.linearity.datservicereplacement.PackageManager.HookIPackageManager;
import com.linearity.utils.SimpleExecutorWithMode;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookAppGlobal {

    public static void doHook(){
        classesAndHooks.put("android.app.AppGlobals", HookAppGlobal::hookAppGlobals);
    }

    public static void hookAppGlobals(Class<?> hookClass) {
        hookAllMethodsWithCache_Auto(hookClass,
            "getPackageManager",
            new SimpleExecutorWithMode(MODE_AFTER, param -> {
                Object pm = param.getResult();
                if (pm == null){return;}
                HookIPackageManager.hookIPackageManager(pm.getClass());
            }),
                noSystemChecker
        );
    }
}
