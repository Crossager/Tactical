package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalIntNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import org.jetbrains.annotations.NotNull;

public interface TacticalIntNBTTagType extends TacticalNumberNBTTagType<Integer, TacticalIntNBTTag> {
    @NotNull
    static TacticalIntNBTTagType type() {
        return TacticalNBTManager.getInstance().intType();
    }
}
