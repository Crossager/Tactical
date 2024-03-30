package net.crossager.tactical.protocol.packet.custom;

import io.netty.buffer.ByteBuf;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.crossager.tactical.api.protocol.packet.PacketConstructor;
import net.crossager.tactical.api.protocol.packet.custom.CustomPacket;
import net.crossager.tactical.api.protocol.packet.custom.CustomPacketConstructor;
import net.crossager.tactical.api.reflect.ReflectionUtils;
import net.crossager.tactical.protocol.packet.SimplePacketReader;
import net.crossager.tactical.protocol.packet.SimplePacketWriter;
import net.crossager.tactical.util.reflect.MinecraftClasses;

import java.io.IOException;

public class PacketWrapper {
    private final PacketConstructor<?> packetConstructor;
    private final Class<?> wrappedClass;

    public PacketWrapper(Class<?> wrappedClass, CustomPacketConstructor<?> constructor) {
        this.wrappedClass = wrappedClass;
        this.packetConstructor = data -> ReflectionUtils.getConstructor(wrappedClass, CustomPacket.class)
                .construct(constructor.create(new SimplePacketReader((ByteBuf) data)));
    }

    public Class<?> getWrappedClass() {
        return wrappedClass;
    }

    public PacketConstructor<?> getPacketConstructor() {
        return packetConstructor;
    }

    public static class WriteDelegator {
        @SuppressWarnings("unused")
        public static void write(@FieldValue("customPacket") CustomPacket customPacket, @AllArguments Object[] args) {
            customPacket.write(new SimplePacketWriter((ByteBuf) args[0]));
        }
    }

    public static class AcceptListenerDelegator {
        @SuppressWarnings("unused")
        public static void acceptListener(@FieldValue("customPacket") CustomPacket customPacket, @AllArguments Object[] args) {
            customPacket.onReceived();
        }
    }

    public static <T extends CustomPacket> PacketWrapper createWrapper(CustomPacketConstructor<T> constructor, String wrappedClassName) {
        return new PacketWrapper(createClass(wrappedClassName), constructor);
    }

    // excuse me, what the fuck is this?
    private static Class<?> createClass(String name) {
        try (DynamicType.Unloaded<?> unloaded = new ByteBuddy()
                .subclass(CustomPacketWrapper.class)
                .implement(MinecraftClasses.getPacketClass())
                .name(name)
                .method(ElementMatchers.takesArguments(MinecraftClasses.getPacketDataSerializerClass()))
                .intercept(MethodDelegation.to(WriteDelegator.class))
                .method(ElementMatchers.takesArguments(MinecraftClasses.getPacketListenerClass()))
                .intercept(MethodDelegation.to(AcceptListenerDelegator.class))
                .make()) {
            return unloaded.load(PacketWrapper.class.getClassLoader()).getLoaded();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
