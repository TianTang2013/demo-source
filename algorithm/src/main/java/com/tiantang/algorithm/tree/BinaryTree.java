package com.tiantang.algorithm.tree;

/**
 * @author liujinkun
 * @Title: BinaryTree
 * @Description: 二叉树
 * @date 2020/3/30 11:18 PM
 */


public class BinaryTree {

    static class Node<E> {
        private E data;

        private Node<E> left;

        private Node<E> right;

        Node(E data, Node<E> left, Node<E> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        // 构造一个树
        Node<String> D = new Node<>("D", null, null);
        Node<String> C = new Node<>("C", D, null);
        Node<String> B = new Node<>("B", null, C);

        Node<String> K = new Node<>("K", null, null);
        Node<String> J = new Node<>("J", null, null);
        Node<String> G = new Node<>("G", K, J);
        Node<String> H = new Node<>("H", null, null);
        Node<String> F = new Node<>("F", G, H);
        Node<String> E = new Node<>("E", null, F);
        Node<String> A = new Node<>("A", B, E);
        BinaryTree binaryTree = new BinaryTree();
        System.out.println("前序");
        binaryTree.pre(A);
        System.out.println("后序");
        binaryTree.post(A);
        System.out.println("中序");
        binaryTree.in(A);


    }

    /**
     * 前序遍历（根、左、右）
     *
     * @param root
     */
    public void pre(Node root) {
        print(root);
        if (root.left != null) {
            pre(root.left);
        }
        if (root.right != null) {
            pre(root.right);
        }
    }

    /**
     * 中序遍历（左、根、右）
     *
     * @param root
     */
    public void in(Node root) {
        if (root.left != null) {
            in(root.left);
        }
        print(root);
        if (root.right != null) {
            in(root.right);
        }
    }

    /**
     * 后序遍历（左、右、根）
     */
    public void post(Node root) {
        if (root.left != null) {
            post(root.left);
        }
        if (root.right != null) {
            post(root.right);
        }
        print(root);
    }

    public void print(Node node) {
        System.out.print(node.data);
    }
}
