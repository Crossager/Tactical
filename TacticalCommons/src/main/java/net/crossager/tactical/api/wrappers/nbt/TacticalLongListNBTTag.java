package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalLongListNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface TacticalLongListNBTTag extends TacticalListNBTTag<TacticalLongNBTTag> {
    @NotNull
    TacticalLongListNBTTag copy();

    long[] toArray();

    @NotNull
    static TacticalLongListNBTTag of() {
        return ofMutable(new ArrayList<>());
    }

    @NotNull
    static TacticalLongListNBTTag of(@NotNull List<TacticalLongNBTTag> tags) {
        return ofMutable(new ArrayList<>(tags));
    }

    @NotNull
    static TacticalLongListNBTTag ofMutable(@NotNull List<TacticalLongNBTTag> tags) {
        return TacticalLongListNBTTagType.type().create(tags);
    }

    @NotNull
    static TacticalLongListNBTTag of(@NotNull TacticalLongNBTTag... tags) {
        return of(Arrays.asList(tags));
    }
}
