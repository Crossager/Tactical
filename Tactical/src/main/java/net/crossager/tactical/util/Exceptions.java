package net.crossager.tactical.util;

import java.util.NoSuchElementException;

public class Exceptions {
    public static <T> T nse(String message) {
        throw new NoSuchElementException(message);
    }

    public static <T> T notApplicableOfType(Class<?> actualType, Class<?> providedType) {
        return nse("Value of type %s, is not applicable to type %s".formatted(actualType.getName(), providedType.getName()));
    }

    public static <T> T notFound(String object) {
        return nse(object + " not found");
    }

    public static <T> T noValue(String value) {
        return nse("No value '" + value + "'");
    }

    public static <T> T noValue() {
        return nse("No value");
    }

    public static <T> T lazyNoValue() {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        if (methodName.startsWith("get")) methodName = methodName.substring(3);
        methodName = Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);
        return noValue(methodName);
    }
}
