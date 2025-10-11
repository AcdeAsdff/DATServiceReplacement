package com.linearity.datservicereplacement.androidhooking.com.android.server.credentials;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

public class HookCredential {

    public static void doHook(){
        classesAndHooks.put("com.android.server.credentials.CredentialManagerService$CredentialManagerServiceStub", HookCredential::hookICredentialManager);

    }
    public static void hookICredentialManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"executeGetCredential",null,getSystemChecker_PackageNameAt(2));//ICancellationSignal
        hookAllMethodsWithCache_Auto(hookClass,"executePrepareGetCredential",null,getSystemChecker_PackageNameAt(3));//ICancellationSignal
        hookAllMethodsWithCache_Auto(hookClass,"executeCreateCredential",null,getSystemChecker_PackageNameAt(2));//ICancellationSignal
        hookAllMethodsWithCache_Auto(hookClass,"getCandidateCredentials",null,getSystemChecker_PackageNameAt(3));//ICancellationSignal
        hookAllMethodsWithCache_Auto(hookClass,"clearCredentialState",null,getSystemChecker_PackageNameAt(2));//ICancellationSignal
        hookAllMethodsWithCache_Auto(hookClass,"setEnabledProviders",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerCredentialDescription",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterCredentialDescription",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"isEnabledCredentialProviderService",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getCredentialProviderServices",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getCredentialProviderServicesForTesting",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"isServiceEnabled",true);
    }

}
