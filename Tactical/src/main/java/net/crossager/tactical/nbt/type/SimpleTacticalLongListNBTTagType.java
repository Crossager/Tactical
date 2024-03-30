package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.NBTParseException;
import net.crossager.tactical.api.wrappers.nbt.TacticalLongListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalLongNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalLongListNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalLongListNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleTacticalLongListNBTTagType extends SimpleTacticalListNBTTagType<TacticalLongNBTTag, TacticalLongListNBTTag> implements TacticalLongListNBTTagType {
    public static final byte ID = 12;
    public SimpleTacticalLongListNBTTagType() {
        super(SimpleTacticalLongListNBTTag::new);
    }

    @Override
    public char identifier() {
        return 'L';
    }

    @Override
    public @NotNull Class<TacticalLongNBTTag> elementType() {
        return TacticalLongNBTTag.class;
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalLongListNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(24);
        int length = dataInput.readInt();
        if (length > 0x1000000)
            throw new NBTParseException("List too long. Expected length to be below " + 0x1000000 + ", actual length: " + length);
        maxBytes.read(8L * length);
        List<TacticalLongNBTTag> tags = new ArrayList<>(length);
        for(int i = 0; i < length; ++i) {
            tags.set(i, TacticalLongNBTTag.of(dataInput.readLong()));
        }

        return new SimpleTacticalLongListNBTTag(tags);
    }
}
