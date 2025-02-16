package com.linearity.datservicereplacement.Bluetooth;

import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.Bluetooth.AppBLERegistration.START_SCAN;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.AttributionSource;
import android.os.Binder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class BLEMethodHookRegistration {
    public static final XC_MethodHook hookRegisterScanner = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                doRegister(param);
                param.setResult(null);
            }
            AttributionSource attributionSource = null;
            for (Object o:param.args){
                if (o instanceof AttributionSource){
                    attributionSource = (AttributionSource) o;
                }
            }
            if (attributionSource != null){
                if (!isSystemApp(attributionSource.getUid())){
                    doRegister(param);
                    param.setResult(null);
                }
            }
        }
    };
    public static final XC_MethodHook hookUnregisterScanner = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                doUnregister(param);
                param.setResult(null);
            }
            AttributionSource attributionSource = null;
            for (Object o:param.args){
                if (o instanceof AttributionSource){
                    attributionSource = (AttributionSource) o;
                }
            }
            if (attributionSource != null){
                if (!isSystemApp(attributionSource.getUid())){
                    doUnregister(param);
                    param.setResult(null);
                }
            }
        }
    };
    public static final Map<Integer, AppBLERegistration> registeredScanners = new HashMap<>();
    public static final long MAX_LASTS = 60*1000;

    public static void doRegister(XC_MethodHook.MethodHookParam param){
        stopLongLastScanners();
        Object iScannerCallback = param.args[0];
        AttributionSource attributionSource = (AttributionSource) param.args[2];
        AppBLERegistration appBLERegistration = new AppBLERegistration(iScannerCallback,attributionSource);
        while (true){
            int scannerID = new Random(System.currentTimeMillis()).nextInt(65536) + Binder.getCallingUid();
            if (!registeredScanners.containsKey(scannerID)){
                registeredScanners.put(scannerID,appBLERegistration);
                XposedHelpers.callMethod(iScannerCallback,"onScannerRegistered", BluetoothGatt.GATT_SUCCESS,scannerID);
                break;
            }
        }
        param.setResult(null);
    }

    public static void doUnregister(XC_MethodHook.MethodHookParam param){
        stopLongLastScanners();
        int scannerID = (int) param.args[0];
        AppBLERegistration appBLERegistration = registeredScanners.getOrDefault(scannerID,null);
        if (appBLERegistration != null){
            appBLERegistration.mThread.doStop();
            registeredScanners.remove(scannerID);
        }
        param.setResult(null);
    }

    //prevent from serious memory leak.
    public static void stopLongLastScanners(){
        List<Integer> toRemove = new LinkedList<>();

        for (Map.Entry<Integer, AppBLERegistration> entry:registeredScanners.entrySet()){
            if(System.currentTimeMillis() - entry.getValue().registerTime >= MAX_LASTS){
                toRemove.add(entry.getKey());
            }
        }
        for (int key:toRemove){
            AppBLERegistration appBLERegistration = registeredScanners.get(key);
            if (appBLERegistration == null){continue;}
            appBLERegistration.mThread.doStop();
            registeredScanners.remove(key);
        }
    }

    public static final XC_MethodHook hookStartScan = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                doStartScan(param);
                param.setResult(null);
            }
            AttributionSource attributionSource = null;
            for (Object o:param.args){
                if (o instanceof AttributionSource){
                    attributionSource = (AttributionSource) o;
                }
            }
            if (attributionSource != null){
                if (!isSystemApp(attributionSource.getUid())){
                    doStartScan(param);
                    param.setResult(null);
                }
            }
        }
    };
    public static final XC_MethodHook hookStopScan = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                doStopScan(param);
                param.setResult(null);
            }
            AttributionSource attributionSource = null;
            for (Object o:param.args){
                if (o instanceof AttributionSource){
                    attributionSource = (AttributionSource) o;
                }
            }
            if (attributionSource != null){
                if (!isSystemApp(attributionSource.getUid())){
                    doStopScan(param);
                    param.setResult(null);
                }
            }
        }
    };
    public static void doStartScan(XC_MethodHook.MethodHookParam param){
        int scannerID = (int) param.args[0];
        AppBLERegistration registration = registeredScanners.getOrDefault(scannerID,null);
        if (registration==null){return;}
        ScanSettings scanSettings = (ScanSettings) param.args[1];
        List<ScanFilter> filters = (List<ScanFilter>) param.args[2];
        AttributionSource attributionSource = (AttributionSource) param.args[3];
        registration.mThread.setFilter(filters);
        registration.mThread.setCommand(START_SCAN);
        registration.mThread.start();
        param.setResult(null);
    }
    public static void doStopScan(XC_MethodHook.MethodHookParam param){
        int scannerID = (int) param.args[0];
        AppBLERegistration registration = registeredScanners.getOrDefault(scannerID,null);
        if (registration==null){return;}
        registration.mThread.doStop();
        param.setResult(null);
    }
}
