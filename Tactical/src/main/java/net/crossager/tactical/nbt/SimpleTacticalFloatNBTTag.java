package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.TacticalFloatNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalFloatNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class SimpleTacticalFloatNBTTag extends SimpleTacticalNumberNBTTag<Float, TacticalFloatNBTTag> implements TacticalFloatNBTTag {
    public SimpleTacticalFloatNBTTag(Float number) {
        super(number, TacticalFloatNBTTagType.type());
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(value);
    }

    @Override
    public @NotNull TacticalFloatNBTTag copy() {
        return new SimpleTacticalFloatNBTTag(value);
    }

    @Override
    protected String suffix() {
        return "f";
    }

    @Override
    public @NotNull TacticalFloatNBTTag asFloatTag() {
        return this;
    }
}
