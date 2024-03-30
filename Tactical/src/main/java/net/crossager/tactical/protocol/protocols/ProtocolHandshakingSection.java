package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.TacticalProtocol;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.protocols.HandshakingInContainer;
import net.crossager.tactical.api.protocol.protocols.HandshakingOutContainer;
import net.crossager.tactical.api.protocol.protocols.HandshakingSection;
import org.jetbrains.annotations.NotNull;

public class ProtocolHandshakingSection implements HandshakingSection {
    private final HandshakingInContainer in = new ProtocolHandshakingInContainer(TacticalProtocol.getInstance().getProtocolManager(Protocol.HANDSHAKING, Sender.CLIENT));
    @Override
    public @NotNull Protocol protocol() {
        return Protocol.HANDSHAKING;
    }

    @Override
    public @NotNull HandshakingInContainer in() {
        return in;
    }

    @Override
    public @NotNull HandshakingOutContainer out() {
        return (HandshakingOutContainer) TacticalProtocol.getInstance().getProtocolManager(Protocol.HANDSHAKING, Sender.SERVER);
    }
}
