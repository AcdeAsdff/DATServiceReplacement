package com.linearity.utils.AndroidFakes.Display;

import static com.linearity.utils.AndroidFakes.Display.PNPIDArray.PNP_ID_Array;
import static com.linearity.utils.PublicSeed.publicSeed;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.graphics.Point;
import android.hardware.display.DeviceProductInfo;
import android.os.Binder;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Display;
import android.view.Surface;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.SimpleExecutorWithMode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;

public class DisplayInfoConstructor {

    public static Class<?> DisplayInfoClass = XposedHelpers.findClass("android.view.DisplayInfo", Surface.class.getClassLoader());
    public static Constructor<?> DISPLAY_INFO_CONSTRUCTOR = XposedHelpers.findConstructorExact(DisplayInfoClass);
    public static Constructor<?> DISPLAY_INFO_CLONER = XposedHelpers.findConstructorExact(DisplayInfoClass,DisplayInfoClass);
    public static Field DisplayInfo_layerStack;
    public static Field DisplayInfo_flags;
    public static Field DisplayInfo_type;
    public static Field DisplayInfo_displayId;
    public static Field DisplayInfo_displayGroupId;
    public static Field DisplayInfo_address;
    public static Field DisplayInfo_deviceProductInfo;
    public static Field DisplayInfo_name;
    public static Field DisplayInfo_uniqueId;
    public static Field DisplayInfo_appWidth;
    public static Field DisplayInfo_appHeight;
    public static Field DisplayInfo_smallestNominalAppWidth;
    public static Field DisplayInfo_smallestNominalAppHeight;
    public static Field DisplayInfo_largestNominalAppWidth;
    public static Field DisplayInfo_largestNominalAppHeight;
    public static Field DisplayInfo_logicalWidth;
    public static Field DisplayInfo_logicalHeight;
    public static Field DisplayInfo_displayCutout;
    public static Field DisplayInfo_rotation;
    public static Field DisplayInfo_modeId;
    public static Field DisplayInfo_renderFrameRate;
    public static Field DisplayInfo_defaultModeId;
    public static Field DisplayInfo_supportedModes;
    public static Field DisplayInfo_colorMode;
    public static Field DisplayInfo_supportedColorModes;
    public static Field DisplayInfo_hdrCapabilities;
    public static Field DisplayInfo_userDisabledHdrTypes;
    public static Field DisplayInfo_minimalPostProcessingSupported;
    public static Field DisplayInfo_logicalDensityDpi;
    public static Field DisplayInfo_physicalXDpi;
    public static Field DisplayInfo_physicalYDpi;
    public static Field DisplayInfo_appVsyncOffsetNanos;
    public static Field DisplayInfo_presentationDeadlineNanos;
    public static Field DisplayInfo_state;
    public static Field DisplayInfo_committedState;
    public static Field DisplayInfo_ownerUid;
    public static Field DisplayInfo_ownerPackageName;
    public static Field DisplayInfo_refreshRateOverride;
    public static Field DisplayInfo_removeMode;
    public static Field DisplayInfo_brightnessMinimum;
    public static Field DisplayInfo_brightnessMaximum;
    public static Field DisplayInfo_brightnessDefault;
    public static Field DisplayInfo_roundedCorners;
    public static Field DisplayInfo_installOrientation;
    public static Field DisplayInfo_displayShape;
    public static Field DisplayInfo_layoutLimitedRefreshRate;
    public static Field DisplayInfo_hdrSdrRatio;
    public static Field DisplayInfo_thermalRefreshRateThrottling;
    public static Field DisplayInfo_thermalBrightnessThrottlingDataId;
    static{
        DisplayInfo_layerStack = XposedHelpers.findField(DisplayInfoClass,"layerStack");//int
        DisplayInfo_flags = XposedHelpers.findField(DisplayInfoClass,"flags");//int
        DisplayInfo_type = XposedHelpers.findField(DisplayInfoClass,"type");//int
        DisplayInfo_displayId = XposedHelpers.findField(DisplayInfoClass,"displayId");//int
        DisplayInfo_displayGroupId = XposedHelpers.findField(DisplayInfoClass,"displayGroupId");//int
        DisplayInfo_address = XposedHelpers.findField(DisplayInfoClass,"address");//DisplayAddress
        DisplayInfo_deviceProductInfo = XposedHelpers.findField(DisplayInfoClass,"deviceProductInfo");//DeviceProductInfo
        DisplayInfo_name = XposedHelpers.findField(DisplayInfoClass,"name");//String
        DisplayInfo_uniqueId = XposedHelpers.findField(DisplayInfoClass,"uniqueId");//String
        DisplayInfo_appWidth = XposedHelpers.findField(DisplayInfoClass,"appWidth");//int
        DisplayInfo_appHeight = XposedHelpers.findField(DisplayInfoClass,"appHeight");//int
        DisplayInfo_smallestNominalAppWidth = XposedHelpers.findField(DisplayInfoClass,"smallestNominalAppWidth");//int
        DisplayInfo_smallestNominalAppHeight = XposedHelpers.findField(DisplayInfoClass,"smallestNominalAppHeight");//int
        DisplayInfo_largestNominalAppWidth = XposedHelpers.findField(DisplayInfoClass,"largestNominalAppWidth");//int
        DisplayInfo_largestNominalAppHeight = XposedHelpers.findField(DisplayInfoClass,"largestNominalAppHeight");//int
        DisplayInfo_logicalWidth = XposedHelpers.findField(DisplayInfoClass,"logicalWidth");//int
        DisplayInfo_logicalHeight = XposedHelpers.findField(DisplayInfoClass,"logicalHeight");//int
        DisplayInfo_displayCutout = XposedHelpers.findField(DisplayInfoClass,"displayCutout");//DisplayCutout
        DisplayInfo_rotation = XposedHelpers.findField(DisplayInfoClass,"rotation");//int
        DisplayInfo_modeId = XposedHelpers.findField(DisplayInfoClass,"modeId");//int
        DisplayInfo_renderFrameRate = XposedHelpers.findField(DisplayInfoClass,"renderFrameRate");//float
        DisplayInfo_defaultModeId = XposedHelpers.findField(DisplayInfoClass,"defaultModeId");//int
        DisplayInfo_supportedModes = XposedHelpers.findField(DisplayInfoClass,"supportedModes");//Display[].Mode
        DisplayInfo_colorMode = XposedHelpers.findField(DisplayInfoClass,"colorMode");//int
        DisplayInfo_supportedColorModes = XposedHelpers.findField(DisplayInfoClass,"supportedColorModes");//int[]
        DisplayInfo_hdrCapabilities = XposedHelpers.findField(DisplayInfoClass,"hdrCapabilities");//Display.HdrCapabilities
        DisplayInfo_userDisabledHdrTypes = XposedHelpers.findField(DisplayInfoClass,"userDisabledHdrTypes");//int[]
        DisplayInfo_minimalPostProcessingSupported = XposedHelpers.findField(DisplayInfoClass,"minimalPostProcessingSupported");//boolean
        DisplayInfo_logicalDensityDpi = XposedHelpers.findField(DisplayInfoClass,"logicalDensityDpi");//int
        DisplayInfo_physicalXDpi = XposedHelpers.findField(DisplayInfoClass,"physicalXDpi");//float
        DisplayInfo_physicalYDpi = XposedHelpers.findField(DisplayInfoClass,"physicalYDpi");//float
        DisplayInfo_appVsyncOffsetNanos = XposedHelpers.findField(DisplayInfoClass,"appVsyncOffsetNanos");//long
        DisplayInfo_presentationDeadlineNanos = XposedHelpers.findField(DisplayInfoClass,"presentationDeadlineNanos");//long
        DisplayInfo_state = XposedHelpers.findField(DisplayInfoClass,"state");//int
        DisplayInfo_committedState = XposedHelpers.findField(DisplayInfoClass,"committedState");//int
        DisplayInfo_ownerUid = XposedHelpers.findField(DisplayInfoClass,"ownerUid");//int
        DisplayInfo_ownerPackageName = XposedHelpers.findField(DisplayInfoClass,"ownerPackageName");//String
        DisplayInfo_refreshRateOverride = XposedHelpers.findField(DisplayInfoClass,"refreshRateOverride");//float
        DisplayInfo_removeMode = XposedHelpers.findField(DisplayInfoClass,"removeMode");//int
        DisplayInfo_brightnessMinimum = XposedHelpers.findField(DisplayInfoClass,"brightnessMinimum");//float
        DisplayInfo_brightnessMaximum = XposedHelpers.findField(DisplayInfoClass,"brightnessMaximum");//float
        DisplayInfo_brightnessDefault = XposedHelpers.findField(DisplayInfoClass,"brightnessDefault");//float
        DisplayInfo_roundedCorners = XposedHelpers.findField(DisplayInfoClass,"roundedCorners");//RoundedCorners
        DisplayInfo_installOrientation = XposedHelpers.findField(DisplayInfoClass,"installOrientation");//int
        DisplayInfo_displayShape = XposedHelpers.findField(DisplayInfoClass,"displayShape");//DisplayShape
        DisplayInfo_layoutLimitedRefreshRate = XposedHelpers.findField(DisplayInfoClass,"layoutLimitedRefreshRate");//SurfaceControl.RefreshRateRange
        DisplayInfo_hdrSdrRatio = XposedHelpers.findField(DisplayInfoClass,"hdrSdrRatio");//float
        DisplayInfo_thermalRefreshRateThrottling = XposedHelpers.findField(DisplayInfoClass,"thermalRefreshRateThrottling");//SparseArray<SurfaceControl.RefreshRateRange>
        DisplayInfo_thermalBrightnessThrottlingDataId = XposedHelpers.findField(DisplayInfoClass,"thermalBrightnessThrottlingDataId");//String
    }
    public static Class<?> DisplayAddressPhysicalClass = XposedHelpers.findClass("android.view.DisplayAddress$Physical", Display.class.getClassLoader());
    public static Constructor<?> DISPLAY_ADDRESS_CONSTRUCTOR = XposedHelpers.findConstructorExact(DisplayAddressPhysicalClass,int.class,Long.class);
    public static Field Physical_mPhysicalDisplayId;
    static{
        Physical_mPhysicalDisplayId = XposedHelpers.findField(DisplayAddressPhysicalClass,"mPhysicalDisplayId");//long
    }
    public static Object /*DisplayAddress$Physical*/ constructPhysicalDisplayAddress(int callingUid){
        try {
            ExtendedRandom extendedRandom = new ExtendedRandom(callingUid ^ DisplayAddressPhysicalClass.getName().hashCode()
                    + publicSeed ^((long) callingUid * callingUid));
            return DISPLAY_ADDRESS_CONSTRUCTOR.newInstance(extendedRandom.nextInt(255),extendedRandom.nextLong());
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static Field DeviceProductInfo_mName;
    public static Field DeviceProductInfo_mManufacturerPnpId;
    public static Field DeviceProductInfo_mProductId;
    public static Field DeviceProductInfo_mModelYear;
    public static Field DeviceProductInfo_mManufactureDate;
    public static Field DeviceProductInfo_mConnectionToSinkType;
    static{
        DeviceProductInfo_mName = XposedHelpers.findField(DeviceProductInfo.class,"mName");//String
        DeviceProductInfo_mManufacturerPnpId = XposedHelpers.findField(DeviceProductInfo.class,"mManufacturerPnpId");//String
        DeviceProductInfo_mProductId = XposedHelpers.findField(DeviceProductInfo.class,"mProductId");//String
        DeviceProductInfo_mModelYear = XposedHelpers.findField(DeviceProductInfo.class,"mModelYear");//Integer
        DeviceProductInfo_mManufactureDate = XposedHelpers.findField(DeviceProductInfo.class,"mManufactureDate");//ManufactureDate
        DeviceProductInfo_mConnectionToSinkType = XposedHelpers.findField(DeviceProductInfo.class,"mConnectionToSinkType");//int
    }

    public static DeviceProductInfo constructDeviceProductInfo(int callingUid){
        try {
            ExtendedRandom extendedRandom = new ExtendedRandom(
                    DeviceProductInfo.class.toString().hashCode()
                    + callingUid ^ DeviceProductInfo.class.toString().hashCode());
            int year = extendedRandom.nextInt(14)+2010;
            DeviceProductInfo result = new DeviceProductInfo(
                    extendedRandom.nextString(extendedRandom.nextInt(15)+3),
                    extendedRandom.pickFromArray(PNP_ID_Array),
                    extendedRandom.nextString(extendedRandom.nextInt(15)+3),
                    year,
                    extendedRandom.nextInt(2)
            );
            DeviceProductInfo_mManufactureDate.set(result,/*ManufactureDate*/constructManufactureDate(extendedRandom.nextInt(52),year));
            return result;
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static Class<?> ManufactureDateClass = XposedHelpers.findClass("android.hardware.display.DeviceProductInfo$ManufactureDate",DeviceProductInfo.class.getClassLoader());
    public static Constructor<?> MANUFACTURE_DATE_CONSTRUCTOR = XposedHelpers.findConstructorExact(ManufactureDateClass,Integer.class,Integer.class);
    public static Object /*ManufactureDate*/ constructManufactureDate(int week,int year){
        try {
            return MANUFACTURE_DATE_CONSTRUCTOR.newInstance(week,year);
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static Field HdrCapabilities_mSupportedHdrTypes;
    public static Field HdrCapabilities_mMaxLuminance;
    public static Field HdrCapabilities_mMaxAverageLuminance;
    public static Field HdrCapabilities_mMinLuminance;
    static{
        HdrCapabilities_mSupportedHdrTypes = XposedHelpers.findField(Display.HdrCapabilities.class,"mSupportedHdrTypes");//int[]
        HdrCapabilities_mMaxLuminance = XposedHelpers.findField(Display.HdrCapabilities.class,"mMaxLuminance");//float
        HdrCapabilities_mMaxAverageLuminance = XposedHelpers.findField(Display.HdrCapabilities.class,"mMaxAverageLuminance");//float
        HdrCapabilities_mMinLuminance = XposedHelpers.findField(Display.HdrCapabilities.class,"mMinLuminance");//float
    }
    public static Constructor<Display.HdrCapabilities> HDR_COMPABILITIES_CONSTRUCTOR = (Constructor<Display.HdrCapabilities>) XposedHelpers.findConstructorExact(Display.HdrCapabilities.class);
    public static Display.HdrCapabilities constructHdrCapabilities(int callingUid,int[] hdrTypes){
        try {
            ExtendedRandom extendedRandom = new ExtendedRandom(Display.HdrCapabilities.class.getName().hashCode() * callingUid * ((long)Math.log10(callingUid)));
            Display.HdrCapabilities result = HDR_COMPABILITIES_CONSTRUCTOR.newInstance();
            HdrCapabilities_mSupportedHdrTypes.set(result,/*int[]*/hdrTypes);
            //idk what Luminance range it should be in.
            int Luminance = extendedRandom.nextInt(200)+300;
            HdrCapabilities_mMaxLuminance.set(result,/*float*/(float)(Luminance));
            HdrCapabilities_mMaxAverageLuminance.set(result,/*float*/(float)((Luminance/2.) + extendedRandom.nextGaussian() * extendedRandom.nextSign()));
            HdrCapabilities_mMinLuminance.set(result,/*float*/(float)extendedRandom.nextGaussian());
            return result;
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static Constructor<Display.Mode> DISPLAY_MODE_CONSTRUCTOR = (Constructor<Display.Mode>) XposedHelpers.findConstructorExact(Display.Mode.class,
            int.class,int.class,int.class,float.class,float[].class,int[].class);
    public static Display.Mode constructDisplayMode(int modeId, int width, int height, float refreshRate,
                                                    float[] alternativeRefreshRates, int[] supportedHdrTypes){
        try {
            return DISPLAY_MODE_CONSTRUCTOR.newInstance(modeId,width,height,refreshRate,alternativeRefreshRates,supportedHdrTypes);
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static final float[] fpsArr = {60,90,120,180,240};
    public static final int[] colorModes = new int[]{0,1,2,3,4,5,6,7,8,9};
    public static final int[] hdrTypes = new int[]{1,2,3,4,};
    //其实国内好像不爱弄各种屏幕ID，导致获取出来按道理是null之类。到24年才看到小米在官方的列表(https://uefi.org/PNP_ID_List)里
    public static Pair<Object/*DisplayInfo*/,Display.Mode>  constructDisplayInfo(int callingUid){
        try {
            Object result = DISPLAY_INFO_CONSTRUCTOR.newInstance();
            ExtendedRandom extendedRandom = new ExtendedRandom(
                    Double.doubleToRawLongBits(
                            Math.exp(
                                    (Double.doubleToRawLongBits(Math.sqrt(callingUid))) ^ publicSeed)
                    ) ^ DisplayInfoClass.getName().hashCode());
            ExtendedRandom extendedRandom_displayMode = new ExtendedRandom(
                    Double.doubleToRawLongBits(
                            Math.exp(
                                    (Double.doubleToRawLongBits(Math.cbrt(callingUid))) ^ publicSeed)
                    ) ^ DisplayInfoClass.getName().hashCode());


            int[] hdrTypeSubArr = extendedRandom_displayMode.nextSubArray(hdrTypes);
            int width = extendedRandom_displayMode.nextInt(100)*20;
            int height = extendedRandom_displayMode.nextInt(1000)*20+1000;
            float fps = extendedRandom_displayMode.pickFromArray(fpsArr);
            Arrays.sort(hdrTypeSubArr);
            int modeId = 1;
            Display.Mode mode = constructDisplayMode(modeId,width,height,fps,new float[0],hdrTypeSubArr);

            DisplayInfo_layerStack.set(result,/*int*/0);
            DisplayInfo_flags.set(result,/*int*/extendedRandom.randomFlag(13));
            DisplayInfo_type.set(result,/*int*/1);
            DisplayInfo_displayId.set(result,/*int*/0);
            DisplayInfo_displayGroupId.set(result,/*int*/0);
            DisplayInfo_address.set(result,/*DisplayAddress*/constructPhysicalDisplayAddress(callingUid));
            DisplayInfo_deviceProductInfo.set(result,/*DeviceProductInfo*/constructDeviceProductInfo(callingUid));
            DisplayInfo_name.set(result,/*String*/"内置屏幕");
            DisplayInfo_uniqueId.set(result,/*String*/"local:"+extendedRandom.nextRandomDecimal(extendedRandom.nextInt(15)+3));
            DisplayInfo_appWidth.set(result,/*int*/width);
            DisplayInfo_appHeight.set(result,/*int*/height);
            DisplayInfo_smallestNominalAppWidth.set(result,/*int*/width);
            DisplayInfo_smallestNominalAppHeight.set(result,/*int*/width - extendedRandom.nextInt(20));
            DisplayInfo_largestNominalAppWidth.set(result,/*int*/height);
            DisplayInfo_largestNominalAppHeight.set(result,/*int*/height - extendedRandom.nextInt(20));
            DisplayInfo_logicalWidth.set(result,/*int*/width);
            DisplayInfo_logicalHeight.set(result,/*int*/height);
            //TODO:Finish this👇.
            DisplayInfo_displayCutout.set(result,/*DisplayCutout*/null);
            DisplayInfo_rotation.set(result,/*int*/0);
            DisplayInfo_modeId.set(result,/*int*/modeId);
            DisplayInfo_renderFrameRate.set(result,/*float*/fps);
            DisplayInfo_defaultModeId.set(result,/*int*/1);
            DisplayInfo_supportedModes.set(result,/*Display.Mode[]*/new Display.Mode[]{
                    mode
            });
            int[] subColorModeArr = extendedRandom.nextSubArray(colorModes);
            DisplayInfo_colorMode.set(result,/*int*/extendedRandom.pickFromArray(subColorModeArr));
            DisplayInfo_supportedColorModes.set(result,/*int[]*/subColorModeArr);

            DisplayInfo_hdrCapabilities.set(result,/*Display.HdrCapabilities*/constructHdrCapabilities(callingUid,hdrTypeSubArr));
            DisplayInfo_userDisabledHdrTypes.set(result,/*int[]*/EmptyArrays.EMPTY_INT_ARRAY);
            DisplayInfo_minimalPostProcessingSupported.set(result,/*boolean*/extendedRandom.nextBoolean());
            int dpi = extendedRandom.nextInt(600);
            DisplayInfo_logicalDensityDpi.set(result,/*int*/dpi);
            DisplayInfo_physicalXDpi.set(result,/*float*/(float)((float)dpi - extendedRandom.nextSmallDouble(5)));
            DisplayInfo_physicalYDpi.set(result,/*float*/(float)((float)dpi - extendedRandom.nextSmallDouble(5)));
            DisplayInfo_appVsyncOffsetNanos.set(result,/*long*/1000000L);//idk how to randomize
            DisplayInfo_presentationDeadlineNanos.set(result,/*long*/16666666L);
            DisplayInfo_state.set(result,/*int*/2);//ON
            DisplayInfo_committedState.set(result,/*int*/2);
            DisplayInfo_ownerUid.set(result,/*int*/0);
            DisplayInfo_ownerPackageName.set(result,/*String*/null);
            DisplayInfo_refreshRateOverride.set(result,/*float*/fps);
            DisplayInfo_removeMode.set(result,/*int*/0);
            DisplayInfo_brightnessMinimum.set(result,/*float*/0.f);
            DisplayInfo_brightnessMaximum.set(result,/*float*/1.f);
            DisplayInfo_brightnessDefault.set(result,/*float*/((float)extendedRandom.nextSmallDouble(1)));
            //TODO:Finish this👇.
            DisplayInfo_roundedCorners.set(result,/*RoundedCorners*/null);
            DisplayInfo_installOrientation.set(result,/*int*/0);
            //TODO:Finish this👇.
            DisplayInfo_displayShape.set(result,/*DisplayShape*/null);
            DisplayInfo_layoutLimitedRefreshRate.set(result,/*SurfaceControl.RefreshRateRange*/null);
            DisplayInfo_hdrSdrRatio.set(result,/*float*/Float.NaN);
            DisplayInfo_thermalRefreshRateThrottling.set(result,/*SparseArray<SurfaceControl.RefreshRateRange>*/new SparseArray<>());
            DisplayInfo_thermalBrightnessThrottlingDataId.set(result,/*String*/"default");
            return new Pair<>(result,mode);
        }catch (Exception e){
            LoggerLog(e);
            return new Pair<>(null,null);
        }
    }
    public static SimpleExecutorWithMode modifyDisplayInfoAfterCalled = new SimpleExecutorWithMode(MODE_AFTER,param ->
            param.setResult(
                    modifyDisplayInfo(
                            Binder.getCallingUid(),param.getResult()
                    ).first
            )
    );
    public static SimpleExecutorWithMode modifyDisplayModeAfterCalled = new SimpleExecutorWithMode(MODE_AFTER,param ->
            param.setResult(
                    modifyDisplayMode(
                            Binder.getCallingUid(), (Display.Mode) param.getResult()
                    )
            )
    );
    public static SimpleExecutorWithMode modifyPointAfterCalled = new SimpleExecutorWithMode(MODE_AFTER,param ->
            param.setResult(
                    modifyPoint(Binder.getCallingUid(), (Point) param.getResult())
            )
    );
    public static Display.Mode modifyDisplayMode(int callingUid,Display.Mode displayMode){
        try {
            ExtendedRandom extendedRandom_displayMode = new ExtendedRandom(
                    Double.doubleToRawLongBits(
                            Math.exp(
                                    (Double.doubleToRawLongBits(Math.cbrt(callingUid))) ^ publicSeed)
                    ) ^ DisplayInfoClass.getName().hashCode());

            int[] hdrTypeSubArr = extendedRandom_displayMode.nextSubArray(hdrTypes);
            Arrays.sort(hdrTypeSubArr);
            int width = extendedRandom_displayMode.nextInt(10)*(-10) + displayMode.getPhysicalWidth();
            int height = extendedRandom_displayMode.nextInt(10)*(-10) + displayMode.getPhysicalHeight();
            float fps = extendedRandom_displayMode.pickFromArray(fpsArr);
            int modeId = 1;
            return constructDisplayMode(modeId,width,height,fps,new float[0],hdrTypeSubArr);
        }catch (Exception e){
            return null;
        }
    }
    public static Pair<Object/*DisplayInfo*/,Display.Mode> modifyDisplayInfo(int callingUid,Object displayInfo){
        try {
            displayInfo = DISPLAY_INFO_CLONER.newInstance(displayInfo);
            ExtendedRandom extendedRandom = new ExtendedRandom(
                    Double.doubleToRawLongBits(
                            Math.exp(
                                    (Double.doubleToRawLongBits(Math.sqrt(callingUid))) ^ publicSeed)
                    ) ^ DisplayInfoClass.getName().hashCode());
            ExtendedRandom extendedRandom_displayMode = new ExtendedRandom(
                    Double.doubleToRawLongBits(
                            Math.exp(
                                    (Double.doubleToRawLongBits(Math.cbrt(callingUid))) ^ publicSeed)
                    ) ^ DisplayInfoClass.getName().hashCode());

            int[] hdrTypeSubArr = extendedRandom_displayMode.nextSubArray(hdrTypes);
            Arrays.sort(hdrTypeSubArr);
            int width = extendedRandom_displayMode.nextInt(10)*(-10) + DisplayInfo_appWidth.getInt(displayInfo);
            int height = extendedRandom_displayMode.nextInt(10)*(-10) + DisplayInfo_appHeight.getInt(displayInfo);
            float fps = extendedRandom_displayMode.pickFromArray(fpsArr);
            int modeId = 1;
            Display.Mode mode = constructDisplayMode(modeId,width,height,fps,new float[0],hdrTypeSubArr);


            DisplayInfo_layerStack.set(displayInfo,/*int*/0);
            DisplayInfo_flags.set(displayInfo,/*int*/extendedRandom.randomFlag(13));
            DisplayInfo_type.set(displayInfo,/*int*/1);
            DisplayInfo_displayId.set(displayInfo,/*int*/0);
            DisplayInfo_displayGroupId.set(displayInfo,/*int*/0);
            DisplayInfo_address.set(displayInfo,/*DisplayAddress*/constructPhysicalDisplayAddress(callingUid));
            DisplayInfo_deviceProductInfo.set(displayInfo,/*DeviceProductInfo*/constructDeviceProductInfo(callingUid));
            DisplayInfo_name.set(displayInfo,/*String*/"内置屏幕");
            DisplayInfo_uniqueId.set(displayInfo,/*String*/"local:"+extendedRandom.nextRandomDecimal(extendedRandom.nextInt(15)+3));
            DisplayInfo_appWidth.set(displayInfo,/*int*/width);
            DisplayInfo_appHeight.set(displayInfo,/*int*/height);
            DisplayInfo_smallestNominalAppWidth.set(displayInfo,/*int*/width);
            DisplayInfo_smallestNominalAppHeight.set(displayInfo,/*int*/width - extendedRandom.nextInt(20));
            DisplayInfo_largestNominalAppWidth.set(displayInfo,/*int*/height);
            DisplayInfo_largestNominalAppHeight.set(displayInfo,/*int*/height - extendedRandom.nextInt(20));
            DisplayInfo_logicalWidth.set(displayInfo,/*int*/width);
            DisplayInfo_logicalHeight.set(displayInfo,/*int*/height);
            //TODO:Finish this👇.
//            DisplayInfo_displayCutout.set(displayInfo,/*DisplayCutout*/null);
            DisplayInfo_rotation.set(displayInfo,/*int*/0);

            DisplayInfo_modeId.set(displayInfo,/*int*/modeId);

            DisplayInfo_renderFrameRate.set(displayInfo,/*float*/fps);
            DisplayInfo_defaultModeId.set(displayInfo,/*int*/1);


            DisplayInfo_supportedModes.set(displayInfo,/*Display.Mode[]*/new Display.Mode[]{
                    mode
            });
            int[] subColorModeArr = extendedRandom.nextSubArray(colorModes);
            DisplayInfo_colorMode.set(displayInfo,/*int*/extendedRandom.pickFromArray(subColorModeArr));
            DisplayInfo_supportedColorModes.set(displayInfo,/*int[]*/subColorModeArr);

            DisplayInfo_hdrCapabilities.set(displayInfo,/*Display.HdrCapabilities*/constructHdrCapabilities(callingUid,hdrTypeSubArr));
            DisplayInfo_userDisabledHdrTypes.set(displayInfo,/*int[]*/EmptyArrays.EMPTY_INT_ARRAY);
            DisplayInfo_minimalPostProcessingSupported.set(displayInfo,/*boolean*/extendedRandom.nextBoolean());

            int dpi = DisplayInfo_logicalDensityDpi.getInt(displayInfo)-extendedRandom.nextInt(10);
            DisplayInfo_logicalDensityDpi.set(displayInfo,/*int*/dpi);
            DisplayInfo_physicalXDpi.set(displayInfo,/*float*/(float)((float)dpi - extendedRandom.nextSmallDouble(5)));
            DisplayInfo_physicalYDpi.set(displayInfo,/*float*/(float)((float)dpi - extendedRandom.nextSmallDouble(5)));

            DisplayInfo_appVsyncOffsetNanos.set(displayInfo,/*long*/1000000L);//idk how to randomize
            DisplayInfo_presentationDeadlineNanos.set(displayInfo,/*long*/16666666L);
            DisplayInfo_state.set(displayInfo,/*int*/2);//ON
            DisplayInfo_committedState.set(displayInfo,/*int*/2);
            DisplayInfo_ownerUid.set(displayInfo,/*int*/0);
            DisplayInfo_ownerPackageName.set(displayInfo,/*String*/null);
            DisplayInfo_refreshRateOverride.set(displayInfo,/*float*/fps);
            DisplayInfo_removeMode.set(displayInfo,/*int*/0);
            DisplayInfo_brightnessMinimum.set(displayInfo,/*float*/0.f);
            DisplayInfo_brightnessMaximum.set(displayInfo,/*float*/1.f);
            DisplayInfo_brightnessDefault.set(displayInfo,/*float*/((float)extendedRandom.nextSmallDouble(1)));
            //TODO:Finish this👇.
//            DisplayInfo_roundedCorners.set(displayInfo,/*RoundedCorners*/null);
            DisplayInfo_installOrientation.set(displayInfo,/*int*/0);
            //TODO:Finish this👇.
//            DisplayInfo_displayShape.set(displayInfo,/*DisplayShape*/null);
//            DisplayInfo_layoutLimitedRefreshRate.set(displayInfo,/*SurfaceControl.RefreshRateRange*/null);

            DisplayInfo_hdrSdrRatio.set(displayInfo,/*float*/Float.NaN);
            DisplayInfo_thermalRefreshRateThrottling.set(displayInfo,/*SparseArray<SurfaceControl.RefreshRateRange>*/new SparseArray<>());
            DisplayInfo_thermalBrightnessThrottlingDataId.set(displayInfo,/*String*/"default");
            return new Pair<>(displayInfo,mode);
        }catch (Exception e){
            LoggerLog(e);
            return new Pair<>(null,null);
        }
    }

    public static android.graphics.Point modifyPoint(int callingUid, Point toModify){
        try {
            ExtendedRandom extendedRandom = new ExtendedRandom(Point.class.getName().hashCode() ^ callingUid)
                    .nextExtendedRandom();
            extendedRandom = new ExtendedRandom(extendedRandom.nextLong()
                    * Double.doubleToRawLongBits(Math.sin(callingUid ^ publicSeed))
                    * publicSeed);
            toModify = new Point(toModify);
            toModify.x = toModify.x + extendedRandom.nextSign() * extendedRandom.nextInt(toModify.x/100);
            toModify.y = toModify.y + extendedRandom.nextSign() * extendedRandom.nextInt(toModify.y/100);
            return toModify;
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
}
