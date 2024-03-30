package net.crossager.tactical.protocol.inject;

import net.crossager.tactical.api.TacticalAPI;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.packet.PacketEvent;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.UnknownPacketEvent;
import net.crossager.tactical.protocol.TacticalProtocolManager;
import net.crossager.tactical.protocol.packet.SimplePacketEvent;
import net.crossager.tactical.protocol.packet.SimpleUnknownPacketEvent;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class PacketInterceptor {
    private final TacticalProtocolManager protocolManager;

    public PacketInterceptor(TacticalProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
    }

    public boolean intercept(Player player, Object packet, Sender sender) {
        PacketType packetType;
        try {
            packetType = protocolManager.getPacketType(packet.getClass());
        } catch (NoSuchElementException e) {
            if (protocolManager.unknownPacketListener().isEmpty()) return true;
            UnknownPacketEvent packetEvent = new SimpleUnknownPacketEvent(player, packet, sender);
            protocolManager.unknownPacketListener().forEach(listener -> {
                try {
                    listener.listen(packetEvent);
                } catch (Exception ex) {
                    TacticalAPI.getInstance().getLogger().warning("Exception during unknown packet listener");
                    ex.printStackTrace();
                }
            });
            return !packetEvent.isCancelled();
        }
        if (packetType.listeners().isEmpty() && protocolManager.packetListeners().isEmpty()) return true;
        PacketEvent packetEvent = new SimplePacketEvent(player, packet, packetType);
        packetType.listeners().forEach(listener -> {
            try {
                listener.listen(packetEvent);
            } catch (Exception e) {
                TacticalAPI.getInstance().getLogger().warning("Exception during packet listener to type " + packetType.packetClass().getName());
                e.printStackTrace();
            }
        });
        protocolManager.packetListeners().forEach(listener -> {
            try {
                listener.listen(packetEvent);
            } catch (Exception e) {
                TacticalAPI.getInstance().getLogger().warning("Exception during packet listener");
                e.printStackTrace();
            }
        });
        return !packetEvent.isCancelled();
    }

    public Predicate<Object> asPredicate(Player player, Sender sender) {
        return packet -> this.intercept(player, packet, sender);
    }
}
