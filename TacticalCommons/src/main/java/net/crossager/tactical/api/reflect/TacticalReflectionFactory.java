package net.crossager.tactical.api.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface TacticalReflectionFactory {
    <T> FieldAccessor<T> createFieldAccessor(Class<T> valueClass, Class<?> targetClass, String fieldName);

    <T> MethodInvoker<T> createMethodInvoker(Class<T> valueClass, Class<?> targetClass, String methodName, Class<?>... args);

    <T> ConstructorInvoker<T> createConstructorInvoker(Class<T> targetClass, Class<?>... args);

    <T> MethodInvoker<T> createMethodInvoker(Method method);

    <T> FieldAccessor<T> createFieldAccessor(Field field);

    <T> ConstructorInvoker<T> createConstructorInvoker(Constructor<T> constructor);
}
