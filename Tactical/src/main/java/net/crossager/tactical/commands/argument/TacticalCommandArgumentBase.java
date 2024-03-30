package net.crossager.tactical.commands.argument;

import net.crossager.tactical.api.commands.TacticalSubCommand;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentPrecondition;
import net.crossager.tactical.util.Exceptions;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public abstract class TacticalCommandArgumentBase implements TacticalCommandArgument {
    private String name = "";
    private boolean shouldAutoFilterTabCompletion = true;
    private int argumentLength = 1;
    private boolean required = true;
    private boolean anyArgumentLength = false;
    private final List<TacticalCommandArgumentPrecondition> preconditions = new LinkedList<>();

    @Override
    public int argumentLength() {
        return argumentLength;
    }

    public @NotNull TacticalCommandArgument argumentLength(int argumentLength) {
        this.argumentLength = argumentLength;
        return this;
    }

    @Override
    public boolean anyArgumentLength() {
        return anyArgumentLength;
    }

    public @NotNull TacticalCommandArgument anyArgumentLength(boolean anyArgumentLength) {
        this.anyArgumentLength = anyArgumentLength;
        return this;
    }

    @Override
    public boolean shouldAutoFilterTabCompletion() {
        return shouldAutoFilterTabCompletion;
    }

    @Override
    public boolean isSubCommand() {
        return false;
    }

    @Override
    public @NotNull List<TacticalSubCommand> subCommands() {
        return Exceptions.lazyNoValue();
    }

    @Override
    public @NotNull TacticalCommandArgument shouldAutoFilterTabCompletion(boolean shouldAutoFilterTabCompletion) {
        this.shouldAutoFilterTabCompletion = shouldAutoFilterTabCompletion;
        return this;
    }

    @Override
    public @NotNull TacticalCommandArgument required(boolean required) {
        this.required = required;
        return this;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull String formattedName() {
        if (required)
            return '(' + name + ')';
        return '[' + name + ']';
    }

    @Override
    public @NotNull TacticalCommandArgument name(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull List<TacticalCommandArgumentPrecondition> preconditions() {
        return preconditions;
    }

    @Override
    public @NotNull TacticalCommandArgument addPrecondition(@NotNull TacticalCommandArgumentPrecondition precondition) {
        preconditions.add(precondition);
        return this;
    }
}
