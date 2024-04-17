package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.protocols.ConfigurationOutContainer;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.jetbrains.annotations.NotNull;

public class ProtocolConfigurationOutContainer extends ProtocolContainerBase implements ConfigurationOutContainer {
    public ProtocolConfigurationOutContainer(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    private final PacketType customPayload = get(true, "CustomPayload");
    private final PacketType disconnect = get(true, "Disconnect");
    private final PacketType finishConfiguration = get(true, "FinishConfiguration");
    private final PacketType keepAlive = get(true, "KeepAlive");
    private final PacketType ping = get(true, "Ping");
    private final PacketType registryData = get(true, "RegistryData");
    private final PacketType resourcePack = get(true, "ResourcePack");
    private final PacketType updateEnabledFeatures = get(true, "UpdateEnabledFeatures");
    private final PacketType updateTags = get(true, "UpdateTags");

    @Override
    public @NotNull PacketType customPayload() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return customPayload;
    }

    @Override
    public @NotNull PacketType disconnect() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return disconnect;
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
    public @NotNull PacketType ping() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return ping;
    }

    @Override
    public @NotNull PacketType registryData() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return registryData;
    }

    @Override
    public @NotNull PacketType resourcePack() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return resourcePack;
    }

    @Override
    public @NotNull PacketType updateEnabledFeatures() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return updateEnabledFeatures;
    }

    @Override
    public @NotNull PacketType updateTags() {
        MinecraftVersion.ensureAboveVersion(MinecraftVersion.v1_20_2);
        return updateTags;
    }
}
