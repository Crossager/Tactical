package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolContainer;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.util.AddedIn;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the incoming container for the configuration section of the tactical protocol.
 */
@AddedIn("1.20.2")
public interface ConfigurationInContainer extends ProtocolContainer {
    @NotNull
    PacketType clientInformation();
    /**
     * aka Plugin Message
     */
    @NotNull
    PacketType customPayload();
    @NotNull
    PacketType finishConfiguration();
    @NotNull
    PacketType keepAlive();
    @NotNull
    PacketType pong();
    @NotNull
    PacketType resourcePack();
}
