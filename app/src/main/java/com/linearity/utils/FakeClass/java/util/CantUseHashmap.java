package com.linearity.utils.FakeClass.java.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linearity.utils.FakeClass.FakeReturnClasses.FakeReturnClassMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CantUseHashmap<K,V> extends HashMap<K,V> {
    public static final CantUseHashmap<?,?> INSTANCE = new CantUseHashmap<>();
    static {
        FakeReturnClassMap.registerInstance(CantUseHashmap.class,INSTANCE);
    }
    public CantUseHashmap(){
        super();
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        return value;
    }

    @Nullable
    @Override
    public V get(@Nullable Object key) {
        return null;
    }

    @Nullable
    @Override
    public V getOrDefault(@Nullable Object key, @Nullable V defaultValue) {
        return defaultValue;
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return false;
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void putAll(@NonNull Map<? extends K, ? extends V> m) {}

    @Nullable
    @Override
    public V remove(@Nullable Object key) {
        return null;
    }

    @Override
    public boolean replace(K key, @Nullable V oldValue, V newValue) {
        return false;
    }

    @Override
    public void replaceAll(@NonNull BiFunction<? super K, ? super V, ? extends V> function) {
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @NonNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return (Set<Entry<K, V>>) CantUseHashSet.INSTANCE;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Nullable
    @Override
    public V computeIfAbsent(K key, @NonNull Function<? super K, ? extends V> mappingFunction) {
        return null;
    }

    @NonNull
    @Override
    public Object clone() {
        return this;
    }

    @Override
    public boolean remove(@Nullable Object key, @Nullable Object value) {
        return true;
    }

    @NonNull
    @Override
    public Set<K> keySet() {
        return (Set<K>) CantUseHashSet.INSTANCE;
    }

    @NonNull
    @Override
    public Collection<V> values() {
        return (Collection<V>) CantUseHashSet.INSTANCE;
    }

    @Nullable
    @Override
    public V compute(K key, @NonNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Nullable
    @Override
    public V computeIfPresent(K key, @NonNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Nullable
    @Override
    public V merge(K key, @NonNull V value, @NonNull BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return value;
    }

    @Nullable
    @Override
    public V putIfAbsent(K key, V value) {
        return value;
    }

    @Nullable
    @Override
    public V replace(K key, V value) {
        return value;
    }

    @Override
    public void forEach(@NonNull BiConsumer<? super K, ? super V> action) {
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
