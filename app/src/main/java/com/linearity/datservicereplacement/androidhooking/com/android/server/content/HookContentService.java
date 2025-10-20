package com.linearity.datservicereplacement.androidhooking.com.android.server.content;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_BUNDLE;
import static com.linearity.datservicereplacement.ReturnIfNonSys.defaultSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_SYNC_ADAPTER_TYPE_ARRAY;

import android.content.SyncAdapterType;
import android.content.SyncStatusInfo;

import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SystemAppChecker;

@NotFinished
public class HookContentService {

    public static void doHook(){
        classesAndHooks.put("com.android.server.content.ContentService",HookContentService::hookIContentService);

    }

    private static void hookIContentService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"unregisterContentObserver",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerContentObserver",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyChange",null,
                param -> getSystemChecker_PackageNameAt(6).checkSystemApp(param) && defaultSystemChecker.checkSystemApp(param));
        hookAllMethodsWithCache_Auto(hookClass,"requestSync",null,
                param -> getSystemChecker_PackageNameAt(3).checkSystemApp(param) && defaultSystemChecker.checkSystemApp(param));
        hookAllMethodsWithCache_Auto(hookClass,"sync",null,
                param -> getSystemChecker_PackageNameAt(1).checkSystemApp(param) && defaultSystemChecker.checkSystemApp(param));
        hookAllMethodsWithCache_Auto(hookClass,"syncAsUser",null,
                param -> getSystemChecker_PackageNameAt(2).checkSystemApp(param) && defaultSystemChecker.checkSystemApp(param));
        hookAllMethodsWithCache_Auto(hookClass,"cancelSync",null);
        hookAllMethodsWithCache_Auto(hookClass,"cancelSyncAsUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"cancelRequest",null);
        hookAllMethodsWithCache_Auto(hookClass,"getSyncAutomatically",true);
        hookAllMethodsWithCache_Auto(hookClass,"getSyncAutomaticallyAsUser",true);
        hookAllMethodsWithCache_Auto(hookClass,"setSyncAutomatically",null);
        hookAllMethodsWithCache_Auto(hookClass,"setSyncAutomaticallyAsUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPeriodicSyncs",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"addPeriodicSync",null);
        hookAllMethodsWithCache_Auto(hookClass,"removePeriodicSync",null);
        hookAllMethodsWithCache_Auto(hookClass,"getIsSyncable",1);
        hookAllMethodsWithCache_Auto(hookClass,"getIsSyncableAsUser",1);
        hookAllMethodsWithCache_Auto(hookClass,"setIsSyncable",null);
        hookAllMethodsWithCache_Auto(hookClass,"setIsSyncableAsUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"setMasterSyncAutomatically",null);
        hookAllMethodsWithCache_Auto(hookClass,"setMasterSyncAutomaticallyAsUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"getMasterSyncAutomatically",true);
        hookAllMethodsWithCache_Auto(hookClass,"getMasterSyncAutomaticallyAsUser",true);
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentSyncs",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentSyncsAsUser",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getSyncAdapterTypes",EMPTY_SYNC_ADAPTER_TYPE_ARRAY);//SyncAdapterType[]
        hookAllMethodsWithCache_Auto(hookClass,"getSyncAdapterTypesAsUser",EMPTY_SYNC_ADAPTER_TYPE_ARRAY);//SyncAdapterType[]
        hookAllMethodsWithCache_Auto(hookClass,"getSyncAdapterPackagesForAuthorityAsUser", EmptyArrays.EMPTY_STRING_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getSyncAdapterPackageAsUser",null);//String
        hookAllMethodsWithCache_Auto(hookClass,"isSyncActive",true);
        hookAllMethodsWithCache_Auto(hookClass,"getSyncStatus", null);//SyncStatusInfo //TODO:Generate?
        hookAllMethodsWithCache_Auto(hookClass,"getSyncStatusAsUser",null);//SyncStatusInfo
        hookAllMethodsWithCache_Auto(hookClass,"isSyncPending",true);
        hookAllMethodsWithCache_Auto(hookClass,"isSyncPendingAsUser",true);
        hookAllMethodsWithCache_Auto(hookClass,"addStatusChangeListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeStatusChangeListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"putCache",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCache",EMPTY_BUNDLE);//Bundle
        hookAllMethodsWithCache_Auto(hookClass,"resetTodayStats",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDbCorruption",null);
    }

}
