package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.protocols.HandshakingInContainer;
import org.jetbrains.annotations.NotNull;

public class ProtocolHandshakingInContainer extends ProtocolContainerBase implements HandshakingInContainer {
    public ProtocolHandshakingInContainer(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    private final PacketType setProtocol = get("SetProtocol", "C00Handshake");

    @NotNull
    @Override
    public PacketType setProtocol() {
        return setProtocol;
    }
}
