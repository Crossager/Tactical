package net.crossager.tactical.api.util.function;

import java.util.Objects;

@FunctionalInterface
public interface FloatConsumer {
    void accept(float f);

    default FloatConsumer andThen(FloatConsumer after) {
        Objects.requireNonNull(after);
        return (float f) -> { accept(f); after.accept(f); };
    }
}