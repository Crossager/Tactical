package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolSection;

/**
 * Represents the play section of the tactical protocol.
 * This section defines methods to access containers for incoming and outgoing play-related packets.
 */
public interface PlaySection extends ProtocolSection<PlayInContainer, PlayOutContainer> {
}
