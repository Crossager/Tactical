package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.protocols.ConfigurationInContainer;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.jetbrains.annotations.NotNull;

public class ProtocolConfigurationInContainer extends ProtocolContainerBase implements ConfigurationInContainer {
    public ProtocolConfigurationInContainer(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    private final PacketType clientInformation = get(true, "ClientInformation");
    private final PacketType customPayload = get(true, "CustomPayload");
    private final PacketType finishConfiguration = get(true, "FinishConfiguration");
    private final PacketType keepAlive = get(true, "KeepAlive");
    private final PacketType pong = get(true, "Pong");
    private final PacketType resourcePack = get(true, "ResourcePack");

    @Override
    public @NotNull PacketType clientInformation() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return clientInformation;
    }

    @Override
    public @NotNull PacketType customPayload() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return customPayload;
    }

    @Override
    public @NotNull PacketType finishConfiguration() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return finishConfiguration;
    }

    @Override
    public @NotNull PacketType keepAlive() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return keepAlive;
    }

    @Override
    public @NotNull PacketType pong() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return pong;
    }

    @Override
    public @NotNull PacketType resourcePack() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return resourcePack;
    }
}
