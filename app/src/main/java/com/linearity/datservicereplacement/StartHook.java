package com.linearity.datservicereplacement;

import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.EMPTY_PACKAGE_INFO;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.IThinkShouldFilterApplication;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.hookGetPackageInfo;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.isSystemApp;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.nonSysPackages;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.nonSysPackagesByName;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.pm;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.sysPackages;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.sysPackagesByName;
import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_STRING_ARRAY;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.LoggerUtils.showObjectFields;
import static com.linearity.utils.ReturnReplacements.returnNull;
import static com.linearity.utils.ReturnReplacements.returnTrue;
import static com.linearity.datservicereplacement.ReturnIfNonSys.*;

import android.content.AttributionSource;
import android.content.ContentProvider;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.Bundle;

import com.linearity.datservicereplacement.Battery.HookIBatteryStats;
import com.linearity.datservicereplacement.Bluetooth.HookBluetooth;
import com.linearity.datservicereplacement.Clipboard.HookIClipboard;
import com.linearity.datservicereplacement.InputMethod.HookInputMethod;
import com.linearity.datservicereplacement.PowerManager.HookIPowerStatsService;
import com.linearity.datservicereplacement.Telecom.HookTelecomService;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.FakeInfo.ExpectInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import com.topjohnwu.superuser.Shell;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
public class StartHook implements IXposedHookLoadPackage {
    public static final Set<String> WHITELIST_PACKAGE_NAMES = new HashSet<>();
    public static final Map<Integer,Map<String,Object>> settingsForUid = new HashMap<>();

