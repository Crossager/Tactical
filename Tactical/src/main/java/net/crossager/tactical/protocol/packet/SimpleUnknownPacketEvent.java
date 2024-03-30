package net.crossager.tactical.protocol.packet;

import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.packet.UnknownPacketEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SimpleUnknownPacketEvent implements UnknownPacketEvent {
    private final Player player;
    private final Object actualPacket;
    private final Sender sender;
    private boolean isCancelled = false;

    public SimpleUnknownPacketEvent(Player player, Object actualPacket, Sender sender) {
        this.player = player;
        this.actualPacket = actualPacket;
        this.sender = sender;
    }

    @Override
    public @NotNull Object packet() {
        return actualPacket;
    }

    @Override
    public @NotNull Player player() {
        return player;
    }

    @Override
    public @NotNull Sender sender() {
        return sender;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }
}
