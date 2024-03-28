package net.crossager.tactical.api.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TacticalUtils {
    private TacticalUtils() {
        assert false;
    }

    public static <T> T ifNullElse(T value, T ifNull) {
        return value == null ? ifNull : value;
    }

    public static <I, O> O ifNullElseMap(I in, O def, Function<I, O> mapper) {
        return in == null ? def : mapper.apply(in);
    }

    /**
     * Returns an {@link Optional} containing the provider of the given service class registered with the Bukkit services manager,
     * or an empty {@link Optional} if the service is not registered.
     *
     * @param clazz the class of the service to retrieve
     * @param <T>   the type of the service to retrieve
     * @return an {@link Optional} containing the provider of the service registered with the Bukkit services manager,
     * or an empty {@link Optional} if the service is not registered
     */
    @NotNull
    public static <T> Optional<T> getRegistration(Class<T> clazz) {
        RegisteredServiceProvider<T> registration = Bukkit.getServicesManager().getRegistration(clazz);
        if (registration == null) return Optional.empty();
        return Optional.of(registration.getProvider());
    }

    public static <I, O> List<O> mapList(Iterable<I> list, Function<I, O> function) {
        return mapList(ArrayList::new, list, function);
    }

    public static <I, O> List<O> mapList(Supplier<List<O>> listSupplier, Iterable<I> list, Function<I, O> function) {
        List<O> output = listSupplier.get();
        list.forEach(FunctionUtils.applyAndThen(function, output::add));
        return output;
    }

    public static <IK, OK, IV, OV> Map<OK, OV> mapMap(Map<IK, IV> originalMap, Function<IK, OK> keyFunction, Function<IV, OV> valueFunction) {
        Map<OK, OV> output = new HashMap<>();
        originalMap.forEach(FunctionUtils.apply2Before(output::put, keyFunction, valueFunction));
        return Collections.unmodifiableMap(output);
    }

    public static <I, K, V> Map<K, V> toMap(Iterable<I> iterable, Function<I, K> keyFunction, Function<I, V> valueFunction) {
        Map<K, V> output = new HashMap<>();
        iterable.forEach(FunctionUtils.applyBefore(output::put, keyFunction, valueFunction));
        return Collections.unmodifiableMap(output);
    }

    public static <K, V, O> List<O> toList(Map<K, V> map, BiFunction<K, V, O> function) {
        List<O> output = new ArrayList<>();
        map.forEach(FunctionUtils.applyAndThen(function, output::add));
        return Collections.unmodifiableList(output);
    }

    public static <I> List<I> toList(Iterable<I> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> addTo(List<? extends T> list1, List<? extends T> list2) {
        if (list1.isEmpty()) return (List<T>) list2;
        if (list2.isEmpty()) return (List<T>) list1;
        List<T> newList = new ArrayList<>(list1);
        newList.addAll(list2);
        return Collections.unmodifiableList(newList);
    }

    public static <T, I extends T> List<T> addTo(I item, List<? extends T> list) {
        List<T> newList = new ArrayList<>(list);
        newList.add(item);
        return Collections.unmodifiableList(newList);
    }

    public static <T> List<T> unfoldList(Iterable<? extends Collection<T>> list) {
        return unfold(list, ArrayList::new);
    }

    public static <T, C extends Collection<T>> C unfold(Iterable<? extends Collection<T>> list, Supplier<C> collectionSupplier) {
        C c = collectionSupplier.get();
        list.forEach(c::addAll);
        return c;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> castClassGenerics(Class<?> cl)  {
        return (Class<T>) cl;
    }
}
