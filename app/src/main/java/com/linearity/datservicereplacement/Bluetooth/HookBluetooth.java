package com.linearity.datservicereplacement.Bluetooth;

import static com.linearity.datservicereplacement.Bluetooth.BLEMethodHookRegistration.doRegister;
import static com.linearity.datservicereplacement.Bluetooth.BLEMethodHookRegistration.doStartScan;
import static com.linearity.datservicereplacement.Bluetooth.BLEMethodHookRegistration.doStopScan;
import static com.linearity.datservicereplacement.Bluetooth.BLEMethodHookRegistration.doUnregister;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_HASHMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.fakeBluetoothMacAddr;
import static com.linearity.datservicereplacement.ReturnIfNonSys.fakeBluetoothName;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_AttributionSourceAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_AttributionSourceMayAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_WorkSourceAndAttributionSourceAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.pickFromArray_SimpleExecutor;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.isHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.isPublicHookedPoolRegistered;

import android.bluetooth.BluetoothAdapter;

import com.linearity.datservicereplacement.ReturnIfNonSys;
import com.linearity.utils.ClassHookExecutor;
import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;

import java.util.HashSet;
import java.util.Set;

@NotFinished
public class HookBluetooth {

    public static void doHook(){
        ClassHookExecutor ex_hookIBluetoothManager = HookBluetooth::hookIBluetoothManager;
        //TODO:Find out why these 2 are useless

        classesAndHooks.put("com.android.server.BluetoothManagerService", ex_hookIBluetoothManager);
        classesAndHooks.put("com.android.server.bluetooth.BluetoothServiceBinder", ex_hookIBluetoothManager);
        classesAndHooks.put("com.android.bluetooth.btservice.AdapterService$AdapterServiceBinder", HookBluetooth::hookIBluetooth);
        classesAndHooks.put("com.android.bluetooth.gatt.GattService$BluetoothGattBinder", HookBluetooth::hookIBluetoothGatt);
        classesAndHooks.put("com.android.bluetooth.le_scan.TransitionalScanHelper", HookBluetooth::hookTransitionalScanHelper);

        {
//            hookClass = XposedHelpers.findClassIfExists("android.app.LoadedApk$ServiceDispatcher", classLoader);
//            if (hookClass != null){
//                hookAllMethods(hookClass, "connected", new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        LoggerLog(new Exception(param.args[0] + " " +param.args[1]));
//                    }
//                });
//            }

//            hookClass = XposedHelpers.findClassIfExists("com.android.bluetooth.gatt.GattService", classLoader);
//            if (hookClass != null){
//                hookIBluetoothGatt(hookClass);
//            }
        }
        //auto generated

        classesAndHooks.put("com.android.bluetooth.pbap.BluetoothPbapService$PbapBinder", HookBluetooth::hookIBluetoothPbap);
        classesAndHooks.put("com.android.bluetooth.map.BluetoothMapService$BluetoothMapBinder", HookBluetooth::hookIBluetoothMap);
        classesAndHooks.put("com.android.bluetooth.mapclient.MapClientService$Binder", HookBluetooth::hookIBluetoothMapClient);
        classesAndHooks.put("com.android.bluetooth.sap.SapService$SapBinder", HookBluetooth::hookIBluetoothSap);
        classesAndHooks.put("com.android.bluetooth.hfp.HeadsetService$BluetoothHeadsetBinder", HookBluetooth::hookIBluetoothHeadset);
        classesAndHooks.put("com.android.bluetooth.a2dp.A2dpService$BluetoothA2dpBinder", HookBluetooth::hookIBluetoothA2dp);
        classesAndHooks.put("com.android.bluetooth.a2dpsink.A2dpSinkService$A2dpSinkServiceBinder", HookBluetooth::hookIBluetoothA2dpSink);
//            classesAndHooks.put("com.android.bluetooth.avrcpcontroller.BluetoothMediaBrowserService", HookBluetooth::hookMediaBrowserService);
        classesAndHooks.put("com.android.bluetooth.avrcp.AvrcpTargetService", HookBluetooth::hookIBluetoothAvrcpTarget);
        classesAndHooks.put("com.android.bluetooth.avrcpcontroller.AvrcpControllerService", HookBluetooth::hookIBluetoothAvrcpController);
        classesAndHooks.put("com.android.bluetooth.hid.HidHostService", HookBluetooth::hookIBluetoothHidHost);
        classesAndHooks.put("com.android.bluetooth.hid.HidDeviceService", HookBluetooth::hookIBluetoothHidDevice);
        classesAndHooks.put("com.android.bluetooth.mcp.McpService", HookBluetooth::hookIBluetoothMcpServiceManager);
        classesAndHooks.put("com.android.bluetooth.pan.PanService", HookBluetooth::hookIBluetoothPan);
        classesAndHooks.put("com.android.bluetooth.hfpclient.HeadsetClientService", HookBluetooth::hookIBluetoothHeadsetClient);
//            classesAndHooks.put("com.android.bluetooth.hfpclient.connserv.HfpClientConnectionService", HookBluetooth::hookConnectionService);
        classesAndHooks.put("com.android.bluetooth.pbapclient.PbapClientService", HookBluetooth::hookIBluetoothPbapClient);
        classesAndHooks.put("com.android.bluetooth.hearingaid.HearingAidService", HookBluetooth::hookIBluetoothHearingAid);
        classesAndHooks.put("com.android.bluetooth.vc.VolumeControlService", HookBluetooth::hookIBluetoothVolumeControl);
        classesAndHooks.put("com.android.bluetooth.le_audio.LeAudioService", HookBluetooth::hookIBluetoothLeAudio);
        classesAndHooks.put("com.android.bluetooth.csip.CsipSetCoordinatorService", HookBluetooth::hookIBluetoothCsipSetCoordinator);
//            classesAndHooks.put("com.android.bluetooth.pbapclient.AuthenticationService", HookBluetooth::hookAccountAuthenticator);
//            classesAndHooks.put("com.android.bluetooth.telephony.BluetoothInCallService", HookBluetooth::hookInCallService);

    }
    public static void hookTransitionalScanHelper(Class<?> hookClass) {
        if(isPublicHookedPoolRegistered(hookClass)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"registerScanner",(SimpleExecutor)param->{
            doRegister(param);
            param.setResult(null);
        },getSystemChecker_WorkSourceAndAttributionSourceAt(1,2));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterScanner",(SimpleExecutor)param->{
            doUnregister(param);
            param.setResult(null);
        },getSystemChecker_AttributionSourceAt(-1));
        hookAllMethodsWithCache_Auto(hookClass,"startScan",(SimpleExecutor)param->{
            doStartScan(param);
            param.setResult(null);
        },getSystemChecker_AttributionSourceMayAt(-1));
        hookAllMethodsWithCache_Auto(hookClass,"stopScan",(SimpleExecutor)param->{
            doStopScan(param);
            param.setResult(null);
        },getSystemChecker_AttributionSourceAt(-1));
        hookAllMethodsWithCache_Auto(hookClass,"registerPiAndStartScan",
                null,
                getSystemChecker_AttributionSourceAt(-1)
        );
        hookAllMethodsWithCache_Auto(hookClass,"flushPendingBatchResults",
                null,
                getSystemChecker_AttributionSourceAt(-1)
        );
        hookAllMethodsWithCache_Auto(hookClass,"registerSync",
                null,
                getSystemChecker_AttributionSourceAt(-1)
        );
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSync",
                null,
                getSystemChecker_AttributionSourceAt(-1)
        );
        hookAllMethodsWithCache_Auto(hookClass,"transferSync",
                null,
                getSystemChecker_AttributionSourceAt(-1)
        );
        hookAllMethodsWithCache_Auto(hookClass,"transferSetInfo",
                null,
                getSystemChecker_AttributionSourceAt(-1)
        );
        hookAllMethodsWithCache_Auto(hookClass,"numHwTrackFiltersAvailable",
                1,
                getSystemChecker_AttributionSourceAt(0)
        );
    }

    public static final Set<Class<?>> IBluetoothManagerHookedPool = new HashSet<>();
    public static void hookIBluetoothManager(Class<?> hookClass){
        if (isHookedPoolRegistered(hookClass,IBluetoothManagerHookedPool)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"registerAdapter",(SimpleExecutor)param -> {
            Object toRegister = param.args[0];
            hookAllMethodsWithCache_Auto(toRegister.getClass(),"onBluetoothServiceDown",null);
            hookAllMethodsWithCache_Auto(toRegister.getClass(),"onBluetoothOff",null);
        },noSystemChecker);//IBinder
        hookAllMethodsWithCache_Auto(hookClass,"unregisterAdapter",null);
        hookAllMethodsWithCache_Auto(hookClass,"enable",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"enableNoAutoConnect",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"disable",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getState",BluetoothAdapter.STATE_ON);
        hookAllMethodsWithCache_Auto(hookClass,"getAddress",fakeBluetoothMacAddr,getSystemChecker_AttributionSourceAt(0));//String
        hookAllMethodsWithCache_Auto(hookClass,"getName",fakeBluetoothName,param -> {
            showBefore.simpleExecutor.execute(param);
            return getSystemChecker_AttributionSourceAt(0).checkSystemApp(param);
        });//String
        hookAllMethodsWithCache_Auto(hookClass,"onFactoryReset",false,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isBleScanAvailable",true);
        hookAllMethodsWithCache_Auto(hookClass,"enableBle",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"disableBle",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isHearingAidProfileSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"setBtHciSnoopLogMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"getBtHciSnoopLogMode",2);
        hookAllMethodsWithCache_Auto(hookClass,"isAutoOnSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"isAutoOnEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setAutoOnEnabled",null);
    }

    public static final long[] DISCOVERY_END_MILLS = new long[]{60000,30000,15000};
    public static final Set<Class<?>> IBluetoothHookedPool = new HashSet<>();
    public static void hookIBluetooth(Class<?> hookClass){
        if(isHookedPoolRegistered(hookClass,IBluetoothHookedPool)){return;}
        {
            hookAllMethodsWithCache_Auto(hookClass,"getState",BluetoothAdapter.STATE_ON);
            hookAllMethodsWithCache_Auto(hookClass,"offToBleOn",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"onToBleOn",null,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"getAddress",fakeBluetoothMacAddr,getSystemChecker_AttributionSourceAt(0));//String
            hookAllMethodsWithCache_Auto(hookClass,"getUuids",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"setName",true,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getIdentityAddress",fakeBluetoothMacAddr);//String
            hookAllMethodsWithCache_Auto(hookClass,"getIdentityAddressWithType",null);//BluetoothDevice.BluetoothAddress
            hookAllMethodsWithCache_Auto(hookClass,"getName",showBefore,noSystemChecker);//String
            hookAllMethodsWithCache_Auto(hookClass,"getName",fakeBluetoothName,getSystemChecker_AttributionSourceAt(0));//String
            hookAllMethodsWithCache_Auto(hookClass,"getNameLengthForAdvertise",0,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"getScanMode",0,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"setScanMode",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getDiscoverableTimeout",pickFromArray_SimpleExecutor(DISCOVERY_END_MILLS),getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"setDiscoverableTimeout",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"startDiscovery",true,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"cancelDiscovery",true,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"isDiscovering",true,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"getDiscoveryEndMillis",pickFromArray_SimpleExecutor(DISCOVERY_END_MILLS),getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"getAdapterConnectionState",0);
            hookAllMethodsWithCache_Auto(hookClass,"getProfileConnectionState",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getBondedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"createBond",true,getSystemChecker_AttributionSourceAt(4));
            hookAllMethodsWithCache_Auto(hookClass,"cancelBondProcess",true,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"removeBond",true,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getBondState",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"isBondingInitiatedLocally",true,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getSupportedProfiles",0L,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getConnectionHandle",0,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getRemoteName",fakeBluetoothName,getSystemChecker_AttributionSourceAt(1));//String
            hookAllMethodsWithCache_Auto(hookClass,"getRemoteType",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getRemoteAlias",fakeBluetoothName,getSystemChecker_AttributionSourceAt(1));//String
            hookAllMethodsWithCache_Auto(hookClass,"setRemoteAlias",0,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getRemoteClass",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getRemoteUuids",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"fetchRemoteUuids",true,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"sdpSearch",true,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getBatteryLevel",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getMaxConnectedAudioDevices",0,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"setPin",true,getSystemChecker_AttributionSourceAt(4));
            hookAllMethodsWithCache_Auto(hookClass,"setPasskey",true,getSystemChecker_AttributionSourceAt(4));
            hookAllMethodsWithCache_Auto(hookClass,"setPairingConfirmation",true,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getPhonebookAccessPermission",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"setSilenceMode",true,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getSilenceMode",true,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"setPhonebookAccessPermission",true,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getMessageAccessPermission",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"setMessageAccessPermission",true,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getSimAccessPermission",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"setSimAccessPermission",true,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"registerCallback",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"unregisterCallback",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"logL2capcocServerConnection",null);
            hookAllMethodsWithCache_Auto(hookClass,"getSocketManager",null);//IBluetoothSocketManager
            hookAllMethodsWithCache_Auto(hookClass,"logL2capcocClientConnection",null);
            hookAllMethodsWithCache_Auto(hookClass,"logRfcommConnectionAttempt",null);
            hookAllMethodsWithCache_Auto(hookClass,"factoryReset",true,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"isMultiAdvertisementSupported",true);
            hookAllMethodsWithCache_Auto(hookClass,"isOffloadedFilteringSupported",true);
            hookAllMethodsWithCache_Auto(hookClass,"isOffloadedScanBatchingSupported",true);
            hookAllMethodsWithCache_Auto(hookClass,"isActivityAndEnergyReportingSupported",true);
            hookAllMethodsWithCache_Auto(hookClass,"isLe2MPhySupported",true);
            hookAllMethodsWithCache_Auto(hookClass,"isLeCodedPhySupported",true);
            hookAllMethodsWithCache_Auto(hookClass,"isLeExtendedAdvertisingSupported",true);
            hookAllMethodsWithCache_Auto(hookClass,"isLePeriodicAdvertisingSupported",true);
            hookAllMethodsWithCache_Auto(hookClass,"isLeAudioSupported",0);
            hookAllMethodsWithCache_Auto(hookClass,"isLeAudioBroadcastSourceSupported",0);
            hookAllMethodsWithCache_Auto(hookClass,"isLeAudioBroadcastAssistantSupported",0);
            hookAllMethodsWithCache_Auto(hookClass,"isDistanceMeasurementSupported",0,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"getLeMaximumAdvertisingDataLength",0);
            hookAllMethodsWithCache_Auto(hookClass,"reportActivityInfo",null,getSystemChecker_AttributionSourceAt(0));//BluetoothActivityEnergyInfo
            hookAllMethodsWithCache_Auto(hookClass,"registerMetadataListener",true,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"unregisterMetadataListener",true,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"setMetadata",true,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"getMetadata", EmptyArrays.EMPTY_BYTE_ARRAY,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"requestActivityInfo",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"bleOnToOn",null,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"bleOnToOff",null,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"connectAllEnabledProfiles",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"disconnectAllEnabledProfiles",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"setActiveDevice",true,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getActiveDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getMostRecentlyConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"removeActiveDevice",true,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"registerBluetoothConnectionCallback",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"unregisterBluetoothConnectionCallback",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"canBondWithoutDialog",true,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getPackageNameOfBondingApplication",null,getSystemChecker_AttributionSourceAt(1));//String
            hookAllMethodsWithCache_Auto(hookClass,"generateLocalOobData",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"allowLowLatencyAudio",true);
            hookAllMethodsWithCache_Auto(hookClass,"isRequestAudioPolicyAsSinkSupported",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"requestAudioPolicyAsSink",0,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getRequestedAudioPolicyAsSink",null,getSystemChecker_AttributionSourceAt(1));//BluetoothSinkAudioPolicy
            hookAllMethodsWithCache_Auto(hookClass,"startRfcommListener",0,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"stopRfcommListener",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"retrievePendingSocketForServiceRecord",null,getSystemChecker_AttributionSourceAt(1));//IncomingRfcommSocketInfo
            hookAllMethodsWithCache_Auto(hookClass,"setForegroundUserId",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"setPreferredAudioProfiles",0,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getPreferredAudioProfiles",null,getSystemChecker_AttributionSourceAt(1));//Bundle
            hookAllMethodsWithCache_Auto(hookClass,"isDualModeAudioEnabled",0,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"registerPreferredAudioProfilesChangedCallback",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"unregisterPreferredAudioProfilesChangedCallback",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"notifyActiveDeviceChangeApplied",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"registerBluetoothQualityReportReadyCallback",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"unregisterBluetoothQualityReportReadyCallback",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"registerHciVendorSpecificCallback",null);
            hookAllMethodsWithCache_Auto(hookClass,"unregisterHciVendorSpecificCallback",null);
            hookAllMethodsWithCache_Auto(hookClass,"sendHciVendorSpecificCommand",null);
            hookAllMethodsWithCache_Auto(hookClass,"getOffloadedTransportDiscoveryDataScanSupported",0,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"isMediaProfileConnected",true,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"getBluetoothGatt",null);//IBinder
            hookAllMethodsWithCache_Auto(hookClass,"getBluetoothScan",null);//IBinder
            hookAllMethodsWithCache_Auto(hookClass,"unregAllGattClient",null,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"getProfile",null);//IBinder
            hookAllMethodsWithCache_Auto(hookClass,"setActiveAudioDevicePolicy",0,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"getActiveAudioDevicePolicy",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"setMicrophonePreferredForCalls",0,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"isMicrophonePreferredForCalls",true,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"killBluetoothProcess",null);
        }
    }

    public static final Set<Class<?>> IBluetoothGattHookedPool = new HashSet<>();
    public static void hookIBluetoothGatt(Class<?> hookClass){
        if(isHookedPoolRegistered(hookClass,IBluetoothGattHookedPool)){return;}
        {
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerScanner", BLEMethodHookRegistration.hookRegisterScanner);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterScanner", BLEMethodHookRegistration.hookUnregisterScanner);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startScan",BLEMethodHookRegistration.hookStartScan);
            ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopScan",BLEMethodHookRegistration.hookStopScan);

            hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"startScanForIntent",null,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"stopScanForIntent",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"flushPendingBatchResults",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"startAdvertisingSet",null,getSystemChecker_AttributionSourceAt(9));
            hookAllMethodsWithCache_Auto(hookClass,"stopAdvertisingSet",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getOwnAddress",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"enableAdvertisingSet",null,getSystemChecker_AttributionSourceAt(4));
            hookAllMethodsWithCache_Auto(hookClass,"setAdvertisingData",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"setScanResponseData",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"setAdvertisingParameters",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"setPeriodicAdvertisingParameters",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"setPeriodicAdvertisingData",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"setPeriodicAdvertisingEnable",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"registerSync",null,getSystemChecker_AttributionSourceAt(4));
            hookAllMethodsWithCache_Auto(hookClass,"unregisterSync",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"transferSync",null,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"transferSetInfo",null,getSystemChecker_AttributionSourceAt(4));
            hookAllMethodsWithCache_Auto(hookClass,"registerClient",null,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"unregisterClient",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"clientConnect",null,getSystemChecker_AttributionSourceAt(7));
            hookAllMethodsWithCache_Auto(hookClass,"clientDisconnect",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"clientSetPreferredPhy",null,getSystemChecker_AttributionSourceAt(5));
            hookAllMethodsWithCache_Auto(hookClass,"clientReadPhy",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"refreshDevice",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"discoverServices",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"discoverServiceByUuid",null,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"readCharacteristic",null,getSystemChecker_AttributionSourceAt(4));
            hookAllMethodsWithCache_Auto(hookClass,"readUsingCharacteristicUuid",null,getSystemChecker_AttributionSourceAt(6));
            hookAllMethodsWithCache_Auto(hookClass,"writeCharacteristic",0,getSystemChecker_AttributionSourceAt(6));
            hookAllMethodsWithCache_Auto(hookClass,"readDescriptor",null,getSystemChecker_AttributionSourceAt(4));
            hookAllMethodsWithCache_Auto(hookClass,"writeDescriptor",0,getSystemChecker_AttributionSourceAt(5));
            hookAllMethodsWithCache_Auto(hookClass,"registerForNotification",null,getSystemChecker_AttributionSourceAt(4));
            hookAllMethodsWithCache_Auto(hookClass,"beginReliableWrite",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"endReliableWrite",null,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"readRemoteRssi",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"configureMTU",null,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"connectionParameterUpdate",null,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"leConnectionUpdate",null,getSystemChecker_AttributionSourceAt(8));
            hookAllMethodsWithCache_Auto(hookClass,"registerServer",null,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"unregisterServer",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"serverConnect",null,getSystemChecker_AttributionSourceAt(5));
            hookAllMethodsWithCache_Auto(hookClass,"serverDisconnect",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"serverSetPreferredPhy",null,getSystemChecker_AttributionSourceAt(5));
            hookAllMethodsWithCache_Auto(hookClass,"serverReadPhy",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"addService",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"removeService",null,getSystemChecker_AttributionSourceAt(2));
            hookAllMethodsWithCache_Auto(hookClass,"clearServices",null,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"sendResponse",null,getSystemChecker_AttributionSourceAt(6));
            hookAllMethodsWithCache_Auto(hookClass,"sendNotification",0,getSystemChecker_AttributionSourceAt(5));
            hookAllMethodsWithCache_Auto(hookClass,"disconnectAll",null,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"numHwTrackFiltersAvailable",0,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"leSubrateRequest",null,getSystemChecker_AttributionSourceAt(7));
            hookAllMethodsWithCache_Auto(hookClass,"subrateModeRequest",null,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"getSupportedDistanceMeasurementMethods",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"startDistanceMeasurement",null,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"stopDistanceMeasurement",0,getSystemChecker_AttributionSourceAt(3));
            hookAllMethodsWithCache_Auto(hookClass,"getChannelSoundingMaxSupportedSecurityLevel",0,getSystemChecker_AttributionSourceAt(1));
            hookAllMethodsWithCache_Auto(hookClass,"getLocalChannelSoundingMaxSupportedSecurityLevel",0,getSystemChecker_AttributionSourceAt(0));
            hookAllMethodsWithCache_Auto(hookClass,"getChannelSoundingSupportedSecurityLevels",EmptyArrays.EMPTY_INT_ARRAY,getSystemChecker_AttributionSourceAt(0));
        }
    }
    public static void hookIBluetoothAvrcpTarget(Class<?> hookClass){
        if(isPublicHookedPoolRegistered(hookClass)){return;}
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"sendVolumeChanged",null);
    }

    public static void hookIAudioInputCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onDescriptionChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onStatusChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSetGainSettingFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSetGainModeFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSetMuteFailed",null);
    }
    public static void hookIBluetoothA2dp(Class<?> hookClass){
        if(isPublicHookedPoolRegistered(hookClass)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setActiveDevice",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getActiveDevice",null,getSystemChecker_AttributionSourceAt(0));//BluetoothDevice
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setAvrcpAbsoluteVolume",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isA2dpPlaying",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getSupportedCodecTypes",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getCodecStatus",null,getSystemChecker_AttributionSourceAt(1));//BluetoothCodecStatus
        hookAllMethodsWithCache_Auto(hookClass,"setCodecConfigPreference",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"enableOptionalCodecs",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disableOptionalCodecs",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isOptionalCodecsSupported",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isOptionalCodecsEnabled",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setOptionalCodecsEnabled",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getDynamicBufferSupport",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getBufferConstraints",null,getSystemChecker_AttributionSourceAt(0));//BufferConstraints
        hookAllMethodsWithCache_Auto(hookClass,"setBufferLengthMillis",true,getSystemChecker_AttributionSourceAt(2));
    }
    public static void hookIBluetoothA2dpSink(Class<?> hookClass){
        if(isPublicHookedPoolRegistered(hookClass)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioConfig",null,getSystemChecker_AttributionSourceAt(1));//BluetoothAudioConfig
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isA2dpPlaying",true,getSystemChecker_AttributionSourceAt(1));
    }
    public static void hookIBluetoothActivityEnergyInfoListener(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onBluetoothActivityEnergyInfoAvailable",null);
    }
    public static void hookIBluetoothAvrcpController(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getPlayerSettings",null,getSystemChecker_AttributionSourceAt(1));//BluetoothAvrcpPlayerSettings
        hookAllMethodsWithCache_Auto(hookClass,"setPlayerApplicationSetting",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"sendGroupNavigationCmd",null,getSystemChecker_AttributionSourceAt(3));
    }
    public static void hookIBluetoothCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onBluetoothStateChange",null);
        hookAllMethodsWithCache_Auto(hookClass,"onAdapterNameChange",null);
        hookAllMethodsWithCache_Auto(hookClass,"onAdapterAddressChange",null);
    }
    public static void hookIBluetoothConnectionCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onDeviceConnected",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDeviceDisconnected",null);
    }
    public static void hookIBluetoothCsipSetCoordinator(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getAllGroupIds",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getGroupUuidMapByDevice",EMPTY_HASHMAP,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getDesiredGroupSize",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"lockGroup",null,getSystemChecker_AttributionSourceAt(2));//ParcelUuid
        hookAllMethodsWithCache_Auto(hookClass,"unlockGroup",null,getSystemChecker_AttributionSourceAt(1));
    }
    public static void hookIBluetoothCsipSetCoordinatorCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onCsisSetMemberAvailable",null);
    }
    public static void hookIBluetoothCsipSetCoordinatorLockCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onGroupLockSet",null);
    }
    public static void hookIBluetoothGattCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onClientRegistered",null);
        hookAllMethodsWithCache_Auto(hookClass,"onClientConnectionState",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPhyUpdate",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPhyRead",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSearchComplete",null);
        hookAllMethodsWithCache_Auto(hookClass,"onCharacteristicRead",null);
        hookAllMethodsWithCache_Auto(hookClass,"onCharacteristicWrite",null);
        hookAllMethodsWithCache_Auto(hookClass,"onExecuteWrite",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDescriptorRead",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDescriptorWrite",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotify",null);
        hookAllMethodsWithCache_Auto(hookClass,"onReadRemoteRssi",null);
        hookAllMethodsWithCache_Auto(hookClass,"onConfigureMTU",null);
        hookAllMethodsWithCache_Auto(hookClass,"onConnectionUpdated",null);
        hookAllMethodsWithCache_Auto(hookClass,"onServiceChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSubrateChange",null);
    }
    public static void hookIBluetoothGattServerCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onServerRegistered",null);
        hookAllMethodsWithCache_Auto(hookClass,"onServerConnectionState",null);
        hookAllMethodsWithCache_Auto(hookClass,"onServiceAdded",null);
        hookAllMethodsWithCache_Auto(hookClass,"onCharacteristicReadRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDescriptorReadRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"onCharacteristicWriteRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDescriptorWriteRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"onExecuteWrite",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotificationSent",null);
        hookAllMethodsWithCache_Auto(hookClass,"onMtuChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPhyUpdate",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPhyRead",null);
        hookAllMethodsWithCache_Auto(hookClass,"onConnectionUpdated",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSubrateChange",null);
    }
    public static void hookIBluetoothHapClient(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getHapGroup",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getActivePresetIndex",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getActivePresetInfo",null,getSystemChecker_AttributionSourceAt(1));//BluetoothHapPresetInfo
        hookAllMethodsWithCache_Auto(hookClass,"selectPreset",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"selectPresetForGroup",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"switchToNextPreset",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"switchToNextPresetForGroup",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"switchToPreviousPreset",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"switchToPreviousPresetForGroup",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getPresetInfo",null,getSystemChecker_AttributionSourceAt(2));//BluetoothHapPresetInfo
        hookAllMethodsWithCache_Auto(hookClass,"getAllPresetInfo",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getFeatures",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setPresetName",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"setPresetNameForGroup",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"registerCallback",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterCallback",null,getSystemChecker_AttributionSourceAt(1));
    }
    public static void hookIBluetoothHapClientCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onPresetSelected",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPresetSelectionFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPresetSelectionForGroupFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPresetInfoChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSetPresetNameFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSetPresetNameForGroupFailed",null);
    }
    public static void hookIBluetoothHciVendorSpecificCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onCommandStatus",null);
        hookAllMethodsWithCache_Auto(hookClass,"onCommandComplete",null);
        hookAllMethodsWithCache_Auto(hookClass,"onEvent",null);
    }
    public static void hookIBluetoothHeadset(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startVoiceRecognition",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"stopVoiceRecognition",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isAudioConnected",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"sendVendorSpecificResultCode",true,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"connectAudio",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"disconnectAudio",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setAudioRouteAllowed",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioRouteAllowed",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setForceScoAudio",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startScoUsingVirtualVoiceCall",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"stopScoUsingVirtualVoiceCall",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"phoneStateChanged",null,getSystemChecker_AttributionSourceAt(6));
        hookAllMethodsWithCache_Auto(hookClass,"clccResponse",null,getSystemChecker_AttributionSourceAt(7));
        hookAllMethodsWithCache_Auto(hookClass,"setActiveDevice",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getActiveDevice",null,getSystemChecker_AttributionSourceAt(0));//BluetoothDevice
        hookAllMethodsWithCache_Auto(hookClass,"isInbandRingingEnabled",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isNoiseReductionSupported",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isVoiceRecognitionSupported",true,getSystemChecker_AttributionSourceAt(1));
    }
    public static void hookIBluetoothHeadsetClient(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startVoiceRecognition",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"stopVoiceRecognition",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentCalls",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentAgEvents",null,getSystemChecker_AttributionSourceAt(1));//Bundle
        hookAllMethodsWithCache_Auto(hookClass,"acceptCall",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"holdCall",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"rejectCall",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"terminateCall",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"enterPrivateMode",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"explicitCallTransfer",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"dial",null,getSystemChecker_AttributionSourceAt(2));//BluetoothHeadsetClientCall
        hookAllMethodsWithCache_Auto(hookClass,"sendDTMF",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getLastVoiceTagNumber",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"connectAudio",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnectAudio",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setAudioRouteAllowed",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioRouteAllowed",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"sendVendorAtCommand",true,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentAgFeatures",null,getSystemChecker_AttributionSourceAt(1));//Bundle
    }
    public static void hookIBluetoothHearingAid(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setActiveDevice",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getActiveDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setVolume",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getHiSyncId",0L,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceSide",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceMode",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getAdvertisementServiceData",null,getSystemChecker_AttributionSourceAt(1));//AdvertisementServiceData
    }
    public static void hookIBluetoothHidDevice(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"registerApp",true,getSystemChecker_AttributionSourceAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterApp",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"sendReport",true,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"replyReport",true,getSystemChecker_AttributionSourceAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"reportError",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"unplug",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getUserAppName",null,getSystemChecker_AttributionSourceAt(0));//String
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
    }
    public static void hookIBluetoothHidDeviceCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onAppStatusChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onConnectionStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onGetReport",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSetReport",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSetProtocol",null);
        hookAllMethodsWithCache_Auto(hookClass,"onInterruptData",null);
        hookAllMethodsWithCache_Auto(hookClass,"onVirtualCableUnplug",null);
    }
    public static void hookIBluetoothHidHost(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setPreferredTransport",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getPreferredTransport",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getProtocolMode",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"virtualUnplug",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setProtocolMode",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getReport",true,getSystemChecker_AttributionSourceAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"setReport",true,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"sendData",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getIdleTime",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setIdleTime",true,getSystemChecker_AttributionSourceAt(2));
    }
    public static void hookIBluetoothLeAudio(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setActiveDevice",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getActiveDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedGroupLeadDevice",null,getSystemChecker_AttributionSourceAt(1));//BluetoothDevice
        hookAllMethodsWithCache_Auto(hookClass,"getCodecStatus",null,getSystemChecker_AttributionSourceAt(1));//BluetoothLeAudioCodecStatus
        hookAllMethodsWithCache_Auto(hookClass,"setCodecConfigPreference",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"setBroadcastToUnicastFallbackGroup",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getBroadcastToUnicastFallbackGroup",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"registerCallback",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterCallback",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setCcidInformation",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"setInCall",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setInactiveForHfpHandover",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getGroupId",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setVolume",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"groupAddNode",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"groupRemoveNode",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioLocation",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isInbandRingtoneEnabled",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"registerLeBroadcastCallback",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterLeBroadcastCallback",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startBroadcast",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"stopBroadcast",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"updateBroadcast",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"isPlaying",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getAllBroadcastMetadata",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getMaximumNumberOfBroadcasts",0);
        hookAllMethodsWithCache_Auto(hookClass,"getMaximumStreamsPerBroadcast",0);
        hookAllMethodsWithCache_Auto(hookClass,"getMaximumSubgroupsPerBroadcast",0);
        hookAllMethodsWithCache_Auto(hookClass,"isBroadcastActive",true,getSystemChecker_AttributionSourceAt(0));
    }
    public static void hookIBluetoothLeAudioCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onCodecConfigChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onGroupNodeAdded",null);
        hookAllMethodsWithCache_Auto(hookClass,"onGroupNodeRemoved",null);
        hookAllMethodsWithCache_Auto(hookClass,"onGroupStatusChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onGroupStreamStatusChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBroadcastToUnicastFallbackGroupChanged",null);
    }
    public static void hookIBluetoothLeBroadcastAssistant(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"registerCallback",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterCallback",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startSearchingForSources",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"stopSearchingForSources",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isSearchInProgress",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"addSource",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"modifySource",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"removeSource",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getAllSources",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getMaximumSourceCapacity",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getSourceMetadata",null,getSystemChecker_AttributionSourceAt(2));//BluetoothLeBroadcastMetadata
    }
    public static void hookIBluetoothLeBroadcastAssistantCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onSearchStarted",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSearchStartFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSearchStopped",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSearchStopFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSourceFound",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSourceAdded",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSourceAddFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSourceModified",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSourceModifyFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSourceRemoved",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSourceRemoveFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onReceiveStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onSourceLost",null);
    }
    public static void hookIBluetoothLeBroadcastCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onBroadcastStarted",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBroadcastStartFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBroadcastStopped",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBroadcastStopFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPlaybackStarted",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPlaybackStopped",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBroadcastUpdated",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBroadcastUpdateFailed",null);
        hookAllMethodsWithCache_Auto(hookClass,"onBroadcastMetadataChanged",null);
    }
    public static void hookIBluetoothLeCallControl(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"registerBearer",null,getSystemChecker_AttributionSourceAt(7));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterBearer",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"requestResult",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"callAdded",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"callRemoved",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"callStateChanged",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"currentCallsList",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"networkStateChanged",null,getSystemChecker_AttributionSourceAt(3));
    }
    public static void hookIBluetoothLeCallControlCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onBearerRegistered",null);
        hookAllMethodsWithCache_Auto(hookClass,"onAcceptCall",null);
        hookAllMethodsWithCache_Auto(hookClass,"onTerminateCall",null);
        hookAllMethodsWithCache_Auto(hookClass,"onHoldCall",null);
        hookAllMethodsWithCache_Auto(hookClass,"onUnholdCall",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPlaceCall",null);
        hookAllMethodsWithCache_Auto(hookClass,"onJoinCalls",null);
    }
    public static void hookIBluetoothMap(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getState",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getClient",null,getSystemChecker_AttributionSourceAt(0));//BluetoothDevice
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isConnected",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
    }
    public static void hookIBluetoothMapClient(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"sendMessage",true,getSystemChecker_AttributionSourceAt(5));
    }
    public static void hookIBluetoothMcpServiceManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceAuthorized",null,getSystemChecker_AttributionSourceAt(2));
    }
    public static void hookIBluetoothMetadataListener(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onMetadataChanged",null);
    }
    public static void hookIBluetoothOobDataCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onOobData",null);
        hookAllMethodsWithCache_Auto(hookClass,"onError",null);
    }
    public static void hookIBluetoothPan(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"isTetheringOn",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setBluetoothTethering",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
    }
    public static void hookIBluetoothPanCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onAvailable",null);
        hookAllMethodsWithCache_Auto(hookClass,"onUnavailable",null);
    }
    public static void hookIBluetoothPbap(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
    }
    public static void hookIBluetoothPbapClient(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"connect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
    }
    public static void hookIBluetoothPreferredAudioProfilesCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onPreferredAudioProfilesChanged",null);
    }
    public static void hookIBluetoothProfileServiceConnection(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onServiceConnected",null);
        hookAllMethodsWithCache_Auto(hookClass,"onServiceDisconnected",null);
    }
    public static void hookIBluetoothQualityReportReadyCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onBluetoothQualityReportReady",null);
    }
    public static void hookIBluetoothSap(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getState",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getClient",null,getSystemChecker_AttributionSourceAt(0));//BluetoothDevice
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isConnected",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
    }
    public static void hookIBluetoothScan(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"registerScanner",null,getSystemChecker_WorkSourceAndAttributionSourceAt(2,1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterScanner",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startScan",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"startScanForIntent",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"stopScan",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"stopScanForIntent",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"flushPendingBatchResults",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"registerSync",null,getSystemChecker_AttributionSourceAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSync",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"transferSync",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"transferSetInfo",null,getSystemChecker_AttributionSourceAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"numHwTrackFiltersAvailable",0,getSystemChecker_AttributionSourceAt(0));
    }
    public static void hookIBluetoothSocketManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"connectSocket",null);//ParcelFileDescriptor
        hookAllMethodsWithCache_Auto(hookClass,"createSocketChannel",null);//ParcelFileDescriptor
        hookAllMethodsWithCache_Auto(hookClass,"requestMaximumTxDataLength",null);
        hookAllMethodsWithCache_Auto(hookClass,"getL2capLocalChannelId",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getL2capRemoteChannelId",0,getSystemChecker_AttributionSourceAt(1));
    }
    public static void hookIBluetoothVolumeControl(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getConnectedDevices",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesMatchingConnectionStates",EMPTY_ARRAYLIST,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionState",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setConnectionPolicy",true,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionPolicy",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isVolumeOffsetAvailable",true,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getNumberOfVolumeOffsetInstances",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setVolumeOffset",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"setGroupVolume",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getGroupVolume",0,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setGroupActive",null,getSystemChecker_AttributionSourceAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceVolume",null,getSystemChecker_AttributionSourceAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"mute",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"muteGroup",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unmute",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unmuteGroup",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"registerCallback",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterCallback",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"notifyNewRegisteredCallback",null,getSystemChecker_AttributionSourceAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getNumberOfAudioInputControlServices",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"registerAudioInputControlCallback",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterAudioInputControlCallback",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioInputGainSettingUnit",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioInputGainSettingMin",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioInputGainSettingMax",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioInputDescription",null,getSystemChecker_AttributionSourceAt(0));//String
        hookAllMethodsWithCache_Auto(hookClass,"isAudioInputDescriptionWritable",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setAudioInputDescription",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioInputStatus",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioInputType",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioInputGainSetting",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setAudioInputGainSetting",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioInputGainMode",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setAudioInputGainMode",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getAudioInputMute",0,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setAudioInputMute",true,getSystemChecker_AttributionSourceAt(0));
    }
    public static void hookIBluetoothVolumeControlCallback(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onVolumeOffsetChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onVolumeOffsetAudioLocationChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onVolumeOffsetAudioDescriptionChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDeviceVolumeChanged",null);
    }
}
