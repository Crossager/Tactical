package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.TacticalListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalAnyListNBTTagType;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalListNBTTagType;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagType;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleTacticalListNBTTag<E extends TacticalNBTTag<?>> extends SimpleTacticalNBTTag<List<E>> implements TacticalListNBTTag<E> {
    private static final char ARRAY_START = '[';
    private static final char ARRAY_END = ']';

    private final TacticalListNBTTagType<E, ? extends TacticalListNBTTag<E>> type;

    public SimpleTacticalListNBTTag(List<E> value, TacticalListNBTTagType<E, ? extends TacticalListNBTTag<E>> type) {
        super(value);
        this.type = type;
    }

    public SimpleTacticalListNBTTag(List<E> value) {
        this(value, TacticalAnyListNBTTagType.type());
    }

    @Override
    public @NotNull String serialize() {
        return ARRAY_START +
                startList() +
                value.stream().map(TacticalNBTTag::serialize).collect(Collectors.joining(", ")) +
                ARRAY_END;
    }

    @Override
    public void serialize(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(
                value.isEmpty() ?
                        0 :
                        value.get(0).type().id()
        );
        dataOutput.writeInt(value.size());

        for (TacticalNBTTag<?> tag : value) {
            tag.serialize(dataOutput);
        }
    }

    protected String startList() {
        return "";
    }

    @Override
    public @NotNull TacticalNBTTagType<List<E>, ? extends TacticalNBTTag<List<E>>> type() {
        return type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull TacticalListNBTTag<TacticalNBTTag<?>> asListTag() {
        return (TacticalListNBTTag<TacticalNBTTag<?>>) this;
    }

    @Override
    public @NotNull TacticalListNBTTag<E> copy() {
        return new SimpleTacticalListNBTTag<>(new ArrayList<>(value), type);
    }

    @Override
    public @NotNull TacticalListNBTTag<E> add(@NotNull E element) {
        if (value.size() > 0 && element.getClass() != value.get(0).getClass())
            throw new UnsupportedOperationException("Element of type " + element.getClass().getName() + " cannot be added to list of type " + type.elementType());
        value.add(element);
        return this;
    }

    @Override
    public @NotNull TacticalListNBTTag<E> set(int index, @NotNull E element) {
        if (value.size() > 0 && !(value.size() == 1 && index == 0) && element.getClass() != value.get(0).getClass())
            throw new UnsupportedOperationException("Cannot add " + element.getClass().getSimpleName() + " to list of type " + value.get(0).getClass().getSimpleName());
        value.set(index, element);
        return this;
    }

    @Override
    public @NotNull TacticalListNBTTag<E> remove(int index) {
        value.remove(index);
        return this;
    }

    @Override
    public @NotNull TacticalListNBTTag<E> remove(@NotNull E element) {
        value.remove(element);
        return this;
    }

    @Override
    public @NotNull TacticalListNBTTag<E> clear() {
        value.clear();
        return this;
    }

    @Override
    public @NotNull E get(int index) {
        return value.get(index);
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return value.iterator();
    }
}
