package net.crossager.tactical.api;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public interface TacticalAPI extends TacticalConfigs {
    @NotNull
    static TacticalAPI getInstance() {
        return APIHolder.getInstance();
    }
    Logger getLogger();
}
