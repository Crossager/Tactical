package net.crossager.tactical.api.config;

import net.crossager.tactical.api.TacticalConfigs;
import net.crossager.tactical.api.config.items.TacticalConfigSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public interface TacticalConfig extends TacticalConfigSection {
    /**
     * Reads data from the file and stores it into this {@link TacticalConfig}
     * @throws IOException if the read operation fails
     * @see TacticalConfig#loadRaw(boolean, String)
     */
    void reload() throws IOException;
    void reload(boolean reset) throws IOException;
    void loadRaw(boolean reset, String raw);
    void save() throws IOException;
    @NotNull
    String saveAsString() throws IOException;
    @NotNull
    TacticalConfigSerializer serializer();
    void serializer(TacticalConfigSerializer serializer);
    @NotNull
    TacticalConfigOptions options();
    @NotNull
    File file();

    /**
     * Creates a new instance of a {@link TacticalConfig}
     * @param type the way you want data to be stored
     * @param file the file to store data in
     * @return a new instance of a {@link TacticalConfig}
     * @throws IOException either if the file path is malformed, or if the JVM does not have permission to read/write the specified file
     * @see TacticalConfigFactory#create(TacticalConfigSerializer, File)
     */
    @NotNull
    static TacticalConfig create(TacticalConfigSerializer type, File file) throws IOException {
        return TacticalConfigs.getInstance().getConfigFactory().create(type, file);
    }
    /**
     * Creates a new instance of a {@link TacticalConfig}
     * @param type the way you want data to be stored
     * @param path the path to the file to store data in
     * @return a new instance of a {@link TacticalConfig}
     * @throws IOException either if the file path is malformed, or if the JVM does not have permission to read/write the specified file
     * @see TacticalConfigFactory#create(TacticalConfigSerializer, String)
     */
    @NotNull
    static TacticalConfig create(TacticalConfigSerializer type, String path) throws IOException {
        return TacticalConfigs.getInstance().getConfigFactory().create(type, path);
    }
    /**
     * Creates a new instance of a {@link TacticalConfig}
     * @param type the way you want data to be stored
     * @param parent the folder to put the file in
     * @param path the path to the file to store data in
     * @return a new instance of a {@link TacticalConfig}
     * @throws IOException either if the file path is malformed, or if the JVM does not have permission to read/write the specified file
     * @see TacticalConfigFactory#create(TacticalConfigSerializer, File, String)
     */
    @NotNull
    static TacticalConfig create(TacticalConfigSerializer type, File parent, String path) throws IOException {
        return TacticalConfigs.getInstance().getConfigFactory().create(type, parent, path);
    }
}
