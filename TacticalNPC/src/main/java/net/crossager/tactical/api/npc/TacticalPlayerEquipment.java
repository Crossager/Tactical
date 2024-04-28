package net.crossager.tactical.api.npc;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface TacticalPlayerEquipment {
    void setEquipmentSlot(@NotNull EquipmentSlot equipmentSlot, @NotNull ItemStack itemStack);

    void clearEquipmentSlot(@NotNull EquipmentSlot equipmentSlot);

    boolean hasEquipment(@NotNull EquipmentSlot equipmentSlot);

    @NotNull
    ItemStack getEquipment(@NotNull EquipmentSlot equipmentSlot);

    void clearEquipment();
}
