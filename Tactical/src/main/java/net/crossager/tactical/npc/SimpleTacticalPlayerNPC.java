package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalMobAnimation;
import net.crossager.tactical.api.npc.TacticalPlayerNPC;
import net.crossager.tactical.api.npc.TacticalPlayerNPCMetaData;
import net.crossager.tactical.api.npc.TacticalPlayerSkin;
import net.crossager.tactical.api.protocol.packet.PacketData;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.PacketWriter;
import net.crossager.tactical.protocol.ProtocolUtils;
import net.crossager.tactical.util.PlayerSet;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;

public class SimpleTacticalPlayerNPC extends SimpleTacticalClientObject<TacticalPlayerNPC> implements TacticalPlayerNPC {
    private final Set<Player> isDisplayedForPlayer = new PlayerSet();
    private final SimpleTacticalPlayerNPCMetaData metaData;
    private final TacticalPlayerNPCPacketManager packetManager;
    private final EnumSet<SimpleTacticalPlayerNPCMetaData.ChangedMetaData> changedMetaData = EnumSet.noneOf(SimpleTacticalPlayerNPCMetaData.ChangedMetaData.class);
    private boolean removeOnUnload = false;

    public SimpleTacticalPlayerNPC(JavaPlugin plugin, String profileName, Location location, Consumer<TacticalPlayerNPCMetaData> applyPlayerData, TacticalPlayerSkin skin, long updateInterval) {
        metaData = new SimpleTacticalPlayerNPCMetaData(
                changedMetaData,
                location,
                ProtocolUtils.GENERATE_ENTITY_ID.get(),
                profileName,
                skin);
        applyPlayerData.accept(metaData);
        this.packetManager = new TacticalPlayerNPCPacketManager(metaData);

        plugin.getServer().getScheduler().runTaskTimer(plugin, this::onTick, 0, updateInterval);

        PacketType.play().in().useEntity().addPacketListener(new TacticalClientEntityListener<>(metaData.entityId(), this, event -> {
            Bukkit.getScheduler().runTask(plugin, () -> onInteract.accept(event));
        }));
    }

    @Override
    public void playEntityStatus(int status) {
        PacketData data = PacketType.play().out().entityStatus().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeInt(metaData.entityId());
        writer.writeByte(status);
        isDisplayedForPlayer.forEach(data::send);
    }

    @Override
    public void playEntityStatus(@NotNull EntityEffect status) {
        playEntityStatus(status.getData());
    }

    @Override
    protected TacticalPlayerNPC returnThis() {
        return this;
    }

    @Override
    public void playAnimation(@NotNull TacticalMobAnimation animation) {
        PacketData data = PacketType.play().out().animation().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(metaData.entityId());
        writer.writeByte(animation.id());
        isDisplayedForPlayer.forEach(data::send);
    }

    @Override
    public void playHurtAnimation() {
        if (!MinecraftVersion.hasVersion(MinecraftVersion.v1_19_4)) {
            playEntityStatus(EntityEffect.HURT);
            return;
        }
        PacketData data = PacketType.play().out().hurtAnimation().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(metaData.entityId());
        writer.writeFloat(0); // we do not need to provide an angle since it's not a player
        isDisplayedForPlayer.forEach(data::send);
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
