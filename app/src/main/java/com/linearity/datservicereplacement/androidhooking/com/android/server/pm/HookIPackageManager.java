package com.linearity.datservicereplacement.androidhooking.com.android.server.pm;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.IPackageManagers;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.hookPackageManager.isSystemApplicationInfo;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;

import com.linearity.utils.SimpleExecutorWithMode;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class HookIPackageManager {
    public static void doHook(){
        classesAndHooks.put("com.android.server.pm.PackageManagerService$IPackageManagerImpl",HookIPackageManager::hookIPackageManager);
//        LoggerLog("IPackageManager hooked");

    }

    public static void hookIPackageManager(Class<?> hookClass){
        XposedBridge.hookAllConstructors(hookClass, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                IPackageManagers.add(param.thisObject);
            }
        });
//        hookAllMethodsWithCache_Auto(hookClass,"getPackageInfo",null,);
//        hookAllMethodsWithCache_Auto(hookClass,"getPackageUid",(),param -> isSystemApp(Binder.getCallingUid()));
//        hookAllMethodsWithCache_Auto(hookClass,"getPackageGids",0);
////    hookAllMethodsWithCache_Auto(hookClass,"currentToCanonicalPackageNames",String);
////    hookAllMethodsWithCache_Auto(hookClass,"canonicalToCurrentPackageNames",String);
////    hookAllMethodsWithCache_Auto(hookClass,"getPermissionInfo",PermissionInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"queryPermissionsByGroup",List);
////    hookAllMethodsWithCache_Auto(hookClass,"getPermissionGroupInfo",PermissionGroupInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"getAllPermissionGroups",List);
////    hookAllMethodsWithCache_Auto(hookClass,"getApplicationInfo",ApplicationInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"getActivityInfo",ActivityInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"getReceiverInfo",ActivityInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"getServiceInfo",ServiceInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"getProviderInfo",ProviderInfo);

//        SimpleExecutor debugger = param -> {
//            LoggerLog(new Exception("checkPermission:"+Arrays.deepToString(param.args) + "\ncallingPackage:"+getPackageName(Binder.getCallingUid())));
//        };
//        hookAllMethodsWithCache_Auto(hookClass,"checkPermission",debugger,param -> false);
//        hookAllMethodsWithCache_Auto(hookClass,"checkUidPermission",debugger, param -> false);
//        hookAllMethodsWithCache_Auto(hookClass,"addPermission",true);
//        hookAllMethodsWithCache_Auto(hookClass,"removePermission",null);
//        hookAllMethodsWithCache_Auto(hookClass,"grantPermission",null);
//        hookAllMethodsWithCache_Auto(hookClass,"revokePermission",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isProtectedBroadcast",true);
//        hookAllMethodsWithCache_Auto(hookClass,"checkSignatures",0);
//        hookAllMethodsWithCache_Auto(hookClass,"checkUidSignatures",0);
////    hookAllMethodsWithCache_Auto(hookClass,"getPackagesForUid",String);
////    hookAllMethodsWithCache_Auto(hookClass,"getNameForUid",String);
//        hookAllMethodsWithCache_Auto(hookClass,"getUidForSharedUser",0);
////    hookAllMethodsWithCache_Auto(hookClass,"resolveIntent",ResolveInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"queryIntentActivities",List);
////    hookAllMethodsWithCache_Auto(hookClass,"queryIntentActivityOptions",List);
////    hookAllMethodsWithCache_Auto(hookClass,"queryIntentReceivers",List);
////    hookAllMethodsWithCache_Auto(hookClass,"resolveService",ResolveInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"queryIntentServices",List);
////    hookAllMethodsWithCache_Auto(hookClass,"getInstalledPackages",ParceledListSlice);
////    hookAllMethodsWithCache_Auto(hookClass,"getInstalledApplications",ParceledListSlice);
        hookAllMethodsWithCache_Auto(hookClass,"getPersistentApplications",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            Object result = param.getResult();
            List<ApplicationInfo> toIdentify = null;
            try {
                if (result instanceof ParceledListSlice<?> slice){
                    toIdentify = (List<ApplicationInfo>) slice.getList();
                }else {
                    toIdentify = (List<ApplicationInfo>) result;
                }
                toIdentify.removeIf(appInfo -> !isSystemApplicationInfo(appInfo));
            }catch (Exception e){
                LoggerLog(e);
            }
        }),noSystemChecker);
