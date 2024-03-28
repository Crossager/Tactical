package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalIntListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalIntNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import org.jetbrains.annotations.NotNull;

public interface TacticalIntListNBTTagType extends TacticalListNBTTagType<TacticalIntNBTTag, TacticalIntListNBTTag> {
    @NotNull
    static TacticalIntListNBTTagType type() {
        return TacticalNBTManager.getInstance().intListType();
    }
}
