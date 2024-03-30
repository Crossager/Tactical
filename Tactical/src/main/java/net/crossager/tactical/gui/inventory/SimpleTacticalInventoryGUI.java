package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.gui.TacticalGUIManager;
import org.bukkit.Bukkit;

public class SimpleTacticalInventoryGUI extends SimpleAbstractTacticalInventoryGUI {
    public SimpleTacticalInventoryGUI(TacticalGUIManager guiManager, int rows, String title) {
        super(guiManager, 9, rows, player -> Bukkit.createInventory(null, 9 * rows, title));
    }
}
