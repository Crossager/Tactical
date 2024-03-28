package net.crossager.tactical.api;

class ProtocolAPIHolder {
    private static TacticalProtocol instance;

    static TacticalProtocol getInstance() {
        if (instance == null) throw new IllegalStateException("Tactical not loaded");
        return instance;
    }

    static void setInstance(TacticalProtocol instance) {
        ProtocolAPIHolder.instance = instance;
    }
}
