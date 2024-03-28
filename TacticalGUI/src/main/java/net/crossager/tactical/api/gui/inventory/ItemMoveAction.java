package net.crossager.tactical.api.gui.inventory;

/**
 * Enumerates the possible actions for moving items in a GUI inventory.
 */
public enum ItemMoveAction {

    /**
     * Indicates a drag action, where the item is being dragged within the inventory.
     */
    DRAG,

    /**
     * Like shift clicking to switch the item from inventory to inventory
     */
    MOVE_INVENTORY
}
