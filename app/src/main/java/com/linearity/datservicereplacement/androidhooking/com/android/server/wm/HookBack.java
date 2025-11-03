package com.linearity.datservicereplacement.androidhooking.com.android.server.wm;

import static android.window.BackNavigationInfo.TYPE_CROSS_ACTIVITY;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.newWeakSet;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.isSystemTask;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.wm.HookWindowManagerService.isWindowStateSystem;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.wm.HookWindowManagerService.isWindowStateSystem_uid;
import static com.linearity.utils.HookUtils.listenClass;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE;

import android.window.BackNavigationInfo;

import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookBack {
    private static final long ABILITY_COOLDOWN_MILLISECONDS = 2000;
    private static final int ABILITY_TRIGGER_NEEDS_RETURN_TIMES = 6;
    public static void doHook(){
        classesAndHooks.put("com.android.server.wm.BackNavigationController",HookBack::hookBackNavigationController);
        classesAndHooks.put("android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper",HookBack::hookOnBackInvokedCallbackWrapper);
    }

    private static final String[] mustContainsString = {
            "com.tencent.mobileqq/com.tencent.mobileqq.activity.QPublicFragmentActivity"
    };
    private static void hookBackNavigationController(Class<?> hookClass){

        hookAllMethodsWithCache_Auto(hookClass, "startBackNavigation", new SimpleExecutor() {
            final AtomicLong timeStampLast = new AtomicLong(System.currentTimeMillis());
            final AtomicLong timeStampCooldown = new AtomicLong(System.currentTimeMillis());
            final AtomicInteger triggerCounter = new AtomicInteger(0);
            final Set<Object> affectedWindows = newWeakSet();

            @Override
            public void execute(XC_MethodHook.MethodHookParam param) {
//            BackNavigationInfo info = (BackNavigationInfo) param.getResult();
                long current = System.currentTimeMillis();
                if (current < timeStampCooldown.get()){return;}
                boolean runHideWindowFlag = false;
                long timeDistance = current - timeStampLast.get();
                if (100 < timeDistance && timeDistance < 200){
                    if (triggerCounter.incrementAndGet() % ABILITY_TRIGGER_NEEDS_RETURN_TIMES == 0){
                        triggerCounter.set(0);
                        runHideWindowFlag = true;
                    }
                }

                if (runHideWindowFlag){
//                XposedHelpers.setIntField(info,"mType",TYPE_CROSS_ACTIVITY);
//                LoggerLog("set type");

                    Object/*WindowManagerService*/ wms = XposedHelpers.getObjectField(param.thisObject,"mWindowManagerService");
                    Object/*ActivityTaskManagerService*/ mAtmService = XposedHelpers.getObjectField(wms,"mAtmService");
                    Object/*RootWindowContainer*/ mRoot = XposedHelpers.getObjectField(wms,"mRoot");
                    AtomicReference<Object/*Task*/> taskRef = new AtomicReference<>(null);
                    XposedHelpers.callMethod(mRoot, "forAllRootTasks", new Consumer<Object/*Task*/>() {
                        @Override
                        public void accept(Object task) {
                            boolean isHomeOrRecents = (boolean) XposedHelpers.callMethod(task, "isActivityTypeHomeOrRecents");
//                            boolean isVisible = (boolean) XposedHelpers.callMethod(task, "isVisible");

                            if (!isHomeOrRecents) {
                                if (!isSystemTask(task)){
                                    if (taskRef.get() == null){
                                        taskRef.set(task);
                                    }
//                                    LoggerLog(task);
                                }
                            }
                        }
                    });
                    Object/*Task*/ topTask = taskRef.get();//XposedHelpers.callMethod(mRoot,"getTopDisplayFocusedRootTask");
                    if (topTask == null){
                        return;
                    }
                    Object/*DisplayContent*/ displayContent = XposedHelpers.getObjectField(topTask,"mDisplayContent");
//                Object/*DisplayContent*/ displayContent = XposedHelpers.callMethod(mRoot,"getDisplayContent",new Class[]{int.class},0);
//                Object/*ActivityRecord*/ activityRecord = XposedHelpers.callMethod(topTask, "getTopResumedActivity");
                    {
                        Class<?> toBooleanFuncClass = XposedHelpers.findClass(
                                "com.android.internal.util.ToBooleanFunction",
                                null
                        );
                        LoggerLog("--------------------");
                        AtomicReference<Object> topWindowRef = new AtomicReference<>(null);
                        //sorry guys i didn't get a android sdk jar with com.android.internal.util.ToBooleanFunction
                        Object callback = java.lang.reflect.Proxy.newProxyInstance(
                                toBooleanFuncClass.getClassLoader(),
                                new Class[]{toBooleanFuncClass},
                                (proxy, method, args) -> {
                                    if ("apply".equals(method.getName())) {
                                        Object win = args[0];
                                        Boolean visible = (Boolean) XposedHelpers.callMethod(win, "isVisible");
                                        boolean isTargeted = true;
                                        boolean chosenWindowFlag = topWindowRef.get() == null;
//                                        if (chosenWindowFlag){
//                                            return false;
//                                        }
                                        if (isWindowStateSystem(win)){
                                            return false;
                                        }
                                        boolean visibilityFlag = visible != null && visible || affectedWindows.add(win);
                                        LoggerLog(win + " " + win.getClass() + " " + visibilityFlag + " " + isTargeted);
                                        if (
                                                visibilityFlag
                                                        && chosenWindowFlag
                                                        && isTargeted
                                        ) {
                                            // store top window somewhere (field in outer class)
                                            topWindowRef.set(win);
                                            return true; // stop traversal
                                        }
                                        return false;
                                    }
                                    return null;
                                }
                        );
                        XposedHelpers.callMethod(displayContent, "forAllWindows", callback, true /*top-to-bottom*/);
                        Object topWindow = topWindowRef.get();
                        if (topWindow != null){
                            LoggerLog("topWindow:" + topWindow + "|" + topWindow.getClass());
                            //it would be recycled quickly so sometimes throws exception
                            try {
                                XposedHelpers.callMethod(topWindow, "hide", false, false);
                            }catch (NullPointerException ignore){}
                            timeStampCooldown.set(current + ABILITY_COOLDOWN_MILLISECONDS);
                        }
                    }
                }else{
                    timeStampLast.set(current);
                }

            }
        }, param -> {
            if (param.thisObject == null) {
                return true;//wtf but it can happen
            }
            Object/*WindowManagerService*/ wms = XposedHelpers.getObjectField(param.thisObject, "mWindowManagerService");
            Object/*WindowState*/ windowState = XposedHelpers.callMethod(wms, "getFocusedWindowLocked");
            if (windowState == null) {
                return true;
            }
            int uid = XposedHelpers.getIntField(windowState, "mOwnerUid");
            return isSystemApp(uid);
        });
    }

    private static void hookOnBackInvokedCallbackWrapper(Class<?> hookClass){
//        listenClass(hookClass);
    }
}
