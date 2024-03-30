package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.TacticalProtocol;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.protocols.*;
import org.jetbrains.annotations.NotNull;

public class ProtocolLoginSection implements LoginSection {
    private final LoginInContainer in = new ProtocolLoginInContainer(TacticalProtocol.getInstance().getProtocolManager(Protocol.LOGIN, Sender.CLIENT));
    private final LoginOutContainer out = new ProtocolLoginOutContainer(TacticalProtocol.getInstance().getProtocolManager(Protocol.LOGIN, Sender.SERVER));
    @Override
    public @NotNull Protocol protocol() {
        return Protocol.LOGIN;
    }

    @Override
    public @NotNull LoginInContainer in() {
        return in;
    }

    @Override
    public @NotNull LoginOutContainer out() {
        return out;
    }
}
