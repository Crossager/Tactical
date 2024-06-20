package net.crossager.tactical.util;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PlayerMapManager implements Listener {
    private final List<PlayerMap<?>> playerMaps = new ArrayList<>();
    private final List<PlayerSet> playerSets = new ArrayList<>();

    public PlayerMapManager(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void register(PlayerMap<?> playerMap) {
        playerMaps.add(playerMap);
        Bukkit.getOnlinePlayers().forEach(playerMap::join);
    }

    public void unregister(PlayerMap<?> playerMap) {
        playerMaps.remove(playerMap);
    }

    public void register(PlayerSet playerSet) {
        playerSets.add(playerSet);
    }

    public void unregister(PlayerSet playerSet) {
        playerSets.remove(playerSet);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (PlayerMap<?> playerMap : playerMaps) {
            playerMap.join(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        for (PlayerMap<?> playerMap : playerMaps) {
            playerMap.remove(event.getPlayer());
        }
        for (PlayerSet set : playerSets) {
            set.remove(event.getPlayer());
        }
    }
}
