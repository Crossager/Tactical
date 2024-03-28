package net.crossager.tactical.api.commands;

import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;

import java.util.List;
import java.util.NoSuchElementException;

public class InvalidArgumentException extends RuntimeException {
    private final int argumentIndex;
    private final TacticalCommandArgument argument;
    private final List<String> input;
    private final boolean isParseException;

    public InvalidArgumentException(int argumentIndex, String message, TacticalCommandArgument argument, List<String> input, Exception cause, boolean isParseException) {
        super(message, cause);
        this.argumentIndex = argumentIndex;
        this.argument = argument;
        this.input = input;
        this.isParseException = isParseException;
    }

    public InvalidArgumentException(int argumentIndex, String message, TacticalCommandArgument argument, List<String> input, Exception cause) {
        this(argumentIndex, message, argument, input, cause, true);
    }

    public InvalidArgumentException(int argumentIndex, String message) {
        this(argumentIndex, message, null, null, null, false);
    }

    public int argumentIndex() {
        return argumentIndex;
    }

    public List<String> input() {
        if (input == null) throw new NoSuchElementException("No input");
        return input;
    }

    public TacticalCommandArgument argument() {
        if (argument == null) throw new NoSuchElementException("No argument");
        return argument;
    }

    public boolean isParseException() {
        return isParseException;
    }

    public static InvalidArgumentException parseException(int argumentIndex, TacticalCommandArgument argument, List<String> input) {
        return new InvalidArgumentException(argumentIndex, null, argument, input, null, true);
    }
}