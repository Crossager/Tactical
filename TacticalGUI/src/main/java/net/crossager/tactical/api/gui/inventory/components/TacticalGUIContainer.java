package net.crossager.tactical.api.gui.inventory.components;

import net.crossager.tactical.api.TacticalGUI;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents a container within a Tactical GUI, containing multiple components.
 */
public interface TacticalGUIContainer extends TacticalGUIComponent {

    /**
     * Sets the component at the specified position within the container.
     *
     * @param x         The x-coordinate of the component
     * @param y         The y-coordinate of the component
     * @param component The component to set
     * @return The container with the component set
     */
    @NotNull
    TacticalGUIContainer setComponent(int x, int y, @NotNull TacticalGUIComponent component);

    /**
     * Removes the component at the specified position within the container.
     *
     * @param x The x-coordinate of the component to remove
     * @param y The y-coordinate of the component to remove
     * @return The container with the component removed
     */
    @NotNull
    TacticalGUIContainer removeComponent(int x, int y);

    /**
     * Creates a border around the container using the specified component.
     *
     * @param component The component to use for the border
     * @return The container with the border created
     */
    @NotNull
    TacticalGUIContainer createBorder(@NotNull TacticalGUIComponent component);

    /**
     * Fills the specified region within the container with the given component.
     *
     * @param fromX     The starting x-coordinate of the region
     * @param fromY     The starting y-coordinate of the region
     * @param toX       The ending x-coordinate of the region
     * @param toY       The ending y-coordinate of the region
     * @param component The component to fill the region with
     * @return The container with the region filled
     */
    @NotNull
    TacticalGUIContainer fillComponent(int fromX, int fromY, int toX, int toY, @NotNull TacticalGUIComponent component);

    /**
     * Retrieves the component at the specified position within the container, if present.
     *
     * @param x The x-coordinate of the component
     * @param y The y-coordinate of the component
     * @return An optional containing the component at the specified position, or empty if no component is present
     */
    @NotNull
    Optional<TacticalGUIComponent> getComponent(int x, int y);

    /**
     * Creates a new TacticalGUIContainer with the specified width and height.
     *
     * @param width  The width of the container
     * @param height The height of the container
     * @return The newly created TacticalGUIContainer
     */
    @NotNull
    static TacticalGUIContainer create(int width, int height) {
        return TacticalGUI.getInstance().getGUIFactory().createContainer(width, height);
    }
}
