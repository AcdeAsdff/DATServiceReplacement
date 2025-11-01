package com.linearity.datservicereplacement.androidhooking.com.android.server.phone;

import static com.linearity.datservicereplacement.StartHook.newWeakSet;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.currentSignalStrength;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.updateAllCellInfo;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.updateCellInfo;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.cellTowers;
import static com.linearity.utils.Phone.GsmCdmaPhoneConstructor.DEVICE_ID;
import static com.linearity.utils.Phone.GsmCdmaPhoneConstructor.DEVICE_IMEI_0;
import static com.linearity.utils.Phone.GsmCdmaPhoneConstructor.DEVICE_IMEI_1;
import static com.linearity.utils.Phone.GsmCdmaPhoneConstructor.DEVICE_MEID_0;
import static com.linearity.utils.Phone.GsmCdmaPhoneConstructor.DEVICE_MEID_1;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_HASHMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBeforeThenNull;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.hookLocation;
import static com.linearity.datservicereplacement.StartHook.isHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.listenServiceMap;
import static com.linearity.utils.AndroidFakes.Telephony.ServiceStateUtils.TEMP_SERVICE_STATE;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;

import com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NotFinished
public class HookTelephony {

    public static void doHook(){
        classesAndHooks.put("com.android.phone.PhoneInterfaceManager", HookTelephony::hookITelephony);
//        hookITelephony(TelephonyManager.class);

//        hookClass = XposedHelpers.findClassIfExists("android.telephony.TelephonyFrameworkInitializer", classLoader);
//        if (hookClass != null){
//            for (Method m:hookClass.getDeclaredMethods()){
//                if (m.getName().equals("toString") && Objects.equals(m.getReturnType(),String.class)){
//                    return;
//                }
//                if (!Modifier.isStatic(m.getModifiers())){
//                    return;
//                }
//                if (Objects.equals(m.getReturnType(),void.class)){
//                    return;
//                }
//                XposedBridge.hookMethod(m, new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        showAfter.simpleExecutor.execute(param);
//                    }
//                });
//            }
//        }
        listenServiceMap.put(Context.TELEPHONY_SERVICE, HookTelephony::hookITelephony);
    }


    public static Set<Class<?>> alreadyHookedClass_ITelephony = newWeakSet();

