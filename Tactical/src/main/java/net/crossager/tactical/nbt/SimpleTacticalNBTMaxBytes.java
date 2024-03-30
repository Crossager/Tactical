package net.crossager.tactical.nbt;

import net.crossager.tactical.api.wrappers.nbt.NBTParseException;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTMaxBytes;

public class SimpleTacticalNBTMaxBytes implements TacticalNBTMaxBytes {
    private final int maxBytes;
    private int readBytes;

    public SimpleTacticalNBTMaxBytes(int maxBytes) {
        this.maxBytes = maxBytes;
    }

    @Override
    public void read(long bytes) {
        readBytes += bytes;
        if (readBytes > maxBytes)
            throw new NBTParseException("NBT too larg. Expected a maximum of " + maxBytes + " bytes, got " + readBytes);
    }
}
