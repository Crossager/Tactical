package net.crossager.tactical.api.protocol;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a protocol container
 */
public interface ProtocolContainer extends DirectionalProtocol {
    /**
     * Retrieves the protocol manager associated with this protocol container.
     *
     * @return The protocol manager associated with this protocol container.
     */
    @NotNull
    ProtocolManager protocolManager();
}
