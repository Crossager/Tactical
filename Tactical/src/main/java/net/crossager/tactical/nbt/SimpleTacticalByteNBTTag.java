package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.TacticalByteNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalByteNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class SimpleTacticalByteNBTTag extends SimpleTacticalNumberNBTTag<Byte, TacticalByteNBTTag> implements TacticalByteNBTTag {
    public SimpleTacticalByteNBTTag(Byte number) {
        super(number, TacticalByteNBTTagType.type());
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(value);
    }

    @Override
    public @NotNull TacticalByteNBTTag copy() {
        return new SimpleTacticalByteNBTTag(value);
    }

    @Override
    public boolean asBoolean() {
        if (value == 0) return false;
        if (value == 1) return true;
        throw new IllegalStateException("Expected either 0 or 1, got " + value);
    }

    @Override
    protected String suffix() {
        return "b";
    }

    @Override
    public @NotNull TacticalByteNBTTag asByteTag() {
        return this;
    }
}
