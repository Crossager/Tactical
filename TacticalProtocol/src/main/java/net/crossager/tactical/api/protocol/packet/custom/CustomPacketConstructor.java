package net.crossager.tactical.api.protocol.packet.custom;

import net.crossager.tactical.api.protocol.packet.PacketReader;

public interface CustomPacketConstructor<P extends CustomPacket> {
    P create(PacketReader reader);
}
