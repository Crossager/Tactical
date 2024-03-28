package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.ProtocolContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the outgoing packet container for the login section of the tactical protocol.
 * This interface extends ProtocolContainer and specifies the types of outgoing packets associated
 * with the login section.
 */
public interface LoginOutContainer extends ProtocolContainer {
    @NotNull
    PacketType customPayload();
    @NotNull
    PacketType disconnect();
    @NotNull
    PacketType encryptionBegin();
    @NotNull
    PacketType setCompression();
    @NotNull
    PacketType success();
}
