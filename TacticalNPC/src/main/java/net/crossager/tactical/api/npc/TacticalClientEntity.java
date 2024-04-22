package net.crossager.tactical.api.npc;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Represents an entity on the client side in a Minecraft environment.
 *
 * @param <E> The type of the underlying entity
 */
public interface TacticalClientEntity<E extends Entity> {

    /**
     * Returns the render distance of the entity.
     *
     * @return The render distance of the entity
     */
    double renderDistance();

    /**
     * Returns the extra distance in which the entity won't despawn
     *
     * @return The buffer render distance of the entity
     */
    double bufferRenderDistance();

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
     * Plays an entity status event for all players in render distance. A full list of the entity statuses can be found at <a href="https://wiki.vg/Entity_statuses">https://wiki.vg/Entity_statuses</a>
     * @param status the id of the status to be played
     */
    void playEntityStatus(int status);

    /**
     * Plays an entity status event for all players in render distance.
     * @param status the status to be played
     */
    void playEntityStatus(@NotNull EntityEffect status);

    /**
     * Displays the entity to a specific player.
     *
     * @param player The player to whom the entity will be shown
     * @return {@code true} if the entity is successfully shown to the player, {@code false} otherwise
     */
    boolean showToPlayer(@NotNull Player player);

    /**
     * Sets the render distance of the entity.
     *
     * @param renderDistance The render distance to set
     * @return The modified TacticalClientEntity object
     */
    @NotNull
    TacticalClientEntity<E> renderDistance(double renderDistance);

    /**
     * Sets the buffer render distance of the entity.
     *
     * @param bufferRenderDistance The buffer render distance to set
     * @return The modified TacticalClientEntity object
     */
    @NotNull
    TacticalClientEntity<E> bufferRenderDistance(double bufferRenderDistance);

    /**
     * Sets the location of the entity.
     *
     * @param location The location to set
     * @return The modified TacticalClientEntity object
     */
    @NotNull
    TacticalClientEntity<E> location(Location location);

    /**
     * Displays the entity to a collection of players.
     *
     * @param players The collection of players to whom the entity will be shown
     * @return The modified TacticalClientEntity object
     */
    @NotNull
    TacticalClientEntity<E> showToPlayers(@NotNull Collection<Player> players);

    /**
     * Displays the entity to players that satisfy the given predicate condition.
     *
     * @param showToPlayer The predicate condition to determine which players the entity will be shown to
     * @return The modified TacticalClientEntity object
     */
    @NotNull
    TacticalClientEntity<E> showToPlayers(@NotNull Predicate<Player> showToPlayer);

    /**
     * Registers a consumer function to be invoked when the client entity is interacted with.
     * The consumer receives a TacticalClientEntityInteractEvent object containing relevant information about the interaction event.
     *
     * @param onInteract The consumer function to be invoked on interaction
     * @return The modified TacticalClientEntity object
     */
    @NotNull
    TacticalClientEntity<E> onInteract(@NotNull Consumer<TacticalClientEntityInteractEvent<E>> onInteract);
}
