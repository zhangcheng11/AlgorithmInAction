package com.atstone.algorithm.stucture.tree.bsttree;

import com.atstone.algorithm.stucture.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树
 */
public class BinaryTree<E> implements BinaryTreeInfo {
    protected int size;
    protected Node<E> root;

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        this.root = null;
        this.size = 0;
    }

    public void preOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        preOrder(root, visitor);
    }

    private void preOrder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        preOrder(node.left, visitor);
        preOrder(node.right, visitor);
    }

    public void inOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        inOrder(root, visitor);
    }

    private void inOrder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;
        inOrder(node.left, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        inOrder(node.right, visitor);
    }

    public void postOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        postOrder(root, visitor);
    }

    private void postOrder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        postOrder(node.left, visitor);
        postOrder(node.right, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
    }

    /**
     * 层序遍历
     */
    public void levelOrder(Visitor<E> visitor) {
        if (root == null || visitor == null) return;

        Queue<Node<E>> nodeQueue = new LinkedList<>();
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {
            Node<E> node = nodeQueue.poll();
            if (visitor.visit(node.element)) return;

            if (node.left != null) {
                nodeQueue.offer(node.left);
            }
            if (node.right != null) {
                nodeQueue.offer(node.right);
            }
        }
    }

    /**
     * 判断是否为完全二叉树
     */
    public boolean isCompleted() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) return false;

            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else {
                //后面遍历的节点都必须是叶子节点
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 计算树的高度
     */
    public int height() {
        if (root == null) return 0;
        //树的高度
        int height = 0;
        //存储每一层元素数量
        int levelSize = 1;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            levelSize--;

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelSize == 0) {
                // 意味着即将要访问下一层
                levelSize = queue.size();
                height++;
            }
        }

        return height;
    }

    public int height2() {
        return height(root);
    }

    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * 寻找当前节点的前驱节点
     */
    protected Node<E> predecessor(Node<E> node){
        if(node == null) return null;

        //前驱节点在左子树当中（left.right.right...）
        Node<E> p = node.left;
        if(p != null){
            while (p.right != null){
                p = p.right;
            }
            return p;
        }
        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.left){
            node = node.parent;
        }
        //node.parent == null 或 node == node.parent.right;
        return node.parent;
    }

    /**
     * 寻找当前节点的后继节点
     */
    protected Node<E> successor(Node<E> node){
        if(node == null) return null;

        //后继存在右子树当中
        Node<E> p = node.right;
        if(p != null){
            while (p.left != null){
                p = p.left;
            }
            return p;
        }

        //从父节点、祖父节点中寻找节点
        while (node.parent != null && node == node.parent.right){
            node = node.parent;
        }
        //node.parent == null 或 node == node.parent.left;
        return node.parent;
    }


    @Override
    public Object root() {
        return this.root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>) node;
        String parentString = "null";
        if (myNode.parent != null) {
            parentString = myNode.parent.element.toString();
        }
        return myNode.element + "_p(" + parentString + ")";
    }

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
    }

    public static abstract class Visitor<E> {
        boolean stop;

        /**
         * 返回true代表停止遍历
         */
        abstract boolean visit(E element);
    }
}
