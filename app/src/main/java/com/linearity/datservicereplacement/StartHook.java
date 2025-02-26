package com.linearity.datservicereplacement;

import static com.linearity.datservicereplacement.LibNames.DATZYGISK_LOCAL_PART;
import static com.linearity.datservicereplacement.LoadLibraryUtil.libExists;
import static com.linearity.datservicereplacement.LoadLibraryUtil.loadLibraryByPath;
import static com.linearity.datservicereplacement.Location.HookLocationManager.LocationGetter.initCellTowers;
import static com.linearity.datservicereplacement.Location.HookLocationManager.LocationGetter.initLocations;
import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.getPackageName;
import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.nonSysPackages;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_WATCHED_ARRAYMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.mSystemReady;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.SomeClasses.CanUseUnsafe;
import static com.linearity.datservicereplacement.SomeClasses.ComputerEngineClass;
import static com.linearity.datservicereplacement.SomeClasses.ComputerEngineSettings_getIsolatedOwner;
import static com.linearity.datservicereplacement.SomeClasses.ComputerEngine_isKnownIsolatedComputeApp;
import static com.linearity.datservicereplacement.SomeClasses.ComputerEngine_resolveInternalPackageNameInternalLocked;
import static com.linearity.datservicereplacement.SomeClasses.PackageSettingClass;
import static com.linearity.datservicereplacement.SomeClasses.PackageStateInternalClass;
import static com.linearity.datservicereplacement.SomeClasses.SharedUserSettingClass;
import static com.linearity.datservicereplacement.SomeClasses.UnsafeClass;
import static com.linearity.datservicereplacement.SomeClasses.WatchedArrayMapClass;
import static com.linearity.datservicereplacement.SomeClasses.WatchedSparseIntArrayClass;
import static com.linearity.datservicereplacement.SomeClasses.msgSamplingCOnfigClass;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.os.Binder;
import android.os.BinderProxy;
import android.os.Build;
import android.os.IBinder;
import android.util.Pair;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.io.CharStreams;
import com.linearity.datservicereplacement.Accessibility.HookAccessibility;
import com.linearity.datservicereplacement.Account.HookAccount;
import com.linearity.datservicereplacement.ActivityManagerService.HookAMS;
import com.linearity.datservicereplacement.ActivityManagerService.HookIActivityManager;
import com.linearity.datservicereplacement.ActivityManagerService.HookIActivityTaskManager;
import com.linearity.datservicereplacement.Ad.HookAd;
import com.linearity.datservicereplacement.Alarm.HookAlarm;
import com.linearity.datservicereplacement.AppFilter.HookAppFilter;
import com.linearity.datservicereplacement.AppGlobal.HookAppGlobal;
import com.linearity.datservicereplacement.AppOps.HookAppOpsService;
import com.linearity.datservicereplacement.Battery.HookIBattery;
import com.linearity.datservicereplacement.Biometric.HookBiometric;
import com.linearity.datservicereplacement.Bluetooth.HookBluetooth;
import com.linearity.datservicereplacement.Clipboard.HookIClipboard;
import com.linearity.datservicereplacement.Connectivity.HookConnectivityManager;
import com.linearity.datservicereplacement.ContentProviders.HookSettingsProvider;
import com.linearity.datservicereplacement.ContentProviders.HookTelephonyProvider;
import com.linearity.datservicereplacement.Context.HookContextImpl;
import com.linearity.datservicereplacement.Credential.HookCredential;
import com.linearity.datservicereplacement.DeviceIdentifiersPolicy.HookDeviceIdentifiersPolicy;
import com.linearity.datservicereplacement.DeviceLock.HookDeviceLock;
import com.linearity.datservicereplacement.Display.HookDisplay;
import com.linearity.datservicereplacement.Health.HookHealth;
import com.linearity.datservicereplacement.InputMethod.HookInputMethod;
import com.linearity.datservicereplacement.KeyGuard.HookKeyGuard;
import com.linearity.datservicereplacement.Location.HookGNSS;
import com.linearity.datservicereplacement.Location.HookLocationManager;
import com.linearity.datservicereplacement.MediaProjection.HookMediaProjectionManagerService;
import com.linearity.datservicereplacement.NFC.HookNFC;
import com.linearity.datservicereplacement.Notification.HookNotification;
import com.linearity.datservicereplacement.PackageManager.HookIPackageManager;
import com.linearity.datservicereplacement.Permission.HookPermissionManagerService;
import com.linearity.datservicereplacement.Phone.HookGsmCdmaPhone;
import com.linearity.datservicereplacement.PowerManager.HookIPowerStatsService;
import com.linearity.datservicereplacement.PowerManager.HookPowerManager;
import com.linearity.datservicereplacement.Search.HookSearch;
import com.linearity.datservicereplacement.Sensor.HookSensorPrivacyManager;
import com.linearity.datservicereplacement.ServiceManager.HookServiceManager;
import com.linearity.datservicereplacement.Telecom.HookTelecomService;
import com.linearity.datservicereplacement.Telephony.HookTelephony;
import com.linearity.datservicereplacement.Trust.HookTrust;
import com.linearity.datservicereplacement.Wallpaper.HookWallpaper;
import com.linearity.datservicereplacement.Wifi.HookWifiService;
import com.linearity.datservicereplacement.WindowManagerService.HookWindowManagerService;
import com.linearity.datservicereplacement.preventSystemCrushes.Prevents;
import com.linearity.utils.ClassHookExecutor;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.ServiceHooker;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;
import com.topjohnwu.superuser.NoShellException;
import com.topjohnwu.superuser.Shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class StartHook implements IXposedHookLoadPackage {
    public static final String ORDERED_PHONE_NUMBER = "";
    public static final Set<Class<?>> publicHookedPool = new HashSet<>();
    //if no,register.
    public static boolean isPublicHookedPoolRegistered(Class<?> toCheck){
        if (publicHookedPool.contains(toCheck)) {
            return true;
        }
        publicHookedPool.add(toCheck);
        return false;
    }
    //if no,register.
    public static boolean isHookedPoolRegistered(Class<?> toCheck,Set<Class<?>> hookedPool){
        if (hookedPool.contains(toCheck)) {
            return true;
        }
        hookedPool.add(toCheck);
        return false;
    }
    public static <T> boolean isSetRegistered(T toCheck,Set<T> pool){
        if (pool.contains(toCheck)) {
            return true;
        }
        pool.add(toCheck);
        return false;
    }
    public static final Set<String> WHITELIST_PACKAGE_NAMES = new HashSet<>();
    public static final Map<Integer,Map<String,Object>> settingsForUid = new HashMap<>();

    //Don't forget to add packages u want to tell real info!
    static {
        //since "com.android" is only for system app these 3 maybe not used
        WHITELIST_PACKAGE_NAMES.add("com.android.launcher3");
        WHITELIST_PACKAGE_NAMES.add("com.android.systemui");
        WHITELIST_PACKAGE_NAMES.add("com.android.externalstorage");

        WHITELIST_PACKAGE_NAMES.add("com.github.kr328.clash");
        WHITELIST_PACKAGE_NAMES.add("com.v2ray.ang");
        WHITELIST_PACKAGE_NAMES.add("com.google.android.googlequicksearchbox");
        WHITELIST_PACKAGE_NAMES.add("com.google.android.inputmethod.latin");
        WHITELIST_PACKAGE_NAMES.add("info.zamojski.soft.towercollector");
        WHITELIST_PACKAGE_NAMES.add("me.bmax.apatch");
        WHITELIST_PACKAGE_NAMES.add("top.niunaijun.blackdexa64");
        WHITELIST_PACKAGE_NAMES.add("com.github.metacubex.clash.meta");
        WHITELIST_PACKAGE_NAMES.add("io.wallpaperengine.weclient");
        WHITELIST_PACKAGE_NAMES.add("io.github.qauxv");
        WHITELIST_PACKAGE_NAMES.add("org.mozilla.firefox");
        WHITELIST_PACKAGE_NAMES.add("com.lb.lwp_plus");
        WHITELIST_PACKAGE_NAMES.add("com.emanuelef.remote_capture");
        WHITELIST_PACKAGE_NAMES.add("com.pcapdroid.mltm");
    }

    public static Multimap<String, ServiceHooker> listenServiceMap = HashMultimap.create();
    //ServiceHooker affects class
    //this marks classes the ServiceHooker affected
    public static Multimap<ServiceHooker, Class<?>> alreadyGotServiceClass = HashMultimap.create();
    public static Set<Object> IPackageManagers = new HashSet<>();
    //i mean package accessing computers.
    public static Set<Object> Computers = new HashSet<>();
    public static final long publicSeed = 0x20070831;

    public static Map<String, Function<Class<?>,Void>> registerServiceHook_map = new HashMap<>();
    public static final SimpleExecutor onServicePublish = param ->{
        IBinder binder = (IBinder) param.args[1];
        String name = (String) param.args[0];
        if (binder != null && registerServiceHook_map.containsKey(name)){
            if (binder instanceof BinderProxy){
                return;
            }
            try {
                registerServiceHook_map.get(name).apply(binder.getClass());
            }catch (Exception e){
                LoggerLog(e);
            }
            LoggerLog(name+"|"+binder.getClass());
        }
    };

    public static void doHook(ClassLoader classLoader){
        Class<?> hookClass;
        hookClass = XposedHelpers.findClassIfExists("android.os.SystemService",classLoader);
        if (hookClass != null){
            hookAllMethodsWithCache_Auto(hookClass,"publishBinderService",onServicePublish,noSystemChecker);
        }
        hookClass = XposedHelpers.findClassIfExists("android.os.ServiceManager",classLoader);
        if (hookClass != null){
            hookAllMethodsWithCache_Auto(hookClass,"addService",onServicePublish,noSystemChecker);
            Object iServiceManager = XposedHelpers.callStaticMethod(hookClass,"getIServiceManager");
            //add for all registered
            //TODO:Android 15 may use getService2,then getService is Deprecated
            for (Map.Entry<String, Function<Class<?>, Void>> entry:registerServiceHook_map.entrySet()){
                try {
                    Object s = XposedHelpers.callMethod(iServiceManager,"getService",entry.getKey());
                    if (s != null){
                        IBinder binder = (IBinder) s;//(IBinder) XposedHelpers.callMethod(s,"getBinder");
                        if (binder instanceof BinderProxy){
                            continue;
                        }
                        try {
                            entry.getValue().apply(binder.getClass());
                        }catch (Exception e){
                            LoggerLog(e);
                        }
                        LoggerLog(entry.getKey() + "|" + binder.getClass());
                    }
                }catch (Exception e){
                    LoggerLog(e);
                }

            }
        }
        hookClass = XposedHelpers.findClassIfExists("android.os.ServiceManagerProxy",classLoader);
        if (hookClass != null){
            hookAllMethodsWithCache_Auto(hookClass,"addService",onServicePublish,noSystemChecker);
        }
    }
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        initLocations();
        initCellTowers();

        if (!(Objects.equals(lpparam.processName,"android"))
                && !(Objects.equals(lpparam.processName,"system"))//in fact they have no name
                && lpparam.processName != null){mSystemReady = true;}

        if (lpparam.appInfo != null){
            if ((lpparam.appInfo.uid == 1000) && Objects.equals(null,lpparam.processName)){
                LoggerLog("loading lib"+DATZYGISK_LOCAL_PART+" for:null|" + lpparam.packageName);
                loadLibraryByPath(DATZYGISK_LOCAL_PART,null);
            }
        }
        if (libExists(DATZYGISK_LOCAL_PART)){
            hookLocation = true;
        }
        //try to get some classes.
        {
            ClassLoader classLoader = lpparam.classLoader;
            PackageStateInternalClass = XposedHelpers.findClassIfExists("com.android.server.pm.PackageSetting", classLoader);
            SharedUserSettingClass = XposedHelpers.findClassIfExists("com.android.server.pm.SharedUserSetting", classLoader);
            PackageSettingClass = XposedHelpers.findClassIfExists("com.android.server.pm.PackageSetting", classLoader);
            WatchedSparseIntArrayClass = XposedHelpers.findClassIfExists("com.android.server.utils.WatchedSparseIntArray", classLoader);
            if (WatchedSparseIntArrayClass != null){
                ComputerEngineSettings_getIsolatedOwner = XposedHelpers.findMethodExactIfExists("com.android.server.pm.ComputerEngine$Settings",lpparam.classLoader,"getIsolatedOwner", WatchedSparseIntArrayClass,int.class);
            }
            ComputerEngineClass = XposedHelpers.findClassIfExists("com.android.server.pm.ComputerEngine", classLoader);
            UnsafeClass = XposedHelpers.findClassIfExists("sun.misc.Unsafe", classLoader);
            if (UnsafeClass != null){
                CanUseUnsafe = true;
            }
            if (ComputerEngineClass != null){
                XposedBridge.hookAllConstructors(ComputerEngineClass, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Computers.add(param.thisObject);
                    }
                });
                ComputerEngine_isKnownIsolatedComputeApp = XposedHelpers.findMethodExactIfExists(ComputerEngineClass,
                        "isKnownIsolatedComputeApp",
                        int.class);
                ComputerEngine_resolveInternalPackageNameInternalLocked = XposedHelpers.findMethodExact(ComputerEngineClass,
                        "resolveInternalPackageNameInternalLocked",
                        String.class,long.class,int.class);
            }
            WatchedArrayMapClass = XposedHelpers.findClassIfExists("com.android.server.utils.WatchedArrayMap", classLoader);
            msgSamplingCOnfigClass = XposedHelpers.findClassIfExists("com.android.internal.app.MessageSamplingConfig", classLoader);
        }

        Class<?> hookClass;

        if (WatchedArrayMapClass != null){
            EMPTY_WATCHED_ARRAYMAP = XposedHelpers.newInstance(WatchedArrayMapClass);
        }
