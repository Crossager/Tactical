package net.crossager.tactical.api.wrappers.nbt;

import org.jetbrains.annotations.NotNull;

public interface TacticalNBTMaxBytes {
    void read(long bytes);

    @NotNull
    static TacticalNBTMaxBytes of(int maxBytes) {
        return TacticalNBTManager.getInstance().createMaxBytes(maxBytes);
    }

    @NotNull
    static TacticalNBTMaxBytes limitless() {
        return bytes -> {};
    }
}
