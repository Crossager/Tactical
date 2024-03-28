package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagCompoundType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public interface TacticalNBTTagCompound extends TacticalNBTTag<Map<String, TacticalNBTTag<?>>> {
    @NotNull
    TacticalNBTTagCompound copy();

    @Contract("_, _ -> this")
    @NotNull
    TacticalNBTTagCompound set(@NotNull String path, @NotNull TacticalNBTTag<?> nbtTag);

    @Contract("_, _ -> this")
    @NotNull
    TacticalNBTTagCompound set(@NotNull String path, byte value);

    @Contract("_, _ -> this")
    @NotNull
    TacticalNBTTagCompound set(@NotNull String path, short value);

    @Contract("_, _ -> this")
    @NotNull
    TacticalNBTTagCompound set(@NotNull String path, int value);

    @Contract("_, _ -> this")
    @NotNull
    TacticalNBTTagCompound set(@NotNull String path, long value);

    @Contract("_, _ -> this")
    @NotNull
    TacticalNBTTagCompound set(@NotNull String path, float value);

    @Contract("_, _ -> this")
    @NotNull
    TacticalNBTTagCompound set(@NotNull String path, double value);

    @Contract("_, _ -> this")
    @NotNull
    TacticalNBTTagCompound set(@NotNull String path, boolean value);

    @Contract("_, _ -> this")
    @NotNull
    TacticalNBTTagCompound set(@NotNull String path, @NotNull String value);

    @Contract(" -> this")
    @NotNull
    TacticalNBTTagCompound clear();

    boolean contains(@NotNull String path);

    @NotNull
    Collection<TacticalNBTTag<?>> values();

    @NotNull
    Set<String> keySet();

    @NotNull
    TacticalNBTTag<?> get(@NotNull String path);

    @NotNull
    TacticalNBTTag<?> get(@NotNull String path, @NotNull TacticalNBTTag<?> def);

    @NotNull
    <T extends TacticalNBTTag<?>> T getAs(@NotNull String path, @NotNull Class<T> nbtClass);

    @NotNull
    <T extends TacticalNBTTag<?>> T getAs(@NotNull String path, @NotNull Class<T> nbtClass, @NotNull T def);

    @NotNull
    TacticalNumberNBTTag<?> getNumber(@NotNull String path);

    @NotNull
    TacticalByteNBTTag getByte(@NotNull String path);

    @NotNull
    TacticalShortNBTTag getShort(@NotNull String path);

    @NotNull
    TacticalIntNBTTag getInt(@NotNull String path);

    @NotNull
    TacticalLongNBTTag getLong(@NotNull String path);

    @NotNull
    TacticalFloatNBTTag getFloat(@NotNull String path);

    @NotNull
    TacticalDoubleNBTTag getDouble(@NotNull String path);

    @NotNull
    TacticalStringNBTTag getString(@NotNull String path);

    @NotNull
    TacticalListNBTTag<?> getList(@NotNull String path);

    @NotNull
    TacticalByteListNBTTag getByteList(@NotNull String path);

    @NotNull
    TacticalIntListNBTTag getIntList(@NotNull String path);

    @NotNull
    TacticalLongListNBTTag getLongList(@NotNull String path);

    @NotNull
    static TacticalNBTTagCompound of() {
        return TacticalNBTTagCompoundType.type().create(new LinkedHashMap<>());
    }
}
