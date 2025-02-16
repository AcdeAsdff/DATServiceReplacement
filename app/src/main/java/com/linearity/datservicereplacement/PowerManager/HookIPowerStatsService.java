package com.linearity.datservicereplacement.PowerManager;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import com.linearity.datservicereplacement.ContentProviders.HookSettingsProvider;
import com.linearity.utils.NotFinished;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

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