    /**
     * Don't forget to add packages u want to tell real info!
     */
    static {
        WHITELIST_PACKAGE_NAMES.add("com.github.kr328.clash");
        WHITELIST_PACKAGE_NAMES.add("com.v2ray.ang");
        WHITELIST_PACKAGE_NAMES.add("com.android.launcher3");
        WHITELIST_PACKAGE_NAMES.add("com.google.android.googlequicksearchbox");
        WHITELIST_PACKAGE_NAMES.add("com.google.android.inputmethod.latin");
    }
    public static Set<Object> IPackageManagers = new HashSet<>();
    public static final long publicSeed = System.currentTimeMillis();
    private static final Set<String> logSet = new HashSet<>();
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {

        if (!(Objects.equals(lpparam.processName,"android")) && !(Objects.equals(lpparam.processName,"system")) && lpparam.processName != null){mSystemReady = true;}

        Class<?> hookClass;
        WatchedArrayMapClass = XposedHelpers.findClassIfExists("com.android.server.utils.WatchedArrayMap",lpparam.classLoader);
        if (WatchedArrayMapClass != null){
            EMPTY_WATCHED_ARRAYMAP = XposedHelpers.newInstance(WatchedArrayMapClass);
        }
        hookClass = XposedHelpers.findClassIfExists("android.os.ServiceManager",lpparam.classLoader);

        if (hookClass != null){
            XposedBridge.hookAllMethods(hookClass, "addService", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (param.args[0]==null){return;}
                    if (param.args[0].equals("package")){
                        hookGetPackageInfo(param.args[1].getClass());
                        if (pm == null){
                            pm = param.thisObject;
                        }
                        IPackageManagers.add(param.args[1]);
                        return;
                    }
                    else if (param.args[0].equals("vibrator_manager")){
                        hookVibratorManager(param.args[1]);
                    }
                    else if(param.args[0].equals(Context.ACTIVITY_SERVICE)){//the real threat
                        hookAMS(param.args[1].getClass());
                    }
//                    else if(param.args[0].equals("bluetooth_manager")){
//                        hookBluetoothManager(param.args[1]);
//                    }
//                    else if (param.args[0].equals("tethering")){
////                        LoggerLog(param.args[0].getClass().getMethods());
////                        final Set<String> filterSet = new HashSet<>();
////                        filterSet.add("charAt");
////                        filterSet.add("equals");
////                        filterSet.add("fastSubstring");
////                        filterSet.add("toString");
////                        filterSet.add("indexOf");
////                        filterSet.add("contains");
////                        filterSet.add("doReplace");
////                        for (Method m:param.args[0].getClass().getDeclaredMethods()){
////                            if (filterSet.contains(m.getName())){continue;}
////                            XposedBridge.hookMethod(m, new XC_MethodHook() {
////                                @Override
////                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
////                                    super.beforeHookedMethod(param);
////                                    LoggerLog("called method:" + param.method.getName());
////                                }
////                            });
////                        }
//                    }
                    LoggerLog("[linearity-addservice]",param.args[0] + "|" + param.args[1]);
                }

            });
        }

        hookClass = XposedHelpers.findClassIfExists("com.android.server.pm.AppsFilter",lpparam.classLoader);
        if (hookClass != null){
            XC_MethodHook before = new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (IThinkShouldFilterApplication((int) param.args[0],param.args[1],param.args[2],(int) param.args[3])){param.setResult(true);}
                }
            };
            XposedBridge.hookAllMethods(hookClass, "shouldFilterApplication", before);
            XposedBridge.hookAllMethods(hookClass, "shouldFilterApplicationInternal", before);
        }

        hookClass = XposedHelpers.findClassIfExists("com.android.providers.settings.SettingsProvider",lpparam.classLoader);
        if (hookClass != null){
            XposedBridge.hookAllMethods(hookClass,"call",new XC_MethodHook(){
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    ContentProvider provider = (ContentProvider) param.thisObject;
                    String callingPackageName = provider.getCallingPackage();
                    if (callingPackageName == null){return;}
                    if (Objects.equals(callingPackageName, "android")){return;}
                    AttributionSource attrSource = provider.getCallingAttributionSource();
                    if (attrSource == null){return;}
                    int callingUID = attrSource.getUid();
                    if (callingUID == 1000 || callingUID == 1001){
                        return;
                    }
                    if (isSystemApp(callingUID)){
                        return;
                    }
                    long randomSeed = callingUID;
                    if (nonSysPackages.containsKey(callingUID)){
                        randomSeed += callingPackageName.hashCode();
                    }
                    if (!ExpectInfo.INSTANCES.containsKey(randomSeed)){
                        ExpectInfo.INSTANCES.put(randomSeed,new ExpectInfo(new ExtendedRandom(randomSeed)));
                    }
                    Bundle result = (Bundle) param.getResult();
                    if (result == null){return;}

                    String askingFor = (String)param.args[1];
                    if (askingFor == null){return;}
                    Map<String,Object> kvMap = ExpectInfo.INSTANCES.get(randomSeed).kvMap;

                    if (kvMap.containsKey(askingFor)){
                        Object get = kvMap.get(askingFor);
                        if (get == null){
                            result.putString("value",null);return;
                        }
                        if (get instanceof Bundle){
                            result.putAll((Bundle) get);
                        }
                        else if (get.getClass().equals(int.class) || get instanceof Integer){
                            result.putInt("value", (Integer) get);
                        }
                        else if (get.getClass().equals(long.class) || get instanceof Long){
                            result.putLong("value", (Long) get);
                        }
                        else if (get instanceof String){
                            result.putString("value",(String) get);
                        }
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(callingPackageName).append(" calling ").append(param.args[0]).append(" | ").append(param.args[1]);
                    sb.append("\n--------------------\n");
                    for (String key:result.keySet()){
                        if (result.get(key) != null){
                            sb.append(key).append(" : ").append(result.get(key)).append("[").append(result.get(key).getClass()).append("]\n");
                        }else {
                            sb.append(key).append(" : ").append(result.get(key)).append("[null]\n");
                        }
                    }
                    sb.append("====================");
                    LoggerLog(sb.toString());

//                    LoggerLog(callingPackageName + " calling " + param.args[0] + " | " + param.args[1]);
//                    LoggerLog("--------------------");
//                    if (!logSet.contains(askingFor)){
//                        logSet.add(askingFor);
//                        if (kvMap.containsKey(askingFor)){
//                            return;
//                        }
//                        if (result.get("value") == null){
//                            LoggerLog("kvMap.put(\""+askingFor+"\",null);");
//                        }else if (result.get("value").getClass().equals(String.class)){
//                            LoggerLog("kvMap.put(\""+askingFor+"\",\""+result.get("value")+"\");");
//                        }else if (result.get("value") instanceof Integer){
//                            LoggerLog("kvMap.put(\""+askingFor+"\","+result.get("value")+");");
//                        }else if (result.get("value") instanceof Long){
//                            LoggerLog("kvMap.put(\""+askingFor+"\","+result.get("value")+"L);");
//                        }else if (result.get("value") instanceof Double){
//                            LoggerLog("kvMap.put(\""+askingFor+"\","+result.get("value")+");");
//                        }else if (result.get("value") instanceof Float){
//                            LoggerLog("kvMap.put(\""+askingFor+"\","+result.get("value")+"f);");
//                        }else {
//                            StringBuilder sb = new StringBuilder();
//                            sb.append(callingPackageName).append(" calling ").append(param.args[0]).append(" | ").append(param.args[1]);
//                            sb.append("\n--------------------\n");
//                            for (String key:result.keySet()){
//                                if (result.get(key) != null){
//                                    sb.append(key).append(" : ").append(result.get(key)).append("[").append(result.get(key).getClass()).append("]\n");
//                                }else {
//                                    sb.append(key).append(" : ").append(result.get(key)).append("[null]\n");
//                                }
//                            }
//                            sb.append("====================");
//                            LoggerLog(sb.toString());
//                        }
//                    }
                }
            });
        }

        if (false){
            hookClass = XposedHelpers.findClassIfExists("android.os.Binder", lpparam.classLoader);
            if (hookClass != null) {
                XposedBridge.hookAllMethods(hookClass, "execTransact", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (!isSystemApp(Binder.getCallingUid())) {
                            String packageName = "";
                            if (param.thisObject.toString().startsWith("com.android.server.BluetoothManagerService")) {
                                param.setResult(true);
                                return;
                            }

                            if (nonSysPackages.get(Binder.getCallingUid()) != null) {
                                packageName = nonSysPackages.get(Binder.getCallingUid()).packageName;
                            }
                            LoggerLog(new Exception(param.thisObject.toString() + " " + param.args[0] + " " + param.args[1] + " " + param.args[2] + " " + param.args[3] + " " + packageName));
                        }
                    }
                });
            }
        }
        {
            hookClass = XposedHelpers.findClassIfExists("com.android.server.NetworkManagementService",lpparam.classLoader);
            if (hookClass != null){
                XposedBridge.hookAllMethods(hookClass,"registerObserver",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"unregisterObserver",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"listInterfaces",emptyStrArrIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getInterfaceConfig",fakeInterfaceConfigurationIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setInterfaceConfig",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"clearInterfaceAddresses",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setInterfaceDown",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setInterfaceUp",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setInterfaceIpv6PrivacyExtensions",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"disableIpv6",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"enableIpv6",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setIPv6AddrGenMode",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"shutdown",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getIpForwardingEnabled",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setIpForwardingEnabled",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"startTethering",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"stopTethering",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isTetheringStarted",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"tetherInterface",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"untetherInterface",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"listTetheredInterfaces",emptyStrArrIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"enableNat",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"disableNat",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setInterfaceQuota",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"removeInterfaceQuota",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setInterfaceAlert",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"removeInterfaceAlert",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setUidOnMeteredNetworkDenylist",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setUidOnMeteredNetworkAllowlist",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setDataSaverModeEnabled",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setUidCleartextNetworkPolicy",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isBandwidthControlEnabled",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setFirewallEnabled",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isFirewallEnabled",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setFirewallUidRule",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setFirewallUidRules",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"setFirewallChainEnabled",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"allowProtect",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"denyProtect",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isNetworkRestricted",trueIfNonSys);
            }
        }
        {
            hookClass = XposedHelpers.findClassIfExists("com.android.server.ConnectivityService", lpparam.classLoader);
            if (hookClass != null) {
                XposedBridge.hookAllMethods(hookClass, "declareNetworkRequestUnfulfillable", nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "factoryReset", nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getActiveLinkProperties",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getActiveNetwork", fakeNetworkIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getActiveNetworkForUid", fakeNetworkIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getActiveNetworkInfo", fakeNetworkInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getActiveNetworkInfoForUid", fakeNetworkInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getAllNetworkInfo", fakeNetworkInfoArrIfNonSys);
//            XposedBridge.hookAllMethods(hookClass,"getAllNetworkState",NetworkState[]);
//            XposedBridge.hookAllMethods(hookClass,"getAllNetworkStateSnapshots",List);
                XposedBridge.hookAllMethods(hookClass, "getAllNetworks", fakeNetworkArrIfNonSys);
//            XposedBridge.hookAllMethods(hookClass,"getCaptivePortalServerUrl",String);
                XposedBridge.hookAllMethods(hookClass, "getConnectionOwnerUid", oneIfNonSys);
//            XposedBridge.hookAllMethods(hookClass,"getDefaultNetworkCapabilitiesForUser",NetworkCapabilities[]);
//            XposedBridge.hookAllMethods(hookClass,"getNetworkCapabilities",NetworkCapabilities);
//            XposedBridge.hookAllMethods(hookClass,"getDefaultRequest",NetworkRequest);

                XposedBridge.hookAllMethods(hookClass,"getGlobalProxy",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getLastTetherError", oneIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getLinkProperties",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getLinkPropertiesForType",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getMultipathPreference", oneIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getNetworkForType", fakeNetworkIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getNetworkInfo", fakeNetworkInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getNetworkInfoForUid", fakeNetworkInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getNetworkWatchlistConfigHash", nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getProxyForNetwork", nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getRestoreDefaultNetworkDelay", oneIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getRestrictBackgroundStatusByCaller", oneIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getTetherableIfaces", emptyStrArrIfNonSys);
            }
        }
        if (false){
            hookClass = XposedHelpers.findClassIfExists("com.android.server.pm.PackageManagerService$ComputerEngine",lpparam.classLoader);
            if (hookClass != null){
                XposedBridge.hookAllMethods(hookClass,"getVersion",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getUsed",zeroIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"getActivityInfo",ActivityInfo);
//                XposedBridge.hookAllMethods(hookClass,"getActivityInfoCrossProfile",ActivityInfo);
//                XposedBridge.hookAllMethods(hookClass,"getActivityInfoInternal",ActivityInfo);
                XposedBridge.hookAllMethods(hookClass,"getPackage",new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        int callingUID = Binder.getCallingUid();
                        if (!isSystemApp(callingUID)){
                            int askingUID = (int)param.args[0];
                            if (askingUID != callingUID){
                                param.setResult(null);
                            }
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass,"generateApplicationInfoFromSettings",getConfusedApplicationInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getApplicationInfo",getConfusedApplicationInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getApplicationInfoInternal",getConfusedApplicationInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getDefaultHomeActivity",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getHomeActivitiesAsUser",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getCrossProfileDomainPreferredLpr",nullIfNonSys);
////                XposedBridge.hookAllMethods(hookClass,"getHomeIntent",Intent);
                XposedBridge.hookAllMethods(hookClass,"getMatchingCrossProfileIntentFilters",emptyArrListIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"applyPostResolutionFilter",emptyArrListIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"generatePackageInfo",getConfusedPackageInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getPackageInfo",getConfusedPackageInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getPackageInfoInternal",getConfusedPackageInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getAllAvailablePackageNames",new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        int callingUID = Binder.getCallingUid();
                        if (!isSystemApp(callingUID)){
                            if (nonSysPackages.containsKey(callingUID) && nonSysPackages.get(callingUID) != null){
                                if (nonSysPackages.get(callingUID).packageName != null){
                                    param.setResult(new String[]{nonSysPackages.get(callingUID).packageName});
                                    return;
                                }
                            }
                            param.setResult(EMPTY_STRING_ARRAY);
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass,"getPackageStateInternal",new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        int callingUID = Binder.getCallingUid();
                        if (!isSystemApp(callingUID)){
                            if (nonSysPackages.containsKey(callingUID) && nonSysPackages.get(callingUID) != null){
                                if (!Objects.equals(nonSysPackages.get(callingUID).packageName,param.args[0])){
                                    param.setResult(null);
                                }
                            }
                        }
                    }
                });
//                XposedBridge.hookAllMethods(hookClass,"getPackageStateFiltered",PackageStateInternal);
                XposedBridge.hookAllMethods(hookClass,"getInstalledPackages",emptyParceledListSliceIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"createForwardingResolveInfoUnchecked",ResolveInfo);
                XposedBridge.hookAllMethods(hookClass,"getServiceInfo",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getSharedLibraryInfo",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getInstantAppPackageName",new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        int callingUID = Binder.getCallingUid();
                        if (!isSystemApp(callingUID)){
                            int askingUID = (int)param.args[0];
                            if (askingUID != callingUID){
                                param.setResult(null);
                            }
                        }
                    }
                });
//                XposedBridge.hookAllMethods(hookClass,"resolveExternalPackageName",String);
                XposedBridge.hookAllMethods(hookClass, "resolveInternalPackageName", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        int callingUID = Binder.getCallingUid();
                        if (!isSystemApp(callingUID)){
                            param.setResult(param.args[0]);
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass, "getPackagesForUid", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        int callingUID = Binder.getCallingUid();
                        if (!isSystemApp(callingUID)){
                            int askingUID = (int)param.args[0];
                            if (askingUID != callingUID){
                                param.setResult(null);
                            }
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass,"getProfileParent",fakeUserInfoIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"canViewInstantApps",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"filterSharedLibPackage",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isCallerSameApp",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isCallerSameApp",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isComponentVisibleToInstantApp",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isComponentVisibleToInstantApp",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isImplicitImageCaptureIntentAndNotSetByDpc",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isInstantApp",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isInstantAppInternal",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isSameProfileGroup",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"shouldFilterApplication",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"shouldFilterApplication",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"shouldFilterApplication",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"shouldFilterApplication",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"shouldFilterApplicationIncludingUninstalled",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"shouldFilterApplicationIncludingUninstalledNotArchived",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"shouldFilterApplicationIncludingUninstalled",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"checkUidPermission",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getPackageUidInternal",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"updateFlagsForApplication",longZeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"updateFlagsForComponent",longZeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"updateFlagsForPackage",longZeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getSigningDetails",new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        int calling = Binder.getCallingUid();
                        if (!isSystemApp(calling)){
                            int findingUid = (int) param.args[0];
                            if (findingUid != calling){
                                param.args[0] = -1;
                            }
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass,"filterAppAccess",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"dump",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"findPreferredActivityInternal",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"findPersistentPreferredActivity",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getPreferredActivities", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (!isSystemApp(Binder.getCallingUid())){
                            if (((int)param.args[0]) != 0){
                                param.setResult(null);
                            }
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass,"getPackageStates",newArrMapIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getDisabledSystemPackageStates",newArrMapIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getRenamedPackage",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getNotifyPackagesForReplacedReceived",emptyArrSetIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getPackageStartability",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isPackageAvailable",falseIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isApexPackage",falseIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"currentToCanonicalPackageNames",String[]);
//                XposedBridge.hookAllMethods(hookClass,"canonicalToCurrentPackageNames",String[]);
                XposedBridge.hookAllMethods(hookClass,"getPackageGids",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getTargetSdkVersion",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"activitySupportsIntentAsUser",falseIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"getReceiverInfo",ActivityInfo);
                XposedBridge.hookAllMethods(hookClass,"getSharedLibraries",emptyParceledListSliceIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"canRequestPackageInstalls",falseIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isInstallDisabledForPackage",falseIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getPackagesUsingSharedLibrary",emptyListListPairIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getDeclaredSharedLibraries",emptyParceledListSliceIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getProviderInfo",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getSystemSharedLibraryNamesAndPaths",newArrMapIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"getPackageStateForInstalledAndFiltered",PackageStateInternal);

//                XposedBridge.hookAllMethods(hookClass,"checkSignatures",new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//
//                        int callingUID = Binder.getCallingUid();
//                        if (!isSystemApp(callingUID)){
//                            if (nonSysPackages.containsKey(callingUID) && nonSysPackages.get(callingUID) != null){
//                                if (!Objects.equals(nonSysPackages.get(callingUID).packageName,param.args[0])){
//                                    param.setResult(false);
//                                }
//                            }
//                        }
//                    }
//                });
//                XposedBridge.hookAllMethods(hookClass,"checkUidSignatures",zeroIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"checkUidSignaturesForAllUsers",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"hasSigningCertificate",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"hasUidSigningCertificate",trueIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"getAllPackages",List);
//                XposedBridge.hookAllMethods(hookClass,"getNameForUid",String);
//                XposedBridge.hookAllMethods(hookClass,"getNamesForUids",String[]);
                XposedBridge.hookAllMethods(hookClass,"getUidForSharedUser",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getFlagsForUid",new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                        int callingUID = Binder.getCallingUid();
                        if (!isSystemApp(callingUID)){
                            if (!Objects.equals(callingUID,param.args[0])){
                                param.setResult(0);
                            }
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass,"getPrivateFlagsForUid",new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                        int callingUID = Binder.getCallingUid();
                        if (!isSystemApp(callingUID)){
                            if (!Objects.equals(callingUID,param.args[0])){
                                param.setResult(0);
                            }
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass, "isUidPrivileged", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                        int callingUID = Binder.getCallingUid();
                        if (!isSystemApp(callingUID)){
                            if (nonSysPackages.containsKey(callingUID) && nonSysPackages.get(callingUID) != null){
                                if (!Objects.equals(nonSysPackages.get(callingUID).packageName,param.args[0])){
                                    param.setResult(false);
                                }
                            }
                        }
                    }
                });
//                XposedBridge.hookAllMethods(hookClass,"getAppOpPermissionPackages",String[]);
                XposedBridge.hookAllMethods(hookClass,"getPackagesHoldingPermissions",emptyParceledListSliceIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"getInstalledApplications",List);
//                XposedBridge.hookAllMethods(hookClass,"resolveContentProvider",ProviderInfo);
//                XposedBridge.hookAllMethods(hookClass,"getGrantImplicitAccessProviderInfo",ProviderInfo);
                XposedBridge.hookAllMethods(hookClass,"querySyncProviders",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"queryContentProviders",emptyParceledListSliceIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"getInstrumentationInfoAsUser",InstrumentationInfo);
                XposedBridge.hookAllMethods(hookClass,"queryInstrumentationAsUser",emptyParceledListSliceIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"findSharedNonSystemLibraries",emptyArrListIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getApplicationHiddenSettingAsUser",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isPackageSuspendedForUser",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isPackageQuarantinedForUser",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isPackageStoppedForUser",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isSuspendingAnyPackages",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getAllIntentFilters",emptyParceledListSliceIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getBlockUninstallForUser",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getInstallerPackageName",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getInstallSourceInfo",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getApplicationEnabledSetting",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getComponentEnabledSetting",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getComponentEnabledSettingInternal",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isComponentEffectivelyEnabled",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isApplicationEffectivelyEnabled",trueIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"getKeySetByAlias",KeySet);
//                XposedBridge.hookAllMethods(hookClass,"getSigningKeySet",KeySet);
                XposedBridge.hookAllMethods(hookClass,"isPackageSignedByKeySet",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isPackageSignedByKeySetExactly",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getVisibilityAllowLists",emptySparseArrayIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getVisibilityAllowList",emptyIntArrIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"canQueryPackage",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getPackageUid",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"canAccessComponent",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"isCallerInstallerOfRecord",trueIfNonSys);
//                XposedBridge.hookAllMethods(hookClass,"getInstallReason",zeroIfNonSys);
//                XposedBridge.hookAllMethods(hookClass, "canPackageQuery", new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        int callingUid = Binder.getCallingUid();
//                        if (!isSystemApp(callingUid)){
//                            String[] targetPackageNames = (String[]) param.args[1];
//                            int targetSize = targetPackageNames.length;
//                            boolean[] result = new boolean[targetSize];
//                            param.setResult(result);
//                        }
//                    }
//                });
//                XposedBridge.hookAllMethods(hookClass,"canForwardTo",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getPersistentApplications",emptyArrListIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getAppsWithSharedUserIds",emptySparseArrayIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getSharedUserPackagesForPackage",emptyStrArrIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getUnusedPackages",emptyArrSetIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getHarmfulAppWarning",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"filterOnlySystemPackages",emptyStrArrIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getPackagesForAppId",new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        int calling = Binder.getCallingUid();
                        if (!isSystemApp(calling)){
                            int findingUid = (int) param.args[0];
                            if (findingUid != calling){
                                param.args[0] = -1;
                            }
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass,"getUidTargetSdkVersion",zeroIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getProcessesForUid",newArrMapIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getBlockUninstall",trueIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getSharedLibraries",emptyWatchedArrMapIfNonSys);
                XposedBridge.hookAllMethods(hookClass, "getPackageOrSharedUser", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        int calling = Binder.getCallingUid();
                        if (!isSystemApp(calling)){
                            int findingUid = (int) param.args[0];
                            if (findingUid != calling){
                                param.args[0] = -1;
                            }
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass,"getSharedUser",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getSharedUserPackages",emptyArrSetIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getComponentResolver",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getDisabledSystemPackage",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getInstantAppInstallerInfo",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getFrozenPackages",emptyWatchedArrMapIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"checkPackageFrozen",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getInstantAppInstallerComponent",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"dumpPermissions",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"dumpPackages",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"dumpKeySet",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"dumpSharedUsers",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"dumpSharedUsersProto",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"dumpPackagesProto",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"dumpSharedLibrariesProto",nullIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getVolumePackages",emptyArrListIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getUserInfos",fakeUserInfoArrIfNonSys);
                XposedBridge.hookAllMethods(hookClass,"getSharedUsers",emptyArrMapIfNonSys);
            }

            hookClass = XposedHelpers.findClassIfExists("com.android.server.pm.PackageManagerService",lpparam.classLoader);
            if (hookClass != null){
                Class<?> dexManagerClass = XposedHelpers.findClassIfExists("com.android.server.pm.dex.DexManager",lpparam.classLoader);
                XposedBridge.hookAllMethods(dexManagerClass,"load", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Map<Integer,List<PackageInfo>> allPackageInfo = (Map<Integer, List<PackageInfo>>) param.args[0];
                        if (allPackageInfo == null){return;}
                        //just add all packages to our registry
                        for (List<PackageInfo> packageInfoList:allPackageInfo.values()){
                            for (PackageInfo p:packageInfoList){
                                if (p.applicationInfo != null){
                                    if ((boolean) XposedHelpers.callMethod(p.applicationInfo,"isSystemApp")){
                                        sysPackages.put(p.applicationInfo.uid,EMPTY_PACKAGE_INFO);
                                        if (p.packageName != null){
                                            sysPackagesByName.put(p.packageName,EMPTY_PACKAGE_INFO);
                                        }
                                    }else {
                                        nonSysPackages.put(p.applicationInfo.uid,p);
                                        if (p.packageName != null){
                                            nonSysPackagesByName.put(p.packageName,p);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                XposedBridge.hookAllMethods(hookClass,"getAllPackages",new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        int callingUid = Binder.getCallingUid();
                        if (isSystemApp(callingUid)){return;}

                        param.setResult(filtedStrings((Collection<String>) param.getResult(),callingUid));
                    }
                });
                XposedBridge.hookAllMethods(hookClass, "getPackagesForUid", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        int callingUid = Binder.getCallingUid();
                        if (isSystemApp(callingUid)){return;}
                        if (((int)param.args[0]) == callingUid){
                            return;
                        }
                        param.setResult(filtedStrings((String[]) param.getResult(),callingUid).toArray(EMPTY_STRING_ARRAY));
                    }
                });
            }
        }//after tweaked filter,i dont think it's necessary
        HookBluetooth.doHook(lpparam);
        HookInputMethod.doHook(lpparam);
        HookTelecomService.doHook(lpparam);
        HookIClipboard.doHook(lpparam);
        HookIPowerStatsService.doHook(lpparam);
        HookIBatteryStats.doHook(lpparam);

//        MessageFinder.hookMessage(lpparam);

//        hookClass = XposedHelpers.findClassIfExists("com.android.server.SystemServiceManager",lpparam.classLoader);
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

//        hookClass = XposedHelpers.findClassIfExists("android.net.ConnectivityManager",lpparam.classLoader);
//        if (hookClass != null){
//            XposedBridge.hookAllMethods(hookClass, "systemReady", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    LoggerLog("called systemReady");
//                    if (param.getThrowable() != null){
//                        LoggerLog(param.getThrowable());
//                    }
//                }
//            });
//        }

//        hookClass = XposedHelpers.findClassIfExists("com.android.server.SystemServer",lpparam.classLoader);
//        if (hookClass != null){
//            XposedBridge.hookAllMethods(hookClass, "startSystemUi", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
////                    setPhoneProductCodeName();
//                }
//            });
//        }
//        hookClass = XposedHelpers.findClassIfExists("com.android.server.TelephonyRegistry",lpparam.classLoader);
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
        hookClass = XposedHelpers.findClassIfExists("com.android.internal.telephony.GsmCdmaPhone",lpparam.classLoader);
        if (hookClass != null){
            XC_MethodHook setField = new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    ExtendedRandom random = new ExtendedRandom(param.thisObject.hashCode());
                    XposedHelpers.setObjectField(param.thisObject,"mImei",random.nextRandomDecimal(15));
                    XposedHelpers.setObjectField(param.thisObject,"mImeiSv",random.nextRandomDecimal(16));
                    XposedHelpers.setObjectField(param.thisObject,"mMeid",random.nextRandomHexUpper(15));
                    XposedHelpers.setObjectField(param.thisObject,"mEsn",random.nextRandomHexUpper(15));
                }
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    ExtendedRandom random = new ExtendedRandom(param.thisObject.hashCode());
                    XposedHelpers.setObjectField(param.thisObject,"mImei",random.nextRandomDecimal(15));
                    XposedHelpers.setObjectField(param.thisObject,"mImeiSv",random.nextRandomDecimal(16));
                    XposedHelpers.setObjectField(param.thisObject,"mMeid",random.nextRandomHexUpper(15));
                    XposedHelpers.setObjectField(param.thisObject,"mEsn",random.nextRandomHexUpper(15));
                }
            };
            XposedBridge.hookAllMethods(hookClass, "parseImeiInfo", setField);
            XposedBridge.hookAllMethods(hookClass, "getDeviceSvn", setField);
            XposedBridge.hookAllMethods(hookClass, "getImei", setField);
            XposedBridge.hookAllMethods(hookClass, "getEsn", setField);
            XposedBridge.hookAllMethods(hookClass, "getMeid", setField);
            XposedBridge.hookAllMethods(hookClass, "getNai", returnNull);
        }
        setOtherProperties();

        hookClass = XposedHelpers.findClassIfExists("com.android.server.am.ActivityManagerService",XposedBridge.BOOTCLASSLOADER);
        if (hookClass != null){
            hookAMS(hookClass);
        }

//        hookClass = XposedHelpers.findClassIfExists("android.app.ContextImpl",lpparam.classLoader);
//        if (hookClass != null){
//            hookContextImpl(hookClass);
//        }

//        hookClass = XposedHelpers.findClassIfExists("com.android.server.am.ActiveServices",lpparam.classLoader);
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

//        hookClass = XposedHelpers.findClassIfExists("com.android.server.wm.ActivityTaskManagerService",lpparam.classLoader);
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

    public static void hookAMS(Class<?> hookClass){
        XposedBridge.hookAllMethods(hookClass, "systemReady", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                mSystemReady = true;
            }
        });

//        XposedBridge.hookAllMethods(hookClass, "startService", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                int userid = (int) param.args[6];
//                if (param.args[4] == null){return;}
//                if (!isSystemApp(param.args[4].toString(),userid)){
//                    LoggerLog("--------start service-----------");
//                    StringBuilder sb = new StringBuilder();
//                    for (Object o:param.args){
//                        if (o != null){
//                            sb.append(o.toString()).append("\n");
//                        }else {
//                            sb.append("null\n");
//                        }
//                    }
//                    LoggerLog(sb.toString());
//                }
//            }
//        });
    }
    private void hookBluetoothManager(Class<?> hookClass) {

        XposedBridge.hookAllMethods(hookClass, "onTransact", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                StringBuilder sb = new StringBuilder();
                for (Object o : param.args) {
                    if (o != null) {
                        sb.append(o.toString()).append("\n");
                    } else {
                        sb.append("null\n");
                    }
                }
                LoggerLog(sb.toString());
            }
        });

    }

    public static void hookVibratorManager(Object arg) {
        XposedBridge.hookAllMethods(arg.getClass(), "getVibratorIds", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                int calling = Binder.getCallingUid();
                int[] result = (int[]) param.getResult();
//                LoggerLog(result);
                ExtendedRandom random = new ExtendedRandom(publicSeed);
                param.setResult(random.nextIntArr(result.length + random.nextInt(5)));
            }
        });
        XposedBridge.hookAllMethods(arg.getClass(), "registerVibratorStateListener", returnTrue);
        XposedBridge.hookAllMethods(arg.getClass(), "unregisterVibratorStateListener", returnTrue);
        XposedBridge.hookAllMethods(arg.getClass(), "vibrate", returnNull);
        XposedBridge.hookAllMethods(arg.getClass(), "cancelVibrate", returnNull);
        XposedBridge.hookAllMethods(arg.getClass(), "performHapticFeedback", returnNull);
    }

    void setPhoneProductCodeName(){
        String[] strs = new String[]{
                "ro.build.product", "ro.product.bootimage.device",
                "ro.product.device", "ro.product.odm.device", "ro.product.odm_dlkm.device",
                "ro.product.product.device", "ro.product.system.device",
                "ro.product.system_ext.device",
                "ro.product.vendor.device", "ro.product.vendor_dlkm.device"
        };

        String[] sdkvers = new String[]{
                "ro.bootimage.build.version.sdk", "ro.build.version.sdk", "ro.odm.build.version.sdk",
                "ro.product.build.version.sdk", "ro.system.build.version.sdk", "ro.system_ext.build.version.sdk",
                "ro.vendor.build.version.sdk", "ro.vendor_dlkm.build.version.sdk", "ro.vndk.version",
        };
        String[] codenames = new String[]{
                "ro.bootimage.build.version.release", "ro.bootimage.build.version.release_or_codename",
                "ro.build.version.release", "ro.build.version.release_or_codename",
                "ro.odm.build.version.release", "ro.odm.build.version.release_or_codename",
                "ro.product.build.version.release", "ro.product.build.version.release_or_codename",
                "ro.system.build.version.release", "ro.system.build.version.release_or_codename",
                "ro.system_ext.build.version.release", "ro.system_ext.build.version.release_or_codename",
                "ro.vendor.build.version.release", "ro.vendor.build.version.release_or_codename",
                "ro.vendor_dlkm.build.version.release", "ro.vendor_dlkm.build.version.release_or_codename",
        };
        try {
            //maybe i should call getprop to decide the value for resetprop
            Shell.getShell().newJob().add("su").exec();
//            strs = new String[]{
//                    "ro.build.version.all_codenames","ro.build.version.codename",
//                    "ro.build.version.preview_sdk_fingerprint",
//            };
//            for (String s:strs){
//                Shell.getShell().newJob().add("resetprop -n "+s+" QAQ").exec();
//            }

            String buildProductBackup = Shell.getShell().newJob().add("getprop ro.build.product.backup").exec().getOut().get(0);
            if (buildProductBackup != null){
                Shell.getShell().newJob().add("resetprop -n --delete ro.build.product.backup").exec();
                for (String s:strs){
                    Shell.getShell().newJob().add("resetprop -n "+s+" abc").exec();
                }
            }else {
                String s2 = new ExtendedRandom(publicSeed).nextString(new ExtendedRandom(publicSeed).nextInt(5)+2);
                for (String s:strs){
                    Shell.getShell().newJob().add("resetprop -n "+s+" "+s2).exec();
                }
            }

        }catch (Exception e){
            LoggerLog(e);
        }
        LoggerLog("===========PhoneProductCodeName changed===========");
    }

    void setOtherProperties(){
        Shell.getShell().newJob().add("su").exec();
        Shell.getShell().newJob().add("resetprop -n ro.boot.verifiedbootstate green").exec();
        Shell.getShell().newJob().add("resetprop -n vendor.boot.verifiedbootstate green").exec();
        Shell.getShell().newJob().add("resetprop -n ro.secureboot.lockstate locked").exec();
        Shell.getShell().newJob().add("resetprop -n vendor.boot.vbmeta.device_state locked").exec();
        Shell.getShell().newJob().add("resetprop -n ro.boot.vbmeta.device_state locked").exec();
        Shell.getShell().newJob().add("resetprop -n ro.boot.flash.locked locked").exec();
        Shell.getShell().newJob().add("resetprop -n sys.oem_unlock_allowed 0").exec();
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
            if (s.startsWith("com.tencent") || s.startsWith("com.alipay")){
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
            if (s.startsWith("com.tencent") || s.startsWith("com.alipay")){
                resultList.add(s);
            }else if(changedSelf && self != null){
                if(self.equals(s)){
                    resultList.add(s);
                }
            }
        }
        return resultList;
    }

    public static boolean isAccessiblePackageName(int callingUID,String accessingPackageName){
        if (isSystemApp(callingUID)){return true;}
        if (nonSysPackages.containsKey(callingUID) && nonSysPackages.get(callingUID) != null){
            if (nonSysPackages.get(callingUID).packageName == null){return false;}
            if (Objects.equals(accessingPackageName,nonSysPackages.get(callingUID).packageName)){
                return true;
            }
            if (nonSysPackages.get(callingUID).packageName.startsWith("com.tencent") || nonSysPackages.get(callingUID).packageName.startsWith("com.alipay")){
                return true;
            }
        }
        return false;
    }
}

