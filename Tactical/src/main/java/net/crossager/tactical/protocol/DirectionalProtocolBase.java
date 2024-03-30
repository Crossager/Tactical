package net.crossager.tactical.protocol;

import net.crossager.tactical.api.protocol.DirectionalProtocol;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.Sender;
import org.jetbrains.annotations.NotNull;

public abstract class DirectionalProtocolBase implements DirectionalProtocol {
    protected final ProtocolManager protocolManager;

    protected DirectionalProtocolBase(ProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
    }

    @Override
    public @NotNull Protocol protocol() {
        return protocolManager.protocol();
    }

    @Override
    public @NotNull Sender sender() {
        return protocolManager.sender();
    }

    @Override
    public @NotNull ProtocolManager protocolManager() {
        return protocolManager;
    }
}
