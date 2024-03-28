package net.crossager.tactical.api.commands;

import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**

 The base interface for TacticalCommands. Provides methods for adding arguments, getting and setting the command executor,

 and retrieving the list of current arguments.
 */
public interface TacticalBaseCommand {

    /**
     * Adds a {@link TacticalCommandArgument} to the command.
     * @param argument the {@link TacticalCommandArgument} to add.
     * @return the {@link TacticalBaseCommand} instance.
     */
    @NotNull
    @Contract("_ -> this")
    TacticalBaseCommand addArgument(@NotNull TacticalCommandArgument argument);

    /**
     * Returns the list of {@link TacticalCommandArgument} that have been added to the command.
     * @return the list of {@link TacticalCommandArgument}.
     */
    @NotNull
    List<TacticalCommandArgument> arguments();

    /**
     * Returns the {@link TacticalCommandExecutor} that has been set for the command.
     * @return the {@link TacticalCommandExecutor}.
     */
    @NotNull
    TacticalCommandExecutor commandExecutor();

    /**
     * Sets the {@link TacticalCommandExecutor} for the command.
     * @param commandExecutor the {@link TacticalCommandExecutor} to set.
     * @return the {@link TacticalBaseCommand} instance.
     */
    @NotNull
    @Contract("_ -> this")
    TacticalBaseCommand commandExecutor(@NotNull TacticalCommandExecutor commandExecutor);
}