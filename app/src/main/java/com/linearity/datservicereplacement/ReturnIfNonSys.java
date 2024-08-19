package com.linearity.datservicereplacement;

import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.getPackageName;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.isSystemApp;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.nonSysPackages;
import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_INT_ARRAY;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER_NO_CHECK;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE_AND_AFTER;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE_AND_AFTER_NO_CHECK;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE_NO_CHECK;

import android.content.AttributionSource;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.InetAddresses;
import android.net.LinkAddress;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SparseArray;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.SimpleExecutorWithMode;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class ReturnIfNonSys {
    public static final XC_MethodHook attrSourceAtSomewhere_null = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                param.setResult(null);
            }
            AttributionSource attributionSource = null;
            for (Object o:param.args){
                if (o instanceof AttributionSource){
                    attributionSource = (AttributionSource) o;
                }
            }
            if (attributionSource != null){
                if (!isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())){
                    param.setResult(null);
                }
            }
        }
    };
    public static boolean mSystemReady = false;
    public static final Method numericToInetAddress = XposedHelpers.findMethodExact("android.net.NetworkUtils",XposedBridge.BOOTCLASSLOADER,"numericToInetAddress",String.class);

    public static final XC_MethodHook zeroIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                param.setResult(0);
            }
        }
    };
    public static final XC_MethodHook oneIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                param.setResult(1);
            }
        }
    };

    public static final Map<Integer,XC_MethodHook> integerIfNonSys = new HashMap<>();
    public static final Map<Object,XC_MethodHook> objAttrSourceSomewhere = new HashMap<>();
    public static final Map<Object,XC_MethodHook> pick_AttrSourceSomewhere = new HashMap<>();
    public static final Map<Object,Map<Integer,XC_MethodHook>> ObjectWithAttrSource_Map = new HashMap<>();
    public static XC_MethodHook ObjectWithAttrSource(Object result,int index){
        {
            boolean resultNull = (result == null);
            XC_MethodHook ret;
            Map<Integer,XC_MethodHook> map4int = ObjectWithAttrSource_Map.getOrDefault(result,null);
            if (map4int == null){
                map4int = new HashMap<>();
                ObjectWithAttrSource_Map.put(result,map4int);
            }
            ret = map4int.getOrDefault(index,null);
            if (ret != null){
                return ret;
            }
            if (resultNull){
                ret = new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (!isSystemApp(Binder.getCallingUid())){
                            param.setResult(null);
                        }
                        AttributionSource attributionSource = null;
                        attributionSource = (AttributionSource) param.args[index];
                        if (attributionSource != null){
                            if (!isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())){
                                param.setResult(null);
                            }
                        }
                    }
                };
            }else {
                ret = new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (!isSystemApp(Binder.getCallingUid())){
                            param.setResult(result);
                        }
                        AttributionSource attributionSource = null;
                        attributionSource = (AttributionSource) param.args[index];
                        if (attributionSource != null){
                            if (!isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())){
                                param.setResult(result);
                            }
                        }
                    }
                };
            }
            map4int.put(index,ret);
            return ret;
        }
    }
    public static final Map<SimpleExecutorWithMode,Map<Integer,XC_MethodHook>> SimpleExecutorWithAttrSource_Map = new HashMap<>();
    public static final Map<SimpleExecutorWithMode,XC_MethodHook> SimpleExecutor_Map = new HashMap<>();
    public static XC_MethodHook simpleExecutorWithAttrSource(SimpleExecutorWithMode simpleExecutorWithMode, int index){
        {
            XC_MethodHook ret;
            Map<Integer,XC_MethodHook> map4int = SimpleExecutorWithAttrSource_Map.getOrDefault(simpleExecutorWithMode,null);
            if (map4int == null){
                map4int = new HashMap<>();
                SimpleExecutorWithAttrSource_Map.put(simpleExecutorWithMode,map4int);
            }
            ret = map4int.getOrDefault(index,null);
            if (ret != null){
                return ret;
            }else {
                ret = switchForSimpleExecutor(simpleExecutorWithMode);
            }
            map4int.put(index,ret);
            return ret;
        }
    }
    public static XC_MethodHook simpleExecutorWithoutAttrSource(SimpleExecutorWithMode simpleExecutorWithMode){
        {
            XC_MethodHook ret = SimpleExecutor_Map.getOrDefault(simpleExecutorWithMode,null);
            if (ret != null){
                return ret;
            }else {
                ret = switchForSimpleExecutor(simpleExecutorWithMode);
            }
            SimpleExecutor_Map.put(simpleExecutorWithMode,ret);
            return ret;
        }
    }
    private static XC_MethodHook switchForSimpleExecutor(SimpleExecutorWithMode simpleExecutorWithMode){
        switch (simpleExecutorWithMode.mode){
            case MODE_BEFORE:
                return new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (isSystemApp(Binder.getCallingUid())){return;}
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    }
                };
            case MODE_AFTER:
                return new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        if (isSystemApp(Binder.getCallingUid())){return;}
                        super.afterHookedMethod(param);
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    }
                };
            case MODE_BEFORE_AND_AFTER:
                return new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (isSystemApp(Binder.getCallingUid())){return;}
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if (isSystemApp(Binder.getCallingUid())){return;}//fixed clipboard not working
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    }
                };
            case MODE_BEFORE_NO_CHECK:
                return new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
