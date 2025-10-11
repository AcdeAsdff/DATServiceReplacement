package com.linearity.datservicereplacement.androidhooking.com.android.server.location;

import static android.telephony.CellInfo.CONNECTION_PRIMARY_SERVING;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.getLocationByCurrentTimestamp;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.getLocationResultByCurrentTimestamp;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.getLocationsByCurrentTimestamp;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.setLocationByCurrentTimestamp;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.setLocationResultByCurrentTimestamp;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.SomeClasses.LocationResultClass;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.isHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.listenServiceMap;
import static com.linearity.datservicereplacement.StartHook.registerServiceHook_map;
import static com.linearity.utils.GeometryUtils.distanceOnEarth;
import static com.linearity.utils.LocationUtils.CellInfoConstructor.constructCellIdentityLte;
import static com.linearity.utils.LocationUtils.CellInfoConstructor.constructCellInfoLte;
import static com.linearity.utils.LocationUtils.CellInfoConstructor.constructCellSignalStrengthLte;
import static com.linearity.utils.LocationUtils.CellInfoConstructor.fakeCellInfoConfig;
import static com.linearity.utils.LocationUtils.CellInfoConstructor.fakeCellSignalStrengthLte;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;
import static com.linearity.utils.Wrappers.LocationListenerWrappers.registerLocationListenerWrappers;
import static com.linearity.utils.Wrappers.WrapperCore.getSimpleExecutorForUnwrapper;
import static com.linearity.utils.Wrappers.WrapperCore.getSimpleExecutorForWrapper;
import static com.linearity.utils.Wrappers.WrapperCore.getSimpleExecutorForWrapperNoRegister;

import android.content.Context;
import android.location.IGnssAntennaInfoListener;
import android.location.IGnssMeasurementsListener;
import android.location.IGnssNavigationMessageListener;
import android.location.IGnssNmeaListener;
import android.location.IGnssStatusListener;
import android.location.ILocationCallback;
import android.location.ILocationListener;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationResult;
import android.os.SystemClock;
import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.CellSignalStrengthTdscdma;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.ClosedSubscriberGroupInfo;
import android.telephony.SignalStrength;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.AndroidFakes.Location.CallerIdentityUtils;
import com.linearity.datservicereplacement.ReturnIfNonSys;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.LocationUtils.CellIdentityWithLocation;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

@NotFinished
public class HookLocationManager {

