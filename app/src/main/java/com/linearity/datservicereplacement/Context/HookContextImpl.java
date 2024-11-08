package com.linearity.datservicereplacement.Context;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;

import android.content.pm.PackageManager;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookContextImpl {

    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){
        Class<?> hookClass = XposedHelpers.findClassIfExists("android.app.ContextImpl",lpparam.classLoader);
        if (hookClass != null){
            hookContextImpl(hookClass);
        }
    }

    public static void hookContextImpl(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass, "checkPermission", PackageManager.PERMISSION_GRANTED);
    }

}
