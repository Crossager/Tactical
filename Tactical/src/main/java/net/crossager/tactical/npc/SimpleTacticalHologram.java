package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalHologram;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SimpleTacticalHologram extends SimpleTacticalClientEntity<ArmorStand> implements TacticalHologram {
    private String text;

    public SimpleTacticalHologram(JavaPlugin plugin, Location location, String text, long updateInterval) {
        super(plugin, location, ArmorStand.class, entity -> {
            entity.setCustomName(text);
            entity.setCustomNameVisible(true);
            entity.setInvisible(true);
            entity.setInvulnerable(true);
            entity.setCollidable(false);
            entity.setAI(false);
            entity.setGravity(false);
            entity.setSilent(true);
        }, updateInterval);
        this.text = text;
    }

    @Override
    public String text() {
        return text;
    }

    @Override
    public @NotNull TacticalHologram text(@NotNull String text) {
        this.text = text;
        entity().setCustomName(text);
        updateMetaData();
        return this;
    }
}
