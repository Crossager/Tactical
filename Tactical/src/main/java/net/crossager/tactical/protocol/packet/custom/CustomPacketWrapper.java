package net.crossager.tactical.protocol.packet.custom;

import net.crossager.tactical.api.protocol.packet.custom.CustomPacket;

public class CustomPacketWrapper {
    protected final CustomPacket customPacket;

    public CustomPacketWrapper(CustomPacket customPacket) {
        this.customPacket = customPacket;
    }

    public CustomPacket customPacket() {
        return customPacket;
    }
}
