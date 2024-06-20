package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.ItemUtils;
import net.crossager.tactical.api.gui.inventory.TacticalComponentData;
import net.crossager.tactical.api.gui.inventory.TacticalGUIClickEvent;
import net.crossager.tactical.api.gui.inventory.components.TacticalGUIComponent;
import net.crossager.tactical.api.gui.inventory.components.TacticalPageViewerComponent;
import net.crossager.tactical.gui.GUIUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleTacticalPageViewerComponent<E extends TacticalGUIComponent> implements TacticalPageViewerComponent<E>, ImmutableTacticalGUIComponent {
    private final int width;
    private final int height;
    private TacticalComponentData<Integer> componentData;
    private List<E> items;
    private int pages;
    private int itemWidth;
    private int itemHeight;
    private int itemsPerPage;
    private int widthInItems;

    public SimpleTacticalPageViewerComponent(List<E> items, int width, int height) {
        this.width = width;
        this.height = height;
        componentData = new GlobalComponentData<>(this);
        items(items);
    }

    @Override
    public Integer getDefault() {
        return 0;
    }

    @Override
    public boolean isRegistered() {
        return !(componentData instanceof GlobalComponentData<Integer>);
    }

    @Override
    public void register(@NotNull TacticalComponentData<Integer> data) {
        if (isRegistered()) throw new IllegalStateException("Already registered");
        componentData = data;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public @NotNull ItemStack itemAt(@NotNull Player player, int x, int y, int ticksSinceLastUpdate) {
        GUIUtils.notOutOfBounds(x, y, this);
        int page = page(player);
        int pageStartIndex = page * itemsPerPage;
        int index = (x / itemWidth) + (y / itemHeight) * widthInItems + pageStartIndex;
        if (index >= items.size()) return ItemUtils.AIR;

        int modX = x % itemWidth;
        int modY = y % itemHeight;
        return items.get(index).itemAt(player, modX, modY, ticksSinceLastUpdate);
    }

    @Override
    public void click(@NotNull TacticalGUIClickEvent event) {
        GUIUtils.notOutOfBounds(event.x(), event.y(), this);
        int page = page(event.player());
        int pageStartIndex = page * itemsPerPage;
        int index = (event.x() / itemWidth) + (event.y() / itemHeight) * widthInItems + pageStartIndex;
        if (index >= items.size()) return;

        int modX = event.x() % itemWidth;
        int modY = event.y() % itemHeight;
        items.get(index).click(new SimpleTacticalFromGUIClickEvent(event, modX, modY));
    }

    @Override
    public @NotNull TacticalPageViewerComponent<E> items(@NotNull List<E> items) {
        if (items.isEmpty()) {
            this.items = List.of();
            itemWidth = 0;
            itemHeight = 0;
            return this;
        }
        TacticalGUIComponent sampleComponent = items.get(0);
        int sampleComponentWidth = sampleComponent.width();
        int sampleComponentHeight = sampleComponent.height();
        for (E item : items) {
            if (item.width() != sampleComponentWidth || item.height() != sampleComponentHeight)
                throw new IllegalArgumentException("Provided items contains items with bad dimensions, expected: %s,%s, got %s,%s".formatted(
                        sampleComponentWidth,
                        sampleComponentHeight,
                        item.width(),
                        item.height()));
        }

        if (this.width % sampleComponentWidth != 0)
            throw new IllegalArgumentException("The page viewer width needs to be divisible by the item width");
        if (this.height % sampleComponentHeight != 0)
            throw new IllegalArgumentException("The page viewer height needs to be divisible by the item height");

        itemWidth = sampleComponentWidth;
        itemHeight = sampleComponentHeight;
        this.items = new ArrayList<>(items);

        widthInItems = width / itemWidth;
        int heightInItems = height / itemHeight;
        itemsPerPage = widthInItems * heightInItems;
        pages = (int) Math.ceil((double) items.size() / itemsPerPage);

        componentData.clear();

        return this;
    }

    @Override
    public @NotNull List<E> items() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public int itemWidth() {
        return itemWidth;
    }

    @Override
    public int itemHeight() {
        return itemHeight;
    }

    @Override
    public int page(@NotNull OfflinePlayer player) {
        return componentData.get(player);
    }

    @Override
    public TacticalPageViewerComponent<E> page(@NotNull OfflinePlayer player, int page) {
        componentData.set(player, page);
        return this;
    }

    @Override
    public boolean hasPreviousPage(@NotNull OfflinePlayer player) {
        return page(player) != 0;
    }

    @Override
    public boolean hasNextPage(@NotNull OfflinePlayer player) {
        return page(player) >= pages;
    }

    @Override
    public boolean previousPage(@NotNull OfflinePlayer player) {
        if (!hasPreviousPage(player)) return false;
        page(player, page(player) - 1);
        return true;
    }

    @Override
    public boolean nextPage(@NotNull OfflinePlayer player) {
        if (!hasNextPage(player)) return false;
        page(player, page(player) + 1);
        return true;
    }
}
