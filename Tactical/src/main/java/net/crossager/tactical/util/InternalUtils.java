package net.crossager.tactical.util;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.function.Supplier;

public class InternalUtils {
    public static <T> T cast(Object o, Class<T> clazz) {
        if (!clazz.isInstance(o)) Exceptions.notApplicableOfType(o.getClass(), clazz);
        return clazz.cast(o);
    }

    public static <T> T hasValue(T t, String name) {
        if (t == null) Exceptions.noValue(name);
        return t;
    }

    public static <T> T makeSureHasValue(T t) {
        if (t == null) Exceptions.noValue(Thread.currentThread().getStackTrace()[2].getMethodName());
        return t;
    }

    public static boolean isBootstrapped() {
        return Bukkit.getServer() != null;
    }

    public static <T> ArrayList<T> createFillList(int length, Supplier<T> supplier) {
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(supplier.get());
        }
        return list;
    }
}
