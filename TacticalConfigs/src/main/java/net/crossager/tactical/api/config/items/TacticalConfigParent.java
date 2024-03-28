package net.crossager.tactical.api.config.items;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface TacticalConfigParent {
    /**
     * Gets the {@link TacticalConfigSection} from this parent object
     * @return the parent as a {@link TacticalConfigSection}
     * @throws java.util.NoSuchElementException if the parent is not a {@link TacticalConfigSection}
     * @see TacticalConfigParent#isSection()
     */
    @NotNull
    TacticalConfigSection asSection();

    /**
     * Gets the {@link TacticalConfigList} from this parent object
     * @return the parent as a {@link TacticalConfigList}
     * @throws java.util.NoSuchElementException if the parent is not a {@link TacticalConfigList}
     * @see TacticalConfigParent#isList()
     */
    @NotNull
    TacticalConfigList asList();

    /**
     * Returns {@code true} if the held parent object is a {@link TacticalConfigSection}
     * @return if the parent is a {@link TacticalConfigSection}
     * @see TacticalConfigParent#asSection()
     */
    boolean isSection();

    /**
     * Returns {@code true} if the held parent object is a {@link TacticalConfigList}
     * @return if the parent is a {@link TacticalConfigList}
     * @see TacticalConfigParent#asList() ()
     */
    boolean isList();

    /**
     * Returns the parent to this parent object. This is either a {@link TacticalConfigList} or a {@link TacticalConfigSection}, masked as a {@link TacticalConfigParent}
     * @return the {@link TacticalConfigParent} of this list
     * @throws java.util.NoSuchElementException if the current item has no parents
     * @see TacticalConfigParent#hasParent()
     */
    @NotNull
    TacticalConfigParent parent();

    /**
     * Returns weather this parent object has a parent
     * @return if this parent object has a parent
     * @see TacticalConfigParent#parent()
     */
    boolean hasParent();
}
