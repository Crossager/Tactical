package net.crossager.tactical.api.commands.context;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface TacticalCommandArgumentTabCompletionContext extends TacticalCommandArgumentActionContext {
    boolean hasTargetLocation();
    @NotNull Location targetLocation();
}
