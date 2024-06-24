package net.crossager.tactical.api.npc;

import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
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
     * Plays an animation for this entity
     * @param animation the animation to play
     */
    void playAnimation(@NotNull TacticalMobAnimation animation);
    /**
     * Makes this entity flash red as if they were damaged.
     */
    void playHurtAnimation();
    /**
     * Enables/disables the entity from being rendered
     */
    void setEnabled(boolean enabled);

    /**
     * @return true if the entity is being rendered
     */
    boolean isEnabled();

    /**
     * @return a modifiable list containing all the controllers for this entity
     */
    List<TacticalClientEntityController<?>> controllers();

    /**
     * Retrieves the data associated with the specified tag.
     *
     * @param <E> the type of the data associated with the tag
     * @param tag the tag whose data is to be retrieved, must not be null
     * @return the data associated with the specified tag, or null if no data is associated with the tag
     */
    <E> E getTagData(@NotNull TacticalClientEntityTag tag);

    /**
     * Sets the data for the specified tag.
     *
     * @param <E> the type of the data to be set for the tag
     * @param tag the tag for which the data is to be set, must not be null
     * @param data the data to set for the specified tag, must not be null
     */
    <E> void setTagData(@NotNull TacticalClientEntityTag tag, @NotNull E data);

    /**
     * Removes the data associated with the specified tag.
     *
     * @param tag the tag whose data is to be removed, must not be null
     */
    void removeTagData(@NotNull TacticalClientEntityTag tag);
}
