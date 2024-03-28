package net.crossager.tactical.api.commands.context;

import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentMapping;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface TacticalCommandExecutionContext {
    @NotNull
    CommandSender sender();
    @NotNull
    Player playerSender();
    @NotNull
    TacticalCommandArgumentMapping argument(@NotNull String name);
    @NotNull
    TacticalCommandArgumentMapping argument(@NotNull String name, @NotNull Object defaultValue);
    @NotNull
    TacticalCommandArgumentMapping argument(@NotNull String name, @NotNull Supplier<Object> defaultValue);
    boolean ifArgumentPresent(@NotNull String name, @NotNull Consumer<TacticalCommandArgumentMapping> ifPresent);
    boolean hasArgument(@NotNull String name);
    @NotNull
    String commandString();
    @NotNull
    String[] rawArguments();
    @NotNull
    List<TacticalCommandArgumentMapping> arguments();
    void throwIncorrectUsageException();
}
