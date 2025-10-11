package com.linearity.datservicereplacement.androidhooking.com.android.server.pm.permission;

import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.getPackageName;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.content.pm.PackageManager;
import android.os.Binder;

import com.linearity.datservicereplacement.androidhooking.com.android.server.am.HookIActivityManager;
import com.linearity.utils.ClassHookExecutor;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SimpleExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@NotFinished //momo can detect permission wrong
public class HookPermissionManagerService {
    public static void doHook(){
        ClassHookExecutor ex_hookIPermissionManager = HookPermissionManagerService::hookIPermissionManager;
        ClassHookExecutor ex_hookIPermissionController = HookIActivityManager::hookIPermissionController;
        ClassHookExecutor ex_hookIPermissionChecker = HookPermissionManagerService::hookIPermissionChecker;
        classesAndHooks.put("com.android.server.pm.permission.PermissionManagerService", ex_hookIPermissionManager);
//        classesAndHooks.put("android.permission.PermissionManager", ex_hookIPermissionManager);
        classesAndHooks.put("com.android.server.pm.permission.LegacyPermissionManagerService", HookPermissionManagerService::hookILegacyPermissionManager);
//        classesAndHooks.put("android.permission.PermissionControllerManager", ex_hookIPermissionController);
//        classesAndHooks.put("android.permission.PermissionControllerService", ex_hookIPermissionController);
        classesAndHooks.put("com.android.server.pm.permission.PermissionManagerService$PermissionCheckerService", ex_hookIPermissionChecker);
//        classesAndHooks.put("android.permission.PermissionCheckerManager", ex_hookIPermissionChecker);

    }

    public static SimpleExecutor checkPermissionShow = param -> {
        Exception stackShower = new Exception(
                "requesting permission:" + Arrays.deepToString(param.args)
                        + "\n" + Binder.getCallingUid()
                        + "\n" + getPackageName(Binder.getCallingUid())
        );
        for (StackTraceElement s : stackShower.getStackTrace()) {
            //no enforce,no check
            if (String.valueOf(s).contains("enforce")) {
                return;
            }
        }
//        LoggerLog(stackShower);
        param.setResult(PackageManager.PERMISSION_GRANTED);
    };
    public static void hookIPermissionManager(Class<?> hookClass){
//    hookAllMethodsWithCache_Auto(hookClass,"getAllPermissionGroups",ParceledListSlice);
//    hookAllMethodsWithCache_Auto(hookClass,"getPermissionGroupInfo",PermissionGroupInfo);
        hookAllMethodsWithCache_Auto(hookClass,"getPermissionInfo",null);
//    hookAllMethodsWithCache_Auto(hookClass,"queryPermissionsByGroup",ParceledListSlice);
        hookAllMethodsWithCache_Auto(hookClass,"addPermission",true);
        hookAllMethodsWithCache_Auto(hookClass,"removePermission",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getPermissionFlags",0);
        hookAllMethodsWithCache_Auto(hookClass,"updatePermissionFlags",null);
        hookAllMethodsWithCache_Auto(hookClass,"updatePermissionFlagsForAllApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"addOnPermissionsChangeListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeOnPermissionsChangeListener",null);
//    hookAllMethodsWithCache_Auto(hookClass,"getAllowlistedRestrictedPermissions",List);
        hookAllMethodsWithCache_Auto(hookClass,"addAllowlistedRestrictedPermission",true);
        hookAllMethodsWithCache_Auto(hookClass,"removeAllowlistedRestrictedPermission",true);
        hookAllMethodsWithCache_Auto(hookClass,"grantRuntimePermission",null);
        hookAllMethodsWithCache_Auto(hookClass,"revokeRuntimePermission",null);
        hookAllMethodsWithCache_Auto(hookClass,"revokePostNotificationPermissionWithoutKillForTest",null);
        hookAllMethodsWithCache_Auto(hookClass,"shouldShowRequestPermissionRationale",true);
        hookAllMethodsWithCache_Auto(hookClass,"isPermissionRevokedByPolicy",true);
        hookAllMethodsWithCache_Auto(hookClass,"getSplitPermissions",new ArrayList<>());
        hookAllMethodsWithCache_Auto(hookClass,"startOneTimePermissionSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopOneTimePermissionSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAutoRevokeExemptionRequestedPackages",new ArrayList<>());
        hookAllMethodsWithCache_Auto(hookClass,"getAutoRevokeExemptionGrantedPackages",new ArrayList<>());
        hookAllMethodsWithCache_Auto(hookClass,"setAutoRevokeExempted",true);
        hookAllMethodsWithCache_Auto(hookClass,"isAutoRevokeExempted",true);
//    hookAllMethodsWithCache_Auto(hookClass,"registerAttributionSource",IBinder);
        hookAllMethodsWithCache_Auto(hookClass,"getRegisteredAttributionSourceCount",0);
        hookAllMethodsWithCache_Auto(hookClass,"isRegisteredAttributionSource",true);
        hookAllMethodsWithCache_Auto(hookClass,"checkPermission", checkPermissionShow);
        hookAllMethodsWithCache_Auto(hookClass,"checkUidPermission", checkPermissionShow);
        hookAllMethodsWithCache_Auto(hookClass,"checkPermissionUncached", checkPermissionShow);
        hookAllMethodsWithCache_Auto(hookClass,"getAllPermissionStates",new HashMap<>());
    }

    public static void hookILegacyPermissionManager(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"checkDeviceIdentifierAccess", checkPermissionShow);
//        hookAllMethodsWithCache_Auto(hookClass,"checkPhoneNumberAccess", checkPermissionShow);
        hookAllMethodsWithCache_Auto(hookClass,"grantDefaultPermissionsToEnabledCarrierApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"grantDefaultPermissionsToEnabledImsServices",null);
        hookAllMethodsWithCache_Auto(hookClass,"grantDefaultPermissionsToEnabledTelephonyDataServices",null);
        hookAllMethodsWithCache_Auto(hookClass,"revokeDefaultPermissionsFromDisabledTelephonyDataServices",null);
        hookAllMethodsWithCache_Auto(hookClass,"grantDefaultPermissionsToActiveLuiApp",null);
        hookAllMethodsWithCache_Auto(hookClass,"revokeDefaultPermissionsFromLuiApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"grantDefaultPermissionsToCarrierServiceApp",null);
    }

    public static void hookIPermissionChecker(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"checkPermission",(SimpleExecutor) param -> {
//            Method m = (Method) param.method;
//            Class<?> returnType = m.getReturnType();
//            if (returnType.isAssignableFrom(Integer.class) || returnType.isAssignableFrom(int.class)){
//                grantPermissionByCheckingStackTrace.execute(param);
//            }
//        }
//        );
    }
}
