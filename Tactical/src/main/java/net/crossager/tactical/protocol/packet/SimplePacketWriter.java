package net.crossager.tactical.protocol.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.EncoderException;
import net.crossager.tactical.api.data.LazyInitializedHashMap;
import net.crossager.tactical.api.data.LazyInitializer;
import net.crossager.tactical.api.protocol.packet.PacketWriter;
import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.api.wrappers.BlockLocation;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import net.crossager.tactical.util.reflect.CraftBukkitReflection;
import net.crossager.tactical.util.reflect.DynamicReflection;
import net.crossager.tactical.util.reflect.MinecraftClasses;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;

public class SimplePacketWriter implements PacketWriter {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final LazyInitializedHashMap<Class<?>, MethodInvoker<?>> REFLECT_WRITE_CACHE =
            new LazyInitializedHashMap<>(clazz -> DynamicReflection.getMethodByArgs(MinecraftClasses.getPacketDataSerializerClass(), clazz));
    private static final LazyInitializer<MethodInvoker<?>> ITEM_AS_NMS_COPY = new LazyInitializer<>(() -> DynamicReflection.getMethodByReturnTypeAndArgs(
            CraftBukkitReflection.getCraftItemStackClass(),
            MinecraftClasses.getItemStackClass(),
            ItemStack.class
    ));
    private final ByteBuf byteBuf;

    public SimplePacketWriter(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    @Override
    public @NotNull Object getPacketDataSerializer() {
        return byteBuf;
    }

    @Override
    public void writeBytes(byte[] bytes) {
        byteBuf.writeBytes(bytes);
    }

    @Override
    public void writeByte(int b) {
        byteBuf.writeByte(b);
    }

    @Override
    public void writeShort(int s) {
        byteBuf.writeShort(s);
    }

    @Override
    public void writeInt(int i) {
        byteBuf.writeInt(i);
    }

    @Override
    public void writeVarInt(int i) {
        while((i & -128) != 0) {
            byteBuf.writeByte(i & 127 | 128);
            i >>>= 7;
        }

        byteBuf.writeByte(i);
    }

    @Override
    public void writeLong(long l) {
        byteBuf.writeLong(l);
    }

    @Override
    public void writeVarLong(long i) {
        while((i & -128L) != 0L) {
            byteBuf.writeByte((int)(i & 127L) | 128);
            i >>>= 7;
        }

        byteBuf.writeByte((int)i);
    }

    @Override
    public void writeFloat(float f) {
        byteBuf.writeFloat(f);
    }

    @Override
    public void writeDouble(double d) {
        byteBuf.writeDouble(d);
    }

    @Override
    public void writeBoolean(boolean b) {
        byteBuf.writeBoolean(b);
    }

    @Override
    public void writeString(String s) {
        writeString(s, Short.MAX_VALUE);
    }

    @Override
    public void writeString(String s, int maxLength) {
        if (s.length() > maxLength)
            throw new EncoderException("String too big (was " + s.length() + " characters, max " + maxLength + ")");
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > maxLength * 3)
            throw new EncoderException("String too big (was " + bytes.length + " bytes encoded, max " + maxLength * 3 + ")");
        writeVarInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    @Override
    public void writeItemStack(ItemStack itemStack) {
        reflectWrite(ITEM_AS_NMS_COPY.get().invoke(null, itemStack));
    }

    @Override
    public void writeLocation(Location location) {
        writeDouble(location.getX());
        writeDouble(location.getY());
        writeDouble(location.getZ());
    }

    @Override
    public void writeBlockLocation(BlockLocation blockLocation) {
        writeLong(blockLocation.compress());
    }

    @Override
    public void writeVectorF(Vector vector) {
        writeFloat((float) vector.getX());
        writeFloat((float) vector.getY());
        writeFloat((float) vector.getZ());
    }

    @Override
    public void writeVector(Vector vector) {
        writeDouble(vector.getX());
        writeDouble(vector.getY());
        writeDouble(vector.getZ());
    }

    @Override
    public void writeUUID(UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    @Override
    public void writeEnum(Enum<?> enm) {
        writeVarInt(enm.ordinal());
    }

    @Override
    public void writeNull() {
        writeByte(0);
    }

    @Override
    public void writeNBTTag(TacticalNBTTag<?> nbtTag) {
        if (nbtTag.type().id() != 0) {
            try (ByteBufOutputStream outputStream = new ByteBufOutputStream(byteBuf)) {
                outputStream.writeByte(nbtTag.type().id());
                if (nbtTag.type().id() != 0) {
                    if (!MinecraftVersion.hasVersion(MinecraftVersion.v1_20_2))
                        outputStream.writeUTF("");
                    nbtTag.serialize(outputStream);
                }
            } catch (IOException e) {
                throw new EncoderException(e);
            }
        }
    }

    @Override
    public <T> void writeCollection(Collection<T> collection, Consumer<T> writeFunction) {
        writeVarInt(collection.size());
        collection.forEach(writeFunction);
    }

    @Override
    public void writeEmptyCollection() {
        writeVarInt(0);
    }

    @Override
    public <E extends Enum<E>> void writeEnumSet(Class<E> enumType, Set<E> enumSet) {
        Enum<E>[] constants = enumType.getEnumConstants();
        BitSet bitset = new BitSet(constants.length);

        for (int i = 0; i < constants.length; ++i) {
            bitset.set(i, enumSet.contains(constants[i]));
        }

        writeBitSet(bitset, constants.length);
    }

    @Override
    public void writeEntityDataEntry(int index, int type) {
        byteBuf.writeByte(index);
        writeVarInt(type);
    }

    public void writeBitSet(BitSet bitset, int maxLength) {
        if (bitset.length() > maxLength) {
            throw new EncoderException("BitSet is larger than expected size (" + bitset.length() + ">" + maxLength + ")");
        } else {
            byte[] byteArray = bitset.toByteArray();
            writeBytes(Arrays.copyOf(byteArray, -Math.floorDiv(-maxLength, 8)));
        }
    }

    @Override
    public <T> void writeNullable(T nullable, Consumer<T> writeFunction) {
        writeBoolean(nullable != null);
        if (nullable != null) writeFunction.accept(nullable);
    }

    @Override
    public void writeEmptyBytes(int amount) {
        writeBytes(new byte[amount]);
    }

    @Override
    public void reflectWrite(Object value) {
        reflectWrite(value.getClass(), value);
    }

    @Override
    public void reflectWrite(Class<?> valueClass, Object value) {
        REFLECT_WRITE_CACHE.get(valueClass).invoke(byteBuf, value);
    }

    @Override
    public int writerIndex() {
        return byteBuf.writerIndex();
    }

    @Override
    public void writerIndex(int writerIndex) {
        byteBuf.writerIndex(writerIndex);
    }
}
