package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.TacticalComponentData;
import net.crossager.tactical.api.gui.inventory.TacticalComponentPlayerDataListener;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class GlobalComponentData<T> implements TacticalComponentData<T> {
    private final TacticalComponentPlayerDataListener<T> listener;
    private T value;

    public GlobalComponentData(TacticalComponentPlayerDataListener<T> listener) {
        this.listener = listener;
        clear();
    }

    @Override
    public T get(@NotNull OfflinePlayer player) {
        return value;
    }

    @Override
    public void set(@NotNull OfflinePlayer player, T value) {
        this.value = value;
    }

    @Override
    public void clear() {
        value = listener.getDefault();
    }
}
