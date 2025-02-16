package com.linearity.datservicereplacement;

import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

//debug usage only
public class MessageFinder {
    public static final Map<Object,Exception> binderStacks = new HashMap<>();
    public static final XC_MethodHook checkMessage = new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            int what = XposedHelpers.getIntField(param.thisObject,"what");
            int arg1 = XposedHelpers.getIntField(param.thisObject,"arg1");
            int arg2 = XposedHelpers.getIntField(param.thisObject,"arg2");
            Object obj = XposedHelpers.getObjectField(param.thisObject,"obj");
            Messenger replyTo = (Messenger) XposedHelpers.getObjectField(param.thisObject,"replyTo");
            int sendingUid = XposedHelpers.getIntField(param.thisObject,"sendingUid");
            int workSourceUid = XposedHelpers.getIntField(param.thisObject,"workSourceUid");
            int flags = XposedHelpers.getIntField(param.thisObject,"flags");
            long when = XposedHelpers.getLongField(param.thisObject,"when");
            Bundle data = (Bundle) XposedHelpers.getObjectField(param.thisObject,"data");
            Handler target = (Handler) XposedHelpers.getObjectField(param.thisObject,"target");
            Runnable callback = (Runnable) XposedHelpers.getObjectField(param.thisObject,"callback");
            Message next = (Message) XposedHelpers.getObjectField(param.thisObject,"next");

            if (arg1 == 2 && target != null){
                if (target.getClass().getName().equals("com.android.server.BluetoothManagerService$BluetoothHandler")){
                    StringBuilder sb = new StringBuilder();
                    sb.append("what:").append(what).append("\n")
                            .append("arg1:").append(arg1).append("\n")
                            .append("arg2:").append(arg2).append("\n")
                            .append("obj:").append(obj).append("\n")
                            .append("replyTo:").append(replyTo).append("\n")
                            .append("sendingUid:").append(sendingUid).append("\n")
                            .append("workSourceUid:").append(workSourceUid).append("\n")
                            .append("flags:").append(flags).append("\n")
                            .append("when:").append(when).append("\n")
                            .append("data:").append(data).append("\n")
                            .append("target:").append(target).append("\n")
                            .append("callback:").append(callback).append("\n")
                            .append("next:").append(next).append("\n");
                    LoggerLog(new Exception(sb.toString()));
                    if (obj != null){
                        if (obj.getClass().getName().equals("android.os.BinderProxy")){
                            Exception exception = binderStacks.get(obj);
                            LoggerLog(exception == null?exception:"NULL!");
                        }
                    }
                }
            }
        }
    };
    //i wonder where BluetoothGatt BinderProxy come from,however stacktrace doesn't work well in multi-thread
    public static void hookMessage(XC_LoadPackage.LoadPackageParam lpparam){
        Class<?> hookClass = Message.class;
        for (Method m : hookClass.getDeclaredMethods()) {
            if (Modifier.isAbstract(m.getModifiers()) || Modifier.isStatic(m.getModifiers())) {
                continue;
            }
            XposedBridge.hookMethod(m, checkMessage);
        }
        hookClass = XposedHelpers.findClassIfExists("android.os.BinderProxy", lpparam.classLoader);
        if (hookClass != null){
            XC_MethodHook addToMap = new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    binderStacks.put(
                            param.thisObject,
                            new Exception("not an exception")
                    );
                    if (param.getResult() != null){
                        binderStacks.put(param.getResult(),new Exception("not an exception"));
                    }
                }
            };
            XposedBridge.hookAllConstructors(hookClass, addToMap);
            for (Method m: hookClass.getDeclaredMethods()){
                if (Modifier.isStatic(m.getModifiers()) || Modifier.isAbstract(m.getModifiers())){
                    continue;
                }
                XposedBridge.hookMethod(m,addToMap);
            }
        }
    }
}
