package net.crossager.tactical.api.protocol;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a directional protocol, which specifies the protocol, sender, and protocol manager.
 */
public interface DirectionalProtocol {
    /**
     * Retrieves the protocol associated with this directional protocol.
     *
     * @return The protocol associated with this directional protocol.
     */
    @NotNull
    Protocol protocol();

    /**
     * Retrieves the sender of this directional protocol.
     *
     * @return The sender of this directional protocol.
     */
    @NotNull
    Sender sender();

    /**
     * Retrieves the protocol manager associated with this directional protocol.
     *
     * @return The protocol manager associated with this directional protocol.
     */
    @NotNull
    ProtocolManager protocolManager();
}
