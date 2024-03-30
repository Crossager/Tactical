package net.crossager.tactical.protocol.packet;

import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.packet.*;
import net.crossager.tactical.protocol.DirectionalProtocolBase;
import net.crossager.tactical.protocol.ProtocolUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SimplePacketType extends DirectionalProtocolBase implements PacketType {
    private final Class<?> packetClass;
    private final int id;
    private final PacketConstructor<?> packetConstructor;
    private final List<PacketListener> listeners;

    public SimplePacketType(ProtocolManager protocolManager, Class<?> packetClass, int id, PacketConstructor<?> packetConstructor) {
        super(protocolManager);
        this.packetClass = packetClass;
        this.id = id;
        this.packetConstructor = packetConstructor;
        this.listeners = new ArrayList<>();
    }

    @Override
    public @NotNull Class<?> packetClass() {
        return packetClass;
    }

    @Override
    public int packetId() {
        return id;
    }

    @Override
    public @NotNull PacketConstructor<?> packetConstructor() {
        return packetConstructor;
    }

    @Override
    public @NotNull SimplePacketData createEmptyPacketData() {
        return new SimplePacketData(this);
    }

    @Override
    public void addPacketListener(@NotNull PacketListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removePacketListener(@NotNull PacketListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public @NotNull List<PacketListener> listeners() {
        return listeners;
    }

    @Override
    public void sendPacket(@NotNull Player player, @NotNull PacketData data) {
        ProtocolUtils.sendPacket(player, data.createPacket());
    }

    @Override
    public void sendPacket(@NotNull Player player, @NotNull Consumer<PacketWriter> writeData) {
        SimplePacketData data = createEmptyPacketData();
        writeData.andThen(packetWriter -> sendPacket(player, data))
                .accept(data.writer());
    }

    @Override
    public void sendPacketToAll(@NotNull PacketData data) {
        Object packet = data.createPacket();
        Bukkit.getOnlinePlayers().forEach(player -> ProtocolUtils.sendPacket(player, packet));
    }

    @Override
    public void sendPacketToAll(@NotNull Consumer<PacketWriter> writeData) {
        SimplePacketData data = createEmptyPacketData();
        writeData.andThen(packetWriter -> sendPacketToAll(data))
                .accept(data.writer());
    }

    @Override
    public void receivePacketFrom(@NotNull Player player, @NotNull PacketData data) {
        ProtocolUtils.receivePacket(player, data.createPacket());
    }

    @Override
    public void receivePacketFrom(@NotNull Player player, @NotNull Consumer<PacketWriter> writeData) {
        SimplePacketData data = createEmptyPacketData();
        writeData.andThen(packetWriter -> receivePacketFrom(player, data))
                .accept(data.writer());
    }

    @Override
    public String toString() {
        return "PacketType{" +
                "packetClass=" + packetClass +
                ", id=" + id +
                '}';
    }
}
