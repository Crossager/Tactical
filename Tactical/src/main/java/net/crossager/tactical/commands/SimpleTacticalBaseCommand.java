package net.crossager.tactical.commands;

import net.crossager.tactical.api.commands.TacticalBaseCommand;
import net.crossager.tactical.api.commands.TacticalCommand;
import net.crossager.tactical.api.commands.TacticalCommandExecutor;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SimpleTacticalBaseCommand implements TacticalBaseCommand {
    private final List<TacticalCommandArgument> arguments = new ArrayList<>();
    private TacticalCommandExecutor commandExecutor = TacticalCommandExecutor.NONE;

    @Override
    public @NotNull TacticalBaseCommand addArgument(@NotNull TacticalCommandArgument argument) {
        arguments.add(argument);
        return this;
    }

    @Override
    public @NotNull List<TacticalCommandArgument> arguments() {
        return Collections.unmodifiableList(arguments);
    }

    @Override
    public @NotNull TacticalCommandExecutor commandExecutor() {
        return commandExecutor;
    }

    @Override
    public @NotNull TacticalBaseCommand commandExecutor(@NotNull TacticalCommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        return this;
    }
}
