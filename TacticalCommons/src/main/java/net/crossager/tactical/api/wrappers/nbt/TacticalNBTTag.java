package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public interface TacticalNBTTag<T> {
    @NotNull
    String serialize();

    void serialize(@NotNull DataOutput dataOutput) throws IOException;

    @NotNull
    T value();

    @NotNull
    TacticalNBTTag<T> copy();

    @NotNull
    TacticalNBTTagType<T, ? extends TacticalNBTTag<T>> type();

    @NotNull
    TacticalByteNBTTag asByteTag();

    @NotNull
    TacticalShortNBTTag asShortTag();

    @NotNull
    TacticalIntNBTTag asIntTag();

    @NotNull
    TacticalLongNBTTag asLongTag();

    @NotNull
    TacticalFloatNBTTag asFloatTag();

    @NotNull
    TacticalDoubleNBTTag asDoubleTag();

    @NotNull
    TacticalStringNBTTag asStringTag();

    @NotNull
    TacticalListNBTTag<TacticalNBTTag<?>> asListTag();

    @NotNull
    TacticalByteListNBTTag asByteListTag();

    @NotNull
    TacticalIntListNBTTag asIntListTag();

    @NotNull
    TacticalLongListNBTTag asLongListTag();

    @NotNull
    TacticalNBTTagCompound asCompoundTag();

    @NotNull
    static TacticalNBTTagCompound compound() {
        return TacticalNBTTagCompound.of();
    }

    @NotNull
    static <E extends TacticalNBTTag<?>> TacticalListNBTTag<E> list() {
        return TacticalListNBTTag.of();
    }

    @NotNull
    static TacticalByteNBTTag of(byte b) {
        return TacticalByteNBTTag.of(b);
    }

    @NotNull
    static TacticalShortNBTTag of(short s) {
        return TacticalShortNBTTag.of(s);
    }

    @NotNull
    static TacticalIntNBTTag of(int i) {
        return TacticalIntNBTTag.of(i);
    }

    @NotNull
    static TacticalLongNBTTag of(long l) {
        return TacticalLongNBTTag.of(l);
    }

    @NotNull
    static TacticalFloatNBTTag of(float f) {
        return TacticalFloatNBTTag.of(f);
    }

    @NotNull
    static TacticalDoubleNBTTag of(double d) {
        return TacticalDoubleNBTTag.of(d);
    }

    @NotNull
    static TacticalByteNBTTag of(boolean b) {
        return TacticalByteNBTTag.of(b);
    }

    @NotNull
    static TacticalStringNBTTag of(@NotNull String s) {
        return TacticalStringNBTTag.of(s);
    }

    @NotNull
    static TacticalEndNBTTag endTag() {
        return TacticalEndNBTTag.endTag();
    }
}
