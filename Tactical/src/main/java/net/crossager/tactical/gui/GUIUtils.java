package net.crossager.tactical.gui;

import net.crossager.tactical.api.gui.inventory.components.TacticalGUIComponent;
import net.crossager.tactical.api.gui.inventory.TacticalInventoryGUI;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class GUIUtils {
    public static void notOutOfBounds(int x, int y, TacticalGUIComponent component) {
        if (x < 0) throw new IllegalArgumentException("X cannot be negative");
        if (y < 0) throw new IllegalArgumentException("Y cannot be negative");
        if (x > component.width()) throw new IllegalArgumentException("X (" + x + ") cannot be above " + component.width());
        if (y > component.height()) throw new IllegalArgumentException("Y (" + y + ") cannot be above " + component.height());
    }
    public static void fits(int x, int y, TacticalGUIComponent appended, TacticalGUIComponent component) {
        if (x + appended.width() > component.width()) throw new IllegalArgumentException("Width %s with x %s is too wide for width %s".formatted(appended.width(), x, component.width()));
        if (y + appended.height() > component.height()) throw new IllegalArgumentException("Height %s with y %s is too tall for height %s".formatted(appended.height(), y, component.height()));
    }
    public static int xyToSlot(int x, int y, int width) {
        return x + (y * width);
    }
    public static int[] slotToXY(int slot, int width) {
        return new int[] {x(slot, width), y(slot, width)};
    }
    public static int y(int slot, int width) {
        return slot / width;
    }
    public static int x(int slot, int width) {
        return slot % width;
    }
    public static void checkComponent(TacticalGUIComponent component) {
        if (component instanceof TacticalInventoryGUI) throw new IllegalArgumentException("Provided component cannot be a TacticalInventoryGUI");
    }

    public static void checkDimensions(int width, int height) {
        if (width < 1) throw new IllegalArgumentException("Component cannot have width <1");
        if (height < 1) throw new IllegalArgumentException("Component cannot have height <1");
    }

    @SuppressWarnings("unchecked")
    public static <T extends Event> void registerEvent(Plugin plugin, Listener listener, Class<T> eventClass, Consumer<T> onEvent) {
        plugin.getServer().getPluginManager().registerEvent(
                eventClass,
                listener,
                EventPriority.NORMAL,
                (l, event) -> onEvent.accept((T) event),
                plugin,
                true
        );
    }
}
