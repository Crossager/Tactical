package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.*;
import net.crossager.tactical.npc.controllers.LookAtClosest;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

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

    @Override
    public @NotNull TacticalPlayerNPC createPlayerNPC(@NotNull Location location, @NotNull String profileName) {
        return createPlayerNPC(location, profileName, TacticalPlayerSkin.NO_SKIN);
    }

    @Override
    public @NotNull TacticalPlayerNPC createPlayerNPC(@NotNull Location location, @NotNull String profileName, @NotNull TacticalPlayerSkin skin) {
        return createPlayerNPC(location, profileName, skin, metaData -> {});
    }

    @Override
    public @NotNull TacticalPlayerNPC createPlayerNPC(@NotNull Location location, @NotNull String profileName, @NotNull TacticalPlayerSkin skin, @NotNull Consumer<TacticalPlayerNPCMetaData> applyInitialMetaData) {
        return createPlayerNPC(location, profileName, skin, applyInitialMetaData, DEFAULT_UPDATE_INTERVAL);
    }

    @Override
    public @NotNull TacticalPlayerNPC createPlayerNPC(@NotNull Location location, @NotNull String profileName, @NotNull TacticalPlayerSkin skin, @NotNull Consumer<TacticalPlayerNPCMetaData> applyInitialMetaData, long updateInterval) {
        return createPlayerNPC(location, profileName, skin, applyInitialMetaData, updateInterval, UUID.randomUUID());
    }

    @Override
    public @NotNull TacticalPlayerNPC createPlayerNPC(@NotNull Location location, @NotNull String profileName, @NotNull TacticalPlayerSkin skin, @NotNull Consumer<TacticalPlayerNPCMetaData> applyInitialMetaData, long updateInterval, @NotNull UUID uuid) {
        return new SimpleTacticalPlayerNPC(plugin, profileName, location, applyInitialMetaData, skin, updateInterval, uuid);
    }

    @Override
    public @NotNull TacticalClientEntityController<Location> lookAtClosest(@NotNull Predicate<Entity> entityFilter, int maxDistance) {
        return new LookAtClosest(entityFilter, maxDistance);
    }
}
