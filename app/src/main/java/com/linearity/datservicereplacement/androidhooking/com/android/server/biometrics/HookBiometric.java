package com.linearity.datservicereplacement.androidhooking.com.android.server.biometrics;

import static android.content.Context.AUTH_SERVICE;
import static android.content.Context.FINGERPRINT_SERVICE;
import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_MAX_STRENGTH;
import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.PublicSeed.publicSeed;
import static com.linearity.datservicereplacement.StartHook.registerServiceHook_map;

import android.hardware.biometrics.BiometricAuthenticator;
import android.os.Binder;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;

@NotFinished
//TODO:Randomize
public class HookBiometric {

    public static void doHook(){
        classesAndHooks.put("com.android.server.biometrics.BiometricService$BiometricServiceWrapper",HookBiometric::hookIBiometricService);
        classesAndHooks.put("com.android.server.biometrics.sensors.face.FaceService$FaceServiceWrapper",HookBiometric::hookIFaceService);
        classesAndHooks.put("com.android.server.biometrics.sensors.iris.IrisService$IrisServiceWrapper",HookBiometric::hookIIrisService);
        hookPublishBinderService();
    }
    public static void hookPublishBinderService(){
        registerServiceHook_map.put(FINGERPRINT_SERVICE,c -> {
            hookIFingerprintService(c);
            return null;
        });
        registerServiceHook_map.put(AUTH_SERVICE,c -> {
            hookIAuthService(c);
            return null;
        });
    }
    public static void hookIIrisService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"registerAuthenticators",null);
    }

    public static void hookIFaceService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"createTestSession",null,getSystemChecker_PackageNameAt(2));//ITestSession
        hookAllMethodsWithCache_Auto(hookClass,"dumpSensorServiceStateProto",EmptyArrays.EMPTY_BYTE_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getSensorPropertiesInternal",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getSensorProperties",null,getSystemChecker_PackageNameAt(1));//FaceSensorPropertiesInternal
        hookAllMethodsWithCache_Auto(hookClass,"authenticate",(SimpleExecutor)param -> {
            ExtendedRandom extendedRandom = new ExtendedRandom("com.android.server.biometrics.sensors.face.FaceService$FaceServiceWrapper".hashCode()
                    & (long) Binder.getCallingUid() / "authenticate".hashCode() - publicSeed * publicSeed);
            extendedRandom.nextLongArr(extendedRandom.nextInt(30),1);
            param.setResult(extendedRandom.nextLong());
        });
        hookAllMethodsWithCache_Auto(hookClass,"detectFace",0L);
        hookAllMethodsWithCache_Auto(hookClass,"prepareForAuthentication",null);
        hookAllMethodsWithCache_Auto(hookClass,"startPreparedClient",null);
        hookAllMethodsWithCache_Auto(hookClass,"cancelAuthentication",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"cancelFaceDetect",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"cancelAuthenticationFromService",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"enroll",0L,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"enrollRemotely",0L,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"cancelEnrollment",null);
        hookAllMethodsWithCache_Auto(hookClass,"remove",null,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"removeAll",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"getEnrolledFaces",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"isHardwareDetected",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"generateChallenge",null,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"revokeChallenge",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"hasEnrolledFaces",true,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getLockoutModeForUser",0);
        hookAllMethodsWithCache_Auto(hookClass,"invalidateAuthenticatorId",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAuthenticatorId",0L);
        hookAllMethodsWithCache_Auto(hookClass,"resetLockout",null,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"addLockoutResetCallback",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setFeature",null,getSystemChecker_PackageNameAt(6));
        hookAllMethodsWithCache_Auto(hookClass,"getFeature",null,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"registerAuthenticators",null);
        hookAllMethodsWithCache_Auto(hookClass,"addAuthenticatorsRegisteredCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerAuthenticationStateListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterAuthenticationStateListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerBiometricStateListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"scheduleWatchdog",null);
    }

    public static void hookIAuthService(Class<?> hookClass){
        long timeMills = (System.currentTimeMillis());//hint:it won't change once inited;
        hookAllMethodsWithCache_Auto(hookClass,"createTestSession",null,getSystemChecker_PackageNameAt(2));//ITestSession
        hookAllMethodsWithCache_Auto(hookClass,"getSensorProperties",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getUiPackage","com.android.launcher3");//String
        hookAllMethodsWithCache_Auto(hookClass,"authenticate",(SimpleExecutor)param -> {
            ExtendedRandom extendedRandom = new ExtendedRandom("com/android/server/biometrics/AuthService.java".hashCode()
                    & (long) Binder.getCallingUid() / "authenticate".hashCode() - publicSeed * publicSeed);
            extendedRandom.nextLongArr(extendedRandom.nextInt(30),1);
            param.setResult(extendedRandom.nextLong());
        },getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"cancelAuthentication",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"canAuthenticate",BIOMETRIC_STRONG,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getLastAuthenticationTime",(SimpleExecutor)param -> {
            ExtendedRandom extendedRandom = new ExtendedRandom("com/android/server/biometrics/AuthService.java".hashCode()
                    & (long) Binder.getCallingUid() * "getLastAuthenticationTime".hashCode() - publicSeed);
            param.setResult(timeMills + extendedRandom.nextInt(1000*60));
        });
        hookAllMethodsWithCache_Auto(hookClass,"hasEnrolledBiometrics",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"registerEnabledOnKeyguardCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerAuthenticationStateListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterAuthenticationStateListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"invalidateAuthenticatorIds",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAuthenticatorIds",(SimpleExecutor)param -> {
            ExtendedRandom extendedRandom = new ExtendedRandom("com.android.server.biometrics.AuthService".hashCode() & Binder.getCallingUid());
            extendedRandom.nextSign();
            extendedRandom = new ExtendedRandom(extendedRandom.nextLong() ^ publicSeed);
            param.setResult(extendedRandom.nextLongArr(extendedRandom.nextInt(10)+2,Long.MAX_VALUE));
        });
        hookAllMethodsWithCache_Auto(hookClass,"resetLockoutTimeBound",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"resetLockout",null);
        hookAllMethodsWithCache_Auto(hookClass,"getButtonLabel","",getSystemChecker_PackageNameAt(1));//CharSequence
        hookAllMethodsWithCache_Auto(hookClass,"getPromptMessage","",getSystemChecker_PackageNameAt(1));//CharSequence
        hookAllMethodsWithCache_Auto(hookClass,"getSettingName","",getSystemChecker_PackageNameAt(1));//CharSequence
    }

    public static void hookIBiometricService(Class<?> hookClass){
        long timeMills = (System.currentTimeMillis());//hint:it won't change once inited;
        hookAllMethodsWithCache_Auto(hookClass,"createTestSession",null,getSystemChecker_PackageNameAt(-1));//ITestSession
        hookAllMethodsWithCache_Auto(hookClass,"getSensorProperties",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"authenticate",(SimpleExecutor)param -> {
            ExtendedRandom extendedRandom = new ExtendedRandom("com.android.server.biometrics.BiometricService$BiometricServiceWrapper".hashCode()
                    & (long) Binder.getCallingUid() / "authenticate".hashCode() - publicSeed * publicSeed);
            extendedRandom.nextLongArr(extendedRandom.nextInt(30),1);
            param.setResult(extendedRandom.nextLong());
        },getSystemChecker_PackageNameAt(-2));
        hookAllMethodsWithCache_Auto(hookClass,"cancelAuthentication",null,getSystemChecker_PackageNameAt(-2));
        hookAllMethodsWithCache_Auto(hookClass,"canAuthenticate",BIOMETRIC_STRONG,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getLastAuthenticationTime",(SimpleExecutor)param -> {
            ExtendedRandom extendedRandom = new ExtendedRandom("com.android.server.biometrics.BiometricService$BiometricServiceWrapper".hashCode()
                    & (long) Binder.getCallingUid() * "getLastAuthenticationTime".hashCode() - publicSeed);
            param.setResult(timeMills + extendedRandom.nextInt(1000*60));
        });
        hookAllMethodsWithCache_Auto(hookClass,"hasEnrolledBiometrics",true,getSystemChecker_PackageNameAt(-1));
        hookAllMethodsWithCache_Auto(hookClass,"registerAuthenticator",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerEnabledOnKeyguardCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"onReadyForAuthentication",null);
        hookAllMethodsWithCache_Auto(hookClass,"invalidateAuthenticatorIds",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAuthenticatorIds",(SimpleExecutor)param -> {
            ExtendedRandom extendedRandom = new ExtendedRandom("com.android.server.biometrics.BiometricService$BiometricServiceWrapper".hashCode() & Binder.getCallingUid());
            extendedRandom.nextSign();
            extendedRandom = new ExtendedRandom(extendedRandom.nextLong() ^ publicSeed);
            param.setResult(extendedRandom.nextLongArr(extendedRandom.nextInt(10)+2,Long.MAX_VALUE));
        });
        hookAllMethodsWithCache_Auto(hookClass,"resetLockoutTimeBound",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"resetLockout",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentStrength",BIOMETRIC_MAX_STRENGTH);
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentModality", BiometricAuthenticator.TYPE_FACE,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getSupportedModalities",BiometricAuthenticator.TYPE_FACE);
    }
    public static void hookIFingerprintService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"createTestSession",null,getSystemChecker_PackageNameAt(2));//ITestSession
        hookAllMethodsWithCache_Auto(hookClass,"dumpSensorServiceStateProto",EmptyArrays.EMPTY_BYTE_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getSensorPropertiesInternal",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getSensorProperties",null,getSystemChecker_PackageNameAt(1));//FingerprintSensorPropertiesInternal
        hookAllMethodsWithCache_Auto(hookClass,"authenticate",0L);
        hookAllMethodsWithCache_Auto(hookClass,"detectFingerprint",0L);
        hookAllMethodsWithCache_Auto(hookClass,"prepareForAuthentication",null);
        hookAllMethodsWithCache_Auto(hookClass,"startPreparedClient",null);
        hookAllMethodsWithCache_Auto(hookClass,"cancelAuthentication",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"cancelFingerprintDetect",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"cancelAuthenticationFromService",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"enroll",0L,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"cancelEnrollment",null);
        hookAllMethodsWithCache_Auto(hookClass,"remove",null,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"removeAll",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"rename",null);
        hookAllMethodsWithCache_Auto(hookClass,"getEnrolledFingerprints",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isHardwareDetectedDeprecated",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isHardwareDetected",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"generateChallenge",null,getSystemChecker_PackageNameAt(4));
        hookAllMethodsWithCache_Auto(hookClass,"revokeChallenge",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"hasEnrolledFingerprintsDeprecated",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"hasEnrolledFingerprints",true,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getLockoutModeForUser",0);
        hookAllMethodsWithCache_Auto(hookClass,"invalidateAuthenticatorId",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAuthenticatorId",0L);
        hookAllMethodsWithCache_Auto(hookClass,"resetLockout",null);
        hookAllMethodsWithCache_Auto(hookClass,"addLockoutResetCallback",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isClientActive",true);
        hookAllMethodsWithCache_Auto(hookClass,"addClientActiveCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeClientActiveCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerAuthenticators",null);
        hookAllMethodsWithCache_Auto(hookClass,"addAuthenticatorsRegisteredCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPointerDown",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPointerUp",null);
        hookAllMethodsWithCache_Auto(hookClass,"onUdfpsUiEvent",null);
        hookAllMethodsWithCache_Auto(hookClass,"setIgnoreDisplayTouches",null);
        hookAllMethodsWithCache_Auto(hookClass,"setUdfpsOverlayController",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerAuthenticationStateListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterAuthenticationStateListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerBiometricStateListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"onPowerPressed",null);
        hookAllMethodsWithCache_Auto(hookClass,"scheduleWatchdog",null);
    }


}
