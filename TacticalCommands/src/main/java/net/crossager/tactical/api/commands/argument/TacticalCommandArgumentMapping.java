package net.crossager.tactical.api.commands.argument;

import net.crossager.tactical.api.data.TacticalValueHolder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

/**
 * Represents the value parsed by a {@link TacticalCommandArgument}
 */
public interface TacticalCommandArgumentMapping extends TacticalValueHolder {

    /**
     * Returns the {@link TacticalCommandArgument} associated with this mapping.
     *
     * @return the {@link TacticalCommandArgument} associated with this mapping.
     */
    @NotNull
    TacticalCommandArgument argument();

    /**
     * Returns the name of this mapping, which is the name of the {@link TacticalCommandArgument} associated with it.
     *
     * @return the name of this mapping.
     */
    @NotNull
    String name();

    /**
     * Returns the value of this mapping as a {@link Player}.
     *
     * @return the value of this mapping as a {@link Player}.
     * @throws NoSuchElementException if the value is not a {@code Player}.
     */
    @NotNull
    Player asPlayer();

    /**
     * Returns the value of this mapping as a {@link Location}.
     *
     * @return the value of this mapping as a {@link Location}.
     * @throws NoSuchElementException if the value is not a {@code Location}.
     */
    @NotNull
    Location asLocation();
}