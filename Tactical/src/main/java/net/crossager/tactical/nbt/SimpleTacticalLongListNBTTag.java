package net.crossager.tactical.nbt;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import net.crossager.tactical.api.wrappers.nbt.TacticalLongListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalLongNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalLongListNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class SimpleTacticalLongListNBTTag extends SimpleTacticalListNBTTag<TacticalLongNBTTag> implements TacticalLongListNBTTag {
    public SimpleTacticalLongListNBTTag(List<TacticalLongNBTTag> value) {
        super(value, TacticalLongListNBTTagType.type());
    }

    @Override
    protected String startList() {
        return "L;";
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(value.size());
        for (TacticalLongNBTTag tag : value) {
            dataOutput.writeLong(tag.value());
        }
    }

    @Override
    public @NotNull TacticalLongListNBTTag copy() {
        return new SimpleTacticalLongListNBTTag(value);
    }

    @Override
    public long[] toArray() {
        return new LongArrayList(value.stream().map(TacticalLongNBTTag::value).toList()).toLongArray();
    }

    @Override
    public @NotNull TacticalLongListNBTTag asLongListTag() {
        return this;
    }
}
