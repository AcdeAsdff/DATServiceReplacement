package com.linearity.datservicereplacement.androidhooking.android.os;

import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.pm;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.hookPackageManager.hookGetPackageInfo;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.IPackageManagers;
import static com.linearity.datservicereplacement.StartHook.alreadyGotServiceClass;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.isPublicHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.listenServiceMap;

import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.content.Context;

import com.linearity.datservicereplacement.androidhooking.com.android.server.am.HookAMS;
import com.linearity.utils.ClassHookExecutor;
import com.linearity.utils.ServiceHooker;
import com.linearity.utils.SimpleExecutorWithMode;

import java.util.Collection;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class HookServiceManager {

    public static void doHook(){
        ClassHookExecutor executor = HookServiceManager::hookServiceManager;
        classesAndHooks.put("android.os.ServiceManager", executor);
        classesAndHooks.put("android.os.ServiceManagerNative", executor);
    }

    public static void hookServiceManager(Class<?> hookClass){
        if (isPublicHookedPoolRegistered(hookClass)){return;}
        XposedBridge.hookAllMethods(hookClass, "addService", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (param.args[0]==null){
                    return;
                }
                if (param.args[0].equals("package")){
                    hookGetPackageInfo(param.args[1].getClass());
                    if (pm == null){
                        pm = param.thisObject;
                    }
                    IPackageManagers.add(param.args[1]);
                    return;
                }
                else if(param.args[0].equals(Context.ACTIVITY_SERVICE)){//the real threat
                    HookAMS.hookSystemReady(param.args[1].getClass());
                }

                LoggerLog("[linearity-datsr-addservice]",param.args[0] + "|" + param.args[1]);
            }

        });
        hookAllMethodsWithCache_Auto(hookClass,"checkService",new SimpleExecutorWithMode(MODE_AFTER, param ->{
            String name = (String) param.args[0];
            Object service = param.getResult();
            if (service == null){
                return;
            }
            Class<?> serviceClass = service.getClass();
            Collection<ServiceHooker> hookers = listenServiceMap.get(name);
            for (ServiceHooker hooker:hookers){
                Collection<Class<?>> hooked = alreadyGotServiceClass.get(hooker);
                if (hooked.contains(serviceClass)) {
                    continue;
                }
                hooker.transactService(serviceClass);
                alreadyGotServiceClass.put(hooker,serviceClass);
            }
        }),noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"getService",new SimpleExecutorWithMode(MODE_AFTER, param ->{
            String name = (String) param.args[0];
            Object service = param.getResult();
            if (service == null){
                return;
            }
            Class<?> serviceClass = service.getClass();
            Collection<ServiceHooker> hookers = listenServiceMap.get(name);
            for (ServiceHooker hooker:hookers){
                Collection<Class<?>> hooked = alreadyGotServiceClass.get(hooker);
                if (hooked.contains(serviceClass)) {
                    continue;
                }
                hooker.transactService(serviceClass);
                alreadyGotServiceClass.put(hooker,serviceClass);
            }
        }),noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"get",new SimpleExecutorWithMode(MODE_AFTER, param ->{
            String name = (String) param.args[0];
            Object service = param.getResult();
            if (service == null){
                return;
            }
            Class<?> serviceClass = service.getClass();
            Collection<ServiceHooker> hookers = listenServiceMap.get(name);
            for (ServiceHooker hooker:hookers){
                Collection<Class<?>> hooked = alreadyGotServiceClass.get(hooker);
                if (hooked.contains(serviceClass)) {
                    continue;
                }
                hooker.transactService(serviceClass);
                alreadyGotServiceClass.put(hooker,serviceClass);
            }
        }),noSystemChecker);
    }
}
