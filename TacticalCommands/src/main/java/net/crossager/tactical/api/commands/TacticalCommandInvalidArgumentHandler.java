package net.crossager.tactical.api.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * A functional interface for handling invalid command arguments.
 */
@FunctionalInterface
public interface TacticalCommandInvalidArgumentHandler {
    /**
     * Handles an {@link InvalidArgumentException} that was thrown by a command execution.
     * @param exception the exception that was thrown
     * @param sender the sender who executed the command
     * @param args the arguments passed to the command
     * @param label the label used to execute the command
     */
    void handle(@NotNull InvalidArgumentException exception, @NotNull CommandSender sender,
                @NotNull String[] args, @NotNull String label);
}