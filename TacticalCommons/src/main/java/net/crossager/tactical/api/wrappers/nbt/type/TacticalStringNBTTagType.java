package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import net.crossager.tactical.api.wrappers.nbt.TacticalStringNBTTag;
import org.jetbrains.annotations.NotNull;

public interface TacticalStringNBTTagType extends TacticalNBTTagType<String, TacticalStringNBTTag> {
    @NotNull
    static TacticalStringNBTTagType type() {
        return TacticalNBTManager.getInstance().stringType();
    }
}
