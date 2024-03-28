package net.crossager.tactical.api.gui.input;

import net.crossager.tactical.api.TacticalGUI;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * An interface representing a GUI that allows the player to enter text into a sign.
 *
 * <pre>{@code
 * TacticalSignGUI signGUI = TacticalSignGUI.create()
 *         .type(Material.OAK_SIGN)
 *         .lines("Line 1", "Line 2", "Line 3", "Line 4")
 *         .glowing(true)
 *         .color(DyeColor.BLUE);
 * }</pre>
 */
public interface TacticalSignGUI extends TacticalInputGUI<TacticalSignGUI.TacticalSignInput, Void> {
    /**
     * Sets the lines of the sign to be displayed in the GUI.
     *
     * @param lines the lines of text to be displayed on the sign
     * @return this {@link TacticalSignGUI} instance
     */
    @NotNull
    @Contract("_ -> this")
    TacticalSignGUI lines(@NotNull List<String> lines);

    /**
     * Sets the lines of the sign to be displayed in the GUI.
     *
     * @param lines the lines of text to be displayed on the sign
     * @return this {@link TacticalSignGUI} instance
     */
    @NotNull
    @Contract("_ -> this")
    TacticalSignGUI lines(@NotNull String... lines);

    /**
     * Sets the type of the sign to be displayed in the GUI.
     *
     * @param type the type of sign to be displayed.
     * @return this {@link TacticalSignGUI} instance
     */
    @NotNull
    @Contract("_ -> this")
    TacticalSignGUI type(@NotNull Material type);

    /**
     * Sets whether the sign block should appear to be glowing in the GUI.
     *
     * @param glowing true if the sign should appear to be glowing, false otherwise
     * @return this {@link TacticalSignGUI} instance
     */
    @NotNull
    @Contract("_ -> this")
    TacticalSignGUI glowing(boolean glowing);

    /**
     * Sets the dye color of the sign block to be displayed in the GUI.
     *
     * @param color the dye color of the sign block
     * @return this {@link TacticalSignGUI} instance
     */
    @NotNull
    @Contract("_ -> this")
    TacticalSignGUI color(DyeColor color);

    /**
     * Sets the listener to be executed when the sign input GUI is closed.
     *
     * @param listener the listener to be executed when the sign input GUI is closed
     * @return this {@link TacticalSignGUI} instance
     */
    @NotNull
    @Contract("_ -> this")
    TacticalSignGUI onClose(@NotNull TacticalInputGUIListener<TacticalSignInput> listener);

    /**
     * @deprecated the sign gui cannot be cancelled, only closed
     * @return this {@link TacticalSignGUI} instance
     */
    @Deprecated
    @Contract("_ -> this")
    @Override
    @NotNull
    TacticalSignGUI onCancel(@NotNull TacticalInputGUIListener<Void> listener);

    /**
     * Returns a new instance of {@link TacticalSignGUI} with the specified lines of text.
     *
     * @param lines the lines of text to be displayed on the sign
     * @return a new instance of {@link TacticalSignGUI}
     */
    @NotNull
    static TacticalSignGUI create(@NotNull String... lines) {
        return TacticalGUI.getInstance().getGUIFactory().createSignGUI(lines);
    }

    /**
     * Returns a new instance of {@link TacticalSignGUI} with the specified lines of text.
     *
     * @param lines the lines of text to be displayed on the sign
     * @return a new instance of {@link TacticalSignGUI}
     */
    @NotNull
    static TacticalSignGUI create(@NotNull List<String> lines) {
        return TacticalGUI.getInstance().getGUIFactory().createSignGUI(lines);
    }

    /**
     * Represents a sign input that can be retrieved from a player.
     */
    interface TacticalSignInput {

        /**
         * Returns the text on the specified line of the sign.
         *
         * @param index the index of the line to retrieve
         * @return the text on the specified line of the sign
         * @throws IndexOutOfBoundsException if the index is out of range
         */
        @NotNull
        String line(int index);

        /**
         * Returns a list of all the lines of the sign.
         *
         * @return a list of all the lines of the sign
         */
        @NotNull
        List<String> lines();
    }
}
