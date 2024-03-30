package net.crossager.tactical.commons;

import net.crossager.tactical.api.reflect.ConstructorInvoker;
import net.crossager.tactical.api.reflect.FieldAccessor;
import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.api.reflect.TacticalReflectionFactory;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SimpleTacticalReflectionFactory implements TacticalReflectionFactory {
    @Override
    public <T> FieldAccessor<T> createFieldAccessor(Class<T> valueClass, Class<?> targetClass, String fieldName) {
        Field field;
        try {
            field = targetClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (targetClass.getSuperclass() != null) return createFieldAccessor(valueClass, targetClass.getSuperclass(), fieldName);
            throw new RuntimeException(e);
        }
        field.setAccessible(true);
        return createFieldAccessor(field);
    }

    @Override
    public <T> MethodInvoker<T> createMethodInvoker(Class<T> valueClass, Class<?> targetClass, String methodName, Class<?>... args) {
        Method method;
        try {
            method = targetClass.getDeclaredMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            if (targetClass.getSuperclass() != null) return createMethodInvoker(valueClass, targetClass.getSuperclass(), methodName, args);
            throw new RuntimeException(e);
        }
        method.setAccessible(true);
        return createMethodInvoker(method);
    }

    @Override
    public <T> ConstructorInvoker<T> createConstructorInvoker(Class<T> targetClass, Class<?>... args) {
        Constructor<T> constructor;
        try {
            constructor = targetClass.getDeclaredConstructor(args);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        constructor.setAccessible(true);
        return createConstructorInvoker(constructor);
    }

    @Override
    public <T> MethodInvoker<T> createMethodInvoker(Method method) {
        method.setAccessible(true);
        return new MethodInvoker<>() {
            @SuppressWarnings("unchecked")
            @Override
            public T invoke(Object target, Object... args) {
                try {
                    return (T) method.invoke(target, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public T invoke(Object target, List<Object> args) {
                return invoke(target, args.toArray());
            }

            @Override
            public @NotNull Method method() {
                return method;
            }
        };
    }

    @Override
    public <T> FieldAccessor<T> createFieldAccessor(Field field) {
        field.setAccessible(true);
        return new FieldAccessor<>() {
            @SuppressWarnings("unchecked")
            @Override
            public T read(Object target) {
                try {
                    return (T) field.get(target);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void write(Object target, Object value) {
                try {
                    field.set(target, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public @NotNull Field field() {
                return field;
            }
        };
    }

    @Override
    public <T> ConstructorInvoker<T> createConstructorInvoker(Constructor<T> constructor) {
        constructor.setAccessible(true);
        return new ConstructorInvoker<>() {
            @Override
            public T construct(Object... args) {
                try {
                    return constructor.newInstance(args);
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public T construct(List<Object> args) {
                return construct(args.toArray());
            }

            @Override
            public @NotNull Constructor<T> constructor() {
                return constructor;
            }
        };
    }

    private <T> T getMember(Class<?> targetClass, String name, GetMember<T> function) {
        try {
            return function.get(targetClass, name);
        } catch (ReflectiveOperationException e) {
            if (targetClass.getSuperclass() != null) return getMember(targetClass, name, function);
            throw new RuntimeException(e);
        }
    }

    interface GetMember<T> {
        T get(Class<?> targetClass, String name) throws ReflectiveOperationException;
    }
}
