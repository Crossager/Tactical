package net.crossager.tactical.api.protocol.packet;

import org.jetbrains.annotations.NotNull;

/**
 * A functional interface for listening to packet events.
 */
@FunctionalInterface
public interface PacketListener {
    /**
     * Listens to a packet event.
     *
     * @param event The packet event to handle.
     */
    void listen(@NotNull PacketEvent event);
}
