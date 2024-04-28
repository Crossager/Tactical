package net.crossager.tactical.npc;

import net.crossager.tactical.api.gui.inventory.ItemUtils;
import net.crossager.tactical.api.npc.TacticalPlayerEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public class SimpleTacticalPlayerEquipment implements TacticalPlayerEquipment {
    private final EnumMap<EquipmentSlot, ItemStack> equipment = new EnumMap<>(EquipmentSlot.class);
    private final Runnable onChanged;

    public SimpleTacticalPlayerEquipment(Runnable onChanged) {
        this.onChanged = onChanged;
    }

    @Override
    public void setEquipmentSlot(@NotNull EquipmentSlot equipmentSlot, @NotNull ItemStack itemStack) {
        equipment.put(equipmentSlot, itemStack);
        onChanged.run();
    }

    @Override
    public void clearEquipmentSlot(@NotNull EquipmentSlot equipmentSlot) {
        equipment.remove(equipmentSlot);
        onChanged.run();
    }

    @Override
    public boolean hasEquipment(@NotNull EquipmentSlot equipmentSlot) {
        return equipment.containsKey(equipmentSlot);
    }

    @Override
    public @NotNull ItemStack getEquipment(@NotNull EquipmentSlot equipmentSlot) {
        return equipment.getOrDefault(equipmentSlot, ItemUtils.AIR);
    }

    @Override
    public void clearEquipment() {
        equipment.clear();
        onChanged.run();
    }
}
