package com.linearity.utils.ListenerUtils;

import static com.linearity.utils.LoggerUtils.LoggerLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class ListenerHashMap<K, V> extends HashMap<K, V> {
    public static final boolean useLogger = true;
    public static final boolean useFullLogger = false;

    @Nullable
    @Override
    public V get(@Nullable Object key) {
        V result = super.get(key);
        if (useLogger){
            if (useFullLogger){
                LoggerLog("[linearity-ListenerHashMap]",new Exception( "getting " + key + " " + result));
            }else {

                LoggerLog("[linearity-ListenerHashMap]", "getting " + key + " " + result);
            }
        }
        return result;
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        if (useLogger){
            if (useFullLogger){
                LoggerLog("[linearity-ListenerHashMap]",new Exception( "putting " + key + " " + value));
            }else {

                LoggerLog("[linearity-ListenerHashMap]", "putting " + key + " " + value);
            }
        }
        return super.put(key, value);
    }

    @Nullable
    @Override
    public V getOrDefault(@Nullable Object key, @Nullable V defaultValue) {
        V result = super.getOrDefault(key, defaultValue);
        if (useLogger){
            if (useFullLogger){
                LoggerLog("[linearity-ListenerHashMap]",new Exception( "getOrDefault " + key + " " + result));
            }else {

                LoggerLog("[linearity-ListenerHashMap]", "getOrDefault " + key + " " + result);
            }
        }
        return result;
    }

    @NonNull
    @Override
    public Object clone() {
        if (useLogger){
            if (useFullLogger){
                LoggerLog("[linearity-ListenerHashMap]",new Exception( "cloning self"));
            }else {

                LoggerLog("[linearity-ListenerHashMap]", "cloning self");
            }
        }
        return super.clone();
    }

}
