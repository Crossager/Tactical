package net.crossager.tactical.api.gui;

import net.crossager.tactical.api.gui.animations.TacticalAnimation;
import net.crossager.tactical.api.gui.animations.TacticalAnimationStyle;
import net.crossager.tactical.api.gui.animations.TacticalAnimator;
import net.crossager.tactical.api.gui.input.TacticalAnvilInputGUI;
import net.crossager.tactical.api.gui.input.TacticalSignGUI;
import net.crossager.tactical.api.gui.inventory.*;
import net.crossager.tactical.api.gui.inventory.components.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TacticalGUIFactory {
    @NotNull
    TacticalSignGUI createSignGUI(@NotNull String... lines);

    @NotNull
    TacticalSignGUI createSignGUI(@NotNull List<String> lines);

    @NotNull
    TacticalAnvilInputGUI createAnvilInputGUI();

    @NotNull
    TacticalStaticGUIComponent createStaticComponent(@NotNull ItemStack itemStack);

    @NotNull
    TacticalGUIContainer createContainer(int width, int height);

    @NotNull
    TacticalInventoryGUI createGUI(int rows, @NotNull String title);

    @NotNull
    TacticalStorageGUIComponent createStorage(int width, int height);

    @NotNull
    TacticalInventoryGUI createDispenserGUI(@NotNull String title);

    @NotNull
    TacticalInventoryGUI createHopperGUI(@NotNull String title);

    @NotNull
    TacticalAnimator createAnimationStyle(@NotNull TacticalAnimationStyle style, int ticksPerStep, float stepAmount, float animationInterval);

    @NotNull
    TacticalStaticGUIComponent createStaticComponent(@NotNull TacticalGUIItemGenerator itemGenerator);

    @NotNull
    TacticalStaticGUIComponent createStaticComponent(@NotNull TacticalAnimation<ItemStack> animation, @NotNull TacticalAnimator animator);

    <E extends TacticalGUIComponent> TacticalPageViewerComponent<E> createPageViewer(List<E> items, int width, int height);

    @NotNull
    TacticalItemGUIComponent createItemComponent(@NotNull ItemStack itemStack);
}
