package net.crossager.tactical.commands;

import net.crossager.tactical.api.TacticalCommands;
import net.crossager.tactical.api.commands.TacticalCommand;
import net.crossager.tactical.api.commands.TacticalCommandOptions;
import net.crossager.tactical.api.commands.TacticalCommandExecutor;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

public class SimpleTacticalCommand extends SimpleTacticalBaseCommand implements TacticalCommand {
    private final TacticalCommandOptions details;

    public SimpleTacticalCommand(String prefix, String name) {
        this.details = new SimpleTacticalCommandOptions(prefix, this).name(name);
    }
    public SimpleTacticalCommand(PluginCommand command) {
        this.details = new SimplePluginCommandTacticalCommandOptions(command, this);
    }

    @Override
    public @NotNull TacticalCommand addArgument(@NotNull TacticalCommandArgument argument) {
        return (TacticalCommand) super.addArgument(argument);
    }

    @Override
    public @NotNull TacticalCommand commandExecutor(@NotNull TacticalCommandExecutor commandExecutor) {
        return (TacticalCommand) super.commandExecutor(commandExecutor);
    }

    @Override
    public @NotNull TacticalCommand register() {
        TacticalCommands.getInstance().registerCommand(this);
        return this;
    }

    @Override
    public @NotNull TacticalCommandOptions options() {
        return details;
    }
}
