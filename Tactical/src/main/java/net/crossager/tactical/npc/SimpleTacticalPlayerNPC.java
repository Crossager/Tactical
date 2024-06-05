package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalPlayerNPC;
import net.crossager.tactical.api.npc.TacticalPlayerNPCMetaData;
import net.crossager.tactical.api.npc.TacticalPlayerSkin;
import net.crossager.tactical.api.protocol.packet.PacketListener;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.protocol.ProtocolUtils;
import net.crossager.tactical.util.PlayerSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;

public class SimpleTacticalPlayerNPC extends SimpleTacticalClientObject<TacticalPlayerNPC> implements TacticalPlayerNPC {
    private final Set<Player> isDisplayedForPlayer = new PlayerSet();
    private final SimpleTacticalPlayerNPCMetaData metaData;
    private final TacticalPlayerNPCPacketManager packetManager;
    private final EnumSet<SimpleTacticalPlayerNPCMetaData.ChangedMetaData> changedMetaData = EnumSet.noneOf(SimpleTacticalPlayerNPCMetaData.ChangedMetaData.class);
    private final JavaPlugin plugin;
    private final long updateInterval;
    private boolean removeOnUnload = false;

    private BukkitTask bukkitTask;
    private final PacketListener packetListener;

    public SimpleTacticalPlayerNPC(JavaPlugin plugin, String profileName, Location location, Consumer<TacticalPlayerNPCMetaData> applyPlayerData, TacticalPlayerSkin skin, long updateInterval) {
        this.plugin = plugin;
        this.updateInterval = updateInterval;
        metaData = new SimpleTacticalPlayerNPCMetaData(
                changedMetaData,
                location,
                ProtocolUtils.GENERATE_ENTITY_ID.get(),
                profileName,
                skin);
        applyPlayerData.accept(metaData);
        this.packetManager = new TacticalPlayerNPCPacketManager(metaData);

        this.packetListener = new TacticalClientEntityListener<>(metaData.entityId(), this, event -> {
            Bukkit.getScheduler().runTask(plugin, () -> onInteract.accept(event));
        });

        enable();
    }

    @Override
    protected TacticalPlayerNPC returnThis() {
        return this;
    }

    @Override
    protected int entityId() {
        return metaData.entityId();
    }

    @Override
    protected void enable() {
        bukkitTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::onTick, 0, updateInterval);
        PacketType.play().in().useEntity().addPacketListener(packetListener);
    }

    @Override
    protected void disable() {
        bukkitTask.cancel();
        PacketType.play().in().useEntity().removePacketListener(packetListener);
    }

    @Override
    protected Collection<Player> playersToSendPackets() {
        return isDisplayedForPlayer;
    }

    @Override
    public @NotNull TacticalPlayerNPCMetaData metaData() {
        return metaData;
    }

    @Override
    public boolean removeOnUnload() {
        return removeOnUnload;
    }

    @Override
    public @NotNull TacticalPlayerNPC removeOnUnload(boolean removeOnUnload) {
        this.removeOnUnload = removeOnUnload;
        return this;
    }

    private void onTick() {
        changedMetaData.forEach(changed -> {
            switch (changed) {
                case LOCATION -> packetManager.updateMovementPacket();
                case META_DATA -> packetManager.updateMetaDataPacket();
                case PLAYER_INFO_LISTED -> packetManager.updateUpdateListedPacket();
                case PLAYER_INFO_DISPLAY_NAME -> packetManager.updateUpdateDisplayNamePacket();
                case PLAYER_INFO_LATENCY -> packetManager.updateUpdateLatencyPacket();
                case PLAYER_INFO_GAME_MODE -> packetManager.updateUpdateGameModePacket();
                case EQUIPMENT -> packetManager.updateEquipmentPacket();
            }
        });
        metaData.location().getWorld().getPlayers().forEach(player -> {
            if (!showToPlayer(player)) {
                if (isDisplayedForPlayer.contains(player)) {
                    packetManager.sendRemovePlayerInfoPacket(player);
                    packetManager.sendDestroyPlayerPacket(player);
                    isDisplayedForPlayer.remove(player);
                }
                return;
            }
            double distanceSquared = player.getLocation().distanceSquared(metaData.location());
            if (distanceSquared < renderDistanceSquared) {
                if (!isDisplayedForPlayer.contains(player)) {
                    packetManager.sendAddPlayerInfoPacket(player);
                    packetManager.sendUpdateLatencyPacket(player);
                    packetManager.sendUpdateListedPacket(player);
                    packetManager.sendUpdateGameModePacket(player);
                    packetManager.sendUpdateDisplayNamePacket(player);

                    packetManager.sendSpawnPacket(player);
                    packetManager.sendMetaDataPacket(player);
                    packetManager.sendMovementPacket(player);
                    packetManager.sendEquipmentPacket(player);
                    isDisplayedForPlayer.add(player);
                } else {
                    changedMetaData.forEach(changed -> {
                        switch (changed) {
                            case LOCATION -> packetManager.sendMovementPacket(player);
                            case META_DATA -> packetManager.sendMetaDataPacket(player);
                            case PLAYER_INFO_LISTED -> packetManager.sendUpdateListedPacket(player);
                            case PLAYER_INFO_DISPLAY_NAME -> packetManager.sendUpdateDisplayNamePacket(player);
                            case PLAYER_INFO_GAME_MODE -> packetManager.sendUpdateGameModePacket(player);
                            case PLAYER_INFO_LATENCY -> packetManager.sendUpdateLatencyPacket(player);
                            case EQUIPMENT -> packetManager.sendEquipmentPacket(player);
                        }
                    });
                }
            } else if (distanceSquared > renderDistanceSquared + bufferRenderDistanceSquared) {
                if (!removeOnUnload) {
                    packetManager.sendAddPlayerInfoPacket(player);
                    changedMetaData.forEach(changed -> {
                        switch (changed) {
                            case PLAYER_INFO_LISTED -> packetManager.sendUpdateListedPacket(player);
                            case PLAYER_INFO_DISPLAY_NAME -> packetManager.sendUpdateDisplayNamePacket(player);
                            case PLAYER_INFO_GAME_MODE -> packetManager.sendUpdateGameModePacket(player);
                            case PLAYER_INFO_LATENCY -> packetManager.sendUpdateLatencyPacket(player);
                        }
                    });
                }
                if (isDisplayedForPlayer.contains(player)) {
                    if (removeOnUnload) {
                        packetManager.sendRemovePlayerInfoPacket(player);
                    }
                    packetManager.sendDestroyPlayerPacket(player);
                    isDisplayedForPlayer.remove(player);
                }
            }
        });
        if (!metaData.location().getWorld().getPlayers().isEmpty()) changedMetaData.clear();
    }
}
