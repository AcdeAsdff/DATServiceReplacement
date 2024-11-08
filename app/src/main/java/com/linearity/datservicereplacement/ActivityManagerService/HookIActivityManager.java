package com.linearity.datservicereplacement.ActivityManagerService;

import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.getPackageName;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.isSystemApp;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findArgByClassInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findClassIndexInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findStrAndUidInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findStrInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_UidAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;

import com.linearity.datservicereplacement.AndroidConsts.IntentConstants;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;
import com.linearity.utils.SystemAppChecker;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookIActivityManager {

    public static int PROCESS_STATE_TOP = 3;
    public static final Set<String> BLACKLIST_INTENT_ACTION_SET = new HashSet<>();
    static {
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MAIN);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_VIEW);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DEFAULT);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_QUICK_VIEW);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_ATTACH_DATA);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_EDIT);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_INSERT_OR_EDIT);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PICK);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CREATE_REMINDER);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CREATE_SHORTCUT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_APPLICATION_PREFERENCES);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SHOW_APP_INFO);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_ACTIVITY_RECOGNIZER);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CHOOSER);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_GET_CONTENT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DIAL);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CALL);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CALL_EMERGENCY);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DIAL_EMERGENCY);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CALL_PRIVILEGED);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CARRIER_SETUP);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SENDTO);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SEND);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SEND_MULTIPLE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_ANSWER);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_INSERT);
//        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PASTE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DELETE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_RUN);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SYNC);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PICK_ACTIVITY);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SEARCH);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SYSTEM_TUTORIAL);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_WEB_SEARCH);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_ASSIST);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_VOICE_ASSIST);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_ALL_APPS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SET_WALLPAPER);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_BUG_REPORT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_FACTORY_TEST);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CALL_BUTTON);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_VOICE_COMMAND);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SEARCH_LONG_PRESS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_APP_ERROR);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PENDING_INCIDENT_REPORTS_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_INCIDENT_REPORT_READY);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_POWER_USAGE_SUMMARY);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DEVICE_INITIALIZATION_WIZARD);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_UPGRADE_SETUP);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SHOW_KEYBOARD_SHORTCUTS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DISMISS_KEYBOARD_SHORTCUTS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGE_NETWORK_USAGE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_INSTALL_PACKAGE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_INSTALL_FAILURE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_INSTALL_INSTANT_APP_PACKAGE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_RESOLVE_INSTANT_APP_PACKAGE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_INSTANT_APP_RESOLVER_SETTINGS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_UNINSTALL_PACKAGE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGE_APP_PERMISSIONS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGE_APP_PERMISSION);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGE_PERMISSIONS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_AUTO_REVOKE_PERMISSIONS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGE_UNUSED_APPS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_REVIEW_PERMISSIONS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_VIEW_PERMISSION_USAGE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_VIEW_PERMISSION_USAGE_FOR_PERIOD);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGE_DEFAULT_APP);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGE_SPECIAL_APP_ACCESSES);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGE_PERMISSION_APPS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_REVIEW_PERMISSION_USAGE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_REVIEW_PERMISSION_HISTORY);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_REVIEW_ONGOING_PERMISSION_USAGE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_REVIEW_ACCESSIBILITY_SERVICES);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SCREEN_OFF);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SCREEN_ON);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DREAMING_STOPPED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DREAMING_STARTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_PRESENT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_TIME_TICK);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_TIME_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DATE_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_TIMEZONE_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_ALARM_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_LOCKED_BOOT_COMPLETED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_BOOT_COMPLETED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CLOSE_SYSTEM_DIALOGS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_INSTALL);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_ADDED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_REPLACED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MY_PACKAGE_REPLACED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_REMOVED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_REMOVED_INTERNAL);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_FULLY_REMOVED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_ENABLE_ROLLBACK);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CANCEL_ENABLE_ROLLBACK);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_ROLLBACK_COMMITTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_QUERY_PACKAGE_RESTART);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_RESTARTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_DATA_CLEARED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGES_SUSPENDED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGES_UNSUSPENDED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGES_SUSPENSION_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DISTRACTING_PACKAGES_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MY_PACKAGE_SUSPENDED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SHOW_SUSPENDED_APP_DETAILS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_UNSUSPENDED_MANUALLY);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MY_PACKAGE_UNSUSPENDED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_UID_REMOVED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_FIRST_LAUNCH);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_NEEDS_VERIFICATION);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_VERIFIED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_INTENT_FILTER_NEEDS_VERIFICATION);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DOMAINS_NEED_VERIFICATION);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PREFERRED_ACTIVITY_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_WALLPAPER_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CONFIGURATION_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SPLIT_CONFIGURATION_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_LOCALE_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_BATTERY_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_BATTERY_LEVEL_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_BATTERY_LOW);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_BATTERY_OKAY);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_POWER_CONNECTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_POWER_DISCONNECTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SHUTDOWN);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_REQUEST_SHUTDOWN);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DEVICE_STORAGE_LOW);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DEVICE_STORAGE_OK);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DEVICE_STORAGE_FULL);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DEVICE_STORAGE_NOT_FULL);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGE_PACKAGE_STORAGE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_UMS_CONNECTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_UMS_DISCONNECTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_REMOVED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_UNMOUNTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_CHECKING);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_NOFS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_MOUNTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_SHARED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_UNSHARED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_BAD_REMOVAL);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_UNMOUNTABLE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_EJECT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_SCANNER_STARTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_SCANNER_FINISHED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_SCANNER_SCAN_FILE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_BUTTON);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CAMERA_BUTTON);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_GTALK_SERVICE_CONNECTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_GTALK_SERVICE_DISCONNECTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_INPUT_METHOD_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_AIRPLANE_MODE_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PROVIDER_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_HEADSET_PLUG);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_ADVANCED_SETTINGS_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_APPLICATION_RESTRICTIONS_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_NEW_OUTGOING_CALL);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_REBOOT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DOCK_EVENT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_IDLE_MAINTENANCE_START);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_IDLE_MAINTENANCE_END);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_REMOTE_INTENT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PRE_BOOT_COMPLETED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_GET_RESTRICTION_ENTRIES);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_INITIALIZE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_FOREGROUND);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_BACKGROUND);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_ADDED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_STARTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_STARTING);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_STOPPING);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_STOPPED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_REMOVED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_SWITCHED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_UNLOCKED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_USER_INFO_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGED_PROFILE_ADDED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGED_PROFILE_REMOVED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGED_PROFILE_UNLOCKED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGED_PROFILE_AVAILABLE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MANAGED_PROFILE_UNAVAILABLE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PROFILE_ACCESSIBLE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PROFILE_INACCESSIBLE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DEVICE_LOCKED_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_QUICK_CLOCK);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SHOW_BRIGHTNESS_DIALOG);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_GLOBAL_BUTTON);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MEDIA_RESOURCE_GRANTED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_OVERLAY_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_OPEN_DOCUMENT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_CREATE_DOCUMENT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_OPEN_DOCUMENT_TREE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_TRANSLATE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DEFINE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DYNAMIC_SENSOR_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MASTER_CLEAR);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_MASTER_CLEAR_NOTIFICATION);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_FACTORY_RESET);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SETTING_RESTORED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PROCESS_TEXT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SIM_STATE_CHANGED);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_SERVICE_STATE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_LOAD_DATA);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_THERMAL_EVENT);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DOCK_IDLE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DOCK_ACTIVE);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_DEVICE_CUSTOMIZATION_READY);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_VIEW_LOCUS);
        BLACKLIST_INTENT_ACTION_SET.add(IntentConstants.ACTION_PACKAGE_NEEDS_INTEGRITY_VERIFICATION);
    }

    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){
        PROCESS_STATE_TOP = XposedHelpers.getStaticIntField(android.app.ActivityManager.class,"PROCESS_STATE_TOP");

        Class<?> hookClass = XposedHelpers.findClassIfExists("com.android.server.am.ActivityManagerService", lpparam.classLoader);
        if (hookClass != null){
            hookIActivityManager(hookClass);
        }
    }

    public static void hookIActivityManager(Class<?> hookClass){

//    hookAllMethodsWithCache_Auto(hookClass,"openContentUri",ParcelFileDescriptor);

        hookAllMethodsWithCache_Auto(hookClass,"registerUidObserver",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"registerUidObserverForUids",/*IBinder*/null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterUidObserver",null);
        hookAllMethodsWithCache_Auto(hookClass,"addUidToObserver",null,findStrAndUidInArgs);
        hookAllMethodsWithCache_Auto(hookClass,"removeUidFromObserver",null,findStrAndUidInArgs);
        hookAllMethodsWithCache_Auto(hookClass,"isUidActive",true,findStrAndUidInArgs);
        hookAllMethodsWithCache_Auto(hookClass,"getUidProcessState",PROCESS_STATE_TOP,findStrAndUidInArgs);
        hookAllMethodsWithCache_Auto(hookClass,"checkPermission", PackageManager.PERMISSION_GRANTED,getSystemChecker_UidAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"logFgsApiBegin",null);
        hookAllMethodsWithCache_Auto(hookClass,"logFgsApiEnd",null);
        hookAllMethodsWithCache_Auto(hookClass,"logFgsApiStateChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"handleApplicationCrash",null);
        hookAllMethodsWithCache_Auto(hookClass,"startActivity", (SimpleExecutor) param -> {
            Intent intent = (Intent) param.args[2];
            if (intent == null){return;}
            ComponentName mComponent = intent.getComponent();
            if (mComponent == null){
                return;
            }
            String pkgName = mComponent.getPackageName().toLowerCase();
            String usedClassName = mComponent.getClassName().toLowerCase();
            String callingName = getPackageName(Binder.getCallingUid());
            if (callingName != null && callingName.equals(pkgName)){
                return;
            }
            if (usedClassName.contains(pkgName)){
                return;
            }
            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
                return;
            }
            param.setResult(0);
        },getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"startActivityWithFeature", (SimpleExecutor) param -> {
            Intent intent = (Intent) param.args[3];
            if (intent == null){return;}
            ComponentName mComponent = intent.getComponent();
            if (mComponent == null){
                return;
            }
            String pkgName = mComponent.getPackageName().toLowerCase();
            String usedClassName = mComponent.getClassName().toLowerCase();
            String callingName = getPackageName(Binder.getCallingUid());
            if (callingName != null && callingName.equals(pkgName)){
                return;
            }
            if (usedClassName.contains(pkgName)){
                return;
            }
            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
                return;
            }
            param.setResult(0);
        },getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"unhandledBack",null);

//        hookAllMethodsWithCache_Auto(hookClass,"finishActivity",true);

        SimpleExecutor replaceIntentFilter = (SimpleExecutor) param -> {
            IntentFilter filter = findArgByClassInArgs(param.args,IntentFilter.class);
            if (filter == null){
                return;
            }
            int index = findClassIndexInArgs(param.args,IntentFilter.class);
            IntentFilter replaceFilter = new IntentFilter();
            for (Iterator<String> it = filter.actionsIterator(); it.hasNext(); ) {
                String action = it.next();
                if (BLACKLIST_INTENT_ACTION_SET.contains(action)){
                    continue;
                }
                replaceFilter.addAction(action);
            }
            cloneIntentFilterFilter(filter,replaceFilter);
            param.args[index] = replaceFilter;
        };
        hookAllMethodsWithCache_Auto(hookClass,"registerReceiver",replaceIntentFilter,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"registerReceiverWithFeature",replaceIntentFilter,getSystemChecker_PackageNameAt(1));
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterReceiver",null);

        SimpleExecutor showBroadcastIntent = (SimpleExecutor) param -> {
            int uid = Binder.getCallingUid();
            LoggerLog("uid:" + uid + " " + "packageName:" + getPackageName(uid) +" broadcastIntent:" + findArgByClassInArgs(param.args,Intent.class));
        };SimpleExecutor showBroadcastIntentWithFeature = (SimpleExecutor) param -> {
            int uid = Binder.getCallingUid();
            LoggerLog("uid:" + uid + " " + "packageName:" + getPackageName(uid) +" broadcastIntent:" + findArgByClassInArgs(param.args,Intent.class));
        };
        hookAllMethodsWithCache_Auto(hookClass,"broadcastIntent",showBroadcastIntent);
        hookAllMethodsWithCache_Auto(hookClass,"broadcastIntentWithFeature",showBroadcastIntentWithFeature);
//        hookAllMethodsWithCache_Auto(hookClass,"unbroadcastIntent",null);
//        hookAllMethodsWithCache_Auto(hookClass,"finishReceiver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"attachApplication",null);
//        hookAllMethodsWithCache_Auto(hookClass,"finishAttachApplication",null);
        hookAllMethodsWithCache_Auto(hookClass,"getTasks",new SimpleExecutorWithMode(MODE_AFTER,(SimpleExecutor) param -> {
            List<ActivityManager.RunningTaskInfo> ret = (List<ActivityManager.RunningTaskInfo>) param.getResult();
            int uid = Binder.getCallingUid();
            if(isSystemApp(uid)){return;}
            String packageName = getPackageName(uid);
            if (packageName == null){return;}
            ret.removeIf(runningTaskInfo -> !runningTaskInfo.baseIntent.getPackage().equals(packageName));
        }));
        hookAllMethodsWithCache_Auto(hookClass,"moveTaskToFront",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getTaskForActivity",(SimpleExecutor) param -> {});
//    hookAllMethodsWithCache_Auto(hookClass,"getContentProvider",ContentProviderHolder);
//        hookAllMethodsWithCache_Auto(hookClass,"publishContentProviders",null);
//        hookAllMethodsWithCache_Auto(hookClass,"refContentProvider",true);
        hookAllMethodsWithCache_Auto(hookClass,"getRunningServiceControlPanel",(SimpleExecutor) param -> {

            ComponentName mComponent = (ComponentName) param.args[0];
            if (mComponent == null){
                return;
            }
            String pkgName = mComponent.getPackageName().toLowerCase();
            String callingName = getPackageName(Binder.getCallingUid());
            if (callingName != null && callingName.equals(pkgName)){
                return;
            }
            String usedClassName = mComponent.getClassName().toLowerCase();
            if (usedClassName.contains(pkgName)){
                return;
            }
            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
                return;
            }
            param.setResult(null);
        });
        hookAllMethodsWithCache_Auto(hookClass,"startService",(SimpleExecutor) param -> {

            Intent intent = (Intent) param.args[1];
            if (intent == null){return;}
            ComponentName mComponent = intent.getComponent();
            if (mComponent == null){
                return;
            }
            String pkgName = mComponent.getPackageName().toLowerCase();
            String callingName = getPackageName(Binder.getCallingUid());
            if (callingName != null && callingName.equals(pkgName)){
                return;
            }
            String usedClassName = mComponent.getClassName().toLowerCase();
            if (usedClassName.contains(pkgName)){
                return;
            }
            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
                return;
            }
            param.setResult(null);
        });
