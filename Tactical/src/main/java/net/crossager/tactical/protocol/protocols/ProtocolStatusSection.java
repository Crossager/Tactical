package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.TacticalProtocol;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.protocols.*;
import org.jetbrains.annotations.NotNull;

public class ProtocolStatusSection implements StatusSection {
    private final StatusInContainer in = new ProtocolStatusInContainer(TacticalProtocol.getInstance().getProtocolManager(Protocol.STATUS, Sender.CLIENT));
    private final StatusOutContainer out = new ProtocolStatusOutContainer(TacticalProtocol.getInstance().getProtocolManager(Protocol.STATUS, Sender.SERVER));
    @Override
    public @NotNull Protocol protocol() {
        return Protocol.STATUS;
    }

    @Override
    public @NotNull StatusInContainer in() {
        return in;
    }

    @Override
    public @NotNull StatusOutContainer out() {
        return out;
    }
}
