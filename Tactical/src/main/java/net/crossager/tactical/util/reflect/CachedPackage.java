package net.crossager.tactical.util.reflect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CachedPackage {
    private final Map<String, Class<?>> cachedClasses = new HashMap<>();
    private final String root;

    public CachedPackage(String root) {
        this.root = root;
    }

    public Class<?> getPackageClass(String... names) {
        for (String name : names) {
            try {
                return getPackageClass(name);
            } catch (NoSuchElementException ignored) {

            }
        }
        throw new NoSuchElementException("Class not found: " + Arrays.toString(names));
    }

    public Class<?> getPackageClass(Iterable<String> names) {
        for (String name : names) {
            try {
                return getPackageClass(name);
            } catch (NoSuchElementException ignored) {

            }
        }
        throw new NoSuchElementException("Class not found: " + names);
    }

    public Class<?> getPackageClass(String name) {
        return cachedClasses.computeIfAbsent(name, x -> {
            String fullName = root.isEmpty() ? name : root + "." + name;
            try {
                return Class.forName(fullName);
            } catch (ClassNotFoundException e) {
                throw new NoSuchElementException("Class not found: " + fullName);
            }
        });
    }
}
