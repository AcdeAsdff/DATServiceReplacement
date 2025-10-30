package com.linearity.datservicereplacement.androidhooking.com.android.server.am;

import static com.linearity.datservicereplacement.ReturnIfNonSys.CONSTRUCTOR_METHOD_STRING;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.ProcessListUtils.addProcessList;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import com.linearity.utils.SimpleExecutorWithMode;

public class HookProcessList {
    public static void doHook(){
        classesAndHooks.put("com.android.server.am.ProcessList", HookProcessList::hookProcessList);
    }

    private static void hookProcessList(Class<?> hookClass){
        SimpleExecutorWithMode registerProcessList = new SimpleExecutorWithMode(MODE_AFTER,param -> addProcessList(param.thisObject));
        hookAllMethodsWithCache_Auto(hookClass,CONSTRUCTOR_METHOD_STRING,registerProcessList,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"getLRURecordForAppLOSP",registerProcessList,noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"updateLruProcessLSP",registerProcessList,noSystemChecker);
    }
}
