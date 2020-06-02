package com.atstone.algorithm.stucture.list;

public class LinkedList<E> extends AbstractList<E>{

    private Node<E> first;

    public LinkedList() {
    }


    @Override
    public E get(int index) {
        Node<E> cur = first;
        for(int i=0;i<size;i++){
            if(i == index){
                return cur.element;
            }
            cur = cur.next;
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(E element) {
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        this.first = null;
    }


    public static class Node<E>{
        E element;
        Node<E> next;

        public Node(E element,Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }


}