//                        if (isSystemApp(Binder.getCallingUid())){return;}
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    }
                };
            case MODE_AFTER_NO_CHECK:
                return new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        if (isSystemApp(Binder.getCallingUid())){return;}
                        super.afterHookedMethod(param);
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    }
                };
            case MODE_BEFORE_AND_AFTER_NO_CHECK:
                return new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
//                        if (isSystemApp(Binder.getCallingUid())){return;}
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if (isSystemApp(Binder.getCallingUid())){return;}//fixed clipboard not working
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    }
                };
            default:
                return new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (isSystemApp(Binder.getCallingUid())){return;}//fixed clipboard not working
                        simpleExecutorWithMode.simpleExecutor.execute(param);
                    }
                };
        }
    }

    public static XC_MethodHook getObjectAttrSourceAtSomewhere(int returnIfNonSys){
        if (objAttrSourceSomewhere.containsKey(returnIfNonSys) && objAttrSourceSomewhere.get(returnIfNonSys) != null){
            return objAttrSourceSomewhere.get(returnIfNonSys);
        }
        XC_MethodHook result = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (!isSystemApp(Binder.getCallingUid())){
                    param.setResult(returnIfNonSys);
                }
                AttributionSource attributionSource = null;
                for (Object o:param.args){
                    if (o instanceof AttributionSource){
                        attributionSource = (AttributionSource) o;
                    }
                }
                if (attributionSource != null){
                    if (!isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())){
                        param.setResult(returnIfNonSys);
                    }
                }
            }
        };
        objAttrSourceSomewhere.put(returnIfNonSys,result);
        return result;
    }
    public static XC_MethodHook pickFromArray_AttrSourceAtSomewhere(long[] returnIfNonSysArr){
        if (pick_AttrSourceSomewhere.containsKey(returnIfNonSysArr) && pick_AttrSourceSomewhere.get(returnIfNonSysArr) != null){
            return pick_AttrSourceSomewhere.get(returnIfNonSysArr);
        }
        XC_MethodHook result = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (!isSystemApp(Binder.getCallingUid())){
                    ExtendedRandom extendedRandom = new ExtendedRandom(Arrays.hashCode(returnIfNonSysArr) + Binder.getCallingUid());
                    param.setResult(extendedRandom.pickFromArray(returnIfNonSysArr));
                }
                AttributionSource attributionSource = null;
                for (Object o:param.args){
                    if (o instanceof AttributionSource){
                        attributionSource = (AttributionSource) o;
                    }
                }
                if (attributionSource != null){
                    if (!isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())){
                        ExtendedRandom extendedRandom = new ExtendedRandom(Arrays.hashCode(returnIfNonSysArr) + Binder.getCallingUid() + attributionSource.getUid());
                        if (attributionSource.getPackageName() != null){
                            extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + attributionSource.getPackageName().hashCode());
                        }
                        param.setResult(extendedRandom.pickFromArray(returnIfNonSysArr));
                    }
                }
            }
        };
        pick_AttrSourceSomewhere.put(returnIfNonSysArr,result);
        return result;
    }
    public static XC_MethodHook pickFromArray_AttrSourceAtSomewhere(Object[] returnIfNonSysArr){
        if (pick_AttrSourceSomewhere.containsKey(returnIfNonSysArr) && pick_AttrSourceSomewhere.get(returnIfNonSysArr) != null){
            return pick_AttrSourceSomewhere.get(returnIfNonSysArr);
        }
        XC_MethodHook result = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (!isSystemApp(Binder.getCallingUid())){
                    ExtendedRandom extendedRandom = new ExtendedRandom(Arrays.hashCode(returnIfNonSysArr) + Binder.getCallingUid());
                    param.setResult(extendedRandom.pickFromArray(returnIfNonSysArr));
                }
                AttributionSource attributionSource = null;
                for (Object o:param.args){
                    if (o instanceof AttributionSource){
                        attributionSource = (AttributionSource) o;
                    }
                }
                if (attributionSource != null){
                    if (!isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())){
                        ExtendedRandom extendedRandom = new ExtendedRandom(Arrays.hashCode(returnIfNonSysArr) + Binder.getCallingUid() + attributionSource.getUid());
                        if (attributionSource.getPackageName() != null){
                            extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + attributionSource.getPackageName().hashCode());
                        }
                        param.setResult(extendedRandom.pickFromArray(returnIfNonSysArr));
                    }
                }
            }
        };
        pick_AttrSourceSomewhere.put(returnIfNonSysArr,result);
        return result;
    }
    public static XC_MethodHook getObjectAttrSourceAtSomewhere(Object returnIfNonSys){
        if (returnIfNonSys == null){return attrSourceAtSomewhere_null;}

        if (objAttrSourceSomewhere.containsKey(returnIfNonSys) && objAttrSourceSomewhere.get(returnIfNonSys) != null){
            return objAttrSourceSomewhere.get(returnIfNonSys);
        }
        XC_MethodHook result = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (!isSystemApp(Binder.getCallingUid())){
                    param.setResult(returnIfNonSys);
                }
            }
        };
        objAttrSourceSomewhere.put(returnIfNonSys,result);
        return result;
    }
    public static XC_MethodHook getIntIfNonSys(int returnIfNonSys){

        if (integerIfNonSys.containsKey(returnIfNonSys) && integerIfNonSys.get(returnIfNonSys) != null){
            return integerIfNonSys.get(returnIfNonSys);
        }
        XC_MethodHook result = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (!isSystemApp(Binder.getCallingUid())){
                    param.setResult(returnIfNonSys);
                }
            }
        };
        integerIfNonSys.put(returnIfNonSys,result);
        return result;
    }
    public static final XC_MethodHook longZeroIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                param.setResult(0);
            }
        }
    };
    public static final XC_MethodHook longMaxIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
