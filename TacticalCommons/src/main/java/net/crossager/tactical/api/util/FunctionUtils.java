package net.crossager.tactical.api.util;

import net.crossager.tactical.api.util.function.TriConsumer;

import java.util.function.*;

public class FunctionUtils {
    public static <V> Runnable withArg(Consumer<V> consumer, V arg){
        return () -> consumer.accept(arg);
    }
    public static <V, A> Consumer<V> withFirstArg(BiConsumer<A, V> consumer, A arg){
        return v -> consumer.accept(arg, v);
    }
    public static <V, A> Consumer<V> withSecondArg(BiConsumer<V, A> consumer, A arg){
        return v -> consumer.accept(v, arg);
    }
    public static <I, V, A> BiConsumer<I, V> withThirdArg(TriConsumer<I, V, A> consumer, A arg){
        return (i, v) -> consumer.accept(i, v, arg);
    }

    public static <A, I, O> Function<I, O> withFirstArgF(BiFunction<A, I, O> function, A arg){
        return i -> function.apply(arg, i);
    }
    public static <I, A, O> Function<I, O> withSecondArgF(BiFunction<I, A, O> function, A arg){
        return i -> function.apply(i, arg);
    }
    public static <V, A> Runnable withTwoArgs(BiConsumer<V, A> consumer, V arg1, A arg2){
        return () -> consumer.accept(arg1, arg2);
    }
    public static Consumer<Object> run(Runnable runnable){
        return o -> runnable.run();
    }

    public static <I, O> Consumer<I> applyAndThen(Function<I, O> in, Consumer<O> out){
        return i -> out.accept(in.apply(i));
    }
    public static <I, A, O> BiConsumer<I, A> applyAndThen(BiFunction<I, A, O> in, Consumer<O> out){
        return (i, a) -> out.accept(in.apply(i, a));
    }

    public static <I, I2, O, O2> BiConsumer<I, I2> apply2Before(BiConsumer<O, O2> consumer, Function<I, O> function1, Function<I2, O2> function2) {
        return (i, i2) -> consumer.accept(function1.apply(i), function2.apply(i2));
    }
    public static <I, O, O2> Consumer<I> applyBefore(BiConsumer<O, O2> consumer, Function<I, O> function1, Function<I, O2> function2) {
        return i -> consumer.accept(function1.apply(i), function2.apply(i));
    }
    public static <I> Supplier<I> supplier(I i) {
        return () -> i;
    }
}
