package com.linearity.utils.FakeClass.java.util;

import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_OBJECT_ARRAY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;
import com.linearity.utils.FakeClass.java.util.stream.Stream.CantUseStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class CantUseArrayList<E>  extends ArrayList<E> {
    public static final CantUseArrayList<?> INSTANCE = new CantUseArrayList<>();
    static {
        FakeReturnClassMap.registerInstance(CantUseArrayList.class,INSTANCE);
    }
    public CantUseArrayList(int initialCapacity) {
    }

    public CantUseArrayList() {
    }

    public CantUseArrayList(@NonNull Collection<? extends E> c) {
    }

    @Override
    public void trimToSize() {
    }

    @Override
    public void ensureCapacity(int minCapacity) {
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
    public boolean contains(@Nullable Object o) {
        return false;
    }

    @Override
    public int indexOf(@Nullable Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return 0;
    }

    @NonNull
    @Override
    public Object clone() {
        return this;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return EMPTY_OBJECT_ARRAY;
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return a;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return element;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public void add(int index, E element) {
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends E> c) {
        return true;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends E> c) {
        return true;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return true;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return true;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return (ListIterator<E>) CantUseListIterator.INSTANCE;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator() {
        return (ListIterator<E>) CantUseListIterator.INSTANCE;
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return (ListIterator<E>) CantUseListIterator.INSTANCE;
    }

    @NonNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return (List<E>) CantUseArrayList.INSTANCE;
    }

    @Override
    public void forEach(@NonNull Consumer<? super E> action) {
    }

    @NonNull
    @Override
    public Spliterator<E> spliterator() {
        return (Spliterator<E>) CantUseSpliterator.INSTANCE;
    }

    @Override
    public boolean removeIf(@NonNull Predicate<? super E> filter) {
        return false;
    }

    @Override
    public void replaceAll(@NonNull UnaryOperator<E> operator) {
    }

    @Override
    public void sort(@Nullable Comparator<? super E> c) {
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return "Foolish!";
    }

    @NonNull
    @Override
    public Stream<E> stream() {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @NonNull
    @Override
    public Stream<E> parallelStream() {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    protected void finalize() throws Throwable {
    }
}
