package net.crossager.tactical.commands;

import net.crossager.tactical.api.commands.TacticalCommandExecutor;
import net.crossager.tactical.api.commands.TacticalSubCommand;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import org.jetbrains.annotations.NotNull;

public class SimpleTacticalSubCommand extends SimpleTacticalBaseCommand implements TacticalSubCommand {
    private final String name;

    public SimpleTacticalSubCommand(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull TacticalSubCommand addArgument(@NotNull TacticalCommandArgument argument) {
        return (TacticalSubCommand) super.addArgument(argument);
    }

    @Override
    public @NotNull TacticalSubCommand commandExecutor(@NotNull TacticalCommandExecutor commandExecutor) {
        return (TacticalSubCommand) super.commandExecutor(commandExecutor);
    }
}
