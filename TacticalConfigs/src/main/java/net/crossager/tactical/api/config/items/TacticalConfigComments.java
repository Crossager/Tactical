package net.crossager.tactical.api.config.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.List;

public interface TacticalConfigComments {
    /**
     * Returns the comments of the current {@link TacticalConfigValue}
     * @return the comments of the current {@link TacticalConfigValue}
     */
    @NotNull
    List<String> comments();

    /**
     * Checks to see if any comments are present in the current {@link TacticalConfigValue}
     * @return weather the current {@link TacticalConfigValue} contains any comments
     */
    boolean hasComments();

    /**
     * Adds a single, or more comments to the current {@link TacticalConfigValue}
     * @param comment the comment to add
     * @param comments multiple comments to add
     * @see TacticalConfigComments#addComments(Collection)
     */
    void addComments(@NotNull String comment, @NotNull String... comments);
    void addComments(@NotNull Collection<String> comments);
    void removeComments();
    @NotNull
    List<String> inlineComments();
    boolean hasInlineComments();
    void addInlineComments(@NotNull String inlineComment, @NotNull String... inlineComments);
    void addInlineComments(@NotNull Collection<String> comments);
    void removeInlineComments();
}
