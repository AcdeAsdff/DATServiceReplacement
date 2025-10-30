package com.linearity.datservicereplacement.androidhooking.com.android.server.am;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.registerServiceHook_map;
import static com.linearity.utils.ExtendedRandom.SYSTEM_INSTANCE;
import static com.linearity.utils.HookUtils.listenClass;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.ModifyThrowable.cleanStackTrace;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE;

import android.content.Context;
import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.health.HealthKeys;
import android.os.health.HealthStats;
import android.os.health.HealthStatsParceler;
import android.os.health.HealthStatsWriter;
import android.os.health.TimerStat;
import android.util.ArrayMap;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

@NotFinished//TODO: qq can still get power remaining
public class HookIBattery {


    public static void doHook(){
        classesAndHooks.put("com.android.server.am.BatteryStatsService", HookIBattery::hookIBatteryStats);
        classesAndHooks.put("com.android.server.am.BatteryService$BatteryPropertiesRegistrar", HookIBattery::hookIBatteryPropertiesRegistrar);
        classesAndHooks.put("com.android.server.BatteryService",HookIBattery::hookBatteryService);

        hookPublishBinderService();
    }

    public static void hookPublishBinderService(){
        registerServiceHook_map.put(Context.BATTERY_SERVICE, c -> {
            hookBatteryService(c);
            return null;
        });
    }

