package net.crossager.tactical.protocol.packet;

import net.crossager.tactical.api.data.LazyInitializer;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.packet.PacketData;
import net.crossager.tactical.api.protocol.packet.PacketEvent;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.protocol.ProtocolUtils;
import net.crossager.tactical.protocol.packet.custom.CustomPacketWrapper;
import net.crossager.tactical.util.InternalUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SimplePacketEvent extends SimpleUnknownPacketEvent implements PacketEvent {
    private final LazyInitializer<PacketData> packetData;
    private final PacketType packetType;

    public SimplePacketEvent(Player player, Object actualPacket, PacketType packetType) {
        super(player, actualPacket, packetType.sender());
        this.packetType = packetType;
        this.packetData = new LazyInitializer<>(() -> new SimplePacketData(packetType, ProtocolUtils.writePacketData(actualPacket)));
    }

    @Override
    public @NotNull PacketType type() {
        return packetType;
    }

    @Override
    public @NotNull PacketData data() {
        return packetData.get();
    }

    @Override
    public @NotNull Protocol protocol() {
        return packetType.protocol();
    }

    @Override
    public boolean isCustomPacket() {
        return packet() instanceof CustomPacketWrapper;
    }

    @Override
    public <P> P asPacket(Class<P> packetClass) {
        if (packet() instanceof CustomPacketWrapper wrapper) return InternalUtils.cast(wrapper.customPacket(), packetClass);
        return InternalUtils.cast(packet(), packetClass);
    }
}
