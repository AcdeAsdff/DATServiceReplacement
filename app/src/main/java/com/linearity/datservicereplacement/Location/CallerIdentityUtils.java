package com.linearity.datservicereplacement.Location;


import static com.linearity.utils.LoggerUtils.LoggerLog;

import com.linearity.datservicereplacement.PackageManager.PackageManagerUtils;
import com.linearity.utils.SystemAppChecker;

import java.util.Arrays;
import java.util.HashMap;

import de.robv.android.xposed.XposedHelpers;

public class CallerIdentityUtils {

    public static final HashMap<Integer, SystemAppChecker> getSystemChecker_CallerIdentityAt_map = new HashMap<>();
    public static SystemAppChecker getSystemChecker_CallerIdentityAt(int index){
        if (getSystemChecker_CallerIdentityAt_map.containsKey(index)){
            return getSystemChecker_CallerIdentityAt_map.get(index);
        }
        SystemAppChecker ret = param -> {

            int tempIndex = index;
            if (tempIndex < 0){
                tempIndex += param.args.length;
            }
            try{
                return isCallerIdentitySystemApp(param.args[tempIndex]);
            }catch (Exception e){
                LoggerLog("getCallerIdentity Failed:" + Arrays.deepToString(param.args) + " " + param.method + " " + param.thisObject);
                LoggerLog(e);
                return true;
            }
        };

        getSystemChecker_CallerIdentityAt_map.put(index,ret);
        return ret;
    }

    public static boolean isCallerIdentitySystemApp(Object callerIdentity){
        int uid = XposedHelpers.getIntField(callerIdentity,"mUid");
        return PackageManagerUtils.isSystemApp(uid);
    }
}
