package net.crossager.tactical.commands.argument;

import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentTabCompletionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class SimpleLazyStringValuesArgument extends TacticalCommandArgumentBase {
    private final Supplier<List<String>> valuesGetter;
    private final boolean ensureInList;

    public SimpleLazyStringValuesArgument(String name, Supplier<List<String>> valuesGetter, boolean ensureInList) {
        this.valuesGetter = valuesGetter;
        this.ensureInList = ensureInList;
        name(name);
        shouldAutoFilterTabCompletion(false);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull TacticalCommandArgumentTabCompletionContext context) {
        return valuesGetter.get();
    }

    @Override
    public @Nullable Object parse(@NotNull TacticalCommandArgumentActionContext context) {
        if (ensureInList && !valuesGetter.get().contains(context.argument()))
            context.throwParseException(context.argument() + " is not a valid argument");
        return context.argument();
    }
}
