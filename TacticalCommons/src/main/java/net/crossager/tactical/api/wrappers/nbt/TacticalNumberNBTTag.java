package net.crossager.tactical.api.wrappers.nbt;

import org.jetbrains.annotations.NotNull;

public interface TacticalNumberNBTTag<T extends Number> extends TacticalNBTTag<T> {
    @NotNull
    TacticalNumberNBTTag<T> copy();
}
