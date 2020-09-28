package com.atstone.algorithm.stucture.tree.bsttree;

import java.util.Comparator;

/**
 * 二叉搜索树
 */
public class BinarySearchTree<E> extends BinaryTree<E> {
    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void add(E element) {
        elementNotNullCheck(element);
        if (root == null) {
            //添加第一个节点
            root = new Node<E>(element, null);
            size++;
            return;
        }
        //添加的不是第一个节点
        //找到父节点
        Node<E> parent = root;
        Node<E> node = root;
        int cmp = 0;
        do {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.element = element;
                return;
            }
        } while (node != null);

        //看看插入到父节点的哪个位置上
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
    }

    public void remove(E element) {
        remove(node(element));
    }

    /**
     * 删除指定node
     */
    private void remove(Node<E> node) {
        if (node == null) return;

        size--;
        if (node.hasTwoChildren()) {
            //找到后继节点
            Node<E> successor = successor(node);
            //用后继节点的值覆盖度为2的节点的值
            node.element = successor.element;
            //删除后继节点
            node = successor;
        }

        //删除node节点（node节点的度必然是1或者0）
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {//node是度为1的节点
            //更改parent
            replacement.parent = node.parent;
            //更改parent的left、right指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {// node == node.parent.right
                node.parent.right = replacement;
            }
        } else if (node.parent == null) {// node是叶子节点并且是根节点
            root = null;
        } else {// node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {// node == node.parent.right
                node.parent.right = null;
            }
        }

    }

    public boolean contains(E element) {
        return node(element) != null;
    }


    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    /**
     * @return 返回值等于0，代表e1和e2相等；返回值大于0，代表e1大于e2；返回值小于于0，代表e1小于e2
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }
}
