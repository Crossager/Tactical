package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalLongListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalLongNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import org.jetbrains.annotations.NotNull;

public interface TacticalLongListNBTTagType extends TacticalListNBTTagType<TacticalLongNBTTag, TacticalLongListNBTTag> {
    @NotNull
    static TacticalLongListNBTTagType type() {
        return TacticalNBTManager.getInstance().longListType();
    }
}
