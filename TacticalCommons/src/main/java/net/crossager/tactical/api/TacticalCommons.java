package net.crossager.tactical.api;

import net.crossager.tactical.api.reflect.TacticalReflectionFactory;
import net.crossager.tactical.api.wrappers.nbt.TacticalNBTManager;
import org.jetbrains.annotations.NotNull;

/**
 * The TacticalCommons interface provides access to various utility classes for
 * working with Minecraft server internals.
 */
public interface TacticalCommons {

    /**
     * Gets the singleton instance of the {@link TacticalCommons} interface.
     *
     * @return The singleton instance of the {@link TacticalCommons} interface.
     */
    @NotNull
    static TacticalCommons getInstance() {
        return CommonsAPIHolder.getInstance();
    }

    /**
     * Returns the {@link TacticalReflectionFactory} associated with this {@link TacticalCommons} instance.
     *
     * @return the {@link TacticalReflectionFactory} associated with this {@link TacticalCommons} instance
     */
    @NotNull
    TacticalReflectionFactory getReflectionFactory();

    /**
     * Returns the {@link TacticalNBTManager} instance associated with this {@link TacticalCommons} instance.
     * @return the {@link TacticalNBTManager} instance associated with this {@link TacticalCommons} instance
     */
    @NotNull
    TacticalNBTManager getNBTManager();

    /**
     * Gets the version of the Minecraft server.
     *
     * @return The version of the Minecraft server.
     */
    @NotNull
    String getServerVersion();
}