package net.crossager.tactical.api.npc;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface TacticalNPCFactory {
    @NotNull
    TacticalHologram createHologram(@NotNull Location location, @NotNull String text);
    @NotNull
    TacticalHologram createHologram(@NotNull Location location, @NotNull String text, long updateInterval);
}
