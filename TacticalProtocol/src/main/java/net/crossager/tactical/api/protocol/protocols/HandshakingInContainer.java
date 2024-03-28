package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.ProtocolContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the incoming container for the handshaking section of the tactical protocol.
 * This interface extends ProtocolContainer and defines a method to retrieve the 'setProtocol' packet type.
 */
public interface HandshakingInContainer extends ProtocolContainer {
    @NotNull
    PacketType setProtocol();
}
