package com.linearity.datservicereplacement.androidhooking.com.android.server.pm;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.StartHook.SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.util.Log;

import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;

import de.robv.android.xposed.XposedHelpers;

public class HookSELinuxMMAC {

    public static void doHook(){
        classesAndHooks.put("com.android.server.pm.SELinuxMMAC",HookSELinuxMMAC::hookSELinuxMMAC);
        classesAndHooks.put("com.android.server.pm.PackageSetting",HookSELinuxMMAC::hookPackageSetting);
        classesAndHooks.put("com.android.server.pm.PackageManagerService",HookSELinuxMMAC::hookPackageManagerService);
        classesAndHooks.put("com.android.server.pm.pkg.mutate.PackageStateMutator",HookSELinuxMMAC::hookPackageStateMutator);
    }

    private static void hookSELinuxMMAC(Class<?> hookClass){
//        LoggerLog(new Exception("hooking:"+hookClass));
//        hookAllMethodsWithCache_Auto(hookClass,"getSeInfo",(SimpleExecutor)param -> {
//            String packageName = (String) XposedHelpers.callMethod(param.args[0], "getPackageName");
//            if (SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES.contains(packageName)) {
////                LoggerLog(new Exception("getSeInfo:"+packageName));
////                param.setResult("linearity_trusted_app");
//            }
//        },noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"getSeInfo",(SimpleExecutor)param -> {
//            String packageName = (String) XposedHelpers.callMethod(param.args[0], "getPackageName");
//            if (SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES.contains(packageName)) {
////                param.setResult("default:targetSdkVersion=3");//make difference so that start migration
//            }
//        },noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"getSeInfo",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            String packageName = (String) XposedHelpers.callMethod(param.args[0], "getPackageName");
            String result = (String) param.getResult();
//            LoggerLog("getSeInfo_SE:"+packageName + ":" + param.getResult());
            if (SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES.contains(packageName)) {
                param.setResult("default:targetSdkVersion=30");
            }else if (result != null && result.contains("targetSdkVersion=30")){
                param.setResult("default:targetSdkVersion=25");//this is my place now,get out of here
            }
        }),noSystemChecker);
    }
    private static void hookPackageManagerService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"commitPackageStateMutation",showAfter,noSystemChecker);

    }
    private static void hookPackageSetting(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"getSeInfo",(SimpleExecutor)param -> {
//            String packageName = (String) XposedHelpers.callMethod(param.thisObject,"getPackageName");
//
//            if (SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES.contains(packageName)){
////                param.setResult("default:targetSdkVersion=2");//make difference so that start migration
//            }
//        },noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"getSeInfo",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            String packageName = (String) XposedHelpers.callMethod(param.thisObject,"getPackageName");
            String result = (String) param.getResult();
//            LoggerLog("getSeInfo_SE:"+packageName + ":" + param.getResult());
            if (SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES.contains(packageName)) {
                param.setResult("default:targetSdkVersion=30");
            }else if (result != null && result.contains("targetSdkVersion=30")){
                param.setResult("default:targetSdkVersion=25");//this is my place now,get out of here
            }
        }),noSystemChecker);
    }
    private static void hookPackageStateMutator(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"setOverrideSeInfo",showAfter,noSystemChecker);

    }
}
