package com.linearity.datservicereplacement.androidhooking;

import static com.linearity.datservicereplacement.ReturnIfNonSys.findArgByClassInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.ModifyThrowable.cleanStackTrace;

import android.os.Parcel;

import com.linearity.utils.SimpleExecutor;

public class HookParcel {

    public static void doHook(){
        classesAndHooks.put(Parcel.class.getName(),HookParcel::hookParcel);
    }
    private static void hookParcel(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"writeException",(SimpleExecutor)param -> {
            Exception e = findArgByClassInArgs(param.args,Exception.class);
            if (e != null){
                cleanStackTrace(e);
            }
        },noSystemChecker);
    }
}
