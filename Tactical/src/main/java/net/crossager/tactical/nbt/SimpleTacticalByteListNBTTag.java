package net.crossager.tactical.nbt;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import net.crossager.tactical.api.wrappers.nbt.TacticalByteListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalByteNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalByteListNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class SimpleTacticalByteListNBTTag extends SimpleTacticalListNBTTag<TacticalByteNBTTag> implements TacticalByteListNBTTag {
    public SimpleTacticalByteListNBTTag(List<TacticalByteNBTTag> value) {
        super(value, TacticalByteListNBTTagType.type());
    }

    @Override
    protected String startList() {
        return "B;";
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(value.size());
        dataOutput.write(toArray());
    }

    @Override
    public @NotNull TacticalByteListNBTTag copy() {
        return new SimpleTacticalByteListNBTTag(value);
    }

    @Override
    public byte[] toArray() {
        return new ByteArrayList(value.stream().map(TacticalByteNBTTag::value).toList()).toByteArray();
    }

    @Override
    public @NotNull TacticalByteListNBTTag asByteListTag() {
        return this;
    }
}
