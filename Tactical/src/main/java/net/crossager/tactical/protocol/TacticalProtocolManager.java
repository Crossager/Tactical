package net.crossager.tactical.protocol;

import net.crossager.tactical.api.TacticalAPI;
import net.crossager.tactical.api.data.LazyInitializedHashMap;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.ProtocolSection;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.packet.PacketListener;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.UnknownPacketListener;
import net.crossager.tactical.protocol.inject.PlayerInjectorHandler;
import net.crossager.tactical.protocol.protocols.ProtocolHandshakingSection;
import net.crossager.tactical.protocol.protocols.ProtocolLoginSection;
import net.crossager.tactical.protocol.protocols.ProtocolPlaySection;
import net.crossager.tactical.protocol.protocols.ProtocolStatusSection;
import net.crossager.tactical.util.Exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TacticalProtocolManager {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final LazyInitializedHashMap<Protocol, ProtocolSection<?, ?>> protocolSections = new LazyInitializedHashMap<>(protocol -> switch (protocol) {
        case HANDSHAKING -> new ProtocolHandshakingSection();
        case PLAY -> new ProtocolPlaySection();
        case STATUS -> new ProtocolStatusSection();
        case LOGIN -> new ProtocolLoginSection();
    });
    private final List<ProtocolManager> protocolManagers = new ArrayList<>();
    private boolean isInitialized = false;
    private PlayerInjectorHandler playerInjectorHandler;
    private final List<UnknownPacketListener> unknownPacketListeners = new ArrayList<>();
    private final List<PacketListener> packetListeners = new ArrayList<>();

    public void initialize() {
        if (isInitialized) return;
        this.playerInjectorHandler = new PlayerInjectorHandler(this);
        isInitialized = true;
        TacticalAPI.getInstance().getLogger().info("TacticalProtocol initialized");
    }

    public void disable() {
        if (!isInitialized) return;
        isInitialized = false;
        playerInjectorHandler.uninjectAll();
        if (ProtocolUtils.isCurrentlyReloading())
            TacticalAPI.getInstance().getLogger().warning("TacticalProtocol may cause unexpected issues when reloading your server, consider doing a server restart instead.");
    }

    public ProtocolSection<?, ?> getProtocolSection(Protocol protocol) {
        initialize();
        return protocolSections.get(protocol);
    }

    public ProtocolManager getProtocolManager(Protocol protocol, Sender sender) {
        initialize();
        if (protocol == Protocol.HANDSHAKING && sender == Sender.SERVER)
            return Exceptions.nse(protocol + " and " + sender + " does not share a protocol manager");
        return protocolManagers.stream().filter(protocolManager -> protocolManager.protocol().equals(protocol) && protocolManager.sender().equals(sender)).findAny().orElseGet(() -> {
            SimpleProtocolManager protocolManager = new SimpleProtocolManager(protocol, sender);
            protocolManagers.add(protocolManager);
            return protocolManager;
        });
    }

    public PacketType getPacketType(Class<?> packetClass) {
        try {
            return getPacketType(Sender.CLIENT, packetClass);
        } catch (NoSuchElementException e) {
            return getPacketType(Sender.SERVER, packetClass);
        }
    }

    public PacketType getPacketType(Sender sender, Class<?> packetClass) {
        for (Protocol protocol : Protocol.values()) {
            try {
                return getProtocolManager(protocol, sender).getPacketType(packetClass);
            } catch (NoSuchElementException ignored) {

            }
        }
        return Exceptions.notFound("PacketType");
    }

    public void registerUnknownPacketListener(UnknownPacketListener listener) {
        initialize();
        unknownPacketListeners.add(listener);
    }

    public void unregisterUnknownPacketListener(UnknownPacketListener listener) {
        unknownPacketListeners.remove(listener);
    }

    public List<UnknownPacketListener> unknownPacketListener() {
        return unknownPacketListeners;
    }

    public void registerPacketListener(PacketListener listener) {
        initialize();
        packetListeners.add(listener);
    }

    public void unregisterPacketListener(PacketListener listener) {
        packetListeners.remove(listener);
    }

    public List<PacketListener> packetListeners() {
        return packetListeners;
    }
}
