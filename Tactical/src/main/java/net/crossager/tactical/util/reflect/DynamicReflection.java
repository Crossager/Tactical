package net.crossager.tactical.util.reflect;

import net.crossager.tactical.api.reflect.FieldAccessor;
import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.api.reflect.ReflectionUtils;
import net.crossager.tactical.util.Exceptions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class DynamicReflection {
    public static <T> FieldAccessor<T> getField(Class<?> source, Class<T> type) {
        return getField(source, type, 0, false);
    }
    public static <T> FieldAccessor<T> getField(Class<?> source, Class<T> type, boolean isStatic) {
        return getField(source, type, 0, isStatic);
    }

    public static <T> FieldAccessor<T> getField(Class<?> source, Class<T> type, int index, boolean isStatic) {
        for (Field field : source.getDeclaredFields()) {

            if (Modifier.isStatic(field.getModifiers()) != isStatic) continue;
            if (!type.isAssignableFrom(field.getType())) continue;
            if (index > 0) {
                index--;
                continue;
            }
            field.setAccessible(true);
            return ReflectionUtils.fromField(field);
        }
        if (source.getSuperclass() == null) return Exceptions.notFound("Field");
        return getField(source.getSuperclass(), type, index, isStatic);
    }

    public static <T> MethodInvoker<T> getMethodByArgs(Class<?> source, Class<?>... args) {
        for (Method method : source.getDeclaredMethods()) {
            Class<?>[] params = method.getParameterTypes();
            if (Arrays.equals(params, args)) {
                method.setAccessible(true);
                return ReflectionUtils.fromMethod(method);
            }
        }
        if (source.getSuperclass() == null) return Exceptions.notFound("Method");
        return getMethodByArgs(source.getSuperclass(), args);
    }

    public static <T> MethodInvoker<T> getMethodByReturnType(Class<?> source, Class<T> returnType) {
        for (Method method : source.getDeclaredMethods()) {
            if (method.getReturnType().isAssignableFrom(returnType)) {
                method.setAccessible(true);
                return ReflectionUtils.fromMethod(method);
            }
        }
        if (source.getSuperclass() == null) return Exceptions.notFound("Method");
        return getMethodByReturnType(source.getSuperclass(), returnType);
    }

    public static <T> MethodInvoker<T> getMethodByReturnTypeAndArgs(Class<?> source, Class<T> returnType, Class<?>... args) {
        for (Method method : source.getDeclaredMethods()) {
            Class<?>[] params = method.getParameterTypes();
            if (method.getReturnType().isAssignableFrom(returnType) && Arrays.equals(params, args)) {
                method.setAccessible(true);
                return ReflectionUtils.fromMethod(method);
            }
        }
        if (source.getSuperclass() == null) return Exceptions.notFound("Method");
        return getMethodByReturnTypeAndArgs(source.getSuperclass(), returnType, args);
    }
}
