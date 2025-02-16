package com.linearity.utils.FakeClass.java.util.stream.Stream;

import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_OBJECT_ARRAY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;
import com.linearity.utils.FakeClass.java.util.CantUseListIterator;
import com.linearity.utils.FakeClass.java.util.CantUseSpliterator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class CantUseStream<E> implements Stream<E> {
    public static final CantUseStream<?> INSTANCE = new CantUseStream<>();
    static {
        FakeReturnClassMap.registerInstance(CantUseStream.class,INSTANCE);
    }
    public CantUseStream(){}
    @Override
    public Stream<E> filter(Predicate<? super E> predicate) {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public <R> Stream<R> map(Function<? super E, ? extends R> mapper) {
        return (Stream<R>) CantUseStream.INSTANCE;
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super E> mapper) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super E> mapper) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super E> mapper) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super E, ? extends Stream<? extends R>> mapper) {
        return (Stream<R>) CantUseStream.INSTANCE;
    }

    @Override
    public IntStream flatMapToInt(Function<? super E, ? extends IntStream> mapper) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public LongStream flatMapToLong(Function<? super E, ? extends LongStream> mapper) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super E, ? extends DoubleStream> mapper) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public Stream<E> distinct() {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public Stream<E> sorted() {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public Stream<E> sorted(Comparator<? super E> comparator) {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public Stream<E> peek(Consumer<? super E> action) {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public Stream<E> limit(long maxSize) {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public Stream<E> skip(long n) {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public void forEach(Consumer<? super E> action) {

    }

    @Override
    public void forEachOrdered(Consumer<? super E> action) {

    }

    @Override
    public Object[] toArray() {
        return EMPTY_OBJECT_ARRAY;
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return null;
    }

    @Override
    public E reduce(E identity, BinaryOperator<E> accumulator) {
        return identity;
    }

    @Override
    public Optional<E> reduce(BinaryOperator<E> accumulator) {
        return Optional.empty();
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super E, U> accumulator, BinaryOperator<U> combiner) {
        return identity;
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super E> accumulator, BiConsumer<R, R> combiner) {
        return null;
    }

    @Override
    public <R, A> R collect(Collector<? super E, A, R> collector) {
        return null;
    }

    @Override
    public Optional<E> min(Comparator<? super E> comparator) {
        return Optional.empty();
    }

    @Override
    public Optional<E> max(Comparator<? super E> comparator) {
        return Optional.empty();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean anyMatch(Predicate<? super E> predicate) {
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super E> predicate) {
        return false;
    }

    @Override
    public boolean noneMatch(Predicate<? super E> predicate) {
        return false;
    }

    @Override
    public Optional<E> findFirst() {
        return Optional.empty();
    }

    @Override
    public Optional<E> findAny() {
        return Optional.empty();
    }

    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) CantUseListIterator.INSTANCE;
    }

    @Override
    public Spliterator<E> spliterator() {
        return (Spliterator<E>) CantUseSpliterator.INSTANCE;
    }

    @Override
    public boolean isParallel() {
        return false;
    }

    @Override
    public Stream<E> sequential() {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public Stream<E> parallel() {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public Stream<E> unordered() {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public Stream<E> onClose(Runnable closeHandler) {
        return (Stream<E>) CantUseStream.INSTANCE;
    }

    @Override
    public void close() {

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
