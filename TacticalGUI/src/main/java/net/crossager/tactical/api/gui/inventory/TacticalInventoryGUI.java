package net.crossager.tactical.api.gui.inventory;

import net.crossager.tactical.api.TacticalGUI;
import net.crossager.tactical.api.gui.TacticalBaseGUI;
import net.crossager.tactical.api.gui.inventory.components.TacticalGUIComponent;
import net.crossager.tactical.api.gui.inventory.components.TacticalGUIContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Represents a Tactical GUI for managing inventory-related operations.
 */
public interface TacticalInventoryGUI extends TacticalGUIContainer, TacticalBaseGUI {

    /**
     * Unregisters the GUI, removing it from the system.
     */
    void unregister();

    /**
     * Updates the display of the GUI for the specified player.
     *
     * @param player The player to update the display for
     */
    void updateDisplay(@NotNull Player player);

    /**
     * Updates the display of specific slots in the GUI for the specified player.
     *
     * @param player The player to update the display for
     * @param fromX  The starting x-coordinate of the region to update
     * @param fromY  The starting y-coordinate of the region to update
     * @param toX    The ending x-coordinate of the region to update
     * @param toY    The ending y-coordinate of the region to update
     */
    void updateDisplaySlots(@NotNull Player player, int fromX, int fromY, int toX, int toY);

    /**
     * Sets the component at the specified position within the container.
     *
     * @param x         The x-coordinate of the component
     * @param y         The y-coordinate of the component
     * @param component The component to set
     * @return The container with the component set
     */
    @NotNull
    @Override
    TacticalInventoryGUI setComponent(int x, int y, @NotNull TacticalGUIComponent component);

    /**
     * Removes the component at the specified position within the container.
     *
     * @param x The x-coordinate of the component to remove
     * @param y The y-coordinate of the component to remove
     * @return The container with the component removed
     */
    @NotNull
    @Override
    TacticalInventoryGUI removeComponent(int x, int y);

    /**
     * Creates a border around the container using the specified component.
     *
     * @param component The component to use for the border
     * @return The container with the border created
     */
    @NotNull
    @Override
    TacticalInventoryGUI createBorder(@NotNull TacticalGUIComponent component);

    /**
     * Fills the specified region within the container with the given component.
     *
     * @param fromX     The starting x-coordinate of the region
     * @param fromY     The starting y-coordinate of the region
     * @param toX       The ending x-coordinate of the region
     * @param toY       The ending y-coordinate of the region
     * @param component The component to fill the region with
     * @return The container with the region filled
     */
    @NotNull
    TacticalInventoryGUI fillComponent(int fromX, int fromY, int toX, int toY, @NotNull TacticalGUIComponent component);

    /**
     * Sets a consumer to be invoked when the GUI is closed by a player.
     *
     * @param onClose The consumer to invoke when the GUI is closed
     * @return The updated GUI
     */
    @NotNull
    TacticalInventoryGUI onClose(@NotNull Consumer<Player> onClose);

    /**
     * Checks if players can interact with the inventory.
     *
     * @return True if players can interact with the inventory, false otherwise
     */
    boolean canPlayerInteractWithInventory();

    /**
     * Sets whether players can interact with the inventory.
     *
     * @param canPlayerInteractWithInventory True to allow player interaction, false otherwise
     * @return The updated GUI
     */
    @NotNull
    TacticalInventoryGUI canPlayerInteractWithInventory(boolean canPlayerInteractWithInventory);

    /**
     * Adds an animation area to the GUI with the specified update interval.
     *
     * @param updateInterval The interval in ticks between updates
     * @return The updated GUI
     */
    @NotNull
    TacticalInventoryGUI addAnimationArea(int updateInterval);

    /**
     * Removes the animation area from the GUI.
     *
     * @return The updated GUI
     */
    @NotNull
    TacticalInventoryGUI removeAnimationArea();

    /**
     * Adds an animation area to the GUI with the specified update interval and region.
     *
     * @param updateInterval The interval in ticks between updates
     * @param fromX          The starting x-coordinate of the region
     * @param fromY          The starting y-coordinate of the region
     * @param toX            The ending x-coordinate of the region
     * @param toY            The ending y-coordinate of the region
     * @return The updated GUI
     */
    @NotNull
    TacticalInventoryGUI addAnimationArea(int updateInterval, int fromX, int fromY, int toX, int toY);

    /**
     * Removes the animation area from the specified region of the GUI.
     *
     * @param fromX The starting x-coordinate of the region
     * @param fromY The starting y-coordinate of the region
     * @param toX   The ending x-coordinate of the region
     * @param toY   The ending y-coordinate of the region
     * @return The updated GUI
     */
    @NotNull
    TacticalInventoryGUI removeAnimationArea(int fromX, int fromY, int toX, int toY);

    /**
     * Registers a player data listener to this gui
     * @param listener the data listener to register
     * @param dataPersistence which persistence level the data should have
     * @return The updated GUI
     */
    @NotNull
    <T> TacticalInventoryGUI registerPlayerDataListener(@NotNull TacticalComponentPlayerDataListener<T> listener, @NotNull TacticalComponentPlayerDataListener.DataPersistence dataPersistence);

    /**
     * Creates a new inventory with the specified number of rows and title.
     *
     * @param rows  The number of rows in the inventory
     * @param title The title of the inventory
     * @return The newly created TacticalInventoryGUI
     */
    @NotNull
    static TacticalInventoryGUI create(int rows, @NotNull String title) {
        return TacticalGUI.getInstance().getGUIFactory().createGUI(rows, title);
    }

    /**
     * Creates a new dispenser/dropper inventory (3x3) with a given title
     *
     * @param title The title of the dispenser inventory
     * @return The newly created TacticalInventoryGUI
     */
    @NotNull
    static TacticalInventoryGUI createDispenser(@NotNull String title) {
        return TacticalGUI.getInstance().getGUIFactory().createDispenserGUI(title);
    }

    /**
     * Creates a new hopper inventory (5x1) with a given title
     *
     * @param title The title of the hopper inventory
     * @return The newly created TacticalInventoryGUI
     */
    @NotNull
    static TacticalInventoryGUI createHopper(@NotNull String title) {
        return TacticalGUI.getInstance().getGUIFactory().createHopperGUI(title);
    }
}