//        //but these affects showing :(
//        hookClass = XposedHelpers.findClassIfExists("com.android.server.wm.ClientLifecycleManager", classLoader);
//        if (hookClass != null){
//            try {
//                Class<?> transactionClass = XposedHelpers.findClass("android.app.servertransaction.ClientTransaction", classLoader);
//                XposedHelpers.findAndHookMethod(hookClass, "scheduleTransaction", transactionClass, new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        try{
//                            Object mLifecycleStateRequest = XposedHelpers.getObjectField(param.args[0],"mLifecycleStateRequest");
//                            if (mLifecycleStateRequest == null){return;}
//                            String className = mLifecycleStateRequest.getClass().getName();
//                            if (className.contains("ResumeActivityItem") || className.contains("PauseActivityItem")){
//
//                            }
//                        }catch (Exception e){
//                            LoggerLog(e);
//                        }
//                    }
//                });
//            }catch (Exception e){
//                LoggerLog(e);
//            }
//        }

//        hookClass = XposedHelpers.findClassIfExists("android.app.ActivityThread", classLoader);
//        if (hookClass != null){
//            XposedBridge.hookAllMethods(hookClass, "performResumeActivity", new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                    try {
//                        Object mActivityClientRecord = param.args[0];
//                        /*LoadedApk*/Object packageInfo = XposedHelpers.getObjectField(mActivityClientRecord,"packageInfo");
//                        String mPackageName = (String) XposedHelpers.getObjectField(packageInfo,"mPackageName");
//                        LoggerLog(mPackageName + " | onResume");
//                    }catch (Exception e){
//                        LoggerLog(e);
//                    }
//                }
//            });
//        }


        if (false){
            //when i have no idea what to do,show all transact
            hookClass = XposedHelpers.findClassIfExists("android.os.Binder", lpparam.classLoader);
            if (hookClass != null) {
                XposedBridge.hookAllMethods(hookClass, "execTransact", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (!isSystemApp(Binder.getCallingUid())){
                            LoggerLog(new Exception(
                                    Arrays.deepToString(param.args)
                                    + "\n" + Binder.getCallingUid()
                                    + "\n" + getPackageName(Binder.getCallingUid())
                                    + "\n" + param.thisObject.toString()
                            ));
                        }
//                        if (!isSystemApp(Binder.getCallingUid())) {
//                            String packageName = "";
//                            if (param.thisObject.toString().startsWith("com.android.server.BluetoothManagerService")) {
//                                param.setResult(true);
//                                return;
//                            }
//
//                            if (nonSysPackages.get(Binder.getCallingUid()) != null) {
//                                packageName = nonSysPackages.get(Binder.getCallingUid()).packageName;
//                            }
//                            LoggerLog(new Exception(param.thisObject.toString() + " " + param.args[0] + " " + param.args[1] + " " + param.args[2] + " " + param.args[3] + " " + packageName));
//                        }
                    }
                });
            }
        }
        initHooks();
