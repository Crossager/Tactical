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
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.UUID;
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