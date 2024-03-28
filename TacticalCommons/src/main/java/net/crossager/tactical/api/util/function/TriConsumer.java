package net.crossager.tactical.api.util.function;

public interface TriConsumer<T, U, C> {
    void accept(T t, U u, C c);
}
