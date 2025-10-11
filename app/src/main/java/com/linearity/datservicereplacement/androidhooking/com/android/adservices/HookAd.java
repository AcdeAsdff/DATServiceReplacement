package com.linearity.datservicereplacement.androidhooking.com.android.adservices;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

public class HookAd {

    public static void doHook(){
        classesAndHooks.put("com.android.adservices.service.common.AdServicesCommonServiceImpl",HookAd::hookIAdServicesCommonService);
        classesAndHooks.put("com.android.adservices.service.appsetid.AppSetIdServiceImpl",HookAd::hookIAppSetIdService);
        classesAndHooks.put("com.android.adservices.service.adid.AdIdServiceImpl",HookAd::hookIAdIdService);
        classesAndHooks.put("com.android.adservices.service.measurement.MeasurementServiceImpl",HookAd::hookIMeasurementService);
        classesAndHooks.put("com.android.adservices.service.adselection.AdSelectionServiceImpl",HookAd::hookAdSelectionService);
        classesAndHooks.put("com.android.adservices.service.customaudience.CustomAudienceServiceImpl",HookAd::hookICustomAudienceService);
        classesAndHooks.put("com.android.adservices.service.topics.TopicsServiceImpl",HookAd::hookITopicsService);

    }
    public static void hookITopicsService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getTopics",null);
    }

    public static void hookICustomAudienceService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"joinCustomAudience",null);
        hookAllMethodsWithCache_Auto(hookClass,"fetchAndJoinCustomAudience",null);
        hookAllMethodsWithCache_Auto(hookClass,"leaveCustomAudience",null);
        hookAllMethodsWithCache_Auto(hookClass,"scheduleCustomAudienceUpdate",null);
        hookAllMethodsWithCache_Auto(hookClass,"overrideCustomAudienceRemoteInfo",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeCustomAudienceRemoteInfoOverride",null);
        hookAllMethodsWithCache_Auto(hookClass,"resetAllCustomAudienceOverrides",null);
    }
    public static void hookAdSelectionService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getAdSelectionData",null);
        hookAllMethodsWithCache_Auto(hookClass,"persistAdSelectionResult",null);
        hookAllMethodsWithCache_Auto(hookClass,"selectAds",null);
        hookAllMethodsWithCache_Auto(hookClass,"selectAdsFromOutcomes",null);
        hookAllMethodsWithCache_Auto(hookClass,"reportImpression",null);
        hookAllMethodsWithCache_Auto(hookClass,"reportInteraction",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateAdCounterHistogram",null);
        hookAllMethodsWithCache_Auto(hookClass,"overrideAdSelectionConfigRemoteInfo",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAppInstallAdvertisers",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeAdSelectionConfigRemoteInfoOverride",null);
        hookAllMethodsWithCache_Auto(hookClass,"resetAllAdSelectionConfigRemoteOverrides",null);
        hookAllMethodsWithCache_Auto(hookClass,"overrideAdSelectionFromOutcomesConfigRemoteInfo",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeAdSelectionFromOutcomesConfigRemoteInfoOverride",null);
        hookAllMethodsWithCache_Auto(hookClass,"resetAllAdSelectionFromOutcomesConfigRemoteOverrides",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAdCounterHistogramOverride",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeAdCounterHistogramOverride",null);
        hookAllMethodsWithCache_Auto(hookClass,"resetAllAdCounterHistogramOverrides",null);
    }
    public static void hookIMeasurementService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"register",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerWebSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerWebTrigger",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"getMeasurementApiStatus",null);
        hookAllMethodsWithCache_Auto(hookClass,"deleteRegistrations",null);
    }

    public static void hookIAdIdService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getAdId",null);
    }
    public static void hookIAppSetIdService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getAppSetId",null);
    }

    public static void hookIAdServicesCommonService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"isAdServicesEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAdServicesEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableAdServices",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateAdIdCache",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAdServicesCommonStates",null);
    }

}
