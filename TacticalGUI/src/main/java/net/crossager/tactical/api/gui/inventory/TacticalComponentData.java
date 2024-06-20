package net.crossager.tactical.api.gui.inventory;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public interface TacticalComponentData<T> {
    T get(@NotNull OfflinePlayer player);
    void set(@NotNull OfflinePlayer player, T value);
    void clear();
}
