package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.TacticalComponentData;
import net.crossager.tactical.api.gui.inventory.TacticalComponentPlayerDataListener;
import net.crossager.tactical.util.PlayerMap;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SimpleTacticalComponentData<T> implements TacticalComponentData<T> {
    private final TacticalComponentPlayerDataListener<T> listener;
    private final TacticalComponentPlayerDataListener.DataPersistence dataPersistence;
    private final Map<Object, T> data;

    @SuppressWarnings("unchecked")
    public SimpleTacticalComponentData(TacticalComponentPlayerDataListener<T> listener, TacticalComponentPlayerDataListener.DataPersistence dataPersistence) {
        this.listener = listener;
        this.dataPersistence = dataPersistence;
        this.data = dataPersistence == TacticalComponentPlayerDataListener.DataPersistence.RESET_ON_LEAVE ? (Map<Object, T>) ((Map<?, ?>) new PlayerMap<>()) : new HashMap<>();
    }

    public TacticalComponentPlayerDataListener.DataPersistence dataPersistence() {
        return dataPersistence;
    }

    @Override
    public T get(@NotNull OfflinePlayer player) {
        return data.computeIfAbsent(player, p -> listener.getDefault());
    }

    @Override
    public void set(@NotNull OfflinePlayer player, T value) {
        data.put(player, value);
    }

    @Override
    public void clear() {
        data.clear();
    }
}
