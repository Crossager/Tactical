package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.protocol.DirectionalProtocolBase;
import net.crossager.tactical.protocol.ProtocolUtils;
import net.crossager.tactical.util.reflect.MinecraftClasses;

public abstract class ProtocolContainerBase extends DirectionalProtocolBase {
    public ProtocolContainerBase(ProtocolManager protocolManager) {
        super(protocolManager);
    }

    protected PacketType get(String... names) {
        return protocolManager.getPacketType(
                MinecraftClasses.getClass(
                        ProtocolUtils.formatPossiblePacketNames(
                                protocolManager.protocol(),
                                protocolManager.sender(),
                                names
                        )));
    }
}
