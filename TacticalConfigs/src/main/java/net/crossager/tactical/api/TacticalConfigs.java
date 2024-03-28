package net.crossager.tactical.api;

import net.crossager.tactical.api.config.TacticalConfigFactory;
import net.crossager.tactical.api.config.TacticalConfigSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TacticalConfigs {
    /**
     * Gets the singleton instance of {@link TacticalConfigFactory}
     * @return the {@link TacticalConfigFactory} instance
     */
    @NotNull
    TacticalConfigFactory getConfigFactory();

    /**
     * Returns a list of the default {@link TacticalConfigSerializer TacticalConfigSerializers} inbuilt into the TacticalAPI.
     * The mapping goes as follows:
     * json = index 0
     * xml = index 1
     * yaml = index 2
     * @return the inbuilt {@link TacticalConfigSerializer TacticalConfigSerializers}
     */
    @NotNull
    List<TacticalConfigSerializer> getConfigSerializers();

    /**
     * Returns the singleton instance of {@link TacticalConfigs}
     * @return the {@link TacticalConfigs} instance
     */
    @NotNull
    static TacticalConfigs getInstance() {
        return ConfigAPIHolder.getInstance();
    }
}
