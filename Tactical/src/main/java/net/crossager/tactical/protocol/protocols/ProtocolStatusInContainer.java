package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.protocols.StatusInContainer;
import org.jetbrains.annotations.NotNull;

public class ProtocolStatusInContainer extends ProtocolContainerBase implements StatusInContainer {
    public ProtocolStatusInContainer(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    private final PacketType ping = get("Ping", "CPacketPing");
    private final PacketType start = get("Start", "CPacketServerQuery");

    @NotNull
    @Override
    public PacketType ping() {
        return ping;
    }

    @NotNull
    @Override
    public PacketType start() {
        return start;
    }
}
