package br.ufrn.imd.huffman.dataStructure.binaryTree;

/** Classe Node gerencia e armazenas as informações que cada nó da árvore binária possue
 * @version 1.0
 * @author José Lúcio
 * @author Rita Lopes
 * */

public class Node {
    private int letter;
    private int count;
    private Node left;
    private Node right;

    public Node(){
        this.letter = 0;
        this.count = 0;
        this.left = null;
        this.right = null;
    }

    public Node(int letter){
        this.letter = letter;
        this.count = 1;
    }

    public int getLetter() {
        return letter;
    }

    public int getCount() {
        return count;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node node){
        this.left = node;
    }
    public void setRight(Node node){
        this.right = node;
    }
    public void setLeft(int letter){
        this.left = new Node(letter);
    }
    public void setRight(int letter){
        this.right = new Node(letter);
    }
    public void setLetter(int letter) {this.letter = letter;}
    public void setCount(int count){this.count = count;}

    public void addCount(){
        this.count ++;
    }

    public void insert(Node node){

        if(node.getCount() < this.getCount()){
            if(this.left == null){
                this.left = node;
            } else {
                this.left.insert(node);
            }
        } else if(node.getCount() > this.getCount()){
            if(this.right == null){
                this.right = node;
            } else {
                this.right.insert(node);
            }
        }
    }

    @Override
    public String toString() {
        return "letter: " + (char)this.getLetter() + " count: " + this.getCount();
    }

    public void accessPreOrder(Node root){

        System.out.print(root.getCount() + " ");

        if(root.left != null){
            root.left.accessPreOrder(root.left);
        }

        if(root.right != null){
            root.right.accessPreOrder(root.right);
        }
    }
}
