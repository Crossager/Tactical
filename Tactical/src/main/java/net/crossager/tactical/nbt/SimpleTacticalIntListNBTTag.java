package net.crossager.tactical.nbt;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.crossager.tactical.api.wrappers.nbt.TacticalIntListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalIntNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalIntListNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class SimpleTacticalIntListNBTTag extends SimpleTacticalListNBTTag<TacticalIntNBTTag> implements TacticalIntListNBTTag {
    public SimpleTacticalIntListNBTTag(List<TacticalIntNBTTag> value) {
        super(value, TacticalIntListNBTTagType.type());
    }

    @Override
    protected String startList() {
        return "I;";
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(value.size());
        for (TacticalIntNBTTag tag : value) {
            dataOutput.writeInt(tag.value());
        }
    }

    @Override
    public @NotNull TacticalIntListNBTTag copy() {
        return new SimpleTacticalIntListNBTTag(value);
    }

    @Override
    public int[] toArray() {
        return new IntArrayList(value.stream().map(TacticalIntNBTTag::value).toList()).toIntArray();
    }

    @Override
    public @NotNull TacticalIntListNBTTag asIntListTag() {
        return this;
    }
}
