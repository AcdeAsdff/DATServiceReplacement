package com.linearity.utils.FakeClass.java.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class CantUsePrimitiveIterator<T,E> implements PrimitiveIterator<T,E> {
    public static final CantUsePrimitiveIterator<?,?> INSTANCE = new CantUsePrimitiveIterator<>();
    static {
        FakeReturnClassMap.registerInstance(PrimitiveIterator.class,INSTANCE);
    }
    @Override
    public void forEachRemaining(Object action) {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }

    @Override
    public void remove() {
    }

    @Override
    public void forEachRemaining(@NonNull Consumer action) {
    }

    public CantUsePrimitiveIterator(){}
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


    /**
     * An Iterator specialized for {@code int} values.
     * @since 1.8
     */
    public static class PrimitiveIteratorOfInt implements OfInt {
        public static final PrimitiveIteratorOfInt INSTANCE = new PrimitiveIteratorOfInt();
        static {
            FakeReturnClassMap.registerInstance(PrimitiveIteratorOfInt.class,INSTANCE);
        }


        @Override
        public int nextInt() {
            return 0;
        }

        @Override
        public void forEachRemaining(IntConsumer action) {
        }

        @Override
        public Integer next() {
            return 0;
        }

        @Override
        public void remove() {
        }

        @Override
        public void forEachRemaining(Consumer<? super Integer> action) {
        }

        @Override
        public boolean hasNext() {
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

    /**
     * An Iterator specialized for {@code long} values.
     * @since 1.8
     */
    public static class PrimitiveIteratorOfLong implements OfLong {
        public static final PrimitiveIteratorOfLong INSTANCE = new PrimitiveIteratorOfLong();
        static {
            FakeReturnClassMap.registerInstance(PrimitiveIteratorOfLong.class,INSTANCE);
        }
        @Override
        public long nextLong() {
            return 0;
        }

        @Override
        public void forEachRemaining(LongConsumer action) {
        }

        @Override
        public Long next() {
            return 0L;
        }

        @Override
        public void remove() {
        }

        @Override
        public void forEachRemaining(Consumer<? super Long> action) {
        }

        @Override
        public boolean hasNext() {
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

    /**
     * An Iterator specialized for {@code double} values.
     * @since 1.8
     */
    public static class PrimitiveIteratorOfDouble implements OfDouble {
        public static final PrimitiveIteratorOfDouble INSTANCE = new PrimitiveIteratorOfDouble();
        static {
            FakeReturnClassMap.registerInstance(PrimitiveIteratorOfDouble.class,INSTANCE);
        }
        @Override
        public double nextDouble() {
            return 0;
        }

        @Override
        public void forEachRemaining(DoubleConsumer action) {
        }

        @Override
        public Double next() {
            return 0.;
        }

        @Override
        public void remove() {
        }

        @Override
        public void forEachRemaining(Consumer<? super Double> action) {
        }

        @Override
        public boolean hasNext() {
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
}
