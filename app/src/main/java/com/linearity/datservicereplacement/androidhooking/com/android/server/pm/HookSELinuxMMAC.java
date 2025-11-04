package com.linearity.datservicereplacement.androidhooking.com.android.server.pm;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.SELINUX_TRUST_AS_NORMAL_PACKAGE_HEADERS;
import static com.linearity.datservicereplacement.StartHook.SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.isSystemApp;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import com.linearity.utils.SimpleExecutorWithMode;

import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookSELinuxMMAC {

    public static void doHook(){
        classesAndHooks.put("com.android.server.pm.SELinuxMMAC",HookSELinuxMMAC::hookSELinuxMMAC);
        classesAndHooks.put("com.android.server.pm.PackageSetting",HookSELinuxMMAC::hookPackageSetting);
//        classesAndHooks.put("com.android.server.pm.PackageManagerService",HookSELinuxMMAC::hookPackageManagerService);
//        classesAndHooks.put("com.android.server.pm.pkg.mutate.PackageStateMutator",HookSELinuxMMAC::hookPackageStateMutator);
    }

    private static void hookSELinuxMMAC(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getSeInfo",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            String packageName = (String) XposedHelpers.callMethod(param.args[0], "getPackageName");
            modifySEInfoResult(param,packageName,30,Set.of(30,31,32,33),true, 34);
        }),noSystemChecker);
    }
    private static void hookPackageSetting(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getSeInfo",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            String packageName = (String) XposedHelpers.callMethod(param.thisObject,"getPackageName");
            modifySEInfoResult(param,packageName,25,Set.of(30,31,32,33),true,25);
        }),noSystemChecker);
    }
    //note that packagemanager isn't fully online yet
    private static void modifySEInfoResult(XC_MethodHook.MethodHookParam param,String packageName,
                                           int sdkInt,
                                           Set<Integer> avoidCollisionSet,
                                           boolean avoidCollisionFlag,
                                           Integer avoidTo){
        String result = (String) param.getResult();
        boolean whiteListHeadFlag = false;
        for (String s:SELINUX_TRUST_AS_NORMAL_PACKAGE_HEADERS){
            if (packageName.startsWith(s)){
                whiteListHeadFlag = true;
                break;
            }
        }
        boolean doReplaceFlag = false;
        if (whiteListHeadFlag && result.contains("default:")){
            doReplaceFlag = true;
        }else if (SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES.contains(packageName)){
            doReplaceFlag = true;
        }
        boolean hasSDKVerToAvoid = false;
        for (int i:avoidCollisionSet){
            if (result.contains("targetSdkVersion=" + i)){
                hasSDKVerToAvoid = true;
                break;
            }
        }

        if (doReplaceFlag) {
//            LoggerLog(packageName + " " + result + " " + sdkInt + " " + doReplaceFlag);
            String[] rebuildResult = result.split(":");
            for (int i=0;i<rebuildResult.length;i++){
                if (rebuildResult[i].contains("targetSdkVersion=")){
                    rebuildResult[i] = "targetSdkVersion=" + sdkInt;
                }
            }
            StringBuilder newResultBuilder = new StringBuilder();
            for (int i=0;i<rebuildResult.length-1;i++){
                newResultBuilder.append(rebuildResult[i]).append(":");
            }
            newResultBuilder.append(rebuildResult[rebuildResult.length-1]);
            String newResult = newResultBuilder.toString();

            if (newResult.contains("default:targetSdkVersion")
                    && packageName.startsWith("com.android")
            ){
                newResult = newResult.replace("default:targetSdkVersion","default:privapp:targetSdkVersion");
                newResult = newResult.replace("platform:targetSdkVersion","platform:privapp:targetSdkVersion");
            }
            param.setResult(newResult);
//            LoggerLog(packageName + " " + result + " -> " + newResult.toString());
        }
        else if (result != null && avoidCollisionFlag && hasSDKVerToAvoid){
            StringBuilder resultBuilder = new StringBuilder();
            String[] args = result.split(":");
//            args[0] = "invalid";
            for (String s:args){
                if (!s.contains("targetSdkVersion")){
                    resultBuilder.append(s).append(":");
                } else {
                    resultBuilder.append("targetSdkVersion=").append(avoidTo).append(":");
                }
            }
            param.setResult(resultBuilder.toString().substring(0,resultBuilder.length()-1));//this is my place now,get out of here
        }
//        if (packageName.contains("com.tencent")){
//            LoggerLog(packageName + " " + doReplaceFlag + " " + param.getResult()
//                    + " " + sdkInt  + " " + avoidCollisionFlag + " " + avoidTo + " ");
//        }
    }
    private static void hookPackageManagerService(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"commitPackageStateMutation",showAfter,noSystemChecker);
    }
    private static void hookPackageStateMutator(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"setOverrideSeInfo",showAfter,noSystemChecker);

    }
}
