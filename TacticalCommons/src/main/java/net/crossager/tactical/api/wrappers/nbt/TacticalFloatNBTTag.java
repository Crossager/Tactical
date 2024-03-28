package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalFloatNBTTagType;
import org.jetbrains.annotations.NotNull;

public interface TacticalFloatNBTTag extends TacticalNumberNBTTag<Float> {
    @NotNull
    TacticalFloatNBTTag copy();

    @NotNull
    static TacticalFloatNBTTag of(float f) {
        return TacticalFloatNBTTagType.type().create(f);
    }
}
