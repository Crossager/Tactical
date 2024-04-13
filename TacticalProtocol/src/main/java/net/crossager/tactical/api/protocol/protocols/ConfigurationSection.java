package net.crossager.tactical.api.protocol.protocols;

import net.crossager.tactical.api.protocol.ProtocolSection;
import net.crossager.tactical.api.util.AddedIn;

/**
 * Represents the configuration section of the tactical protocol.
 * This interface extends ProtocolSection and specifies the types of packets
 * associated with the configuration section.
 */
@AddedIn("1.20.2")
public interface ConfigurationSection extends ProtocolSection<ConfigurationInContainer, ConfigurationOutContainer> {
}
