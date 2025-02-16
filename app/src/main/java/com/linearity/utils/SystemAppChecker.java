package com.linearity.utils;

import de.robv.android.xposed.XC_MethodHook;

public interface SystemAppChecker {

    boolean checkSystemApp(XC_MethodHook.MethodHookParam param);
}
