package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.TacticalProtocol;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.protocols.PlayInContainer;
import net.crossager.tactical.api.protocol.protocols.PlayOutContainer;
import net.crossager.tactical.api.protocol.protocols.PlaySection;
import org.jetbrains.annotations.NotNull;

public class ProtocolPlaySection implements PlaySection {
    private final PlayInContainer in = new ProtocolPlayInContainer(TacticalProtocol.getInstance().getProtocolManager(Protocol.PLAY, Sender.CLIENT));
    private final PlayOutContainer out = new ProtocolPlayOutContainer(TacticalProtocol.getInstance().getProtocolManager(Protocol.PLAY, Sender.SERVER));
    @Override
    public @NotNull Protocol protocol() {
        return Protocol.PLAY;
    }

    @Override
    public @NotNull PlayInContainer in() {
        return in;
    }

    @Override
    public @NotNull PlayOutContainer out() {
        return out;
    }
}
