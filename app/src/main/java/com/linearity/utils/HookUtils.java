package com.linearity.utils;

import static com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap.fakeObjects;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.ReturnReplacements.*;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.UUID;
import java.util.Vector;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HookUtils {
    public static XC_MethodHook.Unhook findAndHookMethodIfExists(String className, ClassLoader classLoader, String methodName, Object... parameterTypesAndCallback){
        Class<?> hookClass = XposedHelpers.findClassIfExists(className,classLoader);
        if (hookClass == null){LoggerLog("cannot find class: " + className);return null;}
        return findAndHookMethodIfExists(hookClass,methodName,parameterTypesAndCallback);
    }

    public static XC_MethodHook.Unhook findAndHookMethodIfExists(Class<?> clazz, String methodName, Object... parameterTypesAndCallback){
        if (parameterTypesAndCallback.length == 0 || !(parameterTypesAndCallback[parameterTypesAndCallback.length-1] instanceof XC_MethodHook))
            throw new IllegalArgumentException("no callback defined");
        XC_MethodHook callback = (XC_MethodHook) parameterTypesAndCallback[parameterTypesAndCallback.length-1];
        Method m = XposedHelpers.findMethodExactIfExists(clazz, methodName, parameterTypesAndCallback);
        if (m == null){
            LoggerLog("cannot find method : " + methodName + "(for class : " + clazz.getName() + ")");
            return null;
        }
        return XposedBridge.hookMethod(m, callback);
    }

    public static void disableMethod_random(@NotNull Method m,@NotNull  Class<?> selfClass){
        Class<?> t = m.getReturnType();
        if (Modifier.isAbstract(m.getModifiers())){return;}
        if (t.equals(Boolean.class) || t.equals(boolean.class)){
            XposedBridge.hookMethod(m,returnRandomBoolean);
        }else if (t.equals(int.class) || t.equals(Integer.class)){
            XposedBridge.hookMethod(m,returnIntegerRandom);
        }else if (t.equals(long.class) || t.equals(Long.class)){
            XposedBridge.hookMethod(m,returnLongRandom);
        }else if (t.equals(short.class) || t.equals(Short.class)){
            XposedBridge.hookMethod(m,returnShortRandom);
        }else if (t.equals(double.class) || t.equals(Double.class)){
            XposedBridge.hookMethod(m,returnDoubleRandom);
        }else if (t.equals(float.class) || t.equals(Float.class)){
            XposedBridge.hookMethod(m,returnFloatRandom);
        }else if (t.equals(byte.class) || t.equals(Byte.class)){
            XposedBridge.hookMethod(m,returnByteRandom);
        }else if (t.equals(char.class) || t.equals(Character.class)){
            XposedBridge.hookMethod(m,returnCharRandom);
        }
        else if(t.equals(byte[].class) || t.equals(Byte[].class)){
            XposedBridge.hookMethod(m,returnRandomByteArray);
        }
        else if(t.equals(int[].class) || t.equals(Integer[].class)){
            XposedBridge.hookMethod(m,returnRandomIntegerArray);
        }
        else if(t.equals(boolean[].class) || t.equals(Boolean[].class)){
            XposedBridge.hookMethod(m,returnRandomBooleanArray);
        }
        else if(t.equals(double[].class) || t.equals(Double[].class)){
            XposedBridge.hookMethod(m,returnRandomDoubleArray);
        }
        else if(t.equals(float[].class) || t.equals(Float[].class)){
            XposedBridge.hookMethod(m,returnRandomFloatArray);
        }
        else if(t.equals(long[].class) || t.equals(Long[].class)){
            XposedBridge.hookMethod(m,returnRandomLongArray);
        }
        else if(t.equals(short[].class) || t.equals(Short[].class)){
            XposedBridge.hookMethod(m,returnRandomShortArray);
        }
        else if(t.equals(char[].class) || t.equals(Character[].class)){
            XposedBridge.hookMethod(m,returnRandomCharArray);
        }
        else {
            disableMethod(m,selfClass);
        }
    }
    public static void disableMethod(@NotNull Method m,@NotNull  Class<?> selfClass){
        Class<?> t = m.getReturnType();
        if (Modifier.isAbstract(m.getModifiers())){return;}
        if (t.equals(Void.TYPE)){
            XposedBridge.hookMethod(m,returnNull);
        }else if (t.equals(Boolean.class) || t.equals(boolean.class)){
            XposedBridge.hookMethod(m,returnFalse);
        }else if (t.equals(int.class) || t.equals(Integer.class)){
            XposedBridge.hookMethod(m,returnIntegerZero);
        }else if (t.equals(long.class) || t.equals(Long.class)){
            XposedBridge.hookMethod(m,returnLongZero);
        }else if (t.equals(short.class) || t.equals(Short.class)){
            XposedBridge.hookMethod(m,returnShortZero);
        }else if (t.equals(double.class) || t.equals(Double.class)){
            XposedBridge.hookMethod(m,returnDoubleZero);
        }else if (t.equals(float.class) || t.equals(Float.class)){
            XposedBridge.hookMethod(m,returnFloatZero);
        }else if (t.equals(byte.class) || t.equals(Byte.class)){
            XposedBridge.hookMethod(m,returnByteZero);
        }else if (t.equals(char.class) || t.equals(Character.class)){
            XposedBridge.hookMethod(m,returnCharZero);
        }else if(t.equals(String.class)){
            XposedBridge.hookMethod(m,returnStringOne);
        }else if(t.equals(Map.class) || t.equals(AbstractMap.class) || t.equals(HashMap.class)){
            XposedBridge.hookMethod(m,returnCantUseHashMap);
        }else if(t.equals(List.class) || t.equals(ArrayList.class) || t.equals(AbstractList.class) || t.equals(RandomAccess.class)){
            XposedBridge.hookMethod(m,returnCantUseArrayList);
        }else if(t.equals(Vector.class)){
            XposedBridge.hookMethod(m,returnCantUseVector);
        }else if(t.equals(Enumeration.class)){
            XposedBridge.hookMethod(m,returnCantUseEnumeration);
        }
        else if(t.equals(UUID.class)){
            XposedBridge.hookMethod(m,returnRandomUUID);
        }
        else if (t.isArray() && t.getComponentType() != null){
            XposedBridge.hookMethod(m, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) {
                    return Array.newInstance(t.getComponentType(),0);
                }
            });
        }else if(t.equals(selfClass) || t.isAssignableFrom(selfClass)){
            return;
        }else if (fakeObjects.containsKey(t.getName()) && fakeObjects.get(t.getName()) != null){
            XposedBridge.hookMethod(m,fakeObjects.get(t.getName()).first);
        }else {
            XposedBridge.hookMethod(m,FakeReturnClassMap.GenerateMethodReplacement_FindObjectFromMap(t.getName()));
        };
    }

    public static void disableClass(@NotNull Class<?> selfClass){
        if (Modifier.isAbstract(selfClass.getModifiers()) || Modifier.isInterface(selfClass.getModifiers())){return;}
        XposedBridge.hookAllConstructors(selfClass,returnNullAndRegisterReturn);
        for (Method m:selfClass.getDeclaredMethods()){
            disableMethod(m,selfClass);
        }
    }

    public static void disableClass_random(@NotNull Class<?> selfClass){
        if (Modifier.isAbstract(selfClass.getModifiers()) || Modifier.isInterface(selfClass.getModifiers())){return;}
        XposedBridge.hookAllConstructors(selfClass,returnNullAndRegisterReturn);
        for (Method m:selfClass.getDeclaredMethods()){
            disableMethod_random(m,selfClass);
        }
    }
    public static void listenClass(@NotNull Class<?> selfClass){
        if (Modifier.isAbstract(selfClass.getModifiers()) || Modifier.isInterface(selfClass.getModifiers())){return;}
        for (Method m:selfClass.getDeclaredMethods()){
            listenMethod(m,selfClass);
        }
    }

    public static void listenMethod(@NotNull Method m,@NotNull Class<?> selfClass){
        XposedBridge.hookMethod(m, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                LoggerLog(new Exception("listening method: "+m.getName()+" "+selfClass.getName()));
            }

        });
    }
}
