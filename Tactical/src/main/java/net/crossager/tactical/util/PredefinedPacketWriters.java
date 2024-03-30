package net.crossager.tactical.util;

import net.crossager.tactical.api.protocol.packet.PacketWriter;
import net.crossager.tactical.api.wrappers.NotNullArrayList;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class PredefinedPacketWriters {
    public static Consumer<PacketWriter> outWindowItems(int containerId, int stateId, NotNullArrayList<ItemStack> items, ItemStack mouseItem) {
        return packetWriter -> {
            packetWriter.writeByte(containerId);
            packetWriter.writeVarInt(stateId);
            packetWriter.writeCollection(items, packetWriter::writeItemStack);
            packetWriter.writeItemStack(mouseItem);
        };
    }
    public static Consumer<PacketWriter> closeWindow(int containerId) {
        return packetWriter -> {
            packetWriter.writeByte(containerId);
        };
    }
}
