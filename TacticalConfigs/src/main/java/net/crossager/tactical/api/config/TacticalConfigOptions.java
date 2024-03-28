package net.crossager.tactical.api.config;

import org.jetbrains.annotations.NotNull;

public interface TacticalConfigOptions {
    /**
     * Returns {@code true} if pretty print is turned on
     * @return if pretty print is turned on
     * @see TacticalConfigOptions#prettyPrint(boolean)
     */
    boolean prettyPrint();
    /**
     * Determines weather the current {@link TacticalConfig} should use pretty print when saving the file
     * @param prettyPrint weather to turn pretty print on, or off
     * @return the current {@link TacticalConfigOptions}
     * @see TacticalConfigOptions#prettyPrint
     */
    @NotNull
    TacticalConfigOptions prettyPrint(boolean prettyPrint);
    /**
     * Returns the tab size used when saving the current {@link TacticalConfig}
     * @return the current tab size used
     * @see TacticalConfigOptions#tabSize(int)
     */
    int tabSize();
    /**
     * Sets the tab size used when saving the current {@link TacticalConfig}
     * @param tabSize the tab size to be used
     * @return the current {@link TacticalConfigOptions}
     * @see TacticalConfigOptions#tabSize()
     */
    @NotNull
    TacticalConfigOptions tabSize(int tabSize);
}
