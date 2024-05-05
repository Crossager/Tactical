package net.crossager.tactical.api.commands.argument;

import org.jetbrains.annotations.NotNull;

/**
 * An interface representing a precondition that an argument value must satisfy in order to be considered valid.
 */
public interface TacticalCommandArgumentPrecondition {
    /**
     * Validates the provided argument mapping against the precondition.
     * @param mapping the argument mapping to validate
     * @throws ArgumentPreconditionException if the precondition is not satisfied
     */
    void validate(@NotNull TacticalCommandArgumentMapping mapping);

    /**
     * Throws an {@link ArgumentPreconditionException} with the given message.
     *
     * @param message the message to include in the exception
     */
    static void throwException(@NotNull String message) {
        throw new ArgumentPreconditionException(message);
    }

    /**
     * Creates a precondition that checks if a string argument is not longer than a specified length.
     * @param length the maximum allowed length of the argument
     * @return a new precondition that checks the length of the argument
     */
    @NotNull
    static TacticalCommandArgumentPrecondition stringNotLongerThan(int length) {
        return mapping -> {
            if (mapping.asString().length() > length)
                throwException("Argument %s cannot be longer than " + length);
        };
    }

    /**
     * Creates a precondition that checks if a string argument is not shorter than a specified length.
     * @param length the minimum allowed length of the argument
     * @return a new precondition that checks the length of the argument
     */
    @NotNull
    static TacticalCommandArgumentPrecondition stringNotShorterThan(int length) {
        return mapping -> {
            if (mapping.asString().length() < length)
                throwException("Argument %s cannot be shorter than " + length);
        };
    }

    /**
     * Creates a precondition that checks if a numeric argument is not higher than a specified maximum value.
     * @param max the maximum allowed value of the argument
     * @return a new precondition that checks the value of the argument
     */
    @NotNull
    static TacticalCommandArgumentPrecondition notHigherThan(double max) {
        return mapping -> {
            if (mapping.asDouble() > max)
                throwException("Argument %s cannot be higher than " + max);
        };
    }

    /**
     * Creates a precondition that checks if an integer argument is not higher than a specified maximum value.
     * @param max the maximum allowed value of the argument
     * @return a new precondition that checks the value of the argument
     */
    @NotNull
    static TacticalCommandArgumentPrecondition notHigherThan(int max) {
        return mapping -> {
            if (mapping.asInt() > max)
                throwException("Argument %s cannot be higher than " + max);
        };
    }

    /**
     * Creates a precondition that checks if a long integer argument is not higher than a specified maximum value.
     * @param max the maximum allowed value of the argument
     * @return a new precondition that checks the value of the argument
     */
    @NotNull
    static TacticalCommandArgumentPrecondition notHigherThan(long max) {
        return mapping -> {
            if (mapping.asLong() > max)
                throwException("Argument %s cannot be higher than " + max);
        };
    }

    /**
     * Creates a precondition that checks if a numeric argument is not lower than a specified minimum value.
     * @param min the minimum allowed value of the argument
     * @return a new precondition that checks the value of the argument
     */
    @NotNull
    static TacticalCommandArgumentPrecondition notLowerThan(double min) {
        return mapping -> {
            if (mapping.asDouble() < min)
                throwException("Argument %s cannot be lower than " + min);
        };
    }

    /**
     * Creates a precondition that checks if a numeric argument is not lower than a specified minimum value.
     * @param min the minimum allowed value of the argument
     * @return a new precondition that checks the value of the argument
     */
    @NotNull
    static TacticalCommandArgumentPrecondition notLowerThan(int min) {
        return mapping -> {
            if (mapping.asInt() < min)
                throwException("Argument %s cannot be lower than " + min);
        };
    }

    /**
     * Creates a precondition that checks if a numeric argument is not lower than a specified minimum value.
     * @param min the minimum allowed value of the argument
     * @return a new precondition that checks the value of the argument
     */
    @NotNull
    static TacticalCommandArgumentPrecondition notLowerThan(long min) {
        return mapping -> {
            if (mapping.asLong() < min)
                throwException("Argument %s cannot be lower than " + min);
        };
    }
}
