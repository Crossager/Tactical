package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalIntListNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface TacticalIntListNBTTag extends TacticalListNBTTag<TacticalIntNBTTag> {
    @NotNull
    TacticalIntListNBTTag copy();

    int[] toArray();

    @NotNull
    static TacticalIntListNBTTag of() {
        return ofMutable(new ArrayList<>());
    }

    @NotNull
    static TacticalIntListNBTTag of(@NotNull List<TacticalIntNBTTag> tags) {
        return ofMutable(new ArrayList<>(tags));
    }

    @NotNull
    static TacticalIntListNBTTag ofMutable(@NotNull List<TacticalIntNBTTag> tags) {
        return TacticalIntListNBTTagType.type().create(tags);
    }

    @NotNull
    static TacticalIntListNBTTag of(@NotNull TacticalIntNBTTag... tags) {
        return of(Arrays.asList(tags));
    }
}