//        hookForClassLoader(XposedBridge.BOOTCLASSLOADER);
        hookForClassLoader(lpparam.classLoader);


//        hookClass = XposedHelpers.findClassIfExists("com.android.server.SystemServiceManager", classLoader);
//        if (hookClass != null){
//
//            XposedBridge.hookAllMethods(hookClass, "startService", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    if (param.args[0]==null){
//                        LoggerLog("[linearity-startSystemService]","null");
//                        return;
//                    }
//                    LoggerLog("[linearity-startSystemService]",param.args[0].toString());
//                }
//            });
//
//            XposedBridge.hookAllMethods(hookClass, "startOtherServices", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    if (param.getThrowable() != null){
//                        LoggerLog(param.getThrowable());
//                    }
//                }
//            });
//        }
//        hookClass = XposedHelpers.findClassIfExists("com.android.server.TelephonyRegistry", classLoader);
//        if (hookClass != null){
//            XposedBridge.hookAllConstructors(hookClass, new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    Context mContext = (Context) param.args[0];
//                    TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
//                    LoggerLog(mContext + " | " + telephonyManager);
//                }
//            });
//        }



//        hookClass = XposedHelpers.findClassIfExists("android.app.ContextImpl", classLoader);
//        if (hookClass != null){
//            hookContextImpl(hookClass);
//        }

