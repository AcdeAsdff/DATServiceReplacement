package com.linearity.utils.FakeClass.android.hardware;

import static com.linearity.utils.FakeInfo.FakeProcInfoGenerator.random;

import android.hardware.BatteryState;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

public class CantUseBatteryState extends BatteryState {
    public static final CantUseBatteryState INSTANCE = new CantUseBatteryState();
    static {
        FakeReturnClassMap.registerInstance(BatteryState.class,INSTANCE);
    }
    @Override
    public boolean isPresent() {
        return random.nextBoolean();
    }

    @Override
    public int getStatus() {
        return random.nextInt(3);
    }

    @Override
    public float getCapacity() {
        return random.nextInt(101);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return random.nextBoolean();
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

    @Override
    protected void finalize() throws Throwable {
    }
}
