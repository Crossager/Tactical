package net.crossager.tactical.api.protocol.packet;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a listener for unknown packet events in the tactical protocol system.
 */
public interface UnknownPacketListener {
    /**
     * Handles an unknown packet event.
     *
     * @param event The unknown packet event to handle.
     */
    void listen(@NotNull UnknownPacketEvent event);
}
