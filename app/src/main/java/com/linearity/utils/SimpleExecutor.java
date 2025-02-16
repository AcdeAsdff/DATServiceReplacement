package com.linearity.utils;

import de.robv.android.xposed.XC_MethodHook;

public interface SimpleExecutor {
    int MODE_BEFORE = 1;
    int MODE_AFTER = 2;
    int MODE_BEFORE_AND_AFTER = 3;
    int MODE_BEFORE_NO_CHECK = 4;
    int MODE_AFTER_NO_CHECK = 5;
    int MODE_BEFORE_AND_AFTER_NO_CHECK = 6;
    void execute(XC_MethodHook.MethodHookParam param);
}
