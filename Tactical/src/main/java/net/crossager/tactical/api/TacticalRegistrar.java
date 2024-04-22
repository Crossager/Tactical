package net.crossager.tactical.api;

public class TacticalRegistrar {
    public static void setInstance(TacticalAPI instance) {
        APIHolder.setInstance(instance);
        ConfigAPIHolder.setInstance(instance);
        CommandAPIHolder.setInstance(instance);
        ProtocolAPIHolder.setInstance(instance);
        GUIAPIHolder.setInstance(instance);
        CommonsAPIHolder.setInstance(instance);
        MusicAPIHolder.setInstance(instance);
        NPCAPIHolder.setInstance(instance);
    }

    public static void removeInstance() {
        setInstance(null);
    }
}
