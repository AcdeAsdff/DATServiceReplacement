package com.linearity.datservicereplacement.androidhooking.com.android.server.pm;

import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.hookPackageManager.isSystemApplicationInfo;
import static com.linearity.datservicereplacement.ReturnIfNonSys.mSystemReady;
import static com.linearity.datservicereplacement.StartHook.Computers;
import static com.linearity.datservicereplacement.StartHook.IPackageManagers;
import static com.linearity.datservicereplacement.StartHook.WHITELIST_PACKAGE_NAMES;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.AttributionSource;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Process;
import android.os.UserHandle;
import android.os.WorkSource;
import android.util.ArraySet;

import androidx.annotation.NonNull;

import com.linearity.datservicereplacement.SomeClasses;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.robv.android.xposed.XposedHelpers;

public class PackageManagerUtils {
    public static final PackageInfo EMPTY_PACKAGE_INFO = new PackageInfo(){{
            packageName = "EMPTY";
    }};
    public static final Map<Integer,PackageInfo> nonSysPackages = new ConcurrentHashMap<>();
    public static final Map<Integer,PackageInfo> sysPackages = new ConcurrentHashMap<>();
    public static final int MATCH_FACTORY_ONLY = 0x00200000;
    public static final int MATCH_APEX = 0x40000000;
    public static Object pm = null;
    public static PackageManager fakePM = null;
    public static int UserHandle_SYSTEM = XposedHelpers.getStaticIntField(UserHandle.class,"USER_SYSTEM");

    public static boolean isSystemApp(int callingUID){

        if (callingUID%100000 < 10000){return true;}
        if (sysPackages.containsKey(callingUID)){
            return true;
        }
        if (nonSysPackages.containsKey(callingUID)){
            return false;
        }
        if (!mSystemReady){
            return true;
        }
        if (pm == null){
            tryGetPM();
        }
        if (pm == null && fakePM == null){
            return true;
        }

        if (pm != null){
            String callingPackageName = pm_getName4Uid_noFilter(pm, callingUID);
            if (callingPackageName == null) {
                return true;
            }
            PackageInfo callingPackageInfo = pm_getPackageInfo_noFilter(pm,callingPackageName,callingUID);
            if (callingPackageInfo == null){
//            LoggerLog("callingPackageInfo==null:"+callingUID);
                return true;
            }
            if (callingPackageInfo.applicationInfo == null){
                sysPackages.put(callingUID,EMPTY_PACKAGE_INFO);
//            LoggerLog("applicationInfo==null:"+callingUID);
                return true;
            }
            if (isSystemApplicationInfo(callingPackageInfo.applicationInfo)){
                sysPackages.put(callingUID,EMPTY_PACKAGE_INFO);
                return true;
            }
            if (WHITELIST_PACKAGE_NAMES.contains(callingPackageName)){
//            LoggerLog("isSysApp:"+callingUID);
                sysPackages.put(callingUID,EMPTY_PACKAGE_INFO);
                return true;
            }
            nonSysPackages.put(callingUID,callingPackageInfo);
            return false;
        }else {
            String[] packageNames = fakePM.getPackagesForUid(callingUID);
            if (packageNames == null){
                return true;
            }
            for (String callingPackageName:packageNames){
                PackageInfo callingPackageInfo = null;
                try {
                    callingPackageInfo = fakePM.getPackageInfo(callingPackageName,0);
                }catch (Exception e){
                    continue;
                }
                if (callingPackageInfo == null){
                    continue;
                }
                if (callingPackageInfo.applicationInfo == null){
                    sysPackages.put(callingUID,EMPTY_PACKAGE_INFO);
                    return true;
                }
                if (isSystemApplicationInfo(callingPackageInfo.applicationInfo)){
                    sysPackages.put(callingUID,EMPTY_PACKAGE_INFO);
                    return true;
                }
                if (WHITELIST_PACKAGE_NAMES.contains(callingPackageName)){
                    sysPackages.put(callingUID,EMPTY_PACKAGE_INFO);
                    return true;
                }
                nonSysPackages.put(callingUID,callingPackageInfo);
                return false;
            }
            return true;
        }
    }

    //get package name for uid
    // return "";if not found || isSystemApp
    public static String getPackageName(int callingUid){
//        if (sysPackages.containsKey(callingUid)){return null;}
        if (isSystemApp(callingUid)){return "";}
        PackageInfo nonSysPackageInfo = nonSysPackages.getOrDefault(callingUid,null);
        if (nonSysPackageInfo == null){return "";}
        if (nonSysPackageInfo.packageName == null){return "";}
        return nonSysPackageInfo.packageName;
    }

