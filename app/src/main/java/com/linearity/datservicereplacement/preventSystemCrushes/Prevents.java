package com.linearity.datservicereplacement.preventSystemCrushes;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.annotation.SuppressLint;

import com.linearity.utils.SimpleExecutor;

public class Prevents {

    public static void doHook(){
        classesAndHooks.put("com.android.internal.util.Preconditions", Prevents::fixPreconditions);
    }


    @SuppressLint("DefaultLocale")
    public static void fixPreconditions(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass, "checkArgument",(SimpleExecutor)param -> {
            if (param.args[0] instanceof Float value){
                float lower = (float) param.args[1];
                float upper = (float) param.args[2];
                String valueName = (String) param.args[3];
                if (Float.isNaN(value)) {
                    throw new IllegalArgumentException(valueName + " must not be NaN");
                } else if (value < lower) {
                    LoggerLog(new IllegalArgumentException(
                            String.format(
                                    "%s:%f is out of range of [%f, %f] (too low)", valueName,value, lower, upper)));
                } else if (value > upper) {
                    LoggerLog(new IllegalArgumentException(
                            String.format(
                                    "%s:%f is out of range of [%f, %f] (too high)", valueName,value, lower, upper)));
                }
                param.setResult(Math.min(Math.max(lower,value),upper));
                return;
            }
            if (param.args[0] instanceof Double value){
                double lower = (double) param.args[1];
                double upper = (double) param.args[2];
                String valueName = (String) param.args[3];
                if (Double.isNaN(value)) {
                    throw new IllegalArgumentException(valueName + " must not be NaN");
                } else if (value < lower) {
                    LoggerLog(new IllegalArgumentException(
                            String.format(
                                    "%s:%f is out of range of [%f, %f] (too low)", valueName,value, lower, upper)));
                } else if (value > upper) {
                    LoggerLog(new IllegalArgumentException(
                            String.format(
                                    "%s:%f is out of range of [%f, %f] (too high)", valueName,value, lower, upper)));
                }
                param.setResult(Math.min(Math.max(lower,value),upper));
                return;
            }

            if (param.args[0] instanceof Integer value){
                int lower = (int) param.args[1];
                int upper = (int) param.args[2];
                String valueName = (String) param.args[3];
                if (value < lower) {
                    LoggerLog(new IllegalArgumentException(
                            String.format(
                                    "%s:%d is out of range of [%d, %d] (too low)", valueName, value, lower, upper)));
                } else if (value > upper) {
                    LoggerLog(new IllegalArgumentException(
                            String.format(
                                    "%s:%d is out of range of [%d, %d] (too high)", valueName, value, lower, upper)));
                }
                param.setResult(Math.min(Math.max(lower,value),upper));
                return;
            }

            if (param.args[0] instanceof Long value){
                long lower = (long) param.args[1];
                long upper = (long) param.args[2];
                String valueName = (String) param.args[3];
                if (value < lower) {
                    LoggerLog(new IllegalArgumentException(
                            String.format(
                                    "%s:%d is out of range of [%d, %d] (too low)", valueName, value, lower, upper)));
                } else if (value > upper) {
                    LoggerLog(new IllegalArgumentException(
                            String.format(
                                    "%s:%d is out of range of [%d, %d] (too high)", valueName, value, lower, upper)));
                }
                param.setResult(Math.min(Math.max(lower,value),upper));
                return;
            }
        },noSystemChecker);
    }
}
