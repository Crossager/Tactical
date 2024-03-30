package net.crossager.tactical.nbt.type;

import net.crossager.tactical.api.util.TacticalUtils;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTag;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTTagCompound;
import net.crossager.tactical.api.wrappers.nbt.type.TacticalNBTTagCompoundType;
import net.crossager.tactical.nbt.SimpleTacticalNBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleTacticalNBTTagCompoundType extends SimpleTacticalNBTTagType<Map<String, TacticalNBTTag<?>>, TacticalNBTTagCompound> implements TacticalNBTTagCompoundType {
    public static final byte ID = 10;

    public SimpleTacticalNBTTagCompoundType() {
        super(TacticalUtils.castClassGenerics(Map.class), SimpleTacticalNBTTagCompound::new);
    }

    @Override
    public byte id() {
        return ID;
    }

    @Override
    public @NotNull TacticalNBTTagCompound read(@NotNull DataInput dataInput, TacticalNBTMaxBytes maxBytes) throws IOException {
        maxBytes.read(48);
        Map<String, TacticalNBTTag<?>> map = new HashMap<>();

        byte id;
        while((id = dataInput.readByte()) != 0) {
            String key = dataInput.readUTF();
            maxBytes.read(28 + 2L * key.length());
            TacticalNBTTag<?> tag = TacticalNBTManager.getInstance().getType(id).read(dataInput, maxBytes);
            if (map.put(key, tag) == null) {
                maxBytes.read(36);
            }
        }

        return new SimpleTacticalNBTTagCompound(map);
    }
}
