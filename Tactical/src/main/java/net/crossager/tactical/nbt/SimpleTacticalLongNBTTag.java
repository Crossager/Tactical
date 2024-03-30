package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.TacticalLongNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalLongNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class SimpleTacticalLongNBTTag extends SimpleTacticalNumberNBTTag<Long, TacticalLongNBTTag> implements TacticalLongNBTTag {
    public SimpleTacticalLongNBTTag(Long number) {
        super(number, TacticalLongNBTTagType.type());
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(value);
    }

    @Override
    public @NotNull TacticalLongNBTTag copy() {
        return new SimpleTacticalLongNBTTag(value);
    }

    @Override
    protected String suffix() {
        return "l";
    }

    @Override
    public @NotNull TacticalLongNBTTag asLongTag() {
        return this;
    }
}
