package net.crossager.tactical.api.commands.argument;

import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This interface represents a tab completer for a tactical command argument.
 * It provides a way to complete a partially typed argument value based on the user's input.
 */
@FunctionalInterface
public interface TacticalCommandArgumentTabCompleter {

    /**
     * A constant field that represents an empty tab completer.
     */
    TacticalCommandArgumentTabCompleter NONE = context -> List.of();

    /**
     * This method generates a list of tab completions for a given TacticalCommandArgumentActionContext. The returned list contains strings that represent possible completions for the argument represented by the context. The list may be empty if no completions are found.
     *
     * @param context the TacticalCommandArgumentActionContext for which to generate tab completions.
     * @return a list of possible tab completions for the argument represented by the context. The list may be empty if no completions are found.
     */
    @NotNull
    List<String> tabComplete(@NotNull TacticalCommandArgumentActionContext context);
}
