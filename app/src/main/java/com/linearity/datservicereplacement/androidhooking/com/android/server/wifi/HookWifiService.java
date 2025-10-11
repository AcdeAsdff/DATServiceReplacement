package com.linearity.datservicereplacement.androidhooking.com.android.server.wifi;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_HASHMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.ParceledListSliceGen;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.isPublicHookedPoolRegistered;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.NotFinished;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class HookWifiService {

    public static void doHook(){
        classesAndHooks.put("com.android.server.wifi.WifiServiceImpl", HookWifiService::hookIWifiManager);
        classesAndHooks.put("android.net.wifi.WifiFrameworkInitializer", hookClass -> {
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
                        if (returnTypeName.equals(WifiManager.class.getTypeName())){

                            XposedBridge.hookMethod(m, new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                    super.beforeHookedMethod(param);
                                    Object IWifiManagerInstance = param.args[1];
                                    if (IWifiManagerInstance == null){
                                        return;
                                    }
                                    hookIWifiManager(IWifiManagerInstance.getClass());
                                }
                            });
                            return;
                        }
                    }
                }
        });
    }
    @NotFinished
    public static void hookIWifiManager(Class<?> hookClass){
        if (isPublicHookedPoolRegistered(hookClass)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"getSupportedFeatures",0L);
        hookAllMethodsWithCache_Auto(hookClass,"getWifiActivityEnergyInfoAsync",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNetworkSelectionConfig",null);
        hookAllMethodsWithCache_Auto(hookClass,"getNetworkSelectionConfig",null);
        hookAllMethodsWithCache_Auto(hookClass,"setThirdPartyAppEnablingWifiConfirmationDialogEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isThirdPartyAppEnablingWifiConfirmationDialogEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setScreenOnScanSchedule",null);
        hookAllMethodsWithCache_Auto(hookClass,"setOneShotScreenOnConnectivityScanDelayMillis",null);
        //TODO:Randomize these all.
        hookAllMethodsWithCache_Auto(hookClass,"getConfiguredNetworks",ParceledListSliceGen,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getPrivilegedConfiguredNetworks",ParceledListSliceGen,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getPrivilegedConnectedNetwork",null,getSystemChecker_PackageNameAt(0));//WifiConfiguration
        hookAllMethodsWithCache_Auto(hookClass,"getAllMatchingFqdnsForScanResults",EMPTY_HASHMAP);
        hookAllMethodsWithCache_Auto(hookClass,"setSsidsAllowlist",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getSsidsAllowlist",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getMatchingOsuProviders",EMPTY_HASHMAP);
        hookAllMethodsWithCache_Auto(hookClass,"getMatchingPasspointConfigsForOsuProviders",EMPTY_HASHMAP);
        hookAllMethodsWithCache_Auto(hookClass,"addOrUpdateNetwork",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"addOrUpdatePasspointConfiguration",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"removePasspointConfiguration",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getPasspointConfigurations",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getWifiConfigsForPasspointProfiles",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"queryPasspointIcon",null);
        hookAllMethodsWithCache_Auto(hookClass,"matchProviderWithCurrentNetwork",0);
        hookAllMethodsWithCache_Auto(hookClass,"removeNetwork",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"removeNonCallerConfiguredNetworks",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"enableNetwork",true,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"disableNetwork",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"allowAutojoinGlobal",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"queryAutojoinGlobal",null);
        hookAllMethodsWithCache_Auto(hookClass,"allowAutojoin",null);
        hookAllMethodsWithCache_Auto(hookClass,"allowAutojoinPasspoint",null);
        hookAllMethodsWithCache_Auto(hookClass,"setMacRandomizationSettingPasspointEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"setPasspointMeteredOverride",null);
        hookAllMethodsWithCache_Auto(hookClass,"startScan",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getScanResults",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getChannelData",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"disconnect",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"reconnect",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"reassociate",true,getSystemChecker_PackageNameAt(0));

        hookAllMethodsWithCache_Auto(hookClass,"getConnectionInfo",new WifiInfo.Builder().build(),getSystemChecker_PackageNameAt(0));//WifiInfo
        hookAllMethodsWithCache_Auto(hookClass,"setWifiEnabled",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getWifiEnabledState",0);
        hookAllMethodsWithCache_Auto(hookClass,"registerDriverCountryCodeChangedListener",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterDriverCountryCodeChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"addWifiNetworkStateChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeWifiNetworkStateChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCountryCode","86",getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setOverrideCountryCode",null);
        hookAllMethodsWithCache_Auto(hookClass,"clearOverrideCountryCode",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDefaultCountryCode",null);
        hookAllMethodsWithCache_Auto(hookClass,"is24GHzBandSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"is5GHzBandSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"is6GHzBandSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"is60GHzBandSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"isWifiStandardSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"getDhcpInfo",null,getSystemChecker_PackageNameAt(0));//DhcpInfo
        hookAllMethodsWithCache_Auto(hookClass,"setScanAlwaysAvailable",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isScanAlwaysAvailable",true);
        hookAllMethodsWithCache_Auto(hookClass,"acquireWifiLock",true,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"updateWifiLockWorkSource",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"releaseWifiLock",true);
        hookAllMethodsWithCache_Auto(hookClass,"initializeMulticastFiltering",null);
        hookAllMethodsWithCache_Auto(hookClass,"isMulticastEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"acquireMulticastLock",null);
        hookAllMethodsWithCache_Auto(hookClass,"releaseMulticastLock",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateInterfaceIpState",null);
        hookAllMethodsWithCache_Auto(hookClass,"isDefaultCoexAlgorithmEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCoexUnsafeChannels",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerCoexCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterCoexCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"startSoftAp",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startTetheredHotspot",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startTetheredHotspotRequest",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"stopSoftAp",true);
        hookAllMethodsWithCache_Auto(hookClass,"validateSoftApConfiguration",true);
        hookAllMethodsWithCache_Auto(hookClass,"startLocalOnlyHotspot",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"stopLocalOnlyHotspot",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerLocalOnlyHotspotSoftApCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterLocalOnlyHotspotSoftApCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"startWatchLocalOnlyHotspot",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopWatchLocalOnlyHotspot",null);
        hookAllMethodsWithCache_Auto(hookClass,"getWifiApEnabledState",0);
        hookAllMethodsWithCache_Auto(hookClass,"getWifiApConfiguration",null);//WifiConfiguration
        hookAllMethodsWithCache_Auto(hookClass,"getSoftApConfiguration",null);//SoftApConfiguration
        hookAllMethodsWithCache_Auto(hookClass,"queryLastConfiguredTetheredApPassphraseSinceBoot",null);
        hookAllMethodsWithCache_Auto(hookClass,"setWifiApConfiguration",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setSoftApConfiguration",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"notifyUserOfApBandConversion",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"enableTdls",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableTdlsWithRemoteIpAddress",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableTdlsWithMacAddress",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableTdlsWithRemoteMacAddress",null);
        hookAllMethodsWithCache_Auto(hookClass,"isTdlsOperationCurrentlyAvailable",null);
        hookAllMethodsWithCache_Auto(hookClass,"getMaxSupportedConcurrentTdlsSessions",null);
        hookAllMethodsWithCache_Auto(hookClass,"getNumberOfEnabledTdlsSessions",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentNetworkWpsNfcConfigurationToken","");
        hookAllMethodsWithCache_Auto(hookClass,"enableVerboseLogging",null);
        hookAllMethodsWithCache_Auto(hookClass,"getVerboseLoggingLevel",0);
        hookAllMethodsWithCache_Auto(hookClass,"disableEphemeralNetwork",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"factoryReset",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentNetwork",null);//Network
        hookAllMethodsWithCache_Auto(hookClass,"retrieveBackupData",EmptyArrays.EMPTY_BYTE_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"restoreBackupData",null);
        hookAllMethodsWithCache_Auto(hookClass,"retrieveSoftApBackupData",EmptyArrays.EMPTY_BYTE_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"restoreSoftApBackupData",null);//SoftApConfiguration
        hookAllMethodsWithCache_Auto(hookClass,"restoreSupplicantBackupData",null);
        hookAllMethodsWithCache_Auto(hookClass,"startSubscriptionProvisioning",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerSoftApCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSoftApCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"addWifiVerboseLoggingStatusChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeWifiVerboseLoggingStatusChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"addOnWifiUsabilityStatsListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeOnWifiUsabilityStatsListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerTrafficStateCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterTrafficStateCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerNetworkRequestMatchCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterNetworkRequestMatchCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"addNetworkSuggestions",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"removeNetworkSuggestions",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getNetworkSuggestions",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getFactoryMacAddresses",EmptyArrays.EMPTY_STRING_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceMobilityState",null);
        hookAllMethodsWithCache_Auto(hookClass,"startDppAsConfiguratorInitiator",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startDppAsEnrolleeInitiator",null);
        hookAllMethodsWithCache_Auto(hookClass,"startDppAsEnrolleeResponder",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopDppSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateWifiUsabilityScore",null);
        hookAllMethodsWithCache_Auto(hookClass,"connect",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"save",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"forget",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerScanResultsCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterScanResultsCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerSuggestionConnectionStatusListener",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSuggestionConnectionStatusListener",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"addLocalOnlyConnectionStatusListener",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"removeLocalOnlyConnectionStatusListener",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"calculateSignalLevel",0);
        hookAllMethodsWithCache_Auto(hookClass,"getWifiConfigForMatchedNetworkSuggestionsSharedWithUser",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"setWifiConnectedNetworkScorer",true);
        hookAllMethodsWithCache_Auto(hookClass,"clearWifiConnectedNetworkScorer",null);
        hookAllMethodsWithCache_Auto(hookClass,"setExternalPnoScanRequest",null,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"setPnoScanEnabled",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"clearExternalPnoScanRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"getLastCallerInfoForApi",null);
        hookAllMethodsWithCache_Auto(hookClass,"getMatchingScanResults",EMPTY_HASHMAP,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"setScanThrottleEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isScanThrottleEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"getAllMatchingPasspointProfilesForScanResults",EMPTY_HASHMAP);
        hookAllMethodsWithCache_Auto(hookClass,"setAutoWakeupEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isAutoWakeupEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"startRestrictingAutoJoinToSubscriptionId",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopRestrictingAutoJoinToSubscriptionId",null);
        hookAllMethodsWithCache_Auto(hookClass,"setCarrierNetworkOffloadEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isCarrierNetworkOffloadEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerSubsystemRestartCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSubsystemRestartCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"restartWifiSubsystem",null);
        hookAllMethodsWithCache_Auto(hookClass,"addSuggestionUserApprovalStatusListener",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"removeSuggestionUserApprovalStatusListener",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setEmergencyScanRequestInProgress",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeAppState",null);
        hookAllMethodsWithCache_Auto(hookClass,"setWifiScoringEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"flushPasspointAnqpCache",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getUsableChannels",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"isWifiPasspointEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setWifiPasspointEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"getStaConcurrencyForMultiInternetMode",0);
        hookAllMethodsWithCache_Auto(hookClass,"setStaConcurrencyForMultiInternetMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"notifyMinimumRequiredWifiSecurityLevelChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyWifiSsidPolicyChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"getOemPrivilegedWifiAdminPackages",EmptyArrays.EMPTY_STRING_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"replyToP2pInvitationReceivedDialog",null);
        hookAllMethodsWithCache_Auto(hookClass,"replyToSimpleDialog",null);
        hookAllMethodsWithCache_Auto(hookClass,"addCustomDhcpOptions",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeCustomDhcpOptions",null);
        hookAllMethodsWithCache_Auto(hookClass,"reportCreateInterfaceImpact",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getMaxNumberOfChannelsPerRequest",0);
        hookAllMethodsWithCache_Auto(hookClass,"addQosPolicies",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"removeQosPolicies",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"removeAllQosPolicies",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setLinkLayerStatsPollingInterval",null);
        hookAllMethodsWithCache_Auto(hookClass,"getLinkLayerStatsPollingInterval",null);
        hookAllMethodsWithCache_Auto(hookClass,"setMloMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"getMloMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"addWifiLowLatencyLockListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeWifiLowLatencyLockListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"getMaxMloAssociationLinkCount",null);
        hookAllMethodsWithCache_Auto(hookClass,"getMaxMloStrLinkCount",null);
        hookAllMethodsWithCache_Auto(hookClass,"getSupportedSimultaneousBandCombinations",null);
        hookAllMethodsWithCache_Auto(hookClass,"setWepAllowed",null);
        hookAllMethodsWithCache_Auto(hookClass,"queryWepAllowed",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableMscs",null);
        hookAllMethodsWithCache_Auto(hookClass,"disableMscs",null);
        hookAllMethodsWithCache_Auto(hookClass,"setSendDhcpHostnameRestriction",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"querySendDhcpHostnameRestriction",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setPerSsidRoamingMode",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"removePerSsidRoamingMode",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getPerSsidRoamingModes",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getTwtCapabilities",null);
        hookAllMethodsWithCache_Auto(hookClass,"setupTwtSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"getStatsTwtSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"teardownTwtSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"setD2dAllowedWhenInfraStaDisabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"queryD2dAllowedWhenInfraStaDisabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"retrieveWifiBackupData",null);
        hookAllMethodsWithCache_Auto(hookClass,"restoreWifiBackupData",null);
        hookAllMethodsWithCache_Auto(hookClass,"isPnoSupported",true);
    }

}
