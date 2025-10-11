package com.linearity.datservicereplacement.androidhooking.com.android.providers;

import static com.linearity.utils.LoggerUtils.LoggerLog;

import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class HookContentProvider {
    public static void doHook(){
//        classesAndHooks.put(ContentProvider.class.getName(), HookContentProvider::hookContentProvider);
    }

    private static void hookContentProvider(Class<?> hookClass) {
        XposedBridge.hookAllMethods(
                hookClass,
                "insert",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        Uri uri = (Uri) param.args[0];
//                        ContentValues values = (ContentValues) param.args[1];
//                        showAfter.simpleExecutor.execute(param);

                        LoggerLog(new Exception(
//                                "\n" + resultString
//                                        + "\n" + param.method
                                        Arrays.deepToString(param.args)
//                                        + "\n" + Binder.getCallingUid()
//                                        + "\n" + getPackageName(Binder.getCallingUid())
                        ));
                    }
                });
    }
}