    @NotFinished
    public static void hookITelephony(Class<?> hookClass){
        if (isHookedPoolRegistered(hookClass,alreadyHookedClass_ITelephony)){return;}
//        LoggerLog(hookClass);
        SimpleExecutor setCellInfoList = param -> {
            List<CellInfo> result = new ArrayList<>();
            cellTowers.forEach(pair -> {
                updateCellInfo(HookLocationManager.LocationGetter.getLocationByCurrentTimestamp(),pair);
                result.add(pair.first);
            });
            param.setResult(result);
        };

        SimpleExecutor randomIdentity = param -> {
            updateAllCellInfo();
            param.setResult(cellTowers.get(0).second.getCellIdentity());
        };
//        SimpleExecutor randomIdentity = param -> param.setResult(cellTowers.get(new Random().nextInt(cellTowers.size())).second.getCellIdentity());
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceId",DEVICE_ID,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceIdWithFeature",(SimpleExecutor)param -> {
            param.setResult(DEVICE_ID);
            showBefore.simpleExecutor.execute(param);
        },getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getMeidForSlot",(SimpleExecutor)param->{
            int index = (int) param.args[0];
            if (index == 0){
                param.setResult(DEVICE_MEID_0);
                return;
            }
            if (index == 1){
                param.setResult(DEVICE_MEID_1);
                return;
            }
            param.setResult(new ExtendedRandom(DEVICE_MEID_0.hashCode() + index).nextExtendedRandom().nextRandomDecimal(15));
        },getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getServiceStateForSlot",TEMP_SERVICE_STATE,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"updateServiceLocationWithPackageName",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getCellLocation",randomIdentity, getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getNeighboringCellInfo",showAfter, getSystemChecker_PackageNameAt(0));
        int networkTypeConst = TelephonyManager.NETWORK_TYPE_LTE;
        hookAllMethodsWithCache_Auto(hookClass,"getNetworkTypeForSubscriber", networkTypeConst,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getDataNetworkType",networkTypeConst,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDataNetworkTypeForSubscriber", networkTypeConst,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getVoiceNetworkTypeForSubscriber", networkTypeConst,getSystemChecker_PackageNameAt(1));
        if (hookLocation){
            hookAllMethodsWithCache_Auto(hookClass,"getAllCellInfo",setCellInfoList, getSystemChecker_PackageNameAt(0));
        }
//        hookAllMethodsWithCache_Auto(hookClass,"getAllCellInfo",EMPTY_ARRAYLIST, getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"requestCellInfoUpdate",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCellNetworkScanResults",showAfter, getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"requestNumberVerification",showBeforeThenNull,getSystemChecker_PackageNameAt(3));//void
        hookAllMethodsWithCache_Auto(hookClass,"getLine1NumberForDisplay",showBefore,getSystemChecker_PackageNameAt(1));//String

        hookAllMethodsWithCache_Auto(hookClass,"getPrimaryImei",DEVICE_IMEI_0,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getImeiForSlot",(SimpleExecutor)param->{
            int index = (int) param.args[0];
            if (index == 0){
                param.setResult(DEVICE_IMEI_0);
                return;
            }
            if (index == 1){
                param.setResult(DEVICE_IMEI_1);
                return;
            }
            param.setResult(new ExtendedRandom(DEVICE_IMEI_0.hashCode() + index).nextExtendedRandom().nextRandomDecimal(15));
        },getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"requestIsCommunicationAllowedForCurrentLocation",null);
        hookAllMethodsWithCache_Auto(hookClass,"getLastKnownCellIdentity",randomIdentity, getSystemChecker_PackageNameAt(1));

//        hookAllMethodsWithCache_Auto(hookClass,"dial",null);
//        hookAllMethodsWithCache_Auto(hookClass,"call",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isRadioOn",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isRadioOnWithFeature",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isRadioOnForSubscriber",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isRadioOnForSubscriberWithFeature",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setCallComposerStatus",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCallComposerStatus",0);
        hookAllMethodsWithCache_Auto(hookClass,"supplyPinForSubscriber",true);
        hookAllMethodsWithCache_Auto(hookClass,"supplyPukForSubscriber",true);
        hookAllMethodsWithCache_Auto(hookClass,"supplyPinReportResultForSubscriber",EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"supplyPukReportResultForSubscriber",EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"handlePinMmi",true);
        hookAllMethodsWithCache_Auto(hookClass,"handleUssdRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"handlePinMmiForSubscriber",true);
        hookAllMethodsWithCache_Auto(hookClass,"toggleRadioOnOff",null);
        hookAllMethodsWithCache_Auto(hookClass,"toggleRadioOnOffForSubscriber",null);
        hookAllMethodsWithCache_Auto(hookClass,"setRadio",true);
        hookAllMethodsWithCache_Auto(hookClass,"setRadioForSubscriber",true);
        hookAllMethodsWithCache_Auto(hookClass,"setRadioPower",true);
        hookAllMethodsWithCache_Auto(hookClass,"requestRadioPowerOffForReason",true);
        hookAllMethodsWithCache_Auto(hookClass,"clearRadioPowerOffForReason",true);
        hookAllMethodsWithCache_Auto(hookClass,"getRadioPowerOffReasons",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"updateServiceLocation",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableLocationUpdates",null);
        hookAllMethodsWithCache_Auto(hookClass,"disableLocationUpdates",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableDataConnectivity",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"disableDataConnectivity",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isDataConnectivityPossible",true);
        hookAllMethodsWithCache_Auto(hookClass,"getNetworkCountryIsoForPhone","cn");
        hookAllMethodsWithCache_Auto(hookClass,"getCallState",0);
        hookAllMethodsWithCache_Auto(hookClass,"getCallStateForSubscription",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getDataActivity",0);
        hookAllMethodsWithCache_Auto(hookClass,"getDataActivityForSubId",0);
        hookAllMethodsWithCache_Auto(hookClass,"getDataState",0);
        hookAllMethodsWithCache_Auto(hookClass,"getDataStateForSubId",0);
        hookAllMethodsWithCache_Auto(hookClass,"getActivePhoneType",1);
        hookAllMethodsWithCache_Auto(hookClass,"getActivePhoneTypeForSlot",1);
        hookAllMethodsWithCache_Auto(hookClass,"getCdmaEriIconIndex",0,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getCdmaEriIconIndexForSubscriber",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getCdmaEriIconMode",0,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getCdmaEriIconModeForSubscriber",0,getSystemChecker_PackageNameAt(1));
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getCdmaEriText","",getSystemChecker_PackageNameAt(0));
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getCdmaEriTextForSubscriber","",getSystemChecker_PackageNameAt(1));

        hookAllMethodsWithCache_Auto(hookClass,"needsOtaServiceProvisioning",true);
        hookAllMethodsWithCache_Auto(hookClass,"setVoiceMailNumber",true);
        hookAllMethodsWithCache_Auto(hookClass,"setVoiceActivationState",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDataActivationState",null);
        hookAllMethodsWithCache_Auto(hookClass,"getVoiceActivationState",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getDataActivationState",2,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getVoiceMessageCountForSubscriber",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isConcurrentVoiceAndDataAllowed",true);
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getVisualVoicemailSettings",new Bundle(),getSystemChecker_PackageNameAt(0));
        //TODO:Randomize it👇.(if possible)
        hookAllMethodsWithCache_Auto(hookClass,"getVisualVoicemailPackageName","com.android.dialer",getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"enableVisualVoicemailSmsFilter",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"disableVisualVoicemailSmsFilter",null,getSystemChecker_PackageNameAt(0));
//    hookAllMethodsWithCache_Auto(hookClass,"getVisualVoicemailSmsFilterSettings",VisualVoicemailSmsFilterSettings,getSystemChecker_PackageNameAt(0));
//    hookAllMethodsWithCache_Auto(hookClass,"getActiveVisualVoicemailSmsFilterSettings",VisualVoicemailSmsFilterSettings);
        hookAllMethodsWithCache_Auto(hookClass,"sendVisualVoicemailSmsForSubscriber",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"sendDialerSpecialCode",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"hasIccCard",true);
        hookAllMethodsWithCache_Auto(hookClass,"hasIccCardUsingSlotIndex",true);
        hookAllMethodsWithCache_Auto(hookClass,"getLteOnCdmaMode",1,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getLteOnCdmaModeForSubscriber",1,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"requestCellInfoUpdateWithWorkSource",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"setCellInfoListRate",null);
//    hookAllMethodsWithCache_Auto(hookClass,"iccOpenLogicalChannel",IccOpenLogicalChannelResponse);
        hookAllMethodsWithCache_Auto(hookClass,"iccCloseLogicalChannel",true);
//    hookAllMethodsWithCache_Auto(hookClass,"iccTransmitApduLogicalChannelByPort",String);
//    hookAllMethodsWithCache_Auto(hookClass,"iccTransmitApduLogicalChannel",String);
//    hookAllMethodsWithCache_Auto(hookClass,"iccTransmitApduBasicChannelByPort",String,getSystemChecker_PackageNameAt(2));
//    hookAllMethodsWithCache_Auto(hookClass,"iccTransmitApduBasicChannel",String,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"iccExchangeSimIO",EmptyArrays.EMPTY_BYTE_ARRAY);
//    hookAllMethodsWithCache_Auto(hookClass,"sendEnvelopeWithStatus",String);
//    hookAllMethodsWithCache_Auto(hookClass,"nvReadItem",String);
        hookAllMethodsWithCache_Auto(hookClass,"nvWriteItem",true);
        hookAllMethodsWithCache_Auto(hookClass,"nvWriteCdmaPrl",true);
        hookAllMethodsWithCache_Auto(hookClass,"resetModemConfig",true);
        hookAllMethodsWithCache_Auto(hookClass,"rebootModem",true);
        hookAllMethodsWithCache_Auto(hookClass,"getAllowedNetworkTypesBitmask",0);
        hookAllMethodsWithCache_Auto(hookClass,"isTetheringApnRequiredForSubscriber",true);
        hookAllMethodsWithCache_Auto(hookClass,"enableIms",null);
        hookAllMethodsWithCache_Auto(hookClass,"disableIms",null);
        hookAllMethodsWithCache_Auto(hookClass,"resetIms",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerMmTelFeatureCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterImsFeatureCallback",null);
        //TODO:Randomize it👇.
//    hookAllMethodsWithCache_Auto(hookClass,"getImsRegistration",IImsRegistration);
//    hookAllMethodsWithCache_Auto(hookClass,"getImsConfig",IImsConfig);
        hookAllMethodsWithCache_Auto(hookClass,"setBoundImsServiceOverride",true,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"clearCarrierImsServiceOverride",true);
        //TODO:Randomize it👇.
//    hookAllMethodsWithCache_Auto(hookClass,"getBoundImsServicePackage",String);
        hookAllMethodsWithCache_Auto(hookClass,"getImsMmTelFeatureState",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNetworkSelectionModeAutomatic",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestNetworkScan",0,getSystemChecker_PackageNameAt(5));
        hookAllMethodsWithCache_Auto(hookClass,"stopNetworkScan",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNetworkSelectionModeManual",true);
        hookAllMethodsWithCache_Auto(hookClass,"getAllowedNetworkTypesForReason",0L);
        hookAllMethodsWithCache_Auto(hookClass,"setAllowedNetworkTypesForReason",true);
        hookAllMethodsWithCache_Auto(hookClass,"getDataEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isUserDataEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isDataEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setDataEnabledForReason",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"isDataEnabledForReason",true);
        hookAllMethodsWithCache_Auto(hookClass,"isManualNetworkSelectionAllowed",true);
        hookAllMethodsWithCache_Auto(hookClass,"setImsRegistrationState",null);
//    hookAllMethodsWithCache_Auto(hookClass,"getCdmaMdn",String);
//    hookAllMethodsWithCache_Auto(hookClass,"getCdmaMin",String);
        hookAllMethodsWithCache_Auto(hookClass,"getCarrierPrivilegeStatus",0);
        hookAllMethodsWithCache_Auto(hookClass,"getCarrierPrivilegeStatusForUid",0);
        hookAllMethodsWithCache_Auto(hookClass,"checkCarrierPrivilegesForPackage",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"checkCarrierPrivilegesForPackageAnyPhone",0,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getCarrierPackageNamesForIntentAndPhone",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"setLine1NumberForDisplayForSubscriber",true);
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getLine1AlphaTagForDisplay","UNICOM",getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getMergedSubscriberIds",EmptyArrays.EMPTY_STRING_ARRAY,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getMergedImsisFromGroup",EmptyArrays.EMPTY_STRING_ARRAY,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setOperatorBrandOverride",true);
        hookAllMethodsWithCache_Auto(hookClass,"setRoamingOverride",true);
        hookAllMethodsWithCache_Auto(hookClass,"needMobileRadioShutdown",true);
        hookAllMethodsWithCache_Auto(hookClass,"shutdownMobileRadios",null);
        hookAllMethodsWithCache_Auto(hookClass,"getRadioAccessFamily",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"uploadCallComposerPicture",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"enableVideoCalling",null);
        hookAllMethodsWithCache_Auto(hookClass,"isVideoCallingEnabled",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"canChangeDtmfToneLength",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isWorldPhone",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isTtyModeSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"isRttSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"isHearingAidCompatibilitySupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"isImsRegistered",true);
        hookAllMethodsWithCache_Auto(hookClass,"isWifiCallingAvailable",true);
        hookAllMethodsWithCache_Auto(hookClass,"isVideoTelephonyAvailable",true);
        hookAllMethodsWithCache_Auto(hookClass,"getImsRegTechnologyForMmTel",0);
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getTypeAllocationCodeForSlot","");
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getManufacturerCodeForSlot","99001621");
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceSoftwareVersionForSlot","",getSystemChecker_PackageNameAt(1));

        hookAllMethodsWithCache_Auto(hookClass,"getSubIdForPhoneAccountHandle",0,getSystemChecker_PackageNameAt(1));
//    hookAllMethodsWithCache_Auto(hookClass,"getPhoneAccountHandleForSubscriptionId",PhoneAccountHandle);
        hookAllMethodsWithCache_Auto(hookClass,"factoryReset",null,getSystemChecker_PackageNameAt(1));
//    hookAllMethodsWithCache_Auto(hookClass,"getSimLocaleForSubscriber",String);
        hookAllMethodsWithCache_Auto(hookClass,"requestModemActivityInfo",null);
//    hookAllMethodsWithCache_Auto(hookClass,"getVoicemailRingtoneUri",Uri);
        hookAllMethodsWithCache_Auto(hookClass,"setVoicemailRingtoneUri",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isVoicemailVibrationEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setVoicemailVibrationEnabled",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getPackagesWithCarrierPrivileges",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getPackagesWithCarrierPrivilegesForAllPhones",EMPTY_ARRAYLIST);
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getAidForAppType","");
        hookAllMethodsWithCache_Auto(hookClass,"getEsn","");
        hookAllMethodsWithCache_Auto(hookClass,"getCdmaPrlVersion","");

        hookAllMethodsWithCache_Auto(hookClass,"getTelephonyHistograms",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"setAllowedCarriers",0);
        //TODO:Randomize it👇.(construct CarrierRestrictionRules)
//    hookAllMethodsWithCache_Auto(hookClass,"getAllowedCarriers",CarrierRestrictionRules);
        hookAllMethodsWithCache_Auto(hookClass,"getSubscriptionCarrierId",0);
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getSubscriptionCarrierName","China Unicom");
        hookAllMethodsWithCache_Auto(hookClass,"getSubscriptionSpecificCarrierId",0);
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getSubscriptionSpecificCarrierName","China Unicom");
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getCarrierIdFromMccMnc",1436);

        hookAllMethodsWithCache_Auto(hookClass,"carrierActionSetRadioEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"carrierActionReportDefaultNetworkStatus",null);
        hookAllMethodsWithCache_Auto(hookClass,"carrierActionResetAll",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCallForwarding",null);
        hookAllMethodsWithCache_Auto(hookClass,"setCallForwarding",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCallWaitingStatus",null);
        hookAllMethodsWithCache_Auto(hookClass,"setCallWaitingStatus",null);

        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getClientRequestStats",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setSimPowerStateForSlot",null);
        hookAllMethodsWithCache_Auto(hookClass,"setSimPowerStateForSlotWithCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"getForbiddenPlmns",EmptyArrays.EMPTY_STRING_ARRAY,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"setForbiddenPlmns",0,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"getEmergencyCallbackMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"getSignalStrength",(SimpleExecutor)param -> param.setResult(currentSignalStrength()));
        hookAllMethodsWithCache_Auto(hookClass,"getCardIdForDefaultEuicc",-2,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getUiccCardsInfo",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
//    hookAllMethodsWithCache_Auto(hookClass,"getUiccSlotsInfo",UiccSlotInfo[],getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"switchSlots",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSimSlotMapping",true);
        hookAllMethodsWithCache_Auto(hookClass,"isDataRoamingEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setDataRoamingEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCdmaRoamingMode",0);
        hookAllMethodsWithCache_Auto(hookClass,"setCdmaRoamingMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"getCdmaSubscriptionMode",0);
        hookAllMethodsWithCache_Auto(hookClass,"setCdmaSubscriptionMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCarrierTestOverride",null);
        hookAllMethodsWithCache_Auto(hookClass,"setCarrierServicePackageOverride",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getCarrierIdListVersion",0);
        hookAllMethodsWithCache_Auto(hookClass,"refreshUiccProfile",null);
        hookAllMethodsWithCache_Auto(hookClass,"getNumberOfModemsWithSimultaneousDataConnections",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getNetworkSelectionMode",0);
        hookAllMethodsWithCache_Auto(hookClass,"isInEmergencySmsMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"getRadioPowerState",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"registerImsRegistrationCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterImsRegistrationCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerImsEmergencyRegistrationCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterImsEmergencyRegistrationCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"getImsMmTelRegistrationState",null);
        hookAllMethodsWithCache_Auto(hookClass,"getImsMmTelRegistrationTransportType",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerMmTelCapabilityCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterMmTelCapabilityCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"isCapable",true);
        hookAllMethodsWithCache_Auto(hookClass,"isAvailable",true);
        hookAllMethodsWithCache_Auto(hookClass,"isMmTelCapabilitySupported",null);
        hookAllMethodsWithCache_Auto(hookClass,"isAdvancedCallingSettingEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setAdvancedCallingSettingEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isVtSettingEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setVtSettingEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isVoWiFiSettingEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setVoWiFiSettingEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isCrossSimCallingEnabledByUser",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCrossSimCallingEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isVoWiFiRoamingSettingEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setVoWiFiRoamingSettingEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"setVoWiFiNonPersistent",null);
        hookAllMethodsWithCache_Auto(hookClass,"getVoWiFiModeSetting",0);
        hookAllMethodsWithCache_Auto(hookClass,"setVoWiFiModeSetting",null);
        hookAllMethodsWithCache_Auto(hookClass,"getVoWiFiRoamingModeSetting",0);
        hookAllMethodsWithCache_Auto(hookClass,"setVoWiFiRoamingModeSetting",null);
        hookAllMethodsWithCache_Auto(hookClass,"setRttCapabilitySetting",null);
        hookAllMethodsWithCache_Auto(hookClass,"isTtyOverVolteEnabled",true);
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getEmergencyNumberList",EMPTY_HASHMAP,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isEmergencyNumber",false);
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getCertsFromCarrierPrivilegeAccessRules",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"registerImsProvisioningChangedCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterImsProvisioningChangedCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerFeatureProvisioningChangedCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterFeatureProvisioningChangedCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"setImsProvisioningStatusForCapability",null);
        hookAllMethodsWithCache_Auto(hookClass,"getImsProvisioningStatusForCapability",true);
        hookAllMethodsWithCache_Auto(hookClass,"getRcsProvisioningStatusForCapability",true);
        hookAllMethodsWithCache_Auto(hookClass,"setRcsProvisioningStatusForCapability",null);
        hookAllMethodsWithCache_Auto(hookClass,"getImsProvisioningInt",0);
        //    hookAllMethodsWithCache_Auto(hookClass,"getImsProvisioningString",String);
        hookAllMethodsWithCache_Auto(hookClass,"setImsProvisioningInt",0);
        hookAllMethodsWithCache_Auto(hookClass,"setImsProvisioningString",0);
        hookAllMethodsWithCache_Auto(hookClass,"startEmergencyCallbackMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateEmergencyNumberListTestMode",null);
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getEmergencyNumberListTestMode",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getEmergencyNumberDbVersion",1);
        hookAllMethodsWithCache_Auto(hookClass,"notifyOtaEmergencyNumberDbInstalled",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateOtaEmergencyNumberDbFilePath",null);
        hookAllMethodsWithCache_Auto(hookClass,"resetOtaEmergencyNumberDbFilePath",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableModemForSlot",true);
        hookAllMethodsWithCache_Auto(hookClass,"setMultiSimCarrierRestriction",null);
        hookAllMethodsWithCache_Auto(hookClass,"isMultiSimSupported",TelephonyManager.MULTISIM_ALLOWED,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"switchMultiSimConfig",null);
        hookAllMethodsWithCache_Auto(hookClass,"doesSwitchMultiSimConfigTriggerReboot",true,getSystemChecker_PackageNameAt(1));
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getSlotsMapping",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getRadioHalVersion",1);
        hookAllMethodsWithCache_Auto(hookClass,"getHalVersion",0);

//        hookAllMethodsWithCache_Auto(hookClass,"getCurrentPackageName",String);
        hookAllMethodsWithCache_Auto(hookClass,"isApplicationOnUicc",true);
        hookAllMethodsWithCache_Auto(hookClass,"isModemEnabledForSlot",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isDataEnabledForApn",true,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"isApnMetered",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSystemSelectionChannels",null);
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getSystemSelectionChannels",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"isMvnoMatched",true);
        //TODO:Randomize it👇(use it's callback).
        hookAllMethodsWithCache_Auto(hookClass,"enqueueSmsPickResult",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"showSwitchToManagedProfileDialog",null);
        //TODO:Randomize it👇(i got only empty str).
        hookAllMethodsWithCache_Auto(hookClass,"getMmsUserAgent","");
        //TODO:Randomize it👇(i got only empty str).
        hookAllMethodsWithCache_Auto(hookClass,"getMmsUAProfUrl","");
        hookAllMethodsWithCache_Auto(hookClass,"setMobileDataPolicyEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isMobileDataPolicyEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCepEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyRcsAutoConfigurationReceived",null);
        hookAllMethodsWithCache_Auto(hookClass,"isIccLockEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setIccLockEnabled",0);
        hookAllMethodsWithCache_Auto(hookClass,"changeIccLockPassword",0);
        hookAllMethodsWithCache_Auto(hookClass,"requestUserActivityNotification",null);
//        hookAllMethodsWithCache_Auto(hookClass,"userActivity",null);
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"getManualNetworkSelectionPlmn","46001");
        hookAllMethodsWithCache_Auto(hookClass,"canConnectTo5GInDsdsMode",true);
        //TODO:Randomize it👇.
        List<String> EquivalentHomePlmns = new ArrayList<>();
        EquivalentHomePlmns.add("46001");
        hookAllMethodsWithCache_Auto(hookClass,"getEquivalentHomePlmns",EquivalentHomePlmns,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setVoNrEnabled",0);
        //TODO:Randomize it👇.
        hookAllMethodsWithCache_Auto(hookClass,"isVoNrEnabled",false);
        hookAllMethodsWithCache_Auto(hookClass,"setNrDualConnectivityState",0);
        hookAllMethodsWithCache_Auto(hookClass,"isNrDualConnectivityEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isRadioInterfaceCapabilitySupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"sendThermalMitigationRequest",0,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"bootstrapAuthenticationRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"setBoundGbaServiceOverride",true,getSystemChecker_PackageNameAt(1));
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getBoundGbaService","");
        hookAllMethodsWithCache_Auto(hookClass,"setGbaReleaseTimeOverride",true);
        hookAllMethodsWithCache_Auto(hookClass,"getGbaReleaseTime",0);
        hookAllMethodsWithCache_Auto(hookClass,"setRcsClientConfiguration",null);
        hookAllMethodsWithCache_Auto(hookClass,"isRcsVolteSingleRegistrationCapable",false);
        hookAllMethodsWithCache_Auto(hookClass,"registerRcsProvisioningCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterRcsProvisioningCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"triggerRcsReconfiguration",null);
        hookAllMethodsWithCache_Auto(hookClass,"setRcsSingleRegistrationTestModeEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"getRcsSingleRegistrationTestModeEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceSingleRegistrationEnabledOverride",null);
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceSingleRegistrationEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCarrierSingleRegistrationEnabledOverride",true);
        hookAllMethodsWithCache_Auto(hookClass,"sendDeviceToDeviceMessage",null);
        hookAllMethodsWithCache_Auto(hookClass,"setActiveDeviceToDeviceTransport",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceToDeviceForceEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCarrierSingleRegistrationEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setImsFeatureValidationOverride",true);
        hookAllMethodsWithCache_Auto(hookClass,"getImsFeatureValidationOverride",true);
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getMobileProvisioningUrl","");
        hookAllMethodsWithCache_Auto(hookClass,"removeContactFromEab",0);
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getContactFromEab","");
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getCapabilityFromEab","");
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceUceEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceUceEnabled",null);
        //TODO:Randomize it👇.(construct RcsContactUceCapability)
//        hookAllMethodsWithCache_Auto(hookClass,"addUceRegistrationOverrideShell",RcsContactUceCapability);
//        hookAllMethodsWithCache_Auto(hookClass,"removeUceRegistrationOverrideShell",RcsContactUceCapability);
//        hookAllMethodsWithCache_Auto(hookClass,"clearUceRegistrationOverrideShell",RcsContactUceCapability);
//        hookAllMethodsWithCache_Auto(hookClass,"getLatestRcsContactUceCapabilityShell",RcsContactUceCapability);
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getLastUcePidfXmlShell","0");
        hookAllMethodsWithCache_Auto(hookClass,"removeUceRequestDisallowedStatus",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCapabilitiesRequestTimeout",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSignalStrengthUpdateRequest",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"clearSignalStrengthUpdateRequest",null,getSystemChecker_PackageNameAt(2));

        //TODO:Randomize it👇.(construct PhoneCapability)
//        hookAllMethodsWithCache_Auto(hookClass,"getPhoneCapability",PhoneCapability);

        hookAllMethodsWithCache_Auto(hookClass,"prepareForUnattendedReboot",0);
        hookAllMethodsWithCache_Auto(hookClass,"getSlicingConfig",null);
        hookAllMethodsWithCache_Auto(hookClass,"isPremiumCapabilityAvailableForPurchase",true);
        hookAllMethodsWithCache_Auto(hookClass,"purchasePremiumCapability",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerImsStateCallback",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterImsStateCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"setModemService",true);
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getModemService","");
        hookAllMethodsWithCache_Auto(hookClass,"isProvisioningRequiredForCapability",false);
        hookAllMethodsWithCache_Auto(hookClass,"isRcsProvisioningRequiredForCapability",false);
        hookAllMethodsWithCache_Auto(hookClass,"setVoiceServiceStateOverride",null,getSystemChecker_PackageNameAt(2));
//    hookAllMethodsWithCache_Auto(hookClass,"getCarrierServicePackageNameForLogicalSlot",String);
        hookAllMethodsWithCache_Auto(hookClass,"setRemovableEsimAsDefaultEuicc",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isRemovableEsimDefaultEuicc",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getDefaultRespondViaMessageApplication",new ComponentName("com.android.server.telecom",".RespondViaSmsSettings"));
        hookAllMethodsWithCache_Auto(hookClass,"getSimStateForSlotIndex",TelephonyManager.SIM_STATE_READY);
        hookAllMethodsWithCache_Auto(hookClass,"persistEmergencyCallDiagnosticData",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNullCipherAndIntegrityEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isNullCipherAndIntegrityPreferenceEnabled",true);
        //TODO:Randomize it👇.(I didn't get it)
        hookAllMethodsWithCache_Auto(hookClass,"getCellBroadcastIdRanges",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"setCellBroadcastIdRanges",null);
        hookAllMethodsWithCache_Auto(hookClass,"isDomainSelectionSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"getCarrierRestrictionStatus",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"requestSatelliteEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestIsSatelliteEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestIsDemoModeEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestIsEmergencyModeEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestIsSatelliteSupported",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestSatelliteCapabilities",null);
        hookAllMethodsWithCache_Auto(hookClass,"startSatelliteTransmissionUpdates",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopSatelliteTransmissionUpdates",null);
//    hookAllMethodsWithCache_Auto(hookClass,"provisionSatelliteService",ICancellationSignal);
        hookAllMethodsWithCache_Auto(hookClass,"deprovisionSatelliteService",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerForSatelliteProvisionStateChanged",0);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterForSatelliteProvisionStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestIsSatelliteProvisioned",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerForSatelliteModemStateChanged",0);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterForModemStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerForIncomingDatagram",0);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterForIncomingDatagram",null);
        hookAllMethodsWithCache_Auto(hookClass,"pollPendingDatagrams",null);
        hookAllMethodsWithCache_Auto(hookClass,"sendDatagram",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestTimeForNextSatelliteVisibility",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceAlignedWithSatellite",null);
        hookAllMethodsWithCache_Auto(hookClass,"setSatelliteServicePackageName",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSatelliteGatewayServicePackageName",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSatelliteListeningTimeoutDuration",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSatellitePointingUiClassName",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setDatagramControllerTimeoutDuration",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSatelliteControllerTimeoutDuration",true);
        hookAllMethodsWithCache_Auto(hookClass,"setEmergencyCallToSatelliteHandoverType",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCountryCodes",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSatelliteAccessControlOverlayConfigs",true);
        hookAllMethodsWithCache_Auto(hookClass,"setOemEnabledSatelliteProvisionStatus",true);
        hookAllMethodsWithCache_Auto(hookClass,"getShaIdFromAllowList",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"addAttachRestrictionForCarrier",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeAttachRestrictionForCarrier",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAttachRestrictionReasonsForCarrier", EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"requestNtnSignalStrength",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerForNtnSignalStrengthChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterForNtnSignalStrengthChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerForCapabilitiesChanged",0);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterForCapabilitiesChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"setShouldSendDatagramToModemInDemoMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"setDomainSelectionServiceOverride",true);
        hookAllMethodsWithCache_Auto(hookClass,"clearDomainSelectionServiceOverride",true);
        hookAllMethodsWithCache_Auto(hookClass,"isAospDomainSelectionService",true);
        hookAllMethodsWithCache_Auto(hookClass,"setEnableCellularIdentifierDisclosureNotifications",null);
        hookAllMethodsWithCache_Auto(hookClass,"isCellularIdentifierDisclosureNotificationsEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setNullCipherNotificationsEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isNullCipherNotificationsEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"getSatellitePlmnsForCarrier",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"registerForSatelliteSupportedStateChanged",0);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterForSatelliteSupportedStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerForCommunicationAllowedStateChanged",0);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterForCommunicationAllowedStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDatagramControllerBooleanConfig",true);
        hookAllMethodsWithCache_Auto(hookClass,"setIsSatelliteCommunicationAllowedForCurrentLocationCache",true);
        hookAllMethodsWithCache_Auto(hookClass,"requestSatelliteSessionStats",null);
    }


}
