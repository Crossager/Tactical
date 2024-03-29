package net.crossager.tactical.api.gui.animations;

import org.jetbrains.annotations.NotNull;

public interface TacticalAnimatable<T> {
    /**
     * Sets the animation for this static GUI component.
     *
     * @param animation The animation to set
     * @return This static GUI component with the animation set
     */
    @NotNull
    TacticalAnimatable<T> animate(@NotNull TacticalAnimation<T> animation);
    /**
     * Sets the animation and animator for this static GUI component.
     *
     * @param animation The animation to set
     * @param animator  The animator to use
     * @return This static GUI component with the animation and animator set
     */
    @NotNull
    TacticalAnimatable<T> animate(@NotNull TacticalAnimation<T> animation, @NotNull TacticalAnimator animator);
}
