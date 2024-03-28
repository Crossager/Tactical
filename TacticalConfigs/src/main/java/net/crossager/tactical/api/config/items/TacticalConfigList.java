package net.crossager.tactical.api.config.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

@SuppressWarnings("unused")
public interface TacticalConfigList extends Iterable<TacticalConfigValue> {
    /**
     * Returns a list that contains the all ancestors of this list, listed with the deepest value at index 0. This works like chained {@link TacticalConfigParent#parent()} calls.
     * @return the ancestors of this list
     */
    @NotNull
    @Unmodifiable
    List<TacticalConfigParent> ancestors();

    /**
     * Returns the parent to this list. This is either a {@link TacticalConfigList} or a {@link TacticalConfigSection}, masked as a {@link TacticalConfigParent}
     * @return the {@link TacticalConfigParent} of this list
     * @throws java.util.NoSuchElementException if the current item has no parents
     * @see TacticalConfigList#hasParent()
     */
    @NotNull
    TacticalConfigParent parent();

    /**
     * Returns weather this list has a parent
     * @return if this list has a parent
     * @see TacticalConfigList#parent()
     */
    boolean hasParent();

    /**
     * Removes this list from the current config hierarchy
     * @see TacticalConfigList#exists()
     */
    void remove();

    /**
     * Checks to see if this list exists in the current config hierarchy
     * @return if the current item exists
     * @see TacticalConfigList#remove()
     */
    boolean exists();

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     * @see List#get(int)
     */
    @NotNull
    TacticalConfigValue get(int index);

    /**
     * Removes all elements from this list
     * @see List#clear()
     */
    void clear();

    /**
     * Returns the number of elements in this list.  If this list contains
     * more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this list
     * @see List#size()
     */
    int size();

    /**
     * Returns a representation of the current list as a {@link java.util.List}
     * @return a {@link java.util.List} representation of this object
     */
    @Unmodifiable
    @NotNull
    <T> List<T> toList(Class<T> type);

    /**
     * Appends a {@link TacticalConfigSection} to the end of this list
     *
     * @return the {@link TacticalConfigSection} added to this
     */
    @NotNull
    TacticalConfigSection addSection();
    /**
     * Replaces the value at the specified position of this list with a {@link TacticalConfigSection}
     *
     * @param index index of the value to replace
     * @return the {@link TacticalConfigSection} put into the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    @NotNull
    TacticalConfigSection setSection(int index);
    /**
     * Appends the specified element to the end of this list
     *
     * @param value element to be appended to this list
     * @return the {@link TacticalConfigValue} holding the value appended to the list
     * @see List#add(Object)
     */
    @NotNull
    TacticalConfigValue addValue(@NotNull Object value);
    /**
     * Replaces the value at the specified position in this list with the
     * specified value
     *
     * @param index index of the value to replace
     * @param value value to be stored at the specified position
     * @return the {@link TacticalConfigValue} holding the value appended to the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     * @see List#set(int, Object)
     */
    @NotNull
    TacticalConfigValue setValue(int index, @NotNull Object value);
    /**
     * Appends a {@link TacticalConfigList} to the end of this list.
     * This works the same as
     * {@code TacticalConfigList.addValue(new Object[0])}
     *
     * @return the {@link TacticalConfigList} appended to this list
     * @see TacticalConfigList#addValue(Object)
     */
    @NotNull
    TacticalConfigList addList();
    /**
     * Replaces the element at the specified position in this list with a {@link TacticalConfigList}
     *
     * @param index index of the value to replace
     * @return the {@link TacticalConfigList} holding the value appended to the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    @NotNull
    TacticalConfigList setList(int index);
    /**
     * Removes the element at the specified position in this list (optional
     * operation).  Shifts any subsequent elements to the left (subtracts one
     * from their indices).  Returns the element that was removed from the
     * list.
     *
     * @param index the index of the element to be removed
     * @return the {@link TacticalConfigValue} containing the value previously stored at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     * @see List#remove(int)
     */
    @NotNull
    TacticalConfigValue remove(int index);
    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present
     *
     * @param element value to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    boolean remove(@NotNull TacticalConfigValue element);
    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present
     *
     * @param o value to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    boolean remove(@NotNull Object o);
}