    public static String getPackageNameIncludingSys(int callingUid){
        return String.valueOf(getPackageSettingFromUid(callingUid));
    }

    public static String pm_getName4Uid_noFilter(Object pm, int callingUID){
        assert pm != null;
        Object computer = XposedHelpers.callMethod(pm,"snapshot");
        return (String) XposedHelpers.callMethod(computer,"getNameForUid",callingUID);
//        return computerEngine_getName4Uid_noFilter(computer,callingUID);
    }

    public static PackageInfo pm_getPackageInfo_noFilter(Object pm, String callingPackageName, int callingUID){
        assert pm != null;
        Object computer = XposedHelpers.callMethod(pm,"snapshot");
//        return computerEngine_getPackageInfoInternalBody_noFilter(computer,callingPackageName,-1,MATCH_APEX, callingUID,UserHandle_SYSTEM);
        return (PackageInfo) XposedHelpers.callMethod(computer,
                "getPackageInfoInternal",
                callingPackageName,
                -1L,
                MATCH_APEX,
                callingUID,
                UserHandle_SYSTEM);
    }

    public static int UserHandleClass_getAppId(int uid){
        return (int) XposedHelpers.callStaticMethod(UserHandle.class,"getAppId",uid);
    }

    public static int UserHandleClass_getUserId(int uid){
        return (int) XposedHelpers.callStaticMethod(UserHandle.class,"getUserId",uid);
    }

    public static String computerEngine_getInstantAppPackageName(Object cEngine, int callingUid) {
        Object mIsolatedOwners = XposedHelpers.getObjectField(cEngine,"mIsolatedOwners");
        return computerEngine_getInstantAppPackageName(cEngine,mIsolatedOwners,callingUid);
    }

    public static String computerEngine_getInstantAppPackageName(Object mSettings, Object mIsolatedOwners, int callingUid) {
        // If the caller is an isolated app use the owner's uid for the lookup.
        boolean isIso = (boolean) XposedHelpers.callStaticMethod(android.os.Process.class,"isIsolated",callingUid);
        try {
            if (isIso && SomeClasses.ComputerEngineSettings_getIsolatedOwner != null) {
                callingUid = (int) SomeClasses.ComputerEngineSettings_getIsolatedOwner.invoke(mSettings,mIsolatedOwners,callingUid);
            }else {
                return "null";
            }
        }catch (Exception e){
            LoggerLog(e);
        }
        final int appId = UserHandleClass_getAppId(callingUid);
        final Object obj = XposedHelpers.callMethod(mSettings,"getSettingBase",appId);
        if (obj == null){
            return null;
        }
        boolean isPackageStateInternalClass;
        if (SomeClasses.PackageStateInternalClass == null){
            isPackageStateInternalClass = obj.getClass().getName().contains("PackageStateInternal");
        }else {
            isPackageStateInternalClass = obj.getClass().isAssignableFrom(SomeClasses.PackageStateInternalClass);
        }
        if (isPackageStateInternalClass) {
//            final PackageStateInternal ps = (PackageStateInternal) obj;
            Object userState = XposedHelpers.callMethod(obj,"getUserStateOrDefault",UserHandleClass_getUserId(callingUid));
            final boolean isInstantApp = (boolean) XposedHelpers.callMethod(userState,"isInstantApp");
            if (isInstantApp){
                Object pkg = XposedHelpers.callMethod(obj,"getPkg");
                return (String) XposedHelpers.callMethod(pkg,"getPackageName");
            }
        }
        return null;
    }

    public static Object computerEngine_getPackageSetting_noFilter(Object cEngine, int uid){
        Object mSettings = XposedHelpers.getObjectField(cEngine,"mSettings");
        if (Process.isIsolatedUid(uid)) {
            try {
                uid = (int) SomeClasses.ComputerEngineSettings_getIsolatedOwner.invoke(cEngine,uid);
            } catch (Exception e) {
                LoggerLog(e);
            }
        }
        final int appId = UserHandleClass_getAppId(uid);
        //mSettings.getSettingBase(appId);

        return XposedHelpers.callMethod(mSettings,"getSettingBase",appId);
    }

