package net.crossager.tactical;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class TacticalPlugin extends JavaPlugin {
    private Tactical tactical;

    @Override
    public void onEnable() {
        tactical = new Tactical(this);
        tactical.init();

    }

    @Override
    public void onDisable() {
        tactical.disable();
        tactical = null;
    }
}
