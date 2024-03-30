package net.crossager.tactical.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.crossager.tactical.Tactical;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class PlayerMap<V> extends Object2ObjectArrayMap<Player, V> {
    private final Function<Player, V> onJoinFunction;

    public PlayerMap() {
        this(null);
    }

    public PlayerMap(Function<Player, V> onJoinFunction) {
        this.onJoinFunction = onJoinFunction;
        Tactical.getInstance().getPlayerMapManager().register(this);
    }

    public void unregister() {
        Tactical.getInstance().getPlayerMapManager().unregister(this);
    }

    void join(Player player) {
        if (onJoinFunction == null) return;
        put(player, onJoinFunction.apply(player));
    }
}
