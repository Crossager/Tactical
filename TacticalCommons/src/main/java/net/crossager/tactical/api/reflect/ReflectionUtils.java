package net.crossager.tactical.api.reflect;

import net.crossager.tactical.api.TacticalCommons;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * A utility class for performing reflection operations.
 */
public class ReflectionUtils {
    private ReflectionUtils() {
        assert false;
    }

    /**
     * Returns a {@link FieldAccessor} for the specified {@link Field}.
     *
     * @param field the field to create the accessor for
     * @param <T> the type of the field
     * @return a field accessor for the specified field
     */
    @NotNull
    public static <T> FieldAccessor<T> fromField(@NotNull Field field) {
        return TacticalCommons.getInstance().getReflectionFactory().createFieldAccessor(field);
    }

    /**
     * Returns a {@link MethodInvoker} for the specified {@link Method}.
     *
     * @param method the method to create the invoker for
     * @param <T> the return type of the method
     * @return a method invoker for the specified method
     */
    @NotNull
    public static <T> MethodInvoker<T> fromMethod(@NotNull Method method) {
        return TacticalCommons.getInstance().getReflectionFactory().createMethodInvoker(method);
    }
    /**
     * Returns a {@link ConstructorInvoker} for the specified {@link Constructor}.
     *
     * @param constructor the constructor to create the invoker for
     * @param <T> the type of the object created by the constructor
     * @return a constructor invoker for the specified constructor
     */
    @NotNull
    public static <T> ConstructorInvoker<T> fromConstructor(@NotNull Constructor<T> constructor) {
        return TacticalCommons.getInstance().getReflectionFactory().createConstructorInvoker(constructor);
    }

    /**
     * Returns a {@link FieldAccessor} for the specified field name on the specified class.
     *
     * @param targetClass the class containing the field
     * @param name the name of the field
     * @return a field accessor for the specified field name on the specified class
     */
    @NotNull
    public static FieldAccessor<Object> getField(@NotNull Class<?> targetClass, @NotNull String name) {
        return getField(Object.class, targetClass, name);
    }

    /**
     * Returns a {@link FieldAccessor} for the specified field name on the specified class and value type.
     *
     * @param valueClass the type of the field
     * @param targetClass the class containing the field
     * @param name the name of the field
     * @param <T> the type of the value
     * @return a field accessor for the specified field name on the specified class and value type
     */
    @NotNull
    public static <T> FieldAccessor<T> getField(@NotNull Class<T> valueClass, @NotNull Class<?> targetClass, @NotNull String name) {
        return TacticalCommons.getInstance().getReflectionFactory().createFieldAccessor(valueClass, targetClass, name);
    }

    /**
     * Returns a {@link MethodInvoker} for the specified method name and argument types on the specified class.
     *
     * @param targetClass the class containing the method
     * @param name the name of the method
     * @param args the argument types of the method
     * @return a method invoker for the specified method name and argument types on the specified class
     */
    @NotNull
    public static MethodInvoker<Object> getMethod(@NotNull Class<?> targetClass, @NotNull String name, @NotNull Class<?>... args) {
        return getMethod(Object.class, targetClass, name, args);
    }

    /**
     * Returns a MethodInvoker for the specified method in the target class.
     *
     * @param valueClass the return type of the method
     * @param targetClass the class that contains the method
     * @param name the name of the method
     * @param args the parameter types of the method
     * @return a MethodInvoker for the specified method
     * @throws NullPointerException if any of the parameters are null
     */
    public static <T> MethodInvoker<T> getMethod(@NotNull Class<T> valueClass, @NotNull Class<?> targetClass, @NotNull String name, @NotNull Class<?>... args) {
        return TacticalCommons.getInstance().getReflectionFactory().createMethodInvoker(valueClass, targetClass, name, args);
    }

    /**
     * Returns a ConstructorInvoker for the specified constructor in the target class.
     *
     * @param targetClass the class that contains the constructor
     * @param args the parameter types of the constructor
     * @return a ConstructorInvoker for the specified constructor
     * @throws NullPointerException if any of the parameters are null
     */
    public static <T> ConstructorInvoker<T> getConstructor(@NotNull Class<T> targetClass, @NotNull Class<?>... args) {
        return TacticalCommons.getInstance().getReflectionFactory().createConstructorInvoker(targetClass, args);
    }

    /**
     * Returns the class with the specified name.
     *
     * @param name the name of the class
     * @return the class with the specified name
     * @throws RuntimeException if the class is not found
     * @throws NullPointerException if the parameter is null
     */
    public static Class<?> getClass(@NotNull String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
