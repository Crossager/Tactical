package net.crossager.tactical.api.data;

import java.util.AbstractList;

/**
 * An implementation of the {@link java.util.List} interface that returns the same element for every index within a fixed size.
 *
 *  @param <E> the type of the element
 */
public class RepeatingList<E> extends AbstractList<E> {
    private final E element;
    private final int size;

    /**
     * Constructs a new instance of {@link RepeatingList} with the specified element and size.
     *
     * @param element the element to be repeated
     * @param size    the size of the list
     */
    public RepeatingList(E element, int size) {
        this.element = element;
        this.size = size;
    }

    @Override
    public E get(int index) {
        return element;
    }

    @Override
    public int size() {
        return size;
    }
}
