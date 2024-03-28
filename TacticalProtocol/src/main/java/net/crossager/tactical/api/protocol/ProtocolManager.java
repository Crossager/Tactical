package net.crossager.tactical.api.protocol;

import net.crossager.tactical.api.TacticalProtocol;
import net.crossager.tactical.api.protocol.packet.PacketConstructor;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.custom.CustomPacket;
import net.crossager.tactical.api.protocol.packet.custom.CustomPacketConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * A manager interface for protocols, providing methods to register and retrieve packet types.
 */
public interface ProtocolManager {
    /**
     * Retrieves the protocol associated with this protocol manager.
     *
     * @return The protocol associated with this protocol manager.
     */
    @NotNull
    Protocol protocol();

    /**
     * Retrieves the sender associated with this protocol manager.
     *
     * @return The sender associated with this protocol manager.
     */
    @NotNull
    Sender sender();

    /**
     * Retrieves a list of all registered packet types.
     *
     * @return A list of all registered packet types.
     */
    @NotNull
    List<PacketType> allPackets();

    /**
     * Retrieves the packet ID for the given packet class.
     *
     * @param packetClass The class of the packet.
     * @return The packet ID for the given packet class.
     */
    int getPacketId(@NotNull Class<?> packetClass);

    /**
     * Retrieves the packet type for the given packet class.
     *
     * @param packetClass The class of the packet.
     * @return The packet type for the given packet class.
     */
    @NotNull
    PacketType getPacketType(@NotNull Class<?> packetClass);

    /**
     * Registers a packet type for the given packet class.
     *
     * @param packetClass  The class of the packet.
     * @param <P>          The type of the packet.
     * @return The registered packet type.
     */
    @NotNull
    <P> PacketType registerPacket(@NotNull Class<P> packetClass);

    /**
     * Registers a packet type for the given packet class.
     *
     * @param packetClass  The class of the packet.
     * @param constructor  The constructor for creating instances of the packet.
     * @param <P>          The type of the packet.
     * @return The registered packet type.
     */
    @NotNull
    <P> PacketType registerPacket(@NotNull Class<P> packetClass, @NotNull PacketConstructor<P> constructor);

    /**
     * Registers a packet type for the given packet class with a specific ID.
     *
     * @param packetClass  The class of the packet.
     * @param constructor  The constructor for creating instances of the packet.
     * @param specificId   The specific ID to register the packet with.
     * @param <P>          The type of the packet.
     * @return The registered packet type.
     */
    @NotNull
    <P> PacketType registerPacketWithSpecificId(@NotNull Class<P> packetClass, @NotNull PacketConstructor<P> constructor, int specificId);

    /**
     * Registers a custom packet type for the given packet class.
     *
     * @param packetClass  The class of the custom packet.
     * @param constructor  The constructor for creating instances of the custom packet.
     * @param <P>          The type of the custom packet.
     * @return The registered packet type.
     */
    @NotNull
    <P extends CustomPacket> PacketType registerCustomPacket(@NotNull Class<P> packetClass, @NotNull CustomPacketConstructor<P> constructor);

    /**
     * Registers a custom packet type for the given packet class with a specific ID.
     *
     * @param packetClass  The class of the custom packet.
     * @param constructor  The constructor for creating instances of the custom packet.
     * @param specificId   The specific ID to register the custom packet with.
     * @param <P>          The type of the custom packet.
     * @return The registered packet type.
     */
    @NotNull
    <P extends CustomPacket> PacketType registerCustomPacketWithSpecificId(@NotNull Class<P> packetClass, @NotNull CustomPacketConstructor<P> constructor, int specificId);

    /**
     * Registers a custom packet type for the given wrapped class name.
     *
     * @param wrappedClassName The wrapped class name of the custom packet.
     * @param constructor      The constructor for creating instances of the custom packet.
     * @param <P>              The type of the custom packet.
     * @return The registered packet type.
     */
    @NotNull
    <P extends CustomPacket> PacketType registerCustomPacket(@NotNull String wrappedClassName, @NotNull CustomPacketConstructor<P> constructor);

    /**
     * Registers a custom packet type for the given wrapped class name with a specific ID.
     *
     * @param wrappedClassName The wrapped class name of the custom packet.
     * @param constructor      The constructor for creating instances of the custom packet.
     * @param specificId       The specific ID to register the custom packet with.
     * @param <P>              The type of the custom packet.
     * @return The registered packet type.
     */
    @NotNull
    <P extends CustomPacket> PacketType registerCustomPacketWithSpecificId(@NotNull String wrappedClassName, @NotNull CustomPacketConstructor<P> constructor, int specificId);

    /**
     * Retrieves the underlying packet map containing packet classes and their corresponding IDs.
     *
     * @return The underlying packet map.
     */
    @NotNull
    Map<Class<?>, Integer> getUnderlyingPacketMap();

    /**
     * Retrieves the protocol manager for the specified protocol and sender.
     *
     * @param protocol The protocol.
     * @param sender   The sender.
     * @return The protocol manager for the specified protocol and sender.
     */
    static ProtocolManager getProtocolManager(@NotNull Protocol protocol, @NotNull Sender sender) {
        return TacticalProtocol.getInstance().getProtocolManager(protocol, sender);
    }
}
