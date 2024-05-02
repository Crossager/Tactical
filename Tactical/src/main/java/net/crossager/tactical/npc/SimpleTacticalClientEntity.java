package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalClientEntity;
import net.crossager.tactical.api.protocol.packet.PacketData;
import net.crossager.tactical.api.protocol.packet.PacketListener;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.PacketWriter;
import net.crossager.tactical.api.reflect.FieldAccessor;
import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.api.util.TacticalUtils;
import net.crossager.tactical.util.CachedEnumValues;
import net.crossager.tactical.util.PlayerSet;
import net.crossager.tactical.util.reflect.CraftBukkitReflection;
import net.crossager.tactical.util.reflect.DynamicReflection;
import net.crossager.tactical.util.reflect.InternalRegistry;
import net.crossager.tactical.util.reflect.MinecraftClasses;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class SimpleTacticalClientEntity<E extends Entity> extends SimpleTacticalClientObject<TacticalClientEntity<E>> implements TacticalClientEntity<E> {
    public static final byte HAS_MORE_ITEMS_MASK = (byte) (1 << 7);
    public static final float ANGLE_TO_BYTE = 256F / 360F;

    public static final MethodInvoker<?> CREATE_ENTITY = DynamicReflection.getMethodByReturnTypeAndArgs(CraftBukkitReflection.getCraftRegionAccessorClass(), MinecraftClasses.getEntityClass(), Location.class, Class.class, Boolean.TYPE);
    public static final MethodInvoker<?> GET_BUKKIT_ENTITY = DynamicReflection.getMethodByReturnTypeAndArgs(MinecraftClasses.getEntityClass(), CraftBukkitReflection.getCraftEntityClass());
    public static final MethodInvoker<List<?>> GENERATE_DATAWATCHER_LIST = DynamicReflection.getMethodByReturnTypeAndArgs(MinecraftClasses.getDataWatcherClass(), TacticalUtils.castClassGenerics(List.class), 1);
    public static final MethodInvoker<List<?>> DATAWATCHER_SERIALIZE = DynamicReflection.getMethodByArgs(MinecraftClasses.getDataWatcherBClass(), MinecraftClasses.getPacketDataSerializerClass());
    public static final FieldAccessor<?> ENTITY_DATAWATCHER = DynamicReflection.getField(MinecraftClasses.getEntityClass(), MinecraftClasses.getDataWatcherClass());

    private final E entity;
    private final Object dataWatcher;
    private final JavaPlugin plugin;
    private final long updateInterval;
    private Location lastLocation;
    protected final Set<Player> isDisplayedForPlayer = new PlayerSet();

    // cache packets for performance
    private final PacketData destroyPacket;
    private PacketData spawnPacket;
    private PacketData metaDataPacket;
    private PacketData equipmentPacket;

    private BukkitTask bukkitTask;
    private final PacketListener packetListener;

    @SuppressWarnings("unchecked")
    public SimpleTacticalClientEntity(JavaPlugin plugin, Location location, Class<E> entityClass, Consumer<E> applyEntityData, long updateInterval) {
        this.plugin = plugin;
        this.updateInterval = updateInterval;
        Object nmsEntity = CREATE_ENTITY.invoke(location.getWorld(), location, entityClass, false);
        this.entity = (E) GET_BUKKIT_ENTITY.invoke(nmsEntity);
        this.dataWatcher = ENTITY_DATAWATCHER.read(nmsEntity);
        this.lastLocation = location.clone();
        applyEntityData.accept(entity);
        spawnPacket = generateSpawnEntityPacket();
        destroyPacket = generateDestroyPacket();
        metaDataPacket = generateMetaDataPacket();
        equipmentPacket = generateEquipmentPacket();

        packetListener = new TacticalClientEntityListener<>(entity().getEntityId(), this, event -> {
            Bukkit.getScheduler().runTask(plugin, () -> onInteract.accept(event));
        });

        enable();
    }

    @Override
    public @NotNull E entity() {
        return entity;
    }

    @Override
    public void updateMetaData() {
        spawnPacket = generateSpawnEntityPacket();
        metaDataPacket = generateMetaDataPacket();
        equipmentPacket = generateEquipmentPacket();
        isDisplayedForPlayer.forEach(this::sendMeta);
    }

    @Override
    public @NotNull TacticalClientEntity<E> location(Location location) {
        entity.teleport(location);
        return this;
    }

    @Override
    protected TacticalClientEntity<E> returnThis() {
        return this;
    }

    @Override
    protected int entityId() {
        return entity.getEntityId();
    }

    @Override
    protected void enable() {
        bukkitTask = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            List<PacketData> movementPackets = !lastLocation.equals(entity.getLocation()) ? generateMovementPackets() : List.of();
            if (!lastLocation.equals(entity.getLocation())) lastLocation = entity.getLocation().clone();
            entity.getLocation().getWorld().getPlayers().forEach(player -> {
                if (!showToPlayer(player)) {
                    if (isDisplayedForPlayer.contains(player)) {
                        destroyPacket.send(player);
                        isDisplayedForPlayer.remove(player);
                    }
                    return;
                }
                double distanceSquared = player.getLocation().distanceSquared(entity.getLocation());
                if (distanceSquared < renderDistanceSquared) {
                    if (!isDisplayedForPlayer.contains(player)) {
                        spawnPacket.send(player);
                        isDisplayedForPlayer.add(player);
                    }
                    movementPackets.forEach(data -> data.send(player));
                    sendMeta(player);
                } else if (distanceSquared > renderDistanceSquared + bufferRenderDistanceSquared) {
                    if (isDisplayedForPlayer.contains(player)) {
                        destroyPacket.send(player);
                        isDisplayedForPlayer.remove(player);
                    }
                }
            });
        }, 0, updateInterval);

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

    private void sendMeta(Player player) {
        metaDataPacket.send(player);
        if (equipmentPacket != null)
            equipmentPacket.send(player);
    }

    private PacketData generateSpawnEntityPacket() {
        PacketData data = PacketType.play().out().spawnEntity().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(entity.getEntityId());
        writer.writeUUID(entity.getUniqueId());
        writer.writeVarInt(InternalRegistry.ENTITY_TYPES.getIdByKey(entity.getType().getKey()));
        writer.writeDouble(entity.getLocation().getX());
        writer.writeDouble(entity.getLocation().getY());
        writer.writeDouble(entity.getLocation().getZ());
        writer.writeByte((int) (entity.getLocation().getPitch() * ANGLE_TO_BYTE));
        int yaw = (int) (entity.getLocation().getYaw() * ANGLE_TO_BYTE);
        writer.writeByte(yaw);
        writer.writeByte(yaw);
        writer.writeVarInt(0); // other data, might implement access
        writer.writeShort(0); // velocity x, y, z
        writer.writeShort(0);
        writer.writeShort(0);
        return data;
    }

    private PacketData generateDestroyPacket() {
        PacketData data = PacketType.play().out().entityDestroy().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(1); // only 1 id
        writer.writeVarInt(entity.getEntityId());
        return data;
    }

    private PacketData generateMetaDataPacket() {
        PacketData data = PacketType.play().out().entityMetadata().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(entity.getEntityId());
        List<?> dataWatcherList = GENERATE_DATAWATCHER_LIST.invoke(dataWatcher);

        for (Object dataWatcherB : dataWatcherList) {
            DATAWATCHER_SERIALIZE.invoke(dataWatcherB, writer.getPacketDataSerializer());
        }

        writer.writeByte(255);
        return data;
    }

    private PacketData generateEquipmentPacket() {
        if (!(entity instanceof LivingEntity livingEntity)) return null;
        if (livingEntity.getEquipment() == null) return null;
        PacketData data = PacketType.play().out().entityEquipment().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(entity.getEntityId());
        for (int i = 0; i < CachedEnumValues.EQUIPMENT_SLOT.length; i++) {
            ItemStack item = livingEntity.getEquipment().getItem(CachedEnumValues.EQUIPMENT_SLOT[i]);
            boolean isLast = i == CachedEnumValues.EQUIPMENT_SLOT.length - 1;
            writer.writeByte(isLast ? i : i | HAS_MORE_ITEMS_MASK);
            writer.writeItemStack(item);
        }
        return data;
    }

    private List<PacketData> generateMovementPackets() {
        PacketData yawPacket = PacketType.play().out().entityHeadRotation().createEmptyPacketData();
        yawPacket.writer().writeVarInt(entity.getEntityId());
        yawPacket.writer().writeByte((int) (entity.getLocation().getYaw() * ANGLE_TO_BYTE));
        if (entity.getLocation().distanceSquared(lastLocation) < 63) { // max move is 8 blocks, removed 1 as a buffer
            Location relLoc = entity.getLocation().subtract(lastLocation);
            PacketData movePacket = PacketType.play().out().relEntityMoveLook().createEmptyPacketData();
            PacketWriter writer = movePacket.writer();
            writer.writeVarInt(entity.getEntityId());
            writer.writeShort((int) (relLoc.getX() * 32 * 128));
            writer.writeShort((int) (relLoc.getY() * 32 * 128));
            writer.writeShort((int) (relLoc.getZ() * 32 * 128));
            writer.writeByte((int) (entity.getLocation().getYaw() * ANGLE_TO_BYTE));
            writer.writeByte((int) (relLoc.getPitch() * ANGLE_TO_BYTE));
            writer.writeBoolean(true); // is on ground
            return List.of(movePacket, yawPacket);
        } else { // teleport otherwise
            PacketData teleportPacket = PacketType.play().out().entityTeleport().createEmptyPacketData();
            PacketWriter writer = teleportPacket.writer();
            writer.writeVarInt(entity.getEntityId());
            writer.writeDouble(entity.getLocation().getX());
            writer.writeDouble(entity.getLocation().getY());
            writer.writeDouble(entity.getLocation().getZ());
            writer.writeByte((int) (entity.getLocation().getYaw() * ANGLE_TO_BYTE));
            writer.writeByte((int) (entity.getLocation().getPitch() * ANGLE_TO_BYTE));
            writer.writeBoolean(true); // is on ground
            return List.of(teleportPacket, yawPacket);
        }
    }
}
