package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalEndNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalEndNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalEndNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;

public class SimpleTacticalEndNBTTagType extends SimpleTacticalNBTTagType<Void, TacticalEndNBTTag> implements TacticalEndNBTTagType {
    public static final byte ID = 0;

    public SimpleTacticalEndNBTTagType() {
        super(Void.class, v -> TacticalEndNBTTag.endTag());
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalEndNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(8);
        return SimpleTacticalEndNBTTag.INSTANCE;
    }
}
