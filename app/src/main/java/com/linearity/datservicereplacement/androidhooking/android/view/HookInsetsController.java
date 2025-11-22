package com.linearity.datservicereplacement.androidhooking.android.view;

import static android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE;
import static android.view.WindowManager.LayoutParams.TYPE_STATUS_BAR;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import com.linearity.datservicereplacement.androidhooking.com.android.server.wm.HookWindowManagerService;
import com.linearity.utils.SimpleExecutor;

import de.robv.android.xposed.XposedHelpers;

public class HookInsetsController {

    //all doesn't work?
    public static void doHook(){
        classesAndHooks.put("android.view.InsetsController", HookInsetsController::hookInsetsController);
        classesAndHooks.put("com.android.server.wm.InsetsPolicy", HookInsetsController::hookInsetsPolicy);

    }

    public static void hookInsetsController(Class<?> hookClass){

        hookAllMethodsWithCache_Auto(hookClass,"hide",(SimpleExecutor)param -> {
            int types = (int) param.args[0];
            types |= WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars();
            param.args[0]=types;
            XposedHelpers.callMethod(param.thisObject,"setSystemBarsBehavior", BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        },noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass, "setSystemBarsBehavior", (SimpleExecutor) param -> {
            param.args[0] = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE;
        }, noSystemChecker);
    }
    public static void hookInsetsPolicy(Class<?> hookClass){

        hookAllMethodsWithCache_Auto(hookClass, "hasHiddenSources", (SimpleExecutor) param -> {
            int types = (int) param.args[0];

            // 如果检查的是导航栏或状态栏
            if ((types & WindowInsets.Type.navigationBars()) != 0
                    || (types & WindowInsets.Type.statusBars()) != 0) {

                // 获取当前 WindowState
                Object win = XposedHelpers.getObjectField(param.thisObject, "mDisplayContent"); // 或 mFocusedWindow
                // 判断是否是 StatusBar 窗口
                if (win != null) {
                    Object topWindow = XposedHelpers.getObjectField(win, "mCurrentFocus");
                    if (topWindow != null) {
                        WindowManager.LayoutParams params = (WindowManager.LayoutParams) XposedHelpers.getObjectField(topWindow,"mAttrs");
                        if (params.type == WindowManager.LayoutParams.TYPE_STATUS_BAR) {
                            // StatusBar 窗口返回 true，认为隐藏导航栏（immersive）
                            param.setResult(true);
                        }
                    }
                }

            }
        }, noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass, "updateSystemBars", (SimpleExecutor) param -> {
            param.args[1] = false;
            param.args[2] = false;
            // WindowState 对象
            Object win = param.args[0];

            // 获取 window.mAttrs.insetsFlags.behavior
            WindowManager.LayoutParams attrs = (WindowManager.LayoutParams) XposedHelpers.getObjectField(win, "mAttrs");
            attrs.insetsFlags.behavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE;

        }, noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass, "remoteInsetsControllerControlsSystemBars", (SimpleExecutor) param -> {
            Object focusedWin = param.args[0];
            if (focusedWin != null) {
                // 获取 window 类型
                WindowManager.LayoutParams attrs = (WindowManager.LayoutParams) XposedHelpers.getObjectField(focusedWin, "mAttrs");
                int type = attrs.type;

            }
            param.setResult(false); // 强制返回 false
        }, noSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass, "showTransient", (SimpleExecutor) param -> {

            int types = (int) param.args[0];
            boolean isGesture = (boolean) param.args[1];

            // 1. 确保下拉手势显示系统栏
            if (isGesture) {
                // 可以保持原样，不修改 types
                return;
            }

            // 2. 强制隐藏状态栏和导航栏（不响应非手势触发）
            types &= ~(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            param.args[0] = types;

        }, noSystemChecker);
    }
}
