package net.crossager.tactical.api.commands;

/**

 This enum represents different strategies for tab completion in a command.
 */
public enum TabCompletionStrategy {
    /**
     * Indicates that no tab completion should be done for the command.
     */
    NONE,

    /**
     * Indicates that tab completion should be done using Bukkit's default tab completion strategy.
     */
    BUKKIT,

    /**
     * Indicates that tab completion should be done using Tactical's custom tab completion strategy.
     */
    TACTICAL
}