    public static boolean hookListeners = false;
    public static void doHook(){
        registerLocationListenerWrappers();
        if (hookListeners){
            classesAndHooks.put("com.android.server.location.gnss.GnssLocationProvider", HookLocationManager::hookGnssLocationProvider);
            classesAndHooks.put("com.android.server.location.LocationManagerService", hookClass -> {
                hookLocation(hookClass);
                hookLocationManagerService(hookClass);
                hookILocationManager(hookClass);
            });
            classesAndHooks.put("com.android.server.location.provider.LocationProviderManager", hookClass -> {
                hookLocationProviderManager(hookClass);
                hookAbstractLocationProviderListener(hookClass);
            });
            classesAndHooks.put("com.android.server.location.provider.DelegateLocationProvider", HookLocationManager::hookAbstractLocationProviderListener);
            classesAndHooks.put("com.android.server.location.provider.PassiveLocationProvider", HookLocationManager::hookPassiveLocationProvider);
            classesAndHooks.put("com.android.server.location.provider.LocationProviderManager$LocationListenerTransport", HookLocationManager::hookLocationListenerTransport);
            classesAndHooks.put("com.android.server.location.provider.proxy.ProxyLocationProvider$Proxy", HookLocationManager::hookILocationProviderManager);
            classesAndHooks.put("com.android.server.location.provider.MockableLocationProvider", HookLocationManager::hookMockableLocationProvider);
            classesAndHooks.put("com.android.server.location.provider.MockableLocationProvider$ListenerWrapper", HookLocationManager::hookAbstractLocationProviderListener);
            listenServiceMap.put(Context.LOCATION_SERVICE, c -> {
                hookLocation(c);
                hookILocationManager(c);
            });
            hookLocationManager(LocationManager.class);
            hookLocationManager_ProviderPackage(LocationManager.class);
            classesAndHooks.put("android.location.LocationManager", HookLocationManager::hookLocationManager);
            classesAndHooks.put("android.location.LocationManager", HookLocationManager::hookLocationManager_ProviderPackage);
            classesAndHooks.put("android.location.LocationManager$LocationListenerTransport", HookLocationManager::hookILocationListener);
        }

        classesAndHooks.put("com.android.location.fused.FusedLocationProvider", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.gnss.GnssLocationProvider", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.gnss.GnssOverlayLocationProvider", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.gnss.GnssOverlayLocationProvider$GnssLocationListener", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.LocationManagerService", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.provider.LocationProviderManager", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.provider.DelegateLocationProvider", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.provider.StationaryThrottlingLocationProvider", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.provider.PassiveLocationProvider", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.provider.PassiveLocationProviderManager", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.provider.LocationProviderManager$LocationListenerTransport", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.provider.proxy.ProxyLocationProvider$Proxy", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.provider.MockableLocationProvider", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.android.server.location.provider.MockableLocationProvider$ListenerWrapper", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("com.google.android.location.settings.DrivingConditionProvider", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("android.location.LocationManager", HookLocationManager::listenLocationRelatedClass);
        classesAndHooks.put("android.location.LocationManager$ProviderPackage", HookLocationManager::listenLocationRelatedClass);
        hookPublishBinderService();
    }
    public static void hookPublishBinderService(){
        registerServiceHook_map.put(Context.LOCATION_SERVICE, c -> {
            if (hookListeners){
                hookLocationManager(c);
                hookLocationManagerService(c);
                hookILocationManager(c);
            }
            listenLocationRelatedClass(c);
            return null;
        });
    }

    public static void hookLocation(Class<?> hookClass){
        XC_MethodHook modifyCurrentLoc = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedHelpers.callMethod(param.args[2],"onLocation",LocationGetter.getLocationByCurrentTimestamp());
                param.setResult(null);
            }
        };
        ReturnIfNonSys.hookAllMethodsWithCache_Auto(hookClass, "getLastLocation", setLocationByCurrentTimestamp, getSystemChecker_PackageNameAt(2));
        ReturnIfNonSys.hookAllMethodsWithCache_Auto(hookClass, "getCurrentLocation", modifyCurrentLoc, getSystemChecker_PackageNameAt(3));
    }
    public static void hookLocationManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getLastKnownLocation",
                (SimpleExecutor)param->param.setResult(getLocationByCurrentTimestamp()),
                noSystemChecker);
    }

    public static List<Location> preparedLocations = new ArrayList<>();
    public static List<Pair<CellInfo,CellIdentityWithLocation>> cellTowers = new ArrayList<>();

    public static void hookLocationManager_ProviderPackage(Class<?> hookClass){
        XposedBridge.hookAllConstructors(hookClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                if (param.args[1] == null){
                    return;
                }
                hookILocationManager(param.args[1].getClass());
            }
        });
        hookAllMethodsWithCache_Auto(hookClass,"getService",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            Object result = param.getResult();
            if (result == null){
                return;
            }
            hookILocationManager(result.getClass());
        }),noSystemChecker);
    }
    public static void hookLocationListenerTransport(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"deliverOnLocationChanged",(SimpleExecutor)param -> {
            try {
                param.args[0] = LocationResult.create(getLocationsByCurrentTimestamp());
//                LocationResult_mLocations.set(param.args[0],getLocationsByCurrentTimestamp());
            }catch (Exception e){
                LoggerLog(e);
            }
        },noSystemChecker);
    }

    public static void hookILocationListener(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onLocationChanged",(SimpleExecutor)param -> {
            param.args[0] = getLocationsByCurrentTimestamp();
        },noSystemChecker);
    }
