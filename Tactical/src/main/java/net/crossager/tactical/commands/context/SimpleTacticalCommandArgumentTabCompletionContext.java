package net.crossager.tactical.commands.context;

import net.crossager.tactical.api.commands.context.TacticalCommandArgumentTabCompletionContext;
import net.crossager.tactical.util.Exceptions;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SimpleTacticalCommandArgumentTabCompletionContext extends SimpleTacticalCommandArgumentActionContext implements TacticalCommandArgumentTabCompletionContext {
    private final Location targetLocation;

    protected SimpleTacticalCommandArgumentTabCompletionContext(Location targetLocation, CommandSender sender, String... arguments) {
        super(sender, arguments);
        this.targetLocation = targetLocation;
    }

    @Override
    public boolean hasTargetLocation() {
        return targetLocation != null;
    }

    @Override
    public @NotNull Location targetLocation() {
        if (!hasTargetLocation()) return Exceptions.lazyNoValue();
        return targetLocation;
    }

    public static SimpleTacticalCommandArgumentTabCompletionContext of(Location location, CommandSender sender, int startArgumentIndex, int endArgumentIndex, String... arguments) {
        if (startArgumentIndex == endArgumentIndex) return new SimpleTacticalCommandArgumentTabCompletionContext(location, sender, arguments[startArgumentIndex]);
        return new SimpleTacticalCommandArgumentTabCompletionContext(location, sender, Arrays.copyOfRange(arguments, startArgumentIndex, endArgumentIndex));
    }
    public static SimpleTacticalCommandArgumentTabCompletionContext of(Location location, CommandSender sender, String... arguments) {
        return new SimpleTacticalCommandArgumentTabCompletionContext(location, sender, arguments);
    }
}
