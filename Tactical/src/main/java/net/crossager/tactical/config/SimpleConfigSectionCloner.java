package net.crossager.tactical.config;

import net.crossager.tactical.api.config.items.TacticalConfigList;
import net.crossager.tactical.api.config.items.TacticalConfigParent;
import net.crossager.tactical.api.config.items.TacticalConfigSection;
import net.crossager.tactical.api.config.items.TacticalConfigValue;
import net.crossager.tactical.config.items.SimpleTacticalConfigList;
import net.crossager.tactical.config.items.SimpleTacticalConfigSection;
import net.crossager.tactical.config.items.SimpleTacticalConfigValue;

import java.util.List;

public final class SimpleConfigSectionCloner {
    private final TacticalConfigSection section;

    public SimpleConfigSectionCloner(TacticalConfigSection section) {
        this.section = section;
    }
    public TacticalConfigSection getNewSection(TacticalConfigParent newParent) {
        return copyOfSection(section, newParent);
    }
    private TacticalConfigSection copyOfSection(TacticalConfigSection section, TacticalConfigParent newParent) {
        SimpleTacticalConfigSection newSection = new SimpleTacticalConfigSection(newParent);
        section.children().forEach((path, value) -> newSection.childrenRaw().put(path, copyOfValue(value, newSection.selfParent())));
        return newSection;
    }
    private TacticalConfigList copyOfList(TacticalConfigList list, TacticalConfigParent newParent) {
        SimpleTacticalConfigList newList = new SimpleTacticalConfigList(newParent, List.of());
        list.forEach(value -> newList.data().add(copyOfValue(value, newList.selfParent())));
        return newList;
    }
    private TacticalConfigValue copyOfValue(TacticalConfigValue value, TacticalConfigParent newParent) {
        if (value.isEmpty()) return SimpleTacticalConfigValue.newEmpty(newParent);
        if (value.isList()) return SimpleTacticalConfigValue.of(newParent, copyOfList(value.asList(), newParent));
        if (value.isSection()) return SimpleTacticalConfigValue.of(newParent, copyOfSection(value.asSection(), newParent));
        return SimpleTacticalConfigValue.of(newParent, value.get());
    }
}
