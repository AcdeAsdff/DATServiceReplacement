package com.linearity.utils;

import static com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap.fakeObjects;

import android.os.Bundle;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;
import com.linearity.utils.FakeClass.java.util.CantUseArrayList;
import com.linearity.utils.FakeClass.java.util.CantUseEnumeration;
import com.linearity.utils.FakeClass.java.util.CantUseHashmap;
import com.linearity.utils.FakeClass.java.util.CantUseVector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class ReturnReplacements {
    public static final XC_MethodReplacement returnNull = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return null;
        }
    };
    public static final XC_MethodReplacement returnNullAndRegisterReturn = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            FakeReturnClassMap.registerInstance(param.thisObject.getClass(),param.thisObject);
            Field[] fields = param.thisObject.getClass().getDeclaredFields();
            for (Field f:fields){
                if (Modifier.isStatic(f.getModifiers())){continue;}
                if (fakeObjects.containsKey(f.getType().getName()) && fakeObjects.get(f.getType().getName()) != null){
                    XposedHelpers.setObjectField(param.thisObject,f.getName(),fakeObjects.get(f.getType().getName()).second);
                }
            }
            return null;
        }
    };
    public static final XC_MethodReplacement returnSelf = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            return param.thisObject;
        }
    };
    public static final XC_MethodReplacement returnFalse = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return false;
        }
    };
    public static final XC_MethodReplacement returnTrue = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return true;
        }
    };
    public static final XC_MethodReplacement returnIntegerZero = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return 0;
        }
    };
    public static final XC_MethodReplacement returnIntegerNegativeOne = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return 1;
        }
    };
    public static final XC_MethodReplacement returnByteArr114514 = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return byteArray114514;
        }
    };
    public static final XC_MethodReplacement returnIntegerOne = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return 1;
        }
    };
    public static final XC_MethodReplacement returnIntegerRandom = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return random.nextInt();
        }
    };
    public static final XC_MethodReplacement returnStringOne = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return "1";
        }
    };
    public static final XC_MethodReplacement returnIntegerMIN = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return -2147483648;
        }
    };
    public static final XC_MethodReplacement returnIntegerMAX = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return 2147483647;
        }
    };
    public static final XC_MethodReplacement returnShortZero = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return (short)0;
        }
    };
    public static final XC_MethodReplacement returnCharZero = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return '\0';
        }
    };
    public static final XC_MethodReplacement returnByteZero = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return (byte)0;
        }
    };
    public static final XC_MethodReplacement returnFloatZero = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return 0.f;
        }
    };
    public static final XC_MethodReplacement returnFloatRandom = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return random.nextFloat();
        }
    };
    public static final XC_MethodReplacement returnByteRandom = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return (byte)(random.nextInt(256)-128);
        }
    };
    public static final XC_MethodReplacement returnDoubleZero = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return 0.;
        }
    };
    public static final XC_MethodReplacement returnLongZero = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return 0L;
        }
    };
    public static final XC_MethodReplacement returnLongRandom = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return random.nextLong();
        }
    };
    public static final XC_MethodReplacement returnShortRandom = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return (short)(random.nextInt(65537)-32768);
        }
    };
    public static final XC_MethodReplacement returnCharRandom = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return (char)(random.nextInt(Integer.MAX_VALUE));
        }
    };
    public static final XC_MethodReplacement returnDoubleRandom = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return (random.nextDouble());
        }
    };
    public static final XC_MethodReplacement returnLongOne = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return 1L;
        }
    };
    public static final XC_MethodReplacement returnLongMIN = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return 0x8000000000000000L;
        }
    };
    public static final XC_MethodReplacement returnLongMAX = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return 0x7fffffffffffffffL;
        }
    };
    public static final XC_MethodReplacement returnRandomStr20 = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return new ExtendedRandom().nextString(20);
        }
    };
    public static final Bundle EmptyBundle = new Bundle();
    public static final XC_MethodReplacement returnEmptyBundle = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return EmptyBundle;
        }
    };
    public static final Map<String, Object> EmptyMap_String_Object = new HashMap<>();
    public static final XC_MethodReplacement returnEmptyMap_String_Object = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) {
            return EmptyMap_String_Object;
        }
    };
    public static final XC_MethodReplacement returnCantUseHashMap = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            return CantUseHashmap.INSTANCE;
        }
    };

    public static final XC_MethodReplacement returnCantUseArrayList = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            return CantUseArrayList.INSTANCE;
        }
    };

    public static final XC_MethodReplacement returnRandomUUID = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            return UUID.randomUUID();
        }
    };
    public static final XC_MethodReplacement returnRandomByteArray = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            byte[] result = new byte[random.nextInt()];
            for (int i=0;i<result.length;i++){
                result[i] = (byte) (random.nextInt(256)-128);
            }
            return result;
        }
    };
    public static final XC_MethodReplacement returnRandomIntegerArray = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            int[] result = new int[random.nextInt()];
            for (int i=0;i<result.length;i++){
                result[i] = random.nextInt(Integer.MAX_VALUE);
            }
            return result;
        }
    };
    public static final XC_MethodReplacement returnRandomLongArray = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            long[] result = new long[random.nextInt()];
            for (int i=0;i<result.length;i++){
                result[i] = random.nextLong();
            }
            return result;
        }
    };
    public static final XC_MethodReplacement returnRandomShortArray = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            short[] result = new short[random.nextInt()];
            for (int i=0;i<result.length;i++){
                result[i] = (short)(random.nextInt(65536)-32768);
            }
            return result;
        }
    };
    public static final XC_MethodReplacement returnRandomCharArray = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            char[] result = new char[random.nextInt()];
            for (int i=0;i<result.length;i++){
                result[i] = (char)(random.nextInt(Integer.MAX_VALUE));
            }
            return result;
        }
    };
    public static final XC_MethodReplacement returnRandomFloatArray = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            float[] result = new float[random.nextInt()];
            for (int i=0;i<result.length;i++){
                result[i] = random.nextFloat();
            }
            return result;
        }
    };
    public static final XC_MethodReplacement returnRandomDoubleArray = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            double[] result = new double[random.nextInt()];
            for (int i=0;i<result.length;i++){
                result[i] = random.nextDouble();
            }
            return result;
        }
    };
    public static final XC_MethodReplacement returnRandomBooleanArray = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            boolean[] result = new boolean[random.nextInt()];
            for (int i=0;i<result.length;i++){
                result[i] = random.nextBoolean();
            }
            return result;
        }
    };

    public static final XC_MethodReplacement returnCantUseVector = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            return new CantUseVector<>();
        }
    };

    public static final XC_MethodReplacement returnCantUseEnumeration = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            return CantUseEnumeration.INSTANCE;
        }
    };
    public static final XC_MethodReplacement returnRandomBoolean = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            return random.nextBoolean();
        }
    };

    public static final SecureRandom random = new SecureRandom();
    public static final byte[] byteArray114514 = new byte[]{
            (byte) (random.nextInt(256)-127),(byte) (random.nextInt(256)-127),(byte) (random.nextInt(256)-127),
            (byte) (random.nextInt(256)-127),(byte) (random.nextInt(256)-127),(byte) (random.nextInt(256)-127),};




    public static double randomSmallDouble(double max){
        double result = 0;
        for (int i=0;i<5;i++){
            max *= random.nextInt(10)*0.001;
            result += max;
        }
        result *= (random.nextBoolean()?-1:1);
        return result;
    }
    public static double randomSmallDouble(double max,Random random){
        double result = 0;
        for (int i=0;i<5;i++){
            max *= random.nextInt(10)*0.001;
            result += max;
        }
        result *= (random.nextBoolean()?-1:1);
        return result;
    }
}
