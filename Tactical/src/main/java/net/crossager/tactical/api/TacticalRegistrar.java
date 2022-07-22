package net.crossager.tactical.api;

public class TacticalRegistrar {
    public static void setInstance(TacticalAPI instance) {
        APIHolder.setInstance(instance);
        ConfigAPIHolder.setInstance(instance);
    }
}
