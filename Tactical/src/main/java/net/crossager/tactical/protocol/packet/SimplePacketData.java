package net.crossager.tactical.protocol.packet;

import io.netty.buffer.ByteBuf;
import net.crossager.tactical.api.protocol.packet.PacketData;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.protocol.ProtocolUtils;
import org.jetbrains.annotations.NotNull;

public class SimplePacketData implements PacketData {
    private final PacketType packetType;
    private final SimplePacketReader reader;
    private final SimplePacketWriter writer;
    private final ByteBuf byteBuf;

    public SimplePacketData(PacketType packetType) {
        this(packetType, ProtocolUtils.newPacketDataSerializer(ProtocolUtils.newByteBuf()));
    }

    public SimplePacketData(PacketType packetType, ByteBuf byteBuf) {
        this.packetType = packetType;
        this.byteBuf = byteBuf;
        this.writer = new SimplePacketWriter(byteBuf);
        this.reader = new SimplePacketReader(byteBuf);
    }

    @Override
    public @NotNull SimplePacketWriter writer() {
        return writer;
    }

    @Override
    public @NotNull SimplePacketReader reader() {
        return reader;
    }

    @Override
    public @NotNull PacketData createCopy() {
        return new SimplePacketData(packetType, byteBuf.copy());
    }

    @Override
    public @NotNull PacketType type() {
        return packetType;
    }

    @Override
    public @NotNull Object createPacket() {
        return reader.readSilentAndReturn(x -> packetType.packetConstructor().apply(byteBuf));
    }
}
