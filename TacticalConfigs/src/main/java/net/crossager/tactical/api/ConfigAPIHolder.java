package net.crossager.tactical.api;

class ConfigAPIHolder {
    private static TacticalConfigs instance;

    static TacticalConfigs getInstance() {
        if (instance == null) throw new IllegalStateException("Tactical not loaded");
        return instance;
    }

    static void setInstance(TacticalConfigs instance) {
        ConfigAPIHolder.instance = instance;
    }
}
