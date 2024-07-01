package com.linearity.utils.FakeClass.java.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.util.DoubleSummaryStatistics;
import java.util.function.DoubleConsumer;

public class CantUseDoubleSummaryStatistics extends DoubleSummaryStatistics {
    public static final CantUseDoubleSummaryStatistics INSTANCE = new CantUseDoubleSummaryStatistics();
    static {
        FakeReturnClassMap.registerInstance(CantUseDoubleSummaryStatistics.class,INSTANCE);
    }
    public CantUseDoubleSummaryStatistics() {
    }

    @Override
    public void accept(double value) {
    }

    @Override
    public void combine(DoubleSummaryStatistics other) {
    }

    @Override
    public String toString() {
        return "Foolish!";
    }

    @Override
    public DoubleConsumer andThen(DoubleConsumer after) {
        return INSTANCE;
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
