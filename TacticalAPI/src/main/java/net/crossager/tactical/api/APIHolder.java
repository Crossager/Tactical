package net.crossager.tactical.api;

class APIHolder {
    private static TacticalAPI instance;

    static TacticalAPI getInstance() {
        if (instance == null) throw new IllegalStateException("Tactical not loaded");
        return instance;
    }

    static void setInstance(TacticalAPI instance) {
        APIHolder.instance = instance;
    }
}
