package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.*;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagCompoundType;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagType;
import net.crossager.tactical.util.InternalUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class SimpleTacticalNBTTagCompound extends SimpleTacticalNBTTag<Map<String, TacticalNBTTag<?>>> implements TacticalNBTTagCompound {
    private static final char OBJECT_START = '{';
    private static final char OBJECT_END = '}';
    private static final char NAME_IDENTIFIER = '"';
    private static final char KEY_VALUE_IDENTIFIER = ':';

    public SimpleTacticalNBTTagCompound(Map<String, TacticalNBTTag<?>> value) {
        super(value);
    }

    @Override
    public @NotNull String serialize() {
        return OBJECT_START +
                String.join(", ",
                        value.entrySet().stream().map(entry -> NAME_IDENTIFIER + entry.getKey() + NAME_IDENTIFIER +
                                KEY_VALUE_IDENTIFIER + ' ' + entry.getValue().serialize()).toList()) +
                OBJECT_END;
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        for (String key : value.keySet()) {
            TacticalNBTTag<?> tag = value.get(key);
            dataOutput.writeByte(tag.type().id());
            if (tag.type().id() != 0) {
                dataOutput.writeUTF(key);
                tag.serialize(dataOutput);
            }
        }
        dataOutput.writeByte(0);
    }

    @Override
    public @NotNull TacticalNBTTagType<Map<String, TacticalNBTTag<?>>, ? extends TacticalNBTTag<Map<String, TacticalNBTTag<?>>>> type() {
        return TacticalNBTTagCompoundType.type();
    }

    @Override
    public @NotNull TacticalNBTTagCompound copy() {
        return new SimpleTacticalNBTTagCompound(new LinkedHashMap<>(value));
    }

    @Override
    public @NotNull TacticalNBTTagCompound set(@NotNull String path, @NotNull TacticalNBTTag<?> nbtTag) {
        value.put(path, nbtTag);
        return this;
    }

    @Override
    public @NotNull TacticalNBTTagCompound set(@NotNull String path, byte value) {
        return set(path, new SimpleTacticalByteNBTTag(value));
    }

    @Override
    public @NotNull TacticalNBTTagCompound set(@NotNull String path, short value) {
        return set(path, new SimpleTacticalShortNBTTag(value));
    }

    @Override
    public @NotNull TacticalNBTTagCompound set(@NotNull String path, int value) {
        return set(path, new SimpleTacticalIntNBTTag(value));
    }

    @Override
    public @NotNull TacticalNBTTagCompound set(@NotNull String path, long value) {
        return set(path, new SimpleTacticalLongNBTTag(value));
    }

    @Override
    public @NotNull TacticalNBTTagCompound set(@NotNull String path, float value) {
        return set(path, new SimpleTacticalFloatNBTTag(value));
    }

    @Override
    public @NotNull TacticalNBTTagCompound set(@NotNull String path, double value) {
        return set(path, new SimpleTacticalDoubleNBTTag(value));
    }

    @Override
    public @NotNull TacticalNBTTagCompound set(@NotNull String path, boolean value) {
        return set(path, TacticalByteNBTTag.of(value));
    }

    @Override
    public @NotNull TacticalNBTTagCompound set(@NotNull String path, @NotNull String value) {
        return set(path, new SimpleTacticalStringNBTTag(value));
    }

    @Override
    public @NotNull TacticalNBTTagCompound clear() {
        value.clear();
        return this;
    }

    @Override
    public boolean contains(@NotNull String path) {
        return value.containsKey(path);
    }

    @Override
    public @NotNull Collection<TacticalNBTTag<?>> values() {
        return value.values();
    }

    @Override
    public @NotNull Set<String> keySet() {
        return value.keySet();
    }

    @Override
    public @NotNull TacticalNBTTag<?> get(@NotNull String path) {
        return InternalUtils.hasValue(value.get(path), path);
    }

    @Override
    public @NotNull TacticalNBTTag<?> get(@NotNull String path, @NotNull TacticalNBTTag<?> def) {
        TacticalNBTTag<?> tag = value.get(path);
        if (tag != null) return tag;
        return def;
    }

    @Override
    public <T extends TacticalNBTTag<?>> @NotNull T getAs(@NotNull String path, @NotNull Class<T> nbtClass, @NotNull T def) {
        try {
            return getAs(path, nbtClass);
        } catch (NoSuchElementException e) {
            return def;
        }
    }

    @Override
    public <T extends TacticalNBTTag<?>> @NotNull T getAs(@NotNull String path, @NotNull Class<T> nbtClass) {
        return InternalUtils.cast(get(path), nbtClass);
    }

    @Override
    public @NotNull TacticalNumberNBTTag<?> getNumber(@NotNull String path) {
        return getAs(path, TacticalNumberNBTTag.class);
    }

    @Override
    public @NotNull TacticalByteNBTTag getByte(@NotNull String path) {
        return getAs(path, TacticalByteNBTTag.class);
    }

    @Override
    public @NotNull TacticalShortNBTTag getShort(@NotNull String path) {
        return getAs(path, TacticalShortNBTTag.class);
    }

    @Override
    public @NotNull TacticalIntNBTTag getInt(@NotNull String path) {
        return getAs(path, TacticalIntNBTTag.class);
    }

    @Override
    public @NotNull TacticalLongNBTTag getLong(@NotNull String path) {
        return getAs(path, TacticalLongNBTTag.class);
    }

    @Override
    public @NotNull TacticalFloatNBTTag getFloat(@NotNull String path) {
        return getAs(path, TacticalFloatNBTTag.class);
    }

    @Override
    public @NotNull TacticalDoubleNBTTag getDouble(@NotNull String path) {
        return getAs(path, TacticalDoubleNBTTag.class);
    }

    @Override
    public @NotNull TacticalStringNBTTag getString(@NotNull String path) {
        return getAs(path, TacticalStringNBTTag.class);
    }

    @Override
    public @NotNull TacticalListNBTTag<?> getList(@NotNull String path) {
        return getAs(path, TacticalListNBTTag.class);
    }

    @Override
    public @NotNull TacticalByteListNBTTag getByteList(@NotNull String path) {
        return getAs(path, TacticalByteListNBTTag.class);
    }

    @Override
    public @NotNull TacticalIntListNBTTag getIntList(@NotNull String path) {
        return getAs(path, TacticalIntListNBTTag.class);
    }

    @Override
    public @NotNull TacticalLongListNBTTag getLongList(@NotNull String path) {
        return getAs(path, TacticalLongListNBTTag.class);
    }
}
