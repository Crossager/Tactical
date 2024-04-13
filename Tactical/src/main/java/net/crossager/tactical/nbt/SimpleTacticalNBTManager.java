package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.*;
import net.crossager.tactical.api.wrappers.nbt.type.*;
import net.crossager.tactical.nbt.type.*;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class SimpleTacticalNBTManager implements TacticalNBTManager {
    private final SimpleTacticalByteNBTTagType byteType = new SimpleTacticalByteNBTTagType();
    private final SimpleTacticalShortNBTTagType shortType = new SimpleTacticalShortNBTTagType();
    private final SimpleTacticalIntNBTTagType intType = new SimpleTacticalIntNBTTagType();
    private final SimpleTacticalLongNBTTagType longType = new SimpleTacticalLongNBTTagType();
    private final SimpleTacticalFloatNBTTagType floatType = new SimpleTacticalFloatNBTTagType();
    private final SimpleTacticalDoubleNBTTagType doubleType = new SimpleTacticalDoubleNBTTagType();
    private final SimpleTacticalStringNBTTagType stringType = new SimpleTacticalStringNBTTagType();
    private final SimpleTacticalAnyListNBTTagType anyListType = new SimpleTacticalAnyListNBTTagType();
    private final SimpleTacticalByteListNBTTagType byteListType = new SimpleTacticalByteListNBTTagType();
    private final SimpleTacticalIntListNBTTagType intListType = new SimpleTacticalIntListNBTTagType();
    private final SimpleTacticalLongListNBTTagType longListType = new SimpleTacticalLongListNBTTagType();
    private final SimpleTacticalNBTTagCompoundType compoundType = new SimpleTacticalNBTTagCompoundType();
    private final SimpleTacticalEndNBTTagType endTagType = new SimpleTacticalEndNBTTagType();

    private final List<TacticalNBTTagType<?, ?>> types = List.of(
            byteType,
            shortType,
            intType,
            longType,
            floatType,
            doubleType,
            stringType,
            anyListType,
            byteListType,
            intListType,
            longListType,
            compoundType,
            endTagType
    );

    @Override
    public @NotNull TacticalNBTParser parse(@NotNull String parseString) {
        return new SimpleTacticalNBTParser(this, parseString);
    }

    @Override
    public @NotNull TacticalNBTTagType<?, ?> getType(byte id) {
        return types.stream().filter(type -> type.id() == id).findAny().orElseThrow(() -> new NoSuchElementException("No type with id " + id));
    }

    @Override
    public @NotNull List<TacticalNBTTagType<?, ?>> types() {
        return types;
    }

    @Override
    public @NotNull TacticalNBTTag<?> read(@NotNull DataInput dataInput, @NotNull TacticalNBTMaxBytes maxBytes) throws IOException {
        byte id = dataInput.readByte();
        if (id == 0)
            return endTag();
        if (!MinecraftVersion.hasVersion(MinecraftVersion.v1_20_2))
            dataInput.skipBytes(dataInput.readUnsignedShort());
        return getType(id).read(dataInput, maxBytes);
    }

    @Override
    public @NotNull TacticalNBTMaxBytes createMaxBytes(int maxBytes) {
        return new SimpleTacticalNBTMaxBytes(maxBytes);
    }

    @Override
    public @NotNull TacticalEndNBTTag endTag() {
        return SimpleTacticalEndNBTTag.INSTANCE;
    }

    @Override
    public @NotNull TacticalByteNBTTagType byteType() {
        return byteType;
    }

    @Override
    public @NotNull TacticalShortNBTTagType shortType() {
        return shortType;
    }

    @Override
    public @NotNull TacticalIntNBTTagType intType() {
        return intType;
    }

    @Override
    public @NotNull TacticalLongNBTTagType longType() {
        return longType;
    }

    @Override
    public @NotNull TacticalFloatNBTTagType floatType() {
        return floatType;
    }

    @Override
    public @NotNull TacticalDoubleNBTTagType doubleType() {
        return doubleType;
    }

    @Override
    public @NotNull TacticalStringNBTTagType stringType() {
        return stringType;
    }

    @Override
    public @NotNull TacticalAnyListNBTTagType anyListType() {
        return anyListType;
    }

    @Override
    public @NotNull TacticalByteListNBTTagType byteListType() {
        return byteListType;
    }

    @Override
    public @NotNull TacticalIntListNBTTagType intListType() {
        return intListType;
    }

    @Override
    public @NotNull TacticalLongListNBTTagType longListType() {
        return longListType;
    }

    @Override
    public @NotNull TacticalEndNBTTagType endTagType() {
        return endTagType;
    }

    @Override
    public @NotNull TacticalNBTTagCompoundType compoundType() {
        return compoundType;
    }
}
