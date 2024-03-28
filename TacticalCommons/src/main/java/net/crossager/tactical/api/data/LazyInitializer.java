package net.crossager.tactical.api.data;

import java.util.Optional;
import java.util.function.Supplier;

public class LazyInitializer<T> implements Supplier<T> {
    private final Supplier<T> supplier;
    private T value = null;
    private boolean isInitialized = false;

    public LazyInitializer(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (!isInitialized) {
            isInitialized = true;
            value = supplier.get();
        }
        return value;
    }

    public void clear() {
        isInitialized = false;
        value = null;
    }

    public Optional<T> asOptional() {
        return Optional.ofNullable(value);
    }

    public boolean isInitialized() {
        return isInitialized;
    }
}
