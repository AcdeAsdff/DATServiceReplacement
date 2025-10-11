package com.linearity.utils.AndroidFakes.Telephony;

import static android.telephony.ServiceState.STATE_IN_SERVICE;

import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.telephony.ServiceState;

import java.lang.reflect.Field;
import java.util.ArrayList;

import de.robv.android.xposed.XposedHelpers;

public class ServiceStateUtils {
    public static Field ServiceState_mVoiceRegState;
    public static Field ServiceState_mDataRegState;
    public static Field ServiceState_mOperatorAlphaLong;
    public static Field ServiceState_mOperatorAlphaShort;
    public static Field ServiceState_mOperatorNumeric;
    public static Field ServiceState_mIsManualNetworkSelection;
    public static Field ServiceState_mIsEmergencyOnly;
    public static Field ServiceState_mCssIndicator;
    public static Field ServiceState_mNetworkId;
    public static Field ServiceState_mSystemId;
    public static Field ServiceState_mCdmaRoamingIndicator;
    public static Field ServiceState_mCdmaDefaultRoamingIndicator;
    public static Field ServiceState_mCdmaEriIconIndex;
    public static Field ServiceState_mCdmaEriIconMode;
    public static Field ServiceState_mNrFrequencyRange;
    public static Field ServiceState_mChannelNumber;
    public static Field ServiceState_mCellBandwidths;
    public static Field ServiceState_mArfcnRsrpBoost;
    public static Field ServiceState_mNetworkRegistrationInfos;
    public static Field ServiceState_mOperatorAlphaLongRaw;
    public static Field ServiceState_mOperatorAlphaShortRaw;
    public static Field ServiceState_mIsDataRoamingFromRegistration;
    public static Field ServiceState_mIsIwlanPreferred;
    static{
        ServiceState_mVoiceRegState = XposedHelpers.findField(ServiceState.class,"mVoiceRegState");//int
        ServiceState_mDataRegState = XposedHelpers.findField(ServiceState.class,"mDataRegState");//int
        ServiceState_mOperatorAlphaLong = XposedHelpers.findField(ServiceState.class,"mOperatorAlphaLong");//String
        ServiceState_mOperatorAlphaShort = XposedHelpers.findField(ServiceState.class,"mOperatorAlphaShort");//String
        ServiceState_mOperatorNumeric = XposedHelpers.findField(ServiceState.class,"mOperatorNumeric");//String
        ServiceState_mIsManualNetworkSelection = XposedHelpers.findField(ServiceState.class,"mIsManualNetworkSelection");//boolean
        ServiceState_mIsEmergencyOnly = XposedHelpers.findField(ServiceState.class,"mIsEmergencyOnly");//boolean
        ServiceState_mCssIndicator = XposedHelpers.findField(ServiceState.class,"mCssIndicator");//boolean
        ServiceState_mNetworkId = XposedHelpers.findField(ServiceState.class,"mNetworkId");//int
        ServiceState_mSystemId = XposedHelpers.findField(ServiceState.class,"mSystemId");//int
        ServiceState_mCdmaRoamingIndicator = XposedHelpers.findField(ServiceState.class,"mCdmaRoamingIndicator");//int
        ServiceState_mCdmaDefaultRoamingIndicator = XposedHelpers.findField(ServiceState.class,"mCdmaDefaultRoamingIndicator");//int
        ServiceState_mCdmaEriIconIndex = XposedHelpers.findField(ServiceState.class,"mCdmaEriIconIndex");//int
        ServiceState_mCdmaEriIconMode = XposedHelpers.findField(ServiceState.class,"mCdmaEriIconMode");//int
        ServiceState_mNrFrequencyRange = XposedHelpers.findField(ServiceState.class,"mNrFrequencyRange");//int
        ServiceState_mChannelNumber = XposedHelpers.findField(ServiceState.class,"mChannelNumber");//int
        ServiceState_mCellBandwidths = XposedHelpers.findField(ServiceState.class,"mCellBandwidths");//int[]
        ServiceState_mArfcnRsrpBoost = XposedHelpers.findField(ServiceState.class,"mArfcnRsrpBoost");//int
        ServiceState_mNetworkRegistrationInfos = XposedHelpers.findField(ServiceState.class,"mNetworkRegistrationInfos");//List<NetworkRegistrationInfo>
        ServiceState_mOperatorAlphaLongRaw = XposedHelpers.findField(ServiceState.class,"mOperatorAlphaLongRaw");//String
        ServiceState_mOperatorAlphaShortRaw = XposedHelpers.findField(ServiceState.class,"mOperatorAlphaShortRaw");//String
        ServiceState_mIsDataRoamingFromRegistration = XposedHelpers.findField(ServiceState.class,"mIsDataRoamingFromRegistration");//boolean
        ServiceState_mIsIwlanPreferred = XposedHelpers.findField(ServiceState.class,"mIsIwlanPreferred");//boolean
    }
    //TODO:Randomize mOperatorAlphaLong and more

