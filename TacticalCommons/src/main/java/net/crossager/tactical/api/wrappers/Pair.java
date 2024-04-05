package net.crossager.tactical.api.wrappers;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public record Pair<F, S>(F first, S second) {

    public Pair<S, F> swap() {
        return of(second, first);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof final Pair<?, ?> other)) {
            return false;
        }
        return Objects.equals(first, other.first) && Objects.equals(second, other.second);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(first, second);
    }

    public <F2> Pair<F2, S> mapFirst(final Function<? super F, ? extends F2> function) {
        return of(function.apply(first), second);
    }

    public <S2> Pair<F, S2> mapSecond(final Function<? super S, ? extends S2> function) {
        return of(first, function.apply(second));
    }

    public static <F, S> Pair<F, S> of(final F first, final S second) {
        return new Pair<>(first, second);
    }

    public static <F, S> Collector<Pair<F, S>, ?, Map<F, S>> toMap() {
        return Collectors.toMap(Pair::first, Pair::second);
    }
}
