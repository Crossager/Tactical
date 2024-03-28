package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalLongNBTTagType;
import org.jetbrains.annotations.NotNull;

public interface TacticalLongNBTTag extends TacticalNumberNBTTag<Long> {
    @NotNull
    TacticalLongNBTTag copy();

    @NotNull
    static TacticalLongNBTTag of(long l) {
        return TacticalLongNBTTagType.type().create(l);
    }
}
