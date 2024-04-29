package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalPlayerSkinParts;
import net.crossager.tactical.api.protocol.packet.PacketData;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.PacketWriter;
import net.crossager.tactical.util.CachedEnumValues;
import net.crossager.tactical.util.reflect.InternalRegistry;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;

import static net.crossager.tactical.npc.SimpleTacticalClientEntity.HAS_MORE_ITEMS_MASK;

public class TacticalPlayerNPCPacketManager {
    private final SimpleTacticalPlayerNPCMetaData metaData;
    private PacketData spawnPacket;
    private PacketData addPlayerInfoPacket;
    private PacketData removePlayerInfoPacket;
    private PacketData destroyPlayerPacket;
    private PacketData metaDataPacket;
    private PacketData updateListedPacket;
    private PacketData updateLatencyPacket;
    private PacketData updateDisplayNamePacket;
    private PacketData updateGameModePacket;
    private PacketData equipmentPacket;
    private List<PacketData> movementPackets;

    private Location lastLocation;

    public TacticalPlayerNPCPacketManager(SimpleTacticalPlayerNPCMetaData metaData) {
        this.metaData = metaData;
        lastLocation = metaData.location().clone();
    }

    public void sendSpawnPacket(Player player) {
        if (spawnPacket == null) {
            if (MinecraftVersion.hasVersion(MinecraftVersion.v1_20_2))
                spawnPacket = generateSpawnEntityPacket();
            else
                spawnPacket = generateSpawnPacket();
        }
        spawnPacket.send(player);
    }

    public void sendAddPlayerInfoPacket(Player player) {
        if (addPlayerInfoPacket == null) addPlayerInfoPacket = generateAddPlayerInfoPacket();
        addPlayerInfoPacket.send(player);
    }

    public void sendMetaDataPacket(Player player) {
        if (metaDataPacket == null) metaDataPacket = generateMetaDataPacket();
        metaDataPacket.send(player);
    }

    public void sendMovementPacket(Player player) {
        if (lastLocation.equals(metaData.location()) && !metaData.useCustomHeadRotation()) return;
        if (movementPackets == null) movementPackets = generateMovementPackets();
        movementPackets.forEach(data -> data.send(player));
    }

    public void sendUpdateListedPacket(Player player) {
        if (updateListedPacket == null) updateListedPacket = generateUpdateListedPacket();
        updateListedPacket.send(player);
    }

    public void sendUpdateLatencyPacket(Player player) {
        if (updateLatencyPacket == null) updateLatencyPacket = generateUpdateLatencyPacket();
        updateLatencyPacket.send(player);
    }

    public void sendUpdateDisplayNamePacket(Player player) {
        if (updateDisplayNamePacket == null) updateDisplayNamePacket = generateUpdateDisplayNamePacket();
        updateDisplayNamePacket.send(player);
    }

    public void sendEquipmentPacket(Player player) {
        if (equipmentPacket == null) equipmentPacket = generateEquipmentPacket();
        equipmentPacket.send(player);
    }

    public void sendDestroyPlayerPacket(Player player) {
        if (destroyPlayerPacket == null) destroyPlayerPacket = generateDestroyPlayerPacket();
        destroyPlayerPacket.send(player);
    }

    public void sendRemovePlayerInfoPacket(Player player) {
        if (removePlayerInfoPacket == null) removePlayerInfoPacket = generateRemovePlayerInfoPacket();
        removePlayerInfoPacket.send(player);
    }

    public void sendUpdateGameModePacket(Player player) {
        if (updateGameModePacket == null) updateGameModePacket = generateUpdateGameModePacket();
        updateGameModePacket.send(player);
    }

    public void updateMetaDataPacket() {
        metaDataPacket = null;
    }

    public void updateMovementPacket() {
        movementPackets = null;
        spawnPacket = null;
    }

    public void updateUpdateListedPacket() {
        updateListedPacket = null;
    }

    public void updateUpdateLatencyPacket() {
        updateLatencyPacket = null;
    }

    public void updateUpdateDisplayNamePacket() {
        updateDisplayNamePacket = null;
    }

    public void updateUpdateGameModePacket() {
         updateGameModePacket = null;
    }

    public void updateEquipmentPacket() {
        equipmentPacket = null;
    }

