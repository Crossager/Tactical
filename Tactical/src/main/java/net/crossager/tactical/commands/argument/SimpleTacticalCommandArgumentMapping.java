package net.crossager.tactical.commands.argument;

import net.crossager.tactical.api.commands.TacticalSubCommand;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentMapping;
import net.crossager.tactical.commons.AbstractTacticalValueHolder;
import net.crossager.tactical.util.Exceptions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SimpleTacticalCommandArgumentMapping implements AbstractTacticalValueHolder, TacticalCommandArgumentMapping {
    private final Object data;
    private final TacticalCommandArgument argument;
    private final String name;

    public SimpleTacticalCommandArgumentMapping(TacticalCommandArgument argument, Object data) {
        this.argument = argument;
        this.data = data;
        this.name = argument.name();
    }

    public SimpleTacticalCommandArgumentMapping(String name, Object data) {
        this.name = name;
        this.data = data;
        this.argument = null;
    }

    public Optional<TacticalSubCommand> asSubCommand() {
        if (data instanceof TacticalSubCommand subCommand) return Optional.of(subCommand);
        return Optional.empty();
    }

    @Override
    public @NotNull TacticalCommandArgument argument() {
        if (argument == null) return Exceptions.lazyNoValue();
        return argument;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull Player asPlayer() {
        return getAsType(Player.class);
    }

    @Override
    public @NotNull Location asLocation() {
        return getAsType(Location.class);
    }

    @Override
    public boolean isPresent() {
        return data != null;
    }

    @Override
    public @NotNull Object get() {
        if (isEmpty()) Exceptions.noValue();
        return data;
    }

    @Override
    public boolean isOfType(@NotNull Class<?> type) {
        return type.isInstance(data);
    }
}
