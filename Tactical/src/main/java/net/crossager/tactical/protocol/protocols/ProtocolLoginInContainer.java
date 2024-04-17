package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.protocols.LoginInContainer;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.jetbrains.annotations.NotNull;

public class ProtocolLoginInContainer extends ProtocolContainerBase implements LoginInContainer {
    public ProtocolLoginInContainer(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    private final PacketType customPayload = get("CustomPayload", "CPacketCustomPayload");
    private final PacketType encryptionBegin = get("EncryptionBegin", "CPacketEncryptionResponse");
    private final PacketType start = get("Start", "CPacketLoginStart");
    private final PacketType acknowledged = get(true, "LoginAcknowledged");

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

    @Override
    public @NotNull PacketType acknowledged() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return acknowledged;
    }
}
