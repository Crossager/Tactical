package net.crossager.tactical.config.items;

import net.crossager.tactical.api.config.items.TacticalConfigList;
import net.crossager.tactical.api.config.items.TacticalConfigParent;
import net.crossager.tactical.api.config.items.TacticalConfigSection;
import net.crossager.tactical.api.config.items.TacticalConfigValue;
import net.crossager.tactical.commons.AbstractTacticalValueHolder;
import net.crossager.tactical.config.ConfigUtils;
import net.crossager.tactical.util.Checks;
import net.crossager.tactical.util.Exceptions;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpleTacticalConfigValue extends BaseConfigItem implements AbstractTacticalValueHolder, TacticalConfigValue {
    private Object value;
    private final SimpleTacticalConfigComments comments = new SimpleTacticalConfigComments();

    private SimpleTacticalConfigValue(TacticalConfigParent parent, Object value) {
        super(parent);
        this.value = value;
    }

    @Override
    public @NotNull TacticalConfigSection asSection() {
        return getAsType(TacticalConfigSection.class);
    }

    @Override
    public @NotNull TacticalConfigList asList() {
        return getAsType(TacticalConfigList.class);
    }

    @Override
    public @NotNull Object get() {
        checkExists();
        if (value == null) return Exceptions.noValue();
        return value;
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
    public boolean isOfType(@NotNull Class<?> type) {
        if (isEmpty()) return false;
        return Checks.notNull(type, "type cannot be null").isInstance(value);
    }

    @Override
    public boolean isPresent() {
        return value != null;
    }

    @Override
    public void set(@NotNull Object value) {
        checkExists();
        this.value = ConfigUtils.convertValueToConfig(value, parent);
    }

    @Override
    public void clear() {
        this.value = null;
    }

    @Override
    public @NotNull TacticalConfigSection makeSection() {
        checkExists();
        if (isOfType(TacticalConfigSection.class)) return (TacticalConfigSection) value;
        this.value = new SimpleTacticalConfigSection(parent);
        return (TacticalConfigSection) value;
    }

    @Override
    public @NotNull TacticalConfigList makeList() {
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
