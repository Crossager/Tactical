package net.crossager.tactical.util;

import net.crossager.tactical.gui.GUIUtils;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.IntFunction;

public class Array2D<E> {
    private final E[][] internalArray;
    private final int height;
    private final IntFunction<E[]> arrayConstructor;
    private E defaultvalue = null;

    public Array2D(int width, int height, IntFunction<E[]> arrayConstructor, IntFunction<E[][]> largeArrayConstructor) {
        this.internalArray = largeArrayConstructor.apply(width);
        this.height = height;
        this.arrayConstructor = arrayConstructor;
    }

    public Array2D(int width, int height, IntFunction<E[]> arrayConstructor, IntFunction<E[][]> largeArrayConstructor, E defaultvalue) {
        this.defaultvalue = defaultvalue;
        this.internalArray = largeArrayConstructor.apply(width);
        this.height = height;
        this.arrayConstructor = arrayConstructor;
    }

    @SuppressWarnings("unchecked")
    public E get(int x, int y) {
        return (E) row(x)[y];
    }

    public void set(int x, int y, E element) {
        row(x)[y] = element == null ? defaultvalue : element;
    }

    public void set(List<E> list) {
        for (int x = 0; x < internalArray.length; x++) {
            for (int y = 0; y < height; y++) {
                int index = GUIUtils.xyToSlot(x, y, internalArray.length);
                set(x, y, list.size() <= index ? defaultvalue : list.get(index));
            }
        }
    }

    private Object[] row(int x) {
        if (internalArray[x] == null) {
            E[] arr = arrayConstructor.apply(height);
            if (defaultvalue != null)
                Arrays.fill(arr, defaultvalue);
            return internalArray[x] = arr;
        }
        return internalArray[x];
    }

    public E[][] asArrays() {
        return internalArray;
    }

    public List<E> asList() {
        return new ArrayList2D();
    }

    private class ArrayList2D extends AbstractList<E> implements RandomAccess {
        @Override
        public E get(int index) {
            return Array2D.this.get(
                    GUIUtils.x(index, internalArray.length),
                    GUIUtils.y(index, internalArray.length)
            );
        }

        @Override
        public int size() {
            return internalArray.length * height;
        }
    }
}
