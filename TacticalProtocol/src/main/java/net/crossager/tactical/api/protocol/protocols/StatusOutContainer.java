package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.ProtocolContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the output container for the status section of the tactical protocol.
 * This container defines methods to access different types of outgoing packets related to status information.
 */
public interface StatusOutContainer extends ProtocolContainer {
    @NotNull
    PacketType pong();
    @NotNull
    PacketType serverInfo();
}
