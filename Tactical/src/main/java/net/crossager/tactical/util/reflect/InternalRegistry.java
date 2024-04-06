package net.crossager.tactical.util.reflect;

import net.crossager.tactical.api.reflect.MethodInvoker;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class InternalRegistry {
    private static final MethodInvoker<?> NEW_MINECRAFT_KEY = DynamicReflection.getMethodByReturnTypeAndArgs(MinecraftClasses.getMinecraftKeyClass(), MinecraftClasses.getMinecraftKeyClass(), String.class, String.class);
    private static final MethodInvoker<Object> GET_BY_MINECRAFT_KEY = DynamicReflection.getMethodByReturnTypeAndArgs(MinecraftClasses.getIRegistryClass(), Object.class, MinecraftClasses.getMinecraftKeyClass());
    private static final MethodInvoker<Integer> GET_ID = DynamicReflection.getMethodByReturnType(MinecraftClasses.getIRegistryClass(), Integer.TYPE);

    public static final Map<Class<?>, InternalRegistry> BY_PARAM_CLASS;

    public static final InternalRegistry ENTITY_TYPES;

    static {
        try {
            BY_PARAM_CLASS = new HashMap<>();
            Field[] fields = MinecraftClasses.getBuiltInRegistriesClass().getFields();
            Class<?> iRegistryClass = MinecraftClasses.getIRegistryClass();

            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) continue;
                if (!iRegistryClass.isAssignableFrom(field.getType())) continue;
                if (!(field.getGenericType() instanceof ParameterizedType type)) continue;
                Type registryType = type.getActualTypeArguments()[0];
                Class<?> registryTypeAsClass;
                if (registryType instanceof ParameterizedType parameterizedType) {
                    registryTypeAsClass = (Class<?>) parameterizedType.getRawType();
                } else if (registryType instanceof Class<?> cls) {
                    registryTypeAsClass = cls;
                } else {
                    // Skip other registries
                    continue;
                }
                Object registry = field.get(null);
                BY_PARAM_CLASS.put(registryTypeAsClass, new InternalRegistry(registry));
            }
            ENTITY_TYPES = BY_PARAM_CLASS.get(MinecraftClasses.getEntityTypesClass());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final Object registry;

    private InternalRegistry(Object registry) {
        this.registry = registry;
    }

    public int getIdByInternal(Object internal) {
        return GET_ID.invoke(registry, internal);
    }

    public Object getInternalByKey(NamespacedKey key) {
        return GET_BY_MINECRAFT_KEY.invoke(registry, NEW_MINECRAFT_KEY.invoke(null, key.getNamespace(), key.getKey()));
    }

    public int getIdByKey(NamespacedKey key) {
        return getIdByInternal(getInternalByKey(key));
    }
}
