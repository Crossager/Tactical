package net.crossager.tactical.api.data;

import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Represents an object that can hold a value of a certain type, which may or may not be present.
 */
public interface TacticalValueHolder {

    /**
     * Returns whether this holder has a non-null value.
     *
     * @return true if a value is present, false otherwise
     */
    boolean isPresent();

    /**
     * Returns whether this holder has a null value.
     *
     * @return true if no value is present, false otherwise
     */
    boolean isEmpty();

    /**
     * Calls the specified consumer with the value held by this holder, if present.
     *
     * @param ifPresent the consumer to call if a value is present
     * @throws NullPointerException if the specified consumer is null
     */
    void ifPresent(@NotNull Consumer<Object> ifPresent);

    /**
     * Calls the specified consumer with the value held by this holder, cast to the specified type,
     * if present and assignable to that type.
     *
     * @param ifPresent the consumer to call if a value is present and assignable to the specified type
     * @param type the type to cast the value to before passing it to the consumer
     * @throws NullPointerException if either argument is null
     */
    <T> void ifPresent(@NotNull Consumer<T> ifPresent, @NotNull Class<T> type);

    /**
     * Returns the value held by this holder, if present.
     *
     * @return the value held by this holder
     * @throws NoSuchElementException if no value is present
     */
    @NotNull
    Object get();

    /**
     * Returns the value held by this holder, if present, or the specified default value otherwise.
     *
     * @param def the default value to return if no value is present
     * @return the value held by this holder, or the specified default value if no value is present
     */
    @Contract("!null -> !null")
    @Nullable
    Object get(@Nullable Object def);

    /**
     * Returns the value held by this holder, cast to the specified type, if present and assignable to that type.
     *
     * @param type the type to cast the value to before returning it
     * @return the value held by this holder, cast to the specified type
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to the specified type
     */
    @NotNull
    <T> T getAsType(@NotNull Class<T> type);

    /**
     * Returns the value held by this holder, cast to the specified type, if present and assignable to that type,
     * or the specified default value otherwise.
     *
     * @param type the type to cast the value to before returning it
     * @param def the default value to return if no value is present, or if the value is not assignable to the specified type
     * @return the value held by this holder, cast to the specified type, or the specified default value if no value is present or not assignable to that type
     */
    @Contract("_,!null -> !null")
    @Nullable
    <T> T getAsType(@NotNull Class<T> type, @Nullable T def);

    /**
     * Returns the value held by this holder as an integer, if present and assignable to that type.
     *
     * @return the value held by this holder as an integer
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to an integer
     */
    int asInt();
    /**
     * Returns the value held by this holder as a string, if present and assignable to that type.
     * @return the value held by this holder as a string
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to a string
     */
    String asString();

    /**
     * Returns the value held by this holder as a long, if present and assignable to that type.
     * @return the value held by this holder as a long
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to a long
     */
    long asLong();

    /**
     * Returns the value held by this holder as a short, if present and assignable to that type.
     * @return the value held by this holder as a short
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to a short
     */
    short asShort();

    /**
     * Returns the value held by this holder as a byte, if present and assignable to that type.
     * @return the value held by this holder as a byte
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to a byte
     */
    byte asByte();

    /**
     * Returns the value held by this holder as a float, if present and assignable to that type.
     * @return the value held by this holder as a float
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to a float
     */
    float asFloat();

    /**
     * Returns the value held by this holder as a double, if present and assignable to that type.
     * @return the value held by this holder as a double
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to a double
     */
    double asDouble();

    /**
     * Returns the value held by this holder as a boolean, if present and assignable to that type.
     * @return the value held by this holder as a boolean
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to a boolean
     */
    boolean asBoolean();

    /**
     * Returns the value held by this holder as a character, if present and assignable to that type.
     * @return the value held by this holder as a character
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to a character
     */
    char asCharacter();

    /**
     * Returns the value held by this holder as a number, if present and assignable to that type.
     * @return the value held by this holder as a number
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to a number
     */
    Number asNumber();

    /**
     * Returns the value held by this holder as an enum constant of the specified enum type, if present and assignable to that type.
     * @param enumClass the class object representing the enum type to which to convert the value
     * @return the value held by this holder as an enum constant of the specified enum type
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to the specified enum type
     */
    <T extends Enum<T>> T asEnum(Class<T> enumClass);

    /**
     * Returns the value held by this holder as a material, if present and assignable to that type.
     * @return the value held by this holder as a material
     * @throws NoSuchElementException if no value is present, or if the value is not assignable to a material
     */
    Material asMaterial();

    /**
     * Returns true if the value held by this holder is of the specified type.
     *
     * @param type the class representing the type to check against
     * @return true if the value held by this holder is of the specified type, false otherwise
     * @throws NullPointerException if the type argument is null
     */
    boolean isOfType(@NotNull Class<?> type);

    /**
     * Returns true if the value held by this holder is an integer.
     *
     * @return true if the value held by this holder is an integer, false otherwise
     */
    boolean isInt();

    /**
     * Returns true if the value held by this holder is a string.
     *
     * @return true if the value held by this holder is a string, false otherwise
     */
    boolean isString();

    /**
     * Returns true if the value held by this holder is a long.
     *
     * @return true if the value held by this holder is a long, false otherwise
     */
    boolean isLong();

    /**
     * Returns true if the value held by this holder is a short.
     *
     * @return true if the value held by this holder is a short, false otherwise
     */
    boolean isShort();

    /**
     * Returns true if the value held by this holder is a byte.
     *
     * @return true if the value held by this holder is a byte, false otherwise
     */
    boolean isByte();

    /**
     * Returns true if the value held by this holder is a float.
     *
     * @return true if the value held by this holder is a float, false otherwise
     */
    boolean isFloat();

    /**
     * Returns true if the value held by this holder is a double.
     *
     * @return true if the value held by this holder is a double, false otherwise
     */
    boolean isDouble();

    /**
     * Returns true if the value held by this holder is a boolean.
     *
     * @return true if the value held by this holder is a boolean, false otherwise
     */
    boolean isBoolean();

    /**
     * Returns true if the value held by this holder is a character.
     *
     * @return true if the value held by this holder is a character, false otherwise
     */
    boolean isCharacter();

    /**
     * Returns true if the value held by this holder is a number.
     *
     * @return true if the value held by this holder is a number, false otherwise
     */
    boolean isNumber();

    /**
     * Returns true if the value held by this holder is an enum of the specified type.
     *
     * @param enumClass the class representing the enum type to check against
     * @param <T>       the type of the enum
     * @return true if the value held by this holder is an enum of the specified type, false otherwise
     * @throws NullPointerException if the enumClass argument is null
     */
    <T extends Enum<T>> boolean isEnum(Class<T> enumClass);

    /**
     * Returns true if the value held by this holder is a Material enum.
     *
     * @return true if the value held by this holder is a Material enum, false otherwise
     */
    boolean isMaterial();
}
