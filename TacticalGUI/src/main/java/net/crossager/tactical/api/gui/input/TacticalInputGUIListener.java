package net.crossager.tactical.api.gui.input;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A functional interface for a listener that accepts input from a TacticalInputGUI.
 *
 * @param <T> the type of the input
 */
@FunctionalInterface
public interface TacticalInputGUIListener<T> {

    /**
     * Accepts input from a TacticalInputGUI.
     *
     * @param player the player who entered the input
     * @param input the input entered by the player
     */
    void acceptInput(@NotNull Player player, @NotNull T input);

    /**
     * Returns a TacticalInputGUIListener that does nothing when input is accepted.
     *
     * @return a TacticalInputGUIListener that does nothing
     */
    @NotNull
    static <T> TacticalInputGUIListener<T> none() {
        return (player, input) -> {};
    }
}
