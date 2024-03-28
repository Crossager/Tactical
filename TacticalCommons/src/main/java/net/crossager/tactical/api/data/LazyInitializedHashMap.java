package net.crossager.tactical.api.data;

import java.util.HashMap;
import java.util.function.Function;

public class LazyInitializedHashMap<K, V> extends HashMap<K, V> {
    private final Function<K, V> function;

    public LazyInitializedHashMap(Function<K, V> function) {
        this.function = function;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        try {
            return super.computeIfAbsent((K) key, function);
        } catch (ClassCastException e) {
            return null;
        }
    }
}