////    hookAllMethodsWithCache_Auto(hookClass,"resolveContentProvider",ProviderInfo);
//        hookAllMethodsWithCache_Auto(hookClass,"querySyncProviders",null);
////    hookAllMethodsWithCache_Auto(hookClass,"queryContentProviders",List);
////    hookAllMethodsWithCache_Auto(hookClass,"getInstrumentationInfo",InstrumentationInfo);
////    hookAllMethodsWithCache_Auto(hookClass,"queryInstrumentation",List);
//        hookAllMethodsWithCache_Auto(hookClass,"installPackage",null);
//        hookAllMethodsWithCache_Auto(hookClass,"finishPackageInstall",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setInstallerPackageName",null);
//        hookAllMethodsWithCache_Auto(hookClass,"deletePackage",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getInstallerPackageName",String);
//        hookAllMethodsWithCache_Auto(hookClass,"addPackageToPreferred",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removePackageFromPreferred",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getPreferredPackages",List);
//        hookAllMethodsWithCache_Auto(hookClass,"addPreferredActivity",null);
//        hookAllMethodsWithCache_Auto(hookClass,"replacePreferredActivity",null);
//        hookAllMethodsWithCache_Auto(hookClass,"clearPackagePreferredActivities",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getPreferredActivities",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setComponentEnabledSetting",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getComponentEnabledSetting",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setApplicationEnabledSetting",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getApplicationEnabledSetting",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setPackageStoppedState",null);
//        hookAllMethodsWithCache_Auto(hookClass,"freeStorageAndNotify",null);
//        hookAllMethodsWithCache_Auto(hookClass,"freeStorage",null);
//        hookAllMethodsWithCache_Auto(hookClass,"deleteApplicationCacheFiles",null);
//        hookAllMethodsWithCache_Auto(hookClass,"clearApplicationUserData",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getPackageSizeInfo",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getSystemSharedLibraryNames",String);
////    hookAllMethodsWithCache_Auto(hookClass,"getSystemAvailableFeatures",FeatureInfo);
//        hookAllMethodsWithCache_Auto(hookClass,"hasSystemFeature",true);
//        hookAllMethodsWithCache_Auto(hookClass,"enterSafeMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isSafeMode",true);
//        hookAllMethodsWithCache_Auto(hookClass,"systemReady",null);
//        hookAllMethodsWithCache_Auto(hookClass,"hasSystemUidErrors",true);
//        hookAllMethodsWithCache_Auto(hookClass,"performBootDexOpt",null);
//        hookAllMethodsWithCache_Auto(hookClass,"performDexOpt",true);
//        hookAllMethodsWithCache_Auto(hookClass,"updateExternalMediaStatus",null);
////    hookAllMethodsWithCache_Auto(hookClass,"nextPackageToClean",String);
//        hookAllMethodsWithCache_Auto(hookClass,"movePackage",null);
//        hookAllMethodsWithCache_Auto(hookClass,"addPermissionAsync",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setInstallLocation",true);
//        hookAllMethodsWithCache_Auto(hookClass,"getInstallLocation",0);
////    hookAllMethodsWithCache_Auto(hookClass,"createUser",UserInfo);
//        hookAllMethodsWithCache_Auto(hookClass,"removeUser",true);
//        hookAllMethodsWithCache_Auto(hookClass,"installPackageWithVerification",null);
//        hookAllMethodsWithCache_Auto(hookClass,"verifyPendingInstall",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getVerifierDeviceIdentity",VerifierDeviceIdentity);
//        hookAllMethodsWithCache_Auto(hookClass,"isFirstBoot",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getUsers",List);
//        hookAllMethodsWithCache_Auto(hookClass,"setPermissionEnforcement",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getPermissionEnforcement",0);
    }
}
