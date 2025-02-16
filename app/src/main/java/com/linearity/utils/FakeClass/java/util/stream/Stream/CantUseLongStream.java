package com.linearity.utils.FakeClass.java.util.stream.Stream;

import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_LONG_ARRAY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;
import com.linearity.utils.FakeClass.java.util.CantUseLongSummaryStatistics;
import com.linearity.utils.FakeClass.java.util.CantUsePrimitiveIterator;
import com.linearity.utils.FakeClass.java.util.CantUseSpliterator;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class CantUseLongStream implements LongStream {
    public static final CantUseLongStream INSTANCE = new CantUseLongStream();
    static {
        FakeReturnClassMap.registerInstance(CantUseLongStream.class,INSTANCE);
    }
    public CantUseLongStream(){}

    @Override
    public LongStream filter(LongPredicate predicate) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public LongStream map(LongUnaryOperator mapper) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
        return (Stream<U>) CantUseStream.INSTANCE;
    }

    @Override
    public IntStream mapToInt(LongToIntFunction mapper) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public LongStream distinct() {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public LongStream sorted() {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public LongStream peek(LongConsumer action) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public LongStream limit(long maxSize) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public LongStream skip(long n) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public void forEach(LongConsumer action) {

    }

    @Override
    public void forEachOrdered(LongConsumer action) {

    }

    @Override
    public long[] toArray() {
        return EMPTY_LONG_ARRAY;
    }

    @Override
    public long reduce(long identity, LongBinaryOperator op) {
        return 0;
    }

    @Override
    public OptionalLong reduce(LongBinaryOperator op) {
        return OptionalLong.empty();
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return null;
    }

    @Override
    public long sum() {
        return 0;
    }

    @Override
    public OptionalLong min() {
        return OptionalLong.empty();
    }

    @Override
    public OptionalLong max() {
        return OptionalLong.empty();
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
    public LongSummaryStatistics summaryStatistics() {
        return CantUseLongSummaryStatistics.INSTANCE;
    }

    @Override
    public boolean anyMatch(LongPredicate predicate) {
        return false;
    }

    @Override
    public boolean allMatch(LongPredicate predicate) {
        return false;
    }

    @Override
    public boolean noneMatch(LongPredicate predicate) {
        return false;
    }

    @Override
    public OptionalLong findFirst() {
        return OptionalLong.empty();
    }

    @Override
    public OptionalLong findAny() {
        return OptionalLong.empty();
    }

    @Override
    public DoubleStream asDoubleStream() {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public Stream<Long> boxed() {
        return (Stream<Long>) CantUseStream.INSTANCE;
    }

    @Override
    public LongStream sequential() {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public LongStream parallel() {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public LongStream unordered() {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public LongStream onClose(Runnable closeHandler) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public void close() {

    }

    @Override
    public PrimitiveIterator.OfLong iterator() {
        return new CantUsePrimitiveIterator.PrimitiveIteratorOfLong();
    }

    @Override
    public Spliterator.OfLong spliterator() {
        return new CantUseSpliterator.OfLongClass();
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
