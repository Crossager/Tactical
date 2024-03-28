package net.crossager.tactical.api.gui.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a TacticalGUI click event.
 */
public interface TacticalGUIClickEvent {

    /**
     * Returns the player who triggered the event.
     *
     * @return the player who triggered the event.
     */
    @NotNull
    Player player();

    /**
     * Returns the X-coordinate of the clicked item in the GUI.
     *
     * @return the X-coordinate of the clicked item in the GUI.
     */
    int x();

    /**
     * Returns the Y-coordinate of the clicked item in the GUI.
     *
     * @return the Y-coordinate of the clicked item in the GUI.
     */
    int y();

    /**
     * Returns the type of click performed.
     *
     * @return the type of click performed.
     */
    @NotNull
    ClickType clickType();

    /**
     * Returns the action of the inventory click.
     *
     * @return the action of the inventory click.
     */
    @NotNull
    InventoryAction action();

    /**
     * Returns the hotbar button which was clicked, or -1 if {@code ClickType != NUMBER_KEY}
     *
     * @return the hotbar button which was clicked, or -1 if {@code ClickType != NUMBER_KEY}
     */
    int hotbarButton();

    /**
     * Sets the result of the event.
     *
     * @param result the result of the event.
     */
    void result(@NotNull Event.Result result);

    /**
     * Returns the result of the event.
     *
     * @return the result of the event.
     */
    @NotNull
    Event.Result result();

    /**
     * Returns the TacticalInventoryGUI that the event occurred in.
     *
     * @return the TacticalInventoryGUI that the event occurred in.
     */
    @NotNull
    TacticalInventoryGUI gui();
}