package com.linearity.datservicereplacement;

import android.location.LocationRequest;
import android.net.NetworkRequest;
import android.os.UserHandle;
import android.view.inputmethod.InputMethodSubtype;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XposedHelpers;

/**
 * put static Class,Methods balabala..... here
 */
public class SomeClasses {
    public static Class<?> PackageStateInternalClass;
    public static Class<?> SharedUserSettingClass;
    public static Class<?> PackageSettingClass;
    public static Class<?> WatchedSparseIntArrayClass;
    public static Class<?> ComputerEngineClass;
    public static Class<?> msgSamplingCOnfigClass;
    public static Class<?> WatchedArrayMapClass;
    public static Class<?> UnsafeClass;
    public static Class<?> NetworkStateClass = XposedHelpers.findClass("android.net.NetworkState", NetworkRequest.class.getClassLoader());
    public static Class<?> InputMethodSubtypeArrayClass = XposedHelpers.findClass(
            "android.view.inputmethod.InputMethodSubtypeArray",
            InputMethodSubtype.class.getClassLoader()
    );
    public static Class<?> LocationResultClass = XposedHelpers.findClass(
            "android.location.LocationResult",
            LocationRequest.class.getClassLoader()
    );


    public static boolean CanUseUnsafe = false;
    public static Method ComputerEngine_isKnownIsolatedComputeApp;
    public static Method ComputerEngineSettings_getIsolatedOwner;
    public static Method ComputerEngine_resolveInternalPackageNameInternalLocked;
    public static Method UserHandle_getAppId_static = XposedHelpers.findMethodExact(UserHandle.class,"getAppId",int.class);

    public static Field LocationResult_mLocations = XposedHelpers.findField(LocationResultClass,"mLocations");
}
