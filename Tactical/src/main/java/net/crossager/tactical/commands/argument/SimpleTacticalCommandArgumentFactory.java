package net.crossager.tactical.commands.argument;

import net.crossager.tactical.api.commands.TacticalSubCommand;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentFactory;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentParser;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgumentTabCompleter;
import net.crossager.tactical.api.commands.context.TacticalCommandArgumentActionContext;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleTacticalCommandArgumentFactory implements TacticalCommandArgumentFactory {
    private TacticalCommandArgument createSimple(String name, Function<String, Object> parser) {
        return create(name, TacticalCommandArgumentTabCompleter.NONE, context -> parser.apply(context.argument()));
    }

    @Override
    public @NotNull SimpleTacticalCommandArgument create(@NotNull String name, @NotNull TacticalCommandArgumentTabCompleter tabCompleter, @NotNull TacticalCommandArgumentParser parser) {
        return new SimpleTacticalCommandArgument(name, tabCompleter, parser);
    }

    @Override
    public <T> @NotNull TacticalCommandArgument createValues(@NotNull String name, @NotNull Supplier<List<? extends T>> valueSupplier, @NotNull Function<T, String> toStringFunction, @NotNull Function<String, T> fromStringFunction) {
        return create(name, context -> valueSupplier.get().stream().map(toStringFunction).toList(), (context -> fromStringFunction.apply(context.argument())));
    }

    @Override
    public @NotNull TacticalCommandArgument stringValues(@NotNull String name, @NotNull List<String> values) {
        return createValues(name, () -> values, Function.identity(), s -> values.contains(s) ? s : null);
    }

    @Override
    public @NotNull TacticalCommandArgument material(@NotNull String name) {
        return createValues(name, () -> Arrays.asList(Material.values()), material -> material.name().toLowerCase(), string -> Material.getMaterial(string.toUpperCase()));
    }

    @Override
    public @NotNull TacticalCommandArgument player(@NotNull String name) {
        return create(name, context -> {
            if (context.isPlayerSender())
                return Bukkit.getOnlinePlayers().stream().filter(player -> context.playerSender().canSee(player)).map(Player::getName).toList();
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }, context -> Bukkit.getPlayerExact(context.argument()));
    }

    @Override
    public @NotNull TacticalCommandArgument integer(@NotNull String name) {
        return createSimple(name, Integer::parseInt);
    }

    @Override
    public @NotNull TacticalCommandArgument bool(@NotNull String name) {
        return createSimple(name, Boolean::parseBoolean);
    }

    @Override
    public @NotNull TacticalCommandArgument decimal(@NotNull String name) {
        return createSimple(name, Double::parseDouble);
    }

    @Override
    public @NotNull TacticalCommandArgument string(@NotNull String name) {
        return createSimple(name, String::valueOf);
    }

    @Override
    public @NotNull TacticalCommandArgument message(@NotNull String name) {
        return create(name, TacticalCommandArgumentTabCompleter.NONE, TacticalCommandArgumentActionContext::argumentString).anyArgumentLength(true);
    }

    @Override
    public @NotNull TacticalCommandArgument location(@NotNull String name, int decimals) {
        return new TacticalLocationArgument(name, decimals);
    }

    @Override
    public TacticalCommandArgument subCommands(@NotNull List<TacticalSubCommand> subCommands) {
        return new SimpleTacticalSubCommandArgument(subCommands);
    }

    @Override
    public @NotNull TacticalCommandArgument merge(@NotNull List<TacticalCommandArgument> arguments) {
        return new MergedTacticalCommandArgument(arguments);
    }
}
