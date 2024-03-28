package net.crossager.tactical.api.wrappers.nbt.type;

import net.crossager.tactical.api.wrappers.nbt.TacticalByteListNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalByteNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import org.jetbrains.annotations.NotNull;

public interface TacticalByteListNBTTagType extends TacticalListNBTTagType<TacticalByteNBTTag, TacticalByteListNBTTag> {
    @NotNull
    static TacticalByteListNBTTagType type() {
        return TacticalNBTManager.getInstance().byteListType();
    }
}
