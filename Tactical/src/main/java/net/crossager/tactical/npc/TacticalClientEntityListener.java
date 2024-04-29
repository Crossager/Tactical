package net.crossager.tactical.npc;

import net.crossager.tactical.api.npc.TacticalClientEntityInteractEvent;
import net.crossager.tactical.api.npc.TacticalClientEntityInteractType;
import net.crossager.tactical.api.npc.TacticalClientObject;
import net.crossager.tactical.api.protocol.packet.PacketEvent;
import net.crossager.tactical.api.protocol.packet.PacketListener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public record TacticalClientEntityListener<E extends TacticalClientObject<E>>(int entityId, E clientEntity, Consumer<TacticalClientEntityInteractEvent<E>> onInteract) implements PacketListener {
    @Override
    public void listen(@NotNull PacketEvent event) {
        event.data().reader().readSilent(reader -> {
            int entityId = reader.readVarInt();
            if (entityId != this.entityId) return;
            TacticalClientEntityInteractType action = TacticalClientEntityInteractType.fromId(reader.readVarInt());
            Vector target = null;
            EquipmentSlot hand = EquipmentSlot.HAND;

            if (action == TacticalClientEntityInteractType.INTERACT_AT) {
                float x = reader.readFloat();
                float y = reader.readFloat();
                float z = reader.readFloat();
                target = new Vector(x, y, z);
            }

            if (action == TacticalClientEntityInteractType.INTERACT || action == TacticalClientEntityInteractType.INTERACT_AT) {
                hand = reader.readVarInt() == 0 ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
            }

            boolean isSneaking = reader.readBoolean();

            SimpleTacticalClientEntityInteractEvent<E> interactEvent = new SimpleTacticalClientEntityInteractEvent<>(
                    clientEntity,
                    event.player(),
                    target,
                    entityId,
                    hand,
                    action,
                    isSneaking
            );
            onInteract.accept(interactEvent);
        });
    }
}
