package com.atstone.algorithm.stucture.heap;

/**
 * 二叉堆
 *
 * 二叉堆逻辑结构是完全二叉树，也加完全二叉堆。物理结构基于数组实现。
 *
 * 性质：
 *  如果 2i + 1 <= n-1 ,有左子节点，左子节点的索引为 2i + 1  （n为节点总数）
 *  如果 2i + 1 >  n-1 ,无左子节点。
 *
 *  如果 2i + 2 <= n-1 ,有右子节点，右子节点的索引为 2i + 2
 *  如果 2i + 2 >  n-1 ,无右子节点。
 */
public interface Heap<E> {
    int size();//元素的数量
    boolean isEmpty();//是否为空
    void clear();//清空
    void add(E element);//添加元素
    E get();//获得堆顶元素
    E remove();//删除堆顶元素
    E replace(E element);//删除堆顶元素的同时插入一个新元素 
}
