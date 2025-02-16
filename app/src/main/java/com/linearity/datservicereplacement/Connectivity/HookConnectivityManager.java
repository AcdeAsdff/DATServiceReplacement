package com.linearity.datservicereplacement.Connectivity;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.Connectivity.NetworkConstructUtils.FAKE_NETWORK_INFO_ARR;
import static com.linearity.datservicereplacement.Connectivity.NetworkConstructUtils.FAKE_NETWORK_INFO_INSTANCE;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_UidAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.Connectivity.NetworkConstructUtils.returnNetworkArrayByCallingUID;
import static com.linearity.datservicereplacement.Connectivity.NetworkConstructUtils.returnNetworkByCallingUID;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.isHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.isPublicHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.registerServiceHook_map;
import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_NETWORK_STATE_ARRAY;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import com.linearity.datservicereplacement.Location.HookGNSS;
import com.linearity.utils.FakeClass.java.util.EmptyArrays;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookConnectivityManager {

    public static void doHook(){
        classesAndHooks.put("com.android.server.ConnectivityService", HookConnectivityManager::hookIConnectivityManager);
//        may cause crush(lost LTE after boot)
//        classesAndHooks.put("android.net.connectivity.com.android.server.ConnectivityService", HookConnectivityManager::hookIConnectivityManager);
        classesAndHooks.put("android.net.ConnectivityFrameworkInitializer", hookClass -> {
            for (Method m:hookClass.getDeclaredMethods()){
                if (m.getName().equals("toString") && Objects.equals(m.getReturnType(),String.class)){
                    return;
                }
                if (!Modifier.isStatic(m.getModifiers())){
                    return;
                }
                if (Objects.equals(m.getReturnType(),void.class)){
                    return;
                }
                if (m.getParameterCount() == 2){

                    String returnTypeName = m.getReturnType().getTypeName();
                    if (returnTypeName.equals(ConnectivityManager.class.getTypeName())){

                        XposedBridge.hookMethod(m, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                Object IConnectivityManagerInstance = param.args[1];
                                if (IConnectivityManagerInstance == null){
                                    return;
                                }
                                hookIConnectivityManager(IConnectivityManagerInstance.getClass());
                            }
                        });
                        return;
                    }
                }
            }
        });
        hookPublishBinderService();
    }
    public static void hookPublishBinderService(){
        registerServiceHook_map.put(Context.CONNECTIVITY_SERVICE, c -> {
            hookIConnectivityManager(c);
            return null;
        });
    }


    public static final Set<Class<?>> IConnectivityManagerHookedPool = new HashSet<>();

    //TODO:Randomize
    public static void hookIConnectivityManager(Class<?> hookClass){
        if (isHookedPoolRegistered(hookClass,IConnectivityManagerHookedPool)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"getActiveNetwork",returnNetworkByCallingUID);//Network
        hookAllMethodsWithCache_Auto(hookClass,"getActiveNetworkForUid",returnNetworkByCallingUID);
        hookAllMethodsWithCache_Auto(hookClass,"getActiveNetworkInfo",FAKE_NETWORK_INFO_INSTANCE);
        hookAllMethodsWithCache_Auto(hookClass,"getActiveNetworkInfoForUid",FAKE_NETWORK_INFO_INSTANCE,getSystemChecker_UidAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getNetworkInfo",FAKE_NETWORK_INFO_INSTANCE);
        hookAllMethodsWithCache_Auto(hookClass,"getNetworkInfoForUid",FAKE_NETWORK_INFO_INSTANCE,getSystemChecker_UidAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getAllNetworkInfo",FAKE_NETWORK_INFO_ARR);
        hookAllMethodsWithCache_Auto(hookClass,"getNetworkForType",returnNetworkByCallingUID);
        hookAllMethodsWithCache_Auto(hookClass,"getAllNetworks",returnNetworkArrayByCallingUID);
        hookAllMethodsWithCache_Auto(hookClass,"getDefaultNetworkCapabilitiesForUser",EmptyArrays.EMPTY_NETWORK_CAPABILITY_ARRAY,getSystemChecker_PackageNameAt(1));//NetworkCapabilities[]
        hookAllMethodsWithCache_Auto(hookClass,"isNetworkSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"getActiveLinkProperties",null);
        hookAllMethodsWithCache_Auto(hookClass,"getLinkPropertiesForType",null);
        hookAllMethodsWithCache_Auto(hookClass,"getLinkProperties",null);
        hookAllMethodsWithCache_Auto(hookClass,"getRedactedLinkPropertiesForPackage",null,getSystemChecker_PackageNameAt(2));//LinkProperties
        hookAllMethodsWithCache_Auto(hookClass,"getNetworkCapabilities",null,getSystemChecker_PackageNameAt(1));//NetworkCapabilities
        hookAllMethodsWithCache_Auto(hookClass,"getRedactedNetworkCapabilitiesForPackage",null,getSystemChecker_PackageNameAt(2));//NetworkCapabilities

        hookAllMethodsWithCache_Auto(hookClass,"getAllNetworkState",EMPTY_NETWORK_STATE_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getAllNetworkStateSnapshots",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"isActiveNetworkMetered",true);
        hookAllMethodsWithCache_Auto(hookClass,"requestRouteToHostAddress",true,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getLastTetherError",0);
        hookAllMethodsWithCache_Auto(hookClass,"getTetherableIfaces",EmptyArrays.EMPTY_STRING_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getTetheredIfaces",EmptyArrays.EMPTY_STRING_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getTetheringErroredIfaces",EmptyArrays.EMPTY_STRING_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getTetherableUsbRegexs",EmptyArrays.EMPTY_STRING_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getTetherableWifiRegexs",EmptyArrays.EMPTY_STRING_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"reportInetCondition",null);
        hookAllMethodsWithCache_Auto(hookClass,"reportNetworkConnectivity",null);
        hookAllMethodsWithCache_Auto(hookClass,"getGlobalProxy",null);//ProxyInfo
        hookAllMethodsWithCache_Auto(hookClass,"setGlobalProxy",null);
        hookAllMethodsWithCache_Auto(hookClass,"getProxyForNetwork",null);
        hookAllMethodsWithCache_Auto(hookClass,"setRequireVpnForUids",null);
        hookAllMethodsWithCache_Auto(hookClass,"setLegacyLockdownVpnEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"setProvisioningNotificationVisible",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAirplaneMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestBandwidthUpdate",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerNetworkProvider",0);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterNetworkProvider",null);
        hookAllMethodsWithCache_Auto(hookClass,"declareNetworkRequestUnfulfillable",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerNetworkAgent",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestNetwork",null,getSystemChecker_PackageNameAt(8));
//        hookAllMethodsWithCache_Auto(hookClass,"pendingRequestForNetwork",null,getSystemChecker_PackageNameAt(2));
//        hookAllMethodsWithCache_Auto(hookClass,"releasePendingNetworkRequest",null);
//        hookAllMethodsWithCache_Auto(hookClass,"listenForNetwork",null,getSystemChecker_PackageNameAt(4));
//        hookAllMethodsWithCache_Auto(hookClass,"pendingListenForNetwork",null,getSystemChecker_PackageNameAt(2));
//        hookAllMethodsWithCache_Auto(hookClass,"releaseNetworkRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAcceptUnvalidated",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setAcceptPartialConnectivity",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAvoidUnvalidated",null);
        hookAllMethodsWithCache_Auto(hookClass,"startCaptivePortalApp",null);
        hookAllMethodsWithCache_Auto(hookClass,"startCaptivePortalAppInternal",null);
        hookAllMethodsWithCache_Auto(hookClass,"shouldAvoidBadWifi",true);
        hookAllMethodsWithCache_Auto(hookClass,"getMultipathPreference",0);
        hookAllMethodsWithCache_Auto(hookClass,"getDefaultRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"getRestoreDefaultNetworkDelay",0);
        hookAllMethodsWithCache_Auto(hookClass,"factoryReset",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startNattKeepalive",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startNattKeepaliveWithFd",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startTcpKeepalive",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopKeepalive",null);
        hookAllMethodsWithCache_Auto(hookClass,"getSupportedKeepalives",EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getCaptivePortalServerUrl","");
        hookAllMethodsWithCache_Auto(hookClass,"getNetworkWatchlistConfigHash",EmptyArrays.EMPTY_BYTE_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getConnectionOwnerUid",1000);
//        hookAllMethodsWithCache_Auto(hookClass,"registerConnectivityDiagnosticsCallback",null,getSystemChecker_PackageNameAt(2));
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterConnectivityDiagnosticsCallback",null);

//        hookAllMethodsWithCache_Auto(hookClass,"startOrGetTestNetworkService",null);//IBinder

        hookAllMethodsWithCache_Auto(hookClass,"simulateDataStall",null);
//        hookAllMethodsWithCache_Auto(hookClass,"systemReady",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerNetworkActivityListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterNetworkActivityListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"isDefaultNetworkActive",true);
//        hookAllMethodsWithCache_Auto(hookClass,"registerQosSocketCallback",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterQosCallback",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setOemNetworkPreference",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setProfileNetworkPreferences",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getRestrictBackgroundStatusByCaller",0);
//        hookAllMethodsWithCache_Auto(hookClass,"offerNetwork",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unofferNetwork",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setTestAllowBadWifiUntil",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setDataSaverEnabled",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updateMeteredNetworkAllowList",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updateMeteredNetworkDenyList",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setUidFirewallRule",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getUidFirewallRule",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setFirewallChainEnabled",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getFirewallChainEnabled",true);
//        hookAllMethodsWithCache_Auto(hookClass,"replaceFirewallChain",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getCompanionDeviceManagerProxyService",null);//IBinder
//        hookAllMethodsWithCache_Auto(hookClass,"setVpnNetworkPreference",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setTestLowTcpPollingTimerForKeepalive",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getRoutingCoordinatorService",null);//IBinder
    }
}
