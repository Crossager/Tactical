package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import org.jetbrains.annotations.NotNull;

public interface TacticalAnyListNBTTagType extends TacticalListNBTTagType<TacticalNBTTag<?>, TacticalListNBTTag<TacticalNBTTag<?>>> {
    @SuppressWarnings("unchecked")
    @NotNull
    static <E extends TacticalNBTTag<?>, T extends TacticalListNBTTag<E>> TacticalListNBTTagType<E, T> type() {
        return (TacticalListNBTTagType<E, T>) TacticalNBTManager.getInstance().anyListType();
    }
}
