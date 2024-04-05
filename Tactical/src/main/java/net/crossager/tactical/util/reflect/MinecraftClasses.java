package net.crossager.tactical.util.reflect;

import java.util.List;

public class MinecraftClasses {
    private static final String MINECRAFT_PACKAGE = "net.minecraft";
    private static final CachedPackage MINECRAFT_CLASSES = new CachedPackage(MINECRAFT_PACKAGE);

    public static Class<?> getClass(String... paths) {
        return MINECRAFT_CLASSES.getPackageClass(paths);
    }

    public static Class<?> getClass(List<String> paths) {
        return MINECRAFT_CLASSES.getPackageClass(paths);
    }

    public static Class<?> getEnumProtocolClass() {
        return getClass("network.EnumProtocol", "network.ConnectionProtocol", "EnumProtocol");
    }

    public static Class<?> getEnumProtocolHandleClass() {
        return getEnumProtocolClass().getDeclaredClasses()[1];
    }

    public static Class<?> getEnumProtocolDirectionClass() {
        return getClass("network.protocol.EnumProtocolDirection", "EnumProtocolDirection");
    }

    public static Class<?> getPacketDataSerializerClass() {
        return getClass("network.PacketDataSerializer", "network.FriendlyByteBuf", "PacketDataSerializer");
    }

    public static Class<?> getEntityPlayerClass() {
        return getClass("server.level.EntityPlayer", "server.level.ServerPlayer", "EntityPlayer");
    }

    public static Class<?> getEntityClass() {
        return getClass("world.entity.Entity", "Entity");
    }

    public static Class<?> getPlayerConnectionClass() {
        return getClass("server.network.PlayerConnection", "server.network.ServerGamePacketListenerImpl", "PlayerConnection");
    }

    public static Class<?> getNetworkManagerClass() {
        return getClass("network.NetworkManager", "network.Connection", "NetworkManager");
    }

    public static Class<?> getPacketClass() {
        return getClass("network.protocol.Packet", "Packet");
    }

    public static Class<?> getPacketListenerClass() {
        return getClass("network.PacketListener", "PacketListener");
    }

    public static Class<?> getItemStackClass() {
        return getClass("world.item.ItemStack", "ItemStack");
    }

    public static Class<?> getChatSerializerClass() {
        return getClass("network.chat.IChatBaseComponent$ChatSerializer", "network.chat.Component$Serializer", "IChatBaseComponent$ChatSerializer");
    }
}
