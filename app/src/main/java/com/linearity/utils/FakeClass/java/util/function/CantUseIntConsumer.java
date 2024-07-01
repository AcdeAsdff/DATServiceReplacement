package com.linearity.utils.FakeClass.java.util.function;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.util.function.IntConsumer;

public class CantUseIntConsumer implements IntConsumer {
    public static final CantUseIntConsumer INSTANCE = new CantUseIntConsumer();
    static {
        FakeReturnClassMap.registerInstance(IntConsumer.class,INSTANCE);
    }
    @Override
    public void accept(int value) {

    }

    @Override
    public IntConsumer andThen(IntConsumer after) {
        return CantUseIntConsumer.INSTANCE;
    }

    public CantUseIntConsumer() {
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this;
    }
    @Override
    public boolean equals(@Nullable Object obj) {
        return false;
    }


    @Override
    public int hashCode() {
        return 0;
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
