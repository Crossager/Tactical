package net.crossager.tactical.api.reflect;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Represents a constructor invoker that can be used to construct objects of a particular type.
 *
 * @param <T> the type of object that this invoker can construct
 */
public interface ConstructorInvoker<T> {

    /**
     * Constructs an object of type {@code T} with the given arguments.
     *
     * @param args the arguments to pass to the constructor
     * @return the constructed object
     */
    T construct(Object... args);

    /**
     * Constructs an object of type {@code T} with the given arguments.
     *
     * @param args the arguments to pass to the constructor
     * @return the constructed object
     */
    T construct(List<Object> args);

    /**
     * Returns the constructor that this invoker represents.
     *
     * @return the constructor that this invoker represents
     */
    @NotNull
    Constructor<T> constructor();
}