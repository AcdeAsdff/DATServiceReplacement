package com.linearity.utils.FakeClass.java.util;

import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_OBJECT_ARRAY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;
import com.linearity.utils.FakeClass.java.util.stream.Stream.CantUseStream;

import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class CantUseVector<E> extends Vector<E> {

    public static final CantUseVector<?> INSTANCE = new CantUseVector<>();
    static {
        FakeReturnClassMap.registerInstance(Vector.class,INSTANCE);
    }
    public CantUseVector(int initialCapacity, int capacityIncrement) {
        super(initialCapacity, capacityIncrement);
    }

    public CantUseVector(int initialCapacity) {
    }

    public CantUseVector() {
    }

    public CantUseVector(@NonNull Collection<? extends E> c) {
    }

    @Override
    public void copyInto(@NonNull Object[] anArray) {
    }

    @Override
    public void trimToSize() {
    }

    @Override
    public void ensureCapacity(int minCapacity) {
    }

    @Override
    public void setSize(int newSize) {
    }

    @Override
    public int capacity() {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Enumeration<E> elements() {
        return (Enumeration<E>) CantUseEnumeration.INSTANCE;
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
    public int indexOf(@Nullable Object o, int index) {
        return 0;
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(@Nullable Object o, int index) {
        return 0;
    }

    @Override
    public E elementAt(int index) {
        return null;
    }

    @Override
    public E firstElement() {
        return null;
    }

    @Override
    public E lastElement() {
        return null;
    }

    @Override
    public void setElementAt(E obj, int index) {
    }

    @Override
    public void removeElementAt(int index) {
    }

    @Override
    public void insertElementAt(E obj, int index) {
    }

    @Override
    public void addElement(E obj) {
    }

    @Override
    public boolean removeElement(@Nullable Object obj) {return false;
    }

    @Override
    public void removeAllElements() {
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
    public synchronized <T> T[] toArray(@NonNull T[] a) {
        return a;
    }

    @Override
    public synchronized E get(int index) {
        return null;
    }

    @Override
    public synchronized E set(int index, E element) {
        return element;
    }

    @Override
    public boolean add(E e) {
        return true;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return true;
    }

    @Override
    public void add(int index, E element) {
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public void clear() {
    }

    @Override
    public synchronized boolean containsAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends E> c) {
        return true;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return true;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public synchronized boolean addAll(int index, @NonNull Collection<? extends E> c) {
        return true;
    }

    @Override
    public synchronized boolean equals(@Nullable Object o) {
        return false;
    }

    @Override
    public synchronized int hashCode() {
        return 0;
    }

    @NonNull
    @Override
    public synchronized String toString() {
        return "Foolish!";
    }

    @NonNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return (List<E>) CantUseArrayList.INSTANCE;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
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
    public synchronized Iterator<E> iterator() {
        return (ListIterator<E>) CantUseListIterator.INSTANCE;
    }

    @Override
    public synchronized void forEach(@NonNull Consumer<? super E> action) {
    }

    @Override
    public synchronized boolean removeIf(@NonNull Predicate<? super E> filter) {
        return true;
    }

    @Override
    public synchronized void replaceAll(@NonNull UnaryOperator<E> operator) {
    }

    @Override
    public void sort(@Nullable Comparator<? super E> c) {
    }

    @NonNull
    @Override
    public Spliterator<E> spliterator() {
        return (Spliterator<E>) CantUseSpliterator.INSTANCE;
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
