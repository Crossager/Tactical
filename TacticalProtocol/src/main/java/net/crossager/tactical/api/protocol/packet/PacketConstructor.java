package net.crossager.tactical.api.protocol.packet;

import java.util.function.Function;

/**
 * Represents a constructor for creating packet instances from PacketDataSerializer instances.
 * In this case, the object passed to the constructor function is actually an instance of an internal NMS class PacketDataSerializer.
 *
 * @param <P> The type of packet.
 */
public interface PacketConstructor<P> extends Function<Object, P> {
    /**
     * Creates a PacketConstructor from the given function.
     *
     * @param function The function to create the PacketConstructor from.
     * @param <P>      The type of packet.
     * @return A PacketConstructor created from the given function.
     */
    static <P> PacketConstructor<P> fromFunction(Function<Object, P> function) {
        return function::apply;
    }
}
