package com.linearity.utils.FakeClass.android.hardware.usb;

import static com.linearity.utils.FakeInfo.FakeProcInfoGenerator.random;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.nio.ByteBuffer;

public class CantUseUsbRequest extends UsbRequest {
    public static final CantUseUsbRequest INSTANCE = new CantUseUsbRequest();
    static {
        FakeReturnClassMap.registerInstance(UsbRequest.class,INSTANCE);
    }
    @Override
    public boolean initialize(UsbDeviceConnection connection, UsbEndpoint endpoint) {
        return random.nextBoolean();
    }

    @Override
    public void close() {
    }

    @Override
    protected void finalize() throws Throwable {
    }

    @Override
    public UsbEndpoint getEndpoint() {
        return CantUseUsbEndpoint.INSTANCE;
    }

    @Override
    public Object getClientData() {
        return null;
    }

    @Override
    public void setClientData(Object data) {
    }

    @Override
    public boolean queue(ByteBuffer buffer, int length) {
        return random.nextBoolean();
    }

    @Override
    public boolean queue(@Nullable ByteBuffer buffer) {
        return random.nextBoolean();
    }

    @Override
    public boolean cancel() {
        return random.nextBoolean();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return false;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Foolish!";
    }
}
