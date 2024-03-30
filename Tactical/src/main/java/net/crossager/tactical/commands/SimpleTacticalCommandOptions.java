package net.crossager.tactical.commands;

import net.crossager.tactical.api.commands.TacticalCommand;
import net.crossager.tactical.api.commands.TacticalCommandOptions;
import net.crossager.tactical.util.Exceptions;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleTacticalCommandOptions extends BaseTacticalCommandOptions {
    private String permission = "";
    private final List<String> aliases = new ArrayList<>();
    private String name = "";
    private String description = "";
    private String usage = "";
    private final String prefix;

    public SimpleTacticalCommandOptions(String prefix, TacticalCommand command) {
        super(command);
        this.prefix = prefix;
    }

    @Override
    public @NotNull TacticalCommandOptions addAlias(@NotNull String alias) {
        aliases.add(alias);
        return this;
    }

    @Override
    public @NotNull TacticalCommandOptions removeAlias(@NotNull String alias) {
        aliases.remove(alias);
        return this;
    }

    @Override
    public @NotNull List<String> aliases() {
        return Collections.unmodifiableList(aliases);
    }

    @Override
    public @NotNull TacticalCommandOptions name(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull TacticalCommandOptions permission(String permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public @NotNull String permission() {
        return permission;
    }

    @Override
    public @NotNull TacticalCommandOptions description(@NotNull String description) {
        this.description = description;
        return this;
    }

    @Override
    public @NotNull String description() {
        return description;
    }

    @Override
    public @NotNull TacticalCommandOptions usage(@NotNull String usage) {
        this.usage = usage;
        return this;
    }

    @Override
    public @NotNull String usage() {
        return usage;
    }

    @Override
    public @NotNull String prefix() {
        return prefix;
    }

    @Override
    public @NotNull PluginCommand pluginCommand() {
        return Exceptions.lazyNoValue();
    }

    @Override
    public boolean hasPluginCommand() {
        return false;
    }
}
