package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.protocols.LoginInContainer;
import org.jetbrains.annotations.NotNull;

public class ProtocolLoginInContainer extends ProtocolContainerBase implements LoginInContainer {
    public ProtocolLoginInContainer(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    private final PacketType customPayload = get("CustomPayload", "CPacketCustomPayload");
    private final PacketType encryptionBegin = get("EncryptionBegin", "CPacketEncryptionResponse");
    private final PacketType start = get("Start", "CPacketLoginStart");

    @NotNull
    @Override
    public PacketType customPayload() {
        return customPayload;
    }

    @NotNull
    @Override
    public PacketType encryptionBegin() {
        return encryptionBegin;
    }

    @NotNull
    @Override
    public PacketType start() {
        return start;
    }
}
