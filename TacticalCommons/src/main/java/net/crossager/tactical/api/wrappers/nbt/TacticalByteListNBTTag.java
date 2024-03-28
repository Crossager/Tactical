package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalByteListNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface TacticalByteListNBTTag extends TacticalListNBTTag<TacticalByteNBTTag> {
    @NotNull
    TacticalByteListNBTTag copy();

    byte[] toArray();

    @NotNull
    static TacticalByteListNBTTag of() {
        return ofMutable(new ArrayList<>());
    }

    @NotNull
    static TacticalByteListNBTTag of(@NotNull List<TacticalByteNBTTag> tags) {
        return ofMutable(new ArrayList<>(tags));
    }

    @NotNull
    static TacticalByteListNBTTag ofMutable(@NotNull List<TacticalByteNBTTag> tags) {TacticalByteListNBTTagType.type().create(tags);
        return TacticalByteListNBTTagType.type().create(tags);
    }

    @NotNull
    static TacticalByteListNBTTag of(@NotNull TacticalByteNBTTag... tags) {
        return of(Arrays.asList(tags));
    }
}
