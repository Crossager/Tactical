package net.crossager.tactical.api.gui.animations;

import net.crossager.tactical.api.TacticalGUI;
import org.jetbrains.annotations.NotNull;

/**
 * Interface representing a tactical animator, which is responsible for animating GUI components.
 *
 * @since 1.0
 */
public interface TacticalAnimator {

    /**
     * Returns the animation style used by this animator.
     *
     * @return the animation style used by this animator
     */
    @NotNull
    TacticalAnimationStyle style();

    /**
     * Returns the interval between each step of the animation.
     *
     * @return the interval between each step of the animation
     */
    int ticksPerStep();

    /**
     * Returns the amount by which each step of the animation changes the component.
     *
     * @return the amount by which each step of the animation changes the component
     */
    float stepAmount();

    /**
     * Returns the interval between each animation loop.
     *
     * @return the interval between each animation loop
     */
    float animationInterval();

    /**
     * The linear tactical animator.
     */
    TacticalAnimator LINEAR = defaultFromStyle(TacticalAnimationStyle.LINEAR);

    /**
     * The fade-in tactical animator.
     */
    TacticalAnimator FADE_IN = defaultFromStyle(TacticalAnimationStyle.FADE_IN);

    /**
     * The fade-out tactical animator.
     */
    TacticalAnimator FADE_OUT = defaultFromStyle(TacticalAnimationStyle.FADE_OUT);

    /**
     * The fade-in-out tactical animator.
     */
    TacticalAnimator FADE_IN_OUT = defaultFromStyle(TacticalAnimationStyle.FADE_IN_OUT);

    /**
     * Animator that doesn't animate
     */
    TacticalAnimator NONE = of(TacticalAnimationStyle.LINEAR, 0, 0, 0);

    /**
     * Returns a new instance of a tactical animator with the specified style.
     *
     * @param style the style of the new animator
     * @return a new instance of a tactical animator with the specified style
     */
    @NotNull
    static TacticalAnimator defaultFromStyle(@NotNull TacticalAnimationStyle style) {
        return of(style, 4, 0.2F, 0.2F);
    }

    /**
     * Returns a new instance of a tactical animator with the specified parameters.
     *
     * @param style the style of the new animator
     * @param ticksPerStep the interval between each step of the animation
     * @param stepAmount the amount by which each step of the animation changes the component
     * @param animationInterval the interval between each animation loop
     * @return a new instance of a tactical animator with the specified parameters
     */
    @NotNull
    static TacticalAnimator of(@NotNull TacticalAnimationStyle style,
                               int ticksPerStep,
                               float stepAmount,
                               float animationInterval) {
        return TacticalGUI.getInstance().getGUIFactory().createAnimationStyle(style, ticksPerStep, stepAmount, animationInterval);
    }
}
