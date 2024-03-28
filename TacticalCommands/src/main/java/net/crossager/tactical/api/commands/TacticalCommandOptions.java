package net.crossager.tactical.api.commands;

import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents some further information for a TacticalCommand.
 */
public interface TacticalCommandOptions {
    /**
     * Gets the command associated with these options.
     *
     * @return the command associated with these options.
     */
    @NotNull
    TacticalCommand command();

    /**
     * Adds an alias for the command associated with these options.
     *
     * @param alias the alias to add.
     * @return these options.
     */
    @NotNull
    @Contract("_ -> this")
    TacticalCommandOptions addAlias(@NotNull String alias);

    /**
     * Removes an alias for the command associated with these options.
     *
     * @param alias the alias to remove.
     * @return these options.
     */
    @NotNull
    @Contract("_ -> this")
    TacticalCommandOptions removeAlias(@NotNull String alias);

    /**
     * Gets a list of aliases for the command associated with these options.
     *
     * @return a list of aliases for the command associated with these options.
     */
    @NotNull
    List<String> aliases();

    /**
     * Sets the name of the command associated with these options.
     *
     * @param name the name to set.
     * @return these options.
     */
    @NotNull
    @Contract("_ -> this")
    TacticalCommandOptions name(@NotNull String name);

    /**
     * Gets the name of the command associated with these options.
     *
     * @return the name of the command associated with these options.
     */
    @NotNull
    String name();

    /**
     * Sets the permission required to execute the command associated with these options.
     *
     * @param permission the permission to set.
     * @return these options.
     */
    @NotNull
    @Contract("_ -> this")
    TacticalCommandOptions permission(String permission);

    /**
     * Gets the permission required to execute the command associated with these options.
     *
     * @return the permission required to execute the command associated with these options.
     */
    @NotNull
    String permission();

    /**
     * Sets whether the command associated with these options can only be executed by a player.
     *
     * @param playerOnly true if the command can only be executed by a player, false otherwise.
     * @return these options.
     */
    @NotNull
    @Contract("_ -> this")
    TacticalCommandOptions playerOnly(boolean playerOnly);

    /**
     * Checks whether the command associated with these options can only be executed by a player.
     *
     * @return true if the command can only be executed by a player, false otherwise.
     */
    boolean isPlayerOnly();

    /**
     * Sets the description of the command associated with these options.
     *
     * @param description the description to set.
     * @return these options.
     */
    @NotNull
    @Contract("_ -> this")
    TacticalCommandOptions description(@NotNull String description);

    /**
     * Gets the description of the command associated with these options.
     *
     * @return the description of the command associated with these options.
     */
    @NotNull
    String description();

    /**
     * Sets the usage message of the command associated with these options.
     *
     * @param usage the usage message to set.
     * @return these options.
     */
    @NotNull
    @Contract("_ -> this")
    TacticalCommandOptions usage(@NotNull String usage);

    /**
     * Gets the usage message of the command associated with these options.
     *
     * @return the usage message of the command associated with these options.
     */
    @NotNull
    String usage();

    /**
     * Gets the prefix for the command.
     * @return the prefix for the command
     */
    @NotNull
    String prefix();

    /**
     * Gets the plugin command associated with this TacticalCommand, if any.
     * @return the plugin command associated with this TacticalCommand, or null if there is none
     */
    @NotNull
    PluginCommand pluginCommand();

    /**
     * Checks whether this TacticalCommand has a plugin command associated with it.
     * @return true if this TacticalCommand has a plugin command associated with it, false otherwise
     */
    boolean hasPluginCommand();

    /**
     * Sets the error handler for this command.
     * @param errorHandler the error handler to set
     * @return this TacticalCommandOptions instance
     */
    @NotNull
    TacticalCommandOptions errorHandler(@NotNull TacticalCommandInvalidArgumentHandler errorHandler);

    /**
     * Gets the error handler for this command.
     * @return the error handler for this command
     */
    @NotNull
    TacticalCommandInvalidArgumentHandler errorHandler();

    /**
     * Gets the tab completion strategy for this command.
     * @return the tab completion strategy for this command
     */
    @NotNull
    TabCompletionStrategy tabCompletionStrategy();

    /**
     * Sets the tab completion strategy for this command.
     * @param tabCompletionStrategy the tab completion strategy to set
     * @return this TacticalCommandOptions instance
     */
    @NotNull
    TacticalCommandOptions tabCompletionStrategy(@NotNull TabCompletionStrategy tabCompletionStrategy);

}
