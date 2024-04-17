package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolContainer;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.util.AddedIn;
import net.crossager.tactical.api.util.RemovedIn;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the outgoing container for the configuration section of the tactical protocol.
 */
@AddedIn("1.20.2")
public interface ConfigurationOutContainer extends ProtocolContainer {
    /**
     * aka Plugin Message
     */
    @NotNull
    PacketType customPayload();
    @NotNull
    PacketType disconnect();
    @NotNull
    PacketType finishConfiguration();
    @NotNull
    PacketType keepAlive();
    @NotNull
    PacketType ping();
    @NotNull
    PacketType registryData();
    @NotNull
    PacketType updateEnabledFeatures();
    @NotNull
    PacketType updateTags();

    @AddedIn("1.20.3")
    @NotNull
    PacketType removeResourcePack();
    @AddedIn("1.20.3")
    @NotNull
    PacketType addResourcePack();

    @RemovedIn("1.20.3")
    @NotNull
    PacketType resourcePack();
}
