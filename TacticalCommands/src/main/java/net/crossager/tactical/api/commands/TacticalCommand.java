package net.crossager.tactical.api.commands;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.crossager.tactical.api.TacticalCommands;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The TacticalCommand interface represents a command that can be registered to the server.
 *
 * <pre>{@code
 * TacticalCommand myCommand = TacticalCommand.create(this, "mycommand")
 *             .options()
 *                 .description("This is my command")
 *                 .usage("/mycommand <arg>")
 *                 .permission("myplugin.mycommand")
 *                 .command()
 *             .addArgument(TacticalCommandArgument.string("arg"))
 *             .commandExecutor(context -> {
 *                 String arg = context.argument("arg").asString();
 *                 context.sender().sendMessage("You provided the argument: " + arg);
 *             })
 *             .register();}</pre>
 */
public interface TacticalCommand extends TacticalBaseCommand {
    /**
     * Gets the options for this command.
     *
     * @return the options for this command.
     */
    @NotNull
    TacticalCommandOptions options();

    /**
     * Adds an argument to this command.
     *
     * @param argument the argument to add.
     * @return this {@link TacticalCommand} instance.
     */
    @Contract("_ -> this")
    @NotNull
    TacticalCommand addArgument(@NotNull TacticalCommandArgument argument);

    /**
     * Sets the executor for this command.
     *
     * @param commandExecutor the executor for this command.
     * @return this {@link TacticalCommand} instance.
     */
    @Contract("_ -> this")
    @NotNull
    TacticalCommand commandExecutor(@NotNull TacticalCommandExecutor commandExecutor);

    /**
     * Registers this command to the server.
     *
     * @return this {@link TacticalCommand} instance.
     */
    @CanIgnoreReturnValue
    @Contract(" -> this")
    @NotNull
    TacticalCommand register();

    /**
     * Creates a new TacticalCommand instance with the given prefix and name.
     *
     * @param prefix the prefix for the command.
     * @param name the name for the command.
     * @return a new TacticalCommand instance.
     */
    @NotNull
    static TacticalCommand create(@NotNull String prefix, @NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().createCommand(prefix, name);
    }

    /**
     * Creates a new TacticalCommand instance with the given plugin prefix and name.
     *
     * @param plugin the plugin for the command.
     * @param name the name for the command.
     * @return a new TacticalCommand instance.
     */
    @NotNull
    static TacticalCommand create(@NotNull JavaPlugin plugin, @NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().createCommand(plugin, name);
    }

    /**
     * Creates a new TacticalCommand instance from an existing PluginCommand.
     *
     * @param pluginCommand the PluginCommand to create the TacticalCommand from.
     * @return a new TacticalCommand instance.
     */
    @NotNull
    static TacticalCommand create(@NotNull PluginCommand pluginCommand) {
        return TacticalCommands.getInstance().getCommandFactory().createCommand(pluginCommand);
    }
}
