package net.crossager.tactical.api.config.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public interface TacticalConfigSection extends Cloneable {
    /**
     * Max path length when doing get/set
     */
    int MAX_PATH_LENGTH = 256;

    /**
     * Returns a list that contains the all ancestors of this section, listed with the deepest value at index 0. This works like chained {@link TacticalConfigParent#parent()} calls.
     * @return the ancestors of this section
     */
    @NotNull
    @Unmodifiable
    List<TacticalConfigParent> ancestors();

    /**
     * Returns the parent to this section. This is either a {@link TacticalConfigList} or a {@link TacticalConfigSection}, masked as a {@link TacticalConfigParent}
     * @return the {@link TacticalConfigParent} of this section
     * @throws java.util.NoSuchElementException if the current item has no parents
     * @see TacticalConfigSection#hasParent()
     */
    @NotNull
    TacticalConfigParent parent();

    /**
     * Returns weather this list has a parent
     * @return if this list has a parent
     * @see TacticalConfigSection#parent()
     */
    boolean hasParent();

    /**
     * Sets a value in the config hierarchy.
     * @param path path to where you want the value to be stored
     * @param value value to set in the config tree
     * @return the {@link TacticalConfigValue} holding the value
     * @see TacticalConfigSection#addSection(String)
     * @see TacticalConfigSection#addList(String)
     */
    @NotNull
    TacticalConfigValue set(@NotNull String path, @NotNull Object value);

    /**
     * Gets a value from the config hierarchy
     * @param path path to where the value is stored
     * @return a {@link TacticalConfigValue} containing the value held at the position
     * @see TacticalConfigSection#isSet(String)
     */
    @NotNull
    TacticalConfigValue get(@NotNull String path);

    /**
     * Gets a value from the config hierarchy
     * @param path path to where the value is stored
     * @return at the value at the specified path
     * @see TacticalConfigSection#isSet(String)
     * @throws java.util.NoSuchElementException if no value is present
     */
    @NotNull
    Object getAny(@NotNull String path);

    /**
     * Removes a value from the config hierarchy.
     * @param path path the value you want to remove
     * @return if the value was present in the config hierarchy
     */
    boolean remove(@NotNull String path);

    /**
     * Checks if a value is present in the config hierarchy
     * @param path the path to check if a value is present at
     * @return if a value is present at the location
     */
    boolean isSet(@NotNull String path);

//    /**
//     * Gets the comments at a specified path
//     * @param path the path to where the comments are stored
//     * @return the comments at the specified path
//     * @see TacticalConfigValue#comments()
//     */
//    @NotNull
//    TacticalConfigComments comments(@NotNull String path);

    /**
     * Creates a {@link TacticalConfigSection} at the specified path
     * @param path path to where the {@link TacticalConfigSection} will be created
     * @return the {@link TacticalConfigSection} created at the path
     * @see TacticalConfigValue#makeSection()
     */
    @NotNull
    TacticalConfigSection addSection(@NotNull String path);

    /**
     * Creates a {@link TacticalConfigList} at the specified path
     * @param path path to where the {@link TacticalConfigList} will be created
     * @return the {@link TacticalConfigList} created at the path
     * @see TacticalConfigValue#makeList()
     */
    @NotNull
    TacticalConfigList addList(@NotNull String path);

    /**
     * Returns the children of this section, mapped by key to child
     * @return this section's children
     * @see TacticalConfigSection#clearChildren()
     */
    @NotNull
    @Unmodifiable
    Map<String, TacticalConfigValue> children();

    /**
     * Removes all the children from this section
     * @see TacticalConfigSection#children() ()
     */
    void clearChildren();

    /**
     * Returns an identical copy of this section
     * @return a copy of this section
     */
    @NotNull
    TacticalConfigSection clone();
}
