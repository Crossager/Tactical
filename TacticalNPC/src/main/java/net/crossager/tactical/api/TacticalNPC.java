package net.crossager.tactical.api;

import org.jetbrains.annotations.NotNull;

public interface TacticalNPC {
    @NotNull
    static TacticalNPC getInstance() {
        return NPCAPIHolder.getInstance();
    }
}
