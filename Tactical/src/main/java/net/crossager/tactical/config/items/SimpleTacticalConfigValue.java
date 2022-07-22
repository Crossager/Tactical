package net.crossager.tactical.config.items;

import net.crossager.tactical.api.config.items.TacticalConfigList;
import net.crossager.tactical.api.config.items.TacticalConfigParent;
import net.crossager.tactical.api.config.items.TacticalConfigSection;
import net.crossager.tactical.api.config.items.TacticalConfigValue;
import net.crossager.tactical.checks.Checks;
import net.crossager.tactical.config.ConfigUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class SimpleTacticalConfigValue extends BaseConfigItem implements TacticalConfigValue {
    private Object value;

    private SimpleTacticalConfigValue(TacticalConfigParent parent, Object value) {
        super(parent);
        this.value = value;
    }

    @Override
    public TacticalConfigSection asSection() {
        return getAsType(TacticalConfigSection.class);
    }

    @Override
    public TacticalConfigList asList() {
        return getAsType(TacticalConfigList.class);
    }

    @Override
    public String asString() {
        return get().toString();
    }

    @Override
    public @NotNull Object get() {
        checkExists();
        if (value == null) throw new NoSuchElementException();
        return value;
    }

    @Override
    public @Nullable Object get(@Nullable Object def) {
        return isEmpty() ? def : get();
    }

    @Override
    public <T> @NotNull T getAsType(@NotNull Class<T> type) {
        Object value = get();
        if (!type.isInstance(value)) throw new NoSuchElementException("Value of type " + value.getClass() + ", is not applicable to type " + type);
        return type.cast(value);
    }

    @Override
    public <T> @Nullable T getAsType(@NotNull Class<T> type, @Nullable T def) {
        return isEmpty() ? def :
                (isOfType(type) ? getAsType(type) : def);
    }

    @Override
    public int asInt() {
        return getAsType(Integer.class);
    }

    @Override
    public long asLong() {
        return getAsType(Long.class);
    }

    @Override
    public short asShort() {
        return getAsType(Short.class);
    }

    @Override
    public byte asByte() {
        return getAsType(Byte.class);
    }

    @Override
    public float asFloat() {
        return getAsType(Float.class);
    }

    @Override
    public double asDouble() {
        return getAsType(Double.class);
    }

    @Override
    public boolean asBoolean() {
        return getAsType(Boolean.class);
    }

    @Override
    public char asCharacter() {
        return getAsType(Character.class);
    }

    @Override
    public Number asNumber() {
        return getAsType(Number.class);
    }

    @Override
    public boolean isSection() {
        return isOfType(TacticalConfigSection.class);
    }

    @Override
    public boolean isList() {
        return isOfType(TacticalConfigList.class);
    }

    @Override
    public boolean isString() {
        return isOfType(CharSequence.class);
    }

    @Override
    public boolean isOfType(@NotNull Class<?> type) {
        if (isEmpty()) return false;
        return Checks.notNull(type, "type cannot be null").isInstance(value);
    }

    @Override
    public boolean isInt() {
        return isOfType(Integer.class);
    }

    @Override
    public boolean isLong() {
        return isOfType(Long.class);
    }

    @Override
    public boolean isShort() {
        return isOfType(Short.class);
    }

    @Override
    public boolean isByte() {
        return isOfType(Byte.class);
    }

    @Override
    public boolean isFloat() {
        return isOfType(Float.class);
    }

    @Override
    public boolean isDouble() {
        return isOfType(Double.class);
    }

    @Override
    public boolean isBoolean() {
        return isOfType(Boolean.class);
    }

    @Override
    public boolean isCharacter() {
        return isOfType(Character.class);
    }

    @Override
    public boolean isNumber() {
        return isOfType(Number.class);
    }

    @Override
    public boolean isPresent() {
        return !isEmpty();
    }

    @Override
    public boolean isEmpty() {
        return value == null;
    }

    @Override
    public void ifPresent(@NotNull Consumer<Object> ifPresent) {
        if (isPresent()) ifPresent.accept(get());
    }

    @Override
    public <T> void ifPresent(@NotNull Consumer<T> ifPresent, @NotNull Class<T> type) {
        if (isPresent()) ifPresent.accept(getAsType(type));
    }

    @Override
    public void set(@NotNull Object value) {
        checkExists();
        this.value = ConfigUtils.convertValue(value, parent);
    }

    @Override
    public void clear() {
        this.value = null;
    }

    @Override
    public TacticalConfigSection createSection() {
        checkExists();
        if (isOfType(TacticalConfigSection.class)) return (TacticalConfigSection) value;
        this.value = new SimpleTacticalConfigSection(parent);
        return (TacticalConfigSection) value;
    }

    @Override
    public TacticalConfigList createList() {
        checkExists();
        if (isOfType(TacticalConfigList.class)) return (TacticalConfigList) value;
        this.value = new SimpleTacticalConfigList(parent, List.of());
        return (TacticalConfigList) value;
    }

    @Override
    public String toString() {
        return isEmpty() ? "" : value.toString();
    }

    public static SimpleTacticalConfigValue of(TacticalConfigParent parent, Object obj){
        return new SimpleTacticalConfigValue(parent, obj);
    }
    public static SimpleTacticalConfigValue newEmpty(TacticalConfigParent parent) {
        return new SimpleTacticalConfigValue(parent, null);
    }
}
