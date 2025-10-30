package com.linearity.utils.AndroidFakes.ActivityManagerService;

import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.content.pm.ApplicationInfo;
import android.util.Pair;

import java.lang.reflect.Field;

import de.robv.android.xposed.XposedHelpers;

/**
 * i mean com.android.server.am.ProcessRecord
 */
public class ProcessRecordUtils {
    public static Class<?> PROCESS_RECORD_CLASS;
    public static Field applicationInfoField;
    private static Field processInfoField;
    private static Field uidField;
    public static void initProcessRecordUtils(ClassLoader classLoader){
        PROCESS_RECORD_CLASS = XposedHelpers.findClass("com.android.server.am.ProcessRecord",classLoader);
        applicationInfoField = XposedHelpers.findField(PROCESS_RECORD_CLASS,"info");
        processInfoField = XposedHelpers.findField(PROCESS_RECORD_CLASS,"processInfo");
        uidField = XposedHelpers.findField(PROCESS_RECORD_CLASS,"uid");
    }
    public static ProcessRecordInformation outputInformation(Object processRecord){
        ProcessRecordInformation info = new ProcessRecordInformation();
        try {
            info.appInfo = (ApplicationInfo) applicationInfoField.get(processRecord);
        }catch (Exception e){
            LoggerLog(e);
        }
        try {
            info.uid = (int) uidField.get(processRecord);
        }catch (Exception e){
            LoggerLog(e);
        }
        return info;
    }
    public static String processRecordToString(Object processRecord){
        Pair<ApplicationInfo,Integer> appInfoPair = getApplicationInfoAndUidFromProcessRecord(processRecord);
        if (appInfoPair == null){
            return "[processRecord:null]";
        }
        StringBuilder sb = new StringBuilder();
        if (appInfoPair.first != null){
            sb.append("ProcessRecord{")
                    .append(appInfoPair.first.packageName).append('|')
                    .append(appInfoPair.first.processName).append('|')
                    .append(appInfoPair.first.uid).append('|')
                    .append(appInfoPair.second).append('}');
            return sb.toString();
        }else {
            return String.valueOf(appInfoPair);
        }
    }
    public static Pair<ApplicationInfo,Integer> getApplicationInfoAndUidFromProcessRecord(Object processRecord){

        try {
            ApplicationInfo appInfo = (ApplicationInfo) applicationInfoField.get(processRecord);
            int uid = (int) uidField.get(processRecord);
            return new Pair<>(appInfo,uid);
        }catch (Exception e){
            LoggerLog(e);
        }
        return null;
    }

    public static class ProcessRecordInformation{
        public int uid = -1;
        public ApplicationInfo appInfo = null;

    }
}
