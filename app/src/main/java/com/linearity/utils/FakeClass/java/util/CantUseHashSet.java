package com.linearity.utils.FakeClass.java.util;

import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_OBJECT_ARRAY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CantUseHashSet<E> extends HashSet<E> {
    public static final CantUseHashSet<?> INSTANCE = new CantUseHashSet<>();
    static {
        FakeReturnClassMap.registerInstance(CantUseHashSet.class,INSTANCE);
    }
    public CantUseHashSet(){}

    @NonNull
    @Override
    public String toString() {
        return "Foolish!";
    }

    @NonNull
    @Override
    public Object clone() {
        return this;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return true;
    }

    @Override
    public boolean add(E e) {
        return true;
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return false;
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) CantUseListIterator.INSTANCE;
    }

    @NonNull
    @Override
    public Spliterator<E> spliterator() {
        return (Spliterator<E>) CantUseSpliterator.INSTANCE;
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends E> c) {
        return true;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return true;
    }

    @Override
    public boolean removeIf(@NonNull Predicate<? super E> filter) {
        return true;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return false;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return EMPTY_OBJECT_ARRAY;
    }

    @NonNull
    @Override
    public Stream<E> parallelStream() {
        return super.parallelStream();
    }
}
