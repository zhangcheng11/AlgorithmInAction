package com.atstone.algorithm.stucture.array;

import java.util.Arrays;

public class ArrayList<E> implements List<E> {

    private E[] arr;
    private int size;
    public static final int DEFAULT_CAPACITY = 10;
    public static final int ELEMENT_NOT_FOUND = -1;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        capacity = capacity < DEFAULT_CAPACITY ? DEFAULT_CAPACITY : capacity;
        this.arr = (E[]) new Object[capacity];
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    @Override
    public void add(E element) {
        add(size, element);
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index:" + index + ",size:" + this.size);
        }
        return arr[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index:" + index + ",size:" + this.size);
        }
        E oldElement = arr[index];
        arr[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index:" + index + ",size:" + this.size);
        }
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            arr[i] = arr[i - 1];
        }
        arr[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index:" + index + ",size:" + this.size);
        }
        E element = arr[index];
        for (int i = index; i < size - 1; i++) {
            arr[index] = arr[i + 1];
        }
        size--;
        arr[size] = null;
        return element;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(arr[i])) {
                return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        //this.arr = null;
        for (int i = 0; i < size; i++) {
            arr[i] = null;
        }
        this.size = 0;
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = arr.length;
        if (oldCapacity >= capacity) {
            return;
        }
        //扩容1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newArr = (E[]) new Object[newCapacity];
        System.arraycopy(arr, 0, newArr, 0, this.size);
        this.arr = newArr;
    }

    @Override
    public String toString() {
        return "ArrayList{" +
                "arr=" + Arrays.toString(arr) +
                ", size=" + size +
                '}';
    }
}
