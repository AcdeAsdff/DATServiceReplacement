package com.linearity.utils.FakeClass.java.util.stream.Stream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;
import com.linearity.utils.FakeClass.java.util.CantUseDoubleSummaryStatistics;
import com.linearity.utils.FakeClass.java.util.CantUsePrimitiveIterator;
import com.linearity.utils.FakeClass.java.util.CantUseSpliterator;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class CantUseDoubleStream implements DoubleStream {
    public static final CantUseDoubleStream INSTANCE = new CantUseDoubleStream();
    static {
        FakeReturnClassMap.registerInstance(CantUseDoubleStream.class,INSTANCE);
    }
    public CantUseDoubleStream(){}
    @Override
    public DoubleStream filter(DoublePredicate predicate) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public DoubleStream map(DoubleUnaryOperator mapper) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        return (Stream<U>) CantUseStream.INSTANCE;
    }

    @Override
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        return CantUseIntStream.INSTANCE;
    }

    @Override
    public LongStream mapToLong(DoubleToLongFunction mapper) {
        return CantUseLongStream.INSTANCE;
    }

    @Override
    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public DoubleStream distinct() {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public DoubleStream sorted() {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public DoubleStream peek(DoubleConsumer action) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public DoubleStream limit(long maxSize) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public DoubleStream skip(long n) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public void forEach(DoubleConsumer action) {

    }

    @Override
    public void forEachOrdered(DoubleConsumer action) {

    }

    @Override
    public double[] toArray() {
        return new double[0];
    }

    @Override
    public double reduce(double identity, DoubleBinaryOperator op) {
        return 0;
    }

    @Override
    public OptionalDouble reduce(DoubleBinaryOperator op) {
        return OptionalDouble.empty();
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return null;
    }

    @Override
    public double sum() {
        return 0;
    }

    @Override
    public OptionalDouble min() {
        return OptionalDouble.empty();
    }

    @Override
    public OptionalDouble max() {
        return OptionalDouble.empty();
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
    public DoubleSummaryStatistics summaryStatistics() {
        return CantUseDoubleSummaryStatistics.INSTANCE;
    }

    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        return false;
    }

    @Override
    public boolean allMatch(DoublePredicate predicate) {
        return false;
    }

    @Override
    public boolean noneMatch(DoublePredicate predicate) {
        return false;
    }

    @Override
    public OptionalDouble findFirst() {
        return OptionalDouble.empty();
    }

    @Override
    public OptionalDouble findAny() {
        return OptionalDouble.empty();
    }

    @Override
    public Stream<Double> boxed() {
        return (Stream<Double>) CantUseStream.INSTANCE;
    }

    @Override
    public DoubleStream sequential() {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public DoubleStream parallel() {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public DoubleStream unordered() {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public DoubleStream onClose(Runnable closeHandler) {
        return CantUseDoubleStream.INSTANCE;
    }

    @Override
    public void close() {

    }

    @Override
    public PrimitiveIterator.OfDouble iterator() {
        return CantUsePrimitiveIterator.PrimitiveIteratorOfDouble.INSTANCE;
    }

    @Override
    public Spliterator.OfDouble spliterator() {
        return CantUseSpliterator.OfDoubleClass.INSTANCE;
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
