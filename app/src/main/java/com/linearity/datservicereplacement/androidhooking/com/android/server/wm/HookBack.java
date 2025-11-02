package com.linearity.datservicereplacement.androidhooking.com.android.server.wm;

import static android.window.BackNavigationInfo.TYPE_CROSS_ACTIVITY;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findArgByClassInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.newWeakSet;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.isSystemApp;
import static com.linearity.utils.HookUtils.listenClass;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.os.RemoteCallback;
import android.view.IWindowManager;
import android.window.BackAnimationAdapter;
import android.window.BackNavigationInfo;
import android.window.IOnBackInvokedCallback;

import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;
import com.linearity.utils.SystemAppChecker;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HookBack {
    public static void doHook(){
        classesAndHooks.put("com.android.server.wm.BackNavigationController",HookBack::hookBackNavigationController);
        classesAndHooks.put("android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper",HookBack::hookOnBackInvokedCallbackWrapper);
    }

    private static void hookBackNavigationController(Class<?> hookClass){
        AtomicLong timeStampLast = new AtomicLong(System.currentTimeMillis());
        hookAllMethodsWithCache_Auto(hookClass,"startBackNavigation",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            BackNavigationInfo info = (BackNavigationInfo) param.getResult();
            long current = System.currentTimeMillis();
            if (current - timeStampLast.get() < 300){
                XposedHelpers.setIntField(info,"mType",TYPE_CROSS_ACTIVITY);
//                LoggerLog("set type");
            }
            timeStampLast.set(current);

        }), param -> {
            Object/*WindowManagerService*/ wms = XposedHelpers.getObjectField(param.thisObject,"mWindowManagerService");
            Object/*WindowState*/ windowState = XposedHelpers.callMethod(wms,"getFocusedWindowLocked");
            int uid = XposedHelpers.getIntField(windowState,"mOwnerUid");
            return isSystemApp(uid);
        });
    }

    private static void hookOnBackInvokedCallbackWrapper(Class<?> hookClass){
        listenClass(hookClass);
    }
}
