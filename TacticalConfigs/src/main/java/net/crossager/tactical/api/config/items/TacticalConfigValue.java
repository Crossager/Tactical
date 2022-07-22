package net.crossager.tactical.api.config.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface TacticalConfigValue {
    @NotNull
    @Unmodifiable
    List<TacticalConfigParent> ancestors();
    @NotNull
    TacticalConfigParent parent();
    boolean hasParent();

    @NotNull
    Object get();
    @Nullable
    Object get(@Nullable Object def);
    @NotNull
    <T> T getAsType(@NotNull Class<T> type);
    @Nullable
    <T> T getAsType(@NotNull Class<T> type, @Nullable T def);

    boolean isPresent();
    boolean isEmpty();
    void ifPresent(@NotNull Consumer<Object> ifPresent);
    <T> void ifPresent(@NotNull Consumer<T> ifPresent, @NotNull Class<T> type);

    void remove();
    boolean exists();

    void set(@NotNull Object value);
    void clear();
    TacticalConfigSection createSection();
    TacticalConfigList createList();

    TacticalConfigSection asSection();
    TacticalConfigList asList();
    String asString();
    int asInt();
    long asLong();
    short asShort();
    byte asByte();
    float asFloat();
    double asDouble();
    boolean asBoolean();
    char asCharacter();
    Number asNumber();

    boolean isSection();
    boolean isList();
    boolean isString();
    boolean isOfType(@NotNull Class<?> type);

    boolean isInt();
    boolean isLong();
    boolean isShort();
    boolean isByte();
    boolean isFloat();
    boolean isDouble();
    boolean isBoolean();
    boolean isCharacter();
    boolean isNumber();
}