    private PacketData generateSpawnPacket() {
        PacketData data = PacketType.play().out().namedEntitySpawn().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(metaData.entityId());
        writer.writeUUID(metaData.profileId());
        writer.writeLocation(metaData.location());
        writer.writeByte((int) (metaData.location().getYaw() * SimpleTacticalClientEntity.ANGLE_TO_BYTE));
        writer.writeByte((int) (metaData.location().getPitch() * SimpleTacticalClientEntity.ANGLE_TO_BYTE));
        return data;
    }

    private PacketData generateSpawnEntityPacket() {
        PacketData data = PacketType.play().out().spawnEntity().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(metaData.entityId());
        writer.writeUUID(metaData.profileId());
        writer.writeVarInt(InternalRegistry.ENTITY_TYPES.getIdByKey(EntityType.PLAYER.getKey()));
        writer.writeDouble(metaData.location().getX());
        writer.writeDouble(metaData.location().getY());
        writer.writeDouble(metaData.location().getZ());
        writer.writeByte((int) (metaData.location().getPitch() * SimpleTacticalClientEntity.ANGLE_TO_BYTE));
        int yaw = (int) (metaData.location().getYaw() * SimpleTacticalClientEntity.ANGLE_TO_BYTE);
        writer.writeByte(yaw);
        writer.writeByte(yaw);
        writer.writeVarInt(0); // other data, might implement access
        writer.writeShort(0); // velocity x, y, z
        writer.writeShort(0);
        writer.writeShort(0);
        return data;
    }

    private PacketData generateAddPlayerInfoPacket() {
        return generatePlayerInfoPacket(UpdatePlayerInfo.ADD_PLAYER, writer -> {
            writer.writeString(metaData.profileName());

            writer.writeVarInt(1); // profile properties
            writer.writeString("textures");
            writer.writeString(metaData.skin().texture());
            if (metaData.skin().signature().isEmpty()) {
                writer.writeBoolean(false);
            } else {
                writer.writeBoolean(true);
                writer.writeString(metaData.skin().signature());
            }
        });
    }

    private PacketData generateUpdateLatencyPacket() {
        return generatePlayerInfoPacket(UpdatePlayerInfo.UPDATE_LATENCY, writer -> {
            writer.writeVarInt(metaData.ping());
        });

    }

    private PacketData generateUpdateListedPacket() {
        return generatePlayerInfoPacket(UpdatePlayerInfo.UPDATE_LISTED, writer -> {
            writer.writeBoolean(metaData.showInTab());
        });
    }

    private PacketData generateUpdateGameModePacket() {
        return generatePlayerInfoPacket(UpdatePlayerInfo.UPDATE_GAME_MODE, writer -> {
            writer.writeVarInt(metaData.gameMode().getValue());
        });
    }

    private PacketData generateUpdateDisplayNamePacket() {
        return generatePlayerInfoPacket(UpdatePlayerInfo.UPDATE_DISPLAY_NAME, writer -> {
            writer.writeBoolean(metaData.useCustomTabListName());
            if (metaData.useCustomTabListName())
                writer.writeJsonTextComponent(TextComponent.fromLegacyText(metaData.customTabListName()));
        });
    }

    private PacketData generatePlayerInfoPacket(UpdatePlayerInfo updatePlayerInfo, Consumer<PacketWriter> applyData) {
        PacketData data = PacketType.play().out().playerInfoUpdate().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeEnumSet(UpdatePlayerInfo.class, EnumSet.of(updatePlayerInfo));

        writer.writeVarInt(1); // 1 action
        writer.writeUUID(metaData.profileId());
        applyData.accept(writer);
        return data;
    }

    private PacketData generateRemovePlayerInfoPacket() {
        PacketData data = PacketType.play().out().playerInfoRemove().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(1); // 1 player
        writer.writeUUID(metaData.profileId());
        return data;
    }

    private PacketData generateDestroyPlayerPacket() {
        PacketData data = PacketType.play().out().entityDestroy().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(1); // 1 entity
        writer.writeVarInt(metaData.entityId());
        return data;
    }

