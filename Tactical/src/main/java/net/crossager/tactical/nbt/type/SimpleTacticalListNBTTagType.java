package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.util.TacticalUtils;
import net.crossager.tactical.api.wrappers.nbt.TacticalListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalListNBTTagType;

import java.util.List;
import java.util.function.Function;

public abstract class SimpleTacticalListNBTTagType<E extends TacticalNBTTag<?>, T extends TacticalListNBTTag<E>> extends SimpleTacticalNBTTagType<List<E>, T> implements TacticalListNBTTagType<E, T> {
    public SimpleTacticalListNBTTagType(Function<List<E>, T> constructor) {
        super(TacticalUtils.castClassGenerics(List.class), constructor);
    }
}
