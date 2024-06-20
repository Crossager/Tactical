package net.crossager.tactical.api.gui.inventory;

import org.jetbrains.annotations.NotNull;

public interface TacticalComponentPlayerDataListener<T> {
    T getDefault();
    boolean isRegistered();
    void register(@NotNull TacticalComponentData<T> data);

    enum DataPersistence {
        KEEP,
        RESET_ON_CLOSE,
        RESET_ON_LEAVE,
    }
}
