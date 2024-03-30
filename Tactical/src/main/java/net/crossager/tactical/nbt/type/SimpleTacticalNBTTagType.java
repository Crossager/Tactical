package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class SimpleTacticalNBTTagType<V, T extends TacticalNBTTag<V>> implements TacticalNBTTagType<V, T> {
    private final Class<V> valueClass;
    private final Function<V, T> constructor;

    public SimpleTacticalNBTTagType(Class<V> valueClass, Function<V, T> constructor) {
        this.valueClass = valueClass;
        this.constructor = constructor;
    }

    @Override
    public @NotNull T create(@NotNull V value) {
        return constructor.apply(value);
    }

    @Override
    public @NotNull Class<V> valueClass() {
        return valueClass;
    }
}
