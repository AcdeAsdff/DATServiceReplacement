package com.linearity.utils.FakeClass.java.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;
import com.linearity.utils.FakeClass.java.util.function.CantUseIntConsumer;

import java.util.IntSummaryStatistics;
import java.util.function.IntConsumer;

public class CantUseIntSummaryStatistics extends IntSummaryStatistics {
    public static final CantUseIntSummaryStatistics INSTANCE = new CantUseIntSummaryStatistics();
    static {
        FakeReturnClassMap.registerInstance(CantUseIntSummaryStatistics.class,INSTANCE);
    }
    public CantUseIntSummaryStatistics(){}

    @Override
    public void accept(int value) {
    }

    @Override
    public void combine(IntSummaryStatistics other) {
    }

    @Override
    public String toString() {
        return "Foolish!";
    }

    @Override
    public IntConsumer andThen(IntConsumer after) {
        return CantUseIntConsumer.INSTANCE;
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

    @Override
    protected void finalize() throws Throwable {
    }
}
