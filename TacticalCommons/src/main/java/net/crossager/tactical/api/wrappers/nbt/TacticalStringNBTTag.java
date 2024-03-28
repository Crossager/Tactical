package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalStringNBTTagType;
import org.jetbrains.annotations.NotNull;

public interface TacticalStringNBTTag extends TacticalNBTTag<String> {
    TacticalStringNBTTag EMPTY = TacticalStringNBTTagType.type().create("");
    @NotNull
    TacticalStringNBTTag copy();

    @NotNull
    static TacticalStringNBTTag of(@NotNull String string) {
        return string.isEmpty() ? EMPTY : TacticalStringNBTTagType.type().create(string);
    }
}
