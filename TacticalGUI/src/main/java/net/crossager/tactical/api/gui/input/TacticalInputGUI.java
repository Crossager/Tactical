package net.crossager.tactical.api.gui.input;

import net.crossager.tactical.api.gui.TacticalBaseGUI;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Tactical Input GUI that extends {@link TacticalBaseGUI} and provides additional functionality for handling
 * input-related events.
 * @param <T> the type of input to be collected by the GUI
 * @param <C> the type of the value to be returned if input collection is cancelled
 */
public interface TacticalInputGUI<T, C> extends TacticalBaseGUI {

    /**
     * Registers a listener to be called when the GUI is closed.
     * @param listener the listener to be registered
     * @return the instance of this {@link TacticalInputGUI}
     */
    @NotNull
    TacticalInputGUI<T, C> onClose(@NotNull TacticalInputGUIListener<T> listener);

    /**
     * Registers a listener to be called when input collection is cancelled.
     * @param listener the listener to be registered
     * @return the instance of this {@link TacticalInputGUI}
     */
    @NotNull
    TacticalInputGUI<T, C> onCancel(@NotNull TacticalInputGUIListener<C> listener);
}
