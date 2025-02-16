package com.linearity.datservicereplacement.Telecom;

import static android.telecom.PhoneAccount.SCHEME_SIP;
import static android.telecom.PhoneAccount.SCHEME_TEL;
import static android.telecom.PhoneAccount.SCHEME_VOICEMAIL;
import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.getPackageName;
import static com.linearity.datservicereplacement.ReturnIfNonSys.ParceledListSliceClass;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_executeIfNonSys;
import static com.linearity.datservicereplacement.StartHook.ORDERED_PHONE_NUMBER;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.UserHandle;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;

import com.linearity.datservicereplacement.NFC.HookNFC;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutorWithMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

@NotFinished
public class HookTelecomService {

    public static void doHook(){
        classesAndHooks.put("com.android.server.telecom.TelecomServiceImpl", hookClass -> XposedBridge.hookAllConstructors(hookClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Object mBinderImpl = XposedHelpers.getObjectField(param.thisObject,"mBinderImpl");
                if (mBinderImpl == null){return;}
                hookITelecomService(mBinderImpl.getClass());

            }
        }));
    }
    public static final ComponentName TELEPHONY_CONN_SERVICE_COMPONENT_NAME = new ComponentName("com.android.phone","com.android.services.telephony.TelephonyConnectionService");
    public static final String[] NETWORK_OPERATOR_NAMES = new String[]{"China Telecom","China Mobile","China Unicom"};
    public static final int[][] PHONE_NUMBER_PART_A = new int[][]{
            {130,131,132,145,155,156,166,175,176,185,186,196,}, //Telecom
            {135,136,137,138,139,147,150,151,152,157,158,159,178,182,183,184,187,188,195,197,198,},// Mobile
            {133,153,173,177,180,181,189,190,191,193,199,},  //Unicom
    };
    public static final String[] SUPPORTED_URI_SCHEMES = new String[]{SCHEME_SIP,SCHEME_TEL,SCHEME_VOICEMAIL};
    public static final Uri ICC_ADN = Uri.parse("content://icc/adn");
    public static final Class<?> TELECOM_ANALYTICS_CLASS = XposedHelpers.findClass("android.telecom.TelecomAnalytics",XposedBridge.BOOTCLASSLOADER);
    public static final String ACTION_MANAGE_BLOCKED_NUMBERS = "android.telecom.action.MANAGE_BLOCKED_NUMBERS";
    public static final Intent MANAGE_BLOCKED_NUMBERS_INTENT = new Intent(ACTION_MANAGE_BLOCKED_NUMBERS);
    public static void hookITelecomService(Class<?> hookClass){
        SimpleExecutorWithMode tweakPhoneAccountHandle = new SimpleExecutorWithMode(MODE_AFTER,param -> {
            PhoneAccountHandle pHandleBefore = (PhoneAccountHandle) param.getResult();
            String pkgName = getPackageName(Binder.getCallingUid());
            ExtendedRandom extendedRandom = new ExtendedRandom(Binder.getCallingUid()*5L);
            if (pkgName != null){
                extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode());
            }
            UserHandle userHandle = (UserHandle) XposedHelpers.newInstance(UserHandle.class,0);
            PhoneAccountHandle result;
            ComponentName componentName = TELEPHONY_CONN_SERVICE_COMPONENT_NAME;
            if (pHandleBefore != null){
                if (pHandleBefore.getId() != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pHandleBefore.getId().hashCode());
                }
                if (pHandleBefore.getComponentName() != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pHandleBefore.getComponentName().hashCode());
                    componentName = pHandleBefore.getComponentName();
                }
            }
            result = new PhoneAccountHandle(
                    componentName,
                    extendedRandom.nextRandomHexUpper(16),
                    userHandle
            );
            param.setResult(result);
        });
        SimpleExecutorWithMode tweakPhoneAccountHandleList = new SimpleExecutorWithMode(MODE_AFTER,param -> {
            List<PhoneAccountHandle> defaultResult = ((List<PhoneAccountHandle>)XposedHelpers.callMethod(param.getResult(),"getList"));
            PhoneAccountHandle pHandleBefore = null;
            if (!defaultResult.isEmpty()){
                pHandleBefore = (PhoneAccountHandle) defaultResult.get(0);
            }
            String pkgName = getPackageName(Binder.getCallingUid());
            ExtendedRandom extendedRandom = new ExtendedRandom(Binder.getCallingUid()*5L);
            if (pkgName != null){
                extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode());
            }
            UserHandle userHandle = (UserHandle) XposedHelpers.newInstance(UserHandle.class,0);
            List<PhoneAccountHandle> resultList = new ArrayList<>();
            Object result = XposedHelpers.newInstance(ParceledListSliceClass,new Class[]{List.class},resultList);
            ComponentName componentName = TELEPHONY_CONN_SERVICE_COMPONENT_NAME;
            if (pHandleBefore != null){
                if (pHandleBefore.getId() != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pHandleBefore.getId().hashCode());
                }
                if (pHandleBefore.getComponentName() != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pHandleBefore.getComponentName().hashCode());
                    componentName = pHandleBefore.getComponentName();
                }
            }
            resultList.add(new PhoneAccountHandle(
                    componentName,
                    extendedRandom.nextRandomHexUpper(16),
                    userHandle
            ));
            param.setResult(result);
        });
        SimpleExecutorWithMode tweakPhoneAccountHandleList_noEmpty = new SimpleExecutorWithMode(MODE_AFTER,param -> {
            List<PhoneAccountHandle> defaultResult = ((List<PhoneAccountHandle>)XposedHelpers.callMethod(param.getResult(),"getList"));
            PhoneAccountHandle pHandleBefore = null;
            if (!defaultResult.isEmpty()){
                pHandleBefore = (PhoneAccountHandle) defaultResult.get(0);
            }
            String pkgName = getPackageName(Binder.getCallingUid());
            ExtendedRandom extendedRandom = new ExtendedRandom(Binder.getCallingUid()*5L);
            if (pkgName != null){
                extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode());
            }
            UserHandle userHandle = (UserHandle) XposedHelpers.newInstance(UserHandle.class,0);
            List<PhoneAccountHandle> resultList = new ArrayList<>();
            Object result = XposedHelpers.newInstance(ParceledListSliceClass,new Class[]{List.class},resultList);
            ComponentName componentName = TELEPHONY_CONN_SERVICE_COMPONENT_NAME;
            if (pHandleBefore != null){
                if (pHandleBefore.getId() != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pHandleBefore.getId().hashCode());
                }
                if (pHandleBefore.getComponentName() != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pHandleBefore.getComponentName().hashCode());
                    componentName = pHandleBefore.getComponentName();
                }
            }
            resultList.add(new PhoneAccountHandle(
                    componentName,
                    extendedRandom.nextRandomHexUpper(16),
                    userHandle
            ));
            param.setResult(result);
        });
        SimpleExecutorWithMode tweakPhoneAccount = new SimpleExecutorWithMode(MODE_AFTER,param -> {
            PhoneAccountHandle pHandleBefore = null;
            if (param.getResult() != null){
                pHandleBefore = ((PhoneAccount) param.getResult()).getAccountHandle();
            }
            String pkgName = getPackageName(Binder.getCallingUid());
            ExtendedRandom extendedRandom = new ExtendedRandom(Binder.getCallingUid()*5L);
            if (pkgName != null){
                extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode());
            }
            UserHandle userHandle = (UserHandle) XposedHelpers.newInstance(UserHandle.class,0);
            PhoneAccountHandle result;
            ComponentName componentName = TELEPHONY_CONN_SERVICE_COMPONENT_NAME;
            if (pHandleBefore != null){
                if (pHandleBefore.getId() != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pHandleBefore.getId().hashCode());
                }
                if (pHandleBefore.getComponentName() != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pHandleBefore.getComponentName().hashCode());
                    componentName = pHandleBefore.getComponentName();
                }
            }
            result = new PhoneAccountHandle(
                    componentName,
                    extendedRandom.nextRandomHexUpper(16),
                    userHandle
            );
            extendedRandom.reset();
            int index = extendedRandom.nextInt(3);
            PhoneAccount.Builder builder = new PhoneAccount.Builder(result,NETWORK_OPERATOR_NAMES[index]);
            for (String s:SUPPORTED_URI_SCHEMES){
                builder.addSupportedUriScheme(s);
            }
            builder.setHighlightColor(16777216);
            builder.setIcon(null);
            builder.setShortDescription("");
            String phoneNum = extendedRandom.pickFromArray(PHONE_NUMBER_PART_A[index]) + extendedRandom.nextRandomDecimal(8);
            if (!(Objects.equals(ORDERED_PHONE_NUMBER, ""))){
                phoneNum = ORDERED_PHONE_NUMBER;
            }
            LoggerLog(new Exception("generated phone num:" + phoneNum));
            Uri telUri = Uri.parse("tel:"+ phoneNum);
            builder.setAddress(telUri);
            builder.setSubscriptionAddress(telUri);
            builder.setCapabilities(Integer.MIN_VALUE);
            param.setResult(builder.build());
        });
        SimpleExecutorWithMode tweakPhoneAccount_Number = new SimpleExecutorWithMode(MODE_AFTER,param -> {
            PhoneAccountHandle pHandleBefore = null;
            if (param.getResult() != null){
                pHandleBefore = ((PhoneAccount) param.getResult()).getAccountHandle();
            }
            String pkgName = getPackageName(Binder.getCallingUid());
            ExtendedRandom extendedRandom = new ExtendedRandom(Binder.getCallingUid()*5L);
            if (pkgName != null){
                extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode());
            }
            UserHandle userHandle = (UserHandle) XposedHelpers.newInstance(UserHandle.class,0);
            PhoneAccountHandle result;
            ComponentName componentName = TELEPHONY_CONN_SERVICE_COMPONENT_NAME;
            if (pHandleBefore != null){
                if (pHandleBefore.getId() != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pHandleBefore.getId().hashCode());
                }
                if (pHandleBefore.getComponentName() != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pHandleBefore.getComponentName().hashCode());
                    componentName = pHandleBefore.getComponentName();
                }
            }
            result = new PhoneAccountHandle(
                    componentName,
                    extendedRandom.nextRandomHexUpper(16),
                    userHandle
            );
            extendedRandom.reset();
            int index = extendedRandom.nextInt(3);
            PhoneAccount.Builder builder = new PhoneAccount.Builder(result,NETWORK_OPERATOR_NAMES[index]);
            for (String s:SUPPORTED_URI_SCHEMES){
                builder.addSupportedUriScheme(s);
            }
            builder.setHighlightColor(16777216);
            builder.setIcon(null);
            builder.setShortDescription("");
            String phoneNum = extendedRandom.pickFromArray(PHONE_NUMBER_PART_A[index]) + extendedRandom.nextRandomDecimal(8);
