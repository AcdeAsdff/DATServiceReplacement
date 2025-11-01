package com.linearity.datservicereplacement.androidhooking.com.android.server.alarm;

import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.isHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.newWeakSet;
import static com.linearity.datservicereplacement.StartHook.registerServiceHook_map;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.content.Context;

import com.linearity.utils.NotFinished;

import java.util.HashSet;
import java.util.Set;

@NotFinished
public class HookAlarm {
    public static void doHook() {
//        classesAndHooks.put("android.app.AlarmManager$ListenerWrapper", HookAlarm::hookIAlarmListener);
//        classesAndHooks.put("com.android.server.alarm.AlarmManagerService$DeliveryTracker", HookAlarm::hookIAlarmCompleteListener);
        hookPublishBinderService();
//        LoggerLog("Alarm hooked");
    }
    public static void hookPublishBinderService(){
        registerServiceHook_map.put(Context.ALARM_SERVICE,c -> {
            hookIAlarmManager(c);
            return null;
        });
    }

    public static void hookIAlarmCompleteListener(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"alarmComplete",null);
    }
    public static void hookIAlarmListener(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"doAlarm",null);
    }
    public static final Set<Class<?>> IAlarmManagerHookedPool = newWeakSet();
    //TODO:Randomize
    public static void hookIAlarmManager(Class<?> hookClass){
        if (isHookedPoolRegistered(hookClass,IAlarmManagerHookedPool)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"set",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setTime",true);
        hookAllMethodsWithCache_Auto(hookClass,"setTimeZone",null);
        hookAllMethodsWithCache_Auto(hookClass,"remove",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeAll",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getNextWakeFromIdleTime",0L);
        hookAllMethodsWithCache_Auto(hookClass,"getNextAlarmClock",null);//AlarmManager.AlarmClockInfo
        hookAllMethodsWithCache_Auto(hookClass,"getNextAlarmClockImpl",null);//AlarmManager.AlarmClockInfo
        hookAllMethodsWithCache_Auto(hookClass,"canScheduleExactAlarms",true);
        hookAllMethodsWithCache_Auto(hookClass,"hasScheduleExactAlarm",true);
        hookAllMethodsWithCache_Auto(hookClass,"getConfigVersion",0);
    }
}
