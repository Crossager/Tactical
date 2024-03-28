package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.TacticalCommons;
import net.crossager.tactical.api.wrappers.nbt.type.*;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public interface TacticalNBTManager {
    @NotNull
    TacticalNBTParser parse(@NotNull String parseString);

    @NotNull
    TacticalNBTTagType<?, ?> getType(byte id);

    @NotNull
    List<TacticalNBTTagType<?, ?>> types();
    
    @NotNull
    static TacticalNBTManager getInstance() {
        return TacticalCommons.getInstance().getNBTManager();
    }

    @NotNull
    TacticalNBTTag<?> read(@NotNull DataInput dataInput, @NotNull TacticalNBTMaxBytes maxBytes) throws IOException;

    @NotNull
    TacticalNBTMaxBytes createMaxBytes(int maxBytes);

    @NotNull
    TacticalEndNBTTag endTag();

    @NotNull
    TacticalByteNBTTagType byteType();

    @NotNull
    TacticalShortNBTTagType shortType();

    @NotNull
    TacticalIntNBTTagType intType();

    @NotNull
    TacticalLongNBTTagType longType();

    @NotNull
    TacticalFloatNBTTagType floatType();

    @NotNull
    TacticalDoubleNBTTagType doubleType();

    @NotNull
    TacticalStringNBTTagType stringType();

    @NotNull
    TacticalAnyListNBTTagType anyListType();

    @NotNull
    TacticalByteListNBTTagType byteListType();

    @NotNull
    TacticalIntListNBTTagType intListType();

    @NotNull
    TacticalLongListNBTTagType longListType();

    @NotNull
    TacticalEndNBTTagType endTagType();

    @NotNull
    TacticalNBTTagCompoundType compoundType();
}
