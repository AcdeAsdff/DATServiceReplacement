package com.linearity.utils.FakeClass.java.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.util.Enumeration;

public class CantUseEnumeration<E> implements Enumeration<E> {
    public static final CantUseEnumeration<?> INSTANCE = new CantUseEnumeration<>();
    static {
        FakeReturnClassMap.registerInstance(CantUseEnumeration.class,INSTANCE);
    }
    public CantUseEnumeration() {
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

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public boolean hasMoreElements() {
        return false;
    }

    @Override
    public E nextElement() {
        return null;
    }
}
