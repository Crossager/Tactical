package net.crossager.tactical.api.npc;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface TacticalClientObject<T extends TacticalClientObject<T>> {
    /**
     * Returns the render distance of the object.
     *
     * @return The render distance of the object
     */
    double renderDistance();

    /**
     * Returns the extra distance in which the object will not despawn
     *
     * @return The buffer render distance of the object
     */
    double bufferRenderDistance();

    /**
     * Returns whether the entity will be displayed to a specific player.
     *
     * @param player The player to whom the entity will be shown
     * @return {@code true} if the entity is shown to the player, {@code false} otherwise
     */
    boolean showToPlayer(@NotNull Player player);

    /**
     * Sets the render distance of the entity.
     *
     * @param renderDistance The render distance to set
     * @return The modified TacticalClientObject object
     */
    @NotNull
    T renderDistance(double renderDistance);

    /**
     * Sets the buffer render distance of the entity.
     *
     * @param bufferRenderDistance The buffer render distance to set
     * @return The modified TacticalClientObject object
     */
    @NotNull
    T bufferRenderDistance(double bufferRenderDistance);

    /**
     * Displays the entity to a collection of players.
     *
     * @param players The collection of players to whom the entity will be shown
     * @return The modified TacticalClientObject object
     */
    @NotNull
    T showToPlayers(@NotNull Collection<Player> players);

    /**
     * Displays the entity to players that satisfy the given predicate condition.
     *
     * @param showToPlayer The predicate condition to determine which players the entity will be shown to
     * @return The modified TacticalClientObject object
     */
    @NotNull
    T showToPlayers(@NotNull Predicate<Player> showToPlayer);

    /**
     * Registers a consumer function to be invoked when the client entity is interacted with.
     * The consumer receives an event object containing relevant information about the interaction event.
     *
     * @param onInteract The consumer function to be invoked on interaction
     * @return The modified TacticalClientObject object
     */
    @NotNull
    T onInteract(@NotNull Consumer<TacticalClientEntityInteractEvent<T>> onInteract);
}