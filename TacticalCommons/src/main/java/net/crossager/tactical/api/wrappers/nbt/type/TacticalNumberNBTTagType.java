package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public interface TacticalNumberNBTTagType<V extends Number, T extends TacticalNBTTag<V>> extends TacticalNBTTagType<V, T> {

    @NotNull
    Pattern numberPattern();

    @NotNull
    T parse(@NotNull String input);
}
