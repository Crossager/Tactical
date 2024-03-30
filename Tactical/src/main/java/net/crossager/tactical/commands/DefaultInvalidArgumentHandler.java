package net.crossager.tactical.commands;

import net.crossager.tactical.api.commands.InvalidArgumentException;
import net.crossager.tactical.api.commands.TacticalCommandInvalidArgumentHandler;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DefaultInvalidArgumentHandler implements TacticalCommandInvalidArgumentHandler {
    private static final DefaultInvalidArgumentHandler instance = new DefaultInvalidArgumentHandler();

    private DefaultInvalidArgumentHandler() {}

    @Override
    public void handle(@NotNull InvalidArgumentException exception, @NotNull CommandSender sender, String[] args, @NotNull String label) {
        if (exception.isParseException()) {
            sender.sendMessage("§cCommand error: Invalid argument %s".formatted(exception.argument().formattedName()));
        } else {
            sender.sendMessage("§cCommand error: %s".formatted(exception.getMessage()));
        }
        StringBuilder builder = new StringBuilder("/");
        builder.append(label);
        if (args.length != 0) {
            int endArguments = exception.isParseException() ? exception.argument().argumentLength() : 1;
            for (int i = 0; i < exception.argumentIndex(); i++) {
                builder.append(' ');
                if (i == exception.argumentIndex() - endArguments)
                    builder.append("§c§n");
                builder.append(args[i]);
            }
        }
        sender.sendMessage("§7%s§c§o<--[HERE]".formatted(builder));
    }

    public static DefaultInvalidArgumentHandler getInstance() {
        return instance;
    }
}
