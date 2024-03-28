package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolSection;

/**
 * Represents the status section of the tactical protocol.
 * This section defines the input and output containers for status-related protocol data.
 */
public interface StatusSection extends ProtocolSection<StatusInContainer, StatusOutContainer> {
}
