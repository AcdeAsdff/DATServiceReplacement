package com.linearity.datservicereplacement.ActivityManagerService;

import static com.linearity.datservicereplacement.ReturnIfNonSys.mSystemReady;
import static com.linearity.datservicereplacement.StartHook.callOnStarted;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.HookUtils.listenClass;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookAMS {

    public static void doHook(){
        classesAndHooks.put("com.android.server.am.ActivityManagerService",HookAMS::hookSystemReady);
    }

    private static final List<XC_MethodHook.Unhook> toUnregister = new ArrayList<>();
    public static void unregister(){
        try {
            for (XC_MethodHook.Unhook unhook:toUnregister){
                unhook.unhook();
            }
        }catch (ConcurrentModificationException c){
            unregister();//again!
        }
    }
    public static void hookSystemReady(Class<?> hookClass){
        if (mSystemReady){return;}
        toUnregister.addAll(
                XposedBridge.hookAllMethods(hookClass, "systemReady", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    mSystemReady = true;
                    callOnStarted();
                    unregister();
                }
            })
        );
        for (Method method:hookClass.getDeclaredMethods()){
            if (Modifier.isStatic(method.getModifiers())
            ){
                continue;
            }
            toUnregister.add(XposedBridge.hookMethod(method, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if (param.thisObject == null){
                        return;
                    }
                    if (XposedHelpers.getBooleanField(param.thisObject,"mSystemReady")){
                        mSystemReady = true;
                        callOnStarted();
                        unregister();
                    }
                }
            }));
        }

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
