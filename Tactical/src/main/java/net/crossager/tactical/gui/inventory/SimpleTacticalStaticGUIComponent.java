package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.animations.TacticalAnimation;
import net.crossager.tactical.api.gui.animations.TacticalAnimator;
import net.crossager.tactical.api.gui.inventory.ItemUtils;
import net.crossager.tactical.api.gui.inventory.TacticalGUIClickEvent;
import net.crossager.tactical.api.gui.inventory.TacticalGUIItemGenerator;
import net.crossager.tactical.api.gui.inventory.components.TacticalStaticGUIComponent;
import net.crossager.tactical.gui.GUIUtils;
import net.crossager.tactical.gui.animations.SimpleTacticalAnimationContext;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SimpleTacticalStaticGUIComponent implements ImmutableTacticalGUIComponent, TacticalStaticGUIComponent {
    private ItemStack itemStack = ItemUtils.AIR;
    private TacticalAnimator animator;
    private TacticalAnimation<ItemStack> animation;
    private float extraAnimationInterval = 0;
    private float lastAnimationProgress = 0;
    private Consumer<TacticalGUIClickEvent> onClick = x -> {};

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
        GUIUtils.notOutOfBounds(x, y, this);
        if (animator == null) return itemStack;

        if (animator.ticksPerStep() == 0)
            return animation.applyAnimation(new SimpleTacticalAnimationContext<>(itemStack, 0, player));

        float actualProgress = (lastAnimationProgress + ((float) ticksSinceLastUpdate / animator.ticksPerStep()) * animator.stepAmount()) % 1;

        float processedProgress = animator.style().progress(actualProgress);

        float prevProcessed = animator.style().progress(lastAnimationProgress);

        float interval = extraAnimationInterval + Math.abs(processedProgress - prevProcessed);

        if (actualProgress > 1) {
            interval += Math.abs(animator.style().progress(1) - processedProgress);
        }

        boolean runAnimation = interval >= animator.animationInterval();
        extraAnimationInterval = interval % animator.animationInterval();
        lastAnimationProgress = actualProgress;
        if (runAnimation)
            itemStack = animation.applyAnimation(new SimpleTacticalAnimationContext<>(itemStack, processedProgress, player));

        return itemStack;
    }



    @Override
    public void click(@NotNull TacticalGUIClickEvent event) {
        GUIUtils.notOutOfBounds(event.x(), event.y(), this);
        onClick.accept(event);
    }

    @Override
    public @NotNull TacticalStaticGUIComponent itemStack(@NotNull ItemStack itemStack) {
        this.animator = null;
        this.itemStack = itemStack;
        return this;
    }

    @Override
    public @NotNull TacticalStaticGUIComponent itemStack(@NotNull TacticalGUIItemGenerator itemGenerator) {
        return animate(context -> itemGenerator.generateItem(context.player()), TacticalAnimator.NONE);
    }

    @Override
    public @NotNull TacticalStaticGUIComponent animate(@NotNull TacticalAnimation<ItemStack> animation) {
        return animate(animation, TacticalAnimator.LINEAR);
    }

    @Override
    public @NotNull TacticalStaticGUIComponent animate(@NotNull TacticalAnimation<ItemStack> animation, @NotNull TacticalAnimator animator) {
        this.animation = animation;
        this.animator = animator;
        return this;
    }

    @Override
    public @NotNull TacticalStaticGUIComponent onClick(@NotNull Consumer<TacticalGUIClickEvent> onClick) {
        this.onClick = onClick;
        return this;
    }

    @Override
    public String toString() {
        return "TacticalStaticGUIComponent";
    }
}
