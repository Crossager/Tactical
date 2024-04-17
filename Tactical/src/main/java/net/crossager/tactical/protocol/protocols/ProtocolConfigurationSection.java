package net.crossager.tactical.protocol.protocols;

import net.crossager.tactical.api.TacticalProtocol;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.protocols.*;
import org.jetbrains.annotations.NotNull;

public class ProtocolConfigurationSection implements ConfigurationSection {
    private final ConfigurationInContainer in = new ProtocolConfigurationInContainer(TacticalProtocol.getInstance().getProtocolManager(Protocol.CONFIGURATION, Sender.CLIENT));
    private final ConfigurationOutContainer out = new ProtocolConfigurationOutContainer(TacticalProtocol.getInstance().getProtocolManager(Protocol.CONFIGURATION, Sender.SERVER));
    @Override
    public @NotNull Protocol protocol() {
        return Protocol.CONFIGURATION;
    }

    @Override
    public @NotNull ConfigurationInContainer in() {
        return in;
    }

    @Override
    public @NotNull ConfigurationOutContainer out() {
        return out;
    }
}
