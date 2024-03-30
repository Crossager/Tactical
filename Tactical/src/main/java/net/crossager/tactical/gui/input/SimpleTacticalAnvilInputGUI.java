package net.crossager.tactical.gui.input;

import net.crossager.tactical.api.data.RepeatingList;
import net.crossager.tactical.api.gui.input.TacticalAnvilInputGUI;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.gui.inventory.ItemUtils;
import net.crossager.tactical.api.wrappers.NotNullArrayList;
import net.crossager.tactical.gui.GUIReflection;
import net.crossager.tactical.gui.PlayerGuiEntry;
import net.crossager.tactical.gui.TacticalGUIManager;
import net.crossager.tactical.util.PredefinedPacketWriters;
import net.crossager.tactical.util.reflect.CraftBukkitReflection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SimpleTacticalAnvilInputGUI extends SimpleTacticalInputGUI<TacticalAnvilInputGUI.AnvilInputEvent, TacticalAnvilInputGUI.AnvilInputEvent, TacticalAnvilInputGUI> implements TacticalAnvilInputGUI, Listener {
    public static final int ANVIL_CONTAINER_ID = 7;
    private final TacticalGUIManager guiManager;
    private String title = null;
    private ItemStack itemStack = new ItemStack(Material.PAPER);
    private Function<String, ItemStack> itemMapper;
    private RenamingListener onRenaming = (a, b) -> {};
    private Predicate<String> validator = (s) -> true;
    private boolean hideInventoryItems = false;

    public SimpleTacticalAnvilInputGUI(TacticalGUIManager guiManager) {
        this.guiManager = guiManager;
        resetItemMapper();
    }

    @Override
    protected TacticalAnvilInputGUI getThis() {
        return this;
    }

    @Override
    public void open(@NotNull Player player) {
        int containerId = GUIReflection.NEXT_CONTAINER_COUNTER.invoke(CraftBukkitReflection.getPlayerHandle(player));
        PacketType.play().out().openWindow().sendPacket(player, packetWriter -> {
            packetWriter.writeVarInt(containerId);
            packetWriter.writeVarInt(ANVIL_CONTAINER_ID);
            if (title == null)
                packetWriter.writeString("{\"translate\":\"container.repair\"}");
            else
                packetWriter.writeString("{\"text\":\"" + title + "\"}");
        });
        guiManager.entryOf(player).gui(this).associatedValue(new GUIData(containerId));
        updateItems(player);
    }

    @Override
    public @NotNull TacticalAnvilInputGUI title(@NotNull String title) {
        this.title = title;
        return this;
    }

    @Override
    public @NotNull TacticalAnvilInputGUI resetTitle() {
        this.title = null;
        return this;
    }

    @Override
    public @NotNull TacticalAnvilInputGUI item(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack.clone();
        return this;
    }

    @Override
    public @NotNull TacticalAnvilInputGUI hideInventoryItems(boolean hideInventoryItems) {
        this.hideInventoryItems = hideInventoryItems;
        return this;
    }

    @Override
    public @NotNull TacticalAnvilInputGUI nameToItemMapper(@NotNull Function<String, ItemStack> itemMapper) {
        this.itemMapper = itemMapper;
        return this;
    }

    @Override
    public @NotNull TacticalAnvilInputGUI resetItemMapper() {
        this.itemMapper = string ->
            string.isEmpty() ?
                    itemStack :
                    ItemUtils.setName(itemStack.clone(), ChatColor.ITALIC + string);
        return this;
    }

    @Override
    public @NotNull TacticalAnvilInputGUI itemNameModifier(@NotNull BiConsumer<ItemStack, String> nameModifier) {
        return nameToItemMapper(name -> {
            ItemStack itemStack = this.itemStack.clone();
            nameModifier.accept(itemStack, name);
            return itemStack;
        });
    }

    @Override
    public @NotNull TacticalAnvilInputGUI onRenaming(@NotNull RenamingListener onRenaming) {
        this.onRenaming = onRenaming;
        return this;
    }

    @Override
    public @NotNull TacticalAnvilInputGUI renamingValidator(@NotNull Predicate<String> validator) {
        this.validator = validator;
        return this;
    }

    private void updateItems(Player player) {
        GUIData guiData = guiManager.entryOf(player).associatedValue(GUIData.class);
        NotNullArrayList<ItemStack> itemStacks = new NotNullArrayList<>(ItemUtils.AIR, 39);
        itemStacks.addAll(List.of(itemStack, ItemUtils.AIR, itemMapper.apply(guiData.lastText)));
        if (hideInventoryItems) {
            itemStacks.addAll(new RepeatingList<>(ItemUtils.AIR, 36));
        } else {
            List<ItemStack> storageContents = Arrays.asList(player.getInventory().getStorageContents());
            ArrayList<ItemStack> fixedInventoryItems = new ArrayList<>(36);
            fixedInventoryItems.addAll(storageContents.subList(9, 36));
            fixedInventoryItems.addAll(storageContents.subList(0, 9));
            itemStacks.addAll(fixedInventoryItems);
        }
        Bukkit.getScheduler().runTask(guiManager.getPlugin(), () ->
                PacketType.play().out().windowItems().sendPacket(player, PredefinedPacketWriters.outWindowItems(
                        guiData.containerId,
                        guiData.stateId.incrementAndGet(),
                        itemStacks,
                        ItemUtils.AIR
                ))
        );
    }

    public void renaming(Player player, String string) {
        GUIData guiData = guiManager.entryOf(player).associatedValue(GUIData.class);
        if (guiData.lastText.equals(string)) return;
        guiData.lastText = string;
        onRenaming.listen(player, string);
        guiData.lastText = string;
        updateItems(player);
    }

    public void click(Player player, short slot) {
        if (slot == 2) {
            PlayerGuiEntry guiEntry = guiManager.entryOf(player);
            GUIData guiData = guiEntry.associatedValue(GUIData.class);
            if (!validator.test(guiData.lastText)) {
                updateItems(player);
                return;
            }
            PacketType.play().out().closeWindow()
                    .sendPacket(player, PredefinedPacketWriters.closeWindow(guiData.containerId));
            onCloseListener.acceptInput(player, newEvent(guiData.lastText));
            guiEntry.gui(null);
            player.updateInventory();
        } else {
            updateItems(player);
        }
    }

    public void cancel(Player player) {
        PlayerGuiEntry guiEntry = guiManager.entryOf(player);
        onCancelListener.acceptInput(player, newEvent(guiEntry.associatedValue(GUIData.class).lastText));
        guiEntry.gui(null);
        player.updateInventory();
    }

    public static TacticalAnvilInputGUI.AnvilInputEvent newEvent(String renamedText) {
        return new AnvilInputEventImpl(renamedText == null ? "" : renamedText);
    }

    private static class GUIData {
        public final int containerId;
        public final AtomicInteger stateId = new AtomicInteger();
        public String lastText = "";

        public GUIData(int containerId) {
            this.containerId = containerId;
        }
    }

    private static final class AnvilInputEventImpl implements AnvilInputEvent {
        private final String renamedText;
        private boolean cancelled = false;

        private AnvilInputEventImpl(String renamedText) {
            this.renamedText = renamedText;
        }

        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public @NotNull String renamedText() {
            return renamedText;
        }
    }
}
