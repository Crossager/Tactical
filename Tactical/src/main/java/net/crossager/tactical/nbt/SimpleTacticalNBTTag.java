package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.*;
import net.crossager.tactical.util.Exceptions;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class SimpleTacticalNBTTag<T> implements TacticalNBTTag<T> {
    protected final T value;

    public SimpleTacticalNBTTag(T value) {
        this.value = value;
    }

    @Override
    public @NotNull T value() {
        return value;
    }

    @Override
    public @NotNull TacticalByteNBTTag asByteTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalShortNBTTag asShortTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalIntNBTTag asIntTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalLongNBTTag asLongTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalFloatNBTTag asFloatTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalDoubleNBTTag asDoubleTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalStringNBTTag asStringTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalListNBTTag<TacticalNBTTag<?>> asListTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalByteListNBTTag asByteListTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalIntListNBTTag asIntListTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalLongListNBTTag asLongListTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public @NotNull TacticalNBTTagCompound asCompoundTag() {
        return Exceptions.nse("Wrong tag type for" + getClass().getName());
    }

    @Override
    public String toString() {
        return serialize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTacticalNBTTag<?> that = (SimpleTacticalNBTTag<?>) o;
        return Objects.equals(value, that.value);
    }
}
