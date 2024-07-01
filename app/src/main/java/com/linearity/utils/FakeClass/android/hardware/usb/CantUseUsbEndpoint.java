package com.linearity.utils.FakeClass.android.hardware.usb;

import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.hardware.usb.UsbEndpoint;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import de.robv.android.xposed.XposedHelpers;

public class CantUseUsbEndpoint {
    public static UsbEndpoint INSTANCE = null;
    static {
        try {
            INSTANCE = (UsbEndpoint) XposedHelpers.newInstance(UsbEndpoint.class,1,1,1,1);
            FakeReturnClassMap.registerInstance(UsbEndpoint.class,INSTANCE);
        } catch (Exception e){
            LoggerLog(e);
        }
    }
}
