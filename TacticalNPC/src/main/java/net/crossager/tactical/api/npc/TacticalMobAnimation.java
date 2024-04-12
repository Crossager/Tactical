package net.crossager.tactical.api.npc;

public enum TacticalMobAnimation {
    SWING_MAIN_HAND,
    @Deprecated
    TAKE_DAMAGE,
    LEAVE_BED,
    SWING_OFFHAND,
    CRITICAL_EFFECT,
    MAGIC_CRITICAL_EFFECT
    ;

    private final int id;

    TacticalMobAnimation() {
        this.id = ordinal();
    }

    public int id() {
        return id;
    }
}
