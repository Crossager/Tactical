package net.crossager.tactical.commands;

import net.crossager.tactical.api.commands.TabCompletionStrategy;
import net.crossager.tactical.api.commands.TacticalCommand;
import net.crossager.tactical.api.commands.TacticalCommandInvalidArgumentHandler;
import net.crossager.tactical.api.commands.TacticalCommandOptions;
import org.jetbrains.annotations.NotNull;

public abstract class BaseTacticalCommandOptions implements TacticalCommandOptions {
    private final TacticalCommand command;
    private boolean playerOnly = false;
    private TacticalCommandInvalidArgumentHandler errorHandler = DefaultInvalidArgumentHandler.getInstance();
    private TabCompletionStrategy tabCompletionStrategy = TabCompletionStrategy.TACTICAL;

    public BaseTacticalCommandOptions(TacticalCommand command) {
        this.command = command;
    }

    @Override
    public @NotNull TacticalCommand command() {
        return command;
    }

    @Override
    public @NotNull TacticalCommandOptions playerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
        return this;
    }

    @Override
    public boolean isPlayerOnly() {
        return playerOnly;
    }

    @Override
    public @NotNull TacticalCommandOptions errorHandler(@NotNull TacticalCommandInvalidArgumentHandler errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    @Override
    public @NotNull TacticalCommandInvalidArgumentHandler errorHandler() {
        return errorHandler;
    }

    @Override
    public @NotNull TabCompletionStrategy tabCompletionStrategy() {
        return tabCompletionStrategy;
    }

    @Override
    public @NotNull TacticalCommandOptions tabCompletionStrategy(@NotNull TabCompletionStrategy tabCompletionStrategy) {
        this.tabCompletionStrategy = tabCompletionStrategy;
        return this;
    }
}
