package net.crossager.tactical.api.npc;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

public interface TacticalClientMob<E extends Mob> extends TacticalClientEntity<E> {
    /**
     * Plays an animation for this entity
     * @param animation the animation to play
     */
    void playAnimation(@NotNull TacticalMobAnimation animation);
    /**
     * Makes this entity flash red as if they were damaged.
     */
    void playHurtAnimation();
}
