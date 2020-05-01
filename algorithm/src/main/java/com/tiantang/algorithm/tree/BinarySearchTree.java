package com.tiantang.algorithm.tree;

/**
 * @author liujinkun
 * @Title: BinarySearchTree
 * @Description: 二叉搜索树
 * @date 2020/3/31 3:23 PM
 */
public class BinarySearchTree {

    static class Node{
        int data;
        Node left;
        Node right;

        Node(int data){
            this.data = data;
        }

    }

    public void insert(Node root,int data){
        // 左边
        if(root.data > data){
            if(root.left == null){
                root.left = new Node(data);
            }else{
                insert(root.left,data);
            }
        }else{
            // 右边
            if(root.right == null){
                root.right = new Node(data);
            }else{
                insert(root.right,data);
            }
        }
    }

    public void in(Node root){
        if(root.left != null){
            in(root.left);
        }
        System.out.println(root.data);
        if(root.right != null){
            in(root.right);
        }
    }

    public void post(Node root){
        if(root.left != null){
            post(root.left);
        }
        if(root.right != null){
            post(root.right);
        }
        System.out.println(root.data);
    }

    public void pre(Node root){
        System.out.println(root.data);
        if(root.left != null){
            pre(root.left);
        }
        if(root.right != null){
            pre(root.right);
        }
    }

    public int find(Node root,int data){
        if(root == null){
            return -1;
        }
        if(root.data == data){
            return 1;
        }else if(root.data >data){
            return find(root.left,data);
        }else{
            return find(root.right,data);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        int[] data = new int[]{4,6,2,1,9,5};
        Node root = new Node(data[0]);
        for (int i = 1; i < data.length; i++) {
            tree.insert(root,data[i]);
        }
        tree.in(root);
        System.out.println("find result : ");
        System.out.println(tree.find(root,2));
    }
}
