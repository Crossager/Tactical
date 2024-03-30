package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.TacticalEndNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalEndNBTTagType;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class SimpleTacticalEndNBTTag extends SimpleTacticalNBTTag<Void> implements TacticalEndNBTTag {
    public static final SimpleTacticalEndNBTTag INSTANCE = new SimpleTacticalEndNBTTag();

    public SimpleTacticalEndNBTTag() {
        super(null);
    }

    @Override
    public @NotNull String serialize() {
        return "";
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {

    }

    @Override
    public @NotNull TacticalNBTTag<Void> copy() {
        return this;
    }

    @Override
    public @NotNull TacticalNBTTagType<Void, ? extends TacticalNBTTag<Void>> type() {
        return TacticalEndNBTTagType.type();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        return o instanceof TacticalEndNBTTag;
    }
}
