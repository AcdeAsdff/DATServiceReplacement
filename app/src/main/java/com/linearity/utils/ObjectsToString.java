package com.linearity.utils;

import android.app.ActivityManager;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class ObjectsToString {
    
    
    public static String convert(Object o){
        if (o == null){
            return "null";
        }

        if (o.getClass().isArray()) {
            return convertArray(o);
        }
        else if (o instanceof Collection) {
            StringBuilder sb = new StringBuilder();
            for (Object item : (Collection<?>) o) {
                sb.append(convert(item)).append('\n');
            }
            return sb.toString();
        }
        else if (o instanceof ActivityManager.RunningAppProcessInfo){
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) o;
            return "RunningAppProcessInfo: " +
                    info.uid + "\n\t\t\t" +
                    Arrays.deepToString(info.pkgList) + "\n\t\t\t" +
                    info.processName + "\n\t\t\t";
        }
        else if (o instanceof ActivityManager.RunningServiceInfo){

            ActivityManager.RunningServiceInfo info = (ActivityManager.RunningServiceInfo) o;
            return "RunningServiceInfo: " +
                    info.uid + "\n\t\t\t" +
                    info.service + "\n\t\t\t" +
                    info.clientPackage + "\n\t\t\t";
        }
        else {
            return String.valueOf(o);
        }
    }

    private static String convertArray(Object arr){
        assert arr != null;
        assert arr.getClass().isArray();
        if (arr instanceof long[]){
            return Arrays.toString((long[]) arr);
        }
        if (arr instanceof int[]){
            return Arrays.toString((int[]) arr);
        }
        if (arr instanceof short[]){
            return Arrays.toString((short[]) arr);
        }
        if (arr instanceof double[]){
            return Arrays.toString((double[]) arr);
        }
        if (arr instanceof float[]){
            return Arrays.toString((float[]) arr);
        }
        if (arr instanceof char[]){
            return Arrays.toString((char[]) arr);
        }
        if (arr instanceof byte[]){
            return Arrays.toString((byte[]) arr);
        }
        if (arr instanceof boolean[]){
            return Arrays.toString((boolean[]) arr);
        }
        return Arrays.toString((Object[])arr);
    }
}
