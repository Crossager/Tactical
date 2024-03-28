package net.crossager.tactical.api.commands;

import net.crossager.tactical.api.TacticalCommands;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a sub-command of a parent command.
 */
public interface TacticalSubCommand extends TacticalBaseCommand {

    /**
     * Returns the name of this sub-command.
     *
     * @return the name of this sub-command
     */
    @NotNull
    String name();

    /**
     * Adds a TacticalCommandArgument to this sub-command.
     *
     * @param argument the TacticalCommandArgument to add to this sub-command
     * @return this sub-command instance
     */
    @NotNull
    TacticalSubCommand addArgument(@NotNull TacticalCommandArgument argument);

    /**
     * Sets the TacticalCommandExecutor for this sub-command.
     *
     * @param commandExecutor the TacticalCommandExecutor to set for this sub-command
     * @return this sub-command instance
     */
    @NotNull
    TacticalSubCommand commandExecutor(@NotNull TacticalCommandExecutor commandExecutor);

    /**
     * Creates a new TacticalSubCommand instance with the given name.
     *
     * @param name the name of the new sub-command
     * @return the new TacticalSubCommand instance
     */
    @NotNull
    static TacticalSubCommand create(@NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().createSubCommand(name);
    }
}