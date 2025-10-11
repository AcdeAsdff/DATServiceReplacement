package com.linearity.datservicereplacement.androidhooking.com.android.server.healthconnect;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_HASHMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_AttributionSourceAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import com.linearity.utils.NotFinished;

@NotFinished//not sure
public class HookHealth {
    public static void doHook(){
        classesAndHooks.put("com.android.server.healthconnect.HealthConnectServiceImpl", HookHealth::hookIHealthConnectService);
    }

    public static void hookIHealthConnectService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"grantHealthPermission",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"revokeHealthPermission",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"revokeAllHealthPermissions",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getGrantedHealthPermissions",EMPTY_ARRAYLIST,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getHealthPermissionsFlags",EMPTY_HASHMAP,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setHealthPermissionsUserFixedFlagValue",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getHistoricalAccessStartDateInMilliseconds",0L,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"insertRecords",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"aggregateRecords",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"readRecords",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"updateRecords",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getChangeLogToken",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getChangeLogs",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"deleteUsingFilters",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"deleteUsingFiltersForSelf",null,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentPriority",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"updatePriority",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setRecordRetentionPeriodInDays",null);
        hookAllMethodsWithCache_Auto(hookClass,"getRecordRetentionPeriodInDays",0);
        hookAllMethodsWithCache_Auto(hookClass,"getContributorApplicationsInfo",null);
        hookAllMethodsWithCache_Auto(hookClass,"queryAllRecordTypesInfo",null);
        hookAllMethodsWithCache_Auto(hookClass,"queryAccessLogs",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getActivityDates",null);
        hookAllMethodsWithCache_Auto(hookClass,"startMigration",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"finishMigration",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"writeMigrationData",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"insertMinDataMigrationSdkExtensionVersion",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"stageAllHealthConnectRemoteData",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAllDataForBackup",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAllBackupFileNames",null);//BackupFileNamesSet
        hookAllMethodsWithCache_Auto(hookClass,"deleteAllStagedRemoteData",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateDataDownloadState",null);
        hookAllMethodsWithCache_Auto(hookClass,"getHealthConnectDataState",null);
        hookAllMethodsWithCache_Auto(hookClass,"getHealthConnectMigrationUiState",null);
        hookAllMethodsWithCache_Auto(hookClass,"configureScheduledExport",null);
        hookAllMethodsWithCache_Auto(hookClass,"getScheduledExportPeriodInDays",0);
        hookAllMethodsWithCache_Auto(hookClass,"queryDocumentProviders",null);
        hookAllMethodsWithCache_Auto(hookClass,"getScheduledExportStatus",null);
        hookAllMethodsWithCache_Auto(hookClass,"setLowerRateLimitsForTesting",null);
        hookAllMethodsWithCache_Auto(hookClass,"getImportStatus",null);
        hookAllMethodsWithCache_Auto(hookClass,"runImport",null);
        hookAllMethodsWithCache_Auto(hookClass,"readMedicalResources",null,getSystemChecker_AttributionSourceAt(0));
    }

}
