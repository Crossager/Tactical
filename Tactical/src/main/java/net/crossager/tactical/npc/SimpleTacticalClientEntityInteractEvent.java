package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalClientEntityInteractEvent;
import net.crossager.tactical.api.npc.TacticalClientEntityInteractType;
import net.crossager.tactical.api.npc.TacticalClientObject;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

public record SimpleTacticalClientEntityInteractEvent<T extends TacticalClientObject<T>>(
        T clientEntity,
        Player player,
        Vector target,
        int entityId,
        EquipmentSlot hand,
        TacticalClientEntityInteractType type,
        boolean isSneaking
) implements TacticalClientEntityInteractEvent<T> {
    @Override
    public @NotNull Vector target() {
        if (target == null) throw new NoSuchElementException("No target. Target is only available when type == INTERACT_AT");
        return target;
    }
}
