package net.crossager.tactical.api.reflect;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * Represents a field accessor that can be used to get or set the value of a particular field.
 *
 * @param <T> the type of the field that this accessor can access
 */
public interface FieldAccessor<T> {

    /**
     * Gets the value of the field from the given object.
     *
     * @param target the object to get the field value from
     * @return the value of the field from the given object
     */
    @Contract(pure = true)
    T read(Object target);

    /**
     * Sets the value of the field on the given object.
     *
     * @param target the object to set the field value on
     * @param value  the value to set the field to
     */
    @Contract(mutates = "param1")
    void write(Object target, Object value);

    /**
     * Returns the field that this accessor represents.
     *
     * @return the field that this accessor represents
     */
    @NotNull
    @Contract(pure = true)
    Field field();
}
