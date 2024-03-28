package net.crossager.tactical.api.gui.animations;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an animation to be applied to a tactical GUI element.
 *
 * @param <T> The type of the animated element.
 */
public interface TacticalAnimation<T> {

    /**
     * Applies the animation to the specified context.
     *
     * @param context The context for the animation.
     * @return The result of applying the animation.
     */
    @NotNull
    T applyAnimation(@NotNull TacticalAnimationContext<T> context);
}
