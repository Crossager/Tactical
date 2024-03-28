package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalDoubleNBTTagType;
import org.jetbrains.annotations.NotNull;

public interface TacticalDoubleNBTTag extends TacticalNumberNBTTag<Double> {
    @NotNull
    TacticalDoubleNBTTag copy();

    @NotNull
    static TacticalDoubleNBTTag of(double d) {
        return TacticalDoubleNBTTagType.type().create(d);
    }
}
