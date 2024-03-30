package net.crossager.tactical.util;

import java.io.File;
import java.io.IOException;

public class Checks {
    public static void notLonger(CharSequence str, int length, String name) {
        if (str.length() > length) throw new IllegalArgumentException(name + " cannot be longer than " + length + "(" + str + ")");
    }
    public static void notEmpty(String str, String name) {
        if (str == null || str.isEmpty() || str.isBlank()) throw new IllegalArgumentException(name + " cannot be empty (" + str + ")");
    }
    public static void canReadWrite(File file) throws IOException {
        if (!file.exists()) return;
        if (!file.canRead() || !file.canWrite()) throw new IOException("Cannot read or write to file!");
    }
    public static <T> T notNull(T object, String message) {
        if (object == null) throw new NullPointerException(message);
        return object;
    }
    public static <T> T notNull(T object) {
        if (object == null) throw new NullPointerException();
        return object;
    }
}