    public static String computerEngine_getName4Uid_noFilter(Object cEngine, int uid){
        Object mSettings = XposedHelpers.getObjectField(cEngine,"mSettings");
        if (Process.isIsolatedUid(uid)) {
            try {
                uid = (int) SomeClasses.ComputerEngineSettings_getIsolatedOwner.invoke(cEngine,uid);
            } catch (Exception e) {
                LoggerLog(e);
            }
        }
        final int appId = UserHandleClass_getAppId(uid);
        final Object obj = XposedHelpers.callMethod(mSettings,"getSettingBase",appId);//mSettings.getSettingBase(appId);
        if (obj == null){
            return null;
        }

        boolean isSharedUserSettingClass;
        if (SomeClasses.SharedUserSettingClass == null){
            isSharedUserSettingClass = obj.getClass().getName().contains("SharedUserSetting");
        }else {
            isSharedUserSettingClass = obj.getClass().isAssignableFrom(SomeClasses.SharedUserSettingClass);
        }
        boolean isPackageSettingClass;
        if (SomeClasses.PackageSettingClass == null){
            isPackageSettingClass = obj.getClass().getName().contains("PackageSetting");
        }else {
            isPackageSettingClass = obj.getClass().isAssignableFrom(SomeClasses.PackageSettingClass);
        }

        if (isSharedUserSettingClass) {
            return XposedHelpers.getObjectField(obj,"name") + ":" + XposedHelpers.getObjectField(obj,"mAppId");
        } else if (isPackageSettingClass) {
            return (String) XposedHelpers.callMethod(obj,"getPackageName");//ps.getPackageName();
        }
        return null;
    }

    public static PackageInfo computerEngine_getPackageInfoInternalBody_noFilter(Object cEngine, String packageName, long versionCode,
                                                                                 long flags, int filterCallingUid, int userId) {
        Object mIsolatedOwners = XposedHelpers.getObjectField(cEngine,"mIsolatedOwners");
        Object mSettings = XposedHelpers.getObjectField(cEngine,"mSettings");
        Object mPackages = XposedHelpers.getObjectField(cEngine,"mPackages");
        // reader
        // Normalize package name to handle renamed packages and static libs
        packageName = (String) XposedHelpers.callMethod(cEngine,"resolveInternalPackageName",packageName,versionCode);
        //resolveInternalPackageName(packageName, versionCode);

        final boolean matchFactoryOnly = (flags & MATCH_FACTORY_ONLY) != 0;
        final boolean matchApex = (flags & MATCH_APEX) != 0;
        if (matchFactoryOnly) {
            // Instant app filtering for APEX modules is ignored
            final Object/*PackageStateInternal*/ ps = XposedHelpers.callMethod(mSettings,"getDisabledSystemPkg",packageName);
            //↑mSettings.getDisabledSystemPkg(packageName);


            if (ps != null) {
                Object pkg = XposedHelpers.callMethod(ps,"getPkg");
                if (!matchApex && pkg != null) {
                    boolean isApex = (boolean) XposedHelpers.callMethod(pkg,"isApex");
                    if (isApex){
                        return null;
                    }
                }
                boolean filterSharedLibPackage = (boolean) XposedHelpers.callMethod(cEngine,"filterSharedLibPackage",ps, filterCallingUid, userId, flags);
                if (filterSharedLibPackage) {
                    return null;
                }
                return (PackageInfo) XposedHelpers.callMethod(cEngine,"generatePackageInfo",ps,flags,userId);
            }
        }

        Object/*AndroidPackage*/ p = XposedHelpers.callMethod(mPackages,"get",packageName);
        Object/*var(wtf,i'm programming in java not js!)*/ packageState = XposedHelpers.callMethod(mSettings,"getPackage",packageName);
        if (matchFactoryOnly && p != null) {
            boolean packageState_isSystem = (boolean) XposedHelpers.callMethod(packageState,"isSystem");
            if (!packageState_isSystem){
                return null;
            }
        }
//        if (DEBUG_PACKAGE_INFO) {
//            Log.v(TAG, "getPackageInfo " + packageName + ": " + p);
//        }
        if (p != null) {
            String pName = (String) XposedHelpers.callMethod(p,"getPackageName");
            final Object ps = XposedHelpers.callMethod(cEngine,"getPackageStateInternal",pName);//getPackageStateInternal(p.getPackageName());
            if (!matchApex) {
                boolean isApex = (boolean) XposedHelpers.callMethod(p,"isApex");
                if (isApex){
                    return null;
                }
            }
            boolean filterSharedLibPackage = (boolean) XposedHelpers.callMethod(cEngine,"filterSharedLibPackage",ps, filterCallingUid, userId, flags);
            if (filterSharedLibPackage) {
                return null;
            }
            return (PackageInfo) XposedHelpers.callMethod(cEngine,"generatePackageInfo",ps,flags,userId);
        }
        if (!matchFactoryOnly && (flags & (/*MATCH_KNOWN_PACKAGES*/(0x00002000 | 0x00400000) | /*MATCH_ARCHIVED_PACKAGES*/1L << 32)) != 0) {
            final Object ps = XposedHelpers.callMethod(cEngine,"getPackageStateInternal",packageName);//getPackageStateInternal(p.getPackageName());
            if (ps == null) {
                return null;
            }
            boolean filterSharedLibPackage = (boolean) XposedHelpers.callMethod(cEngine,"filterSharedLibPackage",ps, filterCallingUid, userId, flags);
            if (filterSharedLibPackage) {
                return null;
            }
            return (PackageInfo) XposedHelpers.callMethod(cEngine,"generatePackageInfo",ps,flags,userId);
        }
        return null;
    }

