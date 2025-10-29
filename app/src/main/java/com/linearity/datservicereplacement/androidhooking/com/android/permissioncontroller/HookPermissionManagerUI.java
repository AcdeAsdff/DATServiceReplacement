package com.linearity.datservicereplacement.androidhooking.com.android.permissioncontroller;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

public class HookPermissionManagerUI {

    public static void doHook(){
        classesAndHooks.put("com.android.permissioncontroller.permission.utils.KotlinUtils",HookPermissionManagerUI::hookKotlinUtils);
        classesAndHooks.put("com.android.permissioncontroller.permission.ui.model.AppPermissionViewModel", HookPermissionManagerUI::hookAppPermissionViewModel);
    }
    private static void hookAppPermissionViewModel(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"shouldShowPhotoPickerPromptForApp",true,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"expandsToStorageSupergroup",false,noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"expandToSupergroup",true,noSystemChecker);
    }
    private static void hookKotlinUtils(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"isPhotoPickerPromptEnabled",true,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"isPhotoPickerPromptSupported",true,noSystemChecker);
    }
}
