package com.atstone.algorithm.stucture.list;

public class LinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    public LinkedList() {
    }


    @Override
    public E get(int index) {
        Node<E> node = index(index);
        return node.element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = index(index);
        E oldValue = node.element;
        node.element = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        if (index == 0) {
            first = new Node<E>(element, first);
        } else {
            Node<E> pre = index(index - 1);
            pre.next = new Node<E>(element, pre.next);
        }
        size++;
    }

    @Override
    public E remove(int index) {
        Node<E> node = first;
        if (index == 0) {
            first = first.next;
        } else {
            Node<E> prev = index(index - 1);
            node = prev.next;
            prev.next = prev.next.next;
        }
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        if(element == null){
            Node<E> node = first;
            for(int i=0;i<size;i++){
                if(node.element == null){
                    return i;
                }
                node = node.next;
            }
        }else{
            Node<E> node = first;
            for(int i=0;i<size;i++){
                if(node.element.equals(element)){
                    return i;
                }
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        this.first = null;
        this.size = 0;
    }

    private Node<E> index(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("the index is outOfBound.");
        }
        Node<E> cur = first;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur;
    }


    public static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

    }


}
