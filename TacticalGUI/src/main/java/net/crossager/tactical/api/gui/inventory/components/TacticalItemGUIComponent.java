package net.crossager.tactical.api.gui.inventory.components;

import net.crossager.tactical.api.TacticalGUI;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface TacticalItemGUIComponent extends TacticalGUIComponent {

    /**
     * Sets the ItemStack for this GUI component.
     *
     * @param itemStack The ItemStack to set
     * @return This GUI component with the ItemStack set
     */
    @NotNull
    TacticalItemGUIComponent itemStack(@NotNull ItemStack itemStack);

    /**
     * Creates a new component with the specified ItemStack.
     *
     * @param itemStack The ItemStack for the new component
     * @return The newly created component
     */
    @NotNull
    static TacticalItemGUIComponent of(@NotNull ItemStack itemStack) {
        return TacticalGUI.getInstance().getGUIFactory().createItemComponent(itemStack);
    }
}
