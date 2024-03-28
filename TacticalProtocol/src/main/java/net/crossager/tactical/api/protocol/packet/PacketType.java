package net.crossager.tactical.api.protocol.packet;

import net.crossager.tactical.api.TacticalProtocol;
import net.crossager.tactical.api.protocol.DirectionalProtocol;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.protocols.HandshakingSection;
import net.crossager.tactical.api.protocol.protocols.LoginSection;
import net.crossager.tactical.api.protocol.protocols.PlaySection;
import net.crossager.tactical.api.protocol.protocols.StatusSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a type of packet within the tactical protocol system.
 * This interface extends DirectionalProtocol.
 */
public interface PacketType extends DirectionalProtocol {
    /**
     * Retrieves the class of the packet.
     *
     * @return The class of the packet.
     */
    @NotNull
    Class<?> packetClass();

    /**
     * Retrieves the ID of the packet.
     *
     * @return The ID of the packet.
     */
    int packetId();

    /**
     * Retrieves the constructor function for creating packets.
     *
     * @return The constructor function for creating packets.
     */
    @NotNull
    PacketConstructor<?> packetConstructor();

    /**
     * Creates an empty packet data.
     *
     * @return An empty packet data.
     */
    @NotNull
    PacketData createEmptyPacketData();

    /**
     * Adds a packet listener to this packet type.
     *
     * @param listener The packet listener to add.
     */
    void addPacketListener(@NotNull PacketListener listener);

    /**
     * Removes a packet listener from this packet type.
     *
     * @param listener The packet listener to remove.
     */
    void removePacketListener(@NotNull PacketListener listener);

    /**
     * Retrieves the list of packet listeners associated with this packet type.
     *
     * @return The list of packet listeners.
     */
    @NotNull
    List<PacketListener> listeners();

    /**
     * Sends a packet to a specific player.
     *
     * @param player   The player to send the packet to.
     * @param data     The packet data to send.
     */
    void sendPacket(@NotNull Player player, @NotNull PacketData data);

    /**
     * Sends a packet to a specific player with data provided by a consumer.
     *
     * @param player    The player to send the packet to.
     * @param writeData The consumer providing the packet data.
     */
    void sendPacket(@NotNull Player player, @NotNull Consumer<PacketWriter> writeData);

    /**
     * Sends a packet to all players.
     *
     * @param data The packet data to send.
     */
    void sendPacketToAll(@NotNull PacketData data);

    /**
     * Sends a packet to all players with data provided by a consumer.
     *
     * @param writeData The consumer providing the packet data.
     */
    void sendPacketToAll(@NotNull Consumer<PacketWriter> writeData);

    /**
     * Receives a packet from a specific player.
     *
     * @param player The player from whom the packet is received.
     * @param data   The packet data received.
     */
    void receivePacketFrom(@NotNull Player player, @NotNull PacketData data);

    /**
     * Receives a packet from a specific player with data provided by a consumer.
     *
     * @param player    The player from whom the packet is received.
     * @param writeData The consumer providing the packet data.
     */
    void receivePacketFrom(@NotNull Player player, @NotNull Consumer<PacketWriter> writeData);

    /**
     * Retrieves the handshaking section of the tactical protocol.
     *
     * @return The handshaking section.
     */
    static HandshakingSection handshaking() {
        return (HandshakingSection) TacticalProtocol.getInstance().getProtocolSection(Protocol.HANDSHAKING);
    }

    /**
     * Retrieves the play section of the tactical protocol.
     *
     * @return The play section.
     */
    static PlaySection play() {
        return (PlaySection) TacticalProtocol.getInstance().getProtocolSection(Protocol.PLAY);
    }

    /**
     * Retrieves the status section of the tactical protocol.
     *
     * @return The status section.
     */
    static StatusSection status() {
        return (StatusSection) TacticalProtocol.getInstance().getProtocolSection(Protocol.STATUS);
    }

    /**
     * Retrieves the login section of the tactical protocol.
     *
     * @return The login section.
     */
    static LoginSection login() {
        return (LoginSection) TacticalProtocol.getInstance().getProtocolSection(Protocol.LOGIN);
    }
}
