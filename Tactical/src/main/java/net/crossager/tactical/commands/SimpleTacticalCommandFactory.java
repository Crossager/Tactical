package net.crossager.tactical.commands;

import net.crossager.tactical.api.commands.TacticalCommand;
import net.crossager.tactical.api.commands.TacticalCommandFactory;
import net.crossager.tactical.api.commands.TacticalSubCommand;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentFactory;
import net.crossager.tactical.commands.argument.SimpleTacticalCommandArgumentFactory;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SimpleTacticalCommandFactory implements TacticalCommandFactory {
    private final TacticalCommandArgumentFactory argumentFactory = new SimpleTacticalCommandArgumentFactory();

    @Override
    public @NotNull TacticalCommand createCommand(@NotNull String prefix, @NotNull String name) {
        return new SimpleTacticalCommand(prefix, name);
    }

    @Override
    public @NotNull TacticalCommand createCommand(@NotNull JavaPlugin plugin, @NotNull String name) {
        return createCommand(plugin.getName(), name);
    }

    @Override
    public @NotNull TacticalCommand createCommand(@NotNull PluginCommand pluginCommand) {
        return new SimpleTacticalCommand(pluginCommand);
    }

    @Override
    public @NotNull TacticalSubCommand createSubCommand(@NotNull String name) {
        return new SimpleTacticalSubCommand(name);
    }

    @Override
    public @NotNull TacticalCommandArgumentFactory argumentFactory() {
        return this.argumentFactory;
    }
}
