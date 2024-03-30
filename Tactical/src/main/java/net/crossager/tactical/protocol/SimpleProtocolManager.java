package net.crossager.tactical.protocol;

import net.crossager.tactical.api.protocol.Protocol;
import net.crossager.tactical.api.protocol.ProtocolManager;
import net.crossager.tactical.api.protocol.Sender;
import net.crossager.tactical.api.protocol.packet.PacketConstructor;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.protocol.packet.custom.CustomPacket;
import net.crossager.tactical.api.protocol.packet.custom.CustomPacketConstructor;
import net.crossager.tactical.api.reflect.FieldAccessor;
import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.api.reflect.ReflectionUtils;
import net.crossager.tactical.protocol.packet.SimplePacketType;
import net.crossager.tactical.protocol.packet.custom.PacketWrapper;
import net.crossager.tactical.util.reflect.DynamicReflection;
import net.crossager.tactical.util.reflect.MinecraftClasses;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class SimpleProtocolManager implements ProtocolManager {
    @SuppressWarnings("rawtypes")
    private static final FieldAccessor<Map> GET_HANDLE_MAP = DynamicReflection.getField(MinecraftClasses.getEnumProtocolClass(), Map.class, false);
    @SuppressWarnings("rawtypes")
    private static final FieldAccessor<Map> GET_PACKET_MAP = DynamicReflection.getField(MinecraftClasses.getEnumProtocolHandleClass(), Map.class);
    @SuppressWarnings("rawtypes")
    private static final FieldAccessor<List> GET_PACKET_CONSTRUCTOR_LIST = DynamicReflection.getField(MinecraftClasses.getEnumProtocolHandleClass(), List.class);
    private static final MethodInvoker<?> REGISTER_PACKET = DynamicReflection.getMethodByArgs(MinecraftClasses.getEnumProtocolHandleClass(), Class.class, Function.class);
    @SuppressWarnings("unchecked")
    private static final Map<Class<?>, Enum<?>> PACKET_PROTOCOL_MAP =
            DynamicReflection.getField(MinecraftClasses.getEnumProtocolClass(), Map.class, true).read(null);

    private final Protocol protocol;
    private final Sender sender;
    private final List<PacketType> packetTypes;

    private final Object handle;
    private final Map<Class<?>, Integer> packetMap;
    private final List<Function<Object, ?>> packetConstructors;

    @SuppressWarnings("unchecked")
    public SimpleProtocolManager(Protocol protocol, Sender sender) {
        this.protocol = protocol;
        this.sender = sender;
        this.handle = GET_HANDLE_MAP.read(ProtocolUtils.getNMSProtocol(protocol)).get(ProtocolUtils.getNMSSender(sender));
        this.packetMap = GET_PACKET_MAP.read(handle);
        this.packetConstructors = GET_PACKET_CONSTRUCTOR_LIST.read(handle);
        this.packetTypes = new ArrayList<>(packetMap.size());

        packetMap.forEach((packetClass, id) -> packetTypes.add(new SimplePacketType(this, packetClass, id, PacketConstructor.fromFunction(packetConstructors.get(id)))));
    }

    @Override
    public @NotNull Protocol protocol() {
        return protocol;
    }

    @Override
    public @NotNull Sender sender() {
        return sender;
    }

    @Override
    public int getPacketId(@NotNull Class<?> packetClass) {
        return packetMap.getOrDefault(packetClass, -1);
    }

    @Override
    public @NotNull List<PacketType> allPackets() {
        return packetTypes;
    }

    @Override
    public @NotNull PacketType getPacketType(@NotNull Class<?> packetClass) {
        try {
            return packetTypes.stream().filter(type -> type.packetClass().equals(packetClass)).findAny()
                    .orElseThrow(() -> new NoSuchElementException("No PacketType found for class: " + packetClass));
        } catch (NullPointerException e) {
            throw new IllegalStateException("NULL PACKET");
        }
    }

    @Override
    public <T> @NotNull PacketType registerPacket(@NotNull Class<T> packetClass) {
        return registerPacket(
                packetClass,
                ReflectionUtils.getConstructor(packetClass, MinecraftClasses.getPacketDataSerializerClass())::construct
        );
    }

    @Override
    public <P> @NotNull PacketType registerPacket(@NotNull Class<P> packetClass, @NotNull PacketConstructor<P> constructor) {
        REGISTER_PACKET.invoke(handle, packetClass, constructor);
        PacketType packetType = new SimplePacketType(this, packetClass, packetMap.get(packetClass), constructor);
        packetTypes.add(packetType);
        PACKET_PROTOCOL_MAP.put(packetClass, ProtocolUtils.getNMSProtocol(protocol));
        return packetType;
    }

    @Override
    public <P> @NotNull PacketType registerPacketWithSpecificId(@NotNull Class<P> packetClass, @NotNull PacketConstructor<P> constructor, int specificId) {
        PacketType packetType = new SimplePacketType(this, packetClass, specificId, constructor);
        packetTypes.add(packetType);
        packetMap.put(packetClass, specificId);
        packetConstructors.add(constructor);
        PACKET_PROTOCOL_MAP.put(packetClass, ProtocolUtils.getNMSProtocol(protocol));
        return packetType;
    }

    @Override
    public @NotNull <P extends CustomPacket> PacketType registerCustomPacket(@NotNull Class<P> packetClass, @NotNull CustomPacketConstructor<P> constructor) {
        return registerCustomPacket(packetClass.getName() + "Wrapper", constructor);
    }

    @Override
    public @NotNull <P extends CustomPacket> PacketType registerCustomPacketWithSpecificId(@NotNull Class<P> packetClass, @NotNull CustomPacketConstructor<P> constructor, int specificId) {
        return registerCustomPacketWithSpecificId(packetClass.getName() + "Wrapper", constructor, specificId);
    }

    @Override
    public @NotNull <P extends CustomPacket> PacketType registerCustomPacket(@NotNull String wrappedClassName, @NotNull CustomPacketConstructor<P> constructor) {
        PacketWrapper wrapper = PacketWrapper.createWrapper(constructor, wrappedClassName);
        Class<?> packetClass = wrapper.getWrappedClass();
        REGISTER_PACKET.invoke(handle, packetClass, wrapper.getPacketConstructor());
        PacketType packetType = new SimplePacketType(this, packetClass, packetMap.get(packetClass), wrapper.getPacketConstructor());
        packetTypes.add(packetType);
        PACKET_PROTOCOL_MAP.put(packetClass, ProtocolUtils.getNMSProtocol(protocol));
        return packetType;
    }

    @Override
    public @NotNull <P extends CustomPacket> PacketType registerCustomPacketWithSpecificId(@NotNull String wrappedClassName, @NotNull CustomPacketConstructor<P> constructor, int specificId) {
        PacketWrapper wrapper = PacketWrapper.createWrapper(constructor, wrappedClassName);
        Class<?> packetClass = wrapper.getWrappedClass();
        PacketType packetType = new SimplePacketType(this, packetClass, specificId, wrapper.getPacketConstructor());
        packetTypes.add(packetType);
        packetMap.put(packetClass, specificId);
        packetConstructors.add(wrapper.getPacketConstructor());
        PACKET_PROTOCOL_MAP.put(packetClass, ProtocolUtils.getNMSProtocol(protocol));
        return packetType;
    }

    @Override
    public @NotNull Map<Class<?>, Integer> getUnderlyingPacketMap() {
        return packetMap;
    }
}
