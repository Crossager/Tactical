package net.crossager.tactical.config.items;

import com.google.common.collect.Maps;
import net.crossager.tactical.api.config.TacticalConfig;
import net.crossager.tactical.api.config.items.*;
import net.crossager.tactical.config.ConfigUtils;
import net.crossager.tactical.config.SimpleConfigSectionCloner;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SimpleTacticalConfigSection extends BaseConfigItem implements TacticalConfigSection {
    private final Map<String, TacticalConfigValue> children = new LinkedHashMap<>();
    private transient final TacticalConfigParent selfParent;

    public SimpleTacticalConfigSection(TacticalConfigParent parent) {
        super(parent);
        selfParent = SimpleTacticalConfigParent.of(parent, this);
    }

    public TacticalConfigParent selfParent() {
        return selfParent;
    }

    public Map<String, TacticalConfigValue> childrenRaw() {
        return children;
    }

    @Override
    public void clearChildren() {
        checkExists();
        children.clear();
    }

    @Override
    public @NotNull TacticalConfigValue set(@NotNull String path, @NotNull Object value) {
        checkExists();
        TacticalConfigValue obj = get(path);
        obj.set(value);
        return obj;
    }

    @Override
    public @NotNull TacticalConfigValue get(@NotNull String path) {
        checkExists();
        ConfigUtils.checkPath(path);
        String[] str = path.split("\\.", 2);

        if (str.length == 1) {
            children.putIfAbsent(str[0], SimpleTacticalConfigValue.newEmpty(selfParent));
            return children.get(str[0]);
        }
        if (children.containsKey(str[0]))
            return children.get(str[0]).makeSection().get(str[1]);
        TacticalConfigSection section = new SimpleTacticalConfigSection(selfParent);
        children.put(str[0], SimpleTacticalConfigValue.of(selfParent, section));
        return section.get(str[1]);
    }

    @Override
    public @NotNull Object getAny(@NotNull String path) {
        return get(path).get();
    }

    @Override
    public boolean remove(@NotNull String path) {
        checkExists();
        ConfigUtils.checkPath(path);
        String[] str = path.split("\\.", 2);
        if (str.length == 1) {
            TacticalConfigValue value = children.remove(str[0]);
            if (value != null) value.remove();
            return value != null && value.isPresent();
        }
        TacticalConfigValue value = children.get(str[0]);
        if (!value.isSection()) return false;
        return value.asSection().remove(str[1]);
    }

    @Override
    public boolean isSet(@NotNull String path) {
        checkExists();
        ConfigUtils.checkPath(path);
        String[] str = path.split("\\.", 2);
        TacticalConfigValue value = children.get(str[0]);
        if (value == null) return false;
        if (value.isEmpty()) {
            children.remove(str[0]);
            value.remove();
            return false;
        }
        if (str.length == 1) return true;
        if (!value.isSection()) return false;
        return value.asSection().isSet(str[1]);
    }

//    @Override
//    public @NotNull TacticalConfigComments comments(@NotNull String path) {
//        return get(path).comments();
//    }

    @Override
    public @NotNull TacticalConfigSection addSection(@NotNull String path) {
        checkExists();
        return get(ConfigUtils.checkPath(path)).makeSection();
    }

    @Override
    public @NotNull TacticalConfigList addList(@NotNull String path) {
        checkExists();
        return get(ConfigUtils.checkPath(path)).makeList();
    }

    @Override
    public @NotNull Map<String, TacticalConfigValue> children() {
        checkExists();
        HashMap<String, TacticalConfigValue> hashMap = Maps.newHashMap(children);
        hashMap.forEach(((s, value) -> {
            if (value.isEmpty()) hashMap.remove(s);
        }));
        return Collections.unmodifiableMap(hashMap);
    }

    @SuppressWarnings("all")
    @Override
    public @NotNull TacticalConfigSection clone() {
        checkExists();
        return new SimpleConfigSectionCloner(this).getNewSection(parent);
    }

    @Override
    public String toString() {
        return children.toString();
    }
}
