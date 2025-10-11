package com.linearity.datservicereplacement.androidhooking.com.android.server.location;

import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.getLocationByCurrentTimestamp;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.getLocationsByCurrentTimestamp;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.Wrappers.LocationListenerWrappers.registerLocationListenerWrappers;
import static com.linearity.utils.Wrappers.WrapperCore.getSimpleExecutorForUnwrapper;
import static com.linearity.utils.Wrappers.WrapperCore.getSimpleExecutorForWrapper;

import android.location.IGnssAntennaInfoListener;
import android.location.IGnssMeasurementsListener;
import android.location.IGnssNavigationMessageListener;
import android.location.IGnssNmeaListener;
import android.location.IGnssStatusListener;
import android.location.Location;

import com.linearity.utils.SimpleExecutor;

public class HookGNSS {
    public static void doHook(){
        registerLocationListenerWrappers();
        if (HookLocationManager.hookListeners){
            classesAndHooks.put("com.android.server.location.gnss.GnssManagerService", HookGNSS::hookGnssManagerService);
            classesAndHooks.put("com.android.server.location.gnss.hal.GnssNative", HookGNSS::hookGnssNative);
        }

        classesAndHooks.put("com.android.server.location.gnss.GnssManagerService", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.gnss.hal.GnssNative", HookLocationManager::listenLocationRelatedClass);

    }

    public static void hookGnssManagerService(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"onSystemReady",null);
        hookAllMethodsWithCache_Auto(hookClass,"getGnssLocationProvider",null);//GnssLocationProvider
//        hookAllMethodsWithCache_Auto(hookClass,"setAutomotiveGnssSuspended",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isAutomotiveGnssSuspended",true);
//    hookAllMethodsWithCache_Auto(hookClass,"getGnssGeofenceProxy",IGpsGeofenceHardware);
//        hookAllMethodsWithCache_Auto(hookClass,"getGnssYearOfHardware",0);
//        hookAllMethodsWithCache_Auto(hookClass,"getGnssHardwareModelName",String);
        hookAllMethodsWithCache_Auto(hookClass,"getGnssCapabilities",null);//GnssCapabilities
        hookAllMethodsWithCache_Auto(hookClass,"getGnssAntennaInfos",EMPTY_ARRAYLIST);//List<GnssAntennaInfo>
        hookAllMethodsWithCache_Auto(hookClass,"getGnssBatchSize",0);
        hookAllMethodsWithCache_Auto(hookClass,"registerGnssStatusCallback",getSimpleExecutorForWrapper(IGnssStatusListener.class),getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterGnssStatusCallback",getSimpleExecutorForUnwrapper(IGnssStatusListener.class),noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"registerGnssNmeaCallback",getSimpleExecutorForWrapper(IGnssNmeaListener.class),getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterGnssNmeaCallback",getSimpleExecutorForUnwrapper(IGnssNmeaListener.class),noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"addGnssMeasurementsListener",getSimpleExecutorForWrapper(IGnssMeasurementsListener.class),getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"removeGnssMeasurementsListener",getSimpleExecutorForUnwrapper(IGnssMeasurementsListener.class),noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"injectGnssMeasurementCorrections",null);
        hookAllMethodsWithCache_Auto(hookClass,"addGnssNavigationMessageListener",getSimpleExecutorForWrapper(IGnssNavigationMessageListener.class),getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"removeGnssNavigationMessageListener",getSimpleExecutorForUnwrapper(IGnssNavigationMessageListener.class),noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"addGnssAntennaInfoListener",getSimpleExecutorForWrapper(IGnssAntennaInfoListener.class),getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"removeGnssAntennaInfoListener",getSimpleExecutorForUnwrapper(IGnssAntennaInfoListener.class),noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"addProviderRequestListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeProviderRequestListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dump",null);
    }

    public static void hookGnssNative(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,
                "reportLocation",
                (SimpleExecutor)param
                        -> param.args[1] = getLocationByCurrentTimestamp(),
                noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"reportLocationBatch",
                (SimpleExecutor)param
                        -> param.args[0] = (Location[])getLocationsByCurrentTimestamp().toArray(),
                noSystemChecker);
    }

}
