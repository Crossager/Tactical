package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.components.TacticalGUIComponent;
import net.crossager.tactical.api.gui.inventory.components.TacticalStaticGUIComponent;
import net.crossager.tactical.api.gui.inventory.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

record ComponentRepresentation(TacticalGUIComponent component, int modX, int modY) {
    public ItemStack item(Player player, int ticksSinceLastUpdate) {
        return component.itemAt(player, modX, modY, ticksSinceLastUpdate);
    }

    static ComponentRepresentation EMPTY = new ComponentRepresentation(TacticalStaticGUIComponent.of(ItemUtils.AIR), 0, 0);
}
