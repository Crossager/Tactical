package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.TacticalStringNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalStringNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalStringNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;

public class SimpleTacticalStringNBTTagType extends SimpleTacticalNBTTagType<String, TacticalStringNBTTag> implements TacticalStringNBTTagType {
    public static final byte ID = 8;

    public SimpleTacticalStringNBTTagType() {
        super(String.class, SimpleTacticalStringNBTTag::new);
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalStringNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(36);
        String string = dataInput.readUTF();
        maxBytes.read(2L * string.length());
        return TacticalStringNBTTag.of(string);
    }
}
