package net.crossager.tactical.api.commands.argument;

import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The TacticalCommandArgumentParser interface represents an object that can parse a given command argument and
 * convert it into an object of a certain type.
 * The parse method accepts a TacticalCommandArgumentActionContext object as its argument, which contains information
 * about the command, the sender, and the argument being parsed. The method returns an Object that represents the parsed
 * value or null if the argument cannot be parsed.
 */
public interface TacticalCommandArgumentParser {

    /**
     * Parses the given TacticalCommandArgumentActionContext and returns the resulting object or null if the argument
     * cannot be parsed.
     *
     * @param context The TacticalCommandArgumentActionContext object that contains information about the command, the
     *                sender, and the argument being parsed.
     * @return An Object representing the parsed value or null if the argument cannot be parsed.
     */
    @Nullable
    Object parse(@NotNull TacticalCommandArgumentActionContext context);
}