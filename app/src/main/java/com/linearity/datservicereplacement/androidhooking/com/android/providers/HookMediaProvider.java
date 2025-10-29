package com.linearity.datservicereplacement.androidhooking.com.android.providers;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import com.linearity.datservicereplacement.androidhooking.com.android.permissioncontroller.HookPermissionManagerUI;

public class HookMediaProvider {

    public static void doHook(){
        classesAndHooks.put("com.android.providers.media.AccessChecker", HookMediaProvider::hookAccessChecker);
        classesAndHooks.put("com.android.providers.media.MediaProvider", HookMediaProvider::hookMediaProvider);
        classesAndHooks.put("com.android.providers.media.LocalCallingIdentity", HookMediaProvider::hookLocalCallingIdentity);
    }

    private static void hookAccessChecker(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"hasUserSelectedAccess",showAfter,noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"getWhereForConstrainedAccess",showAfter,noSystemChecker);
    }
    private static void hookMediaProvider(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"appendAccessCheckQuery",showAfter,noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"getQueryBuilderInternal",showAfter,noSystemChecker);
    }
    private static void hookLocalCallingIdentity(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"checkCallingPermissionUserSelected",true);
    }
}
