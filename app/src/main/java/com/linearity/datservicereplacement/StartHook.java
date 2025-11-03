package com.linearity.datservicereplacement;

import static com.linearity.datservicereplacement.LibNames.DATZYGISK_LOCAL_PART;
import static com.linearity.datservicereplacement.LoadLibraryUtil.libExists;
import static com.linearity.datservicereplacement.LoadLibraryUtil.loadLibraryByPath;
import static com.linearity.datservicereplacement.ReturnIfNonSys.SyntheticNameResolver.fullResolvedClassName;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.initCellTowers;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager.LocationGetter.initLocations;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.getPackageName;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.nonSysPackages;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_WATCHED_ARRAYMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.mSystemReady;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
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
import com.google.common.collect.MapMaker;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.linearity.datservicereplacement.androidhooking.HookParcel;
import com.linearity.datservicereplacement.androidhooking.com.android.permissioncontroller.HookPermissionManagerUI;
import com.linearity.datservicereplacement.androidhooking.com.android.providers.HookMediaProvider;
import com.linearity.datservicereplacement.androidhooking.com.android.server.accessibility.HookAccessibility;
import com.linearity.datservicereplacement.androidhooking.com.android.server.accounts.HookAccount;
import com.linearity.datservicereplacement.androidhooking.com.android.server.am.HookAMS;
import com.linearity.datservicereplacement.androidhooking.com.android.server.am.HookIActivityManager;
import com.linearity.datservicereplacement.androidhooking.com.android.server.am.HookProcessList;
import com.linearity.datservicereplacement.androidhooking.com.android.server.content.HookContentService;
import com.linearity.datservicereplacement.androidhooking.com.android.server.job.HookJobSchedulerService;
import com.linearity.datservicereplacement.androidhooking.com.android.server.pm.HookSELinuxMMAC;
import com.linearity.datservicereplacement.androidhooking.com.android.server.pm.hookPackageManager;
import com.linearity.datservicereplacement.androidhooking.com.android.server.policy.keyguard.HookKeyGuard;
import com.linearity.datservicereplacement.androidhooking.com.android.server.wm.HookBack;
import com.linearity.datservicereplacement.androidhooking.com.android.server.wm.HookClientLifecycleManager;
import com.linearity.datservicereplacement.androidhooking.com.android.server.wm.HookIActivityTaskManager;
import com.linearity.datservicereplacement.androidhooking.com.android.adservices.HookAd;
import com.linearity.datservicereplacement.androidhooking.com.android.server.alarm.HookAlarm;
import com.linearity.datservicereplacement.androidhooking.com.android.server.pm.HookAppFilter;
import com.linearity.datservicereplacement.androidhooking.com.android.server.appop.HookAppOpsService;
import com.linearity.datservicereplacement.androidhooking.com.android.server.am.HookIBattery;
import com.linearity.datservicereplacement.androidhooking.com.android.server.biometrics.HookBiometric;
import com.linearity.datservicereplacement.androidhooking.com.android.bluetooth.HookBluetooth;
import com.linearity.datservicereplacement.androidhooking.com.android.server.clipboard.HookIClipboard;
import com.linearity.datservicereplacement.androidhooking.com.android.server.HookConnectivityManager;
import com.linearity.datservicereplacement.androidhooking.com.android.providers.HookContentProvider;
import com.linearity.datservicereplacement.androidhooking.com.android.providers.HookSettingsProvider;
import com.linearity.datservicereplacement.androidhooking.android.app.HookContextImpl;
import com.linearity.datservicereplacement.androidhooking.com.android.server.credentials.HookCredential;
import com.linearity.datservicereplacement.androidhooking.com.android.server.os.HookDeviceIdentifiersPolicy;
import com.linearity.datservicereplacement.androidhooking.com.android.server.devicelock.HookDeviceLock;
import com.linearity.datservicereplacement.androidhooking.com.android.server.display.HookDisplay;
import com.linearity.datservicereplacement.androidhooking.com.android.server.healthconnect.HookHealth;
import com.linearity.datservicereplacement.androidhooking.com.android.server.inputmethod.HookInputMethod;
import com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookGNSS;
import com.linearity.datservicereplacement.androidhooking.com.android.server.location.HookLocationManager;
import com.linearity.datservicereplacement.androidhooking.com.android.server.media.projection.HookMediaProjectionManagerService;
import com.linearity.datservicereplacement.androidhooking.com.android.nfc.HookNFC;
import com.linearity.datservicereplacement.androidhooking.android.app.notification.HookNotification;
import com.linearity.datservicereplacement.androidhooking.com.android.server.pm.HookIPackageManager;
import com.linearity.datservicereplacement.androidhooking.com.android.server.pm.permission.HookPermissionManagerService;
import com.linearity.datservicereplacement.androidhooking.com.android.server.powerstats.HookIPowerStatsService;
import com.linearity.datservicereplacement.androidhooking.com.android.server.power.HookPowerManager;
import com.linearity.datservicereplacement.androidhooking.com.android.server.wm.HookTaskRelated;
import com.linearity.datservicereplacement.androidhooking.com.android.systemui.HookScreenShot;
import com.linearity.datservicereplacement.androidhooking.com.android.server.search.HookSearch;
import com.linearity.datservicereplacement.androidhooking.com.android.server.HookSensorPrivacyManager;
import com.linearity.datservicereplacement.androidhooking.android.os.HookServiceManager;
import com.linearity.datservicereplacement.androidhooking.com.android.server.telecom.HookTelecomService;
import com.linearity.datservicereplacement.androidhooking.android.app.HookTrust;
import com.linearity.datservicereplacement.androidhooking.com.android.server.wallpaper.HookWallpaper;
import com.linearity.datservicereplacement.androidhooking.com.android.server.wifi.HookWifiService;
import com.linearity.datservicereplacement.androidhooking.com.android.server.wm.HookWindowManagerService;
import com.linearity.datservicereplacement.androidhooking.com.android.systemui.HookSystemUIApplication;
import com.linearity.datservicereplacement.androidhooking.com.android.systemui.statusbar.HookStatusBar;
import com.linearity.utils.ClassHookExecutor;
import com.linearity.utils.HookUtils;
import com.linearity.utils.ServiceHooker;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;
import com.topjohnwu.superuser.NoShellException;
import com.topjohnwu.superuser.Shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class StartHook implements IXposedHookLoadPackage {
    public static final String ORDERED_PHONE_NUMBER = "";
    public static <T> Set<T> newWeakSet(){
        return Collections.newSetFromMap(new MapMaker().weakKeys().makeMap());
    }
    public static final Set<Class<?>> publicHookedPool = newWeakSet();
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
        return !pool.add(toCheck);
    }
    public static <T> boolean isSetRegistered(T toCheck,Map<T,Boolean> pool){
        if (pool.containsKey(toCheck)) {
            return true;
        }
        pool.put(toCheck,true);
        return false;
    }
    //TODO:Dynamic add packages and persists after reboot
    public static final Set<String> WHITELIST_PACKAGE_NAMES = new HashSet<>();
    public static final Set<String> SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES = new HashSet<>();
    public static final Set<String> SELINUX_TRUST_AS_NORMAL_PACKAGE_HEADERS = new HashSet<>();
    public static final Map<Integer,Map<String,Object>> settingsForUid = new HashMap<>();

    //Don't forget to add packages u want to tell real info!
    static {
        //since "com.android" is only for system app these 3 maybe not used
        WHITELIST_PACKAGE_NAMES.add("com.android.launcher3");
        WHITELIST_PACKAGE_NAMES.add("com.android.systemui");
        WHITELIST_PACKAGE_NAMES.add("com.android.externalstorage");

        WHITELIST_PACKAGE_NAMES.add("com.google.android.gms");
        WHITELIST_PACKAGE_NAMES.add("com.google.android.googlequicksearchbox");
        WHITELIST_PACKAGE_NAMES.add("com.google.android.inputmethod.latin");

        for (String pkgName:new String[]{
                "com.github.kr328.clash",
                "com.v2ray.ang",
                "info.zamojski.soft.towercollector",
                "me.bmax.apatch",
                "top.niunaijun.blackdexa64",
                "com.github.metacubex.clash.meta",
                "io.wallpaperengine.weclient",
                "io.github.qauxv",
                "org.mozilla.firefox",
                "com.lb.lwp_plus",
                "com.emanuelef.remote_capture",
                "com.pcapdroid.mltm",
                "me.bingyue.IceCore",
                "com.zcshou.gogogo",
                "com.zhufucdev.motion_emulator",
                "com.zhufucdev.mock_location_plugin",
                "com.zhufucdev.cp_plugin",
                "com.zhufucdev.ws_plugin",
                "jp.pxv.android",
                "com.twitter.android",
                "com.supercell.clashroyale",
                "com.sega.ColorfulStage.en",
                "com.sega.pjsekai",
                "com.hermes.mk.asia.qooapp",
                "com.drdisagree.colorblendr",
                "com.prism.hider.tg.ninja",
                "com.android.messaging",
                "com.android.htmlviewer",
        }){
            WHITELIST_PACKAGE_NAMES.add(pkgName);
            SELINUX_TRUST_AS_NORMAL_PACKAGE_NAMES.add(pkgName);
        }
        SELINUX_TRUST_AS_NORMAL_PACKAGE_HEADERS.add("com.android");
//        WHITELIST_PACKAGE_NAMES.add("com.lerist.fakelocation");
//        WHITELIST_PACKAGE_NAMES.add("com.MobileTicket");
    }

    public static Multimap<String, ServiceHooker> listenServiceMap = HashMultimap.create();
    //ServiceHooker affects class
    //this marks classes the ServiceHooker affected
    public static Multimap<ServiceHooker, Class<?>> alreadyGotServiceClass = HashMultimap.create();
    public static Set<Object> IPackageManagers = new HashSet<>();
    //i mean package accessing computers.
    public static Set<Object> Computers = new HashSet<>();

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
        try {
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
            hookForClassLoader(XposedBridge.BOOTCLASSLOADER);
            hookForClassLoader(lpparam.classLoader);
        }catch (Exception e){
            LoggerLog(e);
        }

    }
    public static Multimap<String, ClassHookExecutor> classesAndHooks = Multimaps.synchronizedSetMultimap(HashMultimap.create());
    public static final Set<ClassLoader> iteratedClassLoaders = Collections.newSetFromMap(new MapMaker().weakKeys().makeMap());
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
        HookSELinuxMMAC.doHook();
        hookPackageManager.doHook();
        HookAppFilter.doHook();
        HookProcessList.doHook();

        HookInputMethod.doHook();
        HookIClipboard.doHook();

        HookWallpaper.doHook();

        HookSettingsProvider.doHook();
        HookContentProvider.doHook();

        HookIPowerStatsService.doHook();
        HookPowerManager.doHook();
        HookIBattery.doHook();
        HookDisplay.doHook();

        HookSensorPrivacyManager.doHook();

        HookServiceManager.doHook();

        HookPermissionManagerService.doHook();
        HookAppOpsService.doHook();

        HookWindowManagerService.doHook();
        HookStatusBar.doHook();
        HookScreenShot.doHook();
        HookSystemUIApplication.doHook();
        HookContentService.doHook();

        HookIActivityManager.doHook();
        HookIActivityTaskManager.doHook();

        HookKeyGuard.doHook();
        HookTrust.doHook();

        HookAlarm.doHook();

        HookAccount.doHook();
        HookNotification.doHook();
        HookAccessibility.doHook();

        HookCredential.doHook();

        HookBluetooth.doHook();
        HookNFC.doHook();

        HookTaskRelated.doHook();

        HookTelecomService.doHook();
