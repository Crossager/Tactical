package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.TacticalShortNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalShortNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class SimpleTacticalShortNBTTag extends SimpleTacticalNumberNBTTag<Short, TacticalShortNBTTag> implements TacticalShortNBTTag {
    public SimpleTacticalShortNBTTag(Short number) {
        super(number, TacticalShortNBTTagType.type());
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeShort(value);
    }

    @Override
    public @NotNull TacticalShortNBTTag copy() {
        return new SimpleTacticalShortNBTTag(value);
    }

    @Override
    protected String suffix() {
        return "s";
    }

    @Override
    public @NotNull TacticalShortNBTTag asShortTag() {
        return this;
    }
}
