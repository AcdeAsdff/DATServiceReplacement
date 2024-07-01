package com.linearity.utils.FakeClass.java.util.stream.Stream;

import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_INT_ARRAY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;
import com.linearity.utils.FakeClass.java.util.CantUseIntSummaryStatistics;
import com.linearity.utils.FakeClass.java.util.CantUsePrimitiveIterator;
import com.linearity.utils.FakeClass.java.util.CantUseSpliterator;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class CantUseIntStream implements IntStream {
    public static final CantUseIntStream INSTANCE = new CantUseIntStream();
    static {
        FakeReturnClassMap.registerInstance(CantUseIntStream.class,INSTANCE);
    }
    public CantUseIntStream(){}
    @Override
    public IntStream filter(IntPredicate predicate) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        return (Stream<U>) CantUseStream.INSTANCE;
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public IntStream distinct() {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public IntStream sorted() {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public IntStream peek(IntConsumer action) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public IntStream limit(long maxSize) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public IntStream skip(long n) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public void forEach(IntConsumer action) {

    }

    @Override
    public void forEachOrdered(IntConsumer action) {

    }

    @Override
    public int[] toArray() {
        return EMPTY_INT_ARRAY;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        return 0;
    }

    @Override
    public OptionalInt reduce(IntBinaryOperator op) {
        return OptionalInt.empty();
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return null;
    }

    @Override
    public int sum() {
        return 0;
    }

    @Override
    public OptionalInt min() {
        return OptionalInt.empty();
    }

    @Override
    public OptionalInt max() {
        return OptionalInt.empty();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public OptionalDouble average() {
        return OptionalDouble.empty();
    }

    @Override
    public IntSummaryStatistics summaryStatistics() {
        return CantUseIntSummaryStatistics.INSTANCE;
    }

    @Override
    public boolean anyMatch(IntPredicate predicate) {
        return false;
    }

    @Override
    public boolean allMatch(IntPredicate predicate) {
        return false;
    }

    @Override
    public boolean noneMatch(IntPredicate predicate) {
        return false;
    }

    @Override
    public OptionalInt findFirst() {
        return OptionalInt.empty();
    }

    @Override
    public OptionalInt findAny() {
        return OptionalInt.empty();
    }

    @Override
    public LongStream asLongStream() {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public DoubleStream asDoubleStream() {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public Stream<Integer> boxed() {
        return (Stream<Integer>) CantUseStream.INSTANCE;
    }

    @Override
    public IntStream sequential() {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public IntStream parallel() {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public IntStream unordered() {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public IntStream onClose(Runnable closeHandler) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public void close() {

    }

    @Override
    public PrimitiveIterator.OfInt iterator() {
        return CantUsePrimitiveIterator.PrimitiveIteratorOfInt.INSTANCE;
    }

    @Override
    public Spliterator.OfInt spliterator() {
        return CantUseSpliterator.OfIntClass.INSTANCE;
    }

    @Override
    public boolean isParallel() {
        return false;
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
