package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.protocols.StatusOutContainer;
import org.jetbrains.annotations.NotNull;

public class ProtocolStatusOutContainer extends ProtocolContainerBase implements StatusOutContainer {
    public ProtocolStatusOutContainer(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    private final PacketType pong = get("Pong", "SPacketPong");
    private final PacketType serverInfo = get("ServerInfo", "SPacketServerInfo");

    @NotNull
    @Override
    public PacketType pong() {
        return pong;
    }

    @NotNull
    @Override
    public PacketType serverInfo() {
        return serverInfo;
    }
}
