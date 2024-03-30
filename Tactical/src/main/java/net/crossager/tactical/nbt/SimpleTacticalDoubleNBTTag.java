package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.TacticalDoubleNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalDoubleNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class SimpleTacticalDoubleNBTTag extends SimpleTacticalNumberNBTTag<Double, TacticalDoubleNBTTag> implements TacticalDoubleNBTTag {
    public SimpleTacticalDoubleNBTTag(Double number) {
        super(number, TacticalDoubleNBTTagType.type());
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(value);
    }

    @Override
    public @NotNull TacticalDoubleNBTTag copy() {
        return new SimpleTacticalDoubleNBTTag(value);
    }

    @Override
    protected String suffix() {
        return "d";
    }

    @Override
    public @NotNull TacticalDoubleNBTTag asDoubleTag() {
        return this;
    }
}