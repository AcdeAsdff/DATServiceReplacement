package com.linearity.datservicereplacement.androidhooking.com.android.server.powerstats;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import com.linearity.utils.NotFinished;

@NotFinished
public class HookIPowerStatsService {


    public static void doHook(){
        classesAndHooks.put("com.android.server.powerstats.PowerStatsHALWrapper$PowerStatsHAL20WrapperImpl", HookIPowerStatsService::HookIPowerStatsHALWrapper);
        classesAndHooks.put("com.android.server.powerstats.PowerStatsHALWrapper$PowerStatsHAL10WrapperImpl", HookIPowerStatsService::HookIPowerStatsHALWrapper);
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
