package com.linearity.datservicereplacement.Permission;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;

import android.content.pm.PackageManager;

import com.linearity.utils.NotFinished;

import java.util.ArrayList;
import java.util.HashMap;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
@NotFinished
public class HookPermissionManagerService {
    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){
        Class<?> hookClass = XposedHelpers.findClassIfExists("com.android.server.pm.permission.PermissionManagerService",lpparam.classLoader);
        if (hookClass != null){
            hookIPermissionManager(hookClass);
        }
        hookClass = XposedHelpers.findClassIfExists("com.android.server.pm.permission.LegacyPermissionManagerService",lpparam.classLoader);
        if (hookClass != null){
            hookILegacyPermissionManager(hookClass);
        }
        hookClass = XposedHelpers.findClassIfExists("com.android.server.pm.permission.PermissionManagerService$PermissionCheckerService",lpparam.classLoader);
        if (hookClass != null){
            hookIPermissionChecker(hookClass);
        }
    }

    public static void hookIPermissionManager(Class<?> hookClass){
//    hookAllMethodsWithCache_Auto(hookClass,"getAllPermissionGroups",ParceledListSlice);
//    hookAllMethodsWithCache_Auto(hookClass,"getPermissionGroupInfo",PermissionGroupInfo);
        hookAllMethodsWithCache_Auto(hookClass,"getPermissionInfo",null);
//    hookAllMethodsWithCache_Auto(hookClass,"queryPermissionsByGroup",ParceledListSlice);
        hookAllMethodsWithCache_Auto(hookClass,"addPermission",true);
        hookAllMethodsWithCache_Auto(hookClass,"removePermission",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPermissionFlags",0);
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
        hookAllMethodsWithCache_Auto(hookClass,"checkPermission",PackageManager.PERMISSION_GRANTED);
        hookAllMethodsWithCache_Auto(hookClass,"checkUidPermission",PackageManager.PERMISSION_GRANTED);
        hookAllMethodsWithCache_Auto(hookClass,"getAllPermissionStates",new HashMap<>());
    }

    public static void hookILegacyPermissionManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"checkDeviceIdentifierAccess",PackageManager.PERMISSION_GRANTED);
        hookAllMethodsWithCache_Auto(hookClass,"checkPhoneNumberAccess",PackageManager.PERMISSION_GRANTED);
        hookAllMethodsWithCache_Auto(hookClass,"grantDefaultPermissionsToEnabledCarrierApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"grantDefaultPermissionsToEnabledImsServices",null);
        hookAllMethodsWithCache_Auto(hookClass,"grantDefaultPermissionsToEnabledTelephonyDataServices",null);
        hookAllMethodsWithCache_Auto(hookClass,"revokeDefaultPermissionsFromDisabledTelephonyDataServices",null);
        hookAllMethodsWithCache_Auto(hookClass,"grantDefaultPermissionsToActiveLuiApp",null);
        hookAllMethodsWithCache_Auto(hookClass,"revokeDefaultPermissionsFromLuiApps",null);
        hookAllMethodsWithCache_Auto(hookClass,"grantDefaultPermissionsToCarrierServiceApp",null);
    }

    public static void hookIPermissionChecker(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"checkPermission",PackageManager.PERMISSION_GRANTED);
        hookAllMethodsWithCache_Auto(hookClass,"finishDataDelivery",null);
        hookAllMethodsWithCache_Auto(hookClass,"checkOp",MODE_ALLOWED);
    }
}
