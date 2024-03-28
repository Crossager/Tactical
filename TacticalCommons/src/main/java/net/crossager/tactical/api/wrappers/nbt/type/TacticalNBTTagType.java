package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;

public interface TacticalNBTTagType<V, T extends TacticalNBTTag<V>> {
    @NotNull
    T create(@NotNull V value);

    @NotNull
    Class<V> valueClass();

    byte id();

    @NotNull
    T read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException;
}
