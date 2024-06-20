package net.crossager.tactical.api.gui.inventory.components;

import net.crossager.tactical.api.TacticalGUI;
import net.crossager.tactical.api.gui.inventory.TacticalComponentPlayerDataListener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * Represents a component of a storage GUI that can hold items.
 */
public interface TacticalStorageGUIComponent extends TacticalGUIComponent, TacticalComponentPlayerDataListener<ItemStack[][]> {

    /**
     * Sets a listener to be notified when items in this storage component are updated.
     *
     * @param listener The listener to be notified
     * @return This storage component
     */
    @NotNull
    TacticalStorageGUIComponent onItemsUpdate(@NotNull ItemsUpdateListener listener);

    /**
     * Retrieves the contents of this storage component for a specific player.
     *
     * @param player The player for whom to retrieve the contents
     * @return A 2D array representing the contents of the storage
     */
    @NotNull
    ItemStack[][] contents(@NotNull Player player);

    /**
     * Retrieves the items contained in this storage component for a specific player.
     *
     * @param player The player for whom to retrieve the items
     * @return A list of ItemStacks representing the items in the storage
     */
    @NotNull
    @Unmodifiable
    List<ItemStack> items(@NotNull Player player);

    /**
     * Sets the items contained in this storage component for a specific player.
     *
     * @param player The player for whom to set the items
     * @param items  The list of ItemStacks to set as the items in the storage
     * @return This storage component
     */
    @NotNull
    TacticalStorageGUIComponent items(@NotNull Player player, @NotNull List<ItemStack> items);

    /**
     * Clears the items contained in this storage component for a specific player.
     *
     * @param player The player for whom to clear the items
     * @return This storage component
     */
    @NotNull
    TacticalStorageGUIComponent clear(@NotNull Player player);

    /**
     * Sets the item predicate for this storage component.
     *
     * @param itemPredicate The item predicate to set
     * @return This storage component
     */
    @NotNull
    TacticalStorageGUIComponent itemPredicate(@NotNull ItemPredicate itemPredicate);

    /**
     * Creates a new storage GUI component with the specified width and height.
     *
     * @param width  The width of the storage component
     * @param height The height of the storage component
     * @return A new storage GUI component
     */
    @NotNull
    static TacticalStorageGUIComponent create(int width, int height) {
        return TacticalGUI.getInstance().getGUIFactory().createStorage(width, height);
    }

    /**
     * Functional interface for listeners to be notified when items in the storage component are updated.
     */
    interface ItemsUpdateListener {
        /**
         * Called when items in the storage component are updated.
         *
         * @param player The player associated with the update
         * @param items  The updated items in the storage component
         */
        void onItemsUpdate(@NotNull Player player, @NotNull ItemStack[][] items);
    }

    /**
     * Functional interface representing a predicate that determines whether an item is allowed in the storage component.
     */
    interface ItemPredicate {
        /**
         * Checks whether the specified item is allowed in the inventory of the given player.
         *
         * @param player    The player
         * @param itemStack The ItemStack to check
         * @return true if the item is allowed, otherwise false
         */
        boolean isItemAllowedInInventory(@NotNull Player player, @NotNull ItemStack itemStack);
    }
}
