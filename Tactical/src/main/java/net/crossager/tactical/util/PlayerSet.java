package net.crossager.tactical.util;

import net.crossager.tactical.Tactical;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class PlayerSet extends HashSet<Player> {

    public PlayerSet() {
        Tactical.getInstance().getPlayerMapManager().register(this);
    }

    public void unregister() {
        Tactical.getInstance().getPlayerMapManager().unregister(this);
    }
}
