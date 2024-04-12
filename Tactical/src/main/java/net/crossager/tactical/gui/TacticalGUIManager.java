package net.crossager.tactical.gui;

import net.crossager.tactical.api.gui.TacticalGUIFactory;
import net.crossager.tactical.api.gui.animations.TacticalAnimation;
import net.crossager.tactical.api.gui.animations.TacticalAnimationStyle;
import net.crossager.tactical.api.gui.animations.TacticalAnimator;
import net.crossager.tactical.api.gui.input.TacticalAnvilInputGUI;
import net.crossager.tactical.api.gui.TacticalBaseGUI;
import net.crossager.tactical.api.gui.input.TacticalSignGUI;
import net.crossager.tactical.api.gui.inventory.*;
import net.crossager.tactical.api.gui.inventory.components.TacticalGUIContainer;
import net.crossager.tactical.api.gui.inventory.components.TacticalStaticGUIComponent;
import net.crossager.tactical.api.gui.inventory.components.TacticalStorageGUIComponent;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.gui.animations.SimpleTacticalAnimator;
import net.crossager.tactical.gui.input.SimpleTacticalAnvilInputGUI;
import net.crossager.tactical.gui.input.SimpleTacticalSignGUI;
import net.crossager.tactical.gui.inventory.*;
import net.crossager.tactical.util.reflect.MinecraftVersion;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TacticalGUIManager implements TacticalGUIFactory {
    private final JavaPlugin plugin;
    private boolean isInitialized = false;
    private final Map<Player, PlayerGuiEntry> guiCloseListeners = new HashMap<>();

    public TacticalGUIManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private void ensureInitialized() {
        if (isInitialized) return;
        isInitialized = true;
        initPacketListeners();
    }

    private void initPacketListeners() {
        PacketType.play().in().updateSign().addPacketListener(event -> {
            TacticalBaseGUI gui = entryOf(event.player()).gui();
            if (!(gui instanceof SimpleTacticalSignGUI signGUI)) return;
            guiCloseListeners.remove(event.player());
            event.setCancelled(true);
            signGUI.close(event.player(), event.data().reader().readSilentAndReturn(packetReader -> {
                packetReader.readBlockLocation();
                if (MinecraftVersion.hasVersion(MinecraftVersion.v1_20))
                    packetReader.readBoolean(); // is front of sign
                List<String> lines = new ArrayList<>(4);
                for (int i = 0; i < 4; i++) {
                    lines.add(packetReader.readString());
                }
                return lines;
            }));
        });
        PacketType.play().in().itemName().addPacketListener(event -> {
            TacticalBaseGUI gui = entryOf(event.player()).gui();
            if (!(gui instanceof SimpleTacticalAnvilInputGUI anvilGUI)) return;
            event.setCancelled(true);
            anvilGUI.renaming(event.player(), event.data().reader().readString());
        });
        PacketType.play().in().windowClick().addPacketListener(event -> {
            TacticalBaseGUI gui = entryOf(event.player()).gui();
            if (!(gui instanceof SimpleTacticalAnvilInputGUI anvilGUI)) return;
            event.setCancelled(true);
            event.data().reader().readByte();
            event.data().reader().readVarInt();
            anvilGUI.click(event.player(), event.data().reader().readShort());
        });
        PacketType.play().in().closeWindow().addPacketListener(event -> {
            TacticalBaseGUI gui = entryOf(event.player()).gui();
            if (!(gui instanceof SimpleTacticalAnvilInputGUI anvilGUI)) return;
            event.setCancelled(true);
            anvilGUI.cancel(event.player());
        });
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public PlayerGuiEntry entryOf(Player player) {
        return guiCloseListeners.computeIfAbsent(player, PlayerGuiEntry::new);
    }

    @Override
    public @NotNull TacticalSignGUI createSignGUI(@NotNull String... lines) {
        ensureInitialized();
        return new SimpleTacticalSignGUI(this).lines(lines);
    }

    @Override
    public @NotNull TacticalSignGUI createSignGUI(@NotNull List<String> lines) {
        ensureInitialized();
        return new SimpleTacticalSignGUI(this).lines(lines);
    }

    @Override
    public @NotNull TacticalAnvilInputGUI createAnvilInputGUI() {
        ensureInitialized();
        return new SimpleTacticalAnvilInputGUI(this);
    }

    @Override
    public @NotNull TacticalInventoryGUI createGUI(int rows, @NotNull String title) {
        return new SimpleTacticalInventoryGUI(this, rows, title);
    }

    @Override
    public @NotNull TacticalStorageGUIComponent createStorage(int width, int height) {
        return new SimpleTacticalStorageGUIComponent(width, height);
    }

    @Override
    public @NotNull TacticalInventoryGUI createDispenserGUI(@NotNull String title) {
        return new SimpleTacticalDispenserInventoryGUI(this, title);
    }

    @Override
    public @NotNull TacticalInventoryGUI createHopperGUI(@NotNull String title) {
        return new SimpleTacticalHopperInventoryGUI(this, title);
    }

    @Override
    public @NotNull TacticalAnimator createAnimationStyle(@NotNull TacticalAnimationStyle style, int ticksPerStep, float stepAmount, float animationInterval) {
        return new SimpleTacticalAnimator(style, ticksPerStep, stepAmount, animationInterval);
    }

    @Override
    public @NotNull TacticalStaticGUIComponent createStaticComponent(@NotNull TacticalGUIItemGenerator itemGenerator) {
        return new SimpleTacticalStaticGUIComponent().itemStack(itemGenerator);
    }

    @Override
    public @NotNull TacticalStaticGUIComponent createStaticComponent(@NotNull TacticalAnimation<ItemStack> animation, @NotNull TacticalAnimator animator) {
        return new SimpleTacticalStaticGUIComponent().animate(animation, animator);
    }

    @Override
    public @NotNull TacticalStaticGUIComponent createStaticComponent(@NotNull ItemStack itemStack) {
        return new SimpleTacticalStaticGUIComponent().itemStack(itemStack);
    }

    @Override
    public @NotNull TacticalGUIContainer createContainer(int width, int height) {
        return new SimpleTacticalGUIContainer(width, height);
    }
}
