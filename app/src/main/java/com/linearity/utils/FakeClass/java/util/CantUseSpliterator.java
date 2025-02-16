package com.linearity.utils.FakeClass.java.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class CantUseSpliterator<E> implements Spliterator<E> {
    public static final CantUseSpliterator<?> INSTANCE = new CantUseSpliterator<>();
    static {
        FakeReturnClassMap.registerInstance(Spliterator.class,INSTANCE);
    }
    public CantUseSpliterator(){}
    @Override
    public boolean tryAdvance(Consumer action) {
        return false;
    }

    @Override
    public void forEachRemaining(Consumer action) {
    }

    @Override
    public Spliterator<E> trySplit() {
        return (Spliterator<E>) CantUseSpliterator.INSTANCE;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public long getExactSizeIfKnown() {
        return 0;
    }

    @Override
    public int characteristics() {
        return 0;
    }

    @Override
    public boolean hasCharacteristics(int characteristics) {
        return false;
    }

    @Override
    public Comparator<E> getComparator() {
        return (Comparator<E>) CantUseComparator.INSTANCE;
    }

    @NonNull
    @Override
    public String toString() {
        return "Foolish!";
    }

    @Override
    public int hashCode() {
        return 0;
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
    protected void finalize() throws Throwable {
    }


    /**
     * A Spliterator specialized for {@code int} values.
     * @since 1.8
     */
    public interface OfInt extends Spliterator.OfInt {
        @Override
        default long estimateSize(){return 0;}

        @Override
        default int characteristics(){return 0;}

        @Override
        default boolean hasCharacteristics(int characteristics) {
            return false;
        }

        @Override
        default long getExactSizeIfKnown() {
            return 0;
        }

        @Override
        default OfInt trySplit(){return this;}

        @Override
        default boolean tryAdvance(IntConsumer action){return false;}

        @Override
        default void forEachRemaining(IntConsumer action) {
            do { } while (tryAdvance(action));
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code IntConsumer} then it is cast
         * to {@code IntConsumer} and passed to
         * {@link #tryAdvance(IntConsumer)}; otherwise
         * the action is adapted to an instance of {@code IntConsumer}, by
         * boxing the argument of {@code IntConsumer}, and then passed to
         * {@link #tryAdvance(IntConsumer)}.
         */
        @Override
        default boolean tryAdvance(Consumer<? super Integer> action) {
            return false;
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code IntConsumer} then it is cast
         * to {@code IntConsumer} and passed to
         * {@link #forEachRemaining(IntConsumer)}; otherwise
         * the action is adapted to an instance of {@code IntConsumer}, by
         * boxing the argument of {@code IntConsumer}, and then passed to
         * {@link #forEachRemaining(IntConsumer)}.
         */
        @Override
        default void forEachRemaining(Consumer<? super Integer> action) {

        }

        @Override
        default Comparator<? super Integer> getComparator() {
            return (Comparator<? super Integer>) CantUseComparator.INSTANCE;
        }
    }

    /**
     * A Spliterator specialized for {@code long} values.
     * @since 1.8
     */
    public interface OfLong extends Spliterator.OfLong {
        @Override
        default long estimateSize(){return 0;}

        @Override
        default int characteristics(){return 0;}

        @Override
        default boolean hasCharacteristics(int characteristics) {
            return false;
        }

        @Override
        default long getExactSizeIfKnown() {
            return 0;
        }


        @Override
        default OfLong trySplit(){return this;}

        @Override
        default boolean tryAdvance(LongConsumer action){return false;}

        @Override
        default void forEachRemaining(LongConsumer action) {
            do { } while (tryAdvance(action));
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code LongConsumer} then it is cast
         * to {@code LongConsumer} and passed to
         * {@link #tryAdvance(LongConsumer)}; otherwise
         * the action is adapted to an instance of {@code LongConsumer}, by
         * boxing the argument of {@code LongConsumer}, and then passed to
         * {@link #tryAdvance(LongConsumer)}.
         */
        @Override
        default boolean tryAdvance(Consumer<? super Long> action) {
            return false;
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code LongConsumer} then it is cast
         * to {@code LongConsumer} and passed to
         * {@link #forEachRemaining(LongConsumer)}; otherwise
         * the action is adapted to an instance of {@code LongConsumer}, by
         * boxing the argument of {@code LongConsumer}, and then passed to
         * {@link #forEachRemaining(LongConsumer)}.
         */
        @Override
        default void forEachRemaining(Consumer<? super Long> action) {}

        @Override
        default Comparator<? super Long> getComparator() {
            return (Comparator<? super Long>) CantUseComparator.INSTANCE;
        }
    }

    /**
     * A Spliterator specialized for {@code double} values.
     * @since 1.8
     */
    public interface OfDouble extends Spliterator.OfDouble {
        @Override
        default long estimateSize(){return 0;}

        @Override
        default int characteristics(){return 0;}

        @Override
        default boolean hasCharacteristics(int characteristics) {
            return false;
        }

        @Override
        default long getExactSizeIfKnown() {
            return 0;
        }
        @Override
        default OfDouble trySplit(){
            return this;
        }

        @Override
        default boolean tryAdvance(DoubleConsumer action) {
            return false;
        }

        @Override
        default void forEachRemaining(DoubleConsumer action) {

        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code DoubleConsumer} then it is
         * cast to {@code DoubleConsumer} and passed to
         * {@link #tryAdvance(DoubleConsumer)}; otherwise
         * the action is adapted to an instance of {@code DoubleConsumer}, by
         * boxing the argument of {@code DoubleConsumer}, and then passed to
         * {@link #tryAdvance(DoubleConsumer)}.
         */
        @Override
        default boolean tryAdvance(Consumer<? super Double> action) {return false;}

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code DoubleConsumer} then it is
         * cast to {@code DoubleConsumer} and passed to
         * {@link #forEachRemaining(DoubleConsumer)};
         * otherwise the action is adapted to an instance of
         * {@code DoubleConsumer}, by boxing the argument of
         * {@code DoubleConsumer}, and then passed to
         * {@link #forEachRemaining(DoubleConsumer)}.
         */
        @Override
        default void forEachRemaining(Consumer<? super Double> action) {

        }

        @Override
        default Comparator<? super Double> getComparator() {
            return (Comparator<? super Double>) CantUseComparator.INSTANCE;
        }
    }

    public static class OfIntClass implements OfInt{
        public static final OfIntClass INSTANCE = new OfIntClass();
        static {
            FakeReturnClassMap.registerInstance(CantUseSpliterator.OfInt.class,INSTANCE);
        }
        public OfIntClass(){}

        @Override
        public OfInt trySplit() {
            return new OfIntClass();
        }

        @Override
        public long estimateSize() {
            return 0L;
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
    public static class OfLongClass implements OfLong{
        public static final OfLongClass INSTANCE = new OfLongClass();
        static {
            FakeReturnClassMap.registerInstance(OfLongClass.class,INSTANCE);
        }
        public OfLongClass(){}

        @Override
        public OfLong trySplit() {
            return new OfLongClass();
        }


        @Override
        public long estimateSize() {
            return 0L;
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
    public static class OfDoubleClass implements OfDouble{
        public static final OfDoubleClass INSTANCE = new OfDoubleClass();
        static {
            FakeReturnClassMap.registerInstance(CantUseSpliterator.OfDouble.class,INSTANCE);
        }
        public OfDoubleClass(){}

        @Override
        public long estimateSize() {
            return 0L;
        }

        @Override
        public OfDouble trySplit() {
            return OfDoubleClass.INSTANCE;
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
