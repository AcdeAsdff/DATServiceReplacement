package com.linearity.datservicereplacement.androidhooking.com.android.server.pm;

import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.hookPackageManager.IThinkShouldFilterApplication;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.hookPackageManager.fetchInfoFromSettingString;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.hookPackageManager.isSystemApplicationInfo;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.util.Pair;

import com.linearity.utils.ClassHookExecutor;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;

import java.util.List;
import java.util.Objects;

@NotFinished//i'm not sure
public class HookAppFilter {

    public static void doHook(){
        ClassHookExecutor classHookExecutor = HookAppFilter::hookFilterApplication;
        classesAndHooks.put("com.android.server.pm.AppsFilterImpl",classHookExecutor);
        classesAndHooks.put("com.android.server.pm.AppsFilterSnapshotImpl",classHookExecutor);
        classesAndHooks.put("com.android.server.pm.ComputerEngine",classHookExecutor);
        classesAndHooks.put("com.android.server.pm.ComputerEngine",hookClass -> hookAllMethodsWithCache_Auto(hookClass,"getPersistentApplications",new SimpleExecutorWithMode(MODE_AFTER, param ->{
            try {
                List<ApplicationInfo> result = (List<ApplicationInfo>) param.getResult();
                result.removeIf(applicationInfo -> !isSystemApplicationInfo(applicationInfo));
            }catch (Exception e){
                LoggerLog(e);
            }
        }),noSystemChecker));

    }

    public static void hookFilterApplication(Class<?> hookClass) {
        SimpleExecutor before = param ->{
            try {
                int callingUid = (int) param.args[1];
                if (callingUid % 100000 <= 10000){
                    return;
                }

                Object accessingSetting = param.args[0];
                Pair<String,Integer> accsessingPair = fetchInfoFromSettingString(accessingSetting);
                int callingUidFromSetting = accsessingPair.second;
                if (Objects.equals(param.args[1],callingUidFromSetting)){
                    return;
                }

                int userId;
                if (param.args.length >= 5){
                    userId = (int) param.args[4];
                }else {
                    userId = (int) param.args[param.args.length - 1];
                }
                Exception stackShower = new Exception();
                for (StackTraceElement s:stackShower.getStackTrace()){
                    if (s.toString().contains("IThinkShouldFilterApplication")){
                        return;
                    }
                }
//                ComponentName componentName = findArgByClassInArgs(param.args,ComponentName.class);
                if (IThinkShouldFilterApplication(callingUid,accessingSetting,userId)) {
                    param.setResult(true);
                    return;
                }
//                LoggerLog(getPackageNameIncludingSys(callingUid) + " got msg:" + Arrays.deepToString(param.args));
            }catch (Exception e){
//                LoggerLog(Arrays.deepToString(param.args));
                LoggerLog(e);
            }
        };
        hookAllMethodsWithCache_Auto(hookClass, "shouldFilterApplication", before,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass, "shouldFilterApplicationInternal",before,noSystemChecker);
    }
}
