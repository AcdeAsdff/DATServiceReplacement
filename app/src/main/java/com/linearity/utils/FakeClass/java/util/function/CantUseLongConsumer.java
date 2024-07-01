package com.linearity.utils.FakeClass.java.util.function;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.util.function.LongConsumer;

public class CantUseLongConsumer implements LongConsumer {
    public static final CantUseLongConsumer INSTANCE = new CantUseLongConsumer();
    static {
        FakeReturnClassMap.registerInstance(CantUseLongConsumer.class,INSTANCE);
    }
    @Override
    public void accept(long value) {}

    @Override
    public LongConsumer andThen(LongConsumer after) {
        return CantUseLongConsumer.INSTANCE;
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
