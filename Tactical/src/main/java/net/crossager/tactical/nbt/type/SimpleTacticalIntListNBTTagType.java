package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.NBTParseException;
import net.crossager.tactical.api.wrappers.nbt.TacticalIntListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalIntNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalIntListNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalIntListNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleTacticalIntListNBTTagType extends SimpleTacticalListNBTTagType<TacticalIntNBTTag, TacticalIntListNBTTag> implements TacticalIntListNBTTagType {
    public static final byte ID = 11;

    public SimpleTacticalIntListNBTTagType() {
        super(SimpleTacticalIntListNBTTag::new);
    }

    @Override
    public char identifier() {
        return 'I';
    }

    @Override
    public @NotNull Class<TacticalIntNBTTag> elementType() {
        return TacticalIntNBTTag.class;
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalIntListNBTTag read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(24);
        int length = dataInput.readInt();
        if (length > 0x1000000)
            throw new NBTParseException("List too long. Expected length to be below " + 0x1000000 + ", actual length: " + length);
        maxBytes.read(4L * length);
        List<TacticalIntNBTTag> tags = new ArrayList<>(length);
        for(int i = 0; i < length; ++i) {
            tags.set(i, TacticalIntNBTTag.of(dataInput.readInt()));
        }

        return new SimpleTacticalIntListNBTTag(tags);
    }
}
