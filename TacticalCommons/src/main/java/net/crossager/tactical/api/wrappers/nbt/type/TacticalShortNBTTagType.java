package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import net.crossager.tactical.api.wrappers.nbt.TacticalShortNBTTag;
import org.jetbrains.annotations.NotNull;

public interface TacticalShortNBTTagType extends TacticalNumberNBTTagType<Short, TacticalShortNBTTag> {
    @NotNull
    static TacticalShortNBTTagType type() {
        return TacticalNBTManager.getInstance().shortType();
    }
}
