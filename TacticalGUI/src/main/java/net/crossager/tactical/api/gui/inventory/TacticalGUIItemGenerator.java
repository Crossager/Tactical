package net.crossager.tactical.api.gui.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * An interface that provides a way to generate an {@link ItemStack} for a GUI based on the given {@link Player}.
 */
public interface TacticalGUIItemGenerator {
    /**
     * Generates an {@link ItemStack} based on the given {@link Player}.
     * @param player the player to generate the item stack for
     * @return the generated item stack
     */
    @NotNull
    ItemStack generateItem(@NotNull Player player);
}
