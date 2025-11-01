package com.linearity.datservicereplacement.androidhooking.com.android.systemui;

import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import com.linearity.utils.SimpleExecutorWithMode;

import de.robv.android.xposed.XposedHelpers;

public class HookSystemUIApplication {

    public static void doHook(){
        classesAndHooks.put("com.android.systemui.SystemUIApplication",HookSystemUIApplication::hookSystemUIApplication);
    }

    private static void hookSystemUIApplication(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onCreate",new SimpleExecutorWithMode(MODE_AFTER,param -> {
            showAfter.simpleExecutor.execute(param);
            for (String className:classesAndHooks.keys()){
                if (!className.startsWith("com.android.systemui")){
                    continue;
                }
                try {
                    Class<?> cls = hookClass.getClassLoader().loadClass(className);
//                    if (cls != null){
//                        LoggerLog(new Exception("found:com.android.systemui.statusbar.window.StatusBarWindowView"));
//                    }else {
//                        LoggerLog(new Exception("not found:com.android.systemui.statusbar.window.StatusBarWindowView"));
//                    }
                }catch (ClassNotFoundException clsNotFound){
                    //pass
                }
                catch (Exception e){
                    LoggerLog(e);
                }
            }

        }),noSystemChecker);
    }
}