//            Uri telUri = Uri.parse("tel:"+ phoneNum);
//            builder.setAddress(telUri);
//            builder.setSubscriptionAddress(telUri);
//            builder.setCapabilities(Integer.MIN_VALUE);
            if (!(Objects.equals(ORDERED_PHONE_NUMBER, ""))){
                phoneNum = ORDERED_PHONE_NUMBER;
            }
            LoggerLog(new Exception("generated phone num:" + phoneNum));
            param.setResult(phoneNum);
        });
        SimpleExecutorWithMode generateEmptyCallAnalyticsDump = new SimpleExecutorWithMode(MODE_BEFORE, param -> {
            Object emptyTelecomAnalytics = XposedHelpers.newInstance(
                    TELECOM_ANALYTICS_CLASS
                    ,new ArrayList<>(),new ArrayList<>());
            param.setResult(emptyTelecomAnalytics);
        });

        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"showInCallScreen",null);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getDefaultOutgoingPhoneAccount",tweakPhoneAccountHandle);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getUserSelectedOutgoingPhoneAccount",tweakPhoneAccountHandle);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setUserSelectedOutgoingPhoneAccount",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getCallCapablePhoneAccounts",tweakPhoneAccountHandleList);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getSelfManagedPhoneAccounts",tweakPhoneAccountHandleList);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getOwnSelfManagedPhoneAccounts",tweakPhoneAccountHandleList);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getPhoneAccountsSupportingScheme",tweakPhoneAccountHandleList_noEmpty);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getPhoneAccountsForPackage",tweakPhoneAccountHandleList);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getPhoneAccount",tweakPhoneAccount);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAllPhoneAccountsCount",0);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAllPhoneAccounts",tweakPhoneAccountHandleList);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAllPhoneAccountHandles",tweakPhoneAccountHandleList);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getSimCallManager",tweakPhoneAccountHandle);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getSimCallManagerForUser",tweakPhoneAccountHandle);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"registerPhoneAccount",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"unregisterPhoneAccount",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"clearAccounts",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isVoiceMailNumber",true);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getVoiceMailNumber",tweakPhoneAccount_Number);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getLine1Number",tweakPhoneAccount_Number);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getDefaultPhoneApp",TELEPHONY_CONN_SERVICE_COMPONENT_NAME);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getDefaultDialerPackage",TELEPHONY_CONN_SERVICE_COMPONENT_NAME.getPackageName());
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getDefaultDialerPackageForUser",TELEPHONY_CONN_SERVICE_COMPONENT_NAME.getPackageName());
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getSystemDialerPackage",TELEPHONY_CONN_SERVICE_COMPONENT_NAME.getPackageName());
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"dumpCallAnalytics",generateEmptyCallAnalyticsDump);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"silenceRinger",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isInCall",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"hasManageOngoingCallsPermission",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isInManagedCall",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isRinging",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getCallState",0);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getCallStateUsingPackage",0);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"endCall",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"acceptRingingCall",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"acceptRingingCallWithVideoState",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"cancelMissedCallsNotification",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"handlePinMmi",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"handlePinMmiForPhoneAccount",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getAdnUriForPhoneAccount",ICC_ADN);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isTtySupported",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"getCurrentTtyMode",0);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"addNewIncomingCall",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"addNewIncomingConference",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"addNewUnknownCall",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startConference",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"placeCall",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"enablePhoneAccount",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setDefaultDialer",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopBlockSuppression",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"createManageBlockedNumbersIntent",MANAGE_BLOCKED_NUMBERS_INTENT);
//        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"createLaunchEmergencyDialerIntent",Intent);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isIncomingCallPermitted",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isOutgoingCallPermitted",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"waitOnHandlers",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"acceptHandover",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setTestEmergencyPhoneAccountPackageNameFilter",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isInEmergencyCall",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"handleCallIntent",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"cleanupStuckCalls",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"cleanupOrphanPhoneAccounts",0);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isNonUiInCallServiceBound",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"resetCarMode",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setTestDefaultCallRedirectionApp",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"requestLogMark",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setTestPhoneAcctSuggestionComponent",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setTestDefaultCallScreeningApp",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"addOrRemoveTestCallCompanionApp",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setSystemDialer",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setTestDefaultDialer",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setTestCallDiagnosticService",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isInSelfManagedCall",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"addCall",null);
    }

}
