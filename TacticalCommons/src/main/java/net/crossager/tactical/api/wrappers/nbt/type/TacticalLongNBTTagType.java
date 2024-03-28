package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalLongNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import org.jetbrains.annotations.NotNull;

public interface TacticalLongNBTTagType extends TacticalNumberNBTTagType<Long, TacticalLongNBTTag> {
    @NotNull
    static TacticalLongNBTTagType type() {
        return TacticalNBTManager.getInstance().longType();
    }
}
