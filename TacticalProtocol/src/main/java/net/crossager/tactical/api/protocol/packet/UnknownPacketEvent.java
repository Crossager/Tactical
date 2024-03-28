package net.crossager.tactical.api.protocol.packet;

import net.crossager.tactical.api.protocol.Sender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an unknown packet event in the tactical protocol system.
 */
public interface UnknownPacketEvent {
    /**
     * Retrieves the packet associated with this event.
     *
     * @return The packet associated with this event.
     */
    @NotNull
    Object packet();

    /**
     * Retrieves the player associated with this event.
     *
     * @return The player associated with this event.
     */
    @NotNull
    Player player();

    /**
     * Retrieves the sender of the packet associated with this event.
     *
     * @return The sender of the packet associated with this event.
     */
    @NotNull
    Sender sender();

    /**
     * Checks if this event is cancelled.
     *
     * @return true if the event is cancelled, false otherwise.
     */
    boolean isCancelled();

    /**
     * Sets the cancelled state of this event.
     *
     * @param cancel true to cancel the event, false to allow it.
     */
    void setCancelled(boolean cancel);
}
