package com.linearity.utils;

import de.robv.android.xposed.XC_MethodHook;

public interface SimpleExecutor {
    public static final int MODE_BEFORE = 1;
    public static final int MODE_AFTER = 2;
    public static final int MODE_BEFORE_AND_AFTER = 3;
    public static final int MODE_BEFORE_NO_CHECK = 4;
    public static final int MODE_AFTER_NO_CHECK = 5;
    public static final int MODE_BEFORE_AND_AFTER_NO_CHECK = 6;
    public void execute(XC_MethodHook.MethodHookParam param);
}
