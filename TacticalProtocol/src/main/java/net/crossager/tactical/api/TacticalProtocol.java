package net.crossager.tactical.api;

import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.ProtocolSection;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.packet.PacketListener;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.UnknownPacketListener;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Provides access to protocol sections, protocol managers, and packet types.
 */
public interface TacticalProtocol {

    /**
     * Returns the singleton instance of the {@link TacticalProtocol} interface.
     *
     * @return the singleton instance of the {@link TacticalProtocol} interface
     */
    @NotNull
    static TacticalProtocol getInstance() {
        return ProtocolAPIHolder.getInstance();
    }

    /**
     * Returns the protocol section for the specified protocol.
     *
     * @param protocol the protocol to get the section for
     * @return the protocol section for the specified protocol
     */
    @NotNull
    ProtocolSection<?, ?> getProtocolSection(Protocol protocol);

    /**
     * Returns the protocol manager for the specified protocol and sender.
     *
     * @param protocol the protocol to get the manager for
     * @param sender the sender to get the manager for
     * @return the protocol manager for the specified protocol and sender
     */
    @NotNull
    ProtocolManager getProtocolManager(@NotNull Protocol protocol, @NotNull Sender sender);

    /**
     * Returns the packet type for the specified packet class.
     *
     * @param packetClass the packet class to get the type for
     * @return the packet type for the specified packet class
     */
    @NotNull
    PacketType getPacketType(@NotNull Class<?> packetClass);

    /**
     * Adds an {@link UnknownPacketListener} to this {@link TacticalProtocol} instance.
     * Listens to all unknown incoming packets, usually all packets are known, but in edge cases, this is available.
     *
     * @param listener the {@code UnknownPacketListener} to add
     */
    void addUnknownPacketListener(@NotNull UnknownPacketListener listener);

    /**
     * Removes an {@link UnknownPacketListener} from this {@link TacticalProtocol} instance.
     *
     * @param listener the {@link UnknownPacketListener} to remove
     */
    void removeUnknownPacketListener(@NotNull UnknownPacketListener listener);

    /**
     * Adds a {@link PacketListener} to this {@link TacticalProtocol} instance.
     * Listens to all incoming packets, regardless of type.
     *
     * @param listener the {@link PacketListener} to add
     */
    void addPacketListener(@NotNull PacketListener listener);

    /**
     * Removes a {@link PacketListener} from this {@link TacticalProtocol} instance.
     *
     * @param listener the {@link PacketListener} to remove
     */

    void removePacketListener(@NotNull PacketListener listener);
    /**
     * Sends a packet to a player.
     *
     * @param player the player to send the packet to
     * @param packet the packet to send
     */
    void sendPacket(@NotNull Player player, @NotNull Object packet);
}
