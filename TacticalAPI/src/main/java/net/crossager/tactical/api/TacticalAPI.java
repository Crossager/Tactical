package net.crossager.tactical.api;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * The main interface for the Tactical API. Provides access to all other interfaces and utility methods.
 */
public interface TacticalAPI extends TacticalConfigs, TacticalCommons, TacticalCommands, TacticalProtocol, TacticalGUI, TacticalMusic, TacticalNPC {
    /**
     * Returns the singleton instance of the {@link TacticalAPI} class.
     *
     * @return the singleton instance of the {@link TacticalAPI} class
     */
    @NotNull
    static TacticalAPI getInstance() {
        return APIHolder.getInstance();
    }
    /**
     * Returns the logger for this {@link TacticalAPI} instance.
     *
     * @return the logger for this {@link TacticalAPI} instance
     */
    @NotNull
    Logger getLogger();
}
