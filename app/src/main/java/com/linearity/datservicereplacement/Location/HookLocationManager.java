package com.linearity.datservicereplacement.Location;

import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.getPackageName;
import static com.linearity.datservicereplacement.PackageManager.hookIPackageManager.isSystemApp;

import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.SystemClock;

import com.linearity.datservicereplacement.ReturnIfNonSys;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.NotFinished;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
@NotFinished
public class HookLocationManager {

    public static void doHook(XC_LoadPackage.LoadPackageParam lpparam){
        Class<?> hookClass = XposedHelpers.findClassIfExists("com.android.server.location.LocationManagerService",lpparam.classLoader);
        if (hookClass != null){
            LocationGetter.initLocations();
            hookLocationManager(hookClass);
        }
    }

    public static Set<String> targetPkgName = new HashSet<>();
    static {
        targetPkgName.add("com.gotokeep.keep");
    }
    public static void hookLocationManager(Class<?> hookClass){
        XC_MethodHook modifyLoc = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                int callingUid = Binder.getCallingUid();

                if (!isSystemApp(callingUid)){
                    String pkgName = getPackageName(callingUid);
                    if (targetPkgName.contains(pkgName)){
                        param.setResult(LocationGetter.getLocationByCurrentTimestamp());
                        return;
                    }
                }
                for (Object o:param.args){
                    if (o instanceof String){
                        if (targetPkgName.contains(o)){
                            param.setResult(LocationGetter.getLocationByCurrentTimestamp());
                            return;
                        }
                    }
                }
            }
        };
        XC_MethodHook modifyCurrentLoc = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                int callingUid = Binder.getCallingUid();

                if (!isSystemApp(callingUid)){
                    String pkgName = getPackageName(callingUid);
                    if (targetPkgName.contains(pkgName)){
                        XposedHelpers.callMethod(param.args[2],"onLocation",LocationGetter.getLocationByCurrentTimestamp());
                        return;
                    }
                }
                for (Object o:param.args){
                    if (o instanceof String){
                        if (targetPkgName.contains(o)){
                            XposedHelpers.callMethod(param.args[2],"onLocation",LocationGetter.getLocationByCurrentTimestamp());
                            return;
                        }
                    }
                }
            }
        };
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass, "getLastLocation", modifyLoc);
        ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass, "getCurrentLocation", modifyCurrentLoc);
    }

    public static List<Location> locations = new ArrayList<>();

    public static class LocationGetter{
        public static Location getLocationByCurrentTimestamp(){
            long timeStamp = System.currentTimeMillis();
            timeStamp /= 500;//every 0.5 second
            Location result = locations.get((int) (timeStamp % locations.size()));
            return updateLocation(result);
        }

        public static void initLocations(){
            addLocation(114.340964,30.378593,120.844535,1.000000f);
            addLocation(114.340964,30.378685,120.844535,1.000000f);
            addLocation(114.340964,30.378777,120.844535,1.000000f);
            addLocation(114.340964,30.378870,120.844535,1.000000f);
            addLocation(114.340964,30.378962,120.844535,1.000000f);
            addLocation(114.340964,30.379054,120.844535,1.000000f);
            addLocation(114.340964,30.379146,120.844535,1.000000f);
            addLocation(114.340964,30.379238,120.844535,1.000000f);
            addLocation(114.340964,30.379331,120.844535,1.000000f);
            addLocation(114.340964,30.379423,120.844535,1.000000f);
            addLocation(114.340964,30.379515,120.844535,1.000000f);
            addLocation(114.341768,30.379515,120.844535,1.000000f);
            addLocation(114.341729,30.379763,120.844535,19.000000f);
            addLocation(114.341614,30.379988,120.844535,37.000000f);
            addLocation(114.341437,30.380165,120.844535,55.000000f);
            addLocation(114.341212,30.380280,120.844535,73.000000f);
            addLocation(114.340964,30.380319,120.844535,91.000000f);
            addLocation(114.340716,30.380280,120.844535,109.000000f);
            addLocation(114.340491,30.380165,120.844535,127.000000f);
            addLocation(114.340314,30.379988,120.844535,145.000000f);
            addLocation(114.340199,30.379763,120.844535,163.000000f);
            addLocation(114.340160,30.379515,120.844535,181.000000f);
            addLocation(114.340160,30.379515,120.844535,181.000000f);
            addLocation(114.340160,30.379423,120.844535,181.000000f);
            addLocation(114.340160,30.379331,120.844535,181.000000f);
            addLocation(114.340160,30.379238,120.844535,181.000000f);
            addLocation(114.340160,30.379146,120.844535,181.000000f);
            addLocation(114.340160,30.379054,120.844535,181.000000f);
            addLocation(114.340160,30.378962,120.844535,181.000000f);
            addLocation(114.340160,30.378870,120.844535,181.000000f);
            addLocation(114.340160,30.378777,120.844535,181.000000f);
            addLocation(114.340160,30.378685,120.844535,181.000000f);
            addLocation(114.340160,30.378593,120.844535,181.000000f);
            addLocation(114.339356,30.378593,120.844535,181.000000f);
            addLocation(114.339395,30.378345,120.844535,199.000000f);
            addLocation(114.339510,30.378120,120.844535,217.000000f);
            addLocation(114.339687,30.377943,120.844535,235.000000f);
            addLocation(114.339912,30.377828,120.844535,253.000000f);
            addLocation(114.340160,30.377789,120.844535,271.000000f);
            addLocation(114.340408,30.377828,120.844535,289.000000f);
            addLocation(114.340633,30.377943,120.844535,307.000000f);
            addLocation(114.340810,30.378120,120.844535,325.000000f);
            addLocation(114.340925,30.378345,120.844535,343.000000f);
            addLocation(114.340964,30.378593,120.844535,1.000000f);
        }
        public static void addLocation(double mLatitude,double mLongitude,double mAltitude,float mBearing){
            Location l = new Location( LocationManager.GPS_PROVIDER);
            XposedHelpers.setLongField(l,"mTime",System.currentTimeMillis());
            XposedHelpers.setLongField(l,"mElapsedRealtimeNanos", SystemClock.elapsedRealtimeNanos());
            XposedHelpers.setDoubleField(l,"mElapsedRealtimeUncertaintyNanos", 1000.);
            XposedHelpers.setIntField(l,"mFieldsMask",(1<<8)+(1<<7)+(1<<6)+(1<<5)+(1<<3)+(1<<2)+(1<<1)+(1));
            XposedHelpers.setDoubleField(l,"mLatitude", mLatitude);
            XposedHelpers.setDoubleField(l,"mLongitude", mLongitude);
            XposedHelpers.setDoubleField(l,"mAltitude", mAltitude);
            XposedHelpers.setFloatField(l,"mSpeed", 5.0f);
            XposedHelpers.setFloatField(l,"mBearing", mBearing);
            XposedHelpers.setFloatField(l,"mHorizontalAccuracyMeters", 0.3f);
            XposedHelpers.setFloatField(l,"mVerticalAccuracyMeters", 0.1f);
            XposedHelpers.setFloatField(l,"mSpeedAccuracyMetersPerSecond", 0.1f);
            XposedHelpers.setFloatField(l,"mBearingAccuracyDegrees", 0.1f);
            locations.add(l);
        }

        public static Location updateLocation(Location toUpdate){
            Location ret = new Location(toUpdate);
            XposedHelpers.setLongField(ret,"mTime",System.currentTimeMillis());
            XposedHelpers.setLongField(ret,"mElapsedRealtimeNanos", SystemClock.elapsedRealtimeNanos());
            XposedHelpers.setFloatField(ret,"mSpeed", ((float)new ExtendedRandom(System.currentTimeMillis()).nextSmallDouble(0.5))+4.5f);
            XposedHelpers.setDoubleField(ret,"mLatitude", ret.getLatitude() + 0.00001 * new Random().nextInt(3));
            XposedHelpers.setDoubleField(ret,"mLongitude", ret.getLongitude() + 0.00001 * new Random().nextInt(3));
            return ret;
        }

    }
}
