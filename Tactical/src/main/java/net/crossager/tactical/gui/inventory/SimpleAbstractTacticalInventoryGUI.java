package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.ItemMoveAction;
import net.crossager.tactical.api.gui.inventory.components.TacticalGUIComponent;
import net.crossager.tactical.api.gui.inventory.TacticalInventoryGUI;
import net.crossager.tactical.gui.GUIUtils;
import net.crossager.tactical.gui.TacticalGUIManager;
import net.crossager.tactical.util.PlayerMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleAbstractTacticalInventoryGUI extends SimpleTacticalGUIContainer implements TacticalInventoryGUI, Listener {
    private final TacticalGUIManager guiManager;
    private final Function<Player, Inventory> inventoryCreator;
    private final PlayerMap<Inventory> inventories = new PlayerMap<>();
    private final PlayerMap<Boolean> playerHasOpen = new PlayerMap<>();
    private Consumer<Player> onClose = p -> {};
    private boolean canPlayerInteractWithInventory = false;
    private boolean registered = true;
    private final List<AnimationArea> animationAreas = new ArrayList<>();

    public SimpleAbstractTacticalInventoryGUI(TacticalGUIManager guiManager, int width, int height, Function<Player, Inventory> inventoryCreator) {
        super(width, height);
        this.guiManager = guiManager;
        this.inventoryCreator = inventoryCreator;
        GUIUtils.registerEvent(guiManager.getPlugin(), this, InventoryCloseEvent.class, this::onInventoryClose);
        GUIUtils.registerEvent(guiManager.getPlugin(), this, InventoryClickEvent.class, this::onInventoryClick);
        GUIUtils.registerEvent(guiManager.getPlugin(), this, InventoryDragEvent.class, this::onInventoryDrag);
    }

    @Override
    public void open(@NotNull Player player) {
        Inventory inventory = inventories.computeIfAbsent(player, inventoryCreator);
        setInventory(player, inventory, 0, 0, width() - 1, height() - 1);
        player.openInventory(inventory);
        playerHasOpen.put(player, true);
    }

    @Override
    public void unregister() {
        if (!registered) return;
        registered = false;
        HandlerList.unregisterAll(this);
        inventories.unregister();
        playerHasOpen.unregister();
    }

    @Override
    public void updateDisplay(@NotNull Player player) {
        updateDisplaySlots(player, 0, 0, width() - 1, height() - 1);
    }

    @Override
    public void updateDisplaySlots(@NotNull Player player, int fromX, int fromY, int toX, int toY) {
        GUIUtils.notOutOfBounds(fromX, fromY, this);
        GUIUtils.notOutOfBounds(toX, toY, this);
        Inventory inventory = inventories.computeIfAbsent(player, inventoryCreator);
        setInventory(player, inventory, fromX, fromY, toX, toY);
        player.updateInventory();
    }

    private void setInventory(Player player, Inventory inventory, int fromX, int fromY, int toX, int toY) {
        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                inventory.setItem(GUIUtils.xyToSlot(x, y, width()), itemAt(player, x, y, 0));
            }
        }
    }

    private void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getView().getTopInventory() != inventories.get(player)) return;
        int[] xy = GUIUtils.slotToXY(event.getSlot(), width());
        ComponentRepresentation representation = row(xy[0])[xy[1]];
        SimpleTacticalGUIClickEvent clickEvent = new SimpleTacticalGUIClickEvent(
                player,
                representation,
                event,
                this
        );
        if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR || event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || event.getAction() == InventoryAction.HOTBAR_SWAP) {
            event.setCancelled(true);
            return;
        }
        if (event.getView().getBottomInventory() == event.getClickedInventory()) {
            if (!canPlayerInteractWithInventory) event.setCancelled(true);
            return;
        }
        if (representation == ComponentRepresentation.EMPTY) {
            event.setCancelled(true);
            return;
        }
        event.setCancelled(true);
        representation.component().click(clickEvent);
    }

    private void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        if (event.getView().getTopInventory() != inventories.get(player)) return;
        onClose.accept(player);
        playerHasOpen.remove(player);
    }

    private void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        Inventory inventory = inventories.get(player);
        if (event.getView().getTopInventory() != inventory) return;
        for (Map.Entry<Integer, ItemStack> entry : event.getNewItems().entrySet()) {
            if (inventory.equals(event.getView().getInventory(entry.getKey()))) {
                int[] xy = GUIUtils.slotToXY(entry.getKey(), width());
                ItemStack item = entry.getValue();
                if (item != null && !canItemBeMovedTo(player, xy[0], xy[1], item, ItemMoveAction.DRAG)) {
                    event.setCancelled(true);
                    return;
                }
            } else {
                if (!canPlayerInteractWithInventory) {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        for (Map.Entry<Integer, ItemStack> entry : event.getNewItems().entrySet()) {
            if (inventory == event.getView().getInventory(entry.getKey()))
                 moveItemTo(player, GUIUtils.x(entry.getKey(), width()),
                            GUIUtils.y(entry.getKey(), width()), entry.getValue(), ItemMoveAction.DRAG);
        }
    }

    @Override
    public @NotNull TacticalInventoryGUI setComponent(int x, int y, @NotNull TacticalGUIComponent component) {
        super.setComponent(x, y, component);
        return this;
    }

    @Override
    public @NotNull TacticalInventoryGUI removeComponent(int x, int y) {
        super.removeComponent(x, y);
        return this;
    }

    @Override
    public @NotNull TacticalInventoryGUI createBorder(@NotNull TacticalGUIComponent component) {
        super.createBorder(component);
        return this;
    }

    @Override
    public @NotNull TacticalInventoryGUI fillComponent(int fromX, int fromY, int toX, int toY, @NotNull TacticalGUIComponent component) {
        super.fillComponent(fromX, fromY, toX, toY, component);
        return this;
    }

    @Override
    public @NotNull TacticalInventoryGUI onClose(@NotNull Consumer<Player> onClose) {
        this.onClose = onClose;
        return this;
    }

    @Override
    public boolean canPlayerInteractWithInventory() {
        return canPlayerInteractWithInventory;
    }

    @Override
    public @NotNull TacticalInventoryGUI canPlayerInteractWithInventory(boolean canPlayerInteractWithInventory) {
        this.canPlayerInteractWithInventory = canPlayerInteractWithInventory;
        return this;
    }

    @Override
    public @NotNull TacticalInventoryGUI addAnimationArea(int updateInterval) {
        return addAnimationArea(updateInterval, 0, 0, width() - 1, height() - 1);
    }

    @Override
    public @NotNull TacticalInventoryGUI removeAnimationArea() {
        return removeAnimationArea(0, 0, width() - 1, height() - 1);
    }

    @Override
    public @NotNull TacticalInventoryGUI addAnimationArea(int updateInterval, int fromX, int fromY, int toX, int toY) {
        removeAnimationArea(fromX, fromY, toX, toY);
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(guiManager.getPlugin(), () -> {
            try {
                playerHasOpen.forEach((player, hasOpen) -> {
                    if (hasOpen == null || !hasOpen) return;
                    Inventory inventory = inventories.computeIfAbsent(player, inventoryCreator);
                    for (int x = fromX; x <= toX; x++) {
                        for (int y = fromY; y <= toY; y++) {
                            inventory.setItem(GUIUtils.xyToSlot(x, y, width()), itemAt(player, x, y, updateInterval));
                        }
                    }
                    player.updateInventory();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, updateInterval, updateInterval);

        animationAreas.add(new AnimationArea(updateInterval, taskId, fromX, fromY, toX, toY));
        return this;
    }

    @Override
    public @NotNull TacticalInventoryGUI removeAnimationArea(int fromX, int fromY, int toX, int toY) {
        AnimationArea removalArea = new AnimationArea(0, 0, fromX, fromY, toX, toY);
        animationAreas.removeIf(animationArea -> {
            if (!removalArea.equals(animationArea)) return false;
            Bukkit.getScheduler().cancelTask(animationArea.taskId());
            return true;
        });
        return this;
    }
}
