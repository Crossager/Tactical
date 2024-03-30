package net.crossager.tactical.gui.inventory;

import net.crossager.tactical.api.gui.inventory.ItemMoveAction;
import net.crossager.tactical.api.gui.inventory.components.TacticalGUIComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ImmutableTacticalGUIComponent extends TacticalGUIComponent {
    @Override
    default boolean canItemBeMovedTo(@NotNull Player player, int x, int y, @NotNull ItemStack itemStack, @NotNull ItemMoveAction action) {
        return false;
    }

    @Override
    default void moveItemTo(@NotNull Player player, int x, int y, @NotNull ItemStack itemStack, @NotNull ItemMoveAction action) {
        throw new UnsupportedOperationException("Cannot set item at x: %s, y: %s".formatted(x, y));
    }

    @Override
    default boolean canCollectItemAt(@NotNull Player player, int x, int y) {
        return false;
    }

    @Override
    default Optional<Object> collectItemAt(@NotNull Player player, int x, int y) {
        return Optional.empty();
    }
}
