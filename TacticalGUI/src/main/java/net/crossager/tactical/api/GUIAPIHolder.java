package net.crossager.tactical.api;

class GUIAPIHolder {
    private static TacticalGUI instance;

    static TacticalGUI getInstance() {
        if (instance == null) throw new IllegalStateException("Tactical not loaded");
        return instance;
    }

    static void setInstance(TacticalGUI instance) {
        GUIAPIHolder.instance = instance;
    }
}
