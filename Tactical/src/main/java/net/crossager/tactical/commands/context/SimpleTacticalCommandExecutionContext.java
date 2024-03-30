package net.crossager.tactical.commands.context;

import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentMapping;
import net.crossager.tactical.api.commands.context.TacticalCommandExecutionContext;
import net.crossager.tactical.api.data.LazyInitializer;
import net.crossager.tactical.api.util.FunctionUtils;
import net.crossager.tactical.commands.IncorrectUsageException;
import net.crossager.tactical.commands.argument.SimpleTacticalCommandArgumentMapping;
import net.crossager.tactical.util.Exceptions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleTacticalCommandExecutionContext implements TacticalCommandExecutionContext {
    private final CommandSender sender;
    private final List<TacticalCommandArgumentMapping> arguments;
    private final String[] rawArgs;
    private final LazyInitializer<String> commandString;

    public SimpleTacticalCommandExecutionContext(CommandSender sender, List<TacticalCommandArgumentMapping> arguments, String[] rawArgs) {
        this.sender = sender;
        this.arguments = arguments;
        this.rawArgs = rawArgs;
        this.commandString = new LazyInitializer<>(() -> String.join(" ", rawArgs));
    }

    @Override
    public @NotNull CommandSender sender() {
        return sender;
    }

    @Override
    public @NotNull Player playerSender() {
        if (!(sender instanceof Player)) return Exceptions.lazyNoValue();
        return (Player) sender;
    }

    @Override
    public @NotNull TacticalCommandArgumentMapping argument(@NotNull String name) {
        return arguments.stream().filter(argument -> argument.name().equals(name)).findAny().orElseThrow();
    }

    @Override
    public @NotNull TacticalCommandArgumentMapping argument(@NotNull String name, @NotNull Object defaultValue) {
        return argument(name, FunctionUtils.supplier(defaultValue));
    }

    @Override
    public @NotNull TacticalCommandArgumentMapping argument(@NotNull String name, @NotNull Supplier<Object> defaultValue) {
        return arguments.stream().filter(argument -> argument.name().equals(name)).findAny().orElseGet(() -> new SimpleTacticalCommandArgumentMapping(name, defaultValue.get()));
    }

    @Override
    public boolean ifArgumentPresent(@NotNull String name, @NotNull Consumer<TacticalCommandArgumentMapping> ifPresent) {
        Optional<TacticalCommandArgumentMapping> optional = arguments.stream().filter(argument -> argument.name().equals(name)).findAny();
        optional.ifPresent(ifPresent);
        return optional.isPresent();
    }

    @Override
    public boolean hasArgument(@NotNull String name) {
        return arguments.stream().anyMatch(argument -> argument.name().equals(name));
    }

    @Override
    public @NotNull String commandString() {
        return commandString.get();
    }

    @Override
    public @NotNull String[] rawArguments() {
        return rawArgs;
    }

    @Override
    public @NotNull List<TacticalCommandArgumentMapping> arguments() {
        return arguments;
    }

    @Override
    public void throwIncorrectUsageException() {
        throw new IncorrectUsageException();
    }
}
