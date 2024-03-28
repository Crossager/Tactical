package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalEndNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import org.jetbrains.annotations.NotNull;

public interface TacticalEndNBTTagType extends TacticalNBTTagType<Void, TacticalEndNBTTag> {
    @NotNull
    static TacticalEndNBTTagType type() {
        return TacticalNBTManager.getInstance().endTagType();
    }
}
