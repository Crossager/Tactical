package net.crossager.tactical.api.gui.animations;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the context for a tactical GUI animation.
 *
 * @param <T> The type of the animated element.
 */
public interface TacticalAnimationContext<T> {

    /**
     * Retrieves the previous state of the animated element.
     *
     * @return The previous state of the animated element.
     */
    @NotNull
    T previousElement();

    /**
     * Retrieves the progress of the animation.
     *
     * @return The progress of the animation as a float value between 0.0 and 1.0.
     */
    float progress();

    /**
     * Retrieves the player associated with the animation context.
     *
     * @return The player associated with the animation context.
     */
    @NotNull
    Player player();
}
