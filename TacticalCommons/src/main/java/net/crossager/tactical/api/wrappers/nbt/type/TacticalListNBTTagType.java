package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TacticalListNBTTagType<E extends TacticalNBTTag<?>, T extends TacticalListNBTTag<E>> extends TacticalNBTTagType<List<E>, T> {
    char identifier();

    @NotNull
    Class<E> elementType();
}
