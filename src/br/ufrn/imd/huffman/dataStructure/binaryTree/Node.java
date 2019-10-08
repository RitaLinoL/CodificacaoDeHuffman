package br.ufrn.imd.huffman.dataStructure.binaryTree;

/** Classe Node gerencia e armazenas as informações que cada nó da árvore binária possue
 * @version 1.0
 * @author José Lúcio
 * @author Rita Lopes
 * */

public class Node {
    private Integer letter;
    private Integer count;
    private Node left;
    private Node right;

    public Node(){
        this.letter = null;
        this.count = null;
        this.left = null;
        this.right = null;
    }

    public Node(Integer letter){
        this.letter = letter;
        this.count = 1;
    }

    public Integer getLetter() {
        return letter;
    }

    public Integer getCount() {
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
    public void setLeft(Integer letter){
        this.left = new Node(letter);
    }
    public void setRight(Integer letter){
        this.right = new Node(letter);
    }

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
}
