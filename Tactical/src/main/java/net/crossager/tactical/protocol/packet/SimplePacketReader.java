package net.crossager.tactical.protocol.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.DecoderException;
import net.crossager.tactical.api.data.LazyInitializedHashMap;
import net.crossager.tactical.api.data.LazyInitializer;
import net.crossager.tactical.api.protocol.packet.PacketReader;
import net.crossager.tactical.api.reflect.MethodInvoker;
import net.crossager.tactical.api.wrappers.BlockLocation;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
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
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimplePacketReader implements PacketReader {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final LazyInitializedHashMap<Class<?>, MethodInvoker<?>> REFLECT_READ_CACHE =
            new LazyInitializedHashMap<>(clazz -> DynamicReflection.getMethodByReturnType(MinecraftClasses.getPacketDataSerializerClass(), clazz));
    private static final LazyInitializer<MethodInvoker<ItemStack>> ITEM_AS_BUKKIT_COPY = new LazyInitializer<>(() -> DynamicReflection.getMethodByReturnTypeAndArgs(
            CraftBukkitReflection.getCraftItemStackClass(),
            ItemStack.class,
            MinecraftClasses.getItemStackClass()
    ));
    private final ByteBuf byteBuf;

    public SimplePacketReader(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    @Override
    public @NotNull Object getPacketDataSerializer() {
        return byteBuf;
    }

    @Override
    public byte[] readBytes(int length) {
        byte[] dst = new byte[length];
        readBytes(dst);
        return dst;
    }

    @Override
    public void readBytes(byte[] dst) {
        byteBuf.readBytes(dst);
    }

    @Override
    public byte readByte() {
        return byteBuf.readByte();
    }

    @Override
    public short readShort() {
        return byteBuf.readShort();
    }

    @Override
    public int readInt() {
        return byteBuf.readInt();
    }

    @Override
    public int readVarInt() {
        int varInt = 0;
        int byteIndex = 0;

        byte currentByte;
        do {
            currentByte = readByte();
            varInt |= (currentByte & 127) << byteIndex++ * 7;
            if (byteIndex > 5) {
                throw new DecoderException("VarInt too big");
            }
        } while((currentByte & 128) == 128);

        return varInt;
    }

    @Override
    public long readLong() {
        return byteBuf.readLong();
    }

    @Override
    public long readVarLong() {
        long varLong = 0L;
        int byteIndex = 0;

        byte currentByte;
        do {
            currentByte = readByte();
            varLong |= (long)(currentByte & 127) << byteIndex++ * 7;
            if (byteIndex > 10) {
                throw new DecoderException("VarLong too big");
            }
        } while((currentByte & 128) == 128);

        return varLong;
    }

    @Override
    public float readFloat() {
        return byteBuf.readFloat();
    }

    @Override
    public double readDouble() {
        return byteBuf.readDouble();
    }

    @Override
    public boolean readBoolean() {
        return byteBuf.readBoolean();
    }

    @Override
    public String readString() {
        return readString(Short.MAX_VALUE);
    }

    @Override
    public String readString(int maxLength) {
        int length = readVarInt();
        if (length > maxLength * 3)
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + length + " > " + maxLength * 3 + ")");
        if (length < 0)
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        String s = byteBuf.toString(this.readerIndex(), length, StandardCharsets.UTF_8);
        this.readerIndex(this.readerIndex() + length);
        if (s.length() > maxLength)
            throw new DecoderException("The received string length is longer than maximum allowed (" + s.length() + " > " + maxLength + ")");
        return s;
    }

    @Override
    public ItemStack readItemStack() {
        return ITEM_AS_BUKKIT_COPY.get().invoke(null, reflectRead(MinecraftClasses.getItemStackClass()));
    }

    @Override
    public Location readLocation() {
        return new Location(null, readDouble(), readDouble(), readDouble());
    }

    @Override
    public BlockLocation readBlockLocation() {
        return BlockLocation.decompress(readLong());
    }

    @Override
    public Vector readVectorF() {
        return new Vector(readFloat(), readFloat(), readFloat());
    }

    @Override
    public Vector readVector() {
        return new Vector(readDouble(), readDouble(), readDouble());
    }

    @Override
    public UUID readUUID() {
        return new UUID(readLong(), readLong());
    }

    @Override
    public <T extends Enum<T>> T readEnum(Class<T> enm) {
        return enm.getEnumConstants()[readVarInt()];
    }

    @Override
    public Optional<TacticalNBTTag<?>> readNBTTag() {
        return readNBTTag(TacticalNBTMaxBytes.of(0x200000));
    }

    @Override
    public Optional<TacticalNBTTag<?>> readNBTTag(TacticalNBTMaxBytes maxBytes) {
        int oldReaderIndex = this.readerIndex();
        if (readByte() == 0)
            return Optional.empty();

        this.readerIndex(oldReaderIndex);
        try (ByteBufInputStream inputStream = new ByteBufInputStream(byteBuf)) {
            return Optional.of(TacticalNBTManager.getInstance().read(inputStream, maxBytes));
        } catch (IOException e) {
            throw new DecoderException(e);
        }
    }

    @Override
    public <T> List<T> readCollection(Supplier<T> readFunction) {
        int size = readVarInt();
        List<T> list = new ArrayList<>(size);

        for(int i = 0; i < size; ++i) {
            list.add(readFunction.get());
        }

        return list;
    }

    @Override
    public <T> List<T> readEmptyCollection() {
        readVarInt();
        return List.of();
    }

    @Override
    public <E extends Enum<E>> EnumSet<E> readEnumSet(Class<E> enumType) {
        Enum<E>[] constants = enumType.getEnumConstants();
        BitSet bitset = readBitSet(constants.length);
        EnumSet<E> enumSet = EnumSet.noneOf(enumType);

        for(int i = 0; i < constants.length; ++i) {
            if (bitset.get(i)) {
                enumSet.add((E) constants[i]);
            }
        }

        return enumSet;
    }

    public BitSet readBitSet(int length) {
        byte[] byteArray = new byte[-Math.floorDiv(-length, 8)];
        readBytes(byteArray);
        return BitSet.valueOf(byteArray);
    }

    @Override
    public <T> Optional<T> readOptional(Supplier<T> readFunction) {
        return readBoolean() ? Optional.of(readFunction.get()) : Optional.empty();
    }

    @Override
    public void skipBytes(int amount) {
        byteBuf.skipBytes(amount);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T reflectRead(Class<T> type) {
        return (T) REFLECT_READ_CACHE.get(type).invoke(byteBuf);
    }

    @Override
    public <T> T readSilentAndReturn(Function<PacketReader, T> reader) {
        int previousReaderIndex = readerIndex();
        try {
            T result = reader.apply(this);
            readerIndex(previousReaderIndex);
            return result;
        } catch (RuntimeException e) {
            readerIndex(previousReaderIndex);
            throw e;
        }
    }

    @Override
    public void readSilent(Consumer<PacketReader> reader) {
        int previousReaderIndex = readerIndex();
        try {
            reader.accept(this);
            readerIndex(previousReaderIndex);
        } catch (RuntimeException e) {
            readerIndex(previousReaderIndex);
            throw e;
        }
    }

    @Override
    public int readerIndex() {
        return byteBuf.readerIndex();
    }

    @Override
    public void readerIndex(int readerIndex) {
        byteBuf.readerIndex(readerIndex);
    }

    @Override
    public int readableBytes() {
        return byteBuf.readableBytes();
    }

    @Override
    public void skipRemaining() {
        skipBytes(readableBytes());
    }
}
