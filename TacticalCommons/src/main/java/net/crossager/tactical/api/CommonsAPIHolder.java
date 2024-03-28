package net.crossager.tactical.api;

class CommonsAPIHolder {
    private static TacticalCommons instance;

    static TacticalCommons getInstance() {
        if (instance == null) throw new IllegalStateException("Tactical not loaded");
        return instance;
    }

    static void setInstance(TacticalCommons instance) {
        CommonsAPIHolder.instance = instance;
    }
}
