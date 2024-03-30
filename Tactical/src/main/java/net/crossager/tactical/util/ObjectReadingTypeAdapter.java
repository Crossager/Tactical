package net.crossager.tactical.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import sun.reflect.ReflectionFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class ObjectReadingTypeAdapter<T> extends TypeAdapter<T> {
    private static final List<NumberFieldSetter> NUMBER_FIELD_SETTERS = List.of(
            new NumberFieldSetter(Integer.TYPE, (field, instance, number) -> field.setInt(instance, number.intValue())),
            new NumberFieldSetter(Long.TYPE, (field, instance, number) -> field.setLong(instance, number.longValue())),
            new NumberFieldSetter(Double.TYPE, (field, instance, number) -> field.setDouble(instance, number.doubleValue())),
            new NumberFieldSetter(Float.TYPE, (field, instance, number) -> field.setFloat(instance, number.floatValue()))
    );
    private final Class<?> type;
    private final Map<String, FieldReader<?>> fieldReaders;
    private final Map<String, Function<T, Object>> defaultFields;
    private final Map<String, Field> fields = new HashMap<>();
    private final Constructor<?> constructor;

    public ObjectReadingTypeAdapter(Class<?> type, Map<String, FieldReader<?>> fieldReaders) {
        this(type, fieldReaders, Map.of());
    }

    public ObjectReadingTypeAdapter(Class<?> type, Map<String, FieldReader<?>> fieldReaders, Map<String, Function<T, Object>> defaultFields) {
        this.type = type;
        this.fieldReaders = fieldReaders;
        this.defaultFields = defaultFields;
        try {
            this.constructor = ReflectionFactory.getReflectionFactory()
                    .newConstructorForSerialization(type, Object.class.getDeclaredConstructor());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Field getField(String name) {
        return fields.computeIfAbsent(name, key -> {
            try {
                return type.getDeclaredField(key);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Tried to parse unknown field " + name, e);
            }
        });
    }

    private void setFieldValue(Object instance, Field field, Object value) throws ReflectiveOperationException {
        field.setAccessible(true);
        if (value instanceof Number number) {
            for (NumberFieldSetter numberFieldSetter : NUMBER_FIELD_SETTERS) {
                if (numberFieldSetter.trySet(field, instance, number)) break;
            }
        } else {
            field.set(instance, value);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            Object instance = type.cast(constructor.newInstance());
            in.beginObject();
            List<String> names = new ArrayList<>();
            while (in.peek() != JsonToken.END_OBJECT) {
                String name = in.nextName();
                if (!defaultFields.isEmpty()) names.add(name);
                Field field = getField(name);
                Object value = fieldReaders.get(name).read(in);
                setFieldValue(instance, field, value);
            }
            in.endObject();
            defaultFields.forEach((name, createDefault) -> {
              if (names.contains(name)) return;
              try {
                  setFieldValue(instance, getField(name), createDefault.apply((T) instance));
              } catch (ReflectiveOperationException e) {
                  throw new RuntimeException(e);
              }
            });
            return (T) instance;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public interface FieldReader<T> {
        T read(JsonReader in) throws IOException;
    }

    private record NumberFieldSetter(Class<?> primitiveClass, Setter setter) {
        public boolean trySet(Field field, Object instance, Number value) throws ReflectiveOperationException {
            if (field.getType() != primitiveClass) return false;
            setter.set(field, instance, value);
            return true;
        }
        private interface Setter {
            void set(Field field, Object instance, Number number) throws ReflectiveOperationException;
        }
    }
}
