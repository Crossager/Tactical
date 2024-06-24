package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalClientEntityController;
import net.crossager.tactical.api.npc.TacticalClientEntityInteractEvent;
import net.crossager.tactical.api.npc.TacticalClientObject;
import net.crossager.tactical.api.npc.TacticalMobAnimation;
import net.crossager.tactical.api.protocol.packet.PacketData;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.PacketWriter;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class SimpleTacticalClientObject<T extends TacticalClientObject<T>> implements TacticalClientObject<T> {
    protected double renderDistance = 64;
    protected double renderDistanceSquared = renderDistance * renderDistance;
    protected double bufferRenderDistance = 4;
    protected double bufferRenderDistanceSquared = bufferRenderDistance * bufferRenderDistance;
    protected Predicate<Player> showToPlayer = p -> true;
    protected Consumer<TacticalClientEntityInteractEvent<T>> onInteract = e -> {};
    protected boolean enabled = true;
    protected final List<TacticalClientEntityController<?>> controllers = new ArrayList<>();


    @Override
    public double renderDistance() {
        return renderDistance;
    }

    @Override
    public double bufferRenderDistance() {
        return bufferRenderDistance;
    }

    @Override
    public boolean showToPlayer(@NotNull Player player) {
        return showToPlayer.test(player);
    }

    @Override
    public @NotNull T renderDistance(double renderDistance) {
        this.renderDistance = renderDistance;
        this.renderDistanceSquared = renderDistance * renderDistance;
        return returnThis();
    }

    @Override
    public @NotNull T bufferRenderDistance(double bufferRenderDistance) {
        this.bufferRenderDistance = bufferRenderDistance;
        this.bufferRenderDistanceSquared = bufferRenderDistance * bufferRenderDistance;
        return returnThis();
    }

    @Override
    public @NotNull T showToPlayers(@NotNull Collection<Player> players) {
        this.showToPlayer = players::contains;
        return returnThis();
    }

    @Override
    public @NotNull T showToPlayers(@NotNull Predicate<Player> showToPlayer) {
        this.showToPlayer = showToPlayer;
        return returnThis();
    }

    @Override
    public @NotNull T onInteract(@NotNull Consumer<TacticalClientEntityInteractEvent<T>> onInteract) {
        this.onInteract = onInteract;
        return returnThis();
    }

    @Override
    public void playEntityStatus(int status) {
        PacketData data = PacketType.play().out().entityStatus().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeInt(entityId());
        writer.writeByte(status);
        playersToSendPackets().forEach(data::send);
    }

    @Override
    public void playEntityStatus(@NotNull EntityEffect status) {
        playEntityStatus(status.getData());
    }

    @Override
    public void playAnimation(@NotNull TacticalMobAnimation animation) {
        PacketData data = PacketType.play().out().animation().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(entityId());
        writer.writeByte(animation.id());
        playersToSendPackets().forEach(data::send);
    }

    @Override
    public void playHurtAnimation() {
        if (!MinecraftVersion.hasVersion(MinecraftVersion.v1_19_4)) {
            playEntityStatus(EntityEffect.HURT);
            return;
        }
        PacketData data = PacketType.play().out().hurtAnimation().createEmptyPacketData();
        PacketWriter writer = data.writer();
        writer.writeVarInt(entityId());
        writer.writeFloat(0); // we do not need to provide an angle since it's not a player
        playersToSendPackets().forEach(data::send);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled)
            enable();
        else
            disable();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public List<TacticalClientEntityController<?>> controllers() {
        return controllers;
    }

    protected void runControllers() {
        for (TacticalClientEntityController<?> controller : controllers) {
            runController(controller);
        }
    }

    private <C> void runController(TacticalClientEntityController<C> controller) {
        C data = controller.run(this);
        if (data == null) return;
        controller.applyData(data, this);
    }

    protected abstract T returnThis();

    protected abstract int entityId();

    protected abstract void enable();

    protected abstract void disable();

    protected abstract Collection<Player> playersToSendPackets();
}
