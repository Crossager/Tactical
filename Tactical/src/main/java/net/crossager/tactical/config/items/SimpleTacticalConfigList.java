package net.crossager.tactical.config.items;

import net.crossager.tactical.api.config.items.*;
import net.crossager.tactical.api.util.FunctionUtils;
import net.crossager.tactical.api.util.TacticalUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SimpleTacticalConfigList extends BaseConfigItem implements TacticalConfigList {
    private transient final TacticalConfigParent selfParent;
    private final LinkedList<TacticalConfigValue> data = new LinkedList<>();

    public SimpleTacticalConfigList(TacticalConfigParent parent, Iterable<?> data) {
        super(parent);
        this.data.addAll(convertData(data));
        selfParent = SimpleTacticalConfigParent.of(parent, this);
    }

    private List<SimpleTacticalConfigValue> convertData(Iterable<?> data) {
        List<SimpleTacticalConfigValue> items = new ArrayList<>();
        data.forEach(FunctionUtils.applyAndThen(FunctionUtils.withFirstArgF(SimpleTacticalConfigValue::of, selfParent), items::add));
        return items;
    }

    public LinkedList<TacticalConfigValue> data() {
        return data;
    }

    @Override
    public @NotNull TacticalConfigValue get(int index) {
        checkExists();
        return data.get(index);
    }

    @Override
    public void clear() {
        checkExists();
        data.clear();
    }

    @Override
    public int size() {
        checkExists();
        return data.size();
    }

    @Override
    public @NotNull <T> List<T> toList(Class<T> type) {
        checkExists();
        return Collections.unmodifiableList(TacticalUtils.mapList(data, FunctionUtils.withSecondArgF(TacticalConfigValue::getAsType, type)));
    }

    @Override
    public @NotNull TacticalConfigSection addSection() {
        checkExists();
        TacticalConfigSection section = new SimpleTacticalConfigSection(selfParent);
        data.add(SimpleTacticalConfigValue.of(selfParent, section));
        return section;
    }

    @Override
    public @NotNull TacticalConfigSection setSection(int index) {
        checkExists();
        TacticalConfigSection section = new SimpleTacticalConfigSection(selfParent);
        data.set(index, SimpleTacticalConfigValue.of(selfParent, section));
        return section;
    }

    @Override
    public @NotNull TacticalConfigValue addValue(@NotNull Object value) {
        checkExists();
        TacticalConfigValue item = SimpleTacticalConfigValue.of(selfParent, value);
        data.add(item);
        return item;
    }

    @Override
    public @NotNull TacticalConfigValue setValue(int index, @NotNull Object value) {
        checkExists();
        TacticalConfigValue item = SimpleTacticalConfigValue.of(selfParent, value);
        data.set(index, item);
        return item;
    }

    @Override
    public @NotNull TacticalConfigList addList() {
        checkExists();
        TacticalConfigValue item = SimpleTacticalConfigValue.of(selfParent, new SimpleTacticalConfigList(selfParent, List.of()));
        data.add(item);
        return item.asList();
    }

    @Override
    public @NotNull TacticalConfigList setList(int index) {
        checkExists();
        TacticalConfigValue item = SimpleTacticalConfigValue.of(selfParent, new SimpleTacticalConfigList(selfParent, List.of()));
        data.set(index, item);
        return item.asList();
    }

    @Override
    public boolean remove(@NotNull TacticalConfigValue element) {
        checkExists();
        return data.remove(element);
    }

    @Override
    public boolean remove(@NotNull Object o) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isEmpty()) continue;
            if (o.equals(data.get(i).get())) return data.remove(i) != null;
        }
        return false;
    }

    @Override
    public @NotNull TacticalConfigValue remove(int index) {
        checkExists();
        return data.remove(index);
    }

    @NotNull
    @Override
    public Iterator<TacticalConfigValue> iterator() {
        checkExists();
        return data.iterator();
    }

    public TacticalConfigParent selfParent() {
        return selfParent;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
