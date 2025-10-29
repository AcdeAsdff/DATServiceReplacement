package com.linearity.datservicereplacement.androidhooking.com.android.server.am;

import static com.linearity.datservicereplacement.ReturnIfNonSys.findArgByClassInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexAndObjectInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.mSystemReady;
import static com.linearity.datservicereplacement.StartHook.callOnStarted;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.hookPackageManager.isSystemApplicationInfo;
import static com.linearity.utils.HookUtils.listenClass;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.content.pm.ApplicationInfo;

import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SystemAppChecker;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HookAMS {

    public static void doHook(){
        classesAndHooks.put("com.android.server.am.ActivityManagerService",HookAMS::hookSystemReady);
        classesAndHooks.put("com.android.server.am.ActivityManagerService",HookAMS::hookAMS);
    }

    private static final Queue<XC_MethodHook.Unhook> toUnregister = new ConcurrentLinkedQueue<>();
    public static void unregister(){
        while (!toUnregister.isEmpty()) {
            try {
                XC_MethodHook.Unhook unhook = toUnregister.poll();
                if (unhook != null){
                    unhook.unhook();
                }
            } catch (ConcurrentModificationException c) {
                unregister();//again!
            }
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
    @NotFinished
    private static void hookAMS(Class<?> hookClass){
        Class<?> hostingRecordClass = null;
        try {
            hostingRecordClass = hookClass.getClassLoader().loadClass("com.android.server.am.HostingRecord");
        }catch (Exception e){
            LoggerLog(e);
        }
        if (hostingRecordClass == null){
            hostingRecordClass = XposedHelpers.findClassIfExists("com.android.server.am.HostingRecord",hookClass.getClassLoader());
        }
        Class<?> finalHostingRecordClass = hostingRecordClass;
        hookAllMethodsWithCache_Auto(hookClass, "startProcessLocked", (SimpleExecutor) param -> {

            Object hostingRecord = findArgByClassInArgs(param.args, finalHostingRecordClass);
            if (hostingRecord == null){return;}
            if (XposedHelpers.getBooleanField(hostingRecord,"mIsTopApp")){
                return;
            }
            Exception checkException = new Exception("prevent autostart");
            for (StackTraceElement element:checkException.getStackTrace()){
                if (element.getMethodName().equals("scheduleReceiverColdLocked")){
//                    LoggerLog(checkException);
                    param.setResult(null);
                    return;
                }
            }
        }, param -> {
            ApplicationInfo appInfo = findArgByClassInArgs(param.args,ApplicationInfo.class);
            if (appInfo == null){return true;}
            return isSystemApplicationInfo(appInfo);
        });
    }


}
