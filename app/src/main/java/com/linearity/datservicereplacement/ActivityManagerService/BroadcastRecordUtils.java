package com.linearity.datservicereplacement.ActivityManagerService;

import static com.linearity.datservicereplacement.ActivityManagerService.ProcessRecordUtils.initProcessRecordUtils;
import static com.linearity.datservicereplacement.ActivityManagerService.ProcessRecordUtils.isSystemProcessRecord;
import static com.linearity.datservicereplacement.ActivityManagerService.ProcessRecordUtils.processRecordToString;
import static com.linearity.datservicereplacement.ReturnIfNonSys.sysAppCheckerAndOperation;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.content.Intent;

import com.linearity.utils.SystemAppChecker;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.XposedHelpers;

public class BroadcastRecordUtils {

    public static Class<?> BROADCAST_RECORD_CLASS;
    public static Field callerAppField;//ProcessRecord
    public static Field resultToAppField;//ProcessRecord
    public static Field callingPackageField;//String
    public static Field callingUidField;//int
    public static Field intent;//Intent
    public static void initBroadcastRecordUtils(ClassLoader classLoader){
        initProcessRecordUtils(classLoader);
        BROADCAST_RECORD_CLASS = XposedHelpers.findClass("com.android.server.am.BroadcastRecord",classLoader);
        callerAppField = XposedHelpers.findField(BROADCAST_RECORD_CLASS,"callerApp");
        resultToAppField = XposedHelpers.findField(BROADCAST_RECORD_CLASS,"resultToApp");
//        callingPackageField = XposedHelpers.findField(BROADCAST_RECORD_CLASS,"callingPackage");
        callingUidField = XposedHelpers.findField(BROADCAST_RECORD_CLASS,"callingUid");
        intent = XposedHelpers.findField(BROADCAST_RECORD_CLASS,"intent");
    }
    public static String  broadcastRecordToString(Object broadcastRecord){
        try {
            Object callerProcessRecord = callerAppField.get(broadcastRecord);
            Object resultToProcessRecord = resultToAppField.get(broadcastRecord);
            return processRecordToString(callerProcessRecord) + " || " + processRecordToString(resultToProcessRecord);
        }catch (Exception e){
            LoggerLog(e);
        }
        return null;
    }
    public static Intent getIntentFromBroadcastRecord(Object broadcastRecord){
        try {
            return (Intent) intent.get(broadcastRecord);
        }catch (Exception e){
            LoggerLog(e);
        }
        return null;
    }
    public static boolean isBroadcastRecordSystem2System(Object broadcastRecord){
        try {
            if (broadcastRecord == null){
                return true;//null receiver
            }
            return isSystemProcessRecord(callerAppField.get(broadcastRecord))
                    && isSystemProcessRecord(resultToAppField.get(broadcastRecord));
        }catch (Exception e){
            LoggerLog(e);
        }
        return true;
    }
    public static final Map<Integer,SystemAppChecker> getSystemChecker_BroadcastRecordAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_BroadcastRecordAt(int index){
        if (getSystemChecker_BroadcastRecordAt_map.containsKey(index)){
            return getSystemChecker_BroadcastRecordAt_map.get(index);
        }
        SystemAppChecker ret = param -> {
            int tempIndex = index;
            if (tempIndex < 0){
                tempIndex += param.args.length;
            }
            try{
                return isBroadcastRecordSystem2System(param.args[tempIndex]);
            }catch (Exception e){
                LoggerLog(e);
                return true;
            }
        };

        getSystemChecker_BroadcastRecordAt_map.put(index,ret);
        return ret;
    }
    public static BroadcastRecordInformation outputInformation(Object broadcastRecord){
        BroadcastRecordInformation result = new BroadcastRecordInformation();

        try {
            Object callerProcessRecord = callerAppField.get(broadcastRecord);
            Object resultToProcessRecord = resultToAppField.get(broadcastRecord);
            result.infoA = ProcessRecordUtils.outputInformation(callerProcessRecord);
            result.infoB = ProcessRecordUtils.outputInformation(resultToProcessRecord);
        }catch (Exception e){
            LoggerLog(e);
        }
        result.intent = getIntentFromBroadcastRecord(broadcastRecord);
        return result;
    }
    public static class BroadcastRecordInformation{
        ProcessRecordUtils.ProcessRecordInformation infoA = null;
        ProcessRecordUtils.ProcessRecordInformation infoB = null;
        Intent intent = null;
    }
}
