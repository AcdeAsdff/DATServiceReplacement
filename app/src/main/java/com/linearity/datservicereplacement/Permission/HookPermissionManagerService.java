package com.linearity.datservicereplacement.Permission;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;

import android.content.pm.PackageManager;

import com.linearity.datservicereplacement.Location.HookLocationManager;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookPermissionManagerService {
    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){
        Class<?> hookClass = XposedHelpers.findClassIfExists("com.android.server.pm.permission.PermissionManagerService",lpparam.classLoader);
        if (hookClass != null){
            hookPermissionManager(hookClass);
        }
    }

    public static void hookPermissionManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"checkPermission", PackageManager.PERMISSION_GRANTED);
    }
}
