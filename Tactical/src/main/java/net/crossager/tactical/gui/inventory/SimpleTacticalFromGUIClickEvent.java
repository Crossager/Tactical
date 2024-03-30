package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.TacticalGUIClickEvent;
import net.crossager.tactical.api.gui.inventory.TacticalInventoryGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.jetbrains.annotations.NotNull;

public class SimpleTacticalFromGUIClickEvent implements TacticalGUIClickEvent {
    private final TacticalGUIClickEvent event;
    private final int x;
    private final int y;

    public SimpleTacticalFromGUIClickEvent(TacticalGUIClickEvent event, int x, int y) {
        this.event = event;
        this.x = x;
        this.y = y;
    }
    @Override
    public @NotNull Player player() {
        return event.player();
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public @NotNull ClickType clickType() {
        return event.clickType();
    }

    @Override
    public @NotNull InventoryAction action() {
        return event.action();
    }

    @Override
    public int hotbarButton() {
        return event.hotbarButton();
    }

    @Override
    public void result(Event.@NotNull Result result) {
        event.result(result);
    }

    @Override
    public @NotNull Event.Result result() {
        return event.result();
    }

    @Override
    public @NotNull TacticalInventoryGUI gui() {
        return event.gui();
    }
}
