package com.atstone.algorithm.stucture.tree.avltree;

import java.util.Comparator;

/**
 * 自平衡的二叉搜索树在二叉搜索树的基础上，在添加和删除后增加了恢复平衡的操作
 */
public class AVLTree<E> extends BST<E> {
    public AVLTree(){
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        //添加元素后恢复平衡操作
        //todo
        while ((node = node.parent) != null){

        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        //删除元素后恢复平衡操作
        //todo
    }

    private static class AVLNode<E> extends Node<E>{
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;
            return isLeftChild() ? left : right;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }

}
