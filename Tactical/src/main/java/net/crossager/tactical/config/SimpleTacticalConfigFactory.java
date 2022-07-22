package net.crossager.tactical.config;

import net.crossager.tactical.api.config.TacticalConfigFactory;
import net.crossager.tactical.api.config.TacticalConfigSerializer;
import net.crossager.tactical.api.config.TacticalFileConfig;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class SimpleTacticalConfigFactory implements TacticalConfigFactory {
    @Override
    public @NotNull TacticalFileConfig create(@NotNull TacticalConfigSerializer type, @NotNull File file) throws IOException {
        return new SimpleTacticalConfigFile(type, file);
    }

    @Override
    public @NotNull TacticalFileConfig create(@NotNull TacticalConfigSerializer type, @NotNull String path) throws IOException {
        return create(type, new File(path));
    }

    @Override
    public @NotNull TacticalFileConfig create(@NotNull TacticalConfigSerializer type, @NotNull File parent, @NotNull String path) throws IOException {
        return create(type, new File(parent, path));
    }
}
