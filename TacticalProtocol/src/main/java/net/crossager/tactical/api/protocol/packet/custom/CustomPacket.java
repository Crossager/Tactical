package net.crossager.tactical.api.protocol.packet.custom;

import net.crossager.tactical.api.protocol.packet.PacketWriter;
import org.jetbrains.annotations.NotNull;

public interface CustomPacket {
    void write(@NotNull PacketWriter writer);
    default void onReceived() {

    }
}
