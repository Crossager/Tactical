package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.protocols.LoginOutContainer;
import org.jetbrains.annotations.NotNull;

public class ProtocolLoginOutContainer extends ProtocolContainerBase implements LoginOutContainer {
    public ProtocolLoginOutContainer(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    private final PacketType customPayload = get("CustomPayload", "SPacketCustomPayload");
    private final PacketType disconnect = get("Disconnect", "SPacketDisconnect");
    private final PacketType encryptionBegin = get("EncryptionBegin", "SPacketEncryptionRequest");
    private final PacketType setCompression = get("SetCompression", "SPacketEnableCompression");
    private final PacketType success = get("Success", "SPacketLoginSuccess");

    @NotNull
    @Override
    public PacketType customPayload() {
        return customPayload;
    }

    @NotNull
    @Override
    public PacketType disconnect() {
        return disconnect;
    }

    @NotNull
    @Override
    public PacketType encryptionBegin() {
        return encryptionBegin;
    }

    @NotNull
    @Override
    public PacketType setCompression() {
        return setCompression;
    }

    @NotNull
    @Override
    public PacketType success() {
        return success;
    }
}
