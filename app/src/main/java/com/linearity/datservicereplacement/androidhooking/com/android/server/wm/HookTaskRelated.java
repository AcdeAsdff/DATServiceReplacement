package com.linearity.datservicereplacement.androidhooking.com.android.server.wm;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.isSystemTask;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import com.linearity.utils.SimpleExecutorWithMode;

import java.util.List;

public class HookTaskRelated {

    public static void doHook(){
        classesAndHooks.put("com.android.server.wm.TaskPersister",HookTaskRelated::hookTaskPersister);
    }

    private static void hookTaskPersister(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"restoreTasksForUserLocked",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            try {
                List<?> tasks = (List<?>) param.getResult();
                tasks.removeIf(task -> !isSystemTask(task));
            }catch (Exception e){
                LoggerLog(e);
            }
        }),noSystemChecker);
    }
}
