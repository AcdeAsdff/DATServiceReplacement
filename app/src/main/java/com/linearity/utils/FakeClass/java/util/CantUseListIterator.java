package com.linearity.utils.FakeClass.java.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.util.ListIterator;
import java.util.function.Consumer;

public class CantUseListIterator<E> implements ListIterator<E> {
    public static final CantUseListIterator<?> INSTANCE = new CantUseListIterator<>();
    static {
        FakeReturnClassMap.registerInstance(ListIterator.class,INSTANCE);
    }
    public CantUseListIterator(){}

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public E next() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public E previous() {
        return null;
    }

    @Override
    public int nextIndex() {
        return 0;
    }

    @Override
    public int previousIndex() {
        return 0;
    }

    @Override
    public void remove() {
    }

    @Override
    public void set(E e) {

    }

    @Override
    public void add(E e) {

    }

    @Override
    public void forEachRemaining(@NonNull Consumer<? super E> action) {
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
