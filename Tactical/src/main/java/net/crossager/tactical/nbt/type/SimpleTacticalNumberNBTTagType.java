package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalNumberNBTTag;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNumberNBTTagType;

import java.util.function.Function;

public abstract class SimpleTacticalNumberNBTTagType<V extends Number, T extends TacticalNumberNBTTag<V>> extends SimpleTacticalNBTTagType<V, T> implements TacticalNumberNBTTagType<V, T> {
    private final Function<String, V> parser;

    public SimpleTacticalNumberNBTTagType(Class<V> valueClass, Function<V, T> constructor, Function<String, V> parser) {
        super(valueClass, constructor);
        this.parser = parser;
    }

    protected V doParse(String input) {
        return parser.apply(input);
    }
}
