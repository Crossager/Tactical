package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalFloatNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import org.jetbrains.annotations.NotNull;

public interface TacticalFloatNBTTagType extends TacticalNumberNBTTagType<Float, TacticalFloatNBTTag> {
    @NotNull
    static TacticalFloatNBTTagType type() {
        return TacticalNBTManager.getInstance().floatType();
    }
}