    public static Object/*PackageStateInternal*/ computerEngine_getPackageStateInternal(Object cEngine,
                                                                                        int callingUid) {
        String packageName = computerEngine_getName4Uid_noFilter(cEngine,callingUid);
        if (packageName == null){
            return null;
        }
        return computerEngine_getPackageStateInternal(cEngine,packageName,callingUid);
    }

    public static Object/*PackageStateInternal*/ computerEngine_getPackageStateInternal(Object cEngine, @NonNull String packageName,
                                                                                        int callingUid) {
        try {
            return XposedHelpers.callMethod(cEngine,"getPackageStateInternal",packageName,callingUid);
        } catch (Exception e) {
            LoggerLog(e);
            return null;
        }
    }

    public static Object getPackageSettingFromUid(int callingUid){
        try {
            Object result = null;
            tryGetPM();
            for (Object iPackageManager:IPackageManagers){
                Object computer = XposedHelpers.callMethod(iPackageManager,"snapshot");
                result = computerEngine_getPackageSetting_noFilter(computer,callingUid);
                if (result != null){
                    return result;
                }
            }
            for (Object computer:Computers){
                result = computerEngine_getPackageSetting_noFilter(computer,callingUid);
                if (result != null){
                    return result;
                }
            }
            if (IPackageManagers.isEmpty() && Computers.isEmpty()){
                LoggerLog(new Exception("both IPackageManagers and Computers are empty"));
            }
            return null;
        }catch (ConcurrentModificationException c){
            return getPackageName(callingUid);//again!
        }
    }

    public static boolean getPackageSettingFromUid_isSystem(int callingUid){
        Object callingSetting = getPackageSettingFromUid(callingUid);
        if (callingSetting == null){
            return true;
        }
        if (callingSetting.getClass().getName().equals("com.android.server.pm.SharedUserSetting")){
            Object pkgsWatchedArraySet = XposedHelpers.getObjectField(callingSetting,"mPackages");
            ArraySet<Object> callingPkgSettings = (ArraySet<Object>) XposedHelpers.getObjectField(pkgsWatchedArraySet,"mStorage");
            for (Object singleCallingPkgSetting:callingPkgSettings){
                if ((boolean) XposedHelpers.callMethod(singleCallingPkgSetting,"isSystem")){
                    return true;
                }
            }
//            LoggerLog(callingSetting);
            return false;
        }else {
            return (boolean) XposedHelpers.callMethod(callingSetting,"isSystem");
        }
    }

    public static Object getPackageStateInternalFromUid(int callingUid, int userId){
        Object result;
        for (Object iPackageManager:IPackageManagers){
            Object computer = XposedHelpers.callMethod(iPackageManager,"snapshot");
            result = computerEngine_getPackageStateInternal(computer,callingUid);
            if (result != null){
                return result;
            }
        }
        for (Object computer:Computers){
            result = computerEngine_getPackageStateInternal(computer,callingUid);
            if (result != null){
                return result;
            }
        }
        return null;
    }
    public static void tryGetPMWithContext(Context context){
        if (pm != null){return;}
        if (fakePM != null){return;}
        fakePM = context.getPackageManager();
    }
    public static void tryGetPM(){
        tryGetPM(null);
    }
    public static void tryGetPM(Object caller){
        if (pm != null){return;}
        if (IPackageManagers.isEmpty()){
            if (fakePM == null){
                if (caller instanceof Context c){
                    tryGetPMWithContext(c);
                }else {
                    Application currentApp = AndroidAppHelper.currentApplication();
                    if (currentApp != null){
                        fakePM = currentApp.getPackageManager();
                    }
                }
            }
        }
        else{
            pm = IPackageManagers.toArray()[0];
        }
    }

