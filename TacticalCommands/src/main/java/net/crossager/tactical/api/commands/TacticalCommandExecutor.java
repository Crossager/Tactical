package net.crossager.tactical.api.commands;

import net.crossager.tactical.api.commands.context.TacticalCommandExecutionContext;
import org.jetbrains.annotations.NotNull;

/**
 * A functional interface used when executing a command.
 */
@FunctionalInterface
public interface TacticalCommandExecutor {
    /**
     A constant representing an empty executor.
     */
    TacticalCommandExecutor NONE = context -> {};

    /**
     Executes the command using the provided context.
     @param context the command execution context
     */
    void execute(@NotNull TacticalCommandExecutionContext context);
}