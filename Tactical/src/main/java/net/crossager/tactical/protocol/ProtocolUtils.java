package net.crossager.tactical.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.crossager.tactical.api.data.LazyInitializedHashMap;
import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.reflect.ConstructorInvoker;
import net.crossager.tactical.api.reflect.FieldAccessor;
import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.api.reflect.ReflectionUtils;
import net.crossager.tactical.util.reflect.CraftBukkitReflection;
import net.crossager.tactical.util.reflect.DynamicReflection;
import net.crossager.tactical.util.reflect.MinecraftClasses;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ProtocolUtils {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final LazyInitializedHashMap<String, Enum<?>> GET_NMS_PROTOCOL = new LazyInitializedHashMap<>(string -> (Enum<?>) ReflectionUtils.getField(MinecraftClasses.getEnumProtocolClass(), string).read(null));
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final LazyInitializedHashMap<String, Enum<?>> GET_NMS_SENDER = new LazyInitializedHashMap<>(string -> (Enum<?>) ReflectionUtils.getField(MinecraftClasses.getEnumProtocolDirectionClass(), string).read(null));
    private static final ConstructorInvoker<?> CREATE_PACKETDATASERIALIZER = ReflectionUtils.getConstructor(MinecraftClasses.getPacketDataSerializerClass(), ByteBuf.class);
    private static final MethodInvoker<?> GET_PLAYER_HANDLE = ReflectionUtils.getMethod(CraftBukkitReflection.getCraftPlayerClass(), "getHandle");
    private static final FieldAccessor<?> PLAYER_CONNECTION = DynamicReflection.getField(MinecraftClasses.getEntityPlayerClass(), MinecraftClasses.getPlayerConnectionClass());
    private static final FieldAccessor<?> NETWORK_MANAGER = DynamicReflection.getField(MinecraftClasses.getPlayerConnectionClass(), MinecraftClasses.getNetworkManagerClass());
    private static final FieldAccessor<Channel> CHANNEL = DynamicReflection.getField(MinecraftClasses.getNetworkManagerClass(), Channel.class);

    public static final MethodInvoker<?> SEND_PACKET = DynamicReflection.getMethodByArgs(MinecraftClasses.getPlayerConnectionClass(), MinecraftClasses.getPacketClass());
    public static final MethodInvoker<?> READ_PACKET = DynamicReflection.getMethodByArgs(MinecraftClasses.getNetworkManagerClass(), ChannelHandlerContext.class, MinecraftClasses.getPacketClass());
    public static final Supplier<ByteBuf> BYTEBUF_ALLOCATOR = ByteBufAllocator.DEFAULT::buffer;
    public static final MethodInvoker<?> WRITE_PACKET_DATA = DynamicReflection.getMethodByArgs(MinecraftClasses.getPacketClass(), MinecraftClasses.getPacketDataSerializerClass());

    public static Enum<?> getNMSProtocol(Protocol protocol) {
        return switch (protocol) {
            case HANDSHAKING -> GET_NMS_PROTOCOL.get("a");
            case PLAY -> GET_NMS_PROTOCOL.get("b");
            case STATUS -> GET_NMS_PROTOCOL.get("c");
            case LOGIN -> GET_NMS_PROTOCOL.get("d");
        };
    }

    public static Enum<?> getNMSSender(Sender sender) {
        return switch (sender) {
            case CLIENT -> GET_NMS_SENDER.get("a");
            case SERVER -> GET_NMS_SENDER.get("b");
        };
    }

    public static List<String> formatPossiblePacketNames(Protocol protocol, Sender sender, String... baseNames) {
        List<String> names = new ArrayList<>();

        for (String baseName : baseNames) {
            if (isMcpPacketName(baseName)) {
                names.add(formatMcpClassName(protocol, sender, baseName));
                continue;
            }
            names.add("network.protocol." + protocol.packageName() + "." + sender.bounding() + baseName + "Packet");
            names.add("network.protocol." + protocol.packageName() + ".Packet" + protocol.className() + sender.direction() + baseName);
        }

        return names;
    }

    private static boolean isMcpPacketName(String packetName) {
        return packetName.startsWith("C00") || packetName.startsWith("CPacket") || packetName.startsWith("SPacket");
    }

    private static String formatMcpClassName(Protocol protocol, Sender sender, String name) {
        return "net.minecraft.network." + protocol.name().toLowerCase() + "." + sender.mcpName() + "." + name;
    }

    public static ByteBuf newPacketDataSerializer(ByteBuf byteBuf) {
        return (ByteBuf) CREATE_PACKETDATASERIALIZER.construct(byteBuf);
    }

    public static ByteBuf newByteBuf() {
        return BYTEBUF_ALLOCATOR.get();
    }

    public static Channel getChannel(Player player) {
        return CHANNEL.read(NETWORK_MANAGER.read(PLAYER_CONNECTION.read(GET_PLAYER_HANDLE.invoke(player))));
    }

    public static boolean isCurrentlyReloading() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            String clazz = element.getClassName();
            if (clazz.startsWith("org.bukkit.craftbukkit.")
                    && clazz.endsWith(".CraftServer")
                    && element.getMethodName().equals("reload")) {
                return true;
            }
        }
        return false;
    }

    public static void sendPacket(Player player, Object packet) {
        SEND_PACKET.invoke(PLAYER_CONNECTION.read(GET_PLAYER_HANDLE.invoke(player)), packet);
    }

    public static void receivePacket(Player player, Object packet) {
        READ_PACKET.invoke(NETWORK_MANAGER.read(PLAYER_CONNECTION.read(GET_PLAYER_HANDLE.invoke(player))), null, packet);
    }

    public static ByteBuf writePacketData(Object packet) {
        ByteBuf byteBuf = newPacketDataSerializer(newByteBuf());
        WRITE_PACKET_DATA.invoke(packet, byteBuf);
        return byteBuf;
    }
}
