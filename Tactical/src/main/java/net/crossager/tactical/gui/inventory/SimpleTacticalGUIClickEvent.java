package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.TacticalGUIClickEvent;
import net.crossager.tactical.api.gui.inventory.TacticalInventoryGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class SimpleTacticalGUIClickEvent implements TacticalGUIClickEvent {
    private final Player player;
    private final ComponentRepresentation representation;
    private final InventoryClickEvent event;
    private final TacticalInventoryGUI gui;

    public SimpleTacticalGUIClickEvent(Player player, ComponentRepresentation representation, InventoryClickEvent event, TacticalInventoryGUI gui) {
        this.player = player;
        this.representation = representation;
        this.event = event;
        this.gui = gui;
    }

    @Override
    public @NotNull Player player() {
        return player;
    }

    @Override
    public int x() {
        return representation.modX();
    }

    @Override
    public int y() {
        return representation.modY();
    }

    @Override
    public @NotNull ClickType clickType() {
        return event.getClick();
    }

    @Override
    public @NotNull InventoryAction action() {
        return event.getAction();
    }

    @Override
    public int hotbarButton() {
        return event.getHotbarButton();
    }

    @Override
    public void result(@NotNull Event.Result result) {
        event.setResult(Event.Result.ALLOW);
    }

    @NotNull
    @Override
    public Event.Result result() {
        return event.getResult();
    }

    @Override
    public @NotNull TacticalInventoryGUI gui() {
        return gui;
    }

    @Override
    public String toString() {
        return "SimpleTacticalGUIClickEvent{" +
                "player=" + player +
                ", x=" + x() +
                ", y=" + y() +
                ", clickType=" + clickType() +
                ", action=" + action() +
                ", result=" + result() +
                '}';
    }
}
