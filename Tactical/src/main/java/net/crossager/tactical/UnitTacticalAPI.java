package net.crossager.tactical;

import net.crossager.tactical.api.TacticalAPI;
import net.crossager.tactical.api.TacticalRegistrar;
import net.crossager.tactical.api.config.TacticalConfigFactory;
import net.crossager.tactical.api.config.TacticalConfigSerializer;
import net.crossager.tactical.config.SimpleTacticalConfigFactory;
import net.crossager.tactical.config.serializers.JsonSerializer;
import net.crossager.tactical.config.serializers.XmlSerializer;
import net.crossager.tactical.config.serializers.YamlSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public class UnitTacticalAPI implements TacticalAPI {
    public static void init() {
        TacticalRegistrar.setInstance(new UnitTacticalAPI());
    }
    public static void init(Logger logger) {
        TacticalRegistrar.setInstance(new UnitTacticalAPI(logger));
    }

    public UnitTacticalAPI(Logger logger) {
        this.logger = logger;
    }

    public UnitTacticalAPI() {
        this(Logger.getLogger("Tactical"));
    }

    private final SimpleTacticalConfigFactory configFactory = new SimpleTacticalConfigFactory();
    private final List<TacticalConfigSerializer> configSerializers = List.of(new JsonSerializer(), new XmlSerializer(), new YamlSerializer());
    private final Logger logger;

    @Override
    public @NotNull TacticalConfigFactory getConfigFactory() {
        return configFactory;
    }

    @Override
    public @NotNull List<TacticalConfigSerializer> getConfigSerializers() {
        return configSerializers;
    }

    @Override
    public @NotNull Logger getLogger() {
        return logger;
    }
}
