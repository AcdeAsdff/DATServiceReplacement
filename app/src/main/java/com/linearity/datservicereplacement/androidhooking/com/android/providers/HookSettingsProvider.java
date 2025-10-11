package com.linearity.datservicereplacement.androidhooking.com.android.providers;

import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.nonSysPackages;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showBefore;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.PublicSeed.publicSeed;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.content.ContentProvider;
import android.os.Binder;
import android.os.Bundle;

import com.linearity.datservicereplacement.ReturnIfNonSys;
import com.linearity.utils.FakeInfo.ExpectInfo;
import com.linearity.utils.SimpleExecutorWithMode;

public class HookSettingsProvider {


    public static void doHook(){
        classesAndHooks.put("com.android.providers.settings.SettingsProvider", HookSettingsProvider::hookSettingsProvider);
    }

    public static void hookSettingsProvider(Class<?> hookClass){

        hookAllMethodsWithCache_Auto(hookClass,"call",new SimpleExecutorWithMode(MODE_AFTER,
                        param -> {
            ContentProvider provider = (ContentProvider) param.thisObject;
            String callingPackageName = provider.getCallingPackage();
            if (callingPackageName == null){
                callingPackageName = "";
            }
            int callingUID = Binder.getCallingUid();
            long randomSeed = callingUID ^ publicSeed;
            if (nonSysPackages.containsKey(callingUID)){
                randomSeed += callingPackageName.hashCode();
            }

            Bundle result = (Bundle) param.getResult();
            if (result == null){return;}

            String askingFor = (String)param.args[1];
            if (askingFor == null){
                return;
            }

            ExpectInfo info = ExpectInfo.getInstance(randomSeed);
//            Map<String,Object> kvMap = ExpectInfo.getInstance(randomSeed).kvMap;

            //so if we have prepared something to cheat,give out
            if (info.hasElement(askingFor)){
                Object get = info.getElement(askingFor);
                if (get == null){
                    result.putString("value",null);return;
                }
                if (get instanceof Bundle){
                    result.putAll((Bundle) get);
                }
                else if (get.getClass().equals(int.class) || get instanceof Integer){
                    result.putInt("value", (Integer) get);
                }
                else if (get.getClass().equals(long.class) || get instanceof Long){
                    result.putLong("value", (Long) get);
                }
                else if (get instanceof String){
                    result.putString("value",(String) get);
                }
                return;
            }
            //if not,show it
            StringBuilder sb = new StringBuilder();
            sb.append(callingPackageName).append(" calling ").append(param.args[0]).append(" | ").append(param.args[1]);
            sb.append("\n--------------------\n");
            for (String key:result.keySet()){
                if (result.get(key) != null){
                    sb.append(key).append(" : ").append(result.get(key)).append("[").append(result.get(key).getClass()).append("]\n");
                }
                else {
                    sb.append(key).append(" : ").append(result.get(key)).append("[null]\n");
                }
            }
            sb.append("====================");
            LoggerLog(sb.toString());

        }),
                ReturnIfNonSys.contentProviderSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"mutateSystemSetting",true, ReturnIfNonSys.contentProviderSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"setAllConfigSettings",showBefore, ReturnIfNonSys.contentProviderSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"setAllConfigSettings",1, ReturnIfNonSys.contentProviderSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"getSecureSetting",null, ReturnIfNonSys.contentProviderSystemChecker);
        hookAllMethodsWithCache_Auto(hookClass,"getSystemSetting",null, ReturnIfNonSys.contentProviderSystemChecker);

    }
}
