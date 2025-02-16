package com.linearity.datservicereplacement.Wallpaper;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.defaultSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.defaultSystemCheckerReversed;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;

import android.app.WallpaperColors;
import android.graphics.Color;

import com.linearity.datservicereplacement.Clipboard.HookIClipboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookWallpaper {
    public static void doHook(){
        classesAndHooks.put("com.android.server.wallpaper.WallpaperManagerService", HookWallpaper::hookIWallpaperManager);
    }
    //TODO:Randomize
    public static void hookIWallpaperManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"setWallpaper",null,getSystemChecker_PackageNameAt(1));//ParcelFileDescriptor
        hookAllMethodsWithCache_Auto(hookClass,"setWallpaperComponentChecked",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setWallpaperComponent",null);
        hookAllMethodsWithCache_Auto(hookClass,"getWallpaper",null,getSystemChecker_PackageNameAt(0));//ParcelFileDescriptor
        hookAllMethodsWithCache_Auto(hookClass,"getWallpaperWithFeature",null,getSystemChecker_PackageNameAt(0));//ParcelFileDescriptor
        hookAllMethodsWithCache_Auto(hookClass,"getBitmapCrops",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getFutureBitmapCrops",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getBitmapCrop",null);//Rect
        hookAllMethodsWithCache_Auto(hookClass,"getWallpaperIdForUser",0);
        hookAllMethodsWithCache_Auto(hookClass,"getWallpaperInfo",null);//WallpaperInfo
        hookAllMethodsWithCache_Auto(hookClass,"getWallpaperInfoWithFlags",null);//WallpaperInfo
        hookAllMethodsWithCache_Auto(hookClass,"getWallpaperInfoFile",null);//ParcelFileDescriptor
        hookAllMethodsWithCache_Auto(hookClass,"clearWallpaper",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"hasNamedWallpaper",true);
        hookAllMethodsWithCache_Auto(hookClass,"setDimensionHints",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getWidthHint",0);
        hookAllMethodsWithCache_Auto(hookClass,"getHeightHint",0);
        hookAllMethodsWithCache_Auto(hookClass,"setDisplayPadding",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getName","");//String
        hookAllMethodsWithCache_Auto(hookClass,"settingsRestored",null);
        hookAllMethodsWithCache_Auto(hookClass,"isWallpaperSupported",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isSetWallpaperAllowed",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"isWallpaperBackupEligible",true);
        hookAllMethodsWithCache_Auto(hookClass,"getWallpaperColors",null);//WallpaperColors
        hookAllMethodsWithCache_Auto(hookClass,"removeOnLocalColorsChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"addOnLocalColorsChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerWallpaperColorsCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterWallpaperColorsCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"setInAmbientMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyWakingUp",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyGoingToSleep",null);
        hookAllMethodsWithCache_Auto(hookClass,"setWallpaperDimAmount",null);
        hookAllMethodsWithCache_Auto(hookClass,"getWallpaperDimAmount",0.f);
        hookAllMethodsWithCache_Auto(hookClass,"lockScreenWallpaperExists",true);
        hookAllMethodsWithCache_Auto(hookClass,"isStaticWallpaper",true);
    }

}
