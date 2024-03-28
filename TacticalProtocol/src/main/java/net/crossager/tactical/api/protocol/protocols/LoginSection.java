package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolSection;

/**
 * Represents the login section of the tactical protocol.
 * This interface extends ProtocolSection and specifies the types of incoming and outgoing packets
 * associated with the login section.
 */
public interface LoginSection extends ProtocolSection<LoginInContainer, LoginOutContainer> {
}
