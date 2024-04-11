package net.crossager.tactical.api.npc;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an interaction event with a client-side entity.
 *
 * @param <E> The type of the underlying entity
 */
public interface TacticalClientEntityInteractEvent<E extends Entity> {

    /**
     * Returns the client entity associated with this interaction event.
     *
     * @return The client entity associated with this interaction event
     */
    @NotNull
    TacticalClientEntity<E> clientEntity();

    /**
     * Returns the player who interacted with the client entity.
     *
     * @return The player who interacted with the client entity
     */
    @NotNull
    Player player();

    /**
     * Returns the target vector of the interaction event. Only available when type == {@link TacticalClientEntityInteractType#INTERACT_AT}
     *
     * @return The target vector of the interaction event
     * @throws java.util.NoSuchElementException if the interact type is not correct
     */
    @NotNull
    Vector target();

    /**
     * Returns the ID of the entity involved in the interaction event.
     *
     * @return The ID of the entity involved in the interaction event
     */
    int entityId();

    /**
     * Returns the equipment slot used during the interaction event.
     *
     * @return The equipment slot used during the interaction event
     */
    @NotNull
    EquipmentSlot hand();

    /**
     * Returns the type of interaction event.
     *
     * @return The type of interaction event
     */
    @NotNull
    TacticalClientEntityInteractType type();

    /**
     * Checks if the player is sneaking during the interaction event.
     *
     * @return {@code true} if the player is sneaking, {@code false} otherwise
     */
    boolean isSneaking();
}