package net.crossager.tactical.commons;

import net.crossager.tactical.api.data.TacticalValueHolder;
import net.crossager.tactical.util.InternalUtils;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface AbstractTacticalValueHolder extends TacticalValueHolder {
    @Override
    default String asString() {
        return getAsType(String.class);
    }

    @Override
    default @Nullable Object get(@Nullable Object def) {
        return isEmpty() ? def : get();
    }

    @Override
    default <T> @NotNull T getAsType(@NotNull Class<T> type) {
        return InternalUtils.cast(get(), type);
    }

    @Override
    default <T> @Nullable T getAsType(@NotNull Class<T> type, @Nullable T def) {
        return isEmpty() ? def : (isOfType(type) ? getAsType(type) : def);
    }

    @Override
    default int asInt() {
        return asNumber().intValue();
    }

    @Override
    default long asLong() {
        return asNumber().longValue();
    }

    @Override
    default short asShort() {
        return asNumber().shortValue();
    }

    @Override
    default byte asByte() {
        return asNumber().byteValue();
    }

    @Override
    default float asFloat() {
        return asNumber().floatValue();
    }

    @Override
    default double asDouble() {
        return asNumber().doubleValue();
    }

    @Override
    default boolean asBoolean() {
        return getAsType(Boolean.class);
    }

    @Override
    default char asCharacter() {
        return getAsType(Character.class);
    }

    @Override
    default Number asNumber() {
        return getAsType(Number.class);
    }

    @Override
    default <T extends Enum<T>> T asEnum(Class<T> enumClass) {
        if (isOfType(enumClass)) return getAsType(enumClass);
        return Enum.valueOf(enumClass, asString());
    }

    @Override
    default Material asMaterial() {
        return asEnum(Material.class);
    }

    @Override
    default boolean isString() {
        return isOfType(String.class);
    }

    @Override
    default boolean isInt() {
        return isOfType(Integer.class);
    }

    @Override
    default boolean isLong() {
        return isOfType(Long.class);
    }

    @Override
    default boolean isShort() {
        return isOfType(Short.class);
    }

    @Override
    default boolean isByte() {
        return isOfType(Byte.class);
    }

    @Override
    default boolean isFloat() {
        return isOfType(Float.class);
    }

    @Override
    default boolean isDouble() {
        return isOfType(Double.class);
    }

    @Override
    default boolean isBoolean() {
        return isOfType(Boolean.class);
    }

    @Override
    default boolean isCharacter() {
        return isOfType(Character.class);
    }

    @Override
    default boolean isNumber() {
        return isOfType(Number.class);
    }

    @Override
    default <T extends Enum<T>> boolean isEnum(Class<T> enumClass) {
        if (isOfType(enumClass)) return true;
        if (!isString()) return false;
        try {
            Enum.valueOf(enumClass, asString());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    default boolean isMaterial() {
        return isEnum(Material.class);
    }

    @Override
    default boolean isEmpty() {
        return !isPresent();
    }

    @Override
    default void ifPresent(@NotNull Consumer<Object> ifPresent) {
        if (isPresent()) ifPresent.accept(get());
    }

    @Override
    default <T> void ifPresent(@NotNull Consumer<T> ifPresent, @NotNull Class<T> type) {
        if (isPresent()) ifPresent.accept(getAsType(type));
    }
}
