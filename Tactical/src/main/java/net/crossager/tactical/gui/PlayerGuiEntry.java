package net.crossager.tactical.gui;

import net.crossager.tactical.api.gui.TacticalBaseGUI;
import org.bukkit.entity.Player;

public class PlayerGuiEntry {
    private final Player player;
    private TacticalBaseGUI gui;
    private Object associatedValue;

    public PlayerGuiEntry(Player player) {
        this.player = player;
    }

    public PlayerGuiEntry gui(TacticalBaseGUI gui) {
        this.gui = gui;
        return this;
    }

    public PlayerGuiEntry associatedValue(Object associatedValue) {
        this.associatedValue = associatedValue;
        return this;
    }

    public TacticalBaseGUI gui() {
        return gui;
    }

    @SuppressWarnings("unchecked")
    public <T> T associatedValue(Class<T> type) {
        return (T) associatedValue;
    }
}
