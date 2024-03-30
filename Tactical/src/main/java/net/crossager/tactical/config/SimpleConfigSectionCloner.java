package net.crossager.tactical.config;

import net.crossager.tactical.api.config.items.TacticalConfigList;
import net.crossager.tactical.api.config.items.TacticalConfigParent;
import net.crossager.tactical.api.config.items.TacticalConfigSection;
import net.crossager.tactical.api.config.items.TacticalConfigValue;
import net.crossager.tactical.api.util.FunctionUtils;
import net.crossager.tactical.config.items.SimpleTacticalConfigList;
import net.crossager.tactical.config.items.SimpleTacticalConfigSection;
import net.crossager.tactical.config.items.SimpleTacticalConfigValue;

import java.util.List;
import java.util.function.Function;

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
        section.children().forEach(FunctionUtils.apply2Before(newSection.childrenRaw()::put, Function.identity(), FunctionUtils.withSecondArgF(this::copyOfValue, newSection.selfParent())));
        return newSection;
    }
    private TacticalConfigList copyOfList(TacticalConfigList list, TacticalConfigParent newParent) {
        SimpleTacticalConfigList newList = new SimpleTacticalConfigList(newParent, List.of());
        list.forEach(FunctionUtils.applyAndThen(FunctionUtils.withSecondArgF(this::copyOfValue, newList.selfParent()), newList.data()::add));
        return newList;
    }
    private TacticalConfigValue copyOfValue(TacticalConfigValue value, TacticalConfigParent newParent) {
        if (value.isEmpty()) return SimpleTacticalConfigValue.newEmpty(newParent);
        if (value.isList()) return SimpleTacticalConfigValue.of(newParent, copyOfList(value.asList(), newParent));
        if (value.isSection()) return SimpleTacticalConfigValue.of(newParent, copyOfSection(value.asSection(), newParent));
        return SimpleTacticalConfigValue.of(newParent, value.get());
    }
}
