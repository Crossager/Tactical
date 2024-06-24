package net.crossager.tactical.api.npc;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface TacticalNPCFactory {
    @NotNull
    TacticalHologram createHologram(@NotNull Location location, @NotNull String text);
    @NotNull
    TacticalHologram createHologram(@NotNull Location location, @NotNull String text, long updateInterval);

    @NotNull
    <E extends Entity> TacticalClientEntity<E> createClientEntity(@NotNull Location location, @NotNull Class<E> entityClass);
    @NotNull
    <E extends Entity> TacticalClientEntity<E> createClientEntity(@NotNull Location location, @NotNull Class<E> entityClass, @NotNull Consumer<E> applyInitialData);
    @NotNull
    <E extends Entity> TacticalClientEntity<E> createClientEntity(@NotNull Location location, @NotNull Class<E> entityClass, @NotNull Consumer<E> applyInitialData, long updateInterval);

    @NotNull
    TacticalPlayerNPC createPlayerNPC(@NotNull Location location, @NotNull String profileName);
    @NotNull
    TacticalPlayerNPC createPlayerNPC(@NotNull Location location, @NotNull String profileName, @NotNull TacticalPlayerSkin skin);
    @NotNull
    TacticalPlayerNPC createPlayerNPC(@NotNull Location location, @NotNull String profileName, @NotNull TacticalPlayerSkin skin, @NotNull Consumer<TacticalPlayerNPCMetaData> applyInitialMetaData);
    @NotNull
    TacticalPlayerNPC createPlayerNPC(@NotNull Location location, @NotNull String profileName, @NotNull TacticalPlayerSkin skin, @NotNull Consumer<TacticalPlayerNPCMetaData> applyInitialMetaData, long updateInterval);
    @NotNull
    TacticalPlayerNPC createPlayerNPC(@NotNull Location location, @NotNull String profileName, @NotNull TacticalPlayerSkin skin, @NotNull Consumer<TacticalPlayerNPCMetaData> applyInitialMetaData, long updateInterval, @NotNull UUID uuid);
    @NotNull
    TacticalClientEntityController<Location> lookAtClosest(@NotNull Predicate<Entity> entityFilter, int maxDistance);
}