//        hookAllMethodsWithCache_Auto(hookClass,"stopService",0);
        SimpleExecutor bindService = (SimpleExecutor) param -> {

            Intent intent = (Intent) param.args[2];
            if (intent == null){return;}
            ComponentName mComponent = intent.getComponent();
            if (mComponent == null){
                return;
            }
            String pkgName = mComponent.getPackageName().toLowerCase();
            String callingName = getPackageName(Binder.getCallingUid());
            if (callingName != null && callingName.equals(pkgName)){
                return;
            }
            String usedClassName = mComponent.getClassName().toLowerCase();
            if (usedClassName.contains(pkgName)){
                return;
            }
            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
                return;
            }
            param.setResult(0);
        };
        hookAllMethodsWithCache_Auto(hookClass,"bindService",bindService);
        hookAllMethodsWithCache_Auto(hookClass,"bindServiceInstance",bindService);

        hookAllMethodsWithCache_Auto(hookClass,"updateServiceGroup",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unbindService",true);
        hookAllMethodsWithCache_Auto(hookClass,"publishService",(SimpleExecutor) param -> {
            Intent intent = (Intent) param.args[1];
            if (intent == null){return;}
            ComponentName mComponent = intent.getComponent();
            if (mComponent == null){
                return;
            }
            String pkgName = mComponent.getPackageName().toLowerCase();
            String callingName = getPackageName(Binder.getCallingUid());
            if (callingName != null && callingName.equals(pkgName)){
                return;
            }
            String usedClassName = mComponent.getClassName().toLowerCase();
            if (usedClassName.contains(pkgName)){
                return;
            }
            if (usedClassName.contains("alipay") || usedClassName.contains("tencent.mm")){
                return;
            }
            param.setResult(null);
        });
        hookAllMethodsWithCache_Auto(hookClass,"setDebugApp",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAgentApp",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAlwaysFinish",null);
////        hookAllMethodsWithCache_Auto(hookClass,"startInstrumentation",true);
////        hookAllMethodsWithCache_Auto(hookClass,"addInstrumentationResults",null);
////        hookAllMethodsWithCache_Auto(hookClass,"finishInstrumentation",null);
//
////    hookAllMethodsWithCache_Auto(hookClass,"getConfiguration",Configuration);
//        hookAllMethodsWithCache_Auto(hookClass,"updateConfiguration",true);
//        hookAllMethodsWithCache_Auto(hookClass,"updateMccMncConfiguration",true);
//        hookAllMethodsWithCache_Auto(hookClass,"stopServiceToken",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setProcessLimit",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getProcessLimit",0);
//        hookAllMethodsWithCache_Auto(hookClass,"checkUriPermission",0);
//        hookAllMethodsWithCache_Auto(hookClass,"checkContentUriPermissionFull",0);
//        hookAllMethodsWithCache_Auto(hookClass,"checkUriPermissions",0);
//        hookAllMethodsWithCache_Auto(hookClass,"grantUriPermission",null);
//        hookAllMethodsWithCache_Auto(hookClass,"revokeUriPermission",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setActivityController",null);
//        hookAllMethodsWithCache_Auto(hookClass,"showWaitingForDebugger",null);
//        hookAllMethodsWithCache_Auto(hookClass,"signalPersistentProcesses",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getRecentTasks",ParceledListSlice);
//        hookAllMethodsWithCache_Auto(hookClass,"serviceDoneExecuting",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getIntentSender",IIntentSender);
////    hookAllMethodsWithCache_Auto(hookClass,"getIntentSenderWithFeature",IIntentSender);
//        hookAllMethodsWithCache_Auto(hookClass,"cancelIntentSender",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getInfoForIntentSender",ActivityManager);
//        hookAllMethodsWithCache_Auto(hookClass,"registerIntentSenderCancelListenerEx",true);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterIntentSenderCancelListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"enterSafeMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"noteWakeupAlarm",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeContentProvider",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setRequestedOrientation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unbindFinished",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setProcessImportant",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setServiceForeground",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getForegroundServiceType",0);
//        hookAllMethodsWithCache_Auto(hookClass,"moveActivityTaskToBack",true);
//        hookAllMethodsWithCache_Auto(hookClass,"getMemoryInfo",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getProcessesInErrorState",List);
//        hookAllMethodsWithCache_Auto(hookClass,"clearApplicationUserData",true);
//        hookAllMethodsWithCache_Auto(hookClass,"stopAppForUser",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerForegroundServiceObserver",true);
//        hookAllMethodsWithCache_Auto(hookClass,"forceStopPackage",null);
//        hookAllMethodsWithCache_Auto(hookClass,"forceStopPackageEvenWhenStopping",null);
//        hookAllMethodsWithCache_Auto(hookClass,"killPids",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getServices",List);
////    hookAllMethodsWithCache_Auto(hookClass,"getRunningAppProcesses",List);
////    hookAllMethodsWithCache_Auto(hookClass,"peekService",IBinder);
//        hookAllMethodsWithCache_Auto(hookClass,"profileControl",true);
//        hookAllMethodsWithCache_Auto(hookClass,"shutdown",true);
//        hookAllMethodsWithCache_Auto(hookClass,"stopAppSwitches",null);
//        hookAllMethodsWithCache_Auto(hookClass,"resumeAppSwitches",null);
//        hookAllMethodsWithCache_Auto(hookClass,"bindBackupAgent",true);
//        hookAllMethodsWithCache_Auto(hookClass,"backupAgentCreated",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unbindBackupAgent",null);
//        hookAllMethodsWithCache_Auto(hookClass,"handleIncomingUser",0);
//        hookAllMethodsWithCache_Auto(hookClass,"addPackageDependency",null);
//        hookAllMethodsWithCache_Auto(hookClass,"killApplication",null);
//        hookAllMethodsWithCache_Auto(hookClass,"closeSystemDialogs",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getProcessMemoryInfo",Debug);
//        hookAllMethodsWithCache_Auto(hookClass,"killApplicationProcess",null);
//        hookAllMethodsWithCache_Auto(hookClass,"handleApplicationWtf",true);
//        hookAllMethodsWithCache_Auto(hookClass,"killBackgroundProcesses",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isUserAMonkey",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getRunningExternalApplications",List);
//        hookAllMethodsWithCache_Auto(hookClass,"finishHeavyWeightApp",null);
//        hookAllMethodsWithCache_Auto(hookClass,"handleApplicationStrictModeViolation",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerStrictModeCallback",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isTopActivityImmersive",true);
//        hookAllMethodsWithCache_Auto(hookClass,"crashApplicationWithType",null);
//        hookAllMethodsWithCache_Auto(hookClass,"crashApplicationWithTypeWithExtras",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getMimeTypeFilterAsync",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dumpHeap",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isUserRunning",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setPackageScreenCompatMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"switchUser",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getSwitchingFromUserMessage",String);
////    hookAllMethodsWithCache_Auto(hookClass,"getSwitchingToUserMessage",String);
//        hookAllMethodsWithCache_Auto(hookClass,"setStopUserOnSwitch",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeTask",true);
//        hookAllMethodsWithCache_Auto(hookClass,"registerProcessObserver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterProcessObserver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isIntentSenderTargetedToPackage",true);
//        hookAllMethodsWithCache_Auto(hookClass,"updatePersistentConfiguration",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updatePersistentConfigurationWithAttribution",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getProcessPss",long);
//        hookAllMethodsWithCache_Auto(hookClass,"showBootMessage",null);
//        hookAllMethodsWithCache_Auto(hookClass,"killAllBackgroundProcesses",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getContentProviderExternal",ContentProviderHolder);
//        hookAllMethodsWithCache_Auto(hookClass,"removeContentProviderExternal",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeContentProviderExternalAsUser",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getMyMemoryState",null);
//        hookAllMethodsWithCache_Auto(hookClass,"killProcessesBelowForeground",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getCurrentUser",UserInfo);
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentUserId",0);
//        hookAllMethodsWithCache_Auto(hookClass,"getLaunchedFromUid",0);
//        hookAllMethodsWithCache_Auto(hookClass,"unstableProviderDied",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isIntentSenderAnActivity",true);
//        hookAllMethodsWithCache_Auto(hookClass,"startActivityAsUser",0);
//        hookAllMethodsWithCache_Auto(hookClass,"startActivityAsUserWithFeature",0);
//        hookAllMethodsWithCache_Auto(hookClass,"stopUser",0);
//        hookAllMethodsWithCache_Auto(hookClass,"stopUserWithCallback",0);
//        hookAllMethodsWithCache_Auto(hookClass,"stopUserExceptCertainProfiles",0);
//        hookAllMethodsWithCache_Auto(hookClass,"stopUserWithDelayedLocking",0);
//        hookAllMethodsWithCache_Auto(hookClass,"registerUserSwitchObserver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterUserSwitchObserver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getRunningUserIds",0);
//        hookAllMethodsWithCache_Auto(hookClass,"requestSystemServerHeapDump",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestBugReportWithDescription",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestTelephonyBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestWifiBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestInteractiveBugReportWithDescription",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestInteractiveBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestBugReportWithExtraAttachment",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestFullBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestRemoteBugReport",null);
//        hookAllMethodsWithCache_Auto(hookClass,"launchBugReportHandlerApp",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getBugreportWhitelistedPackages",List);
////    hookAllMethodsWithCache_Auto(hookClass,"getIntentForIntentSender",Intent);
////    hookAllMethodsWithCache_Auto(hookClass,"getLaunchedFromPackage",String);
        hookAllMethodsWithCache_Auto(hookClass,"killUid",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setUserIsMonkey",null);
//        hookAllMethodsWithCache_Auto(hookClass,"hang",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getAllRootTaskInfos",List);
//        hookAllMethodsWithCache_Auto(hookClass,"moveTaskToRootTask",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setFocusedRootTask",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getFocusedRootTaskInfo",ActivityTaskManager);
//        hookAllMethodsWithCache_Auto(hookClass,"restart",null);
//        hookAllMethodsWithCache_Auto(hookClass,"performIdleMaintenance",null);
//        hookAllMethodsWithCache_Auto(hookClass,"appNotRespondingViaProvider",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getTaskBounds",Rect);
//        hookAllMethodsWithCache_Auto(hookClass,"setProcessMemoryTrimLevel",true);
////    hookAllMethodsWithCache_Auto(hookClass,"getTagForIntentSender",String);
//        hookAllMethodsWithCache_Auto(hookClass,"startUserInBackground",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isInLockTaskMode",true);
//        hookAllMethodsWithCache_Auto(hookClass,"startActivityFromRecents",0);
//        hookAllMethodsWithCache_Auto(hookClass,"startSystemLockTaskMode",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isTopOfTask",true);
//        hookAllMethodsWithCache_Auto(hookClass,"bootAnimationComplete",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setThemeOverlayReady",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerTaskStackListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterTaskStackListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"notifyCleartextNetwork",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setTaskResizeable",null);
//        hookAllMethodsWithCache_Auto(hookClass,"resizeTask",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getLockTaskModeState",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setDumpHeapDebugLimit",null);
//        hookAllMethodsWithCache_Auto(hookClass,"dumpHeapFinished",null);
//        hookAllMethodsWithCache_Auto(hookClass,"updateLockTaskPackages",null);
//        hookAllMethodsWithCache_Auto(hookClass,"noteAlarmStart",null);
//        hookAllMethodsWithCache_Auto(hookClass,"noteAlarmFinish",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getPackageProcessState",0);
//        hookAllMethodsWithCache_Auto(hookClass,"startBinderTracking",true);
//        hookAllMethodsWithCache_Auto(hookClass,"stopBinderTrackingAndDump",true);
//        hookAllMethodsWithCache_Auto(hookClass,"suppressResizeConfigChanges",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unlockUser",true);
//        hookAllMethodsWithCache_Auto(hookClass,"unlockUser2",true);
//        hookAllMethodsWithCache_Auto(hookClass,"killPackageDependents",null);
//        hookAllMethodsWithCache_Auto(hookClass,"makePackageIdle",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setDeterministicUidIdle",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getMemoryTrimLevel",0);
//        hookAllMethodsWithCache_Auto(hookClass,"isVrModePackageEnabled",true);
//        hookAllMethodsWithCache_Auto(hookClass,"notifyLockedProfile",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startConfirmDeviceCredentialIntent",null);
//        hookAllMethodsWithCache_Auto(hookClass,"sendIdleJobTrigger",null);
//        hookAllMethodsWithCache_Auto(hookClass,"sendIntentSender",0);
//        hookAllMethodsWithCache_Auto(hookClass,"isBackgroundRestricted",true);
//        hookAllMethodsWithCache_Auto(hookClass,"setRenderThread",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setHasTopUi",null);
//        hookAllMethodsWithCache_Auto(hookClass,"cancelTaskWindowTransition",null);
//        hookAllMethodsWithCache_Auto(hookClass,"scheduleApplicationInfoChanged",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setPersistentVrThread",null);
//        hookAllMethodsWithCache_Auto(hookClass,"waitForNetworkStateUpdate",null);
//        hookAllMethodsWithCache_Auto(hookClass,"backgroundAllowlistUid",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startUserInBackgroundWithListener",true);
//        hookAllMethodsWithCache_Auto(hookClass,"startDelegateShellPermissionIdentity",null);
//        hookAllMethodsWithCache_Auto(hookClass,"stopDelegateShellPermissionIdentity",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getDelegatedShellPermissions",List);
////    hookAllMethodsWithCache_Auto(hookClass,"getLifeMonitor",ParcelFileDescriptor);
//        hookAllMethodsWithCache_Auto(hookClass,"startUserInForegroundWithListener",true);
//        hookAllMethodsWithCache_Auto(hookClass,"appNotResponding",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getHistoricalProcessStartReasons",ParceledListSlice);
//        hookAllMethodsWithCache_Auto(hookClass,"addApplicationStartInfoCompleteListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeApplicationStartInfoCompleteListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"addStartInfoTimestamp",null);
//        hookAllMethodsWithCache_Auto(hookClass,"reportStartInfoViewTimestamps",null);
////    hookAllMethodsWithCache_Auto(hookClass,"getHistoricalProcessExitReasons",ParceledListSlice);
//        hookAllMethodsWithCache_Auto(hookClass,"killProcessesWhenImperceptible",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setActivityLocusContext",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setProcessStateSummary",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isAppFreezerSupported",true);
//        hookAllMethodsWithCache_Auto(hookClass,"isAppFreezerEnabled",true);
//        hookAllMethodsWithCache_Auto(hookClass,"killUidForPermissionChange",null);
//        hookAllMethodsWithCache_Auto(hookClass,"resetAppErrors",null);
//        hookAllMethodsWithCache_Auto(hookClass,"enableAppFreezer",true);
//        hookAllMethodsWithCache_Auto(hookClass,"enableFgsNotificationRateLimit",true);
//        hookAllMethodsWithCache_Auto(hookClass,"holdLock",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startProfile",true);
//        hookAllMethodsWithCache_Auto(hookClass,"stopProfile",true);
////    hookAllMethodsWithCache_Auto(hookClass,"queryIntentComponentsForIntentSender",ParceledListSlice);
//        hookAllMethodsWithCache_Auto(hookClass,"getUidProcessCapabilities",0);
//        hookAllMethodsWithCache_Auto(hookClass,"waitForBroadcastIdle",null);
//        hookAllMethodsWithCache_Auto(hookClass,"waitForBroadcastBarrier",null);
//        hookAllMethodsWithCache_Auto(hookClass,"forceDelayBroadcastDelivery",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isProcessFrozen",true);
//        hookAllMethodsWithCache_Auto(hookClass,"getBackgroundRestrictionExemptionReason",0);
//        hookAllMethodsWithCache_Auto(hookClass,"startUserInBackgroundVisibleOnDisplay",true);
//        hookAllMethodsWithCache_Auto(hookClass,"startProfileWithListener",true);
//        hookAllMethodsWithCache_Auto(hookClass,"restartUserInBackground",0);
//        hookAllMethodsWithCache_Auto(hookClass,"getDisplayIdsForStartingVisibleBackgroundUsers",0);
//        hookAllMethodsWithCache_Auto(hookClass,"shouldServiceTimeOut",true);
//        hookAllMethodsWithCache_Auto(hookClass,"hasServiceTimeLimitExceeded",true);
//        hookAllMethodsWithCache_Auto(hookClass,"registerUidFrozenStateChangedCallback",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterUidFrozenStateChangedCallback",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getUidFrozenState",0);
//        hookAllMethodsWithCache_Auto(hookClass,"checkPermissionForDevice",0);
//        hookAllMethodsWithCache_Auto(hookClass,"frozenBinderTransactionDetected",null);
//        hookAllMethodsWithCache_Auto(hookClass,"getBindingUidProcessState",0);
////    hookAllMethodsWithCache_Auto(hookClass,"getUidLastIdleElapsedTime",long);
//        hookAllMethodsWithCache_Auto(hookClass,"addOverridePermissionState",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeOverridePermissionState",null);
//        hookAllMethodsWithCache_Auto(hookClass,"clearOverridePermissionStates",null);
//        hookAllMethodsWithCache_Auto(hookClass,"clearAllOverridePermissionStates",null);
//        hookAllMethodsWithCache_Auto(hookClass,"noteAppRestrictionEnabled",null);
    }

    public static void cloneIntentFilterFilter(IntentFilter fromObj,IntentFilter toObj){
        XposedHelpers.setIntField(toObj,"mPriority",XposedHelpers.getIntField(fromObj,"mPriority"));
        XposedHelpers.setIntField(toObj,"mOrder",XposedHelpers.getIntField(fromObj,"mOrder"));
//        XposedHelpers.setObjectField(toObj,"mActions",XposedHelpers.getObjectField(fromObj,"mActions"));
        XposedHelpers.setObjectField(toObj,"mCategories",XposedHelpers.getObjectField(fromObj,"mCategories"));
        XposedHelpers.setObjectField(toObj,"mDataSchemes",XposedHelpers.getObjectField(fromObj,"mDataSchemes"));
        XposedHelpers.setObjectField(toObj,"mDataSchemeSpecificParts",XposedHelpers.getObjectField(fromObj,"mDataSchemeSpecificParts"));
        XposedHelpers.setObjectField(toObj,"mDataAuthorities",XposedHelpers.getObjectField(fromObj,"mDataAuthorities"));
        XposedHelpers.setObjectField(toObj,"mDataPaths",XposedHelpers.getObjectField(fromObj,"mDataPaths"));
        XposedHelpers.setObjectField(toObj,"mStaticDataTypes",XposedHelpers.getObjectField(fromObj,"mStaticDataTypes"));
        XposedHelpers.setObjectField(toObj,"mDataTypes",XposedHelpers.getObjectField(fromObj,"mDataTypes"));
        XposedHelpers.setObjectField(toObj,"mMimeGroups",XposedHelpers.getObjectField(fromObj,"mMimeGroups"));
        XposedHelpers.setBooleanField(toObj,"mHasStaticPartialTypes",XposedHelpers.getBooleanField(fromObj,"mHasStaticPartialTypes"));
        XposedHelpers.setBooleanField(toObj,"mHasDynamicPartialTypes",XposedHelpers.getBooleanField(fromObj,"mHasDynamicPartialTypes"));
        XposedHelpers.setIntField(toObj,"mVerifyState",XposedHelpers.getIntField(fromObj,"mVerifyState"));
        XposedHelpers.setIntField(toObj,"mInstantAppVisibility",XposedHelpers.getIntField(fromObj,"mInstantAppVisibility"));
    }
}
