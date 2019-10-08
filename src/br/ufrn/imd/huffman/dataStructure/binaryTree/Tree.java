package br.ufrn.imd.huffman.dataStructure.binaryTree;

public class Tree {
    private Node root = null ;

    public Tree(Node root){
        this.root = root;
    }

    public Node getRoot(){
        return root;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public void insert(Node node){
        if (this.root == null){
            this.root = node;
            return;
        }
        root.insert(node);
    }



}
