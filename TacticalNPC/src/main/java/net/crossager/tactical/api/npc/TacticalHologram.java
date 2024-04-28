package net.crossager.tactical.api.npc;

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
}
