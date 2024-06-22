package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.TacticalGUIClickEvent;
import net.crossager.tactical.api.gui.inventory.components.TacticalItemGUIComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SimpleTacticalItemGUIComponent implements ImmutableTacticalGUIComponent, TacticalItemGUIComponent {
    private ItemStack itemStack;

    public SimpleTacticalItemGUIComponent(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public int width() {
        return 1;
    }

    @Override
    public int height() {
        return 1;
    }

    @Override
    public @NotNull ItemStack itemAt(@NotNull Player player, int x, int y, int ticksSinceLastUpdate) {
        return itemStack;
    }

    @Override
    public void click(@NotNull TacticalGUIClickEvent event) {

    }

    @Override
    public @NotNull TacticalItemGUIComponent itemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }
}
