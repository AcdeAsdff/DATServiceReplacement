package com.linearity.datservicereplacement.Context;

import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.Phone.GsmCdmaPhoneConstructor.DEVICE_ID;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;

import com.linearity.datservicereplacement.Connectivity.HookConnectivityManager;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;
import com.linearity.utils.SystemAppChecker;

import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookContextImpl {
    public static final SystemAppChecker contextImplSysAppChecker = param -> {
        Context thisObj = (Context) param.thisObject;
        return isSystemApp(thisObj.getPackageName()) && isSystemApp(Binder.getCallingUid());
    };

    public static void doHook() {
        classesAndHooks.put("android.app.ContextImpl", HookContextImpl::hookContextImpl);
    }

    public static void hookContextImpl(Class<?> hookClass) {
//        hookAllMethodsWithCache_Auto(hookClass, "startActivity",(SimpleExecutor) param -> {
//            LoggerLog(new Exception("trying to start activity:"+Arrays.deepToString(param.args)));
//        }
//        );
//        hookAllMethodsWithCache_Auto(hookClass, "checkSelfPermission",(SimpleExecutor) param -> {
//            LoggerLog(new Exception("checking self perm:"+Arrays.deepToString(param.args)));
//        });
//        hookAllMethodsWithCache_Auto(hookClass, "enforce", new SimpleExecutorWithMode(MODE_AFTER, param -> {
//            Throwable throwable = param.getThrowable();
//            if (throwable == null) {
//                return;
//            }
//            if (throwable instanceof SecurityException) {//dont tell them that they have SecurityException
////                param.setThrowable(new Throwable(throwable));
//            }
////            LoggerLog(throwable);
//        }),contextImplSysAppChecker);

//        hookAllMethodsWithCache_Auto(hookClass, "checkPermission", (SimpleExecutor) param -> {
//            Exception stackShower = new Exception(Binder.getCallingUid() + "checkPermission" + Arrays.deepToString(param.args));
//            for (StackTraceElement s : stackShower.getStackTrace()) {
//                //no enforce,no check
//                if (String.valueOf(s).contains("enforce")) {
//                    return;
//                }
//            }
////            showBefore.simpleExecutor.execute(param);
////            param.setResult(PackageManager.PERMISSION_GRANTED);
//        },contextImplSysAppChecker);
    }

}
