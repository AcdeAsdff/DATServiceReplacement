package com.linearity.datservicereplacement.PowerManager;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE;

import android.os.Bundle;

import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutorWithMode;

import java.util.concurrent.CompletableFuture;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

@NotFinished
public class HookIPowerStatsService {


    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){
        Class<?> hookClass;
//        hookClass = XposedHelpers.findClassIfExists("com.android.server.powerstats.PowerStatsService",lpparam.classLoader);
//        if (hookClass != null){
//            hookAllMethodsWithCache_Auto(hookClass,"readEnergyMeterAsync",new SimpleExecutorWithMode(MODE_BEFORE,param->{
//                CompletableFuture<EnergyMeasurement[]>
//            }))
//        }
        hookClass = XposedHelpers.findClassIfExists("com.android.server.powerstats.PowerStatsHALWrapper$PowerStatsHAL20WrapperImpl",lpparam.classLoader);
        if (hookClass != null){
            HookIPowerStatsHALWrapper(hookClass);
        }
        hookClass = XposedHelpers.findClassIfExists("com.android.server.powerstats.PowerStatsHALWrapper$PowerStatsHAL10WrapperImpl",lpparam.classLoader);
        if (hookClass != null){
            HookIPowerStatsHALWrapper(hookClass);
        }
    }

    public static void HookIPowerStatsHALWrapper(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getPowerEntityInfo",null);
        hookAllMethodsWithCache_Auto(hookClass,"getStateResidency",null);
        hookAllMethodsWithCache_Auto(hookClass,"getEnergyConsumerInfo",null);
        hookAllMethodsWithCache_Auto(hookClass,"getEnergyConsumed",null);
        hookAllMethodsWithCache_Auto(hookClass,"getEnergyMeterInfo",null);
        hookAllMethodsWithCache_Auto(hookClass,"readEnergyMeter",null);
    }
}
