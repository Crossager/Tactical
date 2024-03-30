package net.crossager.tactical.config.items;

import net.crossager.tactical.api.config.items.TacticalConfigList;
import net.crossager.tactical.api.config.items.TacticalConfigParent;
import net.crossager.tactical.api.config.items.TacticalConfigSection;
import net.crossager.tactical.util.Exceptions;
import org.jetbrains.annotations.NotNull;

public class SimpleTacticalConfigParent implements TacticalConfigParent {
    private final TacticalConfigList list;
    private final TacticalConfigSection section;
    private final TacticalConfigParent parent;

    private SimpleTacticalConfigParent(TacticalConfigParent parent, TacticalConfigList list, TacticalConfigSection section) {
        this.list = list;
        this.section = section;
        this.parent = parent;
    }

    @Override
    public @NotNull TacticalConfigSection asSection() {
        if (section == null) return Exceptions.noValue("section");
        return section;
    }

    @Override
    public @NotNull TacticalConfigList asList() {
        if (list == null) return Exceptions.noValue("list");
        return list;
    }

    @Override
    public boolean isSection() {
        return section != null;
    }

    @Override
    public boolean isList() {
        return list != null;
    }

    @Override
    public @NotNull TacticalConfigParent parent() {
        if (parent == null) return Exceptions.lazyNoValue();
        return parent;
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public String toString() {
        return list != null ? String.valueOf(list) : String.valueOf(section);
    }

    public static SimpleTacticalConfigParent of(TacticalConfigParent parent, TacticalConfigSection section) {
        return new SimpleTacticalConfigParent(parent, null, section);
    }

    public static SimpleTacticalConfigParent of(TacticalConfigParent parent, TacticalConfigList list) {
        return new SimpleTacticalConfigParent(parent, list, null);
    }
}
