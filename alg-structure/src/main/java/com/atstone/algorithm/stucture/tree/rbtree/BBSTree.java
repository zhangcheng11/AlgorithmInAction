package com.atstone.algorithm.stucture.tree.rbtree;


import java.util.Comparator;

public class BBSTree<E> extends BSTree<E> {

    public BBSTree() {
        this(null);
    }

    public BBSTree(Comparator comparator) {
        super(comparator);
    }

    protected void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    protected void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        // 让parent称为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // grand是root节点
            root = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;

        // 更新高度
        /*updateHeight(grand);
        updateHeight(parent);*/
    }

}
