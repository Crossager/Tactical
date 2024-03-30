package net.crossager.tactical.util;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.function.Function;

public class BaseAdapterFactory implements TypeAdapterFactory {
    private final Class<?> type;
    private final Function<Gson, TypeAdapter<?>> adapter;

    public BaseAdapterFactory(Class<?> type, Function<Gson, TypeAdapter<?>> adapter) {
        this.type = type;
        this.adapter = adapter;
    }

    public BaseAdapterFactory(Class<?> type, TypeAdapter<?> adapter) {
        this(type, gson -> adapter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (this.type.isAssignableFrom(type.getRawType())) return (TypeAdapter<T>) adapter.apply(gson);
        return null;
    }
}
