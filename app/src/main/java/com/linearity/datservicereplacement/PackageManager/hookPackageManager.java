package com.linearity.datservicereplacement.PackageManager;

import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.isSystemApp;
import static com.linearity.datservicereplacement.ReturnIfNonSys.mSystemReady;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.StartHook.WHITELIST_PACKAGE_NAMES;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.Attribution;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Binder;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SparseArray;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.FakeClass.java.util.CantUseArrayList;
import com.linearity.utils.NotFinished;
import com.linearity.utils.SystemAppChecker;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class hookPackageManager {


    private static final Set<Class<?>> hooked = new HashSet<>();
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
                        if (PackageManagerUtils.pm == null){
                            PackageManagerUtils.pm = param.thisObject;
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
                        if (callingPackageInfo.packageName.equals("com.android.launcher3")
                                || callingPackageInfo.packageName.equals("com.google.android.googlequicksearchbox")
                                || WHITELIST_PACKAGE_NAMES.contains(callingPackageName)
                        ){
                            PackageManagerUtils.sysPackages.put(callingUID, PackageManagerUtils.EMPTY_PACKAGE_INFO);
                            sysPackagesByName.put(callingPackageName, PackageManagerUtils.EMPTY_PACKAGE_INFO);
                            return;
                        }
                        PackageManagerUtils.nonSysPackages.put(callingUID,callingPackageInfo);
                        if (callingPackageInfo.packageName != null) {
                            nonSysPackagesByName.put(callingPackageInfo.packageName, callingPackageInfo);
                        }
                        if (isSystemApplicationInfo(callingPackageInfo.applicationInfo)){
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
        PackageInfo nonSysPackageInfo = nonSysPackagesByName.getOrDefault(accessingPackageName,null);
        if (nonSysPackageInfo == null){return false;}
        if (nonSysPackageInfo.packageName == null){return false;}
        return callingUID == nonSysPackageInfo.applicationInfo.uid;
    }

    public static final Pair<String,Integer> invalid = new Pair<>("",-0x39c5bb);
    public static Pair<String,Integer> fetchInfoFromSettingString(Object packageOrSharedUserSetting){
        if (packageOrSharedUserSetting == null){
            return invalid;
        }
        try {
            String partRight = packageOrSharedUserSetting.toString().split(" ")[1];
            partRight = partRight.substring(0,partRight.length()-1);
            String[] callingSettingParts = partRight.split("/");
            String callingPackageNameFromSetting = callingSettingParts[0];
            int callingUidFromSetting = Integer.parseInt(callingSettingParts[1]);
            return new Pair<>(callingPackageNameFromSetting,callingUidFromSetting);
        }catch (Exception e){
            LoggerLog(packageOrSharedUserSetting);
            LoggerLog(e);
            return invalid;
        }
    }

    public static final Set<String> bannedAccessingStringInPackageName = new HashSet<>();
    static {
        bannedAccessingStringInPackageName.add("lsposed");
        bannedAccessingStringInPackageName.add("lineage");
        bannedAccessingStringInPackageName.add("com.linearity");
    }
    /**
     when return true:do not show
     */
    public static boolean IThinkShouldFilterApplication(int callingUid,Object /*SettingBase*/ accessingSetting, int userId){
        if (!mSystemReady){//even not ready.
            return false;
        }
        if (PackageManagerUtils.isSystemApp(callingUid)){
            return false;
        }

        Object callingSetting = PackageManagerUtils.getPackageSettingFromUid(callingUid);
        if (callingSetting == null){
            LoggerLog(new Exception("don't have any?:" + callingUid));
            return false;
        }

        Pair<String,Integer> callingPair = fetchInfoFromSettingString(callingSetting);
        String callingPackageNameFromSetting = callingPair.first;
        int callingUidFromSetting = callingPair.second;

        Pair<String,Integer> accessingPair = fetchInfoFromSettingString(accessingSetting);
        String accessingPackageNameFromSetting = accessingPair.first.toLowerCase();
        int accessingUidFromSetting = accessingPair.second;


        if (WHITELIST_PACKAGE_NAMES.contains(callingPackageNameFromSetting)){
            return false;
        }

        for (String s:bannedAccessingStringInPackageName) {
            if (accessingPackageNameFromSetting.contains(s)
                    && !callingPackageNameFromSetting.contains(s)
            ) {
                return true;
            }
        }
        if ((accessingUidFromSetting % 100000) <= 10000){
            return false;
        }

        if (accessingSetting.getClass().getName().equals("com.android.server.pm.SharedUserSetting")){
            Object pkgsWatchedArraySet = XposedHelpers.getObjectField(accessingSetting,"mPackages");
            ArraySet<Object> callingPkgSettings = (ArraySet<Object>) XposedHelpers.getObjectField(pkgsWatchedArraySet,"mStorage");
            for (Object singleCallingPkgSetting:callingPkgSettings){
                if ((boolean) XposedHelpers.callMethod(singleCallingPkgSetting,"isSystem")){
                    return false;
                }
            }
        }
        if ((boolean)XposedHelpers.callMethod(accessingSetting,"isSystem")){
            return false;
        }

        //nonSys access nonSys part
        /**
         * expose some package so that i can login with them.
         * (i hate monopoly capital :( )
         */
        if (accessingPackageNameFromSetting.contains("tencent")
                || accessingPackageNameFromSetting.contains("alipay")
        ){
            return false;
        }
//        LoggerLog(accessingPackageNameFromSetting);
        return true;

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

    public static final HashMap<Integer, SystemAppChecker> getSystemChecker_ApplicationInfoAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_ApplicationInfoAt(int index){
        if (getSystemChecker_ApplicationInfoAt_map.containsKey(index)){
            return getSystemChecker_ApplicationInfoAt_map.get(index);
        }
        SystemAppChecker ret = param -> {

            try {
                int tempIndex = index;
                if (tempIndex < 0) {
                    tempIndex += param.args.length;
                }
                return isSystemApp(Binder.getCallingUid())
                        && isSystemApplicationInfo((ApplicationInfo) param.args[tempIndex]);
            }catch (Exception e){
                showAfter.simpleExecutor.execute(param);
                return isSystemApp(Binder.getCallingUid());
            }
        };
        getSystemChecker_ApplicationInfoAt_map.put(index,ret);
        return ret;
    }
    public static boolean isSystemApplicationInfo(ApplicationInfo applicationInfo){
        return applicationInfo.isSystemApp();
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
        XposedHelpers.setObjectField(toConfuse,"versionName", random.nextString(10));
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
        XposedHelpers.setObjectField(toConfuse,"restrictedAccountType", random.nextString(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"requiredAccountType", random.nextString(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"overlayTarget", random.nextString(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"targetOverlayableName", random.nextString(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"overlayCategory", random.nextString(random.nextInt(5)+2));
        XposedHelpers.setIntField(toConfuse,"overlayPriority", random.nextInt());
        XposedHelpers.setBooleanField(toConfuse,"mOverlayIsStatic", random.nextBoolean());
        XposedHelpers.setIntField(toConfuse,"compileSdkVersion", random.nextInt(32));
        XposedHelpers.setObjectField(toConfuse,"overlayCategory", random.nextString(random.nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"compileSdkVersionCodename", random.nextString(random.nextInt(5)+2));
        XposedHelpers.setBooleanField(toConfuse,"isApex", random.nextBoolean());


        return toConfuse;
    }


}
