package net.crossager.tactical.gui.animations;

import net.crossager.tactical.api.gui.animations.TacticalAnimationStyle;
import net.crossager.tactical.api.gui.animations.TacticalAnimator;

public record SimpleTacticalAnimator(TacticalAnimationStyle style, int ticksPerStep, float stepAmount, float animationInterval) implements TacticalAnimator {
}
