package net.crossager.tactical.api.gui.inventory.components;

import net.crossager.tactical.api.gui.inventory.ItemMoveAction;
import net.crossager.tactical.api.gui.inventory.TacticalGUIClickEvent;
import net.crossager.tactical.api.gui.inventory.components.TacticalStaticGUIComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A base component. Any actual component should be an implementation of this.
 * Go to {@link TacticalStaticGUIComponent} if you need a simple component
 */
public interface TacticalGUIComponent {

    /**
     * Gets the width of the component.
     *
     * @return The width of the component
     */
    int width();

    /**
     * Gets the height of the component.
     *
     * @return The height of the component
     */
    int height();

    /**
     * Retrieves the ItemStack at the specified position and time since last update.
     *
     * @param player              The player viewing the GUI
     * @param x                   The x-coordinate of the item
     * @param y                   The y-coordinate of the item
     * @param ticksSinceLastUpdate The number of ticks since the last update
     * @return The ItemStack at the specified position
     */
    @NotNull
    ItemStack itemAt(@NotNull Player player, int x, int y, int ticksSinceLastUpdate);

    /**
     * Handles a click event on the component.
     *
     * @param event The click event to handle
     */
    void click(@NotNull TacticalGUIClickEvent event);

    /**
     * Checks if the specified item can be moved to the given position.
     *
     * @param player     The player attempting to move the item
     * @param x          The x-coordinate to move the item to
     * @param y          The y-coordinate to move the item to
     * @param itemStack  The ItemStack to move
     * @param action     The action being performed (drag or move inventory)
     * @return True if the item can be moved to the specified position, false otherwise
     */
    boolean canItemBeMovedTo(@NotNull Player player, int x, int y, @NotNull ItemStack itemStack, @NotNull ItemMoveAction action);

    /**
     * Moves the specified item to the given position.
     *
     * @param player     The player moving the item
     * @param x          The x-coordinate to move the item to
     * @param y          The y-coordinate to move the item to
     * @param itemStack  The ItemStack to move
     * @param action     The action being performed (drag or move inventory)
     */
    void moveItemTo(@NotNull Player player, int x, int y, @NotNull ItemStack itemStack, @NotNull ItemMoveAction action);

    /**
     * Checks if an item can be collected at the specified position.
     *
     * @param player The player attempting to collect the item
     * @param x      The x-coordinate of the item
     * @param y      The y-coordinate of the item
     * @return True if an item can be collected at the specified position, false otherwise
     */
    boolean canCollectItemAt(@NotNull Player player, int x, int y);

    /**
     * Collects an item at the specified position.
     *
     * @param player The player collecting the item
     * @param x      The x-coordinate of the item
     * @param y      The y-coordinate of the item
     * @return An optional containing the collected item, or empty if no item was collected
     */
    Optional<Object> collectItemAt(@NotNull Player player, int x, int y);
}
