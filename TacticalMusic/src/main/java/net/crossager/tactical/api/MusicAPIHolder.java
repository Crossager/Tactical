package net.crossager.tactical.api;

class MusicAPIHolder {
    private static TacticalMusic instance;

    static TacticalMusic getInstance() {
        if (instance == null) throw new IllegalStateException("Tactical not loaded");
        return instance;
    }

    static void setInstance(TacticalMusic instance) {
        MusicAPIHolder.instance = instance;
    }
}
