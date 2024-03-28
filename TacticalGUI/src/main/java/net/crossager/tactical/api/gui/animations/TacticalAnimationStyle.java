package net.crossager.tactical.api.gui.animations;

import org.jetbrains.annotations.NotNull;

/**
 * A functional interface for creating custom animation effects.
 */
public interface TacticalAnimationStyle {

    /**
     * Calculates the progress of the animation for a given actual progress value.
     *
     * @param actualProgress The actual progress value of the animation, from 0.0F to 1.0F.
     * @return The progress of the animation, from 0.0F to 1.0F.
     */
    float progress(float actualProgress);

    /**
     * A linear animation effect.
     */
    @NotNull
    TacticalAnimationStyle LINEAR = actualProgress -> actualProgress;

    /**
     * An animation effect that fades in elements using a cubic function.
     */
    @NotNull
    TacticalAnimationStyle FADE_IN = actualProgress -> actualProgress * actualProgress * actualProgress;

    /**
     * An animation effect that fades out elements using a cubic function.
     */
    @NotNull
    TacticalAnimationStyle FADE_OUT = inverse(FADE_IN);

    /**
     * An animation effect that fades in and out elements using a sigmoid function.
     */
    @NotNull
    TacticalAnimationStyle FADE_IN_OUT = sigmoid(-0.5F, 13);

    /**
     * An animation effect that oscillates from 0 to 1 using a sine function
     */
    @NotNull
    TacticalAnimationStyle SINE = actualProgress -> (float) (Math.sin(Math.PI * 2 * actualProgress) + 1) / 2;

    /**
     * Creates an animation effect that uses a sigmoid function.
     *
     * @param modX The horizontal shift of the function.
     * @param strength The strength of the function.
     * @return A new TacticalAnimationStyle that uses a sigmoid function.
     */
    @NotNull
    static TacticalAnimationStyle sigmoid(float modX, float strength) {
        return actualProgress -> (float) (1 / (1 + Math.pow(Math.E, -(actualProgress + modX) * strength)));
    }

    /**
     * Creates an animation effect that is the inverse of another animation effect.
     *
     * @param style The animation effect to inverse.
     * @return A new TacticalAnimationStyle that is the inverse of the given style.
     */
    @NotNull
    static TacticalAnimationStyle inverse(@NotNull TacticalAnimationStyle style) {
        return actualProgress -> style.progress(1 - actualProgress);
    }

    /**
     * Creates an animation effect that combines the given {@code TacticalAnimationStyle} with an "in-out" effect.
     * The resulting animation style will appear as if it reaches a peak at the middle (0.5) and then reverses.
     *
     * @param style The base animation style to be combined with the "in-out" effect.
     * @return A new TacticalAnimationStyle that combines the given style with an "in-out" effect.
     */
    @NotNull
    static TacticalAnimationStyle inOut(@NotNull TacticalAnimationStyle style) {
        return actualProgress -> style.progress(actualProgress > 0.5 ? 2 - 2 * actualProgress : 2 * actualProgress);
    }
}