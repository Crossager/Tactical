package net.crossager.tactical.api.wrappers;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A specialized {@link java.util.ArrayList} that ensures the elements stored are always non-null.
 * If a null value is attempted to be added, the default element specified in the constructor will be added instead.
 *
 * @param <E> the type of the elements stored in the list
 */
public class NotNullArrayList<E> extends ArrayList<E> {
    private final E defaultElement;

    /**
     * Constructs a new NotNullArrayList with the specified default element and initial capacity.
     * @param defaultElement the default element to use when a null element is added
     * @param initialCapacity the initial capacity of the list
     */
    public NotNullArrayList(@NotNull E defaultElement, int initialCapacity) {
        super(initialCapacity);
        this.defaultElement = defaultElement;
    }

    /**
     * Constructs a new NotNullArrayList with the specified default element.
     * @param defaultElement the default element to use when a null element is added
     * */
    public NotNullArrayList(@NotNull E defaultElement) {
        this.defaultElement = defaultElement;
    }

    /**
     * Constructs a new NotNullArrayList with the specified default element and the elements of the specified collection.
     * @param defaultElement the default element to use when a null element is added
     * @param c the collection whose elements are to be placed into this list
     */
    public NotNullArrayList(@NotNull E defaultElement, @NotNull Collection<? extends E> c) {
        super(c);
        this.defaultElement = defaultElement;
    }

    /**
     * Returns the element at the specified position in this list, ensuring it is non-null.
     * @param index the index of the element to return
     * @return the non-null element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @NotNull
    @Override
    public E get(int index) {
        return getNotNullValue(super.get(index));
    }

    private E getNotNullValue(E nullableValue) {
        return nullableValue == null ? defaultElement : nullableValue;
    }
}