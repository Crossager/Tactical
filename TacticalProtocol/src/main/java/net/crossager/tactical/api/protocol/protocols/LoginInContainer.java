package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.ProtocolContainer;
import net.crossager.tactical.api.util.AddedIn;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the incoming packet container for the login section of the tactical protocol.
 * This interface extends ProtocolContainer and specifies the types of incoming packets associated
 * with the login section.
 */
public interface LoginInContainer extends ProtocolContainer {
    @NotNull
    PacketType customPayload();
    @NotNull
    PacketType encryptionBegin();
    @NotNull
    PacketType start();

    @AddedIn("1.20.2")
    @NotNull
    PacketType acknowledged();
}
