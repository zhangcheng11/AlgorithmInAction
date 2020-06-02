package com.atstone.algorithm.stucture.list;

/**
 * 定义数组的基本功能
 * @param <E>  任意java对象类型
 */
public interface List<E> {

    int ELEMENT_NOT_FOUND = -1;

    int size();

    boolean isEmpty();

    boolean contains(E element);

    void add(E element);

    E get(int index);

    E set(int index,E element);

    void add(int index,E element);

    E remove(int index);

    int indexOf(E element);

    void clear();
}
