package com.linearity.datservicereplacement.Phone;

import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.StartHook.publicSeed;

import com.linearity.utils.ExtendedRandom;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class GsmCdmaPhoneConstructor {
    public static String DEVICE_ID;
    public static String DEVICE_IMEI_0;
    public static String DEVICE_IMEI_1;
    public static String DEVICE_MEID_0;
    public static String DEVICE_MEID_1;
    static {
        ExtendedRandom extendedRandom = new ExtendedRandom("com.android.internal.telephony.GsmCdmaPhone".hashCode() * publicSeed ^ Integer.MAX_VALUE);
        DEVICE_ID = extendedRandom.nextPackageName(" ");
        DEVICE_IMEI_0 = extendedRandom.nextRandomDecimal(15);
        DEVICE_IMEI_1 = extendedRandom.nextRandomDecimal(15);
        DEVICE_MEID_0 = extendedRandom.nextRandomDecimal(15);
        DEVICE_MEID_1 = extendedRandom.nextRandomDecimal(15);
    }
    public static Class<?> GsmCdmaPhoneClass = XposedHelpers.findClassIfExists("com.android.internal.telephony.GsmCdmaPhone", XposedBridge.BOOTCLASSLOADER);
    public static Field GsmCdmaPhone_mSsnRegistrants;
    public static Field GsmCdmaPhone_mCdmaSSM;
    public static Field GsmCdmaPhone_mCdmaSubscriptionSource;
    public static Field GsmCdmaPhone_mWakeLock;
    public static Field GsmCdmaPhone_mEcmExitRespRegistrant;
    public static Field GsmCdmaPhone_mEsn;
    public static Field GsmCdmaPhone_mMeid;
    public static Field GsmCdmaPhone_mCarrierOtaSpNumSchema;
    public static Field GsmCdmaPhone_mUiccApplicationsEnabled;
    public static Field GsmCdmaPhone_mIsTestingEmergencyCallbackMode;
    public static Field GsmCdmaPhone_mExitEcmRunnable;
    public static Field GsmCdmaPhone_mSimRecords;
    public static Field GsmCdmaPhone_mManualNetworkSelectionPlmn;
    public static Field GsmCdmaPhone_mIsimUiccRecords;
    public static Field GsmCdmaPhone_mCT;
    public static Field GsmCdmaPhone_mSST;
    public static Field GsmCdmaPhone_mEmergencyNumberTracker;
    public static Field GsmCdmaPhone_mPendingMMIs;
    public static Field GsmCdmaPhone_mIccPhoneBookIntManager;
    public static Field GsmCdmaPhone_mPrecisePhoneType;
    public static Field GsmCdmaPhone_mEcmTimerResetRegistrants;
    public static Field GsmCdmaPhone_mVolteSilentRedialRegistrants;
    public static Field GsmCdmaPhone_mDialArgs;
    public static Field GsmCdmaPhone_mEmergencyDomainSelectedRegistrants;
    public static Field GsmCdmaPhone_mImei;
    public static Field GsmCdmaPhone_mImeiSv;
    public static Field GsmCdmaPhone_mVmNumber;
    public static Field GsmCdmaPhone_mImeiType;
    public static Field GsmCdmaPhone_mSimState;
    public static Field GsmCdmaPhone_mCellBroadcastConfigTracker;
    public static Field GsmCdmaPhone_mIsNullCipherAndIntegritySupported;
    public static Field GsmCdmaPhone_mIsIdentifierDisclosureTransparencySupported;
    public static Field GsmCdmaPhone_mIsNullCipherNotificationSupported;
    public static Field GsmCdmaPhone_mIccSmsInterfaceManager;
    public static Field GsmCdmaPhone_mResetModemOnRadioTechnologyChange;
    public static Field GsmCdmaPhone_mSsOverCdmaSupported;
    public static Field GsmCdmaPhone_mRilVersion;
    public static Field GsmCdmaPhone_mBroadcastEmergencyCallStateChanges;
    public static Field GsmCdmaPhone_mTelecomVoiceServiceStateOverride;
    public static Field GsmCdmaPhone_mCDM;
    public static Field GsmCdmaPhone_mCIM;
    public static Field GsmCdmaPhone_mImsManagerFactory;
    public static Field GsmCdmaPhone_mCarrierPrivilegesTracker;
    public static Field GsmCdmaPhone_mSubscriptionsChangedListener;
    public static Field GsmCdmaPhone_mCallWaitingController;
    public static Field GsmCdmaPhone_mSafetySource;
    public static Field GsmCdmaPhone_mIdentifierDisclosureNotifier;
    public static Field GsmCdmaPhone_mNullCipherNotifier;
    public static Field GsmCdmaPhone_mN1ModeDisallowedReasons;
    public static Field GsmCdmaPhone_mModemN1Mode;
    public static Field GsmCdmaPhone_mBroadcastReceiver;
    static{
        if (GsmCdmaPhoneClass != null){
            GsmCdmaPhone_mSsnRegistrants = XposedHelpers.findField(GsmCdmaPhoneClass, "mSsnRegistrants");//RegistrantList
            GsmCdmaPhone_mCdmaSSM = XposedHelpers.findField(GsmCdmaPhoneClass, "mCdmaSSM");//CdmaSubscriptionSourceManager
            GsmCdmaPhone_mCdmaSubscriptionSource = XposedHelpers.findField(GsmCdmaPhoneClass, "mCdmaSubscriptionSource");//int
            GsmCdmaPhone_mWakeLock = XposedHelpers.findField(GsmCdmaPhoneClass, "mWakeLock");//PowerManager.WakeLock
            GsmCdmaPhone_mEcmExitRespRegistrant = XposedHelpers.findField(GsmCdmaPhoneClass, "mEcmExitRespRegistrant");//Registrant
            GsmCdmaPhone_mEsn = XposedHelpers.findField(GsmCdmaPhoneClass, "mEsn");//String
            GsmCdmaPhone_mMeid = XposedHelpers.findField(GsmCdmaPhoneClass, "mMeid");//String
            GsmCdmaPhone_mCarrierOtaSpNumSchema = XposedHelpers.findField(GsmCdmaPhoneClass, "mCarrierOtaSpNumSchema");//String
            GsmCdmaPhone_mUiccApplicationsEnabled = XposedHelpers.findField(GsmCdmaPhoneClass, "mUiccApplicationsEnabled");//Boolean
            GsmCdmaPhone_mIsTestingEmergencyCallbackMode = XposedHelpers.findField(GsmCdmaPhoneClass, "mIsTestingEmergencyCallbackMode");//boolean
            GsmCdmaPhone_mExitEcmRunnable = XposedHelpers.findField(GsmCdmaPhoneClass, "mExitEcmRunnable");//Runnable
            GsmCdmaPhone_mSimRecords = XposedHelpers.findField(GsmCdmaPhoneClass, "mSimRecords");//SIMRecords
            GsmCdmaPhone_mManualNetworkSelectionPlmn = XposedHelpers.findField(GsmCdmaPhoneClass, "mManualNetworkSelectionPlmn");//String
            GsmCdmaPhone_mIsimUiccRecords = XposedHelpers.findField(GsmCdmaPhoneClass, "mIsimUiccRecords");//IsimUiccRecords
            GsmCdmaPhone_mCT = XposedHelpers.findField(GsmCdmaPhoneClass, "mCT");//GsmCdmaCallTracker
            GsmCdmaPhone_mSST = XposedHelpers.findField(GsmCdmaPhoneClass, "mSST");//ServiceStateTracker
            GsmCdmaPhone_mEmergencyNumberTracker = XposedHelpers.findField(GsmCdmaPhoneClass, "mEmergencyNumberTracker");//EmergencyNumberTracker
            GsmCdmaPhone_mPendingMMIs = XposedHelpers.findField(GsmCdmaPhoneClass, "mPendingMMIs");//ArrayList<MmiCode>
            GsmCdmaPhone_mIccPhoneBookIntManager = XposedHelpers.findField(GsmCdmaPhoneClass, "mIccPhoneBookIntManager");//IccPhoneBookInterfaceManager
            GsmCdmaPhone_mPrecisePhoneType = XposedHelpers.findField(GsmCdmaPhoneClass, "mPrecisePhoneType");//int
            GsmCdmaPhone_mEcmTimerResetRegistrants = XposedHelpers.findField(GsmCdmaPhoneClass, "mEcmTimerResetRegistrants");//RegistrantList
            GsmCdmaPhone_mVolteSilentRedialRegistrants = XposedHelpers.findField(GsmCdmaPhoneClass, "mVolteSilentRedialRegistrants");//RegistrantList
            GsmCdmaPhone_mDialArgs = XposedHelpers.findField(GsmCdmaPhoneClass, "mDialArgs");//DialArgs
            GsmCdmaPhone_mEmergencyDomainSelectedRegistrants = XposedHelpers.findField(GsmCdmaPhoneClass, "mEmergencyDomainSelectedRegistrants");//RegistrantList
            GsmCdmaPhone_mImei = XposedHelpers.findField(GsmCdmaPhoneClass, "mImei");//String
            GsmCdmaPhone_mImeiSv = XposedHelpers.findField(GsmCdmaPhoneClass, "mImeiSv");//String
            GsmCdmaPhone_mVmNumber = XposedHelpers.findField(GsmCdmaPhoneClass, "mVmNumber");//String
            GsmCdmaPhone_mImeiType = XposedHelpers.findField(GsmCdmaPhoneClass, "mImeiType");//int
            GsmCdmaPhone_mSimState = XposedHelpers.findField(GsmCdmaPhoneClass, "mSimState");//int
            GsmCdmaPhone_mCellBroadcastConfigTracker = XposedHelpers.findField(GsmCdmaPhoneClass, "mCellBroadcastConfigTracker");//CellBroadcastConfigTracker
            GsmCdmaPhone_mIsNullCipherAndIntegritySupported = XposedHelpers.findField(GsmCdmaPhoneClass, "mIsNullCipherAndIntegritySupported");//boolean
            GsmCdmaPhone_mIsIdentifierDisclosureTransparencySupported = XposedHelpers.findField(GsmCdmaPhoneClass, "mIsIdentifierDisclosureTransparencySupported");//boolean
            GsmCdmaPhone_mIsNullCipherNotificationSupported = XposedHelpers.findField(GsmCdmaPhoneClass, "mIsNullCipherNotificationSupported");//boolean
            GsmCdmaPhone_mIccSmsInterfaceManager = XposedHelpers.findField(GsmCdmaPhoneClass, "mIccSmsInterfaceManager");//IccSmsInterfaceManager
            GsmCdmaPhone_mResetModemOnRadioTechnologyChange = XposedHelpers.findField(GsmCdmaPhoneClass, "mResetModemOnRadioTechnologyChange");//boolean
            GsmCdmaPhone_mSsOverCdmaSupported = XposedHelpers.findField(GsmCdmaPhoneClass, "mSsOverCdmaSupported");//boolean
            GsmCdmaPhone_mRilVersion = XposedHelpers.findField(GsmCdmaPhoneClass, "mRilVersion");//int
            GsmCdmaPhone_mBroadcastEmergencyCallStateChanges = XposedHelpers.findField(GsmCdmaPhoneClass, "mBroadcastEmergencyCallStateChanges");//boolean
            GsmCdmaPhone_mTelecomVoiceServiceStateOverride = XposedHelpers.findField(GsmCdmaPhoneClass, "mTelecomVoiceServiceStateOverride");//int
            GsmCdmaPhone_mCDM = XposedHelpers.findField(GsmCdmaPhoneClass, "mCDM");//CarrierKeyDownloadManager
            GsmCdmaPhone_mCIM = XposedHelpers.findField(GsmCdmaPhoneClass, "mCIM");//CarrierInfoManager
            GsmCdmaPhone_mImsManagerFactory = XposedHelpers.findField(GsmCdmaPhoneClass, "mImsManagerFactory");//ImsManagerFactory
            GsmCdmaPhone_mCarrierPrivilegesTracker = XposedHelpers.findField(GsmCdmaPhoneClass, "mCarrierPrivilegesTracker");//CarrierPrivilegesTracker
            GsmCdmaPhone_mSubscriptionsChangedListener = XposedHelpers.findField(GsmCdmaPhoneClass, "mSubscriptionsChangedListener");//SubscriptionManager.OnSubscriptionsChangedListener
            GsmCdmaPhone_mCallWaitingController = XposedHelpers.findField(GsmCdmaPhoneClass, "mCallWaitingController");//CallWaitingController
            GsmCdmaPhone_mSafetySource = XposedHelpers.findField(GsmCdmaPhoneClass, "mSafetySource");//CellularNetworkSecuritySafetySource
            GsmCdmaPhone_mIdentifierDisclosureNotifier = XposedHelpers.findField(GsmCdmaPhoneClass, "mIdentifierDisclosureNotifier");//CellularIdentifierDisclosureNotifier
            GsmCdmaPhone_mNullCipherNotifier = XposedHelpers.findField(GsmCdmaPhoneClass, "mNullCipherNotifier");//NullCipherNotifier
            GsmCdmaPhone_mN1ModeDisallowedReasons = XposedHelpers.findField(GsmCdmaPhoneClass, "mN1ModeDisallowedReasons");//Set<Integer>
            GsmCdmaPhone_mModemN1Mode = XposedHelpers.findField(GsmCdmaPhoneClass, "mModemN1Mode");//Boolean
            GsmCdmaPhone_mBroadcastReceiver = XposedHelpers.findField(GsmCdmaPhoneClass, "mBroadcastReceiver");//BroadcastReceiver
        }
    }
    public static Constructor<?> GsmCdmaPhoneConstructor = null;
}
