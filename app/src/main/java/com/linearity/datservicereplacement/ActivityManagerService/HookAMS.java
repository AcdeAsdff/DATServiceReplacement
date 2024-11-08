package com.linearity.datservicereplacement.ActivityManagerService;

import static com.linearity.datservicereplacement.ReturnIfNonSys.mSystemReady;
import static com.linearity.datservicereplacement.StartHook.callOnStarted;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookAMS {

    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){

        Class<?> hookClass = XposedHelpers.findClassIfExists("com.android.server.am.ActivityManagerService",XposedBridge.BOOTCLASSLOADER);
        if (hookClass != null){
            HookAMS.hookAMS(hookClass);
        }
    }

    public static void hookAMS(Class<?> hookClass){

        XposedBridge.hookAllMethods(hookClass, "systemReady", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                mSystemReady = true;
                callOnStarted();
            }
        });

//        XposedBridge.hookAllMethods(hookClass, "startService", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                int userid = (int) param.args[6];
//                if (param.args[4] == null){return;}
//                if (!isSystemApp(param.args[4].toString(),userid)){
//                    LoggerLog("--------start service-----------");
//                    StringBuilder sb = new StringBuilder();
//                    for (Object o:param.args){
//                        if (o != null){
//                            sb.append(o.toString()).append("\n");
//                        }else {
//                            sb.append("null\n");
//                        }
//                    }
//                    LoggerLog(sb.toString());
//                }
//            }
//        });
    }



}
