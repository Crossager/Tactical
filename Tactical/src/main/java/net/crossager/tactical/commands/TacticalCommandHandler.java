package net.crossager.tactical.commands;

import net.crossager.tactical.api.commands.InvalidArgumentException;
import net.crossager.tactical.api.commands.TabCompletionStrategy;
import net.crossager.tactical.api.commands.TacticalCommand;
import net.crossager.tactical.api.commands.TacticalSubCommand;
import net.crossager.tactical.api.commands.argument.ArgumentPreconditionException;
import net.crossager.tactical.api.commands.argument.TacticalCommandArgument;
import net.crossager.tactical.api.util.TacticalUtils;
import net.crossager.tactical.commands.argument.MergedTacticalCommandArgument;
import net.crossager.tactical.commands.argument.SimpleTacticalCommandArgumentMapping;
import net.crossager.tactical.commands.context.SimpleTacticalCommandArgumentActionContext;
import net.crossager.tactical.commands.context.SimpleTacticalCommandArgumentTabCompletionContext;
import net.crossager.tactical.commands.context.SimpleTacticalCommandExecutionContext;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TacticalCommandHandler extends Command implements TabCompleter, CommandExecutor {
    private final TacticalCommand command;

    public TacticalCommandHandler(TacticalCommand command) {
        super(command.options().name());
        setAliases(command.options().aliases());
        this.command = command;
        this.description = command.options().description();
        this.usageMessage = command.options().usage();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        try {
            if (command.options().isPlayerOnly() && !(sender instanceof Player)) {
                sender.sendMessage("§cCommand is player only!");
                return true;
            }
            List<SimpleTacticalCommandArgumentMapping> mappings;
            try {
                mappings = getMappings(command.arguments(), sender, args, 0);
            } catch (InvalidArgumentException e) {
                if (e.isParseException() && System.getProperty("tactical.debug") != null) {
                    System.err.printf("Error parsing argument %s for input %s%n", e.argument().name(), e.input());
                    e.printStackTrace();
                }
                command.options().errorHandler().handle(e, sender, args, label);
                return true;
            }
            SimpleTacticalCommandExecutionContext executionContext = new SimpleTacticalCommandExecutionContext(sender, TacticalUtils.mapList(mappings, x -> x), args);
            command.commandExecutor().execute(executionContext);
            mappings.forEach(mapping -> mapping.asSubCommand().ifPresent(subCommand -> subCommand.commandExecutor().execute(executionContext)));
            return true;
        } catch (IncorrectUsageException e) {
            return false;
        }
    }

    private List<SimpleTacticalCommandArgumentMapping> getMappings(List<TacticalCommandArgument> arguments, CommandSender sender, String[] args, int argumentIndex) {
        List<SimpleTacticalCommandArgumentMapping> mappings = new ArrayList<>();
        for (TacticalCommandArgument argument : arguments) {
            SimpleTacticalCommandArgumentMapping mapping = parse(argument, sender, args, argumentIndex);
            if (mapping == null) break;
            argument = mapping.argument();
            argumentIndex += argument.argumentLength();
            if (argument.anyArgumentLength()) argumentIndex = args.length;
            mappings.add(mapping);
            if (mapping.asSubCommand().isPresent())
                return TacticalUtils.addTo(mappings, getMappings(mapping.asSubCommand().get().arguments(), sender, args, argumentIndex));
        }
        if (args.length != argumentIndex) throw new InvalidArgumentException(args.length, "Too many arguments");
        return mappings;
    }

    private SimpleTacticalCommandArgumentMapping parse(TacticalCommandArgument argument, CommandSender sender, String[] args, int argumentIndex) {
        if (argument instanceof MergedTacticalCommandArgument merged) {
            if (argumentIndex + 1 > args.length) {
                if (argument.isRequired())
                    throw new InvalidArgumentException(argumentIndex, "Not enough arguments");
                else
                    return null;
            }
            for (TacticalCommandArgument newArgument : merged.arguments()) {
                try {
                    SimpleTacticalCommandArgumentMapping parse = parse(newArgument, sender, args, argumentIndex);
                    if (parse == null) continue;
                    return parse;
                } catch (InvalidArgumentException ignored) {

                }
            }
            argumentIndex++;
            throw InvalidArgumentException.parseException(
                    argumentIndex,
                    argument,
                    List.of(args[argumentIndex - 1])
            );
        }
        if (argumentIndex + argument.argumentLength() > args.length) {
            if (argument.isRequired())
                throw new InvalidArgumentException(argumentIndex, "Not enough arguments");
            else
                return null;
        }
        int endArgumentIndex = argument.anyArgumentLength() ?
                args.length:
                argument.argumentLength() + argumentIndex;
        SimpleTacticalCommandArgumentActionContext context =
                SimpleTacticalCommandArgumentActionContext.of(
                        sender,
                        argumentIndex,
                        endArgumentIndex,
                        args
                );
        Object data;
        try {
            data = argument.parse(context);
        } catch (Exception e) {
            throw InvalidArgumentException.parseException(
                    argumentIndex,
                    argument,
                    context.arguments()
            );
        }
        if (data == null)
            throw InvalidArgumentException.parseException(
                    endArgumentIndex,
                    argument,
                    context.arguments()
            );
        SimpleTacticalCommandArgumentMapping mapping = new SimpleTacticalCommandArgumentMapping(argument, data);
        try {
            argument.preconditions().forEach(precondition -> precondition.validate(mapping));
        } catch (ArgumentPreconditionException e) {
            throw new InvalidArgumentException(
                    endArgumentIndex,
                    e.getMessage().formatted(argument.formattedName()),
                    argument,
                    context.arguments(),
                    null,
                    false
            );
        }
        return mapping;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
        if (command.options().tabCompletionStrategy() == TabCompletionStrategy.NONE) return List.of();
        if (command.options().tabCompletionStrategy() == TabCompletionStrategy.BUKKIT) return super.tabComplete(sender, alias, args, location);
        if (args.length == 0) return List.of();
        ArgumentData argumentData = getArgumentIndexForIndex(command.arguments(), args, sender, 0);
        if (argumentData.argument().isEmpty()) return List.of();
        List<String> tabCompleteList =
                new ArrayList<>(
                        argumentData.argument().get().tabComplete(
                                SimpleTacticalCommandArgumentTabCompletionContext.of(
                                        location, sender,
                                        argumentData.argumentBeginIndex() - 1,
                                        args.length - 1,
                                        args
                                )));
        if (argumentData.argument().get().shouldAutoFilterTabCompletion())
            tabCompleteList.removeIf(line -> !StringUtil.startsWithIgnoreCase(line, args[args.length - 1]));
        return tabCompleteList;
    }

    private ArgumentData getArgumentIndexForIndex(List<TacticalCommandArgument> arguments, String[] args, CommandSender sender, int index) {
        for (TacticalCommandArgument argument : arguments) {
            if (argument.anyArgumentLength()) return new ArgumentData(Optional.of(argument), args.length);
            index += argument.argumentLength();
            if (index >= args.length) return new ArgumentData(Optional.of(argument), args.length);
            if (argument.isSubCommand()) {
                TacticalSubCommand subCommand = (TacticalSubCommand) argument.parse(SimpleTacticalCommandArgumentActionContext.of(sender, args[index - 1]));
                if (subCommand == null) return new ArgumentData(Optional.empty(), 0);
                return getArgumentIndexForIndex(subCommand.arguments(), args, sender, index);
            }
        }
        return new ArgumentData(Optional.empty(), 0);
    }

    private record ArgumentData(Optional<TacticalCommandArgument> argument, int argumentBeginIndex) {}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return execute(sender, label, args);
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return tabComplete(sender, "", args, null);
    }
}
