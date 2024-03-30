package net.crossager.tactical.gui.animations;

import net.crossager.tactical.api.gui.animations.TacticalAnimationContext;
import org.bukkit.entity.Player;

public record SimpleTacticalAnimationContext<T>(T previousElement, float progress, Player player) implements TacticalAnimationContext<T> {
}
