package net.crossager.tactical.commands.argument;

import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentParser;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentTabCompleter;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentTabCompletionContext;
import net.crossager.tactical.util.Checks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpleTacticalCommandArgument extends TacticalCommandArgumentBase {
    private final TacticalCommandArgumentTabCompleter tabCompleter;
    private final TacticalCommandArgumentParser parser;

    public SimpleTacticalCommandArgument(@NotNull String name, @NotNull TacticalCommandArgumentTabCompleter tabCompleter, @NotNull TacticalCommandArgumentParser parser) {
        Checks.notEmpty(name, "name");
        name(name);
        this.tabCompleter = tabCompleter;
        this.parser = parser;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull TacticalCommandArgumentTabCompletionContext context) {
        return tabCompleter.tabComplete(context);
    }

    @Override
    public Object parse(@NotNull TacticalCommandArgumentActionContext context) {
        return parser.parse(context);
    }
}