//        HookTelephony.doHook();//causes endless reboot TODO:Find out why
//        HookTelephonyProvider.doHook();//I didn't even implement it.//TODO: implement

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

        HookJobSchedulerService.doHook();


        HookPermissionManagerUI.doHook();
        HookMediaProvider.doHook();
        HookParcel.doHook();

        HookClientLifecycleManager.doHook();

        HookBack.doHook();

//        Others.doHook();

//        HookGsmCdmaPhone.doHook();
//        HookAppGlobal.doHook();
//        MessageFinder.hookMessage();

//        avoidListeningMethod(Configuration.class,Configuration.class,"<init>");
//        avoidListeningMethod(Configuration.class,HookIActivityManager.class,ALL_METHOD);
////        avoidListeningMethod(Configuration.class,Configuration.class,"equals");
////        avoidListeningMethod(Configuration.class,Configuration.class,"hashCode");
//        listenClass(WindowConfiguration.class);
        HookUtils.startListen();
    }
    public static void hookForClassLoader(ClassLoader classLoader){
        if (isSetRegistered(classLoader,iteratedClassLoaders)){return;}
        for (String className:classesAndHooks.keys()){

            Class<?> hookClass = XposedHelpers.findClassIfExists(className,classLoader);
            if (hookClass == null){
                try {
                    hookClass = classLoader.loadClass(className);
                }catch (Exception ignore){}
            }
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
    private static final ConcurrentMap<ClassLoader, Collection<Pair<String,ClassHookExecutor>>> usedExecutors_wrappedMap = new MapMaker()
            .weakKeys()
            .makeMap();
    public static final Multimap<ClassLoader, Pair<String,ClassHookExecutor>> usedExecutors = Multimaps.newMultimap(
            usedExecutors_wrappedMap,
            ConcurrentHashMap::newKeySet
    );
    public static final Set<Class<?>> hookedClassLoaderPool = newWeakSet();
    static {
        hookedClassLoaderPool.add(XposedBridge.BOOTCLASSLOADER.getClass());
    }


    private static void executeWithClassName(String className,Class<?> hookClass,ClassLoader classLoader){
        if (classesAndHooks.containsKey(className)){
            for (ClassHookExecutor executor:classesAndHooks.get(className)){
                Pair<String,ClassHookExecutor> executorKey = new Pair<>(className,executor);
                {
                    if (usedExecutors.get(classLoader).contains(executorKey)) {
                        continue;
                    }
                    usedExecutors.put(classLoader, executorKey);
                }//check if  hooked
                try {
                    executor.startHookClass(hookClass);
                }catch (Exception e){
                    LoggerLog(e);
                }
            }
        }
    }

    //all classLoaders are belong to me
    public static void listenClassLoader(){
        SimpleExecutorWithMode resultClass = new SimpleExecutorWithMode(MODE_AFTER, param -> {
            ClassLoader classLoader = (ClassLoader) param.thisObject;
            hookForClassLoader(classLoader);
            Class<?> result = (Class<?>) param.getResult();
            if (result != null){

                String classNameResolved = fullResolvedClassName(result);
                String classNameNotResolved = result.getName();

                executeWithClassName(classNameResolved,result,classLoader);
                executeWithClassName(classNameNotResolved,result,classLoader);
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
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(6000);
                } catch (InterruptedException e) {
                    LoggerLog(e);
                }
                try {
                    setOtherProperties();
                }catch (Exception e){
                    LoggerLog(e);
                }
            }
        }.start();

    }
    private static final String[] constCommands = {
            "resetprop -n dalvik.vm.usejit false",//hope to avoid unloading

            //but now we check unlock stats via keystore,i can only block it by disable accessing to that service
            "resetprop -n ro.boot.vbmeta.device_state locked",
            "resetprop -n ro.boot.verifiedbootstate green",
            "resetprop -n ro.boot.flash.locked 1",
            "resetprop -n ro.boot.veritymode enforcing",
            "resetprop -n ro.boot.warranty_bit 0",
            "resetprop -n ro.warranty_bit 0",
            "resetprop -n ro.debuggable 0",
            "resetprop -n ro.secure 1",
            "resetprop -n ro.build.type user",
            "resetprop -n ro.build.tags release-keys",
            "resetprop -n ro.vendor.boot.warranty_bit 0",
            "resetprop -n ro.vendor.warranty_bit 0",
            "resetprop -n vendor.boot.vbmeta.device_state locked",
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
                    .add(constCommands).exec();
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

