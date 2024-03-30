package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.util.TacticalUtils;
import net.crossager.tactical.api.wrappers.nbt.*;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalAnyListNBTTagType;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagType;
import net.crossager.tactical.nbt.SimpleTacticalListNBTTag;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleTacticalAnyListNBTTagType extends SimpleTacticalListNBTTagType<TacticalNBTTag<?>, TacticalListNBTTag<TacticalNBTTag<?>>> implements TacticalAnyListNBTTagType {
    public static final byte ID = 9;

    public SimpleTacticalAnyListNBTTagType() {
        super(SimpleTacticalListNBTTag::new);
    }

    @Override
    public char identifier() {
        return ' ';
    }

    @Override
    public @NotNull Class<TacticalNBTTag<?>> elementType() {
        return TacticalUtils.castClassGenerics(TacticalNBTTag.class);
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalListNBTTag<TacticalNBTTag<?>> read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(37);
        byte listTypeId = dataInput.readByte();
        int length = dataInput.readInt();
        if (listTypeId == 0 && length > 0)
            throw new NBTParseException("Missing type on ListTag");

        maxBytes.read(4L * length);
        TacticalNBTTagType<?, ?> type = TacticalNBTManager.getInstance().getType(listTypeId);
        List<TacticalNBTTag<?>> list = new ArrayList<>(length);

        for(int i = 0; i < length; ++i) {
            list.set(i, type.read(dataInput, maxBytes));
        }

        return new SimpleTacticalListNBTTag<>(list);
    }
}
