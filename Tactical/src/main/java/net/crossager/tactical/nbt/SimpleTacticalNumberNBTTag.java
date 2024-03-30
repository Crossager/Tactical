package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagType;
import net.crossager.tactical.api.wrappers.nbt.TacticalNumberNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNumberNBTTagType;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleTacticalNumberNBTTag<V extends Number, T extends TacticalNumberNBTTag<V>> extends SimpleTacticalNBTTag<V> implements TacticalNumberNBTTag<V> {
    protected final TacticalNumberNBTTagType<V, T> type;

    public SimpleTacticalNumberNBTTag(V number, TacticalNumberNBTTagType<V, T> type) {
        super(number);
        this.type = type;
    }

    @Override
    public @NotNull String serialize() {
        return value.toString() + suffix();
    }

    protected abstract String suffix();

    @Override
    public @NotNull TacticalNBTTagType<V, ? extends TacticalNBTTag<V>> type() {
        return type;
    }
}
