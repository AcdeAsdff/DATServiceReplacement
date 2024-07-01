package com.linearity.utils.FakeClass.java.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class CantUseComparator<E> implements Comparator<E> {
    public static final CantUseComparator<?> INSTANCE = new CantUseComparator<>();
    static {
        FakeReturnClassMap.registerInstance(CantUseComparator.class,INSTANCE);
    }
    public CantUseComparator(){};

    @Override
    public int compare(E o1, E o2) {
        return 0;
    }

    @Override
    public Comparator<E> reversed() {
        return (Comparator<E>) CantUseComparator.INSTANCE;
    }

    @Override
    public Comparator<E> thenComparing(Comparator<? super E> other) {
        return (Comparator<E>) CantUseComparator.INSTANCE;
    }

    @Override
    public <U> Comparator<E> thenComparing(Function<? super E, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return (Comparator<E>) CantUseComparator.INSTANCE;
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<E> thenComparing(Function<? super E, ? extends U> keyExtractor) {
        return (Comparator<E>) CantUseComparator.INSTANCE;
    }

    @Override
    public Comparator<E> thenComparingInt(ToIntFunction<? super E> keyExtractor) {
        return (Comparator<E>) CantUseComparator.INSTANCE;
    }

    @Override
    public Comparator<E> thenComparingLong(ToLongFunction<? super E> keyExtractor) {
        return (Comparator<E>) CantUseComparator.INSTANCE;
    }

    @Override
    public Comparator<E> thenComparingDouble(ToDoubleFunction<? super E> keyExtractor) {
        return (Comparator<E>) CantUseComparator.INSTANCE;
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
