package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalDoubleNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import org.jetbrains.annotations.NotNull;

public interface TacticalDoubleNBTTagType extends TacticalNumberNBTTagType<Double, TacticalDoubleNBTTag> {
    @NotNull
    static TacticalDoubleNBTTagType type() {
        return TacticalNBTManager.getInstance().doubleType();
    }
}
