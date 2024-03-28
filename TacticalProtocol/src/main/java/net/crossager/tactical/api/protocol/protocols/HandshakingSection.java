package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolSection;

/**
 * Represents the handshaking section of the tactical protocol.
 * This interface extends ProtocolSection and specifies the types of packets
 * associated with the handshaking section.
 */
public interface HandshakingSection extends ProtocolSection<HandshakingInContainer, HandshakingOutContainer> {
}
