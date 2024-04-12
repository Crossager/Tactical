package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalClientEntity;
import net.crossager.tactical.api.npc.TacticalClientEntityInteractEvent;
import net.crossager.tactical.api.npc.TacticalClientEntityInteractType;
import net.crossager.tactical.api.protocol.packet.PacketData;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.PacketWriter;
import net.crossager.tactical.api.reflect.FieldAccessor;
import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.api.util.TacticalUtils;
import net.crossager.tactical.util.PlayerSet;
import net.crossager.tactical.util.reflect.CraftBukkitReflection;
import net.crossager.tactical.util.reflect.DynamicReflection;
import net.crossager.tactical.util.reflect.InternalRegistry;
import net.crossager.tactical.util.reflect.MinecraftClasses;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SimpleTacticalClientEntity<E extends Entity> implements TacticalClientEntity<E> {
    private static final byte HAS_MORE_ITEMS_MASK = (byte) (1 << 7);
    private static final float ANGLE_TO_BYTE = 256F / 360F;

    public static final MethodInvoker<?> CREATE_ENTITY = DynamicReflection.getMethodByReturnTypeAndArgs(CraftBukkitReflection.getCraftRegionAccessorClass(), MinecraftClasses.getEntityClass(), Location.class, Class.class, Boolean.TYPE);
    public static final MethodInvoker<?> GET_BUKKIT_ENTITY = DynamicReflection.getMethodByReturnTypeAndArgs(MinecraftClasses.getEntityClass(), CraftBukkitReflection.getCraftEntityClass());
    public static final MethodInvoker<List<?>> GENERATE_DATAWATCHER_LIST = DynamicReflection.getMethodByReturnTypeAndArgs(MinecraftClasses.getDataWatcherClass(), TacticalUtils.castClassGenerics(List.class), 1);
    public static final MethodInvoker<List<?>> DATAWATCHER_SERIALIZE = DynamicReflection.getMethodByArgs(MinecraftClasses.getDataWatcherBClass(), MinecraftClasses.getPacketDataSerializerClass());
    public static final FieldAccessor<?> ENTITY_DATAWATCHER = DynamicReflection.getField(MinecraftClasses.getEntityClass(), MinecraftClasses.getDataWatcherClass());

    private static final EquipmentSlot[] CACHED_SLOTS = EquipmentSlot.values();

    private final E entity;
    private final Object nmsEntity;
    private final Object dataWatcher;
    private Location lastLocation;
    private double renderDistance = 64;
    private double renderDistanceSquared = renderDistance * renderDistance;
    private Predicate<Player> showToPlayer = p -> true;
    private Consumer<TacticalClientEntityInteractEvent<E>> onInteract = e -> {};
    private final Set<Player> isDisplayedForPlayer = new PlayerSet();

    // cache packets for performance
    private final PacketData spawnPacket;
    private final PacketData destroyPacket;
    private PacketData metaDataPacket;
    private PacketData equipmentPacket;

    @SuppressWarnings("unchecked")
    public SimpleTacticalClientEntity(JavaPlugin plugin, Location location, Class<E> entityClass, Consumer<E> applyEntityData, long updateInterval) {
        this.nmsEntity = CREATE_ENTITY.invoke(location.getWorld(), location, entityClass, false);
        this.entity = (E) GET_BUKKIT_ENTITY.invoke(nmsEntity);
        this.dataWatcher = ENTITY_DATAWATCHER.read(nmsEntity);
        this.lastLocation = location.clone();
        applyEntityData.accept(entity);
        spawnPacket = generateSpawnEntityPacket();
        destroyPacket = generateDestroyPacket();
        metaDataPacket = generateMetaDataPacket();
        equipmentPacket = generateEquipmentPacket();

        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
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
                if (player.getLocation().distanceSquared(entity.getLocation()) < renderDistanceSquared + 1) { // use squared location for performance
                    if (!isDisplayedForPlayer.contains(player)) {
                        spawnPacket.send(player);
                        isDisplayedForPlayer.add(player);
                    }
                    movementPackets.forEach(data -> data.send(player));
                    sendMeta(player);
                } else {
                    if (isDisplayedForPlayer.contains(player)) {
                        destroyPacket.send(player);
                        isDisplayedForPlayer.remove(player);
                    }
                }
            });
        }, 0, updateInterval);

        PacketType.play().in().useEntity().addPacketListener(event -> {
            event.data().reader().readSilent(reader -> {
                int entityId = reader.readVarInt();
                if (entityId != entity.getEntityId()) return;
                TacticalClientEntityInteractType action = TacticalClientEntityInteractType.fromId(reader.readVarInt());
                Vector target = null;
                EquipmentSlot hand = EquipmentSlot.HAND;

                if (action == TacticalClientEntityInteractType.INTERACT_AT) {
                    float x = reader.readFloat();
                    float y = reader.readFloat();
                    float z = reader.readFloat();
                    target = new Vector(x, y, z);
                }

                if (action == TacticalClientEntityInteractType.INTERACT || action == TacticalClientEntityInteractType.INTERACT_AT) {
                    hand = reader.readVarInt() == 0 ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
                }

                boolean isSneaking = reader.readBoolean();
                onInteract.accept(new SimpleTacticalClientEntityInteractEvent<>(
                        this,
                        event.player(),
                        target,
                        entityId,
                        hand,
                        action,
                        isSneaking
                ));
            });
        });
    }

    @Override
    public double renderDistance() {
        return renderDistance;
    }

    @Override
    public @NotNull E entity() {
        return entity;
    }

    @Override
    public void updateMetaData() {
        metaDataPacket = generateMetaDataPacket();
        equipmentPacket = generateEquipmentPacket();
        isDisplayedForPlayer.forEach(this::sendMeta);
    }

    @Override
    public void playEntityStatus(int status) {
        PacketData data = PacketType.play().out().entityStatus().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeInt(entity.getEntityId());
        writer.writeByte(status);
        isDisplayedForPlayer.forEach(data::send);
    }

    @Override
    public void playEntityStatus(@NotNull EntityEffect status) {
        playEntityStatus(status.getData());
    }

    @Override
    public boolean showToPlayer(@NotNull Player player) {
        return showToPlayer.test(player);
    }

    @Override
    public @NotNull TacticalClientEntity<E> renderDistance(double renderDistance) {
        this.renderDistance = renderDistance;
        this.renderDistanceSquared = renderDistance * renderDistance;
        return this;
    }

    @Override
    public @NotNull TacticalClientEntity<E> location(Location location) {
        entity.teleport(location);
        return this;
    }

    @Override
    public @NotNull TacticalClientEntity<E> showToPlayers(@NotNull Collection<Player> players) {
        showToPlayer = players::contains;
        return this;
    }

    @Override
    public @NotNull TacticalClientEntity<E> showToPlayers(@NotNull Predicate<Player> showToPlayer) {
        this.showToPlayer = showToPlayer;
        return this;
    }

    @Override
    public @NotNull TacticalClientEntity<E> onInteract(@NotNull Consumer<TacticalClientEntityInteractEvent<E>> onInteract) {
        this.onInteract = onInteract;
        return this;
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
        writer.writeByte(0); // yaw does not matter
        writer.writeByte((int) (entity.getLocation().getYaw() * ANGLE_TO_BYTE));
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
        for (int i = 0; i < CACHED_SLOTS.length; i++) {
            ItemStack item = livingEntity.getEquipment().getItem(CACHED_SLOTS[i]);
            boolean isLast = i == CACHED_SLOTS.length - 1;
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
            writer.writeByte(0); // yaw is set by other packet
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
            writer.writeByte(0); // yaw is set by other packet
            writer.writeByte((int) (entity.getLocation().getPitch() * ANGLE_TO_BYTE));
            writer.writeBoolean(true); // is on ground
            return List.of(teleportPacket, yawPacket);
        }
    }
}
