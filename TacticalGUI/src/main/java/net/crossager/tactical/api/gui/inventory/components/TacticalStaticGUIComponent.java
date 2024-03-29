package net.crossager.tactical.api.gui.inventory.components;

import net.crossager.tactical.api.TacticalGUI;
import net.crossager.tactical.api.gui.animations.TacticalAnimatable;
import net.crossager.tactical.api.gui.animations.TacticalAnimation;
import net.crossager.tactical.api.gui.animations.TacticalAnimator;
import net.crossager.tactical.api.gui.inventory.TacticalGUIClickEvent;
import net.crossager.tactical.api.gui.inventory.TacticalGUIItemGenerator;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Represents a static GUI component that does not change over time.
 */
public interface TacticalStaticGUIComponent extends TacticalGUIComponent, TacticalAnimatable<ItemStack> {

    /**
     * Sets the ItemStack for this static GUI component.
     *
     * @param itemStack The ItemStack to set
     * @return This static GUI component with the ItemStack set
     */
    @NotNull
    TacticalStaticGUIComponent itemStack(@NotNull ItemStack itemStack);

    /**
     * Sets the ItemStack for this static GUI component using a generator function.
     *
     * @param itemGenerator The item generator function to use
     * @return This static GUI component with the ItemStack set
     */
    @NotNull
    TacticalStaticGUIComponent itemStack(@NotNull TacticalGUIItemGenerator itemGenerator);

    /**
     * Sets the animation for this static GUI component.
     *
     * @param animation The animation to set
     * @return This static GUI component with the animation set
     */
    @NotNull
    TacticalStaticGUIComponent animate(@NotNull TacticalAnimation<ItemStack> animation);

    /**
     * Sets the animation and animator for this static GUI component.
     *
     * @param animation The animation to set
     * @param animator  The animator to use
     * @return This static GUI component with the animation and animator set
     */
    @NotNull
    TacticalStaticGUIComponent animate(@NotNull TacticalAnimation<ItemStack> animation, @NotNull TacticalAnimator animator);

    /**
     * Sets the onClick event handler for this static GUI component.
     *
     * @param onClick The event handler to set
     * @return This static GUI component with the onClick event handler set
     */
    @NotNull
    TacticalStaticGUIComponent onClick(@NotNull Consumer<TacticalGUIClickEvent> onClick);

    /**
     * Creates a new TacticalStaticGUIComponent with the specified ItemStack.
     *
     * @param itemStack The ItemStack for the new component
     * @return The newly created TacticalStaticGUIComponent
     */
    @NotNull
    static TacticalStaticGUIComponent of(@NotNull ItemStack itemStack) {
        return TacticalGUI.getInstance().getGUIFactory().createStaticComponent(itemStack);
    }

    /**
     * Creates a new TacticalStaticGUIComponent using the specified item generator function.
     *
     * @param itemGenerator The item generator function to use
     * @return The newly created TacticalStaticGUIComponent
     */
    @NotNull
    static TacticalStaticGUIComponent of(@NotNull TacticalGUIItemGenerator itemGenerator) {
        return TacticalGUI.getInstance().getGUIFactory().createStaticComponent(itemGenerator);
    }

    /**
     * Creates a new TacticalStaticGUIComponent with the specified animation and animator.
     *
     * @param animation The animation for the new component
     * @param animator  The animator to use
     * @return The newly created TacticalStaticGUIComponent
     */
    @NotNull
    static TacticalStaticGUIComponent of(@NotNull TacticalAnimation<ItemStack> animation, @NotNull TacticalAnimator animator) {
        return TacticalGUI.getInstance().getGUIFactory().createStaticComponent(animation, animator);
    }
}
