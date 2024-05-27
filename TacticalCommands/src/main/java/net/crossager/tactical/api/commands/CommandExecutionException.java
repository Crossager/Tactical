package net.crossager.tactical.api.commands;

/**
 * Cancels all execution of the command, and sends a message to the commandsender
 */
public class CommandExecutionException extends RuntimeException {
    public CommandExecutionException(String message) {
        super(message);
    }
}
