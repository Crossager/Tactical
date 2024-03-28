package net.crossager.tactical.api.commands.argument;

import net.crossager.tactical.api.commands.TacticalSubCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
/**
 * The factory to create TacticalCommandArgument objects.
 */
public interface TacticalCommandArgumentFactory {

    /**
     * Create a TacticalCommandArgument with given name, tab completer and parser.
     *
     * @param name the name of the argument
     * @param tabCompleter the tab completer to provide tab completions for the argument
     * @param parser the parser to parse the argument input into an object
     * @return the created TacticalCommandArgument object
     */
    @NotNull
    TacticalCommandArgument create(@NotNull String name, @NotNull TacticalCommandArgumentTabCompleter tabCompleter, @NotNull TacticalCommandArgumentParser parser);

    /**
     * Create a TacticalCommandArgument with given name and value supplier.
     *
     * @param name the name of the argument
     * @param valueSupplier the supplier that returns a list of possible values for the argument
     * @param toStringFunction the function to convert a value to its string representation
     * @param fromStringFunction the function to convert a string representation to its object value
     * @param <T> the type of values for the argument
     * @return the created TacticalCommandArgument object
     */
    @NotNull
    <T> TacticalCommandArgument createValues(@NotNull String name, @NotNull Supplier<List<? extends T>> valueSupplier, @NotNull Function<T, String> toStringFunction, @NotNull Function<String, T> fromStringFunction);

    /**
     * Create a TacticalCommandArgument with given name and list of string values.
     *
     * @param name the name of the argument
     * @param values the list of string values for the argument
     * @return the created TacticalCommandArgument object
     */
    @NotNull
    TacticalCommandArgument stringValues(@NotNull String name, @NotNull List<String> values);

    /**
     * Create a TacticalCommandArgument with given name for a material input.
     *
     * @param name the name of the argument
     * @return the created TacticalCommandArgument object
     */
    @NotNull
    TacticalCommandArgument material(@NotNull String name);

    /**
     * Create a TacticalCommandArgument with given name for a player input.
     *
     * @param name the name of the argument
     * @return the created TacticalCommandArgument object
     */
    @NotNull
    TacticalCommandArgument player(@NotNull String name);

    /**
     * Create a TacticalCommandArgument with given name for an integer input.
     *
     * @param name the name of the argument
     * @return the created TacticalCommandArgument object
     */
    @NotNull
    TacticalCommandArgument integer(@NotNull String name);

    /**
     * Create a TacticalCommandArgument with given name for a boolean input.
     *
     * @param name the name of the argument
     * @return the created TacticalCommandArgument object
     */
    @NotNull
    TacticalCommandArgument bool(@NotNull String name);

    /**
     * Create a TacticalCommandArgument with given name for a decimal number input.
     *
     * @param name the name of the argument
     * @return the created TacticalCommandArgument object
     */
    @NotNull
    TacticalCommandArgument decimal(@NotNull String name);

    /**
     * Create a TacticalCommandArgument with given name for a string input.
     *
     * @param name the name of the argument
     * @return the created TacticalCommandArgument object
     */
    @NotNull
    TacticalCommandArgument string(@NotNull String name);

    /**
     * Create a TacticalCommandArgument with given name for a message input.
     *
     * @param name the name of the argument
     * @return the created TacticalCommandArgument object
     */
    @NotNull
    TacticalCommandArgument message(@NotNull String name);

    /**
     * Creates a location argument with the specified name and number of decimals.
     *
     * @param name the name of the argument
     * @param decimals the number of decimals to use for coordinates
     * @return the created location argument
     */
    @NotNull
    TacticalCommandArgument location(@NotNull String name, int decimals);

    /**
     * Creates a sub-commands argument with the specified sub-commands.
     *
     * @param subCommands the sub-commands for the argument
     * @return the created sub-commands argument
     */
    TacticalCommandArgument subCommands(@NotNull List<TacticalSubCommand> subCommands);

    /**
     * Merges the specified arguments into a single argument.
     *
     * @param arguments the arguments to merge
     * @return the merged argument
     */
    @NotNull
    TacticalCommandArgument merge(@NotNull List<TacticalCommandArgument> arguments);
}
