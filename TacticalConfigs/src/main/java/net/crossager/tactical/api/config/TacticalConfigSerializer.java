package net.crossager.tactical.api.config;

import net.crossager.tactical.api.TacticalConfigs;
import net.crossager.tactical.api.config.items.TacticalConfigSection;
import net.crossager.tactical.api.config.items.TacticalConfigValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.io.IOException;
import java.util.Map;

public interface TacticalConfigSerializer {
    @NotNull
    String format();

    @NotNull
    String serialize(@NotNull String name, @NotNull TacticalConfigOptions options, @NotNull @Unmodifiable Map<String, TacticalConfigValue> children) throws IOException;
    void deserialize(@NotNull String data, @NotNull TacticalConfigSection section);

    @NotNull
    static TacticalConfigSerializer json() {
        return TacticalConfigs.getInstance().getConfigSerializers().get(0);
    }
    @NotNull
    static TacticalConfigSerializer xml() {
        return TacticalConfigs.getInstance().getConfigSerializers().get(1);
    }
    @NotNull
    static TacticalConfigSerializer yaml() {
        return TacticalConfigs.getInstance().getConfigSerializers().get(2);
    }
}
