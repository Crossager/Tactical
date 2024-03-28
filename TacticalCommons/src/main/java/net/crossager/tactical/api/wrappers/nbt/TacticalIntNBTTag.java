package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalIntNBTTagType;
import org.jetbrains.annotations.NotNull;

public interface TacticalIntNBTTag extends TacticalNumberNBTTag<Integer> {
    @NotNull
    TacticalIntNBTTag copy();

    @NotNull
    static TacticalIntNBTTag of(int i) {
        return TacticalIntNBTTagType.type().create(i);
    }
}
