package net.crossager.tactical.api.reflect;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Represents a method invoker that can be used to invoke a particular method on an object.
 *
 * @param <T> the return type of the method that this invoker can invoke
 */
public interface MethodInvoker<T> {

    /**
     * Invokes the method with the given arguments on the given target object.
     *
     * @param target the object to invoke the method on
     * @param args   the arguments to pass to the method
     * @return the return value of the method
     */
    @Contract(pure = true)
    T invoke(Object target, Object... args);

    /**
     * Invokes the method with the given arguments on the given target object.
     *
     * @param target the object to invoke the method on
     * @param args   the arguments to pass to the method
     * @return the return value of the method
     */
    @Contract(pure = true)
    T invoke(Object target, List<Object> args);

    /**
     * Returns the method that this invoker represents.
     *
     * @return the method that this invoker represents
     */
    @NotNull
    @Contract(pure = true)
    Method method();
}
