package com.linearity.utils.FakeClass.java.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;
import com.linearity.utils.FakeClass.java.util.function.CantUseIntConsumer;
import com.linearity.utils.FakeClass.java.util.function.CantUseLongConsumer;

import java.util.LongSummaryStatistics;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class CantUseLongSummaryStatistics extends LongSummaryStatistics {
    public static final CantUseLongSummaryStatistics INSTANCE = new CantUseLongSummaryStatistics();
    static {
        FakeReturnClassMap.registerInstance(CantUseLongSummaryStatistics.class,INSTANCE);
    }
    @Override
    public IntConsumer andThen(IntConsumer after) {
        return CantUseIntConsumer.INSTANCE;
    }

    public CantUseLongSummaryStatistics() {
    }

    @Override
    public void accept(int value) {
    }

    @Override
    public void accept(long value) {
    }

    @Override
    public void combine(LongSummaryStatistics other) {
    }

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