//    public static Set<Class<?>> ILocationManagersHooked = new HashSet<>();
    public static void hookLocationManagerService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"addLocationProviderManager",showBefore,noSystemChecker);
    }
    public static void hookILocationManager(Class<?> hookClass){
//        listenClassForNonSysUid(hookClass);
//        if (isHookedPoolRegistered(hookClass,ILocationManagersHooked)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"getLastLocation",setLocationByCurrentTimestamp);
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentLocation",getSimpleExecutorForWrapperNoRegister(ILocationCallback.class));
        hookAllMethodsWithCache_Auto(hookClass,"registerLocationListener",getSimpleExecutorForWrapper(ILocationListener.class),getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterLocationListener",getSimpleExecutorForUnwrapper(ILocationListener.class),noSystemChecker);

        hookAllMethodsWithCache_Auto(hookClass,"registerLocationPendingIntent",showBefore,getSystemChecker_PackageNameAt(3));
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterLocationPendingIntent",null);
        hookAllMethodsWithCache_Auto(hookClass,"injectLocation",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestListenerFlush",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestPendingIntentFlush",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestGeofence",null,getSystemChecker_PackageNameAt(2));
//        hookAllMethodsWithCache_Auto(hookClass,"removeGeofence",null);
        hookAllMethodsWithCache_Auto(hookClass,"isGeocodeAvailable",true);
        hookAllMethodsWithCache_Auto(hookClass,"reverseGeocode",null);
        hookAllMethodsWithCache_Auto(hookClass,"forwardGeocode",null);
//    hookAllMethodsWithCache_Auto(hookClass,"getGnssCapabilities",GnssCapabilities);
        hookAllMethodsWithCache_Auto(hookClass,"getGnssYearOfHardware",0);
//    hookAllMethodsWithCache_Auto(hookClass,"getGnssHardwareModelName",String);
        hookAllMethodsWithCache_Auto(hookClass,"getGnssAntennaInfos",EMPTY_ARRAYLIST);//List<GnssAntennaInfo>
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
        hookAllMethodsWithCache_Auto(hookClass,"getGnssBatchSize",0);
        hookAllMethodsWithCache_Auto(hookClass,"startGnssBatch",getSimpleExecutorForWrapper(ILocationListener.class));
        hookAllMethodsWithCache_Auto(hookClass,"flushGnssBatch",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopGnssBatch",getSimpleExecutorForUnwrapper(ILocationListener.class),noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"hasProvider",true);
//        List<String> providerArrayList = new ArrayList<>();
//        providerArrayList.add("gps");
//        hookAllMethodsWithCache_Auto(hookClass,"getAllProviders",providerArrayList);//List<String>
//        hookAllMethodsWithCache_Auto(hookClass,"getProviders",providerArrayList);//List<String>
//        hookAllMethodsWithCache_Auto(hookClass,"getBestProvider","gps");//String
        hookAllMethodsWithCache_Auto(hookClass,"getProviderProperties",null);//ProviderProperties
        hookAllMethodsWithCache_Auto(hookClass,"isProviderPackage",true);
        hookAllMethodsWithCache_Auto(hookClass,"getProviderPackages",EMPTY_ARRAYLIST);//List<String>
        hookAllMethodsWithCache_Auto(hookClass,"setExtraLocationControllerPackage",null);
        hookAllMethodsWithCache_Auto(hookClass,"getExtraLocationControllerPackage",EMPTY_ARRAYLIST);//String
        hookAllMethodsWithCache_Auto(hookClass,"setExtraLocationControllerPackageEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isExtraLocationControllerPackageEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isProviderEnabledForUser",true);
        hookAllMethodsWithCache_Auto(hookClass,"isLocationEnabledForUser",true);
        hookAllMethodsWithCache_Auto(hookClass,"setLocationEnabledForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"isAdasGnssLocationEnabledForUser",true);
        hookAllMethodsWithCache_Auto(hookClass,"setAdasGnssLocationEnabledForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"isAutomotiveGnssSuspended",true);
        hookAllMethodsWithCache_Auto(hookClass,"setAutomotiveGnssSuspended",null);
        hookAllMethodsWithCache_Auto(hookClass,"addTestProvider",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeTestProvider",null);
        hookAllMethodsWithCache_Auto(hookClass,"setTestProviderLocation",null);
        hookAllMethodsWithCache_Auto(hookClass,"setTestProviderEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"getGnssTimeMillis",null);//LocationTime
        hookAllMethodsWithCache_Auto(hookClass,"sendExtraCommand",null);
//    hookAllMethodsWithCache_Auto(hookClass,"getBackgroundThrottlingWhitelist",String[]);
//    hookAllMethodsWithCache_Auto(hookClass,"getIgnoreSettingsAllowlist",PackageTagsList);
//    hookAllMethodsWithCache_Auto(hookClass,"getAdasAllowlist",PackageTagsList);
    }

    public static void hookLocationProviderManager(Class<?> hookClass){
//        listenClass(hookClass);
//        hookAllMethodsWithCache_Auto(hookClass,"startManager",null);
//        hookAllMethodsWithCache_Auto(hookClass,"stopManager",null);
////        hookAllMethodsWithCache_Auto(hookClass,"getName",String);
////    hookAllMethodsWithCache_Auto(hookClass,"getState",AbstractLocationProvider);
////    hookAllMethodsWithCache_Auto(hookClass,"getProviderIdentity",CallerIdentity);
////    hookAllMethodsWithCache_Auto(hookClass,"getProperties",ProviderProperties);
//        hookAllMethodsWithCache_Auto(hookClass,"hasProvider",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isEnabled",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isVisibleToCaller",true);
//        hookAllMethodsWithCache_Auto(hookClass,"addEnabledListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeEnabledListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"addProviderRequestListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeProviderRequestListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setRealProvider",null);
        hookAllMethodsWithCache_Auto(hookClass,"setMockProvider",(SimpleExecutor)param -> {
            XposedHelpers.callMethod(param.thisObject,"setRealProvider",param.args[0]);
            param.setResult(null);
        },noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"setMockProviderAllowed",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setMockProviderLocation",null);
        hookAllMethodsWithCache_Auto(hookClass,"getLastLocation",setLocationByCurrentTimestamp,
                noSystemChecker);
////    hookAllMethodsWithCache_Auto(hookClass,"calculateLastLocationRequest",LastLocationRequest);
        hookAllMethodsWithCache_Auto(hookClass,"getLastLocationUnsafe",setLocationByCurrentTimestamp,noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"injectLastLocation",null);
        hookAllMethodsWithCache_Auto(hookClass,"setLastLocation",(SimpleExecutor)param -> {
            param.args[0] = getLocationByCurrentTimestamp();
        },noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentLocation",getSimpleExecutorForWrapperNoRegister(ILocationCallback.class),
                noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"sendExtraCommand",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerLocationRequest",getSimpleExecutorForWrapper(ILocationListener.class), CallerIdentityUtils.getSystemChecker_CallerIdentityAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterLocationRequest",getSimpleExecutorForUnwrapper(ILocationListener.class),noSystemChecker );
//        hookAllMethodsWithCache_Auto(hookClass,"onRegister",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onUnregister",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onRegistrationAdded",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onRegistrationReplaced",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onRegistrationRemoved",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerWithService",showBefore,noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"reregisterWithService",true);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterWithService",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setProviderRequest",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isActive",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isActive",true);
//        hookAllMethodsWithCache_Auto(hookClass,"mergeRegistrations",ProviderRequest);
//        hookAllMethodsWithCache_Auto(hookClass,"calculateRequestDelayMillis",0L);
//        hookAllMethodsWithCache_Auto(hookClass,"onUserChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onLocationUserSettingsChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onLocationEnabledChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onScreenInteractiveChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onBackgroundThrottlePackageWhitelistChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onBackgroundThrottleIntervalChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onLocationPowerSaveModeChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onAppForegroundChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onAdasAllowlistChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onIgnoreSettingsWhitelistChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onLocationPackageBlacklistChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onLocationPermissionsChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onLocationPermissionsChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onPackageReset",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isResetableForPackage",true);
//        hookAllMethodsWithCache_Auto(hookClass,"onStateChanged",null);


        hookAllMethodsWithCache_Auto(hookClass,
                "processReportedLocation",
                setLocationResultByCurrentTimestamp,
                noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"onUserStarted",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onUserStopped",null);
//        hookAllMethodsWithCache_Auto(hookClass,"onEnabledChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,
                "getPermittedLocation",
                setLocationByCurrentTimestamp,
                noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,
                "getPermittedLocationResult",
                setLocationResultByCurrentTimestamp,
                noSystemChecker);
//        hookAllMethodsWithCache_Auto(hookClass,"dump",null);
//    hookAllMethodsWithCache_Auto(hookClass,"getServiceState",String);
    }
    public static void hookIGnssMeasurementsListener(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onGnssMeasurementsReceived",null);
        hookAllMethodsWithCache_Auto(hookClass,"onStatusChanged",null);
    }

    public static class LocationGetter{
        @NonNull
        public static Location getLocationByCurrentTimestamp(){

//            LoggerLog(new Exception("gettingLoc"));
            long timeStamp = System.currentTimeMillis();
            timeStamp /= 500;//every 0.5 second
            Location result = preparedLocations.get((int) (timeStamp % preparedLocations.size()));
            return updateLocation(result);
        }
        @NonNull
        public static ArrayList<Location> getLocationsByCurrentTimestamp(){
//            LoggerLog(new Exception("gettingLoc"));
            long timeStamp = System.currentTimeMillis();
            ArrayList<Location> results = new ArrayList<>();
            for (int i=0;i<3;i+=1){
                long tempTime = timeStamp - 500*i;
                tempTime /= 500;//every 0.5 second
                Location result = preparedLocations.get((int) (tempTime % preparedLocations.size()));
                result = updateLocation(result);
                results.add(result);
            }
            results.sort((o1,o2) -> -Long.compare(o1.getElapsedRealtimeMillis(),o2.getElapsedRealtimeMillis()));
            return results;
        }

        public static Object getLocationResultByCurrentTimestamp(){
            return XposedHelpers.newInstance(LocationResultClass,new Class[]{ArrayList.class},getLocationsByCurrentTimestamp());
        }

        public static void initLocations(){
            addLocation(114.334403,30.372249,120.844535,1.000000f);
            addLocation(114.334403,30.372343,120.844535,1.000000f);
            addLocation(114.334403,30.372437,120.844535,1.000000f);
            addLocation(114.334403,30.372531,120.844535,1.000000f);
            addLocation(114.334403,30.372625,120.844535,1.000000f);
            addLocation(114.334403,30.372718,120.844535,1.000000f);
            addLocation(114.334403,30.372812,120.844535,1.000000f);
            addLocation(114.334403,30.372906,120.844535,1.000000f);
            addLocation(114.334403,30.373000,120.844535,1.000000f);
            addLocation(114.334403,30.373094,120.844535,1.000000f);
            addLocation(114.334403,30.373188,120.844535,1.000000f);
            addLocation(114.335313,30.373188,120.844535,1.000000f);
            addLocation(114.335268,30.373469,120.844535,19.000000f);
            addLocation(114.335139,30.373723,120.844535,37.000000f);
            addLocation(114.334938,30.373924,120.844535,55.000000f);
            addLocation(114.334684,30.374053,120.844535,73.000000f);
            addLocation(114.334403,30.374098,120.844535,91.000000f);
            addLocation(114.334122,30.374053,120.844535,109.000000f);
            addLocation(114.333868,30.373924,120.844535,127.000000f);
            addLocation(114.333667,30.373723,120.844535,145.000000f);
            addLocation(114.333538,30.373469,120.844535,163.000000f);
            addLocation(114.333493,30.373188,120.844535,181.000000f);
            addLocation(114.333493,30.372249,120.844535,181.000000f);
            addLocation(114.333493,30.372343,120.844535,181.000000f);
            addLocation(114.333493,30.372437,120.844535,181.000000f);
            addLocation(114.333493,30.372531,120.844535,181.000000f);
            addLocation(114.333493,30.372625,120.844535,181.000000f);
            addLocation(114.333493,30.372718,120.844535,181.000000f);
            addLocation(114.333493,30.372812,120.844535,181.000000f);
            addLocation(114.333493,30.372906,120.844535,181.000000f);
            addLocation(114.333493,30.373000,120.844535,181.000000f);
            addLocation(114.333493,30.373094,120.844535,181.000000f);
            addLocation(114.333493,30.373188,120.844535,181.000000f);
            addLocation(114.333493,30.372249,120.844535,181.000000f);
            addLocation(114.333538,30.371968,120.844535,199.000000f);
            addLocation(114.333667,30.371714,120.844535,217.000000f);
            addLocation(114.333868,30.371513,120.844535,235.000000f);
            addLocation(114.334122,30.371384,120.844535,253.000000f);
            addLocation(114.334403,30.371339,120.844535,271.000000f);
            addLocation(114.334684,30.371384,120.844535,289.000000f);
            addLocation(114.334938,30.371513,120.844535,307.000000f);
            addLocation(114.335139,30.371714,120.844535,325.000000f);
            addLocation(114.335268,30.371968,120.844535,343.000000f);
            addLocation(114.335313,30.372249,120.844535,1.000000f);
        }

        /**
         * auto-generate it
         */
        public static void initCellTowers(){
            addLTECellTower(114.3284,30.381,26280730,441,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3277,30.3822,26347290,441,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3258,30.3797,26280732,441,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3247,30.3772,26253084,65,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3304,30.3756,26293291,402,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3299,30.3762,26578234,34,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3294,30.3748,26819900,402,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3296,30.3757,26293276,65,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3294,30.3757,26819868,65,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3282,30.3745,26253099,441,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3282,30.3747,26819884,433,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3318,30.3716,26291516,402,62475,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3303,30.3756,26293275,253,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.332,30.3737,26819866,402,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3321,30.3729,26291484,65,62475,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3319,30.3735,26819882,402,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3292,30.3707,26819867,442,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3314,30.3694,26898458,402,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3309,30.3684,26432026,402,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3266,30.3748,26933050,441,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3264,30.3758,26578236,266,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3262,30.3761,256331064,306,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3228,30.3751,26553370,442,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3247,30.3769,150230323,441,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3266,30.3779,256352562,441,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3276,30.3791,150230325,156,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.329,30.381,26347291,441,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3291,30.3812,26347547,406,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3291,30.3816,26347546,441,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3258,30.3808,27008538,441,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3202,30.3785,27008540,441,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3196,30.3784,26280748,441,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3173,30.3785,26498074,441,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3168,30.3763,26498091,441,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3168,30.3751,26498075,441,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3166,30.3761,26553372,441,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3174,30.372,26621466,442,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3214,30.3708,26842939,442,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3175,30.3727,27008539,442,28958,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.318,30.3727,150400304,407,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3181,30.3726,150232115,417,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3182,30.3725,26445867,427,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3194,30.371,26842908,442,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3211,30.3711,26842907,442,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.325,30.3702,26933020,442,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3255,30.3702,150231088,487,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3262,30.3702,26933019,37,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3282,30.3702,26898460,442,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.329,30.369,26898459,442,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
            addLTECellTower(114.3289,30.3696,27010418,442,28931,3745,new int[]{8},10000,"460","1","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);
        }
        public static void addLocation(double mLatitude,double mLongitude,double mAltitude,float mBearing){
            Location l = new Location(LocationManager.GPS_PROVIDER);
            XposedHelpers.setLongField(l,"mTimeMs",System.currentTimeMillis());
            XposedHelpers.setLongField(l,"mElapsedRealtimeNs", SystemClock.elapsedRealtimeNanos());
            XposedHelpers.setDoubleField(l,"mElapsedRealtimeUncertaintyNs", 1000.);
            XposedHelpers.setIntField(l,"mFieldsMask",(1<<8)+(1<<7)+(1<<6)+(1<<5)+(1<<3)+(1<<2)+(1<<1)+(1));
            XposedHelpers.setDoubleField(l,"mLatitudeDegrees", mLatitude);
            XposedHelpers.setDoubleField(l,"mLongitudeDegrees", mLongitude);
            XposedHelpers.setDoubleField(l,"mAltitudeMeters", mAltitude);
            XposedHelpers.setDoubleField(l,"mMslAltitudeMeters", mAltitude);
            XposedHelpers.setFloatField(l,"mMslAltitudeAccuracyMeters", (float)mAltitude/100);
            XposedHelpers.setFloatField(l,"mHorizontalAccuracyMeters", 0.03f);
            XposedHelpers.setFloatField(l,"mSpeedMetersPerSecond", 5.0f);
            XposedHelpers.setFloatField(l,"mSpeedAccuracyMetersPerSecond", 5.0f/100);
            XposedHelpers.setFloatField(l,"mBearingAccuracyDegrees", mBearing/100);
            XposedHelpers.setFloatField(l,"mBearingDegrees", mBearing);
            preparedLocations.add(l);
        }
        public static void addLTECellTower(
                double mLatitude, double mLongitude,
                int cid, int pci, int tac, int earfcn,
                @NonNull int[] bands, int bandwidth,
                @Nullable String mccStr, @Nullable String mncStr,
                @Nullable String alphal, @Nullable String alphas,
                @NonNull Collection<String> additionalPlmns,
                @Nullable ClosedSubscriberGroupInfo csgInfo,int freqMHz
        ){
            CellIdentityLte cellIdentityLte = constructCellIdentityLte(
                    cid,pci,tac,earfcn,bands,bandwidth,
                    mccStr,mncStr,alphal,alphas,additionalPlmns,csgInfo);
            CellInfoLte cellInfoLte = constructCellInfoLte(
                    CONNECTION_PRIMARY_SERVING,true,0,
                    cellIdentityLte,fakeCellSignalStrengthLte,fakeCellInfoConfig
            );
            CellIdentityWithLocation cellIdentityWithLocation = new CellIdentityWithLocation(mLatitude,mLongitude,cellIdentityLte,freqMHz);
            cellTowers.add(new Pair<>(cellInfoLte,cellIdentityWithLocation));
        }

        public static Location updateLocation(Location toUpdate){
            Location ret = new Location(toUpdate);
            XposedHelpers.setLongField(ret,"mTimeMs",System.currentTimeMillis());
            XposedHelpers.setLongField(ret,"mElapsedRealtimeNs", SystemClock.elapsedRealtimeNanos());
            XposedHelpers.setFloatField(ret,"mSpeedMetersPerSecond", ((float)new ExtendedRandom(System.currentTimeMillis()).nextSmallDouble(0.5))+4.5f);
            XposedHelpers.setDoubleField(ret,"mLatitudeDegrees", ret.getLatitude() + 0.00001 * new Random().nextInt(3));
            XposedHelpers.setDoubleField(ret,"mLongitudeDegrees", ret.getLongitude() + 0.00001 * new Random().nextInt(3));
            return ret;
        }

        public static void updateAllCellInfo(){
            cellTowers.forEach(pair -> {
                updateCellInfo(HookLocationManager.LocationGetter.getLocationByCurrentTimestamp(),pair);
            });
            cellTowers.sort((o1, o2) -> {
                Location current = getLocationByCurrentTimestamp();
                double dist1 = distanceOnEarth(current.getLatitude(),current.getLongitude(),o1.second.getLat(),o1.second.getLit());
                double dist2 = distanceOnEarth(current.getLatitude(),current.getLongitude(),o2.second.getLat(),o2.second.getLit());
                return Double.compare(dist1, dist2);
            });
        }
        public static CellInfo updateCellInfo(Location updateAt,Pair<CellInfo,CellIdentityWithLocation> toUpdate){
            if (toUpdate.first instanceof CellInfoLte){
                return updateCellInfoLte(updateAt, (CellInfoLte) toUpdate.first,toUpdate.second);
            }
            return null;
        }
        public static CellInfoLte updateCellInfoLte(Location updateAt,CellInfoLte infoToUpdate,CellIdentityWithLocation cellAt){
            long mTimestamp = SystemClock.elapsedRealtimeNanos();
            int rsrp = (int) Math.floor(cellAt.signalStrengthRSRPFromCoordinates(updateAt.getLatitude(),updateAt.getLongitude()));
            int rssi = (int) Math.floor(cellAt.signalStrengthRSSIFromCoordinates(updateAt.getLatitude(),updateAt.getLongitude()));
            CellSignalStrengthLte cellSignalStrengthLte = constructCellSignalStrengthLte(
                    rssi,rsrp,-9 + new Random().nextInt(3),
                    Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,
                    0,4,1
            );
            XposedHelpers.setObjectField(infoToUpdate,"mCellSignalStrengthLte",cellSignalStrengthLte);
            XposedHelpers.setLongField(infoToUpdate,"mTimeStamp",mTimestamp);
            return infoToUpdate;
        }
        public static SignalStrength currentSignalStrength(){
            updateAllCellInfo();
            CellSignalStrength cellSignalStrength = cellTowers.get(
                    0//new Random().nextInt(cellTowers.size())
            ).first.getCellSignalStrength();
            return HookLocationManager.LocationGetter.signalStrengthFrom(cellSignalStrength);
        }
        public static SignalStrength signalStrengthFrom(CellSignalStrength cellSignalStrength){
            try {
                SignalStrength result = SIGNAL_STRENGTH_CONSTRUCTOR.newInstance();
                if (cellSignalStrength instanceof CellSignalStrengthCdma){
                    XposedHelpers.setObjectField(result,"mCdma",cellSignalStrength);
                    return result;
                }
                if (cellSignalStrength instanceof CellSignalStrengthGsm){
                    XposedHelpers.setObjectField(result,"mGsm",cellSignalStrength);
                    return result;
                }
                if (cellSignalStrength instanceof CellSignalStrengthWcdma){
                    XposedHelpers.setObjectField(result,"mWcdma",cellSignalStrength);
                    return result;
                }
                if (cellSignalStrength instanceof CellSignalStrengthTdscdma){
                    XposedHelpers.setObjectField(result,"mTdscdma",cellSignalStrength);
                    return result;
                }
                if (cellSignalStrength instanceof CellSignalStrengthLte){
                    XposedHelpers.setObjectField(result,"mLte",cellSignalStrength);
                    return result;
                }
                if (cellSignalStrength instanceof CellSignalStrengthNr){
                    XposedHelpers.setObjectField(result,"mNr",cellSignalStrength);
                    return result;
                }
                return result;
            }catch (Exception e){
                LoggerLog(e);
                return null;
            }
        }
        public static SimpleExecutor setLocationByCurrentTimestamp = param -> param.setResult(getLocationByCurrentTimestamp());
        public static SimpleExecutor setLocationResultByCurrentTimestamp = param -> {
            param.setResult(getLocationResultByCurrentTimestamp());
        };
        public static Constructor<SignalStrength> SIGNAL_STRENGTH_CONSTRUCTOR =
                (Constructor<SignalStrength>) XposedHelpers.findConstructorExact(
                        SignalStrength.class
                );
    }

    public static void hookILocationProviderManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onInitialize",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSetAllowed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSetProperties",null);
        hookAllMethodsWithCache_Auto(hookClass,"onReportLocation",(SimpleExecutor)param -> {
            param.args[0] = LocationGetter.getLocationByCurrentTimestamp();
        },noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"onReportLocations",(SimpleExecutor)param -> {
            param.args[0] = getLocationsByCurrentTimestamp();
        },noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"onFlushComplete",null);
    }

    public static void hookILocationProvider(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"setLocationProviderManager",null);
        hookAllMethodsWithCache_Auto(hookClass,"setRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"flush",null);
        hookAllMethodsWithCache_Auto(hookClass,"sendExtraCommand",null);
    }
    public static void hookPassiveLocationProvider(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"updateLocation",
                (SimpleExecutor)param -> param.args[0] = getLocationResultByCurrentTimestamp(),
                noSystemChecker);
    }
    public static void hookMockableLocationProvider(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"setMockProvider",(SimpleExecutor)param -> {
            XposedHelpers.callMethod(param.thisObject,"setRealProvider",param.args[0]);
            param.setResult(null);
        },noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"isMock",false,noSystemChecker);

    }
    public static void hookAbstractLocationProviderListener(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onReportLocation",(SimpleExecutor)param -> {
            param.args[0] = getLocationResultByCurrentTimestamp();
        },noSystemChecker);
    }

    public static void hookGnssLocationProvider(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onReportLocation",(SimpleExecutor)param -> {
            Location location = getLocationByCurrentTimestamp();
            location.setProvider("gps");
            int index = findClassIndexInArgs(param.args,Location.class);
            if (index < 0){return;}
            param.args[index] = location;
            LoggerLog(new Exception("onReportLocation"));
        });
        hookAllMethodsWithCache_Auto(hookClass,"handleReportLocation",(SimpleExecutor)param -> {
            Location location = getLocationByCurrentTimestamp();
            location.setProvider("gps");
            int index = findClassIndexInArgs(param.args,Location.class);
            if (index < 0){return;}
            param.args[index] = location;
        });
    }

    public static final Set<Class<?>> listenedLocationRelatedClass = new HashSet<>();
    private static final XC_MethodHook resultLocationListenerForCollection = new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            Object result =  param.getResult();
            if (result instanceof Collection<?> c){
                if (!c.isEmpty()){
                    if (c.toArray()[0] instanceof Location){
                        param.setResult(getLocationsByCurrentTimestamp());
                    }
                }
            }
        }
    };
    private static final XC_MethodHook resultLocationListener_Location = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            return getLocationByCurrentTimestamp();
        }
    };
    private static final XC_MethodHook resultLocationListener_LocationResult = new XC_MethodReplacement() {
        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            return getLocationResultByCurrentTimestamp();
        }
    };
    public static void listenLocationRelatedClass(Class<?> hookClass){
        if (isHookedPoolRegistered(hookClass,listenedLocationRelatedClass)){return;}
        for (Method m:hookClass.getDeclaredMethods()){
            if (Objects.equals(m.getReturnType(),Location.class)){
                XposedBridge.hookMethod(m, resultLocationListener_Location);
            }
            else if (Objects.equals(m.getReturnType(),LocationResult.class)){
                XposedBridge.hookMethod(m, resultLocationListener_LocationResult);
            }
            else if (m.getReturnType() != void.class){
                Class<?> returnType = m.getReturnType();
                if (Collection.class.isAssignableFrom(returnType) || returnType.isAssignableFrom(Collection.class)){
                    XposedBridge.hookMethod(m, resultLocationListenerForCollection);
                }
            }else {
                int currentIndex = 0;
                List<Integer> locationIndexes = new ArrayList<>();
                List<Integer> locationResultIndexes = new ArrayList<>();
                List<Integer> ListIndexes = new ArrayList<>();
                for (Class<?> c:m.getParameterTypes()){
                    if (c.isAssignableFrom(Location.class)){
                        locationIndexes.add(currentIndex);
                    }else if (c.isAssignableFrom(LocationResult.class)){
                        locationResultIndexes.add(currentIndex);
                    }else if (c.isAssignableFrom(List.class)){
                        ListIndexes.add(currentIndex);
                    }
                    currentIndex += 1;
                }
                if (!(
                        locationIndexes.isEmpty() &&
                                locationResultIndexes.isEmpty() &&
                                ListIndexes.isEmpty()
                        )){
                    XposedBridge.hookMethod(m, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            for (int i:locationIndexes){
                                param.args[i] = getLocationByCurrentTimestamp();
                            }
                            for (int i:locationResultIndexes){
                                param.args[i] = getLocationResultByCurrentTimestamp();
                            }
                            for (int i:ListIndexes){
                                List<?> l = (List<?>) param.args[i];
                                if (!l.isEmpty()){
                                    if (l.get(0) instanceof Location){
                                        param.args[i] = getLocationsByCurrentTimestamp();
                                    }else if (l.get(0) instanceof LocationResult){
                                        param.args[i] = getLocationResultByCurrentTimestamp();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
        Class<?> superClass = hookClass.getSuperclass();
        if (superClass != null && superClass != Object.class){
            if (!Modifier.isAbstract(superClass.getModifiers())){
                listenLocationRelatedClass(superClass);
            }
        }
    }
}