    @NotFinished
    public static void hookBatteryService(Class<?> hookClass){
//        listenClass(hookClass);
    }
    static int len = SYSTEM_INSTANCE.nextInt(5)+1;
    public static final Class<?> CelluarBatteryStatsClass = XposedHelpers.findClass("android.os.connectivity.CellularBatteryStats", XposedBridge.BOOTCLASSLOADER);
    public static final Map<Integer,Object> FakeCellularBatteryStatsMap = new ConcurrentHashMap<>();
    public static Object generateFakeCellularBatteryStats(int callingUid){
        ExtendedRandom extendedRandom = ExtendedRandom.generateBasicFromUid(callingUid,"CellularBatteryStats");
        int arrLen = extendedRandom.nextInt(5) + 1;
        Object ret = FakeCellularBatteryStatsMap.getOrDefault(callingUid,null);
        if (ret != null){
            return ret;
        }
        ret = XposedHelpers.newInstance(
            CelluarBatteryStatsClass,
            (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(3000),
            (long) extendedRandom.nextInt(300000), (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(300000),
            (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(3000),
            (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(30000),
            extendedRandom.nextLongArr(arrLen, 3000),
            extendedRandom.nextLongArr(arrLen, 3000), extendedRandom.nextLongArr(arrLen, 3000),
            (long) extendedRandom.nextInt(3000));
        FakeCellularBatteryStatsMap.put(callingUid,ret);
        return ret;
    }
    public static final Class<?> WifiBatteryStatsClass = XposedHelpers.findClass("android.os.connectivity.WifiBatteryStats", XposedBridge.BOOTCLASSLOADER);

    public static final Map<Integer,Object> FakeWifiBatteryStatsMap = new ConcurrentHashMap<>();
    public static Object generateFakeWifiBatteryStats(int callingUid){
        ExtendedRandom extendedRandom = ExtendedRandom.generateBasicFromUid(callingUid,"WifiBatteryStats");
        int arrLen = extendedRandom.nextInt(5) + 1;
        Object ret = FakeWifiBatteryStatsMap.getOrDefault(callingUid,null);
        if (ret != null){
            return ret;
        }
        ret = XposedHelpers.newInstance(
                WifiBatteryStatsClass,
                (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(3000),
                (long) extendedRandom.nextInt(300000), (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(300000),
                (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(3000),
                (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(30000),
                (long) extendedRandom.nextInt(300000), (long) extendedRandom.nextInt(3000), (long) extendedRandom.nextInt(300000),
                extendedRandom.nextLongArr(arrLen, 3000),
                extendedRandom.nextLongArr(arrLen, 3000), extendedRandom.nextLongArr(arrLen, 3000),
                (long) extendedRandom.nextInt(3000));
        FakeWifiBatteryStatsMap.put(callingUid,ret);
        return ret;
    }
    public static final Class<?> GpsBatteryStatsClass = XposedHelpers.findClass("android.os.connectivity.GpsBatteryStats", XposedBridge.BOOTCLASSLOADER);

    public static final Map<Integer,Object> FakeGpsBatteryStatsMap = new ConcurrentHashMap<>();
    public static Object generateFakeGpsBatteryStats(int callingUid){
        ExtendedRandom extendedRandom = ExtendedRandom.generateBasicFromUid(callingUid,"GpsBatteryStats");
        int arrLen = extendedRandom.nextInt(2) + 1;
        Object ret = FakeGpsBatteryStatsMap.getOrDefault(callingUid,null);
        if (ret != null){
            return ret;
        }
        ret = XposedHelpers.newInstance(GpsBatteryStatsClass);
        XposedHelpers.callMethod(ret,"setLoggingDurationMs",(long)extendedRandom.nextInt(3000));
        XposedHelpers.callMethod(ret,"setEnergyConsumedMaMs",(long)extendedRandom.nextInt(3000));
        XposedHelpers.callMethod(ret,"setTimeInGpsSignalQualityLevel", (Object) extendedRandom.nextLongArr(arrLen,30000));
        FakeGpsBatteryStatsMap.put(callingUid,ret);
        return ret;
    }

    //TODO:Randomize
    public static void hookIBatteryStats(Class<?> hookClass){
//        listenClass(hookClass);
        hookAllMethodsWithCache_Auto(hookClass,"noteStartSensor",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStopSensor",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStartVideo",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStopVideo",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStartAudio",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStopAudio",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteResetVideo",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteResetAudio",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteFlashlightOn",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteFlashlightOff",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStartCamera",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStopCamera",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteResetCamera",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteResetFlashlight",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWakeupSensorEvent",null);
        hookAllMethodsWithCache_Auto(hookClass,"getBatteryUsageStats", EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"isCharging",true);
        hookAllMethodsWithCache_Auto(hookClass,"computeBatteryTimeRemaining",0L);
        hookAllMethodsWithCache_Auto(hookClass,"computeChargeTimeRemaining",0L);
        hookAllMethodsWithCache_Auto(hookClass,"computeBatteryScreenOffRealtimeMs",0L);
        hookAllMethodsWithCache_Auto(hookClass,"getScreenOffDischargeMah",0L);
        hookAllMethodsWithCache_Auto(hookClass,"noteEvent",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteSyncStart",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteSyncFinish",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteJobStart",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteJobFinish",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStartWakelock",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStopWakelock",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStartWakelockFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteChangeWakelockFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteStopWakelockFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteLongPartialWakelockStart",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteLongPartialWakelockStartFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteLongPartialWakelockFinish",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteLongPartialWakelockFinishFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteVibratorOn",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteVibratorOff",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteGpsChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteGpsSignalQuality",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteScreenState",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteScreenBrightness",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteUserActivity",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWakeUp",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteInteractive",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteConnectivityChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteMobileRadioPowerState",null);
        hookAllMethodsWithCache_Auto(hookClass,"notePhoneOn",null);
        hookAllMethodsWithCache_Auto(hookClass,"notePhoneOff",null);
        hookAllMethodsWithCache_Auto(hookClass,"notePhoneSignalStrength",null);
        hookAllMethodsWithCache_Auto(hookClass,"notePhoneDataConnectionState",null);
        hookAllMethodsWithCache_Auto(hookClass,"notePhoneState",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiOn",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiOff",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiRunning",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiRunningChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiStopped",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiState",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiSupplicantStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiRssiChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteFullWifiLockAcquired",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteFullWifiLockReleased",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiScanStarted",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiScanStopped",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiMulticastEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiMulticastDisabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteFullWifiLockAcquiredFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteFullWifiLockReleasedFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiScanStartedFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiScanStoppedFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiBatchedScanStartedFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiBatchedScanStoppedFromSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiRadioPowerState",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteNetworkInterfaceForTransports",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteNetworkStatsEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteDeviceIdleMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"setBatteryState",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAwakeTimeBattery",0L);
        hookAllMethodsWithCache_Auto(hookClass,"getAwakeTimePlugged",0L);
        hookAllMethodsWithCache_Auto(hookClass,"noteBleScanStarted",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteBleScanStopped",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteBleScanReset",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteBleScanResults",null);

        hookAllMethodsWithCache_Auto(hookClass,"getCellularBatteryStats",new SimpleExecutorWithMode(MODE_BEFORE,param-> param.setResult(generateFakeCellularBatteryStats(Binder.getCallingUid()))));
        hookAllMethodsWithCache_Auto(hookClass,"getWifiBatteryStats",new SimpleExecutorWithMode(MODE_BEFORE,param-> param.setResult(generateFakeWifiBatteryStats(Binder.getCallingUid()))));
        hookAllMethodsWithCache_Auto(hookClass,"getGpsBatteryStats",new SimpleExecutorWithMode(MODE_BEFORE,param-> param.setResult(generateFakeGpsBatteryStats(Binder.getCallingUid()))));
        hookAllMethodsWithCache_Auto(hookClass,"getWakeLockStats",EMPTY_ARRAYMAP);//android 32
        hookAllMethodsWithCache_Auto(hookClass,"getBluetoothBatteryStats",null);//BluetoothBatteryStats
        SimpleExecutor resultHealthStatsParceler = param -> {//TODO:Generate instead of throw
            RemoteException throwBack = new RemoteException();
            cleanStackTrace(throwBack);
            param.setResult(null);
//            param.setThrowable(throwBack);

//            HealthStatsWriter writer = new HealthStatsWriter(new HealthKeys.Constants() );
//            try {
//                param.setResult(new HealthStatsParceler(parcel));
//            }catch (Exception e){
//                LoggerLog(e);
//            }
        };
        hookAllMethodsWithCache_Auto(hookClass,"takeUidSnapshot",resultHealthStatsParceler);//HealthStatsParceler
        hookAllMethodsWithCache_Auto(hookClass,"takeUidSnapshots",resultHealthStatsParceler);//HealthStatsParceler
        hookAllMethodsWithCache_Auto(hookClass,"noteBluetoothControllerActivity",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteModemControllerActivity",null);
        hookAllMethodsWithCache_Auto(hookClass,"noteWifiControllerActivity",null);
        hookAllMethodsWithCache_Auto(hookClass,"setChargingStateUpdateDelayMillis",true);
        hookAllMethodsWithCache_Auto(hookClass,"setChargerAcOnline",null);
        hookAllMethodsWithCache_Auto(hookClass,"setBatteryLevel",null);
        hookAllMethodsWithCache_Auto(hookClass,"unplugBattery",null);
        hookAllMethodsWithCache_Auto(hookClass,"resetBattery",null);
        hookAllMethodsWithCache_Auto(hookClass,"suspendBatteryInput",null);
    }

    public static void hookIBatteryPropertiesRegistrar(Class<?> hookClass){
//        listenClass(hookClass);
        hookAllMethodsWithCache_Auto(hookClass,"getProperty",0);
        hookAllMethodsWithCache_Auto(hookClass,"scheduleUpdate",null);
    }

}
