package net.crossager.tactical.util;

import net.crossager.tactical.gui.GUIUtils;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.IntFunction;

public class Array2D<E> {
    private E[][] internalArray;
    private final int width;
    private final int height;
    private IntFunction<E[]> arrayConstructor;
    private E defaultvalue = null;
    private List<E> internalList;

    public Array2D(int width, int height, IntFunction<E[]> arrayConstructor, IntFunction<E[][]> largeArrayConstructor) {
        this.internalArray = largeArrayConstructor.apply(width);
        this.width = width;
        this.height = height;
        this.arrayConstructor = arrayConstructor;
    }

    public Array2D(int width, int height, IntFunction<E[]> arrayConstructor, IntFunction<E[][]> largeArrayConstructor, E defaultvalue) {
        this.defaultvalue = defaultvalue;
        this.internalArray = largeArrayConstructor.apply(width);
        this.width = width;
        this.height = height;
        this.arrayConstructor = arrayConstructor;
    }

    public Array2D(int width, int height, List<E> internalList, E defaultvalue) {
        this.defaultvalue = defaultvalue;
        this.width = width;
        this.height = height;
        this.internalList = internalList;
    }

    @SuppressWarnings("unchecked")
    public E get(int x, int y) {
        if (internalList != null) {
            int index = GUIUtils.xyToSlot(x, y, width);
            if (index >= internalList.size()) return defaultvalue;
            E e = internalList.get(index);
            if (e == null) return defaultvalue;
            return e;
        }
        return (E) row(x)[y];
    }

    public void set(int x, int y, E element) {
        if (internalList != null) {
            int index = GUIUtils.xyToSlot(x, y, width);
            internalList.set(index, element);
            return;
        }
        row(x)[y] = element == null ? defaultvalue : element;
    }

    public void set(List<E> list) {
        if (internalList != null) {
            internalList.clear();
            internalList.addAll(list);
            return;
        }
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

    @SuppressWarnings("unchecked")
    public E[][] asArrays() {
        if (internalList != null) {
            return (E[][]) new Array2D<>(width, height, Object[]::new, Object[][]::new, defaultvalue).asArrays();
        }
        return internalArray;
    }

    public List<E> asList() {
        if (internalList != null) return internalList;
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
