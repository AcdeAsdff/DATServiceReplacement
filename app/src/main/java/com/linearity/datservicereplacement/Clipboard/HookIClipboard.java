package com.linearity.datservicereplacement.Clipboard;

import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.getPackageName;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.isSystemApp;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Binder;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.SimpleExecutorWithMode;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookIClipboard {

    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){
        Class<?> hookClass;
        hookClass = XposedHelpers.findClassIfExists("com.android.server.clipboard.ClipboardService$ClipboardImpl",lpparam.classLoader);
        if (hookClass != null){
            hookIClipboard(hookClass);
        }
    }

    public static void hookIClipboard(Class<?> hookClass){
        SimpleExecutorWithMode fakeClipData = new SimpleExecutorWithMode(MODE_BEFORE,param->{
            ExtendedRandom extendedRandom = ExtendedRandom.generateBasicFromUid(Binder.getCallingUid(),"IClipboard");
            LoggerLog(new Exception("returning fake clipdata for:" + getPackageName(Binder.getCallingUid()) + Binder.getCallingUid()));

            param.setResult(
                    new ClipData(new ClipDescription("",new String[]{}),
                    new ClipData.Item(extendedRandom.nextString(extendedRandom.nextInt(20)+4))
                    )
            );
        });

//        hookAllMethodsWithCache_Auto(hookClass,"setPrimaryClip",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setPrimaryClipAsPackage",null);
        hookAllMethodsWithCache_Auto(hookClass,"clearPrimaryClip",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPrimaryClip",fakeClipData);
        hookAllMethodsWithCache_Auto(hookClass,"getPrimaryClipDescription",new ClipDescription("",new String[]{}));
        hookAllMethodsWithCache_Auto(hookClass,"hasPrimaryClip",true);
        hookAllMethodsWithCache_Auto(hookClass,"addPrimaryClipChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"removePrimaryClipChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"hasClipboardText",true);
        hookAllMethodsWithCache_Auto(hookClass,"getPrimaryClipSource",null);
        hookAllMethodsWithCache_Auto(hookClass,"areClipboardAccessNotificationsEnabledForUser",true);
        hookAllMethodsWithCache_Auto(hookClass,"setClipboardAccessNotificationsEnabledForUser",null);

    }
}
