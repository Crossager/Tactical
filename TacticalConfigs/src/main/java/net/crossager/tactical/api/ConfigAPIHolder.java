package net.crossager.tactical.api;

import org.jetbrains.annotations.NotNull;

class ConfigAPIHolder {
    private static TacticalConfigs instance;

    @NotNull
    static TacticalConfigs getInstance() {
        if (instance == null) throw new IllegalStateException("Tactical plugin not loaded");
        return instance;
    }

    static void setInstance(@NotNull TacticalConfigs instance) {
        ConfigAPIHolder.instance = instance;
    }
}
