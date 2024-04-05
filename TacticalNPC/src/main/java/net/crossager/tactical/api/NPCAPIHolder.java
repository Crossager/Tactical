package net.crossager.tactical.api;

class NPCAPIHolder {
    private static TacticalNPC instance;

    static TacticalNPC getInstance() {
        if (instance == null) throw new IllegalStateException("Tactical not loaded");
        return instance;
    }

    static void setInstance(TacticalNPC instance) {
        NPCAPIHolder.instance = instance;
    }
}
