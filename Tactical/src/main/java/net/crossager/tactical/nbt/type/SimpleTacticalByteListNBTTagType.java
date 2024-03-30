package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.NBTParseException;
import net.crossager.tactical.api.wrappers.nbt.TacticalByteListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalByteNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalByteListNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalByteListNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleTacticalByteListNBTTagType extends SimpleTacticalListNBTTagType<TacticalByteNBTTag, TacticalByteListNBTTag> implements TacticalByteListNBTTagType {
    public static final byte ID = 7;
    public SimpleTacticalByteListNBTTagType() {
        super(SimpleTacticalByteListNBTTag::new);
    }

    @Override
    public char identifier() {
        return 'B';
    }

    @Override
    public @NotNull Class<TacticalByteNBTTag> elementType() {
        return TacticalByteNBTTag.class;
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalByteListNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(24);
        int length = dataInput.readInt();
        if (length > 0x1000000)
            throw new NBTParseException("List too long. Expected length to be below " + 0x1000000 + ", actual length: " + length);
        maxBytes.read(length);
        List<TacticalByteNBTTag> tags = new ArrayList<>(length);
        for(int i = 0; i < length; ++i) {
            tags.set(i, TacticalByteNBTTag.of(dataInput.readByte()));
        }

        return new SimpleTacticalByteListNBTTag(tags);
    }
}
