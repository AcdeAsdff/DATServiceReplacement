package com.linearity.datservicereplacement.Bluetooth;

import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.isSystemApp;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.emptyArrListIfNonSys;
import static com.linearity.datservicereplacement.ReturnIfNonSys.fakeBluetoothMacAddrIfNonSys;
import static com.linearity.datservicereplacement.ReturnIfNonSys.fakeBluetoothNameIfNonSys;
import static com.linearity.datservicereplacement.ReturnIfNonSys.fakeBluetoothNameIfNonSys_attrSourceSomewhere;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getIntIfNonSys;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getObjectAttrSourceAtSomewhere;
import static com.linearity.datservicereplacement.ReturnIfNonSys.pickFromArray_AttrSourceAtSomewhere;
import static com.linearity.datservicereplacement.ReturnIfNonSys.trueIfNonSys;
import static com.linearity.datservicereplacement.ReturnIfNonSys.trueIfNonSysUseAttrSource;
import static com.linearity.datservicereplacement.StartHook.settingsForUid;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.ReturnReplacements.returnNull;

import android.bluetooth.BluetoothAdapter;
import android.content.AttributionSource;
import android.os.Binder;

import com.linearity.datservicereplacement.ReturnIfNonSys;
import com.linearity.utils.NotFinished;

import java.util.HashMap;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

@NotFinished
public class HookBluetooth {

    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){
        Class<?> hookClass;
        {
            hookClass = XposedHelpers.findClassIfExists("com.android.server.BluetoothManagerService", lpparam.classLoader);
            if (hookClass != null) {
                hookBluetoothManager(hookClass);
            }
        }
        {
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.btservice.AdapterService",lpparam.classLoader);
            if (hookClass != null){
                hookBluetooth(hookClass);
            }
        }
        {
//            hookClass = XposedHelpers.findClassIfExists("android.app.LoadedApk$ServiceDispatcher",lpparam.classLoader);
//            if (hookClass != null){
//                hookAllMethods(hookClass, "connected", new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        LoggerLog(new Exception(param.args[0] + " " +param.args[1]));
//                    }
//                });
//            }

            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.gatt.GattService",lpparam.classLoader);
            if (hookClass != null){
                hookBluetoothGatt(hookClass);
            }
        }
        {
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.le_scan.TransitionalScanHelper",lpparam.classLoader);
            if (hookClass != null){
                hookTransitionalScanHelper(hookClass);
            }
        }

        //auto generated
        {
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.pbap.BluetoothPbapService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothPbap(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.map.BluetoothMapService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothMap(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.mapclient.MapClientService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothMapClient(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.sap.SapService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothSap(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.hfp.HeadsetService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothHeadset(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.a2dp.A2dpService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothA2dp(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.a2dpsink.A2dpSinkService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothA2dpSink(hookClass);
            }
//            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.avrcpcontroller.BluetoothMediaBrowserService",lpparam.classLoader);
//            if (hookClass != null){
//                hookMediaBrowserService(hookClass);
//            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.avrcp.AvrcpTargetService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothAvrcpTarget(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.avrcpcontroller.AvrcpControllerService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothAvrcpController(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.hid.HidHostService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothHidHost(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.hid.HidDeviceService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothHidDevice(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.mcp.McpService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothMcpServiceManager(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.pan.PanService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothPan(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.hfpclient.HeadsetClientService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothHeadsetClient(hookClass);
            }
//            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.hfpclient.connserv.HfpClientConnectionService",lpparam.classLoader);
//            if (hookClass != null){
//                hookConnectionService(hookClass);
//            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.pbapclient.PbapClientService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothPbapClient(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.hearingaid.HearingAidService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothHearingAid(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.vc.VolumeControlService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothVolumeControl(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.le_audio.LeAudioService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothLeAudio(hookClass);
            }
            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.csip.CsipSetCoordinatorService",lpparam.classLoader);
            if (hookClass != null){
                hookIBluetoothCsipSetCoordinator(hookClass);
            }
//            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.pbapclient.AuthenticationService",lpparam.classLoader);
//            if (hookClass != null){
//                hookAccountAuthenticator(hookClass);
//            }
//            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.telephony.BluetoothInCallService",lpparam.classLoader);
//            if (hookClass != null){
//                hookInCallService(hookClass);
//            }
        }
    }

    private static void hookTransitionalScanHelper(Class<?> hookClass) {

        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerScanner", BLEMethodHookRegistration.hookRegisterScanner);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterScanner", BLEMethodHookRegistration.hookUnregisterScanner);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startScan",BLEMethodHookRegistration.hookStartScan);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopScan",BLEMethodHookRegistration.hookStopScan);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerPiAndStartScan", null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"flushPendingBatchResults", null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerSync", null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterSync", null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"transferSync", null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"transferSetInfo", null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"numHwTrackFiltersAvailable",new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (!isSystemApp(Binder.getCallingUid())){
                    param.setResult(1);
                }
                AttributionSource attributionSource = (AttributionSource) param.args[4];
                if (!isSystemApp(attributionSource.getUid())){
                    param.setResult(1);
                }
            }
        });
    }

    private static void hookBluetoothManager(Class<?> hookClass) {
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass, "registerAdapter", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (!isSystemApp(Binder.getCallingUid())){
                    Object toRegister = param.args[0];
                    ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(toRegister.getClass(),"onBluetoothServiceDown",returnNull);
                    ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(toRegister.getClass(),"onBluetoothOff",returnNull);
                }
            }
        });
