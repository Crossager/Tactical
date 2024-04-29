package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalClientEntityInteractEvent;
import net.crossager.tactical.api.npc.TacticalClientObject;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class SimpleTacticalClientObject<T extends TacticalClientObject<T>> implements TacticalClientObject<T> {
    protected double renderDistance = 64;
    protected double renderDistanceSquared = renderDistance * renderDistance;
    protected double bufferRenderDistance = 4;
    protected double bufferRenderDistanceSquared = bufferRenderDistance * bufferRenderDistance;
    protected Predicate<Player> showToPlayer = p -> true;
    protected Consumer<TacticalClientEntityInteractEvent<T>> onInteract = e -> {};
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

    protected abstract T returnThis();
}
