package net.crossager.tactical.api;

class CommandAPIHolder {
    private static TacticalCommands instance;

    static TacticalCommands getInstance() {
        if (instance == null) throw new IllegalStateException("Tactical not loaded");
        return instance;
    }

    static void setInstance(TacticalCommands instance) {
        CommandAPIHolder.instance = instance;
    }
}