    private PacketData generateMetaDataPacket() {
        PacketData data = PacketType.play().out().entityMetadata().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(metaData.entityId());

        // Entity metadata
        writer.writeEntityDataEntry(0, 0);
        writer.writeByte(booleansToByte(
                metaData.onFire(),
                false, // swimming
                false, // Unused (previously riding)
                metaData.sprinting(),
                false, // crouching
                metaData.invisible(),
                metaData.glowingEffect(),
                metaData.flyingWithElytra()
        ));

        if (MinecraftVersion.hasVersion(MinecraftVersion.v1_19_4))
            writer.writeEntityDataEntry(6, 20);
        else
            writer.writeEntityDataEntry(6, 19);
        writer.writeEnum(metaData.pose());

        writer.writeEntityDataEntry(7, 1);
        writer.writeVarInt(metaData.isFrozen() ? 140 : 0);

        writer.writeEntityDataEntry(12, 1);
        writer.writeVarInt(metaData.arrowsInBody());

        writer.writeEntityDataEntry(13, 1);
        writer.writeVarInt(metaData.stingersInBody());

        writer.writeEntityDataEntry(17, 0);
        writer.writeEnumSet(TacticalPlayerSkinParts.class, metaData.displaySkinParts());

        writer.writeEntityDataEntry(18, 0);
        writer.writeByte(metaData.leftHanded() ? 0 : 1);

        writer.writeByte(0xFF);

        return data;
    }

    public List<PacketData> generateMovementPackets() {
        Location from = lastLocation;
        lastLocation = metaData.location().clone();
        PacketData yawPacket = PacketType.play().out().entityHeadRotation().createEmptyPacketData();
        yawPacket.writer().writeVarInt(metaData.entityId());
        float headYaw = metaData.useCustomHeadRotation() ? metaData.customHeadYaw() : metaData.location().getYaw();
        yawPacket.writer().writeByte((int) (headYaw * SimpleTacticalClientEntity.ANGLE_TO_BYTE));
        if (metaData.location().distanceSquared(from) < 63) { // max move is 8 blocks, removed 1 as a buffer
            Location relLoc = metaData.location().subtract(from);
            PacketData movePacket = PacketType.play().out().relEntityMoveLook().createEmptyPacketData();
            PacketWriter writer = movePacket.writer();
            writer.writeVarInt(metaData.entityId());
            writer.writeShort((int) (relLoc.getX() * 32 * 128));
            writer.writeShort((int) (relLoc.getY() * 32 * 128));
            writer.writeShort((int) (relLoc.getZ() * 32 * 128));
            writer.writeByte((int) (metaData.location().getYaw() * SimpleTacticalClientEntity.ANGLE_TO_BYTE));
            writer.writeByte((int) (relLoc.getPitch() * SimpleTacticalClientEntity.ANGLE_TO_BYTE));
            writer.writeBoolean(metaData.pose() == Pose.STANDING); // is on ground
            return List.of(movePacket, yawPacket);
        } else { // teleport otherwise
            PacketData teleportPacket = PacketType.play().out().entityTeleport().createEmptyPacketData();
            PacketWriter writer = teleportPacket.writer();
            writer.writeVarInt(metaData.entityId());
            writer.writeLocation(metaData.location());
            writer.writeByte((int) (metaData.location().getYaw() * SimpleTacticalClientEntity.ANGLE_TO_BYTE));
            writer.writeByte((int) (metaData.location().getPitch() * SimpleTacticalClientEntity.ANGLE_TO_BYTE));
            writer.writeBoolean(metaData.pose() == Pose.STANDING); // is on ground
            return List.of(teleportPacket, yawPacket);
        }
    }

    private PacketData generateEquipmentPacket() {
        PacketData data = PacketType.play().out().entityEquipment().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(metaData.entityId());
        for (int i = 0; i < CachedEnumValues.EQUIPMENT_SLOT.length; i++) {
            ItemStack item = metaData.equipment().getEquipment(CachedEnumValues.EQUIPMENT_SLOT[i]);
            boolean isLast = i == CachedEnumValues.EQUIPMENT_SLOT.length - 1;
            writer.writeByte(isLast ? i : i | HAS_MORE_ITEMS_MASK);
            writer.writeItemStack(item);
        }
        return data;
    }

    private static byte booleansToByte(boolean... booleans) {
        if (booleans.length > 8) throw new IndexOutOfBoundsException("Boolean array too long");
        byte byteValue = 0;
        for (int i = 0; i < booleans.length; i++) {
            if (booleans[i])
                byteValue |= 1 << i;
        }
        return byteValue;
    }

    public enum UpdatePlayerInfo {
        ADD_PLAYER,
        INITIALIZE_CHAT,
        UPDATE_GAME_MODE,
        UPDATE_LISTED,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME
    }
}