//        hookClass = XposedHelpers.findClassIfExists("com.android.server.am.ActiveServices", classLoader);
//        if (hookClass != null){
//            XposedBridge.hookAllMethods(hookClass, "startServiceLocked", new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                    if (param.args[4] == null){return;}
//                    if (!isSystemApp((int) param.args[4])){
//                        StringBuilder sb = new StringBuilder();
//                        for (Object o:param.args){
//                            if (o != null){
//                                sb.append(o.toString()).append("\n");
//                            }else {
//                                sb.append("null\n");
//                            }
//                        }
//                        LoggerLog(sb.toString());
//                    }
//                }
//            });
//        }

//        hookClass = XposedHelpers.findClassIfExists("com.android.server.wm.ActivityTaskManagerService", classLoader);
//        if (hookClass != null){
//            XposedBridge.hookAllMethods(hookClass, "startActivityAsUser", new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                    int userid = (int) param.args[11];
//                    if (param.args[3] == null){return;}
//                    Intent intent = (Intent) param.args[3];
//                    if (intent != null){
//                        String packageName;
//                        LoggerLog(intent.toString());
//                        if (intent.getPackage() != null){
//                            packageName = intent.getPackage();
//                        }else {
//                            String[] splited = intent.toString().split("cmp=");
//                            if (splited.length == 1){
//                                return;
//                            }
//                            packageName = splited[1].split("/")[0];
//                        }
//                        if (packageName == null){
//                            return;
//                        }
//                        if (!isSystemApp(packageName,userid)){
//                            StringBuilder sb = new StringBuilder();
//                            for (Object o:param.args){
//                                if (o != null){
//                                    sb.append(o.toString()).append("\n");
//                                }else {
//                                    sb.append("null\n");
//                                }
//                            }
//                            LoggerLog(sb.toString());
//                        }
//                    }
//                }
//            });
//        }

    }
    public static Multimap<String, ClassHookExecutor> classesAndHooks = HashMultimap.create();
    public static final Set<ClassLoader> iteratedClassLoaders = new HashSet<>();
    public static boolean hookLocation = false;

    /**
     * i have proofed that there are many classloaders when hooking BroadcastQueueImpl.
     * <p>so i rewrote doHooks</p>
     */
    public static void initHooks(){
        listenClassLoader();
//        Prevents.doHook();

        HookAMS.doHook();
        HookIPackageManager.doHook();
        HookAppFilter.doHook();

        HookInputMethod.doHook();
        HookIClipboard.doHook();

        HookWallpaper.doHook();

        HookSettingsProvider.doHook();

        HookIPowerStatsService.doHook();
        HookPowerManager.doHook();
        HookIBattery.doHook();
        HookDisplay.doHook();

        HookSensorPrivacyManager.doHook();

        HookServiceManager.doHook();

        HookPermissionManagerService.doHook();
        HookAppOpsService.doHook();

        HookWindowManagerService.doHook();
        HookIActivityManager.doHook();
        HookIActivityTaskManager.doHook();

        HookKeyGuard.doHook();
        //causes crush,why?
        HookTrust.doHook();

        HookAlarm.doHook();

        HookAccount.doHook();
        HookNotification.doHook();
        HookAccessibility.doHook();

        HookCredential.doHook();

        HookBluetooth.doHook();
        HookNFC.doHook();

        HookTelecomService.doHook();
        HookTelephony.doHook();
        HookTelephonyProvider.doHook();

        if (hookLocation){
            HookLocationManager.doHook();
            HookGNSS.doHook();
        }

//        HookNetworkManagementService.doHook();
        HookConnectivityManager.doHook();

        HookContextImpl.doHook();
        HookDeviceIdentifiersPolicy.doHook();
        HookDeviceLock.doHook();
        HookMediaProjectionManagerService.doHook();
        HookWifiService.doHook();

        HookSearch.doHook();

        HookHealth.doHook();
        HookBiometric.doHook();

        HookAd.doHook();

        Others.doHook();
//        HookGsmCdmaPhone.doHook();
//        HookAppGlobal.doHook();
//        MessageFinder.hookMessage();
    }
    public static void hookForClassLoader(ClassLoader classLoader){
        if (isSetRegistered(classLoader,iteratedClassLoaders)){return;}
        for (String className:classesAndHooks.keys()){
            Class<?> hookClass = XposedHelpers.findClassIfExists(className,classLoader);
            if (hookClass != null){
                for (ClassHookExecutor executor:classesAndHooks.get(className)){
                    Pair<String,ClassHookExecutor> executorKey = new Pair<>(className,executor);
                    if (usedExecutors.get(classLoader).contains(executorKey)){
                        continue;
                    }
                    usedExecutors.put(classLoader,executorKey);
                    try {
                        executor.startHookClass(hookClass);
                    }catch (Exception e){
                        LoggerLog(e);
                    }
                }
            }
        }
        doHook(classLoader);
    }
    public static Multimap<ClassLoader, Pair<String,ClassHookExecutor>> usedExecutors = HashMultimap.create();
    public static final Set<Class<?>> hookedClassLoaderPool = new HashSet<>();
    static {
        hookedClassLoaderPool.add(XposedBridge.BOOTCLASSLOADER.getClass());
    }
    //all classLoaders are belong to me
    public static void listenClassLoader(){
        SimpleExecutorWithMode resultClass = new SimpleExecutorWithMode(MODE_AFTER, param -> {
            ClassLoader classLoader = (ClassLoader) param.thisObject;
            hookForClassLoader(classLoader);
            Class<?> result = (Class<?>) param.getResult();
            if (result != null){
                String className = result.getName();
                if (classesAndHooks.containsKey(result.getName())){
                    for (ClassHookExecutor executor:classesAndHooks.get(className)){
                        Pair<String,ClassHookExecutor> executorKey = new Pair<>(className,executor);
                        if (usedExecutors.get(classLoader).contains(executorKey)){
                            continue;
                        }
                        usedExecutors.put(classLoader,executorKey);
                        try {
                            executor.startHookClass(result);
                        }catch (Exception e){
                            LoggerLog(e);
                        }
                    }
                }
            }
        });
        SimpleExecutorWithMode resultNotUsed = new SimpleExecutorWithMode(MODE_AFTER, param -> {
            ClassLoader classLoader = (ClassLoader) param.thisObject;
            hookForClassLoader(classLoader);
        });
        ClassHookExecutor[] ex_classLoaderArr = new ClassHookExecutor[1];
        ex_classLoaderArr[0] = hookClass -> {
            if (isHookedPoolRegistered(hookClass,hookedClassLoaderPool)){return;}
            hookAllMethodsWithCache_Auto(hookClass,"findClass",resultClass,noSystemChecker);
            hookAllMethodsWithCache_Auto(hookClass,"defineClass",resultClass,noSystemChecker);
            hookAllMethodsWithCache_Auto(hookClass,"findSystemClass",resultClass,noSystemChecker);
            hookAllMethodsWithCache_Auto(hookClass,"findBootstrapClassOrNull",resultClass,noSystemChecker);
            hookAllMethodsWithCache_Auto(hookClass,"findLoadedClass",resultClass,noSystemChecker);
            hookAllMethodsWithCache_Auto(hookClass,"getParent",new SimpleExecutorWithMode(MODE_AFTER, param -> {
                ClassLoader classLoader = (ClassLoader) param.getResult();
                if (classLoader != null){
                    ex_classLoaderArr[0].startHookClass(classLoader.getClass());
                }
            }),noSystemChecker);
            hookAllMethodsWithCache_Auto(hookClass,"getClassLoader",new SimpleExecutorWithMode(MODE_AFTER, param -> {
                ClassLoader classLoader = (ClassLoader) param.getResult();
                if (classLoader != null){
                    ex_classLoaderArr[0].startHookClass(classLoader.getClass());
                }
            }),noSystemChecker);
            hookAllMethodsWithCache_Auto(hookClass,"findLibrary",resultNotUsed,noSystemChecker);
        };
        ClassHookExecutor ex_classLoader = ex_classLoaderArr[0];

        classesAndHooks.put("java.lang.BootClassLoader",ex_classLoader);
        classesAndHooks.put("java.lang.ClassLoader",ex_classLoader);
        classesAndHooks.put("java.security.SecureClassLoader",ex_classLoader);
        classesAndHooks.put("java.net.URLClassLoader",ex_classLoader);
        classesAndHooks.put("java.net.FactoryURLClassLoader",ex_classLoader);
        classesAndHooks.put("java.util.ResourceBundle$RBClassLoader",ex_classLoader);

        classesAndHooks.put("org.chromium.base.WrappedClassLoader",ex_classLoader);
        classesAndHooks.put("org.chromium.base.BundleUtils$SplitCompatClassLoader",ex_classLoader);

        classesAndHooks.put("android.icu.impl.ClassLoaderUtil$BootstrapClassLoader",ex_classLoader);
        classesAndHooks.put("android.app.LoadedApk.WarningContextClassLoader",ex_classLoader);
//
        classesAndHooks.put("dalvik.system.BaseDexClassLoader",ex_classLoader);
        classesAndHooks.put("dalvik.system.PathClassLoader",ex_classLoader);
        classesAndHooks.put("dalvik.system.DexClassLoader",ex_classLoader);
        classesAndHooks.put("dalvik.system.DelegateLastClassLoader",ex_classLoader);
    }

    public static boolean calledOnStartedFlag = false;
    public static void callOnStarted(){
        if (calledOnStartedFlag){return;}
        calledOnStartedFlag = true;
        try {
            setOtherProperties();
        }catch (Exception e){
            LoggerLog(e);
        }

    }
    private static final String[] constCommands = {
            "resetprop -n ro.boot.verifiedbootstate green",
            "resetprop -n vendor.boot.verifiedbootstate green",
            "resetprop -n ro.secureboot.lockstate locked",
            "resetprop -n vendor.boot.vbmeta.device_state locked",
            "resetprop -n ro.boot.vbmeta.device_state locked",
            "resetprop -n ro.boot.flash.locked locked",
            "resetprop -n sys.oem_unlock_allowed 0"
    };
    public static void setOtherProperties() throws NoShellException, IOException {
        Shell.cmd("resetprop").submit(result -> LoggerLog(new Exception("allProps:"
                + "\n" + Arrays.deepToString(result.getOut().toArray()))
                + "\n" + result.getErr()
                + "\n" + result.isSuccess()
                + "\n" + result.getCode()
        ));
        Shell.getShell(shell -> {
            if (!shell.isRoot()){
                LoggerLog(new Exception("shell is not root!"));
            }
            shell.newJob()
                    .add("resetprop -n ro.boot.verifiedbootstate green",
                            "resetprop -n vendor.boot.verifiedbootstate green",
                            "resetprop -n ro.secureboot.lockstate locked",
                            "resetprop -n vendor.boot.vbmeta.device_state locked",
                            "resetprop -n ro.boot.vbmeta.device_state locked",
                            "resetprop -n ro.boot.flash.locked locked",
                            "resetprop -n sys.oem_unlock_allowed 0"
                    ).exec();
            List<String> fingerPrintOut = shell.newJob().add("resetprop ro.build.fingerprint").exec().getOut();
            List<Pair<String,String>> replaceStringsInProp = new ArrayList<>();//Pair<ReplaceString,ReplaceWith>
            //fixing product code
            for(String s:fingerPrintOut){
                if (s==null){continue;}
                for (String line:s.split("\n")){
                    if (line.isEmpty()){continue;}
                    String[] stringParts = line.split("/");
                    if (stringParts.length < 3){continue;}
                    String productPartWithAndroidVersion = stringParts[2];
                    if (productPartWithAndroidVersion == null){continue;}
                    if (productPartWithAndroidVersion.isEmpty()){continue;}
                    String[] productParts = productPartWithAndroidVersion.split(":");
                    if (productParts.length <= 2){continue;}
                    if (productParts[0].isEmpty()){continue;}
                    replaceStringsInProp.add(new Pair<>(productPartWithAndroidVersion,productParts[0] + ":" + Build.VERSION.RELEASE));
                    replaceStringsInProp.add(new Pair<>(Build.DEVICE,productParts[0]));
                }
            }
            replaceStringsInProp.add(new Pair<>("userdebug","user"));
            replaceStringsInProp.add(new Pair<>("debug","release"));
            List<String> allPropsOut = shell.newJob().add("resetprop").exec().getOut();
            for (String prop:allPropsOut){
                if (prop == null){
                    continue;
                }
                for (String propLine:prop.split("\n")){
                    if (propLine.isEmpty()){continue;}
                    String[] kv = propLine.split("]: \\[");
                    if (kv.length < 2){
                        LoggerLog(new Exception("resetprop kv pair error at:" + propLine));
                        continue;
                    }
                    String key = kv[0].substring(1);
                    kv[1] = kv[1].substring(0, kv.length-1);
                    String valueReplaced = kv[1];
                    for (Pair<String,String> replaceStringKVPair:replaceStringsInProp){
                        if (Objects.equals(replaceStringKVPair.first,replaceStringKVPair.second)){continue;}
                        valueReplaced = valueReplaced.replace(replaceStringKVPair.first,replaceStringKVPair.second);
                    }
                    if (Objects.equals(valueReplaced,kv[1])){
                        continue;
                    }
                    String command = "resetprop -n " + "\"" + key+ "\" \"" + valueReplaced + "\"";
                    LoggerLog(new Exception("resetting prop:"+command));
                    shell.newJob().add(command).exec();
                }
        }
        });
    }


    public static List<String> filtedStrings(Collection<String> strings, int callingUid){

        String self = "null";
        boolean changedSelf = false;
        if (nonSysPackages.containsKey(callingUid) && nonSysPackages.get(callingUid)!=null){
            self = nonSysPackages.get(callingUid).packageName;
            changedSelf = true;
        }
        List<String> resultList = new LinkedList<>();
        for (String s:strings){
            if (s.startsWith("com.tencent")
                    || s.contains("alipay")
                    || s.contains("Alipay")
            ){
                resultList.add(s);
            }else if(changedSelf && self != null){
                if(self.equals(s)){
                    resultList.add(s);
                }
            }
        }
        return resultList;
    }
    public static List<String> filtedStrings(String[] strings,int callingUid){

        String self = "null";
        boolean changedSelf = false;
        if (nonSysPackages.containsKey(callingUid) && nonSysPackages.get(callingUid)!=null){
            self = nonSysPackages.get(callingUid).packageName;
            changedSelf = true;
        }
        List<String> resultList = new LinkedList<>();
        for (String s:strings){
            if (s.startsWith("com.tencent")
                    || s.contains("alipay")
                    || s.contains("Alipay")
            ){
                resultList.add(s);
            }else if(changedSelf && self != null){
                if(self.equals(s)){
                    resultList.add(s);
                }
            }
        }
        return resultList;
    }

    //no you dont need,android has a filter
    public static boolean isAccessiblePackageName(int callingUID,String accessingPackageName){
        if (isSystemApp(callingUID)){return true;}
        if (nonSysPackages.containsKey(callingUID) && nonSysPackages.get(callingUID) != null){
            String pkgName = nonSysPackages.get(callingUID).packageName;
            if (pkgName == null){return false;}
            if (Objects.equals(accessingPackageName,nonSysPackages.get(callingUID).packageName)){
                return true;
            }
            if (nonSysPackages.get(callingUID).packageName.startsWith("com.tencent")
                    || pkgName.contains("alipay")
                    || pkgName.contains("Alipay")
            ){
                return true;
            }
        }
        return false;
    }
}

