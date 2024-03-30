package net.crossager.tactical.config;

import net.crossager.tactical.api.config.TacticalConfigFactory;
import net.crossager.tactical.api.config.TacticalConfigSerializer;
import net.crossager.tactical.api.config.TacticalConfig;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class SimpleTacticalConfigFactory implements TacticalConfigFactory {
    @Override
    public @NotNull TacticalConfig create(@NotNull TacticalConfigSerializer type, @NotNull File file) throws IOException {
        return new SimpleTacticalConfig(type, file);
    }

    @Override
    public @NotNull TacticalConfig create(@NotNull TacticalConfigSerializer type, @NotNull String path) throws IOException {
        return create(type, new File(path));
    }

    @Override
    public @NotNull TacticalConfig create(@NotNull TacticalConfigSerializer type, @NotNull File parent, @NotNull String path) throws IOException {
        return create(type, new File(parent, path));
    }
}
