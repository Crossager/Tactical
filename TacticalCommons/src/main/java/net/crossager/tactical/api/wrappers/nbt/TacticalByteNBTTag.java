package net.crossager.tactical.api.wrappers.nbt;

import net.crossager.tactical.api.wrappers.nbt.type.TacticalByteNBTTagType;
import org.jetbrains.annotations.NotNull;

public interface TacticalByteNBTTag extends TacticalNumberNBTTag<Byte> {
    TacticalByteNBTTag FALSE = of((byte) 0);
    TacticalByteNBTTag TRUE = of((byte) 1);

    @NotNull
    TacticalByteNBTTag copy();
    
    boolean asBoolean();

    @NotNull
    static TacticalByteNBTTag of(byte b) {
        return TacticalByteNBTTagType.type().create(b);
    }

    @NotNull
    static TacticalByteNBTTag of(boolean b) {
        return b ? TRUE : FALSE;
    }
}
