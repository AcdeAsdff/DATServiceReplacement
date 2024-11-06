package com.linearity.datservicereplacement.AppOps;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;

import android.app.AppOpsManager;
import android.content.pm.PackageManager;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookAppOpsService {

    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){
        Class<?> hookClass = XposedHelpers.findClassIfExists("com.android.server.appop.AppOpsService",lpparam.classLoader);
        if (hookClass != null){
            hookAppOps(hookClass);
        }
    }

    //微信老要我这个权限，我骗他一手，但是不改他app，怕被抓到
    public static void hookAppOps(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"checkOperation", AppOpsManager.MODE_FOREGROUND);
    }
}
