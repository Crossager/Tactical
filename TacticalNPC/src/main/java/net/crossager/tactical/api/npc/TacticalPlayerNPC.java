package net.crossager.tactical.api.npc;

import org.bukkit.EntityEffect;
import org.jetbrains.annotations.NotNull;

/**
 * A client-side player which is not physically present on the server. It allows for creating 'fake' players.
 */
public interface TacticalPlayerNPC extends TacticalClientObject<TacticalPlayerNPC> {
    /**
     * Plays an animation for this entity
     * @param animation the animation to play
     */
    void playAnimation(@NotNull TacticalMobAnimation animation);

    /**
     * Makes this entity flash red as if they were damaged.
     */
    void playHurtAnimation();

    /**
     * Plays an entity status event for all players in render distance. A full list of the entity statuses can be found at <a href="https://wiki.vg/Entity_statuses">https://wiki.vg/Entity_statuses</a>
     * @param status the id of the status to be played
     */
    void playEntityStatus(int status);

    /**
     * Plays an entity status event for all players in render distance.
     * @param status the status to be played
     */
    void playEntityStatus(@NotNull EntityEffect status);

    /**
     * Returns the modifiable metadata of this npc
     * @return the metadata associated with this npc
     */
    @NotNull
    TacticalPlayerNPCMetaData metaData();

    /**
     * Whether the npc is removed from tab list when outside render distance
     * @return whether the npc is removed from tab list when outside render distance
     */
    boolean removeOnUnload();

    /**
     * Whether the npc is removed from tab list when outside render distance
     * @param removeOnUnload whether the npc is removed from tab list when outside render distance
     */
    @NotNull
    TacticalPlayerNPC removeOnUnload(boolean removeOnUnload);
}
