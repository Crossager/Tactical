package net.crossager.tactical.api.gui;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a base GUI that can be opened by a player.
 */
public interface TacticalBaseGUI {

    /**
     * Opens the GUI for the specified player.
     *
     * @param player the player to open the GUI for
     */
    void open(@NotNull Player player);
}