package com.linearity.datservicereplacement.androidhooking.com.android.server.pm;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.SELINUX_TRUST_AS_NORMAL_PACKAGE_HEADERS;
import static com.linearity.datservicereplacement.StartHook.SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import com.linearity.utils.SimpleExecutorWithMode;

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
            modifySEInfoResult(param,packageName,30,true, 25);
        }),noSystemChecker);
    }
    private static void hookPackageSetting(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getSeInfo",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            String packageName = (String) XposedHelpers.callMethod(param.thisObject,"getPackageName");
            modifySEInfoResult(param,packageName,25,false,29);
        }),noSystemChecker);
    }
    private static void modifySEInfoResult(XC_MethodHook.MethodHookParam param,String packageName,int sdkInt,boolean avoidCollisionFlag, int avoidTo){
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

        if (doReplaceFlag) {
//            LoggerLog(packageName + " " + result + " " + sdkInt + " " + doReplaceFlag);
            String[] rebuildResult = result.split(":");
            for (int i=0;i<rebuildResult.length;i++){
                if (rebuildResult[i].contains("targetSdkVersion=")){
                    rebuildResult[i] = "targetSdkVersion=" + sdkInt;
                }
            }
            StringBuilder newResult = new StringBuilder();
            for (int i=0;i<rebuildResult.length-1;i++){
                newResult.append(rebuildResult[i]).append(":");
            }
            newResult.append(rebuildResult[rebuildResult.length-1]);
            param.setResult(newResult.toString());
//            LoggerLog(packageName + " " + result + " -> " + newResult.toString());
        }
        else if (result != null && avoidCollisionFlag){
            param.setResult(result.replace("default:targetSdkVersion=" + sdkInt,"default:targetSdkVersion=" + avoidTo));//this is my place now,get out of here
        }
    }
    private static void hookPackageManagerService(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"commitPackageStateMutation",showAfter,noSystemChecker);
    }
    private static void hookPackageStateMutator(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"setOverrideSeInfo",showAfter,noSystemChecker);

    }
}
