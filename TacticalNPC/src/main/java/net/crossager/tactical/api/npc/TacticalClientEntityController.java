package net.crossager.tactical.api.npc;

import net.crossager.tactical.api.TacticalNPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public interface TacticalClientEntityController<T> {
    /**
     * Runs the algorithm behind this controller and returns data used to modify the entity
     * @param clientObject the entity to modify
     * @return the data used to modify the entity
     */
    @Nullable
    T run(@NotNull TacticalClientObject<?> clientObject);

    /**
     * Modifies the entity using the given data
     * @param data the data
     * @param clientObject to entity to modify
     */
    void applyData(@NotNull T data, @NotNull TacticalClientObject<?> clientObject);

    @NotNull
    static TacticalClientEntityController<Location> lookAtClosestPlayer(int maxDistance) {
        return lookAtClosest(e -> e instanceof Player, maxDistance);
    }

    @NotNull
    static TacticalClientEntityController<Location> lookAtClosest(@NotNull Predicate<Entity> entityFilter, int maxDistance) {
        return TacticalNPC.getInstance().getNPCFactory().lookAtClosest(entityFilter, maxDistance);
    }
}
