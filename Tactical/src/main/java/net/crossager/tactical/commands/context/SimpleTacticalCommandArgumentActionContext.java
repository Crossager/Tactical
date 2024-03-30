package net.crossager.tactical.commands.context;

import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import net.crossager.tactical.api.data.LazyInitializer;
import net.crossager.tactical.util.Exceptions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class SimpleTacticalCommandArgumentActionContext implements TacticalCommandArgumentActionContext {
    private final CommandSender sender;
    private final List<String> arguments;
    private final LazyInitializer<String> argumentString;

    protected SimpleTacticalCommandArgumentActionContext(CommandSender sender, String... arguments) {
        this.sender = sender;
        this.arguments = Arrays.asList(arguments);
        this.argumentString = new LazyInitializer<>(() -> String.join(" ", arguments));
    }

    @Override
    public @NotNull CommandSender sender() {
        return sender;
    }

    @Override
    public @NotNull Player playerSender() {
        if (!isPlayerSender()) return Exceptions.lazyNoValue();
        return (Player) sender;
    }

    @Override
    public boolean isPlayerSender() {
        return sender instanceof Player;
    }

    @Override
    public @NotNull List<String> arguments() {
        return arguments;
    }

    @Override
    public @NotNull String argument() {
        return arguments.get(0);
    }

    @Override
    public @NotNull String argumentString() {
        return argumentString.get();
    }

    @Override
    public void throwParseException(@NotNull String message) {
        throw new RuntimeException(message);
    }

    public static SimpleTacticalCommandArgumentActionContext of(CommandSender sender, int startArgumentIndex, int endArgumentIndex, String... arguments) {
        if (startArgumentIndex == endArgumentIndex) return new SimpleTacticalCommandArgumentActionContext(sender, arguments[startArgumentIndex]);
        return new SimpleTacticalCommandArgumentActionContext(sender, Arrays.copyOfRange(arguments, startArgumentIndex, endArgumentIndex));
    }
    public static SimpleTacticalCommandArgumentActionContext of(CommandSender sender, String... arguments) {
        return new SimpleTacticalCommandArgumentActionContext(sender, arguments);
    }
}
