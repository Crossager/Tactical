package net.crossager.tactical.api;

import net.crossager.tactical.api.gui.TacticalGUIFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link TacticalGUI} interface provides access to the Tactical API's GUI functionality.
 */
public interface TacticalGUI {
    /**
     * Returns the {@link TacticalGUIFactory} associated with this {@link TacticalGUI} instance.
     * Throws an {@link IllegalStateException} if no plugin is associated with the current tactical instance.
     *
     * @return the {@link TacticalGUIFactory} associated with this {@link TacticalGUI} instance
     * @throws IllegalStateException if the TacticalGUI is not available without a plugin
     */
    @NotNull
    TacticalGUIFactory getGUIFactory();

    /**
     * Returns the singleton instance of the {@link TacticalGUI} class.
     *
     * @return the singleton instance of the {@link TacticalGUI} class
     */
    @NotNull
    static TacticalGUI getInstance() {
        return GUIAPIHolder.getInstance();
    }
}