    /*
     mServiceState={mVoiceRegState=0(IN_SERVICE), mDataRegState=0(IN_SERVICE), mChannelNumber=3745,
     duplexMode()=1, mCellBandwidths=[10000], mOperatorAlphaLong=CHN-UNICOM, mOperatorAlphaShort=UNICOM,
     isManualNetworkSelection=false(automatic), getRilVoiceRadioTechnology=14(LTE),
     getRilDataRadioTechnology=14(LTE), mCssIndicator=unsupported, mNetworkId=-1, mSystemId=-1,
     mCdmaRoamingIndicator=-1, mCdmaDefaultRoamingIndicator=-1, mIsEmergencyOnly=false,
     isUsingCarrierAggregation=false, mArfcnRsrpBoost=0,
     mNetworkRegistrationInfos=[
     NetworkRegistrationInfo{
     domain=PS transportType=WLAN registrationState=NOT_REG_OR_SEARCHING networkRegistrationState=NOT_REG_OR_SEARCHING
     roamingType=NOT_ROAMING accessNetworkTechnology=IWLAN rejectCause=0 emergencyEnabled=false availableServices=[]
     cellIdentity=null
     voiceSpecificInfo=null dataSpecificInfo=null nrState=**** rRplmn= isUsingCarrierAggregation=false
     isNonTerrestrialNetwork=TERRESTRIAL
     },
     NetworkRegistrationInfo{
     domain=CS transportType=WWAN registrationState=HOME
     networkRegistrationState=HOME
     roamingType=NOT_ROAMING accessNetworkTechnology=LTE
     rejectCause=0 emergencyEnabled=false availableServices=[VOICE,SMS,VIDEO]
     cellIdentity=
     CellIdentityLte:{you generate one,i have already made that when making fake location(although failed.)}
     voiceSpecificInfo=VoiceSpecificRegistrationInfo {
     mCssSupported=false mRoamingIndicator=0 mSystemIsInPrl=0 mDefaultRoamingIndicator=0
     }
     dataSpecificInfo=null
     nrState=**** rRplmn=46001 isUsingCarrierAggregation=false isNonTerrestrialNetwork=TERRESTRIAL
     },
     NetworkRegistrationInfo{
     domain=PS transportType=WWAN registrationState=HOME networkRegistrationState=HOME
     roamingType=NOT_ROAMING accessNetworkTechnology=LTE rejectCause=0 emergencyEnabled=false
     availableServices=[DATA,MMS]
     cellIdentity=CellIdentityLte:{you generate one}
     voiceSpecificInfo=null
     dataSpecificInfo=android.telephony.DataSpecificRegistrationInfo :{
     maxDataCalls = 16 isDcNrRestricted = false isNrAvailable = false isEnDcAvailable = false
     mLteAttachResultType = 0 mLteAttachExtraInfo = 0
     LteVopsSupportInfo :  mVopsSupport = 2 mEmcBearerSupport = 3
     }
     nrState=**** rRplmn=46001 isUsingCarrierAggregation=false isNonTerrestrialNetwork=TERRESTRIAL
     }
     ],
     mNrFrequencyRange=0, mOperatorAlphaLongRaw=CHN-UNICOM, mOperatorAlphaShortRaw=UNICOM, mIsDataRoamingFromRegistration=false, mIsIwlanPreferred=false
     }
     */
    //TODO:Generate it!
    @Deprecated
    public static ServiceState TEMP_SERVICE_STATE = constructServiceState();
    public static ServiceState constructServiceState(){
        ServiceState result = new ServiceState();
        try {
            ServiceState_mVoiceRegState.set(result,/*int*/STATE_IN_SERVICE);
            ServiceState_mDataRegState.set(result,/*int*/STATE_IN_SERVICE);
            ServiceState_mOperatorAlphaLong.set(result,/*String*/"CHN-UNICOM");
            ServiceState_mOperatorAlphaShort.set(result,/*String*/"UNICOM");
            ServiceState_mOperatorNumeric.set(result,/*String*/"46001");//yes,mcc and mnc
            ServiceState_mIsManualNetworkSelection.set(result,/*boolean*/false);
            ServiceState_mIsEmergencyOnly.set(result,/*boolean*/false);
            ServiceState_mCssIndicator.set(result,/*boolean*/true);
            ServiceState_mNetworkId.set(result,/*int*/-1);
            ServiceState_mSystemId.set(result,/*int*/-1);
            ServiceState_mCdmaRoamingIndicator.set(result,/*int*/-1);
            ServiceState_mCdmaDefaultRoamingIndicator.set(result,/*int*/-1);
            ServiceState_mCdmaEriIconIndex.set(result,/*int*/0);
            ServiceState_mCdmaEriIconMode.set(result,/*int*/0);
            ServiceState_mNrFrequencyRange.set(result,/*int*/0);
            ServiceState_mChannelNumber.set(result,/*int*/3745);
            ServiceState_mCellBandwidths.set(result,/*int[]*/new int[]{10000});
            ServiceState_mArfcnRsrpBoost.set(result,/*int*/0);
            ServiceState_mNetworkRegistrationInfos.set(result,/*List<NetworkRegistrationInfo>*/new ArrayList<>());
            ServiceState_mOperatorAlphaLongRaw.set(result,/*String*/"CHN-UNICOM");
            ServiceState_mOperatorAlphaShortRaw.set(result,/*String*/"UNICOM");
            ServiceState_mIsDataRoamingFromRegistration.set(result,/*boolean*/false);
            ServiceState_mIsIwlanPreferred.set(result,/*boolean*/false);
        }catch (Exception e){
            LoggerLog(e);
        }
        return result;
    }
}