    public static boolean isSystemApp(String packageName){
        return isSystemApp(packageName, Binder.getCallingUid()/100000);
    }

    public static boolean isSystemApp(WorkSource source){
        int[] mUids = (int[]) XposedHelpers.getObjectField(source,"mUids");
        for (int uid : mUids){
            if (!isSystemApp(uid)){
                return false;
            }
        }
        return true;
    }

    public static boolean isSystemApp(AttributionSource source){
//        LoggerLog(source);
        return isSystemApp_preventStackOverflow(source,new HashSet<>());
    }

    public static boolean isSystemTask(Object taskObj) {
        try {
            ApplicationInfo appInfo = (ApplicationInfo) XposedHelpers.getObjectField(taskObj, "applicationInfo");
            if (appInfo != null) {
                return isSystemApplicationInfo(appInfo);
            }

            int uid = XposedHelpers.getIntField(taskObj, "effectiveUid");
            return isSystemApp(uid);

//            Object actInfo = XposedHelpers.getObjectField(taskObj, "mActivityInfo");
//            if (actInfo != null) {
//                if (isSystemApplicationInfo())
//            }


            // 3️⃣ Check flags
//            int flags = appInfo.flags;
//            return ( (flags & ApplicationInfo.FLAG_SYSTEM) != 0
//                    || (flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0 );
        } catch (Exception e) {
            LoggerLog(e);
            return true;
        }
    }
    public static boolean isSystemApp_preventStackOverflow(AttributionSource source, Set<AttributionSource> checked){
        if (checked.contains(source)){
            return true;
        }
        boolean current = isSystemApp(source.getUid()) && isSystemApp(source.getPackageName());
        AttributionSource next = source.getNext();
        if (next != null){
            current = (current && isSystemApp(next));
        }
        return current;
    }

    public static boolean isSystemApp(String packageName, int userid){
        if (packageName == null){
            return true;
        }
        if (packageName.startsWith("com.android.")){
            return true;
        }
        if (packageName.equals("android")){
            return true;
        }
        if (hookPackageManager.sysPackagesByName.containsKey(packageName)){
            return true;
        }
        if (hookPackageManager.nonSysPackagesByName.containsKey(packageName)){
            return false;
        }
        PackageInfo callingPackageInfo = null;
        if (pm == null){
            if (fakePM == null){
                return true;
            }
            try {
                callingPackageInfo = fakePM.getPackageInfo(packageName,0);
            }catch (Exception e){
                LoggerLog(e);
            }
        }else {
            callingPackageInfo = (PackageInfo) XposedHelpers.callMethod(pm,"getPackageInfo",packageName,0, userid);
        }

        if (callingPackageInfo == null){
            hookPackageManager.sysPackagesByName.put(packageName, EMPTY_PACKAGE_INFO);
            return true;
        }
        if (callingPackageInfo.applicationInfo == null){
            hookPackageManager.sysPackagesByName.put(packageName, EMPTY_PACKAGE_INFO);
            return true;
        }
        if (isSystemApplicationInfo(callingPackageInfo.applicationInfo)){
            sysPackages.put(callingPackageInfo.applicationInfo.uid, EMPTY_PACKAGE_INFO);
            hookPackageManager.sysPackagesByName.put(packageName, EMPTY_PACKAGE_INFO);
            return true;
        }

        if (WHITELIST_PACKAGE_NAMES.contains(packageName)
        ){
            hookPackageManager.sysPackagesByName.put(packageName, EMPTY_PACKAGE_INFO);
            sysPackages.put(callingPackageInfo.applicationInfo.uid, EMPTY_PACKAGE_INFO);
            return true;
        }
        nonSysPackages.put(callingPackageInfo.applicationInfo.uid,callingPackageInfo);
//        LoggerLog(new Exception(nonSysPackages.get(callingUID).packageName));
        return false;
    }
}
