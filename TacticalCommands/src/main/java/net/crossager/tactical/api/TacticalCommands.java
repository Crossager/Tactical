package net.crossager.tactical.api;

import net.crossager.tactical.api.commands.TacticalCommand;
import net.crossager.tactical.api.commands.TacticalCommandFactory;
import org.jetbrains.annotations.NotNull;
/**
 * Provides methods for registering commands and accessing the {@link TacticalCommandFactory}.
 */
public interface TacticalCommands {
    /**
     * Registers a new {@link TacticalCommand} with this {@link TacticalCommands} instance.
     *
     * @param command the command to register
     * @return this {@link TacticalCommands} instance for method chaining
     * @throws NullPointerException if {@code command} is {@code null}
     */
    @NotNull
    TacticalCommands registerCommand(@NotNull TacticalCommand command);

    /**
     * Returns the {@link TacticalCommandFactory} associated with this {@link TacticalCommands} instance.
     *
     * @return the {@link TacticalCommandFactory} associated with this {@link TacticalCommands} instance
     */
    @NotNull
    TacticalCommandFactory getCommandFactory();

    /**
     * Returns the singleton instance of the {@link TacticalCommands} class.
     *
     * @return the singleton instance of the {@link TacticalCommands} class
     */
    @NotNull
    static TacticalCommands getInstance() {
        return CommandAPIHolder.getInstance();
    }
}
