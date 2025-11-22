package com.linearity.datservicereplacement.androidhooking.com.android.server.healthconnect;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_HASHMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexAndObjectInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_AttributionSourceAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.PublicSeed.publicSeed;

import android.os.BatteryManager;
import android.os.BatteryProperty;
import android.os.Binder;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;

import java.lang.reflect.Field;

import de.robv.android.xposed.XposedHelpers;

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
                long currentAvg = currentNow + signRandom.nextInt(10000) * signRandom.nextSignLong();
                long capacity = (capacityUAhCurrent * 100) / capacityUAhMax;
                switch (id){
                    case BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER -> property.setLong(capacityUAhCurrent);
                    case BatteryManager.BATTERY_PROPERTY_CURRENT_NOW -> property.setLong(currentNow);
                    case BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE -> property.setLong(currentAvg);
                    case BatteryManager.BATTERY_PROPERTY_CAPACITY -> property.setLong(capacity);
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
        ClassLoader loader = hookClass.getClassLoader();
        hookAllMethodsWithCache_Auto(hookClass,"getHealthInfo",(SimpleExecutor)param -> param.setResult(constructHealthInfo(loader,Binder.getCallingUid())));
//        listenClass(hookClass);
    }
    private static Object constructHealthInfo(ClassLoader loader,int uid) {
        Object info = null;
        try {

            ExtendedRandom signRandom = new ExtendedRandom(
                    uid * publicSeed
                            + "BATTERY_PROPERTY_RANDOM".hashCode() * publicSeed
            );
            long capacityUAhMax = signRandom.nextInt(1000000) + 5000000;
            long capacityUAhCurrent = capacityUAhMax - (long)(capacityUAhMax * signRandom.nextSmallDouble(0.5));
            long currentNow = signRandom.pickFromArray(new long[]{
                    (signRandom.nextInt(250000) - 400000),
                    (signRandom.nextInt(1500000) + 300000),
                    (signRandom.nextInt(2000000) + 2000000),
            });
            long currentAvg = currentNow + signRandom.nextInt(10000) * signRandom.nextSignLong();
            long capacity = (capacityUAhCurrent * 100) / capacityUAhMax;
            int maxMicroAmps = 1000000 * (signRandom.nextInt(3)+4);
            int maxMicroVolts = 1000000 * (signRandom.nextInt(4)+5);
            int voltageNow =  (1000000 * signRandom.nextInt(2)) + 3000000;
            int batteryCycles = signRandom.nextInt(100)+30;

            long secondsToGetMaxCharge = currentNow<=0?-1:(capacityUAhMax - capacityUAhCurrent)*3600L/currentAvg;
            // Load the system HealthInfo class
            Class<?> clsHealthInfo = loader.loadClass("android.hardware.health.HealthInfo");

            // Create a new instance
            info = clsHealthInfo.getConstructor().newInstance();

            // Helper to set fields
            Field field;
            XposedHelpers.setBooleanField(info,"chargerAcOnline",true);
            XposedHelpers.setBooleanField(info,"chargerUsbOnline",false);
            XposedHelpers.setBooleanField(info,"chargerWirelessOnline",false);
            XposedHelpers.setBooleanField(info,"chargerDockOnline",false);
            XposedHelpers.setIntField(info,"maxChargingCurrentMicroamps", maxMicroAmps);
            XposedHelpers.setIntField(info,"maxChargingVoltageMicrovolts",maxMicroVolts);

            // Enums
            Class<?> clsStatus = loader.loadClass("android.hardware.health.BatteryStatus");
            Class<?> clsHealth = loader.loadClass("android.hardware.health.BatteryHealth");
            Class<?> clsCapacityLevel = loader.loadClass("android.hardware.health.BatteryCapacityLevel");
            Class<?> clsChargingState = loader.loadClass("android.hardware.health.BatteryChargingState");
            Class<?> clsChargingPolicy = loader.loadClass("android.hardware.health.BatteryChargingPolicy");

            XposedHelpers.setObjectField(info,"batteryStatus", Enum.valueOf((Class<Enum>) clsStatus, "CHARGING"));
            XposedHelpers.setObjectField(info,"batteryHealth", Enum.valueOf((Class<Enum>) clsHealth, "GOOD"));
            String capLevelStr = "LOW";
            if (capacity >= 30){
                capLevelStr = "NORMAL";
            }
            if (capacity >= 90){
                capLevelStr = "HIGH";
            }
            if (capacity == 100){
                capLevelStr = "FULL";
            }
            XposedHelpers.setObjectField(info,"batteryCapacityLevel", Enum.valueOf((Class<Enum>) clsCapacityLevel, capLevelStr));
            XposedHelpers.setObjectField(info,"chargingState", Enum.valueOf((Class<Enum>) clsChargingState, "NORMAL"));
            XposedHelpers.setObjectField(info,"chargingPolicy", Enum.valueOf((Class<Enum>) clsChargingPolicy, "DEFAULT"));

            // Core metrics
            XposedHelpers.setBooleanField(info,"batteryPresent",true);
            XposedHelpers.setIntField(info,"batteryLevel", (int)capacity);
            XposedHelpers.setIntField(info,"batteryVoltageMillivolts", voltageNow/1000);
            XposedHelpers.setIntField(info,"batteryTemperatureTenthsCelsius", 320); // 32.0°C
            XposedHelpers.setIntField(info,"batteryCurrentMicroamps", (int) currentNow); // discharging
            XposedHelpers.setIntField(info,"batteryCurrentAverageMicroamps", (int) currentAvg);
            XposedHelpers.setIntField(info,"batteryCycleCount", batteryCycles);
            XposedHelpers.setIntField(info,"batteryFullChargeUah", (int) capacityUAhMax);
            XposedHelpers.setIntField(info,"batteryChargeCounterUah", (int) capacityUAhCurrent);
            XposedHelpers.setIntField(info,"batteryFullChargeDesignCapacityUah", (int) capacityUAhMax);
            XposedHelpers.setObjectField(info,"batteryTechnology", "Li-ion");
            XposedHelpers.setLongField(info,"batteryChargeTimeToFullNowSeconds", secondsToGetMaxCharge);

            // Disk & storage info
            XposedHelpers.setObjectField(info,"diskStats", null);
            XposedHelpers.setObjectField(info,"storageInfos", null);

            // Optional object
            XposedHelpers.setObjectField(info,"batteryHealthData", null);
        }catch (Throwable e){
            LoggerLog(e);
        }

        return info;
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