//      hookAllMethods(hookClass,"unregisterAdapter",nullIfNonSys);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass, "registerStateChangeCallback", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Object toRegister = param.args[0];
                ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(toRegister.getClass(), "onBluetoothStateChange", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (Objects.equals(param.args[0],false)){param.setResult(null);}
                    }
                });
            }
        });
//        hookAllMethods(hookClass,"unregisterStateChangeCallback",nullIfNonSys);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"enable",trueIfNonSysUseAttrSource);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"enableNoAutoConnect",trueIfNonSysUseAttrSource);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disable",trueIfNonSysUseAttrSource);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getState",getIntIfNonSys(BluetoothAdapter.STATE_ON));
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"bindBluetoothProfileService",new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Object toRegister = param.args[1];
                ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(toRegister.getClass(), "onServiceDisconnected", returnNull);
            }
        });
//        hookAllMethods(hookClass,"unbindBluetoothProfileService",nullIfNonSys);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAddress",fakeBluetoothMacAddrIfNonSys);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getName",fakeBluetoothNameIfNonSys);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"onFactoryReset",trueIfNonSys);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isBleScanAlwaysAvailable",trueIfNonSys);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"enableBle",trueIfNonSysUseAttrSource);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disableBle",trueIfNonSysUseAttrSource);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isBleAppPresent",trueIfNonSys);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isHearingAidProfileSupported",trueIfNonSys);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass, "setBtHciSnoopLogMode", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                int callingUid = Binder.getCallingUid();
                if (!isSystemApp(callingUid)){
                    settingsForUid.getOrDefault(callingUid,new HashMap<>()).put("BtHciSnoopLogMode",2);
                    param.setResult(0);
                }
            }
        });
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getBtHciSnoopLogMode",new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                int callingUid = Binder.getCallingUid();
                if (!isSystemApp(callingUid)){
                    param.setResult(settingsForUid.getOrDefault(callingUid,new HashMap<>()).getOrDefault("BtHciSnoopLogMode",2));
                }
            }
        });
    }

    public static final long[] DISCOVERY_END_MILLS = new long[]{60000,30000,15000};
    public static void hookBluetooth(Class<?> hookClass){
        {
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getState",getIntIfNonSys(BluetoothAdapter.STATE_ON));
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"enable",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disable",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAddress",fakeBluetoothMacAddrIfNonSys);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isLogRedactionEnabled",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getUuids",EMPTY_ARRAYLIST);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setName",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getIdentityAddress",fakeBluetoothMacAddrIfNonSys);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getName",fakeBluetoothNameIfNonSys_attrSourceSomewhere);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getNameLengthForAdvertise",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getScanMode",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setScanMode",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getDiscoverableTimeout",pickFromArray_AttrSourceAtSomewhere(DISCOVERY_END_MILLS));
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setDiscoverableTimeout",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startDiscovery",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"cancelDiscovery",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isDiscovering",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getDiscoveryEndMillis",pickFromArray_AttrSourceAtSomewhere(DISCOVERY_END_MILLS));
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAdapterConnectionState",getObjectAttrSourceAtSomewhere(0));
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getProfileConnectionState",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getBondedDevices",emptyArrListIfNonSys);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"createBond",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"cancelBondProcess",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"removeBond",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getBondState",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isBondingInitiatedLocally",true);
//                hookAllMethods(hookClass,"getSupportedProfiles",long);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionHandle",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getRemoteName",fakeBluetoothNameIfNonSys_attrSourceSomewhere);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getRemoteType",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getRemoteAlias",fakeBluetoothNameIfNonSys_attrSourceSomewhere);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setRemoteAlias",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getRemoteClass",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getRemoteUuids",EMPTY_ARRAYLIST);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"fetchRemoteUuids",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sdpSearch",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getBatteryLevel",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getMaxConnectedAudioDevices",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setPin",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setPasskey",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setPairingConfirmation",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getPhonebookAccessPermission",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setSilenceMode",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getSilenceMode",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setPhonebookAccessPermission",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getMessageAccessPermission",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setMessageAccessPermission",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getSimAccessPermission",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setSimAccessPermission",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerCallback",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterCallback",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"logL2capcocServerConnection",null);
//                hookAllMethods(hookClass,"getSocketManager",IBluetoothSocketManager);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"logL2capcocClientConnection",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"logRfcommConnectionAttempt",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"factoryReset",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isMultiAdvertisementSupported",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isOffloadedFilteringSupported",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isOffloadedScanBatchingSupported",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isActivityAndEnergyReportingSupported",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isLe2MPhySupported",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isLeCodedPhySupported",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isLeExtendedAdvertisingSupported",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isLePeriodicAdvertisingSupported",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isLeAudioSupported",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isLeAudioBroadcastSourceSupported",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isLeAudioBroadcastAssistantSupported",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isDistanceMeasurementSupported",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getLeMaximumAdvertisingDataLength",0);
//    hookAllMethods(hookClass,"reportActivityInfo",BluetoothActivityEnergyInfo);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerMetadataListener",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterMetadataListener",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setMetadata",true);
//            hookAllMethods(hookClass,"getMetadata",byte);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"requestActivityInfo",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startBrEdr",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopBle",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connectAllEnabledProfiles",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnectAllEnabledProfiles",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setActiveDevice",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getActiveDevices",EMPTY_ARRAYLIST);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getMostRecentlyConnectedDevices",EMPTY_ARRAYLIST);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"removeActiveDevice",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerBluetoothConnectionCallback",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterBluetoothConnectionCallback",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"canBondWithoutDialog",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getPackageNameOfBondingApplication",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"generateLocalOobData",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"allowLowLatencyAudio",true);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isRequestAudioPolicyAsSinkSupported",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"requestAudioPolicyAsSink",0);
//            hookAllMethods(hookClass,"getRequestedAudioPolicyAsSink",BluetoothSinkAudioPolicy);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startRfcommListener",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopRfcommListener",0);
//    hookAllMethods(hookClass,"retrievePendingSocketForServiceRecord",IncomingRfcommSocketInfo);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setForegroundUserId",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setPreferredAudioProfiles",0);
//    hookAllMethods(hookClass,"getPreferredAudioProfiles",Bundle);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerPreferredAudioProfilesChangedCallback",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterPreferredAudioProfilesChangedCallback",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"notifyActiveDeviceChangeApplied",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerBluetoothQualityReportReadyCallback",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterBluetoothQualityReportReadyCallback",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getOffloadedTransportDiscoveryDataScanSupported",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isMediaProfileConnected",true);
//    hookAllMethods(hookClass,"getBluetoothGatt",IBinder);
//    hookAllMethods(hookClass,"getBluetoothScan",IBinder);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregAllGattClient",null);
//    hookAllMethods(hookClass,"getProfile",IBinder);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setActiveAudioDevicePolicy",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getActiveAudioDevicePolicy",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"killBluetoothProcess",null);
        }
    }

    public static void hookBluetoothGatt(Class<?> hookClass){
        {
//            hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerScanner", BLEMethodHookRegistration.hookRegisterScanner);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterScanner", BLEMethodHookRegistration.hookUnregisterScanner);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startScan",BLEMethodHookRegistration.hookStartScan);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopScan",BLEMethodHookRegistration.hookStopScan);        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startScanForIntent",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopScanForIntent",null);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"flushPendingBatchResults",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startAdvertisingSet",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopAdvertisingSet",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getOwnAddress",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"enableAdvertisingSet",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setAdvertisingData",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setScanResponseData",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setAdvertisingParameters",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setPeriodicAdvertisingParameters",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setPeriodicAdvertisingData",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setPeriodicAdvertisingEnable",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerSync",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterSync",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"transferSync",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"transferSetInfo",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerClient",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterClient",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"clientConnect",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"clientDisconnect",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"clientSetPreferredPhy",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"clientReadPhy",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"refreshDevice",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"discoverServices",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"discoverServiceByUuid",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"readCharacteristic",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"readUsingCharacteristicUuid",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"writeCharacteristic",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"readDescriptor",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"writeDescriptor",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerForNotification",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"beginReliableWrite",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"endReliableWrite",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"readRemoteRssi",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"configureMTU",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connectionParameterUpdate",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"leConnectionUpdate",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerServer",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterServer",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"serverConnect",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"serverDisconnect",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"serverSetPreferredPhy",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"serverReadPhy",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"addService",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"removeService",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"clearServices",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendResponse",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendNotification",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnectAll",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"numHwTrackFiltersAvailable",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"leSubrateRequest",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"subrateModeRequest",null);
//    hookAllMethods(hookClass,"getSupportedDistanceMeasurementMethods",List);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startDistanceMeasurement",null);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopDistanceMeasurement",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getChannelSoundingMaxSupportedSecurityLevel",0);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getLocalChannelSoundingMaxSupportedSecurityLevel",0);
        }
    }
    public static void hookIBluetoothA2dp(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setActiveDevice",true);
//    hookAllMethods(hookClass,"getActiveDevice",BluetoothDevice);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setAvrcpAbsoluteVolume",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isA2dpPlaying",true);
//    hookAllMethods(hookClass,"getSupportedCodecTypes",List);
//    hookAllMethods(hookClass,"getCodecStatus",BluetoothCodecStatus);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setCodecConfigPreference",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"enableOptionalCodecs",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disableOptionalCodecs",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isOptionalCodecsSupported",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isOptionalCodecsEnabled",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setOptionalCodecsEnabled",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getDynamicBufferSupport",0);
//    hookAllMethods(hookClass,"getBufferConstraints",BufferConstraints);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setBufferLengthMillis",true);
    }
    public static void hookIBluetoothA2dpSink(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
//    hookAllMethods(hookClass,"getAudioConfig",BluetoothAudioConfig);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isA2dpPlaying",true);
    }
    public static void hookIBluetoothAvrcpController(Class<?> hookClass){
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
//    hookAllMethods(hookClass,"getPlayerSettings",BluetoothAvrcpPlayerSettings);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setPlayerApplicationSetting",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendGroupNavigationCmd",null);
    }
    public static void hookIBluetoothAvrcpTarget(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendVolumeChanged",null);
    }
    public static void hookIBluetoothCsipSetCoordinator(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
//    hookAllMethods(hookClass,"getAllGroupIds",List);
//    hookAllMethods(hookClass,"getGroupUuidMapByDevice",Map);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getDesiredGroupSize",0);
//    hookAllMethods(hookClass,"lockGroup",ParcelUuid);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unlockGroup",null);
    }
    public static void hookIBluetoothHeadset(Class<?> hookClass){
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startVoiceRecognition",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopVoiceRecognition",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isAudioConnected",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendVendorSpecificResultCode",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAudioState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isAudioOn",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connectAudio",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnectAudio",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setAudioRouteAllowed",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAudioRouteAllowed",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setForceScoAudio",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startScoUsingVirtualVoiceCall",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopScoUsingVirtualVoiceCall",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"phoneStateChanged",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"clccResponse",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setActiveDevice",true);
//    hookAllMethods(hookClass,"getActiveDevice",BluetoothDevice);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isInbandRingingEnabled",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isNoiseReductionSupported",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isVoiceRecognitionSupported",true);
    }
    public static void hookIBluetoothHeadsetClient(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startVoiceRecognition",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopVoiceRecognition",true);
