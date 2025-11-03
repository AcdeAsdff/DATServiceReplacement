package com.linearity.datservicereplacement;

import static com.linearity.datservicereplacement.ReturnIfNonSys.SyntheticNameResolver.resolveMethodName;
import static com.linearity.datservicereplacement.StartHook.WHITELIST_PACKAGE_NAMES;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.getPackageName;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.nonSysPackages;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.tryGetPM;
import static com.linearity.utils.PublicSeed.publicSeed;
import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_INT_ARRAY;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.*;

import static java.lang.System.identityHashCode;

import android.app.IApplicationThread;
import android.bluetooth.BluetoothAdapter;
import android.content.AttributionSource;
import android.content.ContentProvider;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.net.ConnectivityManager;
import android.net.InetAddresses;
import android.net.LinkAddress;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.WorkSource;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.SparseArray;

import com.linearity.utils.AndroidFakes.ActivityManagerService.ProcessRecordUtils;
import com.linearity.utils.AndroidFakes.Connectivity.NetworkConstructUtils;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.ObjectsToString;
import com.linearity.utils.ProcessListUtils;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;
import com.linearity.utils.SystemAppChecker;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.ParametersAreNonnullByDefault;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class ReturnIfNonSys {
    public static final String CONSTRUCTOR_METHOD_STRING = "<init>";
    public static boolean mSystemReady = false;
    public static final Map<Pair<SimpleExecutorWithMode,SystemAppChecker>,XC_MethodHook> SimpleExecutor_Map = new HashMap<>();
    public static XC_MethodHook simpleExecutorWithoutAttrSource(SimpleExecutorWithMode simpleExecutorWithMode){
        return simpleExecutorWithoutAttrSource(simpleExecutorWithMode,defaultSystemChecker);
    }
    public static XC_MethodHook simpleExecutorWithoutAttrSource(SimpleExecutorWithMode simpleExecutorWithMode,SystemAppChecker systemAppChecker){
        {
            Pair<SimpleExecutorWithMode, SystemAppChecker> key = new Pair<>(simpleExecutorWithMode,systemAppChecker);
            XC_MethodHook ret = SimpleExecutor_Map.getOrDefault(key,null);
            if (ret == null) {
                ret = switchForSimpleExecutor(simpleExecutorWithMode, systemAppChecker);
                SimpleExecutor_Map.put(key, ret);
            }
            return ret;
        }
    }

    public static final SystemAppChecker defaultSystemChecker = param -> {

        tryGetPM(param.thisObject);
        return isSystemApp(Binder.getCallingUid());
    };
    public static final SystemAppChecker defaultSystemCheckerReversed = param -> {

        tryGetPM(param.thisObject);
        return !isSystemApp(Binder.getCallingUid());
    };
    public static final SystemAppChecker noSystemChecker = param -> false;

    public static SystemAppChecker contentProviderSystemChecker = param -> {
        ContentProvider provider = (ContentProvider) param.thisObject;
        String callingPackageName = provider.getCallingPackage();
        return isSystemApp(callingPackageName) && isSystemApp(Binder.getCallingUid());
    };
    public static final SystemAppChecker findStrInArgs = param -> isSystemApp(Binder.getCallingUid()) && isSystemApp(findArgByClassInArgs(param.args,String.class));
    public static final SystemAppChecker findStrAndUidInArgs = param -> {
        Integer uid = findArgByClassInArgs(param.args,int.class);
        if (uid == null){
            return isSystemApp(Binder.getCallingUid())
                    && isSystemApp(
                    findArgByClassInArgs(param.args,String.class));
        }
        return isSystemApp(Binder.getCallingUid())
                && isSystemApp(
                findArgByClassInArgs(param.args,String.class), uid);
    };

    public static final HashMap<Integer,SystemAppChecker> getSystemChecker_UidAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_UidAt(int index){
        if (getSystemChecker_UidAt_map.containsKey(index)){
            return getSystemChecker_UidAt_map.get(index);
        }
        SystemAppChecker ret = param -> {

            int tempIndex = index;
            if (tempIndex < 0){
                tempIndex += param.args.length;
            }
            return isSystemApp(Binder.getCallingUid())
                    && isSystemApp((int) param.args[tempIndex]);
        };
        getSystemChecker_UidAt_map.put(index,ret);
        return ret;
    }
    public static final HashMap<Integer,SystemAppChecker> getSystemChecker_PackageNameAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_PackageNameAt(int index){
//        LoggerLog(new Exception(String.valueOf(index)));
        if (getSystemChecker_PackageNameAt_map.containsKey(index)){
            return getSystemChecker_PackageNameAt_map.get(index);
        }
        SystemAppChecker ret = param -> {
            int tempIndex = index;
            if (tempIndex < 0){
                tempIndex += param.args.length;
            }
            try{
                String packageName = (String) param.args[tempIndex];
                return isSystemApp(Binder.getCallingUid())
                        && isSystemApp(packageName);
            }
            catch (ClassCastException ignore){
                return true;
            }
            catch (Throwable e){
                tryGetPM(param.thisObject);
                if (!isSystemApp(Binder.getCallingUid())){
                    return false;
                }
                LoggerLog("getPackageName Failed:"
                        + "\n" + Arrays.deepToString(param.args)
                        + "\n" + param.method
                        + "\n" + param.thisObject
                        + "\n" + Binder.getCallingUid()
                        + "\n" + getPackageName(Binder.getCallingUid())
                );
                LoggerLog(e);
                return true;
            }
        };

        getSystemChecker_PackageNameAt_map.put(index,ret);
        return ret;
    }
    public static final HashMap<Integer,SystemAppChecker> getSystemChecker_PackageNameMayAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_PackageNameMayAt(int index){
//        LoggerLog(new Exception(String.valueOf(index)));
        if (getSystemChecker_PackageNameMayAt_map.containsKey(index)){
            return getSystemChecker_PackageNameMayAt_map.get(index);
        }
        SystemAppChecker ret = param -> {
            int tempIndex = index;
            if (tempIndex < 0){
                tempIndex += param.args.length;
            }
            try{
                return isSystemApp(Binder.getCallingUid())
                        && isSystemApp((String) param.args[tempIndex]);
            }catch (Throwable e){
                tryGetPM(param.thisObject);
                if (!isSystemApp(Binder.getCallingUid())){
                    return false;
                }
                LoggerLog("getPackageName Failed:"
                        + "\n" + Arrays.deepToString(param.args)
                        + "\n" + param.method
                        + "\n" + param.thisObject
                        + "\n" + Binder.getCallingUid()
                        + "\n" + getPackageName(Binder.getCallingUid())
                );
                LoggerLog(e);
                return true;
            }
        };

        getSystemChecker_PackageNameMayAt_map.put(index,ret);
        return ret;
    }


    private static XC_MethodHook switchForSimpleExecutor(SimpleExecutorWithMode simpleExecutorWithMode){
        return switchForSimpleExecutor(simpleExecutorWithMode,defaultSystemChecker);
    }
    private static XC_MethodHook switchForSimpleExecutor(SimpleExecutorWithMode simpleExecutorWithMode,SystemAppChecker systemAppChecker){
        return switch (simpleExecutorWithMode.mode) {
            case MODE_BEFORE -> new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if (systemAppChecker.checkSystemApp(param)) {
                        return;
                    }
                    try {
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    } catch (Throwable e) {
                        LoggerLog(e);
                    }
                }
            };
            case MODE_AFTER -> new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    if (systemAppChecker.checkSystemApp(param)) {
                        return;
                    }
                    try {
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    } catch (Throwable e) {
                        LoggerLog(e);
                    }
                }
            };
            case MODE_BEFORE_AND_AFTER -> new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if (systemAppChecker.checkSystemApp(param)) {
                        return;
                    }
                    try {
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    } catch (Throwable e) {
                        LoggerLog(e);
                    }
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    if (systemAppChecker.checkSystemApp(param)) {
                        return;
                    }//fixed clipboard not working
                    try {
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    } catch (Throwable e) {
                        LoggerLog(e);
                    }
                }
            };
            case MODE_BEFORE_NO_CHECK -> new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    try {
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    } catch (Throwable e) {
                        LoggerLog(e);
                    }
                }
            };
            case MODE_AFTER_NO_CHECK -> new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    try {
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    } catch (Throwable e) {
                        LoggerLog(e);
                    }
                }
            };
            case MODE_BEFORE_AND_AFTER_NO_CHECK -> new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    try {
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    } catch (Throwable e) {
                        LoggerLog(e);
                    }
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    try {
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    } catch (Throwable e) {
                        LoggerLog(e);
                    }
                }
            };
            default -> new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (systemAppChecker.checkSystemApp(param)) {
                        return;
                    }//fixed clipboard not working
                    try {
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    } catch (Throwable e) {
                        LoggerLog(e);
                    }
                }
            };
        };
    }
    public static final Map<long[],SimpleExecutor> pickFromArray_SimpleExecutor_map = new HashMap<>();
    public static SimpleExecutor pickFromArray_SimpleExecutor(long[] returnIfNonSysArr){
        if (pickFromArray_SimpleExecutor_map.containsKey(returnIfNonSysArr)){
            return pickFromArray_SimpleExecutor_map.get(returnIfNonSysArr);
        }
        return param -> {
            ExtendedRandom extendedRandom = new ExtendedRandom(Arrays.hashCode(returnIfNonSysArr)^publicSeed + Binder.getCallingUid()*publicSeed);
            param.setResult(extendedRandom.pickFromArray(returnIfNonSysArr));
        };
    }

    static {
        NetworkConstructUtils.FAKE_NETWORK_INFO_INSTANCE.setDetailedState(NetworkInfo.DetailedState.CONNECTED,null,null);
        XposedHelpers.setObjectField(NetworkConstructUtils.FAKE_NETWORK_INFO_INSTANCE,"mState", NetworkInfo.State.CONNECTED);
    }

    public static Network constructNetwork(int netId,boolean mPrivateDnsBypass){
        try {
//            return new Network(netId,mPrivateDnsBypass);
            return NetworkConstructUtils.NETWORK_CONSTRUCTOR.newInstance(netId,mPrivateDnsBypass);
        }catch (Throwable e){
            LoggerLog(e);
            return null;
        }
    }
    public static Network constructNetworkByUID(int callingUID){
        ExtendedRandom extendedRandom = new ExtendedRandom(publicSeed ^ (((long) callingUID) * Network.class.getCanonicalName().hashCode()));
        return constructNetwork(extendedRandom.nextInt(Integer.MAX_VALUE/5),false);
    }


    public static NetworkInfo newFakeNetworkInfoInstance(){
        return new NetworkInfo(ConnectivityManager.TYPE_MOBILE, TelephonyManager.NETWORK_TYPE_LTE,"mobile","mobile_ims");
    }

    public static final Class<?> InterfaceConfigurationClass = XposedHelpers.findClass("android.net.InterfaceConfiguration", XposedBridge.BOOTCLASSLOADER);
    public static final XC_MethodHook fakeInterfaceConfigurationIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                long additional = 0;
                for (Object o:param.args){
                    additional += o.hashCode();
                }
                ExtendedRandom extendedRandom = new ExtendedRandom(calling + additional);
                extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + calling * 4L + additional);
                //android.net.InterfaceConfiguration
                Object fakeInterfaceConfiguration = XposedHelpers.newInstance(InterfaceConfigurationClass);
                XposedHelpers.callMethod(fakeInterfaceConfiguration,"setHardwareAddress",extendedRandom.nextMacAddrStr());
                extendedRandom.nextIPV4();
                Object fakeInetAddr = InetAddresses.parseNumericAddress(extendedRandom.nextIPV4());
                Object fakeLinkAddr = XposedHelpers.newInstance(LinkAddress.class,fakeInetAddr,0);
                XposedHelpers.callMethod(fakeInterfaceConfiguration,"setLinkAddress",fakeLinkAddr);
                XposedHelpers.callMethod(fakeInterfaceConfiguration,"setFlag",extendedRandom.nextBoolean()?"up":"down");
                param.setResult(fakeInterfaceConfiguration);
            }
        }
    };
    public static SimpleExecutor fakeInterfaceConfigurationIfNonSys_executor = param -> {
        int calling = Binder.getCallingUid();
        long additional = 0;
        for (Object o:param.args){
            additional += o.hashCode();
        }
        ExtendedRandom extendedRandom = new ExtendedRandom(calling + additional);
        extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + calling * 4L + additional);
        //android.net.InterfaceConfiguration
        Object fakeInterfaceConfiguration = XposedHelpers.newInstance(InterfaceConfigurationClass);
        XposedHelpers.callMethod(fakeInterfaceConfiguration,"setHardwareAddress",extendedRandom.nextMacAddrStr());
        extendedRandom.nextIPV4();
        Object fakeInetAddr = InetAddresses.parseNumericAddress(extendedRandom.nextIPV4());
        Object fakeLinkAddr = XposedHelpers.newInstance(LinkAddress.class,fakeInetAddr,0);
        XposedHelpers.callMethod(fakeInterfaceConfiguration,"setLinkAddress",fakeLinkAddr);
        XposedHelpers.callMethod(fakeInterfaceConfiguration,"setFlag",extendedRandom.nextBoolean()?"up":"down");
        param.setResult(fakeInterfaceConfiguration);

    };
    public static final XC_MethodHook emptyIntArrIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(EMPTY_INT_ARRAY);
            }
        }
    };
    public static final XC_MethodHook emptyStrArrIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(EmptyArrays.EMPTY_STRING_ARRAY);
            }
        }
    };
    public static final Class<?> UserInfoClass = XposedHelpers.findClass("android.content.pm.UserInfo",XposedBridge.BOOTCLASSLOADER);
    public static final Object FAKE_USER_INFO = XposedHelpers.newInstance(UserInfoClass,0,null,0);
    public static final Object FAKE_USER_INFO_ARR = Array.newInstance(UserInfoClass,1);
    static {
        Array.set(FAKE_USER_INFO_ARR,0,FAKE_USER_INFO);
    }
    public static final Bundle EMPTY_BUNDLE = new Bundle();
    public static final ArrayList<Object> EMPTY_ARRAYLIST = new ArrayList<>();
    public static final ArrayMap<Object,Object> EMPTY_ARRAYMAP = new ArrayMap<>();
    public static final ParceledListSlice<?> EMPTY_PARCELED_LIST_SLICE = new ParceledListSlice<>(Collections.emptyList());
    public static final HashMap<Object,Object> EMPTY_HASHMAP = new HashMap<>();
    public static final Class<?> ParceledListSliceClass = XposedHelpers.findClass("android.content.pm.ParceledListSlice",XposedBridge.BOOTCLASSLOADER);
    public static final SimpleExecutor ParceledListSliceGen = param -> param.setResult(XposedHelpers.newInstance(ParceledListSliceClass,new Class[]{List.class},EMPTY_ARRAYLIST));
    public static Object EMPTY_WATCHED_ARRAYMAP;
    public static final SparseArray EMPTY_SPARSE_ARRAY = new SparseArray();
    public static final XC_MethodHook newSparseArrayIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(new SparseArray());
            }
        }
    };
    public static final XC_MethodHook emptySparseArrayIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(EMPTY_SPARSE_ARRAY);
            }
        }
    };
    public static final XC_MethodHook getConfusedApplicationInfoIfNonSys = new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            int callingUID = Binder.getCallingUid();
            if (!isSystemApp(callingUID)){
                ApplicationInfo result = (ApplicationInfo) param.getResult();
                if (result == null){return;}
                if (result.uid == callingUID){
                    return;
                }
                long seed = ((long) callingUID) <<1 + ((long)result.uid) + (long)result.packageName.hashCode();
                if (nonSysPackages.get(callingUID) != null){
                    if (nonSysPackages.get(callingUID).packageName != null){
                        seed += nonSysPackages.get(callingUID).packageName.hashCode();
                    }
                }
                ExtendedRandom extendedRandom = new ExtendedRandom(seed << 2 + seed);
                param.setResult(extendedRandom.confuseApplicationInfo(result));
            }
        }
    };


    public static final Pair<List,List> EMPTY_LIST_LIST_PAIR = new Pair<>(EMPTY_ARRAYLIST,EMPTY_ARRAYLIST);

    public static final SimpleExecutor fakeBluetoothMacAddr = param -> {
        int callingUid = Binder.getCallingUid();
        ExtendedRandom extendedRandom = new ExtendedRandom(callingUid*2L ^ BluetoothAdapter.class.getName().hashCode());
        String pkgName = getPackageName(callingUid);
        if (pkgName != null){
            extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode() + "bluetooth".hashCode());
        }
        param.setResult(extendedRandom.nextMacAddrStr());
    };
    public static final SimpleExecutor fakeBluetoothName = param -> {
        int callingUid = Binder.getCallingUid();
        ExtendedRandom extendedRandom = new ExtendedRandom(callingUid*2L);
        String pkgName = getPackageName(callingUid);
        if (pkgName != null){
            extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode() + "bluetooth_name".hashCode());
        }
        param.setResult(extendedRandom.nextString(extendedRandom.nextInt(10)+5));
    };


    /**
     * stores all object to return
     */
    public static final Map<Pair<Object,SystemAppChecker>,XC_MethodHook> objectIfNonSys_Map = new HashMap<>();
    public static XC_MethodHook objectIfNonSys(Object object,SystemAppChecker systemAppChecker){
        Pair<Object,SystemAppChecker> key = new Pair<>(object,systemAppChecker);
        XC_MethodHook ret = objectIfNonSys_Map.getOrDefault(key,null);
        if (ret != null){
            return ret;
        }
        ret = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (systemAppChecker.checkSystemApp(param)){
                    return;
                }
                param.setResult(object);
            }
        };
        objectIfNonSys_Map.put(key,ret);
        return ret;
    }

    public static final  Map<int[],SimpleExecutorWithMode> argNull = new HashMap<>();
    public static SimpleExecutorWithMode executorNopArg(int[] indexes){
        SimpleExecutorWithMode ret = argNull.getOrDefault(indexes,null);
        if (ret != null){
            return ret;
        }
        ret = new SimpleExecutorWithMode(MODE_BEFORE, param -> {
            for (int index:indexes){
                param.args[index] = null;
            }
        });
        argNull.put(indexes,ret);
        return ret;
    }

    public static List<XC_MethodHook.Unhook> hookAllMethodsWithCache_Auto(Class<?> hookClass, String methodName, Object object){
        return hookAllMethodsWithCache_Auto(hookClass,methodName,object,defaultSystemChecker);
    }

    public static boolean isSystemProcessRecord(Object processRecord){
        try {
            if (processRecord == null){
                return true;//null receiver
            }
            ApplicationInfo appInfo = (ApplicationInfo) ProcessRecordUtils.applicationInfoField.get(processRecord);
            if (appInfo != null){
                String packageName = appInfo.packageName;
                if (packageName != null){
                    if (WHITELIST_PACKAGE_NAMES.contains(packageName)){
                        return true;
                    }
                }
                if (!appInfo.isSystemApp()){return false;}
            }
        }catch (Throwable e){
            LoggerLog(e);
        }
        return true;
    }

    public static boolean isSystemIApplicationThread(IApplicationThread thread){
        try {
            for (Object processListRef: ProcessListUtils.ALL_PROCESS_LISTS){
                if (processListRef == null){
                    continue;
                }
                Object processRecord = XposedHelpers.callMethod(processListRef,"getLRURecordForAppLOSP",thread);
                if (processRecord == null){
                    continue;
                }
                if (!isSystemProcessRecord(processRecord)){
                    return false;
                }
            }
        }catch (Throwable e){
            LoggerLog(e);
        }
        return true;
    }

    public static int uidOfIApplicationThread(IApplicationThread thread){
        try {
            for (Object processListRef: ProcessListUtils.ALL_PROCESS_LISTS){
                if (processListRef == null){
                    continue;
                }
                Object processRecord = XposedHelpers.callMethod(processListRef,"getLRURecordForAppLOSP",thread);
                int uid = uidOfProcessRecord(processRecord);
                if (!(uid==Integer.MIN_VALUE)){
                    return uid;
                }
            }
        }catch (Throwable e){
            LoggerLog(e);
        }
        return Integer.MIN_VALUE;
    }
    public static int uidOfProcessRecord(Object /*ProcessRecord*/ processRecord){
        return ProcessRecordUtils.outputInformation(processRecord).uid;
    }

    @ParametersAreNonnullByDefault
    record HookRecord(int hookClassIdentityHash,String methodName,int objectIdentityHash,int sysAppCheckerIdentityHash){
        HookRecord(Class<?> hookClass, String methodName, Object object,SystemAppChecker systemAppChecker){
            this(identityHashCode(hookClass),methodName,identityHashCode(object),identityHashCode(systemAppChecker));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof HookRecord that)) return false;
            return objectIdentityHash == that.objectIdentityHash
                    && hookClassIdentityHash == that.hookClassIdentityHash
                    && sysAppCheckerIdentityHash == that.sysAppCheckerIdentityHash
                    && Objects.equals(methodName, that.methodName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hookClassIdentityHash, methodName, objectIdentityHash, sysAppCheckerIdentityHash);
        }
    }

    private static final Set<HookRecord> HOOKED_SET = ConcurrentHashMap.newKeySet();
    private static boolean checkHooked(Class<?> hookClass, String methodName, Object object,SystemAppChecker systemAppChecker){
        HookRecord key = new HookRecord(hookClass,methodName,object,systemAppChecker);
        if (HOOKED_SET.contains(key)){
            return true;
        }
        HOOKED_SET.add(key);
        return false;
    }

    /**
     * usually,we hook a method in two steps:
     * <p>check the caller,if it's system or whitelisted,pass.</p>
     * <p>Otherwise,modify return value/do st. else.</p>
     * <p>systemAppChecker is st. like {@link Function}< {@link XC_MethodHook.MethodHookParam},{@link Boolean} >,returns if the caller is system app or whitelisted</p>
     * <p>SimpleExecutor deals with the next step</p>
     * <p>the mode of {@link SimpleExecutorWithMode} decides when will it be executed(before/after/both of the method executed)</p>
     * <p>if a return value is inserted,it will be considered as a SimpleExecutor only sets the result(param.setResult(obj))</p>
     * <p>if a {@link SimpleExecutor} is put in,it will be considered as {@link SimpleExecutorWithMode}({@link com.linearity.utils.SimpleExecutor#MODE_BEFORE},SimpleExecutor)</p>
     * @param hookClass the class to hook
     * @param methodName method to hook(won't hook super class)
     * @param object SimpleExecutorWithMode/SimpleExecutor/returnValue
     * @param systemAppChecker decides how to check if the caller calling this method is system app.
     * @return unhooks for hooked methods
     */
    public static List<XC_MethodHook.Unhook> hookAllMethodsWithCache_Auto(Class<?> hookClass, String methodName, Object object,SystemAppChecker systemAppChecker){

        List<XC_MethodHook.Unhook> unhooks = new ArrayList<>();
        //ReturnObjIfNonSys executes XC_MethodHook,don't worry
//        assert !(object instanceof XC_MethodHook);
        if (object == null){
            Collection<XC_MethodHook.Unhook> unhookCollection = hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,methodName, null,systemAppChecker);
            unhooks.addAll(unhookCollection);
            return unhooks;
        }

        if (object instanceof SimpleExecutorWithMode simpleExecutorWithMode){
            Collection<XC_MethodHook.Unhook> unhookCollection = hookAllMethodsWithCache_executeIfNonSys(hookClass,methodName, simpleExecutorWithMode,systemAppChecker);
            unhooks.addAll(unhookCollection);
            return unhooks;
        }
        else if (object instanceof SimpleExecutor simpleExecutor){
            Collection<XC_MethodHook.Unhook> unhookCollection = hookAllMethodsWithCache_executeIfNonSys(hookClass,methodName,
                    new SimpleExecutorWithMode(MODE_BEFORE, simpleExecutor),
                    systemAppChecker);
            unhooks.addAll(unhookCollection);
            return unhooks;
        }
        else {
            Collection<XC_MethodHook.Unhook> unhookCollection = hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,methodName, object,systemAppChecker);
            unhooks.addAll(unhookCollection);
            return unhooks;
        }
    }
    public static List<XC_MethodHook.Unhook> hookAllMethodsWithCache_ReturnObjIfNonSys(Class<?> hookClass, String methodName, Object object){
        return hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,methodName,object,defaultSystemChecker);
    }
    public static List<XC_MethodHook.Unhook> hookAllMethodsWithCache_ReturnObjIfNonSys(Class<?> hookClass, String methodName, Object object,SystemAppChecker systemAppChecker){

        if (checkHooked(hookClass,methodName,object,systemAppChecker)){
            return Collections.emptyList();
        }
        List<XC_MethodHook.Unhook> unhooks = new ArrayList<>();
        if (object != null){
            if (object instanceof XC_MethodHook methodHook){
                Collection<XC_MethodHook.Unhook> unhook = null;
                if (Objects.equals(CONSTRUCTOR_METHOD_STRING,methodName)){
                    unhook = XposedBridge.hookAllConstructors(hookClass,methodHook);
                }else {
                    XposedBridge.hookAllMethods(hookClass,methodName,methodHook);
                }
                if (unhook != null) {
                    unhooks.addAll(unhook);
                }
                return unhooks;
            }
            if (object instanceof SimpleExecutorWithMode simpleExecutorWithMode){
                Collection<XC_MethodHook.Unhook> unhook = hookAllMethodsWithCache_executeIfNonSys(hookClass,methodName, simpleExecutorWithMode,systemAppChecker);
                unhooks.addAll(unhook);
                return unhooks;
            }
        }
        if (Objects.equals(CONSTRUCTOR_METHOD_STRING,methodName)){
            unhooks.addAll(XposedBridge.hookAllConstructors(hookClass,objectIfNonSys(null, systemAppChecker)));
        }
        else {
            for (Method m : hookClass.getDeclaredMethods()) {
                if (!resolveMethodName(m.getName()).equals(methodName)) {
                    continue;
                }

                XC_MethodHook.Unhook unhook = XposedBridge.hookMethod(m, objectIfNonSys(object, systemAppChecker));
                unhooks.add(unhook);
            }
        }
        return unhooks;
    }

    public static List<XC_MethodHook.Unhook> hookAllMethodsWithCache_executeIfNonSys(Class<?> hookClass, String methodName, SimpleExecutorWithMode simpleExecutorWithMode){
        return hookAllMethodsWithCache_executeIfNonSys(hookClass,methodName,simpleExecutorWithMode,defaultSystemChecker);
    }
    public static List<XC_MethodHook.Unhook> hookAllMethodsWithCache_executeIfNonSys(Class<?> hookClass, String methodName, SimpleExecutorWithMode simpleExecutorWithMode,SystemAppChecker systemAppChecker){

        if (checkHooked(hookClass,methodName,simpleExecutorWithMode,systemAppChecker)){
            return Collections.emptyList();
        }
        List<XC_MethodHook.Unhook> unhooks = new ArrayList<>();
        if (Objects.equals(CONSTRUCTOR_METHOD_STRING,methodName)){
            unhooks.addAll(XposedBridge.hookAllConstructors(hookClass,simpleExecutorWithoutAttrSource(simpleExecutorWithMode,systemAppChecker)));
        }else {
            for (Method m:hookClass.getDeclaredMethods()){
                if (Objects.equals(resolveMethodName(m.getName()),methodName)){
                    XC_MethodHook.Unhook unhook = XposedBridge.hookMethod(m,simpleExecutorWithoutAttrSource(simpleExecutorWithMode,systemAppChecker));
                    unhooks.add(unhook);
                }
            }
        }

        return unhooks;
    }
    public static final class SyntheticNameResolver {

        // === Method name patterns ===
        private static final Pattern[] METHOD_PATTERNS = new Pattern[]{
                // -$$Nest$smwriteImage / -$$Nest$mrun
                Pattern.compile("^-?\\$\\$Nest\\$(?:sm|m)(?<name>\\w+)$"),

                // lambda$doWork$0
                Pattern.compile("^lambda\\$(?<name>\\w+)\\$\\d+$"),

                // exportImage$suspendImpl / saveFile$default
                Pattern.compile("^(?<name>\\w+)\\$(?:suspendImpl|default)$"),

                // access$methodName
                Pattern.compile("^access\\$(?<name>\\w+)$"),

                // Fallback: keep prefix before first '$'
                Pattern.compile("^(?<name>\\w+)\\$.*$")
        };

        // === Field name patterns ===
        private static final Pattern[] FIELD_PATTERNS = new Pattern[]{
                // -$$Nest$fbitmap / -$$Nest$sfINSTANCE
                Pattern.compile("^-?\\$\\$Nest\\$(?:sf|f)(?<name>\\w+)$"),

                // access$getField$p
                Pattern.compile("^access\\$get(?<name>\\w+)\\$p$"),

                // Kotlin backing fields (e.g. _field, field$delegate)
                Pattern.compile("^(?:_)?(?<name>\\w+?)(?:\\$delegate)?$")
        };

        // === Class name patterns ===
        private static final Pattern[] CLASS_PATTERNS = new Pattern[]{
                // Inner and synthetic lambda classes
                Pattern.compile("^(?<name>.+)\\$\\$Lambda\\$\\d+$"),
                Pattern.compile("^(?<name>.+)\\$\\$ExternalSyntheticLambda\\d+$"),

                // Inner / anonymous classes
                Pattern.compile("^(?<name>.+)\\$\\d+$"),        // e.g. MyClass$1
                Pattern.compile("^(?<name>.+)\\$.*$"),          // e.g. MyClass$Companion
        };

        /** Resolve a method name like -$$Nest$smwriteImage → writeImage */
        public static String resolveMethodName(String name) {
            return resolveUsing(name, METHOD_PATTERNS);
        }

        /** Resolve a field name like -$$Nest$fbitmap → bitmap */
        public static String resolveFieldName(String name) {
            return resolveUsing(name, FIELD_PATTERNS);
        }

        /** Resolve a class name like MyClass$$Lambda$1 → MyClass */
        public static String resolveClassName(String name) {
            return resolveUsing(name, CLASS_PATTERNS);
        }
        public static String fullResolvedClassName(Class<?> clazz) {
            if (clazz == null) return null;

            // Get package name (may be empty for default package)
            String packageName = clazz.getPackage() != null ? clazz.getPackage().getName() : "";

            // Resolve synthetic/inner class name
            String resolvedName = SyntheticNameResolver.resolveClassName(clazz.getSimpleName());

            // Combine
            return packageName.isEmpty() ? resolvedName : packageName + "." + resolvedName;
        }
        public static String fullBaseClassName(Class<?> clazz) {
            if (clazz == null) return null;

            // Get package name
            String packageName = clazz.getPackage() != null ? clazz.getPackage().getName() : "";

            // Start with the simple name
            String name = clazz.getSimpleName();

            // Recursively resolve synthetic/inner parts
            String previous;
            do {
                previous = name;
                name = SyntheticNameResolver.resolveClassName(name);
            } while (!name.equals(previous)); // repeat until no change

            // Combine package + resolved name
            return packageName.isEmpty() ? name : packageName + "." + name;
        }

        // === Internal helper ===
        private static String resolveUsing(String name, Pattern[] patterns) {
            try {
                if (name == null) return null;
                for (Pattern p : patterns) {
                    Matcher m = p.matcher(name);
                    if (m.find()) {
                        String real = m.group("name");
                        if (real != null && !real.isEmpty()) return real;
                    }
                }
            }catch (Throwable e){
                LoggerLog(e);
            }
            return name; // fallback — not synthetic or unknown pattern
        }
    }

    public static <T> T findArgByClassInArgs(Object[] args, Class<T> toFind){
        boolean isInterface = toFind.isInterface();
        for (Object arg : args) {
            if (arg == null) {
                continue;
            }
            if (toFind.isAssignableFrom(arg.getClass())) {
                return (T) arg;
            }else if (isInterface){
                for (Class<?> iface:arg.getClass().getInterfaces()){
                    if (toFind.isAssignableFrom(iface)){
                        return (T) arg;
                    }
                }
            }
        }
        return null;
    }

    public static <T> List<T> findArgsByClassInArgs(Object[] args, Class<T> toFind) {
        List<T> results = new ArrayList<>();
        for (Object arg : args) {
            if (arg != null && toFind.isAssignableFrom(arg.getClass())) {
                results.add((T) arg);
            }
        }
        return results;
    }

    public static int findClassIndexInArgs(Object[] args,Class<?> toFind){
        boolean isInterface = toFind.isInterface();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null){continue;}
            if (toFind.isAssignableFrom(args[i].getClass())){
                return i;
            }else if (isInterface){
                for (Class<?> iface:args[i].getClass().getInterfaces()){
                    if (iface.isAssignableFrom(toFind)){
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    /**
    * Do not use basic type like int.class,use Boxed like Integer.class instead
     */
    public static <T> Pair<Integer,T> findClassIndexAndObjectInArgs(Object[] args, Class<T> toFind){
        boolean isInterface = toFind.isInterface();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null){continue;}
            Object retObj = args[i];
            if (toFind.isAssignableFrom(retObj.getClass())){
                return new Pair<>(i,(T) retObj);
            }else if (isInterface){
                for (Class<?> iface:retObj.getClass().getInterfaces()){
                    if (iface.isAssignableFrom(toFind)){
                        return new Pair<>(i,(T) retObj);
                    }
                }
            }
        }
        return new Pair<>(-1,null);
    }
    public static final boolean doShowBefore = false;

    public static SimpleExecutorWithMode showBefore = new SimpleExecutorWithMode(MODE_BEFORE, param -> {
        if (doShowBefore){
            LoggerLog(new Exception(param.getResult()
                    + "\n" + param.method
                    + "\n" + Arrays.deepToString(param.args)
                    + "\n" + Binder.getCallingUid()
                    + "\n" + getPackageName(Binder.getCallingUid())));
        }
    });
    public static SimpleExecutorWithMode showBeforeThenNull = new SimpleExecutorWithMode(MODE_BEFORE, param -> {
        Exception e = new Exception(param.getResult()
                + "\n" + param.method
                + "\n" + Arrays.deepToString(param.args)
                + "\n" + Binder.getCallingUid()
                + "\n" + getPackageName(Binder.getCallingUid()));
        LoggerLog(e);
        param.setResult(null);
    });
    public static SimpleExecutorWithMode showAfter = new SimpleExecutorWithMode(MODE_AFTER, param ->{
        Object result = param.getResult();
        String resultString = ObjectsToString.convert(result);

        LoggerLog(new Exception(
                "\n" + resultString
                + "\n" + param.method
                + "\n" + Arrays.deepToString(param.args)
                + "\n" + Binder.getCallingUid()
                + "\n" + getPackageName(Binder.getCallingUid())));
        });

    //false:not system

    public static final HashMap<Integer,SystemAppChecker> getSystemChecker_AttributionSourceAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_AttributionSourceAt(int index){
        if (getSystemChecker_AttributionSourceAt_map.containsKey(index)){
            return getSystemChecker_AttributionSourceAt_map.get(index);
        }
        SystemAppChecker ret = param -> {
            int tempIndex = index;
            if (tempIndex < 0){
                tempIndex += param.args.length;
            }
            try{
                return isSystemApp(Binder.getCallingUid())
                        && isSystemApp((AttributionSource) param.args[tempIndex]);
            }catch (Throwable e){
                tryGetPM(param.thisObject);
                if (!isSystemApp(Binder.getCallingUid())){
                    return false;
                }
                int callingUid = Binder.getCallingUid();
                LoggerLog("getAttributionSource Failed:"
                        + "\n" + Arrays.deepToString(param.args)
                        + "\n" + param.method
                        + "\n" + param.thisObject
                        + "\n" + callingUid
                        + "\n" + getPackageName(callingUid)
                        + "\n" + "------------------------------"
                );
                LoggerLog(e);
                return true;
            }
        };

        getSystemChecker_AttributionSourceAt_map.put(index,ret);
        return ret;
    }
    public static final HashMap<Integer,SystemAppChecker> getSystemChecker_AttributionSourceMayAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_AttributionSourceMayAt(int index){
        if (getSystemChecker_AttributionSourceMayAt_map.containsKey(index)){
            return getSystemChecker_AttributionSourceMayAt_map.get(index);
        }
        SystemAppChecker ret = param -> {
            int tempIndex = index;
            if (tempIndex < 0){
                tempIndex += param.args.length;
            }
            if (!(param.args[tempIndex] instanceof AttributionSource)){
                return isSystemApp(Binder.getCallingUid());
            }
            try{
                return isSystemApp(Binder.getCallingUid())
                        && isSystemApp((AttributionSource) param.args[tempIndex]);
            }catch (Throwable e){
                tryGetPM(param.thisObject);
                if (!isSystemApp(Binder.getCallingUid())){
                    return false;
                }
                LoggerLog("getAttributionSource Failed:"
                        + "\n" + Arrays.deepToString(param.args)
                        + "\n" + param.method
                        + "\n" + param.thisObject
                        + "\n" + Binder.getCallingUid()
                        + "\n" + getPackageName(Binder.getCallingUid())
                        + "\n" + "------------------------------"
                );
                LoggerLog(e);
                return true;
            }
        };

        getSystemChecker_AttributionSourceMayAt_map.put(index,ret);
        return ret;
    }
    public static final HashMap<Integer,SystemAppChecker> getSystemChecker_WorkSourceAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_WorkSourceAt(int index){
        if (getSystemChecker_WorkSourceAt_map.containsKey(index)){
            return getSystemChecker_WorkSourceAt_map.get(index);
        }
        SystemAppChecker ret = param -> {

            int tempIndex = index;
            if (tempIndex < 0){
                tempIndex += param.args.length;
            }
            try{
                return isSystemApp(Binder.getCallingUid())
                        && isSystemApp((WorkSource) param.args[tempIndex]);
            }catch (Throwable e){
                tryGetPM(param.thisObject);
                if (!isSystemApp(Binder.getCallingUid())){
                    return false;
                }
                LoggerLog("getWorkSource Failed:"
                        + "\n" + Arrays.deepToString(param.args)
                        + "\n" + param.method
                        + "\n" + param.thisObject
                        + "\n" + Binder.getCallingUid()
                        + "\n" + getPackageName(Binder.getCallingUid())
                );
                LoggerLog(e);
                return true;
            }
        };

        getSystemChecker_WorkSourceAt_map.put(index,ret);
        return ret;
    }
    public static final HashMap<Pair<Integer,Integer>,SystemAppChecker> getSystemChecker_WorkSourceAndAttributionSourceAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_WorkSourceAndAttributionSourceAt(int workSourceIndex,int attrSourceIndex){
        Pair<Integer,Integer> key = new Pair<>(workSourceIndex,attrSourceIndex);
        if (getSystemChecker_WorkSourceAndAttributionSourceAt_map.containsKey(key)){
            return getSystemChecker_WorkSourceAndAttributionSourceAt_map.get(key);
        }
        SystemAppChecker ret = param -> {
            int tempWorkSourceIndex = workSourceIndex;
            int tempAttrSourceIndex = attrSourceIndex;
            if (tempWorkSourceIndex < 0){
                tempWorkSourceIndex += param.args.length;
            }
            if (tempAttrSourceIndex < 0){
                tempAttrSourceIndex += param.args.length;
            }
            try{
                
                return isSystemApp(Binder.getCallingUid())
                        && isSystemApp((WorkSource) param.args[tempWorkSourceIndex])
                        && isSystemApp((WorkSource) param.args[tempAttrSourceIndex]);
            }catch (Throwable e){
                tryGetPM(param.thisObject);
                if (!isSystemApp(Binder.getCallingUid())){
                    return false;
                }
                LoggerLog("getWorkSource Failed:"
                        + "\n" + Arrays.deepToString(param.args)
                        + "\n" + param.method
                        + "\n" + param.thisObject
                        + "\n" + Binder.getCallingUid()
                        + "\n" + getPackageName(Binder.getCallingUid())
                );
                LoggerLog(e);
                return true;
            }
        };
        getSystemChecker_WorkSourceAndAttributionSourceAt_map.put(key,ret);
        return ret;
    }
    public static final HashMap<Integer,SystemAppChecker> getSystemChecker_BooleanAt_map = new HashMap<>();

    public static SystemAppChecker getSystemChecker_BooleanAt(int index){
        if (getSystemChecker_BooleanAt_map.containsKey(index)){
            return getSystemChecker_BooleanAt_map.get(index);
        }
        SystemAppChecker ret = param -> {
            int tempIndex = index;
            if (tempIndex < 0){
                tempIndex += param.args.length;
            }
            try{
                return isSystemApp(Binder.getCallingUid())
                        && ((boolean) param.args[tempIndex]);
            }catch (Throwable e){
                tryGetPM(param.thisObject);
                if (!isSystemApp(Binder.getCallingUid())){
                    return false;
                }
                LoggerLog("getBoolean Failed:"
                        + "\n" + Arrays.deepToString(param.args)
                        + "\n" + param.method
                        + "\n" + param.thisObject
                        + "\n" + Binder.getCallingUid()
                        + "\n" + getPackageName(Binder.getCallingUid())
                );
                LoggerLog(e);
                return true;
            }
        };

        getSystemChecker_BooleanAt_map.put(index,ret);
        return ret;
    }
    public static final HashMap<Pair<SystemAppChecker,SystemAppChecker>,SystemAppChecker> sysAppCheckerAndOperation_map = new HashMap<>();
    //maybe over-wrapping
    public static SystemAppChecker sysAppCheckerAndOperation(SystemAppChecker checkerA, SystemAppChecker checkerB){
        Pair<SystemAppChecker,SystemAppChecker> key = new Pair<>(checkerA,checkerB);
        if(sysAppCheckerAndOperation_map.containsKey(key)){
            return sysAppCheckerAndOperation_map.get(key);
        }
        Pair<SystemAppChecker,SystemAppChecker> key2 = new Pair<>(checkerB,checkerA);
        if(sysAppCheckerAndOperation_map.containsKey(key2)){
            return sysAppCheckerAndOperation_map.get(key2);
        }
        SystemAppChecker result = param -> checkerA.checkSystemApp(param) && checkerB.checkSystemApp(param);
        sysAppCheckerAndOperation_map.put(key,result);
        return result;
    }

}
