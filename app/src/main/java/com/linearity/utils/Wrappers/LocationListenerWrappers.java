package com.linearity.utils.Wrappers;

import static com.linearity.datservicereplacement.Location.HookLocationManager.LocationGetter.getLocationByCurrentTimestamp;
import static com.linearity.datservicereplacement.Location.HookLocationManager.LocationGetter.getLocationsByCurrentTimestamp;
import static com.linearity.datservicereplacement.Location.HookLocationManager.hookILocationListener;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.Wrappers.WrapperCore.registerFunctionForWrapper;

import android.location.GnssAntennaInfo;
import android.location.GnssMeasurementsEvent;
import android.location.GnssNavigationMessage;
import android.location.GnssStatus;
import android.location.IGnssAntennaInfoListener;
import android.location.IGnssMeasurementsListener;
import android.location.IGnssNavigationMessageListener;
import android.location.IGnssNmeaListener;
import android.location.IGnssStatusListener;
import android.location.ILocationCallback;
import android.location.ILocationListener;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteException;

import com.linearity.utils.SimpleExecutor;

import java.util.ArrayList;
import java.util.List;

public class LocationListenerWrappers {
    private static boolean registered = false;
    public static void registerLocationListenerWrappers(){
        if (registered){return;}
        registered = true;
        registerFunctionForWrapper(ILocationListener.class, toWrap -> new ILocationListener() {
            @Override
            public void onLocationChanged(List<Location> locations, IRemoteCallback onCompleteCallback) throws RemoteException {
                toWrap.onLocationChanged(getLocationsByCurrentTimestamp(),onCompleteCallback);
            }

            @Override
            public void onProviderEnabledChanged(String provider, boolean enabled) throws RemoteException {
                toWrap.onProviderEnabledChanged(provider,enabled);
            }

            @Override
            public void onFlushComplete(int requestCode) throws RemoteException {
                toWrap.onFlushComplete(requestCode);
            }

            @Override
            public IBinder asBinder() {
                IBinder result = toWrap.asBinder();

//                LoggerLog(new Exception("asBinder:"+result));
                hookAllMethodsWithCache_Auto(result.getClass(),
                        "onLocationChanged",
                        (SimpleExecutor)param -> param.args[0] = getLocationsByCurrentTimestamp()
                        ,noSystemChecker);
                return result;
            }
        });
        registerFunctionForWrapper(IGnssMeasurementsListener.class, defaultListener->new IGnssMeasurementsListener() {
            @Override
            public void onGnssMeasurementsReceived(GnssMeasurementsEvent event) throws RemoteException {
//                LoggerLog(event);
//                defaultListener.onGnssMeasurementsReceived(event);
            }

            @Override
            public void onStatusChanged(int status) throws RemoteException {
                defaultListener.onStatusChanged(status);
            }

            @Override
            public IBinder asBinder() {
                IBinder result = defaultListener.asBinder();
//                LoggerLog(new Exception("asBinder:"+result));
                hookAllMethodsWithCache_Auto(result.getClass(),
                        "onGnssMeasurementsReceived",
                        null,
                        noSystemChecker);
                return result;
            }
        });
        registerFunctionForWrapper(IGnssStatusListener.class, defaultListener -> new IGnssStatusListener() {
            @Override
            public void onGnssStarted() throws RemoteException {
                defaultListener.onGnssStarted();
            }

            @Override
            public void onGnssStopped() throws RemoteException {
            }

            @Override
            public void onFirstFix(int ttff) throws RemoteException {
                defaultListener.onFirstFix(ttff);
            }

            @Override
            public void onSvStatusChanged(GnssStatus gnssStatus) throws RemoteException {
//                LoggerLog(gnssStatus);
//                defaultListener.onSvStatusChanged();
            }

            @Override
            public IBinder asBinder() {
                IBinder result = defaultListener.asBinder();

//                LoggerLog(new Exception("asBinder:"+result));
                hookAllMethodsWithCache_Auto(result.getClass(),
                        "onSvStatusChanged",
                        null,
                        noSystemChecker);
                return result;
            }
        });
        registerFunctionForWrapper(IGnssNmeaListener.class, defaultListener -> new IGnssNmeaListener() {
            @Override
            public void onNmeaReceived(long timestamp, String nmea) throws RemoteException {
                defaultListener.onNmeaReceived(timestamp,nmea);
            }

            @Override
            public IBinder asBinder() {
                IBinder result = defaultListener.asBinder();
//                LoggerLog(new Exception("asBinder:"+result));
                return result;
            }
        });
        registerFunctionForWrapper(IGnssAntennaInfoListener.class, defaultListener -> new IGnssAntennaInfoListener() {
            @Override
            public void onGnssAntennaInfoChanged(List<GnssAntennaInfo> antennaInfos) throws RemoteException {
                defaultListener.onGnssAntennaInfoChanged(new ArrayList<>());
            }

            @Override
            public IBinder asBinder() {
                IBinder result = defaultListener.asBinder();
//                LoggerLog(new Exception("asBinder:"+result));
                hookAllMethodsWithCache_Auto(result.getClass(),
                        "onGnssAntennaInfoChanged",
                        EMPTY_ARRAYLIST,
                        noSystemChecker);
                return result;
            }
        });
        registerFunctionForWrapper(IGnssNavigationMessageListener.class, defaultListener -> new IGnssNavigationMessageListener(){
            @Override
            public IBinder asBinder() {
                IBinder result = defaultListener.asBinder();
//                LoggerLog(new Exception("asBinder:"+result));
                hookAllMethodsWithCache_Auto(result.getClass(),
                        "onGnssNavigationMessageReceived",
                        null,
                        noSystemChecker);
                return result;
            }

            @Override
            public void onGnssNavigationMessageReceived(GnssNavigationMessage event) throws RemoteException {
//                LoggerLog(event);

            }

            @Override
            public void onStatusChanged(int status) throws RemoteException {
                defaultListener.onStatusChanged(status);
            }
        });
        registerFunctionForWrapper(ILocationCallback.class, toWrap -> new ILocationCallback() {
            @Override
            public void onLocation(Location location) throws RemoteException {
                toWrap.onLocation(getLocationByCurrentTimestamp());
            }

            @Override
            public IBinder asBinder() {
                IBinder result = toWrap.asBinder();
//                LoggerLog(new Exception("asBinder:"+result));
                hookAllMethodsWithCache_Auto(result.getClass(),
                        "onLocation",
                        (SimpleExecutor)param -> param.args[0] = getLocationByCurrentTimestamp(),
                        noSystemChecker);
                return result;
            }
        });
    }
}
