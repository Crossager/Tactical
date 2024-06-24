package net.crossager.tactical.npc.controllers;

import net.crossager.tactical.api.npc.*;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public record LookAtClosest(Predicate<Entity> entityFilter, int maxDistance) implements TacticalClientEntityController<Location> {
    @Nullable
    @Override
    public Location run(@NotNull TacticalClientObject<?> clientObject) {
        Location location = clientObject instanceof TacticalPlayerNPC npc ? npc.metaData().location() :
                (clientObject instanceof TacticalClientEntity<?> entity) ? entity.entity().getLocation() : null;
        assert location != null;
        Optional<Entity> lookAt = location.getWorld()
                .getNearbyEntities(location, maxDistance, maxDistance, maxDistance)
                .stream()
                .filter(entity -> entity instanceof LivingEntity)
                .filter(entityFilter)
                .min(Comparator.comparingDouble(e -> e.getLocation().distanceSquared(location)));
        if (lookAt.isEmpty()) return null;
        LivingEntity livingEntity = (LivingEntity) lookAt.get();

        Vector direction = location.clone().add(0, 1.7, 0).toVector().subtract(livingEntity.getEyeLocation().toVector()).multiply(-1);

        return location.setDirection(direction);
    }

    @Override
    public void applyData(@NotNull Location data, @NotNull TacticalClientObject<?> clientObject) {
        if (clientObject instanceof TacticalPlayerNPC npc)
            npc.metaData().location(data);
        else if (clientObject instanceof TacticalClientEntity<?> entity) {
            entity.location(data);
            entity.updateMetaData();
        }
    }
}
