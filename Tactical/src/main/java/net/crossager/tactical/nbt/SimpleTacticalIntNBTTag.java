package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.TacticalIntNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalIntNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class SimpleTacticalIntNBTTag extends SimpleTacticalNumberNBTTag<Integer, TacticalIntNBTTag> implements TacticalIntNBTTag {
    public SimpleTacticalIntNBTTag(Integer number) {
        super(number, TacticalIntNBTTagType.type());
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(value);
    }

    @Override
    public @NotNull TacticalIntNBTTag copy() {
        return new SimpleTacticalIntNBTTag(value);
    }

    @Override
    protected String suffix() {
        return "";
    }

    @Override
    public @NotNull TacticalIntNBTTag asIntTag() {
        return this;
    }
}
