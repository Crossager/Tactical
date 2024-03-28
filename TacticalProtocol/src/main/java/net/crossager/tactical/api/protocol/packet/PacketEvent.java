package net.crossager.tactical.api.protocol.packet;

import net.crossager.tactical.api.protocol.Protocol;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event associated with a packet.
 */
public interface PacketEvent extends UnknownPacketEvent {
    /**
     * Gets the type of packet associated with this event.
     *
     * @return The packet type.
     */
    @NotNull
    PacketType type();

    /**
     * Gets the data associated with the packet event.
     *
     * @return The packet data.
     */
    @NotNull
    PacketData data();

    /**
     * Gets the protocol associated with the packet event.
     *
     * @return The protocol.
     */
    @NotNull
    Protocol protocol();

    /**
     * Checks if the packet associated with this event is a custom packet.
     *
     * @return {@code true} if the packet is a custom packet, {@code false} otherwise.
     */
    boolean isCustomPacket();

    /**
     * Casts the packet associated with this event to the specified packet class.
     *
     * @param packetClass The class of the packet to convert to.
     * @param <P>         The type of the packet.
     * @return The converted packet instance.
     */
    <P> P asPacket(Class<P> packetClass);
}
