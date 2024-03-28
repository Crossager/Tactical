package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalByteNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import org.jetbrains.annotations.NotNull;

public interface TacticalByteNBTTagType extends TacticalNumberNBTTagType<Byte, TacticalByteNBTTag> {
    @NotNull
    static TacticalByteNBTTagType type() {
        return TacticalNBTManager.getInstance().byteType();
    }
}
