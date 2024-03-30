package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.gui.TacticalGUIManager;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;

public class SimpleTacticalDispenserInventoryGUI extends SimpleAbstractTacticalInventoryGUI {
    public SimpleTacticalDispenserInventoryGUI(TacticalGUIManager guiManager, String title) {
        super(guiManager, 3, 3, player -> Bukkit.createInventory(null, InventoryType.DISPENSER, title));
    }
}
