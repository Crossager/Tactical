package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.gui.TacticalGUIManager;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;

public class SimpleTacticalHopperInventoryGUI extends SimpleAbstractTacticalInventoryGUI {
    public SimpleTacticalHopperInventoryGUI(TacticalGUIManager guiManager, String title) {
        super(guiManager, 5, 1, player -> Bukkit.createInventory(null, InventoryType.HOPPER, title));
    }
}
