package net.crossager.tactical.commands;

import net.crossager.tactical.api.commands.TacticalCommand;
import net.crossager.tactical.api.commands.TacticalCommandOptions;
import net.crossager.tactical.util.InternalUtils;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimplePluginCommandTacticalCommandOptions extends BaseTacticalCommandOptions {
    private final PluginCommand pluginCommand;

    public SimplePluginCommandTacticalCommandOptions(PluginCommand pluginCommand, TacticalCommand command) {
        super(command);
        this.pluginCommand = pluginCommand;
    }

    @Override
    public @NotNull TacticalCommandOptions addAlias(@NotNull String alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull TacticalCommandOptions removeAlias(@NotNull String alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull List<String> aliases() {
        return pluginCommand.getAliases();
    }

    @Override
    public @NotNull TacticalCommandOptions name(@NotNull String name) {
        if (!pluginCommand.setName(name)) throw new UnsupportedOperationException();
        return this;
    }

    @Override
    public @NotNull String name() {
        return pluginCommand.getName();
    }

    @Override
    public @NotNull TacticalCommandOptions permission(String permission) {
        pluginCommand.setPermission(permission);
        return this;
    }

    @Override
    public @NotNull String permission() {
        return InternalUtils.makeSureHasValue(pluginCommand.getPermission());
    }

    @Override
    public @NotNull TacticalCommandOptions description(@NotNull String description) {
        pluginCommand.setDescription(description);
        return this;
    }

    @Override
    public @NotNull String description() {
        return pluginCommand.getDescription();
    }

    @Override
    public @NotNull TacticalCommandOptions usage(@NotNull String usage) {
        pluginCommand.setUsage(usage);
        return this;
    }

    @Override
    public @NotNull String usage() {
        return pluginCommand.getUsage();
    }

    @Override
    public @NotNull String prefix() {
        return pluginCommand.getPlugin().getName().toLowerCase();
    }

    @Override
    public @NotNull PluginCommand pluginCommand() {
        return pluginCommand;
    }

    @Override
    public boolean hasPluginCommand() {
        return true;
    }
}
