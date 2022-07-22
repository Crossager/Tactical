package net.crossager.tactical;

import net.crossager.tactical.api.TacticalAPI;
import net.crossager.tactical.api.TacticalRegistrar;
import net.crossager.tactical.api.config.TacticalConfigSerializer;
import net.crossager.tactical.config.SimpleTacticalConfigFactory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class Tactical extends JavaPlugin implements TacticalAPI {
    @Override
    public void onEnable() {
        TacticalRegistrar.setInstance(this);
    }

    @Override
    public void onDisable() {

    }

    // API methods

    public @NotNull SimpleTacticalConfigFactory getConfigFactory() {
        return new SimpleTacticalConfigFactory();
    }

    @Override
    public @NotNull List<TacticalConfigSerializer> getConfigSerializers() {
        return List.of();
    }
}
