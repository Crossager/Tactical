package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalAnyListNBTTagType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface TacticalListNBTTag<E extends TacticalNBTTag<?>> extends TacticalNBTTag<List<E>>, Iterable<E> {
    @NotNull
    TacticalListNBTTag<E> copy();

    @Contract("_ -> this")
    @NotNull
    TacticalListNBTTag<E> add(@NotNull E element);

    @Contract("_, _ -> this")
    @NotNull
    TacticalListNBTTag<E> set(int index, @NotNull E element);

    @Contract("_ -> this")
    @NotNull
    TacticalListNBTTag<E> remove(int index);

    @Contract("_ -> this")
    @NotNull
    TacticalListNBTTag<E> remove(@NotNull E element);

    @Contract(" -> this")
    @NotNull
    TacticalListNBTTag<E> clear();

    @NotNull
    E get(int index);

    int size();

    boolean isEmpty();

    @NotNull
    static <E extends TacticalNBTTag<?>> TacticalListNBTTag<E> of() {
        return ofMutable(new ArrayList<>());
    }

    @NotNull
    static <E extends TacticalNBTTag<?>> TacticalListNBTTag<E> of(@NotNull List<E> tags) {
        return ofMutable(new ArrayList<>(tags));
    }

    @NotNull
    static <E extends TacticalNBTTag<?>> TacticalListNBTTag<E> ofMutable(@NotNull List<E> tags) {
        return TacticalAnyListNBTTagType.<E, TacticalListNBTTag<E>>type().create(tags);
    }

    @SafeVarargs
    @NotNull
    static <E extends TacticalNBTTag<?>> TacticalListNBTTag<E> of(@NotNull E... tags) {
        return of(Arrays.asList(tags));
    }
}
