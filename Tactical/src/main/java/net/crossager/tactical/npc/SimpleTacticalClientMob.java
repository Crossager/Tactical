package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalClientMob;
import net.crossager.tactical.api.npc.TacticalMobAnimation;
import net.crossager.tactical.api.protocol.packet.PacketData;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.PacketWriter;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SimpleTacticalClientMob<E extends Mob> extends SimpleTacticalClientEntity<E> implements TacticalClientMob<E> {
    public SimpleTacticalClientMob(JavaPlugin plugin, Location location, Class<E> entityClass, Consumer<E> applyEntityData, long updateInterval) {
        super(plugin, location, entityClass, applyEntityData, updateInterval);
    }

    @Override
    public void playAnimation(@NotNull TacticalMobAnimation animation) {
        PacketData data = PacketType.play().out().animation().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(entity().getEntityId());
        writer.writeByte(animation.id());
        isDisplayedForPlayer.forEach(data::send);
    }

    @Override
    public void playHurtAnimation() {
        if (!MinecraftVersion.hasVersion(MinecraftVersion.v1_19_4)) {
            playEntityStatus(EntityEffect.HURT);
            return;
        }
        PacketData data = PacketType.play().out().animation().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(entity().getEntityId());
        writer.writeFloat(0); // we do not need to provide an angle since it's not a player
        isDisplayedForPlayer.forEach(data::send);
    }
}
