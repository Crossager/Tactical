package net.crossager.tactical.api.config.items;

import net.crossager.tactical.api.data.TacticalValueHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

@SuppressWarnings("unused")
public interface TacticalConfigValue extends TacticalValueHolder {
    @NotNull
    @Unmodifiable
    List<TacticalConfigParent> ancestors();
    @NotNull
    TacticalConfigParent parent();
    boolean hasParent();

    void remove();
    boolean exists();

    void set(@NotNull Object value);
    void clear();
    @NotNull
    TacticalConfigSection makeSection();
    @NotNull
    TacticalConfigList makeList();
    @NotNull
    TacticalConfigSection asSection();
    @NotNull
    TacticalConfigList asList();

    boolean isSection();
    boolean isList();
}
