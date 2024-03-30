package net.crossager.tactical.protocol.inject;

import net.crossager.tactical.protocol.TacticalProtocolManager;
import net.crossager.tactical.util.PlayerMap;

public class PlayerInjectorHandler {
    private final PacketInterceptor packetInterceptor;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final PlayerMap<PlayerInjector> playerInjectors;

    public PlayerInjectorHandler(TacticalProtocolManager protocolManager) {
        this.packetInterceptor = new PacketInterceptor(protocolManager);
        this.playerInjectors = new PlayerMap<>(player -> new PlayerInjector(player, packetInterceptor).inject());
    }

    public void uninjectAll() {
        playerInjectors.values().forEach(PlayerInjector::uninject);
    }
}
