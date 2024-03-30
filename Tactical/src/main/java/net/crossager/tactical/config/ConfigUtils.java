package net.crossager.tactical.config;

import net.crossager.tactical.api.config.items.TacticalConfigParent;
import net.crossager.tactical.api.config.items.TacticalConfigSection;
import net.crossager.tactical.api.config.items.TacticalConfigValue;
import net.crossager.tactical.util.Checks;
import net.crossager.tactical.config.items.SimpleTacticalConfigList;
import net.crossager.tactical.config.items.SimpleTacticalConfigValue;

import java.util.List;

public class ConfigUtils {
    public static String checkPath(String path) {
        Checks.notEmpty(Checks.notNull(path, "path"), "path");
        Checks.notLonger(path, TacticalConfigSection.MAX_PATH_LENGTH, "path");
        return path;
    }
    public static Object convertValueToConfig(Object obj, TacticalConfigParent parent) {
        if (obj == null) return SimpleTacticalConfigValue.newEmpty(parent);

        if (obj instanceof Iterable<?> iterable)
            return new SimpleTacticalConfigList(parent, iterable);

        if (obj.getClass().isArray())
            return new SimpleTacticalConfigList(parent, List.of((Object[]) obj));

        if (obj instanceof TacticalConfigSection section)
            return new SimpleConfigSectionCloner(section).getNewSection(parent);

        if (obj instanceof TacticalConfigValue value) {
            if (value.isPresent())
                return convertValueToConfig(value.get(), parent);
            return null;
        }
        return obj;
    }
}
