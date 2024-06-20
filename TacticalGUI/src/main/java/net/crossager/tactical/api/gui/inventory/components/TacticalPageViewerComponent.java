package net.crossager.tactical.api.gui.inventory.components;

import net.crossager.tactical.api.TacticalGUI;
import net.crossager.tactical.api.gui.inventory.TacticalComponentPlayerDataListener;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interface representing a paginated viewer component for displaying items of type {@link TacticalGUIComponent}.
 *
 * @param <E> the type of items that the viewer component displays, extending {@link TacticalGUIComponent}
 */
public interface TacticalPageViewerComponent<E extends TacticalGUIComponent> extends TacticalGUIComponent, TacticalComponentPlayerDataListener<Integer> {

    /**
     * Sets the items to be displayed by this page viewer component.
     *
     * @param items the list of items to display, must not be null
     * @return the current {@link TacticalPageViewerComponent} instance
     */
    @NotNull
    TacticalPageViewerComponent<E> items(@NotNull List<E> items);

    /**
     * Retrieves the list of items currently being displayed by this page viewer component.
     *
     * @return the list of items, never null
     */
    @NotNull
    List<E> items();

    /**
     * Gets the width of each item in the page viewer component.
     *
     * @return the item width in pixels
     */
    int itemWidth();

    /**
     * Gets the height of each item in the page viewer component.
     *
     * @return the item height in
     */
    int itemHeight();

    /**
     * Gets the current page index being displayed.
     *
     * @return the current page index
     */
    int page(@NotNull OfflinePlayer player);

    /**
     * Sets the current page index to be displayed.
     *
     * @param page the page index to display
     * @return the current {@link TacticalPageViewerComponent} instance
     */
    TacticalPageViewerComponent<E> page(@NotNull OfflinePlayer player, int page);

    /**
     * Checks if there is a previous page available.
     *
     * @return true if there is a previous page, false otherwise
     */
    boolean hasPreviousPage(@NotNull OfflinePlayer player);

    /**
     * Checks if there is a next page available.
     *
     * @return true if there is a next page, false otherwise
     */
    boolean hasNextPage(@NotNull OfflinePlayer player);

    /**
     * Navigates to the previous page if available.
     *
     * @return true if the page was changed to the previous page, false otherwise
     */
    boolean previousPage(@NotNull OfflinePlayer player);

    /**
     * Navigates to the next page if available.
     *
     * @return true if the page was changed to the next page, false otherwise
     */
    boolean nextPage(@NotNull OfflinePlayer player);

    /**
     * Creates a new {@link TacticalPageViewerComponent} instance with the specified items, width, and height.
     *
     * @param items the list of items to display, must not be null
     * @param width the width of each item
     * @param height the height of each item
     * @param <E> the type of items that the viewer component displays, extending {@link TacticalGUIComponent}
     * @return a new {@link TacticalPageViewerComponent} instance
     * @throws IllegalArgumentException if items is null
     */
    @NotNull
    static <E extends TacticalGUIComponent> TacticalPageViewerComponent<E> of(@NotNull List<E> items, int width, int height) {
        return TacticalGUI.getInstance().getGUIFactory().createPageViewer(items, width, height);
    }
}

