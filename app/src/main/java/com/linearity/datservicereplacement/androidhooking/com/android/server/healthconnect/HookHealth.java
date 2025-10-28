package com.linearity.datservicereplacement.androidhooking.com.android.server.healthconnect;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_HASHMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexAndObjectInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_AttributionSourceAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.HookUtils.listenClass;
import static com.linearity.utils.HookUtils.listenMethod;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.PublicSeed.publicSeed;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.os.BatteryManager;
import android.os.BatteryProperty;
import android.os.Binder;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;

@NotFinished//not sure
public class HookHealth {
    public static void doHook(){
        classesAndHooks.put("com.android.server.healthconnect.HealthConnectServiceImpl", HookHealth::hookIHealthConnectService);
        classesAndHooks.put("com.android.server.health.HealthServiceWrapperAidl",HookHealth::hookHealthServiceWrapper);
        classesAndHooks.put("com.android.server.health.HealthServiceWrapperHidl",HookHealth::hookHealthServiceWrapper);
    }

    public static void hookHealthServiceWrapper(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getProperty",(SimpleExecutor) param -> {
            try {
                Integer id = findClassIndexAndObjectInArgs(param.args,Integer.class).second;
                BatteryProperty property = findClassIndexAndObjectInArgs(param.args,BatteryProperty.class).second;
                if (id == null){
                    LoggerLog(new Exception("id null"));
                    return;
                }
                if (property == null){
                    LoggerLog(new Exception("property null"));
                    return;
                }
                ExtendedRandom signRandom = new ExtendedRandom(
                        Binder.getCallingUid() * publicSeed
                                + "BATTERY_PROPERTY_RANDOM".hashCode() * publicSeed
                );
                long capacityUAhMax = signRandom.nextInt(1000000) + 5000000;
                long capacityUAhCurrent = capacityUAhMax - (long)(capacityUAhMax * signRandom.nextSmallDouble(0.5));
                long currentNow = signRandom.pickFromArray(new long[]{
                        (signRandom.nextInt(250000) - 400000),
                        (signRandom.nextInt(1500000) + 300000),
                        (signRandom.nextInt(2000000) + 2000000),
                });
                switch (id){
                    case BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER -> property.setLong(capacityUAhCurrent);
                    case BatteryManager.BATTERY_PROPERTY_CURRENT_NOW -> property.setLong(currentNow);
                    case BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE -> property.setLong(currentNow + signRandom.nextInt(10000) * signRandom.nextSignLong());
                    case BatteryManager.BATTERY_PROPERTY_CAPACITY -> property.setLong((capacityUAhCurrent * 100) / capacityUAhMax);
                    case BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER -> property.setLong(Long.MIN_VALUE); //TODO:Implement if need for a phone(Google Pixel supports)
                    case BatteryManager.BATTERY_PROPERTY_STATUS -> property.setLong(currentNow > 0 ? 2 : 3);
                    default -> property.setLong(Long.MIN_VALUE);
                }
            }catch (Exception e){
                LoggerLog(e);
            }finally {
                param.setResult(0);
            }
        });
        hookAllMethodsWithCache_Auto(hookClass,"getHealthInfo",null);
//        listenClass(hookClass);
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
