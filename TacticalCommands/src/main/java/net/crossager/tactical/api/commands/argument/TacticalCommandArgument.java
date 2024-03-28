package net.crossager.tactical.api.commands.argument;

import net.crossager.tactical.api.TacticalCommands;
import net.crossager.tactical.api.commands.TacticalBaseCommand;
import net.crossager.tactical.api.commands.TacticalSubCommand;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentTabCompletionContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * A {@link TacticalCommandArgument} represents an argument that can be used with a {@link TacticalBaseCommand}.
 * It defines various properties of the argument such as whether it is required, how many arguments
 * it should take, and how it should be parsed.
 */
public interface TacticalCommandArgument {
    /**
     * Provides a list of suggestions for tab completion given the current command input context.
     * @param context The context in which the tab completion is being requested
     * @return A list of possible suggestions for tab completion
     */
    @NotNull
    List<String> tabComplete(@NotNull TacticalCommandArgumentTabCompletionContext context);

    /**
     * Parses the given arguments into an object. This is called when a command is executed
     * with this argument.
     * @param context The context in which the command is being executed
     * @return The parsed object corresponding to the argument
     */
    @Nullable
    Object parse(@NotNull TacticalCommandArgumentActionContext context);

    /**
     * Gets the number of arguments that this argument should take.
     * Used for multi-argument arguments such as locations.
     * @return The number of arguments
     */
    int argumentLength();

    /**
     * Whether any argument length is allowed for this argument.
     * @return {@code true} if any argument length is allowed, {@code false} otherwise
     */
    boolean anyArgumentLength();

    /**
     * Whether the tab completion should filter automatically based on the player's current input.
     * @return {@code true} if tab completion should auto filter, {@code false} otherwise
     */
    boolean shouldAutoFilterTabCompletion();

    /**
     * Determines whether this argument is a subcommand.
     * @return {@code true} if this argument is a subcommand, {@code false} otherwise
     */
    boolean isSubCommand();

    /**
     * Gets a list of subcommands associated with this argument.
     * @throws java.util.NoSuchElementException if no subcommands are associated with this argument
     * @return A list of subcommands
     */
    @NotNull
    List<TacticalSubCommand> subCommands();

    /**
     * Sets whether the tab completion should filter automatically based on the player's current input.
     * @param shouldAutoFilterTabCompletion {@code true} if tab completion should auto filter, {@code false} otherwise
     * @return This {@link  TacticalCommandArgument} instance
     */
    @Contract("_ -> this")
    @NotNull
    TacticalCommandArgument shouldAutoFilterTabCompletion(boolean shouldAutoFilterTabCompletion);

    /**
     * Sets whether this argument is required.
     * @param required {@code true} if this argument is required, {@code false} otherwise
     * @return This TacticalCommandArgument instance
     */
    @NotNull
    @Contract("_ -> this")
    TacticalCommandArgument required(boolean required);

    /**
     * Whether this argument is required or not.
     * @return {@code true} if this argument is required, {@code false} otherwise
     */
    boolean isRequired();

    /**
     * Gets the name of this argument.
     * @return The name of the argument
     */
    @NotNull
    String name();

    /**
     * Gets the formatted name of this argument.
     * This will be {@code (name)} if the argument is required, and {@code [name]} if not
     *
     * @return The formatted name of the argument
     */
    @NotNull
    String formattedName();

    /**
     * Sets the name of this argument.
     * @param name The name to set
     * @return This TacticalCommandArgument instance
     */
    @NotNull
    @Contract("_ -> this")
    TacticalCommandArgument name(@NotNull String name);

    /**
     * Returns the list of preconditions that must be satisfied for this argument to be valid.
     * @return the list of preconditions
     */
    @NotNull
    List<TacticalCommandArgumentPrecondition> preconditions();

    /**
     * Adds a new precondition to this argument.
     * @param precondition the precondition to add
     * @return this argument instance with the new precondition added
     * @throws NullPointerException if the precondition is null
     */
    @Contract("_ -> this")
    @NotNull
    TacticalCommandArgument addPrecondition(@NotNull TacticalCommandArgumentPrecondition precondition);

    /**
     * Creates a TacticalCommandArgument representing a string argument that accepts a list of possible values.
     *
     * @param name The name of the argument.
     * @param values The list of possible values for the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument stringValues(@NotNull String name, @NotNull List<String> values) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().stringValues(name, values);
    }

    /**
     * Creates a TacticalCommandArgument representing a material argument.
     *
     * @param name The name of the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument material(@NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().material(name);
    }

    /**
     * Creates a TacticalCommandArgument representing a player argument.
     *
     * @param name The name of the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument player(@NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().player(name);
    }

    /**
     * Creates a TacticalCommandArgument representing an integer argument.
     *
     * @param name The name of the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument integer(@NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().integer(name);
    }

    /**
     * Creates a TacticalCommandArgument representing a boolean argument.
     *
     * @param name The name of the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument bool(@NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().bool(name);
    }

    /**
     * Creates a TacticalCommandArgument representing a decimal argument.
     *
     * @param name The name of the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument decimal(@NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().decimal(name);
    }

    /**
     * Creates a TacticalCommandArgument representing a string argument.
     *
     * @param name The name of the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument string(@NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().string(name);
    }

    /**
     * Creates a TacticalCommandArgument representing a message argument. This can be useful when making message commands
     *
     * @param name The name of the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument message(@NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().message(name);
    }

    /**
     * Creates a TacticalCommandArgument representing a location argument with default decimal precision of 2.
     *
     * @param name The name of the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument location(@NotNull String name) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().location(name, 2);
    }

    /**
     * Creates a TacticalCommandArgument representing a location argument with a custom decimal precision.
     *
     * @param name The name of the argument.
     * @param decimals The number of decimal places to round the coordinates to.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument location(@NotNull String name, int decimals) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().location(name, decimals);
    }

    /**
     * Creates a TacticalCommandArgument representing a set of sub-commands.
     *
     * @param subCommands The list of sub-commands to include in the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument subCommands(@NotNull List<TacticalSubCommand> subCommands) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().subCommands(subCommands);
    }

    /**
     * Creates a TacticalCommandArgument representing a set of sub-commands.
     *
     * @param subCommands The array of sub-commands to include in the argument.
     * @return The created TacticalCommandArgument instance.
     */
    static TacticalCommandArgument subCommands(@NotNull TacticalSubCommand... subCommands) {
        return subCommands(Arrays.asList(subCommands));
    }

    /**
     * Merges a list of TacticalCommandArgument instances into a single instance.
     *
     * @param arguments The list of TacticalCommandArgument instances to merge.
     * @return The merged TacticalCommandArgument instance.
     */
    static TacticalCommandArgument merge(@NotNull List<TacticalCommandArgument> arguments) {
        return TacticalCommands.getInstance().getCommandFactory().argumentFactory().merge(arguments);
    }

    /**
     * Merges an array of TacticalCommandArgument instances into a single instance.
     *
     * @param arguments The array of TacticalCommandArgument instances to merge.
     * @return The merged TacticalCommandArgument instance.
     */
    static TacticalCommandArgument merge(@NotNull TacticalCommandArgument... arguments) {
        return merge(Arrays.asList(arguments));
    }
}
