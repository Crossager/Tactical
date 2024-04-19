package net.crossager.tactical.api.protocol.packet;

import net.crossager.tactical.api.wrappers.BlockLocation;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * An interface for writing data into a packet.
 */
public interface PacketWriter {
    /**
     * Gets the underlying PacketDataSerializer object used for writing.
     *
     * @return The PacketDataSerializer object.
     */
    @NotNull
    Object getPacketDataSerializer();

    /**
     * Writes an array of bytes to the packet.
     *
     * @param bytes The bytes to write.
     */
    void writeBytes(byte[] bytes);

    /**
     * Writes a byte to the packet.
     *
     * @param b The byte to write.
     */
    void writeByte(int b);

    /**
     * Writes a short to the packet.
     *
     * @param s The short to write.
     */
    void writeShort(int s);

    /**
     * Writes an integer to the packet.
     *
     * @param i The integer to write.
     */
    void writeInt(int i);

    /**
     * Writes a variable-length integer (VarInt) to the packet.
     *
     * @param i The integer to write.
     */
    void writeVarInt(int i);

    /**
     * Writes a long to the packet.
     *
     * @param l The long to write.
     */
    void writeLong(long l);

    /**
     * Writes a variable-length long (VarLong) to the packet.
     *
     * @param l The long to write.
     */
    void writeVarLong(long l);

    /**
     * Writes a float to the packet.
     *
     * @param f The float to write.
     */
    void writeFloat(float f);

    /**
     * Writes a double to the packet.
     *
     * @param d The double to write.
     */
    void writeDouble(double d);

    /**
     * Writes a boolean to the packet.
     *
     * @param b The boolean to write.
     */
    void writeBoolean(boolean b);

    /**
     * Writes a string to the packet.
     *
     * @param s The string to write.
     */
    void writeString(String s);

    /**
     * Writes a string to the packet with a maximum length.
     *
     * @param s         The string to write.
     * @param maxLength The maximum length of the string.
     */
    void writeString(String s, int maxLength);

    /**
     * Writes an ItemStack to the packet.
     *
     * @param itemStack The ItemStack to write.
     */
    void writeItemStack(ItemStack itemStack);

    /**
     * Writes a Location to the packet.
     *
     * @param location The Location to write.
     */
    void writeLocation(Location location);

    /**
     * Writes a BlockLocation to the packet.
     *
     * @param blockLocation The BlockLocation to write.
     */
    void writeBlockLocation(BlockLocation blockLocation);

    /**
     * Writes a Bukkit Vector to the packet.
     *
     * @param vector The Vector to write.
     */
    void writeVectorF(Vector vector);

    /**
     * Writes a Bukkit Vector to the packet.
     *
     * @param vector The Vector to write.
     */
    void writeVector(Vector vector);

    /**
     * Writes a UUID to the packet.
     *
     * @param uuid The UUID to write.
     */
    void writeUUID(UUID uuid);

    /**
     * Writes an enum to the packet.
     *
     * @param enm The enum to write.
     */
    void writeEnum(Enum<?> enm);

    /**
     * Writes a TacticalNBTTag to the packet.
     *
     * @param nbtTag The TacticalNBTTag to write.
     */
    void writeNBTTag(TacticalNBTTag<?> nbtTag);

    /**
     * Writes a collection to the packet using the provided write function.
     *
     * @param collection    The collection to write.
     * @param writeFunction The function to write individual elements of the collection.
     * @param <T>           The type of elements in the collection.
     */
    <T> void writeCollection(Collection<T> collection, Consumer<T> writeFunction);

    /**
     * Writes an empty collection to the packet.
     */
    void writeEmptyCollection();

    /**
     * Writes an EnumSet
     * @param enumType the class of the enum
     * @param enumSet the EnumSet to write
     * @param <E> the type of enum
     */
    <E extends Enum<E>> void writeEnumSet(Class<E> enumType, Set<E> enumSet);

    /**
     * Writes the beginning of an entity metadata entry
     * @param index the index to write
     * @param type the type to write
     */
    void writeEntityDataEntry(int index, int type);

    /**
     * Writes a nullable value to the packet using the provided write function.
     *
     * @param nullable      The nullable value to write.
     * @param writeFunction The function to write the nullable value.
     * @param <T>           The type of the nullable value.
     */
    <T> void writeNullable(T nullable, Consumer<T> writeFunction);

    /**
     * Writes a specified number of empty bytes to the packet.
     *
     * @param amount The number of empty bytes to write.
     */
    void writeEmptyBytes(int amount);

    /**
     * Writes a null value to the packet.
     */
    void writeNull();

    /**
     * Writes a value to the packet using reflection.
     *
     * @param value The value to write.
     */
    void reflectWrite(Object value);

    /**
     * Writes a value to the packet using reflection with a specified class.
     *
     * @param valueClass The class of the value to write.
     * @param value      The value to write.
     */
    void reflectWrite(Class<?> valueClass, Object value);

    /**
     * Gets the current writer index of the packet.
     *
     * @return The current writer index.
     */
    int writerIndex();

    /**
     * Sets the writer index of the packet.
     *
     * @param writerIndex The new writer index.
     */
    void writerIndex(int writerIndex);

}