//            if (param.thisObject != null){
//                LoggerLog(param.thisObject + " " + param.method);
//            }else {
//                LoggerLog("null " + param.method);
//            }
            if (!isSystemApp(Binder.getCallingUid())){
                param.setResult(Long.MAX_VALUE);
            }
        }
    };
    public static final XC_MethodHook trueIfNonSysUseAttrSource = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                param.setResult(true);
            }
            AttributionSource attributionSource = null;
            for (Object o:param.args){
                if (o instanceof AttributionSource){
                    attributionSource = (AttributionSource) o;
                }
            }
            if (attributionSource != null){
                if (!isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())){
                    param.setResult(true);
                }
            }
        }
    };
    public static final XC_MethodHook falseIfNonSysUseAttrSource = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                param.setResult(false);
            }
            AttributionSource attributionSource = null;
            for (Object o:param.args){
                if (o instanceof AttributionSource){
                    attributionSource = (AttributionSource) o;
                }
            }
            if (attributionSource != null){
                if (!isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())){
                    param.setResult(false);
                }
            }
        }
    };
    public static final XC_MethodHook trueIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                param.setResult(true);
            }
        }
    };
    public static final XC_MethodHook falseIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                param.setResult(false);
            }
        }
    };
    public static final XC_MethodHook nullIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            if (!isSystemApp(Binder.getCallingUid())){
                param.setResult(null);
            }
        }
    };
    public static final XC_MethodHook randomPackageNameIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                long additional = 0;
                for (Object o:param.args){
                    additional += o.hashCode();
                }
                ExtendedRandom extendedRandom = new ExtendedRandom(calling + nonSysPackages.get(calling).packageName.hashCode()* 2L + additional);
                param.setResult(extendedRandom.nextPackageName("."));
            }
        }
    };
    public static final XC_MethodHook bundleEmptyIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(Bundle.EMPTY);
            }
        }
    };
    public static final XC_MethodHook fakeNetworkIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                long additional = 0;
                for (Object o:param.args){
                    additional += o.hashCode();
                }
                String packageName = "";
                if (nonSysPackages.containsKey(calling)){
                    if (nonSysPackages.get(calling) != null){
                        packageName = nonSysPackages.get(calling).packageName;
                    }
                }
                if (packageName == null){
                    packageName = "";
                }
                ExtendedRandom random = new ExtendedRandom(packageName.hashCode() + calling + additional*3);
                random = new ExtendedRandom(random.nextLong());
                random = new ExtendedRandom(random.nextLong());
                random = new ExtendedRandom(random.nextLong());
                Network network = (Network) XposedHelpers.newInstance(Network.class,random.nextInt());
                param.setResult(network);
            }
        }
    };
    public static final NetworkInfo FAKE_NETWORK_INFO_INSTANCE = (NetworkInfo) XposedHelpers.newInstance(NetworkInfo.class,new Class[]{NetworkInfo.class}, (Object) null);

    static {
        XposedHelpers.setIntField(FAKE_NETWORK_INFO_INSTANCE, "mNetworkType", 0);
        XposedHelpers.setIntField(FAKE_NETWORK_INFO_INSTANCE, "mSubtype", 11);
        XposedHelpers.setObjectField(FAKE_NETWORK_INFO_INSTANCE, "mTypeName", "mobile");
        XposedHelpers.setObjectField(FAKE_NETWORK_INFO_INSTANCE, "mSubtypeName", "mobile_ims");
    }
    public static final XC_MethodHook fakeNetworkInfoIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(FAKE_NETWORK_INFO_INSTANCE);
            }
        }
    };
    public static final XC_MethodHook fakeNetworkInfoArrIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(new NetworkInfo[]{FAKE_NETWORK_INFO_INSTANCE});
            }
        }
    };

    public static final XC_MethodHook fakeNetworkArrIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                long additional = 0;
                for (Object o:param.args){
                    additional += o.hashCode();
                }
                String packageName = "";
                if (nonSysPackages.containsKey(calling)){
                    if (nonSysPackages.get(calling) != null){
                        packageName = nonSysPackages.get(calling).packageName;
                    }
                }
                if (packageName == null){
                    packageName = "";
                }
                ExtendedRandom random = new ExtendedRandom(packageName.hashCode() + calling + additional);
                random = new ExtendedRandom(random.nextLong());
                random = new ExtendedRandom(random.nextLong());
                random = new ExtendedRandom(random.nextLong());
                Network network = (Network) XposedHelpers.newInstance(Network.class,random.nextInt());
                param.setResult(new Network[]{network});
            }
        }
    };

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
    public static final XC_MethodHook fakeUserInfoIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(FAKE_USER_INFO);
            }
        }
    };
    public static final XC_MethodHook fakeUserInfoArrIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(FAKE_USER_INFO_ARR);
            }
        }
    };

    public static final XC_MethodHook logIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                StringBuilder sb = new StringBuilder();
                sb.append((nonSysPackages.get(calling).packageName))
                        .append("\ncalled ")
                        .append(param.method);
                for (Object o:param.args){
                    sb.append("\n").append(o);
                }
                LoggerLog(sb.toString()
                        );
            }
        }
    };
    public static final XC_MethodHook doLog = new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            int calling = Binder.getCallingUid();
            StringBuilder sb = new StringBuilder();
            sb.append(nonSysPackages.get(calling)==null?calling:"["+calling+"]"+nonSysPackages.get(calling).packageName)
                    .append("\ncalled ")
                    .append(param.method);
            for (Object o:param.args){
                sb.append("\n").append(o);
                if (o instanceof AttributionSource){
                    AttributionSource attributionSource = (AttributionSource) o;
                    sb.append("\n[attrSource]uid:").append(attributionSource.getUid())
                            .append("\n[attrSource]pkgName:").append(attributionSource.getPackageName());
                }
            }
            LoggerLog(new Exception(sb.toString() +"\nresult:" + param.getResult()));
        }
    };
    public static final ArrayList<Object> EMPTY_ARRAYLIST = new ArrayList<>();
    public static final Class<?> ParceledListSliceClass = XposedHelpers.findClass("android.content.pm.ParceledListSlice",XposedBridge.BOOTCLASSLOADER);
    public static final Object EMPTY_PARCELED_LIST_SLICE = XposedHelpers.newInstance(ParceledListSliceClass,new Class[]{List.class},EMPTY_ARRAYLIST);
    public static final ArrayMap<Object,Object> EMPTY_ARRAYMAP = new ArrayMap<>();
    public static final ArraySet<Object> EMPTY_ARRAYSET = new ArraySet<>();
    public static Class<?> WatchedArrayMapClass;
    public static Object EMPTY_WATCHED_ARRAYMAP;
    public static final XC_MethodHook newArrMapIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(new ArrayMap<>());
            }
        }
    };
    public static final XC_MethodHook emptyArrMapIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(EMPTY_ARRAYMAP);
            }
        }
    };
    public static final XC_MethodHook newWatchedArrMapIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(XposedHelpers.newInstance(WatchedArrayMapClass));
            }
        }
    };
    public static final XC_MethodHook emptyWatchedArrMapIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(EMPTY_WATCHED_ARRAYMAP);
            }
        }
    };
    public static final XC_MethodHook newArrSetIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(new ArraySet<>());
            }
        }
    };
    public static final XC_MethodHook emptyArrSetIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(EMPTY_ARRAYSET);
            }
        }
    };
    public static final XC_MethodHook newArrListIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(new ArrayList<>());
            }
        }
    };
    public static final XC_MethodHook emptyArrListIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(EMPTY_ARRAYLIST);
            }
        }
    };
    public static final XC_MethodHook newParceledListSliceIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(XposedHelpers.newInstance(ParceledListSliceClass,new Class[]{List.class},new ArrayList<>()));
            }
        }
    };
    public static final XC_MethodHook emptyParceledListSliceIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(EMPTY_PARCELED_LIST_SLICE);
            }
        }
    };
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

    public static final XC_MethodHook getConfusedPackageInfoIfNonSys = new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            int callingUID = Binder.getCallingUid();
            if (!isSystemApp(callingUID)){
                PackageInfo result = (PackageInfo) param.getResult();
                if (result == null){return;}
                if (result.applicationInfo != null){
                    if (nonSysPackages.containsKey(callingUID) && nonSysPackages.get(callingUID) != null){
                        if (result.applicationInfo.uid == callingUID){
                            return;
                        }
                    }
                }
                long seed = ((long) callingUID) <<1 + (long)result.packageName.hashCode();
                if (nonSysPackages.get(callingUID) != null){
                    if (nonSysPackages.get(callingUID).packageName != null){
                        seed += nonSysPackages.get(callingUID).packageName.hashCode();
                    }
                }
                if (result.applicationInfo != null){
                    seed += result.applicationInfo.uid;
                }
                ExtendedRandom extendedRandom = new ExtendedRandom(seed << 2 + seed);
                param.setResult(extendedRandom.confusePackageInfo(result));
            }
        }
    };

    public static final Pair<List,List> EMPTY_LIST_LIST_PAIR = new Pair<>(EMPTY_ARRAYLIST,EMPTY_ARRAYLIST);
    public static final XC_MethodHook emptyListListPairIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int calling = Binder.getCallingUid();
            if (!isSystemApp(calling)){
                param.setResult(EMPTY_LIST_LIST_PAIR);
            }
        }
    };

    public static final XC_MethodHook fakeBluetoothMacAddrIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int callingUid = Binder.getCallingUid();
            AttributionSource attributionSource = null;
            for (Object o:param.args){
                if (o instanceof AttributionSource){
                    attributionSource = (AttributionSource) o;
                }
            }
            if ((attributionSource != null && !isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())) || !isSystemApp(callingUid)){
                ExtendedRandom extendedRandom = new ExtendedRandom(callingUid*2L);
                String pkgName = getPackageName(callingUid);
                if (pkgName != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode() + "bluetooth".hashCode());
                }
                param.setResult(extendedRandom.nextMacAddrStr());
            }
        }
    };
    public static final XC_MethodHook fakeBluetoothNameIfNonSys = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int callingUid = Binder.getCallingUid();
            AttributionSource attributionSource = null;
            for (Object o:param.args){
                if (o instanceof AttributionSource){
                    attributionSource = (AttributionSource) o;
                }
            }
            if ((attributionSource != null && !isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())) || !isSystemApp(callingUid)){
                ExtendedRandom extendedRandom = new ExtendedRandom(callingUid*2L);
                String pkgName = getPackageName(callingUid);
                if (pkgName != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode() + "bluetooth".hashCode());
                }
                param.setResult(extendedRandom.nextString(extendedRandom.nextInt(10)+5));
            }
        }
    };
    public static final XC_MethodHook fakeBluetoothNameIfNonSys_attrSourceSomewhere = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            int callingUid = Binder.getCallingUid();
            AttributionSource attributionSource = null;
            for (Object o:param.args){
                if (o instanceof AttributionSource){
                    attributionSource = (AttributionSource) o;
                }
            }
            if ((attributionSource != null && !isSystemApp(attributionSource.getUid(),attributionSource.getPackageName())) || !isSystemApp(callingUid)){
                ExtendedRandom extendedRandom = new ExtendedRandom(callingUid*2L);
                String pkgName = getPackageName(callingUid);
                if (pkgName != null){
                    extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode() + "bluetooth".hashCode());
                }
                param.setResult(extendedRandom.nextString(extendedRandom.nextInt(10)+5));
            }
        }
    };


    public static final Map<Object,XC_MethodHook> objectIfNonSys_Map = new HashMap<>();
    public static XC_MethodHook objectIfNonSys(Object object){
        if (object == null){
            return nullIfNonSys;
        }
        XC_MethodHook ret = objectIfNonSys_Map.getOrDefault(object,null);
        if (ret != null){
            return ret;
        }
        ret = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (!isSystemApp(Binder.getCallingUid())){
                    param.setResult(object);
                }
            }
        };
        objectIfNonSys_Map.put(object,ret);
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

    public static void hookAllMethodsWithCache_Auto(Class<?> hookClass, String methodName, Object object){
        if (object == null){
            hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,methodName, null);
            return;
        }

        if (object instanceof SimpleExecutorWithMode){
            hookAllMethodsWithCache_executeIfNonSys(hookClass,methodName,(SimpleExecutorWithMode) object);
        }else {
            hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,methodName, object);
        }
    }
    public static void hookAllMethodsWithCache_ReturnObjIfNonSys(Class<?> hookClass, String methodName, Object object){
        if (object != null){
            if (object instanceof XC_MethodHook){
                XposedBridge.hookAllMethods(hookClass,methodName,(XC_MethodHook)object);
                return;
            }
            if (object instanceof SimpleExecutorWithMode){
                hookAllMethodsWithCache_executeIfNonSys(hookClass,methodName,(SimpleExecutorWithMode)object);
                return;
            }
        }
        Boolean[] foundAttrSource = new Boolean[]{false};
        for (Method m:hookClass.getDeclaredMethods()){
            if (!m.getName().equals(methodName)){continue;}
            int index=0;
            foundAttrSource[0] = false;
            for (Class<?> c:m.getParameterTypes()){
                if (c==null){continue;}
                if (Objects.equals(c.getCanonicalName(),AttributionSource.class.getCanonicalName())){
                    foundAttrSource[0] = true;
                    XposedBridge.hookMethod(m,ObjectWithAttrSource(object,index));
                    break;
                }
                index++;
            }
            if (!foundAttrSource[0]){
                XposedBridge.hookMethod(m,objectIfNonSys(object));
            }
        }
    }

    public static void hookAllMethodsWithCache_executeIfNonSys(Class<?> hookClass, String methodName, SimpleExecutorWithMode simpleExecutorWithMode){
        Boolean[] foundAttrSource = new Boolean[]{false};
        for (Method m:hookClass.getDeclaredMethods()){
            if (!m.getName().equals(methodName)){continue;}
            int index=0;
            foundAttrSource[0] = false;
            for (Class<?> c:m.getParameterTypes()){
                if (c==null){continue;}
                if (Objects.equals(c.getCanonicalName(),AttributionSource.class.getCanonicalName())){
                    foundAttrSource[0] = true;
                    XposedBridge.hookMethod(m,simpleExecutorWithAttrSource(simpleExecutorWithMode,index));
                    break;
                }
                index++;
            }
            if (!foundAttrSource[0]){
                XposedBridge.hookMethod(m,simpleExecutorWithoutAttrSource(simpleExecutorWithMode));
            }
        }
    }
}
