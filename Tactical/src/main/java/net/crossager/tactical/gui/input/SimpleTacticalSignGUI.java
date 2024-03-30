package net.crossager.tactical.gui.input;

import net.crossager.tactical.api.gui.input.TacticalSignGUI;
import net.crossager.tactical.api.protocol.packet.PacketType;
import net.crossager.tactical.api.wrappers.BlockLocation;
import net.crossager.tactical.gui.TacticalGUIManager;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class SimpleTacticalSignGUI extends SimpleTacticalInputGUI<TacticalSignGUI.TacticalSignInput, Void, TacticalSignGUI> implements TacticalSignGUI {
    private final TacticalGUIManager guiManager;
    private Material type = Material.OAK_SIGN;
    private boolean glowing = false;
    private DyeColor color = DyeColor.BLACK;
    private final String[] lines = new String[] {"", "", "", ""};

    public SimpleTacticalSignGUI(TacticalGUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public void open(@NotNull Player player) {
        Location location = player.getLocation();
        location.setY(location.getBlockY() > 100 ? 0 : 200);
        player.sendBlockChange(location, type.createBlockData());
        player.sendSignChange(location, lines, color, glowing);
        PacketType.play().out().openSignEditor().sendPacket(player, packetWriter -> {
            packetWriter.writeBlockLocation(BlockLocation.fromLocation(location));
        });
        guiManager.entryOf(player).gui(this);
        player.sendBlockChange(location, location.getBlock().getBlockData());
    }

    @Override
    public @NotNull TacticalSignGUI lines(@NotNull List<String> lines) {
        if (lines.size() > 4) throw new IllegalArgumentException("Lines can not be over length 4");
        for (int i = 0; i < 4; i++) {
            if (lines.size() == i) break;
            this.lines[i] = lines.get(i);
        }
        return this;
    }

    @Override
    public @NotNull TacticalSignGUI lines(@NotNull String... lines) {
        if (lines.length > 4) throw new IllegalArgumentException("Lines can not be over length 4");
        for (int i = 0; i < 4; i++) {
            if (lines.length == i) break;
            this.lines[i] = lines[i];
        }
        return this;
    }

    @Override
    public @NotNull TacticalSignGUI type(@NotNull Material type) {
        this.type = type;
        return this;
    }

    @Override
    public @NotNull TacticalSignGUI glowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    @Override
    public @NotNull TacticalSignGUI color(DyeColor color) {
        this.color = color;
        return this;
    }

    @Override
    protected TacticalSignGUI getThis() {
        return this;
    }

    public void close(Player player, List<String> input) {
        onCloseListener.acceptInput(player, new Input(Collections.unmodifiableList(input)));
    }

    private record Input(List<String> lines) implements TacticalSignGUI.TacticalSignInput {
        @Override
        public @NotNull String line(int index) {
            return lines.get(index);
        }
    }
}
