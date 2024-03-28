package net.crossager.tactical.api.commands;

import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentFactory;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * The TacticalCommandFactory interface represents a factory that is used to create instances of TacticalCommand
 * and TacticalSubCommand objects.
 */
public interface TacticalCommandFactory {
    /**
     * Creates a new TacticalCommand instance with the given prefix and name.
     *
     * @param prefix the prefix for the command.
     * @param name the name for the command.
     * @return a new TacticalCommand instance.
     */
    @NotNull
    TacticalCommand createCommand(@NotNull String prefix, @NotNull String name);

    /**
     * Creates a new TacticalCommand instance with the given plugin prefix and name.
     *
     * @param plugin the plugin for the command.
     * @param name the name for the command.
     * @return a new TacticalCommand instance.
     */
    @NotNull
    TacticalCommand createCommand(@NotNull JavaPlugin plugin, @NotNull String name);

    /**
     * Creates a new TacticalCommand instance from an existing PluginCommand.
     *
     * @param pluginCommand the PluginCommand to create the TacticalCommand from.
     * @return a new TacticalCommand instance.
     */
    @NotNull
    TacticalCommand createCommand(@NotNull PluginCommand pluginCommand);

    /**
     * Creates a new TacticalSubCommand with the specified name.
     *
     * @param name the name of the subcommand
     * @return the created TacticalSubCommand object
     */
    @NotNull
    TacticalSubCommand createSubCommand(@NotNull String name);

    /**
     * Returns the instance of {@link TacticalCommandArgumentFactory}.
     *
     * @return The instance of the argument factory.
     */
    TacticalCommandArgumentFactory argumentFactory();
}