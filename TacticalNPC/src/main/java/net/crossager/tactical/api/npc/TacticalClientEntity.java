package net.crossager.tactical.api.npc;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an entity on the client side in a Minecraft environment.
 *
 * @param <E> The type of the underlying entity
 */
public interface TacticalClientEntity<E extends Entity> extends TacticalClientObject<TacticalClientEntity<E>> {
    /**
     * Returns the underlying entity object represented by this client entity.
     *
     * @return The underlying entity object
     */
    @NotNull
    E entity();

    /**
     * Updates the metadata associated with the entity.
     * This method is typically used to synchronize entity data between the server and the client.
     */
    void updateMetaData();
    /**
     * Sets the location of the entity.
     *
     * @param location The location to set
     * @return The modified TacticalClientEntity object
     */
    @NotNull
    TacticalClientEntity<E> location(Location location);
}
