package net.crossager.tactical.api.commands.context;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TacticalCommandArgumentActionContext {
    @NotNull
    CommandSender sender();
    @NotNull
    Player playerSender();
    boolean isPlayerSender();
    @NotNull
    List<String> arguments();
    @NotNull
    String argument();
    @NotNull
    String argumentString();
    void throwParseException(@NotNull String message);
}
