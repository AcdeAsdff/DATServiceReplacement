package com.linearity.datservicereplacement;

import static com.linearity.utils.LoggerUtils.LoggerLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class LoadLibraryUtil {
    public static Method nativeLoadMethod = XposedHelpers.findMethodExact(Runtime.class,"nativeLoad",String.class,ClassLoader.class);

    public static void loadLibraryByPath(String path,ClassLoader classLoader){
        LoggerLog("loading library:"+path);
        try {
            if (!libExists(path)){
                LoggerLog(new FileNotFoundException(path + " not found!"));
                return;
            }
//            if (classLoader == null){
//                throw new RuntimeException("classloader null!");
//            }
            LoggerLog(nativeLoadMethod.invoke(null,path, classLoader));
        }catch (Exception e){
            LoggerLog(e);
        }
    }
    public static boolean libExists(String path){
        return new File(path).exists();
    }
}
