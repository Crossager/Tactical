package net.crossager.tactical.api.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class LazyInitializedMap<K, V> extends AbstractMap<K, V> {
    private final Function<K, V> function;
    private final EntrySet entries = new EntrySet();

    public LazyInitializedMap(Function<K, V> function) {
        this.function = function;
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return entries;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        try {
            Entry<K, V> entry = entries.getForKey((K) key);
            if (entry.getValue() == null) entry.setValue(function.apply((K) key));
            return entry.getValue();
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public V put(K key, V value) {
        return entries.getForKey(key).setValue(value);
    }

    @Override
    public boolean containsKey(Object key) {
        return true;
    }

    @Nullable
    @Override
    public V putIfAbsent(K key, V value) {
        return get(key);
    }

    private class EntrySet extends AbstractSet<Entry<K, V>> {
        private final ArrayList<Entry<K, V>> internalList = new ArrayList<>();
        @Override
        public @NotNull Iterator<Entry<K, V>> iterator() {
            return internalList.iterator();
        }

        @Override
        public int size() {
            return internalList.size();
        }

        @Override
        public boolean add(Entry<K, V> kvEntry) {
            return internalList.add(kvEntry);
        }

        public Entry<K, V> getForKey(K key) {
            for (Entry<K, V> kvEntry : internalList) {
                if (kvEntry.getKey().equals(key)) return kvEntry;;
            }
            Entry<K, V> entry = new SimpleEntry<>(key, null);
            internalList.add(entry);
            return entry;
        }
    }
}
