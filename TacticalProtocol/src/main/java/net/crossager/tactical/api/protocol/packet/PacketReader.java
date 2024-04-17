package net.crossager.tactical.api.protocol.packet;

import net.crossager.tactical.api.wrappers.BlockLocation;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An interface for reading data from a packet.
 */
public interface PacketReader {
    /**
     * Retrieves the underlying PacketDataSerializer used for reading.
     *
     * @return The PacketDataSerializer instance.
     */
    @NotNull
    Object getPacketDataSerializer();

    /**
     * Reads a byte array of the specified length from the packet.
     *
     * @param length The length of the byte array to read.
     * @return The read byte array.
     */
    byte[] readBytes(int length);

    /**
     * Reads bytes into the provided destination byte array.
     *
     * @param dst The destination byte array.
     */
    void readBytes(byte[] dst);

    /**
     * Reads a byte from the packet.
     *
     * @return The read byte.
     */
    byte readByte();

    /**
     * Reads a short from the packet.
     *
     * @return The read short.
     */
    short readShort();

    /**
     * Reads an integer from the packet.
     *
     * @return The read integer.
     */
    int readInt();
    /**
     * Uses an inbuilt nms method to read an integer
     *
     * @return The integer
     */
    int readVarInt();

    /**
     * Reads a long from the packet.
     *
     * @return The read long.
     */
    long readLong();

    /**
     * Uses an inbuilt nms method to read an integer
     *
     * @return The long.
     */
    long readVarLong();

    /**
     * Reads a float from the packet.
     *
     * @return The read float.
     */
    float readFloat();

    /**
     * Reads a double from the packet.
     *
     * @return The read double.
     */
    double readDouble();

    /**
     * Reads a boolean from the packet.
     *
     * @return The read boolean.
     */
    boolean readBoolean();
    /**
     * Reads a string from the packet.
     *
     * @return The read string.
     */
    String readString();

    /**
     * Reads a string from the packet with a specified maximum length.
     *
     * @param maxLength The maximum length of the string to read.
     * @return The read string.
     */
    String readString(int maxLength);

    /**
     * Reads an ItemStack from the packet.
     *
     * @return The read ItemStack.
     */
    ItemStack readItemStack();

    /**
     * Reads a Location from the packet.
     *
     * @return The read Location.
     */
    Location readLocation();

    /**
     * Reads a BlockLocation from the packet.
     *
     * @return The read BlockLocation.
     */
    BlockLocation readBlockLocation();

    /**
     * Reads a Vector in float precision from the packet.
     *
     * @return The read Vector.
     */
    Vector readVectorF();

    /**
     * Reads a Vector in double precision from the packet.
     *
     * @return The read Vector.
     */
    Vector readVector();
    /**
     * Reads a UUID from the packet.
     *
     * @return The read UUID.
     */
    UUID readUUID();

    /**
     * Reads an enum from the packet.
     *
     * @param enm The enum class.
     * @param <T> The type of the enum.
     * @return The read enum value.
     */
    <T extends Enum<T>> T readEnum(Class<T> enm);

    /**
     * Reads a TacticalNBTTag from the packet, if present.
     *
     * @return An Optional containing the read TacticalNBTTag, or empty if not present.
     */
    Optional<TacticalNBTTag<?>> readNBTTag();

    /**
     * Reads a TacticalNBTTag from the packet, if present, with a specified maximum bytes.
     *
     * @param maxBytes The maximum bytes allowed for the tag.
     * @return An Optional containing the read TacticalNBTTag, or empty if not present.
     */
    Optional<TacticalNBTTag<?>> readNBTTag(TacticalNBTMaxBytes maxBytes);

    /**
     * Reads a collection from the packet using the provided read function.
     *
     * @param readFunction The function to read individual elements of the collection.
     * @param <T>          The type of elements in the collection.
     * @return The read collection.
     */
    <T> List<T> readCollection(Supplier<T> readFunction);

    /**
     * Reads an empty collection from the packet.
     *
     * @param <T> The type of elements in the collection.
     * @return An empty list.
     */
    <T> List<T> readEmptyCollection();

    /**
     * Reads an EnumSet
     * @param enumType the class of the enum
     * @param <E> the type of enum
     */
    <E extends Enum<E>> void readEnumSet(Class<E> enumType);

    /**
     * Reads an optional value from the packet using the provided read function.
     *
     * @param readFunction The function to read the optional value.
     * @param <T>          The type of the optional value.
     * @return An Optional containing the read value, or empty if not present.
     */
    <T> Optional<T> readOptional(Supplier<T> readFunction);

    /**
     * Skips a specified number of bytes from the packet.
     *
     * @param amount The number of bytes to skip.
     */
    void skipBytes(int amount);

    /**
     * Reads a value from the packet using reflection.
     *
     * @param type The class of the value to read.
     * @param <T>  The type of the value.
     * @return The read value.
     */
    <T> T reflectRead(Class<T> type);

    /**
     * Reads a value from the packet using the provided reader function without throwing exceptions.
     *
     * @param reader The reader function.
     * @param <T>    The type of the value to return.
     * @return The value returned by the reader function, or null if an exception occurs.
     */
    <T> T readSilentAndReturn(Function<PacketReader, T> reader);

    /**
     * Reads from the packet using the provided reader function without throwing exceptions.
     *
     * @param reader The reader function.
     */
    void readSilent(Consumer<PacketReader> reader);

    /**
     * Gets the current reader index of the packet.
     *
     * @return The current reader index.
     */
    int readerIndex();

    /**
     * Sets the reader index of the packet.
     *
     * @param readerIndex The new reader index.
     */
    void readerIndex(int readerIndex);

    /**
     * Gets the number of readable bytes remaining in the packet.
     *
     * @return The number of readable bytes.
     */
    int readableBytes();

    /**
     * Skips all remaining bytes in the packet.
     */
    void skipRemaining();
}
