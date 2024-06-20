package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.ItemMoveAction;
import net.crossager.tactical.api.gui.inventory.ItemUtils;
import net.crossager.tactical.api.gui.inventory.TacticalComponentData;
import net.crossager.tactical.api.gui.inventory.TacticalGUIClickEvent;
import net.crossager.tactical.api.gui.inventory.components.TacticalStorageGUIComponent;
import net.crossager.tactical.gui.GUIUtils;
import net.crossager.tactical.util.Array2D;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class SimpleTacticalStorageGUIComponent implements TacticalStorageGUIComponent {
    private final int width;
    private final int height;
    private TacticalComponentData<ItemStack[][]> componentData;
    private ItemsUpdateListener onItemsUpdate = (player, items) -> {};
    private ItemPredicate itemPredicate = (player, itemStack) -> true;

    public SimpleTacticalStorageGUIComponent(int width, int height) {
        GUIUtils.checkDimensions(width, height);
        this.width = width;
        this.height = height;
        componentData = new GlobalComponentData<>(this);
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
        return playerItems(player).get(x, y);
    }

    private ItemStack itemAt(TacticalGUIClickEvent event) {
        GUIUtils.notOutOfBounds(event.x(), event.y(), this);
        return playerItems(event.player()).get(event.x(), event.y());
    }

    @Override
    public void click(@NotNull TacticalGUIClickEvent event) {
        GUIUtils.notOutOfBounds(event.x(), event.y(), this);
        if (!itemPredicate.isItemAllowedInInventory(event.player(), event.player().getItemOnCursor())) {
            event.result(Event.Result.DENY);
            return;
        }
        event.result(Event.Result.ALLOW);
        switch (event.action()) {
            case SWAP_WITH_CURSOR -> setNullable(event.player(), event.x(), event.y(), event.player().getItemOnCursor());
            case PICKUP_ALL, DROP_ALL_SLOT -> setNullable(event.player(), event.x(), event.y(), null);
            case PLACE_ALL -> {
                ItemStack item = event.player().getItemOnCursor().clone();
                ItemStack existing = itemAt(event);
                if (existing.getType() == Material.AIR) {
                    setNullable(event.player(), event.x(), event.y(), item);
                    return;
                }
                existing.setAmount(existing.getAmount() + item.getAmount());
                setNullable(event.player(), event.x(), event.y(), existing);
            }
            case PICKUP_HALF -> {
                ItemStack item = itemAt(event).clone();
                if (item.getAmount() / 2 == 0)
                    item = null;
                else
                    item.setAmount(item.getAmount() / 2);
                setNullable(event.player(), event.x(), event.y(), item);
            }
            case PLACE_ONE -> {
                ItemStack item = event.player().getItemOnCursor().clone();
                ItemStack existing = itemAt(event);
                if (existing.getType() == Material.AIR) {
                    item.setAmount(1);
                    setNullable(event.player(), event.x(), event.y(), item);
                    return;
                }
                existing.setAmount(existing.getAmount() + 1);
                setNullable(event.player(), event.x(), event.y(), existing);
            }
            case PLACE_SOME -> {
                ItemStack item = itemAt(event).clone();
                item.setAmount(item.getType().getMaxStackSize());
                setNullable(event.player(), event.x(), event.y(), item);
            }
            case DROP_ONE_SLOT -> {
                ItemStack item = itemAt(event).clone();
                if (item.getAmount() - 1 == 0)
                    item = null;
                else
                    item.setAmount(item.getAmount() - 1);
                setNullable(event.player(), event.x(), event.y(), item);
            }
            default -> event.result(Event.Result.DENY);
        }
    }

    @Override
    public boolean canItemBeMovedTo(@NotNull Player player, int x, int y, @NotNull ItemStack itemStack, @NotNull ItemMoveAction action) {
        GUIUtils.notOutOfBounds(x, y, this);
        return true;
    }

    @Override
    public void moveItemTo(@NotNull Player player, int x, int y, @NotNull ItemStack itemStack, @NotNull ItemMoveAction action) {
        GUIUtils.notOutOfBounds(x, y, this);
        Array2D<ItemStack> items = playerItems(player);
        items.set(x, y, itemStack);
        onItemsUpdate.onItemsUpdate(player, contents(player));
    }

    @Override
    public boolean canCollectItemAt(@NotNull Player player, int x, int y) {
        GUIUtils.notOutOfBounds(x, y, this);
        return true;
    }

    @Override
    public Optional<Object> collectItemAt(@NotNull Player player, int x, int y) {
        GUIUtils.notOutOfBounds(x, y, this);
        Array2D<ItemStack> items = playerItems(player);
        ItemStack itemStack = items.get(x, y);
        items.set(x, y, ItemUtils.AIR);
        onItemsUpdate.onItemsUpdate(player, contents(player));
        if (itemStack.getType() == Material.AIR) return Optional.empty();
        return Optional.of(itemStack);
    }

    private void setNullable(Player player, int x, int y, ItemStack itemStack) {
        Array2D<ItemStack> items = playerItems(player);
        items.set(x, y, itemStack == null ? ItemUtils.AIR : itemStack);
        onItemsUpdate.onItemsUpdate(player, contents(player));
    }

    @Override
    public @NotNull TacticalStorageGUIComponent clear(@NotNull Player player) {
        return items(player, List.of());
    }

    @Override
    public @NotNull TacticalStorageGUIComponent itemPredicate(@NotNull ItemPredicate itemPredicate) {
        this.itemPredicate = itemPredicate;
        return this;
    }

    @Override
    public @NotNull TacticalStorageGUIComponent onItemsUpdate(@NotNull ItemsUpdateListener listener) {
        this.onItemsUpdate = listener;
        return this;
    }

    @Override
    public @NotNull ItemStack[][] contents(@NotNull Player player) {
        return playerItems(player).asArrays();
    }

    @Override
    public @NotNull List<ItemStack> items(@NotNull Player player) {
        return playerItems(player).asList();
    }

    @Override
    public @NotNull TacticalStorageGUIComponent items(@NotNull Player player, @NotNull List<ItemStack> items) {
        playerItems(player).set(items);
        return this;
    }

    private Array2D<ItemStack> playerItems(Player player) {
        return new Array2D<>(componentData.get(player), width, ItemStack[]::new, ItemUtils.AIR);
    }

    @Override
    public ItemStack[][] getDefault() {
        return new ItemStack[height][];
    }

    @Override
    public boolean isRegistered() {
        return !(componentData instanceof GlobalComponentData<ItemStack[][]>);
    }

    @Override
    public void register(@NotNull TacticalComponentData<ItemStack[][]> data) {
        if (isRegistered()) throw new IllegalStateException("Already registered");
        componentData = data;
    }
}
