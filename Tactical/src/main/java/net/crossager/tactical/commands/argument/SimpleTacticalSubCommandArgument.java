package net.crossager.tactical.commands.argument;

import net.crossager.tactical.api.commands.TacticalSubCommand;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentTabCompletionContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class SimpleTacticalSubCommandArgument extends TacticalCommandArgumentBase {
    private final List<TacticalSubCommand> subCommands;

    public SimpleTacticalSubCommandArgument(List<TacticalSubCommand> subCommands) {
        this.subCommands = subCommands;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull TacticalCommandArgumentTabCompletionContext context) {
        return subCommands.stream().map(TacticalSubCommand::name).toList();
    }

    @Override
    public Object parse(@NotNull TacticalCommandArgumentActionContext context) {
        return subCommands.stream().filter(command -> command.name().equalsIgnoreCase(context.argument())).findAny().orElse(null);
    }

    @Override
    public @NotNull TacticalCommandArgument argumentLength(int argumentLength) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull TacticalCommandArgument anyArgumentLength(boolean anyArgumentLength) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSubCommand() {
        return true;
    }

    @Override
    public @NotNull List<TacticalSubCommand> subCommands() {
        return Collections.unmodifiableList(subCommands);
    }

    @Override
    public @NotNull String name() {
        return "";
    }

    @Override
    public @NotNull TacticalCommandArgument name(@NotNull String name) {
        throw new UnsupportedOperationException();
    }
}
