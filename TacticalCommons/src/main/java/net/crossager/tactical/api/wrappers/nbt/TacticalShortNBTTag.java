package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalShortNBTTagType;
import org.jetbrains.annotations.NotNull;

public interface TacticalShortNBTTag extends TacticalNumberNBTTag<Short> {
    @NotNull
    TacticalShortNBTTag copy();

    @NotNull
    static TacticalShortNBTTag of(short s) {
        return TacticalShortNBTTagType.type().create(s);
    }
}
