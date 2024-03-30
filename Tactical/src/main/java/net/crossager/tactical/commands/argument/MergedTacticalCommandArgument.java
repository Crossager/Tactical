package net.crossager.tactical.commands.argument;

import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentTabCompletionContext;
import net.crossager.tactical.api.util.TacticalUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class MergedTacticalCommandArgument extends TacticalCommandArgumentBase {
    private final List<TacticalCommandArgument> arguments;

    public MergedTacticalCommandArgument(List<TacticalCommandArgument> arguments) {
        this.arguments = arguments;
        boolean isRequired = arguments.get(0).isRequired();
        if (this.arguments.stream().anyMatch(argument -> argument.isRequired() != isRequired))
            throw new IllegalArgumentException("Merged arguments cannot have mixed 'isRequired'");
        required(isRequired);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull TacticalCommandArgumentTabCompletionContext context) {
        return TacticalUtils.unfold(arguments.stream().map(argument -> argument.tabComplete(context)).toList(), HashSet::new).stream().toList();
    }

    @Override
    public Object parse(@NotNull TacticalCommandArgumentActionContext context) {
        for (TacticalCommandArgument argument : arguments) {
            try {
                Object parsed = argument.parse(context);
                if (parsed != null) return new MergedArgumentMapping(argument, parsed);
            } catch (Exception ignored) {}
        }
        return null;
    }

    public List<TacticalCommandArgument> arguments() {
        return arguments;
    }

    @Override
    public @NotNull String name() {
        return arguments.stream().map(TacticalCommandArgument::name).collect(Collectors.joining(" | "));
    }
}
