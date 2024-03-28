package net.crossager.tactical.api.wrappers.nbt;

import org.jetbrains.annotations.NotNull;

public interface TacticalEndNBTTag extends TacticalNBTTag<Void> {
    @NotNull
    static TacticalEndNBTTag endTag() {
        return TacticalNBTManager.getInstance().endTag();
    }
}
