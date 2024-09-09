package com.linearity.datservicereplacement.PackageManager;

import static com.linearity.datservicereplacement.ReturnIfNonSys.mSystemReady;
import static com.linearity.datservicereplacement.StartHook.IPackageManagers;
import static com.linearity.datservicereplacement.StartHook.WHITELIST_PACKAGE_NAMES;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.LoggerUtils.showObjectFields;
import static com.linearity.utils.ReturnReplacements.getRandomString;

import android.app.AndroidAppHelper;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.Attribution;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.util.SparseArray;

import androidx.annotation.Nullable;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.FakeClass.java.util.CantUseArrayList;
import com.linearity.utils.NotFinished;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class hookIPackageManager {
    public static final PackageInfo EMPTY_PACKAGE_INFO = new PackageInfo();
    {
        EMPTY_PACKAGE_INFO.packageName = "EMPTY";
    }
    public static Object pm = null;
    
    public static Class<?> ParceledListSlice;//android.content.pm.ParceledListSlice
    private static final Set<Class<?>> hooked = new HashSet<>();
    public static final Map<Integer,PackageInfo> nonSysPackages = new ConcurrentHashMap<>();
    public static final Map<Integer,PackageInfo> sysPackages = new ConcurrentHashMap<>();
    public static final Map<String,PackageInfo> nonSysPackagesByName = new ConcurrentHashMap<>();
    public static final Map<String,PackageInfo> sysPackagesByName = new ConcurrentHashMap<>();
    public static void hookGetPackageInfo(Class<?> /*android.content.pm.IPackageManager*/ packageService) {
        if (hooked.contains(packageService)){return;}
        hooked.add(packageService);
        XposedBridge.hookAllMethods(packageService,
                "getInstalledApplicationsListInternal",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if (pm == null){
                            pm = param.thisObject;
                        }
                        int callingUID = (int)param.args[2];
                        if (callingUID == 1000 || callingUID == 1001){
                            return;
                        }
                        String callingPackageName = (String) XposedHelpers.callMethod(param.thisObject,"getNameForUid",callingUID);
                        if (callingPackageName == null){
                            return;
                        }
                        PackageInfo callingPackageInfo = (PackageInfo) XposedHelpers.callMethod(param.thisObject,"getPackageInfo",callingPackageName,0,(int)param.args[1]);
                        if (callingPackageInfo == null){
                            return;
                        }
                        if (callingPackageInfo.packageName.equals("com.android.launcher3") || callingPackageInfo.packageName.equals("com.google.android.googlequicksearchbox")){
                            sysPackages.put(callingUID,EMPTY_PACKAGE_INFO);
                            sysPackagesByName.put(callingPackageName,EMPTY_PACKAGE_INFO);
                            return;
                        }
                        nonSysPackages.put(callingUID,callingPackageInfo);
                        if (callingPackageInfo.packageName != null) {
                            nonSysPackagesByName.put(callingPackageInfo.packageName, callingPackageInfo);
                        }
                        if ((boolean) XposedHelpers.callMethod(callingPackageInfo.applicationInfo,"isSystemApp")){
                            return;
                        }
//                        LoggerLog("[linearity-getInstalledApplicationsListInternal]",callingPackageInfo.packageName);
                        List<ApplicationInfo> result = (List<ApplicationInfo>) param.getResult();
                        for (ApplicationInfo appInfo:result){
                            if (!appInfo.packageName.equals(callingPackageName)){
                                confuseApplicationInfo(appInfo,new ExtendedRandom(callingPackageName.hashCode() + appInfo.hashCode()));
                            }
                        }
                    }
                });
    }

    public static boolean isSystemApp(int callingUID,String... packageNames){
        if (callingUID%100000 < 10000){return true;}
        if (sysPackages.containsKey(callingUID)){
//            LoggerLog(new Exception(callingUID + Arrays.toString(packageNames)));
            return true;
        }
        if (nonSysPackages.containsKey(callingUID)){
            return false;
        }
        if (!mSystemReady){
            return true;
        }
        if (pm == null){
            if (IPackageManagers.isEmpty()){
                {
                    PackageManager packageManager = AndroidAppHelper.currentApplication().getPackageManager();
                    try {
                        if (packageNames.length == 0 || packageNames == null){
                            packageNames = packageManager.getPackagesForUid(callingUID);
                        }
                        if (packageNames == null){return true;}
                        for (String packageName:packageNames){
                            PackageInfo callingPackageInfo = packageManager.getPackageInfo(packageName, 0);
                            {
                                if (callingPackageInfo == null) {
                                    continue;//return true;
                                }
                                if (callingPackageInfo.applicationInfo == null) {
                                    sysPackages.put(callingUID, EMPTY_PACKAGE_INFO);
                                    continue;//return true;
                                }
                                if ((boolean) XposedHelpers.callMethod(callingPackageInfo.applicationInfo, "isSystemApp")) {
                                    sysPackages.put(callingUID, EMPTY_PACKAGE_INFO);
                                    continue;//return true;
                                }
                                if (WHITELIST_PACKAGE_NAMES.contains(packageName)) {
                                    sysPackages.put(callingUID, EMPTY_PACKAGE_INFO);
                                    continue;//return true;
                                }
                                nonSysPackages.put(callingUID, callingPackageInfo);
                                return false;
                            }
                        }
                        return true;
                    }catch (Exception e){
                        LoggerLog(e);
                    }
                }
                return true;
            }
            pm = IPackageManagers.toArray()[0];
        }
        String callingPackageName = (String) XposedHelpers.callMethod(pm,"getNameForUid",callingUID);
        if (callingPackageName == null){
            return true;
        }
        PackageInfo callingPackageInfo = (PackageInfo) XposedHelpers.callMethod(pm,"getPackageInfo",callingPackageName,0, callingUID/100000);
        if (callingPackageInfo == null){
//            LoggerLog("callingPackageInfo==null:"+callingUID);
            return true;
        }
        if (callingPackageInfo.applicationInfo == null){
            sysPackages.put(callingUID,EMPTY_PACKAGE_INFO);
//            LoggerLog("applicationInfo==null:"+callingUID);
            return true;
        }
        if ((boolean) XposedHelpers.callMethod(callingPackageInfo.applicationInfo,"isSystemApp")){
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
    }
    public static boolean isSystemApp(String packageName,int userid){
        if (packageName == null){
            return true;
        }
        if (packageName.equals("android")){
            return true;
        }
        if (nonSysPackagesByName.containsKey(packageName)){
            return false;
        }
        if (sysPackagesByName.containsKey(packageName)){
            return false;
        }

        PackageInfo callingPackageInfo = (PackageInfo) XposedHelpers.callMethod(pm,"getPackageInfo",packageName,0, userid);
        if (callingPackageInfo == null){
            sysPackagesByName.put(packageName,EMPTY_PACKAGE_INFO);
            return true;
        }
        if (callingPackageInfo.applicationInfo == null){
            sysPackagesByName.put(packageName,EMPTY_PACKAGE_INFO);
            return true;
        }
        if ((boolean) XposedHelpers.callMethod(callingPackageInfo.applicationInfo,"isSystemApp")){

            if (WHITELIST_PACKAGE_NAMES.contains(packageName)
            ){
                sysPackagesByName.put(packageName,EMPTY_PACKAGE_INFO);
                sysPackages.put(callingPackageInfo.applicationInfo.uid,EMPTY_PACKAGE_INFO);
                return true;
            }
            sysPackages.put(callingPackageInfo.applicationInfo.uid,EMPTY_PACKAGE_INFO);
            sysPackagesByName.put(packageName,EMPTY_PACKAGE_INFO);
            return true;
        }
        nonSysPackages.put(callingPackageInfo.applicationInfo.uid,callingPackageInfo);
//        LoggerLog(new Exception(nonSysPackages.get(callingUID).packageName));
        return false;
    }


    public static boolean isAccessingSelf(int callingUID,int accessingUID){
        if (callingUID < 10000){
            return true;
        }
        return accessingUID == callingUID;
    }
    public static boolean isAccessingSelf(int callingUID,String accessingPackageName){
        if (callingUID < 10000){
            return true;
        }
        if (nonSysPackagesByName.containsKey(accessingPackageName)){
            if (nonSysPackagesByName.get(accessingPackageName) != null){
                if (nonSysPackagesByName.get(accessingPackageName).applicationInfo != null){
                    return callingUID == nonSysPackagesByName.get(accessingPackageName).applicationInfo.uid;
                }
            }
        }
        return false;
    }

    //get package name for uid
    // return "";if not found || isSystemApp
    public static String getPackageName(int callingUid){
        if (isSystemApp(callingUid)){
            return "";
        }
        if (nonSysPackages.containsKey(callingUid)){
            if (nonSysPackages.get(callingUid) != null){
                if (nonSysPackages.get(callingUid).packageName != null){
                    return nonSysPackages.get(callingUid).packageName;
                }
            }
        }
        return "";
    }

    /**
     when return true:do not show
     */
    public static boolean IThinkShouldFilterApplication(int callingUid, @Nullable Object /*SettingBase*/ callingSetting,
                                           Object /*PackageSetting*/ targetPkgSetting, int userId){
        if (callingUid==1000 || callingUid==1001 || callingSetting == null || targetPkgSetting == null){return false;}

        if (callingSetting.toString().contains("PackageSetting")){
            if ((boolean)XposedHelpers.callMethod(callingSetting,"isSystem")){
                return false;
            }
            String callingPackageName = callingSetting.toString().split(" ")[1].split("/")[0];
            String targetPackageName = targetPkgSetting.toString().split(" ")[1].split("/")[0];
            if (targetPackageName.equals(callingPackageName)){
                return false;
            }
            if (!callingPackageName.contains("lineage") && targetPackageName.contains("lineage")){
                return true;
            }
            /**
             * expose some package so that i can login with them.
             * (i hate monopoly capital :( )
             */
            if (targetPackageName.contains("com.tencent.mm")
                    || targetPackageName.contains("com.tencent.tim")
                    || targetPackageName.startsWith("com.alipay")
            ){
                return false;
            }

            if ((boolean)XposedHelpers.callMethod(targetPkgSetting,"isSystem")){
                return false;
            }

            return true;
        }
        return false;
    }

    public static final ActivityInfo[] emptyActivityInfo = new ActivityInfo[0];
    public static final ProviderInfo[] emptyProviderInfo = new ProviderInfo[0];
    public static final InstrumentationInfo[] emptyInstrumentationInfo = new InstrumentationInfo[0];
    public static final PermissionInfo[] emptyPermissionInfo = new PermissionInfo[0];
    public static final Attribution[] emptyAttributions = new Attribution[0];
    public static final Signature[] emptySignatures = new Signature[0];
    public static final ConfigurationInfo[] emptyConfigurationInfos = new ConfigurationInfo[0];
    public static final FeatureInfo[] emptyFeatureInfo = new FeatureInfo[0];
    public static final FeatureGroupInfo[] emptyFeatureGroupInfo = new FeatureGroupInfo[0];
    public static PackageInfo confusePackagePath(PackageInfo toConfuse, Class<?> signingDetailClass){

        return toConfuse;
    }
    @NotFinished
    public static ApplicationInfo confuseApplicationInfo(ApplicationInfo toConfuse, ExtendedRandom random){
//        ExtendedRandom random = new ExtendedRandom(Math.abs(toConfuse.packageName.hashCode()));
//        XposedHelpers.setObjectField(toConfuse,"targetActivity",null);
//        if (toConfuse.packageName.startsWith("com.tencent.")){
//            for (Field f:toConfuse.getClass().getFields()){
//                if (Modifier.isStatic(f.getModifiers())){continue;}
//                LoggerLog(f.getName() + ":" + XposedHelpers.getObjectField(toConfuse,f.getName()));
//            }
//            LoggerLog("----------------");
//        }
        XposedHelpers.setObjectField(toConfuse,"processName",random.nextString(20));
        XposedHelpers.setObjectField(toConfuse,"permission",random.nextString(20));
        XposedHelpers.setObjectField(toConfuse,"className",random.nextString(20));
        XposedHelpers.setObjectField(toConfuse,"descriptionRes",0);
        XposedHelpers.setObjectField(toConfuse,"theme",0);
        XposedHelpers.setObjectField(toConfuse,"manageSpaceActivityName",null);
        XposedHelpers.setObjectField(toConfuse,"backupAgentName",null);
        XposedHelpers.setObjectField(toConfuse,"dataExtractionRulesRes",0);
        XposedHelpers.setObjectField(toConfuse,"crossProfile",false);
        XposedHelpers.setObjectField(toConfuse,"uiOptions",0);
        XposedHelpers.setObjectField(toConfuse,"nonLocalizedLabel",null);
        XposedHelpers.setObjectField(toConfuse,"metaData",null);
        XposedHelpers.setObjectField(toConfuse,"zygotePreloadName",random.nextString(random.nextInt(20)+5));
        XposedHelpers.setObjectField(toConfuse,"showUserIcon",(random.nextBoolean()?-1:1)*random.nextInt(10001));
        XposedHelpers.setObjectField(toConfuse,"logo",(random.nextBoolean()?-1:1)*random.nextInt(10001));
        XposedHelpers.setObjectField(toConfuse,"labelRes",(random.nextBoolean()?-1:1)*random.nextInt(Integer.MAX_VALUE-5));
        XposedHelpers.setObjectField(toConfuse,"flags",random.nextInt(Integer.MAX_VALUE));
        XposedHelpers.setObjectField(toConfuse,"requiresSmallestWidthDp",0);
        XposedHelpers.setObjectField(toConfuse,"compatibleWidthLimitDp",0);
        XposedHelpers.setObjectField(toConfuse,"largestWidthLimitDp", Integer.MAX_VALUE);
        XposedHelpers.setObjectField(toConfuse,"maxAspectRatio",Float.MAX_VALUE);
        XposedHelpers.setObjectField(toConfuse,"minAspectRatio",0.f);
        XposedHelpers.setObjectField(toConfuse,"volumeUuid", random.nextUUID().toString());
        XposedHelpers.setObjectField(toConfuse,"storageUuid",random.nextUUID());
        XposedHelpers.setObjectField(toConfuse,"sourceDir",random.nextString(50));

        XposedHelpers.setObjectField(toConfuse,"publicSourceDir",random.nextString(50));
        XposedHelpers.setObjectField(toConfuse,"scanPublicSourceDir",random.nextString(50));
        XposedHelpers.setObjectField(toConfuse,"splitPublicSourceDirs",new String[]{random.nextString(random.nextInt(10)+5),random.nextString(random.nextInt(10)+5)});

        XposedHelpers.setObjectField(toConfuse,"scanSourceDir",random.nextString(50));
        XposedHelpers.setObjectField(toConfuse,"splitNames",new String[]{random.nextString(random.nextInt(10)+5),random.nextString(random.nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"splitSourceDirs",new String[]{random.nextString(random.nextInt(10)+5),random.nextString(random.nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"splitDependencies",new SparseArray<int[]>(0));
        XposedHelpers.setObjectField(toConfuse,"resourceDirs",new String[]{random.nextString(random.nextInt(10)+5),random.nextString(random.nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"overlayPaths",new String[]{random.nextString(random.nextInt(10)+5),random.nextString(random.nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"seInfo",random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"seInfoUser",random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"sharedLibraryFiles",random.nextBoolean()?null:new String[]{random.nextString(random.nextInt(10)+5),random.nextString(random.nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"sharedLibraryInfos", random.nextBoolean()?null:CantUseArrayList.INSTANCE);

//        XposedHelpers.setObjectField(toConfuse,"dataDir", random.getRandomString(random.nextInt(10)+5));
//        XposedHelpers.setObjectField(toConfuse,"deviceProtectedDataDir", random.getRandomString(random.nextInt(10)+5));
//        XposedHelpers.setObjectField(toConfuse,"credentialProtectedDataDir", random.getRandomString(random.nextInt(10)+5));

        XposedHelpers.setObjectField(toConfuse,"nativeLibraryDir", random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"secondaryNativeLibraryDir", random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"nativeLibraryRootRequiresIsa", random.nextBoolean());

        XposedHelpers.setObjectField(toConfuse,"enabled", true);
        XposedHelpers.setObjectField(toConfuse,"enabledSetting", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"nativeLibraryRootDir", random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"secondaryNativeLibraryDir", random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"primaryCpuAbi", random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"secondaryCpuAbi", random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"taskAffinity", random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"uid", 0);
        XposedHelpers.setObjectField(toConfuse,"minSdkVersion", 0);
        XposedHelpers.setObjectField(toConfuse,"longVersionCode", random.nextLong());
        XposedHelpers.setObjectField(toConfuse,"versionCode", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"privateFlags", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"privateFlagsExt", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"compileSdkVersion", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"compileSdkVersionCodename", String.valueOf(random.nextInt(12)));
        XposedHelpers.setObjectField(toConfuse,"installLocation", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"networkSecurityConfigRes", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"targetSandboxVersion", random.nextInt(5));
        XposedHelpers.setObjectField(toConfuse,"targetSdkVersion", random.nextInt(32));
        XposedHelpers.setObjectField(toConfuse,"appComponentFactory", random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"iconRes", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"roundIconRes", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"category", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"classLoaderName", random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"splitClassLoaderNames", new String[]{random.nextString(random.nextInt(10)+5),random.nextString(random.nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"hiddenUntilInstalled", false);
        XposedHelpers.setObjectField(toConfuse,"zygotePreloadName", random.nextString(random.nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"gwpAsanMode", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"mHiddenApiPolicy", random.nextInt());
        return toConfuse;
    }
    @NotFinished
    public static PackageInfo confusePackageInfo(PackageInfo toConfuse,Class<?> signingDetailClass,ExtendedRandom random){

        XposedHelpers.setIntField(toConfuse,"versionCode", random.nextInt());
        XposedHelpers.setIntField(toConfuse,"versionCodeMajor", random.nextInt());
        XposedHelpers.setIntField(toConfuse,"versionCodeMajor", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"versionName", getRandomString(10));
        XposedHelpers.setIntField(toConfuse,"baseRevisionCode", random.nextInt());
        XposedHelpers.setObjectField(toConfuse,"splitRevisionCodes", random.nextIntArr(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"sharedUserId", "0");
        XposedHelpers.setIntField(toConfuse,"sharedUserLabel", 0);
        XposedHelpers.setObjectField(toConfuse,"applicationInfo", confuseApplicationInfo(toConfuse.applicationInfo,random));
        XposedHelpers.setLongField(toConfuse,"firstInstallTime", random.nextLong());
        XposedHelpers.setLongField(toConfuse,"lastUpdateTime", random.nextLong());
        XposedHelpers.setObjectField(toConfuse,"gids", random.nextIntArr(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"activities", emptyActivityInfo);
        XposedHelpers.setObjectField(toConfuse,"receivers", emptyActivityInfo);
        XposedHelpers.setObjectField(toConfuse,"services", emptyActivityInfo);
        XposedHelpers.setObjectField(toConfuse,"providers", emptyProviderInfo);
        XposedHelpers.setObjectField(toConfuse,"instrumentation", emptyInstrumentationInfo);
        XposedHelpers.setObjectField(toConfuse,"permissions", emptyPermissionInfo);
        int l = random.nextInt(5)+1;
        XposedHelpers.setObjectField(toConfuse,"requestedPermissions", random.randomStrArr(l));
        XposedHelpers.setObjectField(toConfuse,"requestedPermissionsFlags", random.nextIntArr(l));
        XposedHelpers.setObjectField(toConfuse,"attributions", emptyAttributions);
        XposedHelpers.setObjectField(toConfuse,"signatures", emptySignatures);
        SigningInfo signingInfo = new SigningInfo();
        Object signingDetail = XposedHelpers.newInstance(signingDetailClass,emptySignatures,random.nextInt());
        XposedHelpers.setObjectField(signingInfo,"mSigningDetails", signingDetail);
        XposedHelpers.setObjectField(toConfuse,"signingInfo", signingInfo);
        XposedHelpers.setObjectField(toConfuse,"configPreferences", emptyConfigurationInfos);
        XposedHelpers.setObjectField(toConfuse,"reqFeatures", emptyFeatureInfo);
        XposedHelpers.setObjectField(toConfuse,"featureGroups", emptyFeatureGroupInfo);
        XposedHelpers.setBooleanField(toConfuse,"isStub", random.nextBoolean());
        XposedHelpers.setBooleanField(toConfuse,"coreApp", false);
        XposedHelpers.setBooleanField(toConfuse,"requiredForAllUsers", false);
        XposedHelpers.setObjectField(toConfuse,"restrictedAccountType", getRandomString(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"requiredAccountType", getRandomString(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"overlayTarget", getRandomString(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"targetOverlayableName", getRandomString(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"overlayCategory", getRandomString(random.nextInt(5)+2));
        XposedHelpers.setIntField(toConfuse,"overlayPriority", random.nextInt());
        XposedHelpers.setBooleanField(toConfuse,"mOverlayIsStatic", random.nextBoolean());
        XposedHelpers.setIntField(toConfuse,"compileSdkVersion", random.nextInt(32));
        XposedHelpers.setObjectField(toConfuse,"overlayCategory", getRandomString(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"compileSdkVersionCodename", getRandomString(random.nextInt(5)+2));
        XposedHelpers.setBooleanField(toConfuse,"isApex", random.nextBoolean());


        return toConfuse;
    }

}
