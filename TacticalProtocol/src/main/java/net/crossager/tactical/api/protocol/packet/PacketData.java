package net.crossager.tactical.api.protocol.packet;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the data associated with a packet.
 */
public interface PacketData {
    /**
     * Gets the writer for the packet data.
     *
     * @return The packet writer.
     */
    @NotNull
    PacketWriter writer();

    /**
     * Gets the reader for the packet data.
     *
     * @return The packet reader.
     */
    @NotNull
    PacketReader reader();

    /**
     * Creates a copy of the packet data.
     *
     * @return A new instance of packet data with the same contents.
     */
    @NotNull
    PacketData createCopy();

    /**
     * Gets the type of packet associated with this data.
     *
     * @return The packet type.
     */
    @NotNull
    PacketType type();

    /**
     * Creates a new instance of the packet associated with this data.
     *
     * @return The new packet instance.
     */
    @NotNull
    Object createPacket();
}
