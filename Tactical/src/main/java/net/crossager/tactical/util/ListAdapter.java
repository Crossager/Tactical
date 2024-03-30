package net.crossager.tactical.util;

import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter {
    public static <T> ObjectReadingTypeAdapter.FieldReader<List<T>> read(ObjectReadingTypeAdapter.FieldReader<T> reader) {
        return in -> {
            List<T> list = new ArrayList<>();
            in.beginArray();
            while (in.peek() != JsonToken.END_ARRAY) {
                list.add(reader.read(in));
            }
            in.endArray();
            return list;
        };
    }
    public static <T> void write(JsonWriter out, List<T> elements, ElementWriter<T> writer) throws IOException {
        out.beginArray();
        for (T element : elements) {
            writer.write(out, element);
        }
        out.endArray();
    }

    public interface ElementWriter<T> {
        void write(JsonWriter out, T value) throws IOException;
    }
}
