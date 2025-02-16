package com.linearity.utils.Wrappers;

import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexAndObjectInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.util.Pair;

import com.linearity.utils.SimpleExecutor;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class WrapperCore {

    public static @NonNull SimpleExecutor getSimpleExecutorForWrapper(Class<?> wrapClass){
        SimpleExecutor result = simpleExecutorForWrapper_Map.get(wrapClass);
        if (result == null){
            LoggerLog("wrapper for " + wrapClass +" is null!");
        }
        return result;
    }
    public static @NonNull SimpleExecutor getSimpleExecutorForUnwrapper(Class<?> wrapClass){
        SimpleExecutor result = simpleExecutorForUnwrapper_Map.get(wrapClass);
        if (result == null){
            LoggerLog("unwrapper for " + wrapClass +" is null!");
        }
        return result;
    }
    public static @NonNull SimpleExecutor getSimpleExecutorForWrapperNoRegister(Class<?> wrapClass){
        SimpleExecutor result = simpleExecutorForWrapperNoRegister_Map.get(wrapClass);
        if (result == null){
            LoggerLog("wrapperNoRegister for " + wrapClass +" is null!");
        }
        return result;
    }
    static final Map<Class<?>,Function<?,?>> toWrapMap = new HashMap<>();
    static final Map<Class<?>,Map<Object,Object>> wrappeds = new HashMap<>();
    static final Map<Class<?>,SimpleExecutor> simpleExecutorForWrapper_Map = new HashMap<>();
    static final Map<Class<?>,SimpleExecutor> simpleExecutorForWrapperNoRegister_Map = new HashMap<>();
    static final Map<Class<?>,SimpleExecutor> simpleExecutorForUnwrapper_Map = new HashMap<>();
    public static <T> void  registerFunctionForWrapper(Class<T> classToWrap,Function<T,T> wrapperConstructor){
        toWrapMap.put(classToWrap,wrapperConstructor);
        registerSimpleExecutorForWrapper(classToWrap);
        registerSimpleExecutorForWrapperNoRegister(classToWrap);
        registerSimpleExecutorForUnwrapper(classToWrap);
    }
    static <T> @NonNull Function<T,T> functionForWrapper(Class<T> classToWrap){
        Function<T,T> result = (Function<T, T>) toWrapMap.get(classToWrap);
        if (result == null){
            LoggerLog(new Exception("wrapper not registered:" +classToWrap));
        }
        return result;
    }
    static <T> void registerSimpleExecutorForWrapper(Class<T> wrapClass){
        simpleExecutorForWrapper_Map.put(wrapClass,param -> {
            Pair<Integer, T> toWrapPair = findClassIndexAndObjectInArgs(param.args,wrapClass);
            T toWrap = toWrapPair.second;

            if (toWrap == null){
                LoggerLog("interface not found");
                return;
            }
            param.args[toWrapPair.first] = wrapObject(wrapClass,toWrap);
            showBefore.simpleExecutor.execute(param);
        });
    }
    static <T> void registerSimpleExecutorForWrapperNoRegister(Class<T> wrapClass){
        simpleExecutorForWrapperNoRegister_Map.put(wrapClass,param -> {
            Pair<Integer, T> toWrapPair = findClassIndexAndObjectInArgs(param.args,wrapClass);
            T toWrap = toWrapPair.second;
            if (toWrap == null){
                LoggerLog("interface not found");
                showBefore.simpleExecutor.execute(param);
                return;
            }
            param.args[toWrapPair.first] = wrapObjectNoRegister(wrapClass,toWrap);
            showBefore.simpleExecutor.execute(param);
        });
    }
    static <T> void registerSimpleExecutorForUnwrapper(Class<T> wrapClass){
        simpleExecutorForUnwrapper_Map.put(wrapClass,param -> {
            Pair<Integer, T> toWrapPair = findClassIndexAndObjectInArgs(param.args,wrapClass);
            T toWrap = toWrapPair.second;

            if (toWrap == null){
                return;
            }
            param.args[toWrapPair.first] = unwrapObject(wrapClass,toWrap);
            showBefore.simpleExecutor.execute(param);
        });
    }
    static <T>  T wrapObject(Class<T> wrapClass,T toWrap){
        Map<T,T> wrapMapForClass = (Map<T, T>) wrappeds.getOrDefault(wrapClass,null);
        if (wrapMapForClass == null){
            wrapMapForClass = new HashMap<>();
            wrappeds.put(wrapClass,(Map<Object, Object>) wrapMapForClass);
        }
        if (wrapMapForClass.containsKey(toWrap)){
            T wrapResult = wrapMapForClass.get(toWrap);
            if (wrapResult != null){
                return wrapResult;//object already wrapped
            }
        }
        if (wrapMapForClass.containsValue(toWrap)){
            return toWrap;//object is already a wrapped class
        }
        T wrapResult = functionForWrapper(wrapClass).apply(toWrap);//wrap
        wrapMapForClass.put(toWrap,wrapResult);
        return wrapResult;
    }
    static <T> T unwrapObject(Class<T> wrapClass,T wrapped){
        Map<T,T> wrapMapForClass = (Map<T, T>) wrappeds.getOrDefault(wrapClass,null);
        if (wrapMapForClass == null){
            wrapMapForClass = new HashMap<>();
            wrappeds.put(wrapClass,(Map<Object, Object>) wrapMapForClass);
        }
        T result = wrapMapForClass.remove(wrapped);
        if (result == null){
            return wrapped;
        }
        return result;
    }
    static <T>  T wrapObjectNoRegister(Class<T> wrapClass,T toWrap){
        T wrapResult = functionForWrapper(wrapClass).apply(toWrap);//wrap
        return wrapResult;
    }
}