//    hookAllMethods(hookClass,"getCurrentCalls",List);
//    hookAllMethods(hookClass,"getCurrentAgEvents",Bundle);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"acceptCall",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"holdCall",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"rejectCall",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"terminateCall",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"enterPrivateMode",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"explicitCallTransfer",true);
//    hookAllMethods(hookClass,"dial",BluetoothHeadsetClientCall);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendDTMF",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getLastVoiceTagNumber",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAudioState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connectAudio",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnectAudio",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setAudioRouteAllowed",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAudioRouteAllowed",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendVendorAtCommand",true);
//    hookAllMethods(hookClass,"getCurrentAgFeatures",Bundle);
    }
    public static void hookIBluetoothHearingAid(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setActiveDevice",true);
//    hookAllMethods(hookClass,"getActiveDevices",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setVolume",null);
//    hookAllMethods(hookClass,"getHiSyncId",long);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getDeviceSide",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getDeviceMode",0);
//    hookAllMethods(hookClass,"getAdvertisementServiceData",AdvertisementServiceData);
    }
    public static void hookIBluetoothHidDevice(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerApp",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterApp",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendReport",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"replyReport",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"reportError",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unplug",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
//    hookAllMethods(hookClass,"getUserAppName",String);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
    }
    public static void hookIBluetoothHidHost(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setPreferredTransport",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getPreferredTransport",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getProtocolMode",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"virtualUnplug",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setProtocolMode",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getReport",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setReport",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendData",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getIdleTime",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setIdleTime",true);
    }
    public static void hookIBluetoothLeAudio(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setActiveDevice",true);
//    hookAllMethods(hookClass,"getActiveDevices",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
//    hookAllMethods(hookClass,"getConnectedGroupLeadDevice",BluetoothDevice);
//    hookAllMethods(hookClass,"getCodecStatus",BluetoothLeAudioCodecStatus);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setCodecConfigPreference",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerCallback",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterCallback",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setCcidInformation",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setInCall",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setInactiveForHfpHandover",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getGroupId",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setVolume",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"groupAddNode",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"groupRemoveNode",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAudioLocation",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isInbandRingtoneEnabled",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerLeBroadcastCallback",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterLeBroadcastCallback",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startBroadcast",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopBroadcast",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"updateBroadcast",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isPlaying",true);
//    hookAllMethods(hookClass,"getAllBroadcastMetadata",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getMaximumNumberOfBroadcasts",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getMaximumStreamsPerBroadcast",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getMaximumSubgroupsPerBroadcast",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isBroadcastActive",true);
    }
    public static void hookIBluetoothMap(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getState",0);
//    hookAllMethods(hookClass,"getClient",BluetoothDevice);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isConnected",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
    }
    public static void hookIBluetoothMapClient(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isConnected",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendMessage",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getUnreadMessages",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getSupportedFeatures",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setMessageStatus",true);
    }
    public static void hookIBluetoothMcpServiceManager(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setDeviceAuthorized",null);
    }
    public static void hookIBluetoothPan(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isTetheringOn",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setBluetoothTethering",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
    }
    public static void hookIBluetoothPbap(Class<?> hookClass){
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
    }
    public static void hookIBluetoothPbapClient(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
    }
    public static void hookIBluetoothSap(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getState",0);
//    hookAllMethods(hookClass,"getClient",BluetoothDevice);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isConnected",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
    }
    public static void hookIBluetoothVolumeControl(Class<?> hookClass){
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"connect",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"disconnect",true);
//    hookAllMethods(hookClass,"getConnectedDevices",List);
//    hookAllMethods(hookClass,"getDevicesMatchingConnectionStates",List);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionState",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setConnectionPolicy",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getConnectionPolicy",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isVolumeOffsetAvailable",true);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getNumberOfVolumeOffsetInstances",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setVolumeOffset",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setGroupVolume",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getGroupVolume",0);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setGroupActive",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setDeviceVolume",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"mute",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"muteGroup",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unmute",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unmuteGroup",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerCallback",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterCallback",null);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"notifyNewRegisteredCallback",null);
    }

}
