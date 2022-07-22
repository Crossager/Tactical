package net.crossager.tactical.api.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TacticalUtils {
    private TacticalUtils() {}

    public static <T> T ifNullElse(T value, T ifNull) {
        return value == null ? ifNull : value;
    }

    @NotNull
    public static <T> Optional<T> getRegistration(Class<T> clazz) {
        RegisteredServiceProvider<T> registration = Bukkit.getServicesManager().getRegistration(clazz);
        if (registration == null) return Optional.empty();
        return Optional.of(registration.getProvider());
    }

    public boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ignored) {}
        return false;
    }

    public boolean isLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException ignored) {}
        return false;
    }

    public boolean isShort(String input) {
        try {
            Short.parseShort(input);
            return true;
        } catch (NumberFormatException ignored) {}
        return false;
    }

    public boolean isByte(String input) {
        try {
            Byte.parseByte(input);
            return true;
        } catch (NumberFormatException ignored) {}
        return false;
    }

    public boolean isFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException ignored) {}
        return false;
    }

    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException ignored) {}
        return false;
    }

    public static <I, O> List<O> convertList(Iterable<I> list, Function<I, O> function) {
        return convertList(ArrayList::new, list, function);
    }

    public static <I, O> List<O> convertList(Supplier<List<O>> listSupplier, Iterable<I> list, Function<I, O> function) {
        List<O> output = listSupplier.get();
        list.forEach(i -> output.add(function.apply(i)));
        return output;
    }

    public static <IK, OK, IV, OV> Map<OK, OV> convertMap(Map<IK, IV> originalMap, Function<IK, OK> keyFunction, Function<IV, OV> valueFunction) {
        Map<OK, OV> output = new HashMap<>();
        originalMap.forEach((ik, iv) -> output.put(keyFunction.apply(ik), valueFunction.apply(iv)));
        return Collections.unmodifiableMap(output);
    }

    public static <I, K, V> Map<K, V> toMap(Iterable<I> iterable, Function<I, K> keyFunction, Function<I, V> valueFunction) {
        Map<K, V> output = new HashMap<>();
        iterable.forEach(i -> output.put(keyFunction.apply(i), valueFunction.apply(i)));
        return Collections.unmodifiableMap(output);
    }

    public static <K, V, O> List<O> toList(Map<K, V> map, BiFunction<K, V, O> function) {
        List<O> output = new ArrayList<>();
        map.forEach((k, v) -> output.add(function.apply(k, v)));
        return Collections.unmodifiableList(output);
    }

    public static <I> List<I> toList(Iterable<I> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
