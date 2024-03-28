package net.crossager.tactical.api.gui.animations;

import org.jetbrains.annotations.NotNull;

public interface TacticalAnimatable<T> {
    @NotNull
    TacticalAnimatable<T> addAnimation(@NotNull TacticalAnimation<T> animation);
    @NotNull
    TacticalAnimatable<T> addAnimation(@NotNull TacticalAnimation<T> animation, @NotNull TacticalAnimator style);
}
