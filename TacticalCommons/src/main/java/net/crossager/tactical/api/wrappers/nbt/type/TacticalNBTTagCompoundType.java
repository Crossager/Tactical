package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface TacticalNBTTagCompoundType extends TacticalNBTTagType<Map<String, TacticalNBTTag<?>>, TacticalNBTTagCompound> {
    @NotNull
    static TacticalNBTTagCompoundType type() {
        return TacticalNBTManager.getInstance().compoundType();
    }
}
