package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalClientEntity;
import net.crossager.tactical.api.npc.TacticalHologram;
import net.crossager.tactical.api.npc.TacticalNPCFactory;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SimpleTacticalNPCFactory implements TacticalNPCFactory {
    private static final long DEFAULT_UPDATE_INTERVAL = 2;
    private final JavaPlugin plugin;

    public SimpleTacticalNPCFactory(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull TacticalHologram createHologram(@NotNull Location location, @NotNull String text) {
        return createHologram(location, text, DEFAULT_UPDATE_INTERVAL);
    }

    @Override
    public @NotNull TacticalHologram createHologram(@NotNull Location location, @NotNull String text, long updateInterval) {
        return new SimpleTacticalHologram(plugin, location, text, updateInterval);
    }

    @Override
    public @NotNull <E extends Entity> TacticalClientEntity<E> createClientEntity(@NotNull Location location, @NotNull Class<E> entityClass) {
        return createClientEntity(location, entityClass, entity -> {});
    }

    @Override
    public @NotNull <E extends Entity> TacticalClientEntity<E> createClientEntity(@NotNull Location location, @NotNull Class<E> entityClass, @NotNull Consumer<E> applyInitialData) {
        return createClientEntity(location, entityClass, applyInitialData, DEFAULT_UPDATE_INTERVAL);
    }

    @Override
    public @NotNull <E extends Entity> TacticalClientEntity<E> createClientEntity(@NotNull Location location, @NotNull Class<E> entityClass, @NotNull Consumer<E> applyInitialData, long updateInterval) {
        return new SimpleTacticalClientEntity<>(plugin, location, entityClass, applyInitialData, updateInterval);
    }
}
