package net.crossager.tactical.api.npc;

import net.crossager.tactical.api.TacticalNPC;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an armor stand hologram
 */
public interface TacticalHologram extends TacticalClientEntity<ArmorStand> {

    /**
     * Retrieves the text displayed by the hologram.
     *
     * @return The text displayed by the hologram
     */
    String text();

    /**
     * Sets the text to be displayed by the hologram.
     *
     * @param text The text to be displayed
     * @return This instance
     */
    @NotNull
    TacticalHologram text(@NotNull String text);

    @NotNull
    static TacticalHologram of(@NotNull Location location, @NotNull String text) {
        return TacticalNPC.getInstance().getNPCFactory().createHologram(location, text);
    }

    @NotNull
    static TacticalHologram of(@NotNull Location location, @NotNull String text, long updateInterval) {
        return TacticalNPC.getInstance().getNPCFactory().createHologram(location, text, updateInterval);
    }
}
