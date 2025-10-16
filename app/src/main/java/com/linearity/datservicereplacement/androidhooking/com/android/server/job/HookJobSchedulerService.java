package com.linearity.datservicereplacement.androidhooking.com.android.server.job;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_HASHMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_PARCELED_LIST_SLICE;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_UidAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.HookUtils.listenClass;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;

import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;

import java.util.HashSet;
import java.util.Set;

public class HookJobSchedulerService {

    public static void doHook(){
        classesAndHooks.put("com.android.server.job.JobSchedulerService",HookJobSchedulerService::hookJobSchedulerService);
        classesAndHooks.put("com.android.server.job.JobSchedulerService$JobSchedulerStub",HookJobSchedulerService::hookIJobScheduler);
    }

    private static void hookIJobScheduler(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"schedule", JobScheduler.RESULT_SUCCESS);
        hookAllMethodsWithCache_Auto(hookClass,"enqueue", JobScheduler.RESULT_SUCCESS);
        hookAllMethodsWithCache_Auto(hookClass,"scheduleAsPackage", JobScheduler.RESULT_SUCCESS);
        hookAllMethodsWithCache_Auto(hookClass,"cancel",null);
        hookAllMethodsWithCache_Auto(hookClass,"cancelAll",null);
        hookAllMethodsWithCache_Auto(hookClass,"cancelAllInNamespace",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAllPendingJobs",EMPTY_HASHMAP);
        hookAllMethodsWithCache_Auto(hookClass,"getAllPendingJobsInNamespace",EMPTY_PARCELED_LIST_SLICE);//ParceledListSlice<JobInfo>
        hookAllMethodsWithCache_Auto(hookClass,"getPendingJob",null);//JobInfo
        hookAllMethodsWithCache_Auto(hookClass,"getPendingJobReason",JobScheduler.PENDING_JOB_REASON_UNDEFINED);
        hookAllMethodsWithCache_Auto(hookClass,"canRunUserInitiatedJobs",true);
        hookAllMethodsWithCache_Auto(hookClass,"hasRunUserInitiatedJobsPermission",true);
        hookAllMethodsWithCache_Auto(hookClass,"getStartedJobs",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getAllJobSnapshots",EMPTY_PARCELED_LIST_SLICE);
        hookAllMethodsWithCache_Auto(hookClass,"registerUserVisibleJobObserver",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterUserVisibleJobObserver",null);
        hookAllMethodsWithCache_Auto(hookClass,"notePendingUserRequestedAppStop",null);
    }

    private static void hookJobSchedulerService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"scheduleAsPackage", JobScheduler.RESULT_SUCCESS,getSystemChecker_UidAt(2));
        Set<String> toAvoid = new HashSet<>();
        toAvoid.add("scheduleAsPackage");
//        listenClass(hookClass,toAvoid);
    }
}
