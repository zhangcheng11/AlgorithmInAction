package com.atstone.algorithm.stucture.tree.rbtree;

import java.util.Comparator;

/**
 * 红黑树
 */
public class RBTree<E> extends BBSTree<E> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator comparator) {
        super(comparator);
    }

    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return null;
        ((RBNode<E>) node).color = color;
        return node;
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        //添加的是根节点
        if (parent == null) {
            black(node);
            return;
        }

        //如果父节点是黑色，直接返回
        if (isBlack(parent)) {
            return;
        }

        //叔父节点
        Node<E> uncle = parent.sibling();
        //祖父节点
        Node<E> grand = parent.parent;
        if (isRed(uncle)) {
            black(parent); //父节点染成黑色
            black(uncle);//叔父节点染成黑色
            //祖父节点当作是新添加的节点
            afterAdd(red(grand));
            return;
        }

        //叔父节点不是红色
        if (parent.isLeftChild()) {//L
            if (node.isLeftChild()) {//LL
                black(parent);//parent染成黑色
                red(grand);//grand染成红色
                rotateRight(grand);
            } else {//LR
                black(node);//node染成黑色
                red(grand);//grand染成红色
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else {//R
            if (node.isLeftChild()) {//RL
                black(node);//node染成黑色
                red(grand);//grand染成红色
                rotateRight(parent);
                rotateLeft(grand);
            } else {//RR
                black(parent);//parent染成黑色
                red(grand);//grand染成红色
                rotateLeft(grand);
            }
        }
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        //如果删除的节点是红色
        if (isRed(node)) return;

        //用以取代node的子节点是红色
        if (isRed(replacement)) {
            black(replacement);
            return;
        }

        //删除的是黑色叶子节点
        //删除的是根节点
        Node<E> parent = node.parent;
        if (parent == null) return;

        //判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) {//被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) {//兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                //更换兄弟
                sibling = parent.right;
            }

            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                if (parentBlack) {
                    afterRemove(parent, null);
                }
            } else {//兄弟节点至少有一个红色子节点,向兄弟节点借元素
                //兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateLeft(sibling);
                    sibling = parent.right;
                }
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else {//被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) {//兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                //更换兄弟
                sibling = parent.left;
            }

            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                if (parentBlack) {
                    afterRemove(parent, null);
                }
            } else {//兄弟节点至少有一个红色子节点,向兄弟节点借元素
                //兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }

        }
    }

    private static class RBNode<E> extends Node<E> {
        boolean color;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }
    }
}
