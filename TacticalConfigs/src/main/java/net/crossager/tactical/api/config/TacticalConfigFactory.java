package net.crossager.tactical.api.config;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public interface TacticalConfigFactory {
    /**
     * Creates a new {@link TacticalConfig}
     * @param type the way you want data to be stored
     * @param file the file to store data in
     * @return a new instance of a {@link TacticalConfig}
     * @throws IOException either if the file path is malformed, or if the JVM does not have permission to read/write the current file
     */
    @NotNull
    TacticalConfig create(@NotNull TacticalConfigSerializer type, @NotNull File file) throws IOException;
    /**
     * Creates a new {@link TacticalConfig}
     * @param type the way you want data to be stored
     * @param path the path to the file to store data in
     * @return a new instance of a FileConfig
     * @throws IOException either if the file path is malformed, or if the JVM does not have permission to read/write the current file
     */
    @NotNull
    TacticalConfig create(@NotNull TacticalConfigSerializer type, @NotNull String path) throws IOException;
    /**
     * Creates a new {@link TacticalConfig}
     * @param type the way you want data to be stored
     * @param parent the folder to put the file in
     * @param path the path to the file to store data in
     * @return a new instance of a {@link TacticalConfig}
     * @throws IOException either if the file path is malformed, or if the JVM does not have permission to read/write the current file
     */
    @NotNull
    TacticalConfig create(@NotNull TacticalConfigSerializer type, @NotNull File parent, @NotNull String path) throws IOException;
}
