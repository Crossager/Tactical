package net.crossager.tactical.api.npc;

public enum TacticalClientEntityInteractType {
    INTERACT,
    INTERACT_AT,
    ATTACK;
    public static TacticalClientEntityInteractType fromId(int id) {
        return switch (id) {
            case 0 -> INTERACT;
            case 1 -> ATTACK;
            case 2 -> INTERACT_AT;
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }
}
