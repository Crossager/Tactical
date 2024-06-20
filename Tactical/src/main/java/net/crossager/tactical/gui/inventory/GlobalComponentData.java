package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.TacticalComponentData;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class GlobalComponentData<T> implements TacticalComponentData<T> {
    private T value;

    public GlobalComponentData(T value) {
        this.value = value;
    }

    @Override
    public T get(@NotNull OfflinePlayer player) {
        return value;
    }

    @Override
    public void set(@NotNull OfflinePlayer player, T value) {
        this.value = value;
    }
}
