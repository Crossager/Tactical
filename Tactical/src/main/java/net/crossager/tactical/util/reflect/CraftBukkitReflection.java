package net.crossager.tactical.util.reflect;

import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.util.InternalUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

import java.util.List;

public class CraftBukkitReflection {
    private static final String CRAFTBUKKIT_PACKAGE = InternalUtils.isBootstrapped() ? Bukkit.getServer().getClass().getPackageName() : "org.bukkit.craftbukkit.v1_19_R2";
    private static final CachedPackage CRAFTBUKKIT_CLASSES = new CachedPackage(CRAFTBUKKIT_PACKAGE);

    public static final MethodInvoker<SimpleCommandMap> GET_COMMAND_MAP =
            DynamicReflection.getMethodByReturnType(getCraftServerClass(), SimpleCommandMap.class);
    public static final MethodInvoker<?> GET_PLAYER_HANDLE =
            DynamicReflection.getMethodByReturnType(getCraftPlayerClass(), MinecraftClasses.getEntityPlayerClass());


    public static Class<?> getClass(String... paths) {
        return CRAFTBUKKIT_CLASSES.getPackageClass(paths);
    }

    public static Class<?> getClass(List<String> paths) {
        return CRAFTBUKKIT_CLASSES.getPackageClass(paths);
    }

    public static Class<?> getCraftPlayerClass() {
        return getClass("entity.CraftPlayer");
    }

    public static Class<?> getCraftItemStackClass() {
        return getClass("inventory.CraftItemStack");
    }

    public static Class<?> getCraftServerClass() {
        return getClass("CraftServer");
    }

    public static Object getPlayerHandle(Player player) {
        return GET_PLAYER_HANDLE.invoke(player);
    }
}
