package br.ufrn.imd.huffman.dataStructure.heap;


import br.ufrn.imd.huffman.dataStructure.binaryTree.Node;

public class Heap {

    private Node[] minHeap;
    private int size;
    private int capacity;

    public int getSize(){
        return size;
    }

    public Node[] getMinHeap() {
        return minHeap;
    }

    public int getCapacity(){
        return capacity;
    }

    public Heap(){
        this(10);
    }

    public Heap(int capacity){
        this.minHeap = new Node[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public void addNode(Integer letter){
        addNode(new Node(letter));
    }

    public void addNode(Node node){
        this.minHeap[getSize()] = node;
        heapifyUp(getSize());
        this.size++;
    }

    private void heapifyUp(int index){
        if(!hasParent(index)){
            return;
        }
        int parentIndex = getParentIndex(index);

        Node node = minHeap[index];
        Node father = minHeap[parentIndex];

        if(node.getCount() < father.getCount()){
            minHeap[index] = father;
            minHeap[parentIndex] = node;
            heapifyUp(parentIndex);
        }
    }

    private boolean hasParent(int index){
        return getParentIndex(index) >= 0 && getParentIndex(index) < size;
    }

    private int getParentIndex(int index){
        return (int) Math.floor((index - 1) / 2);
    }

    public Node peek(){
        if(getSize() == 0){
            return null;
        }
        return minHeap[0];
    }

    public void remove(){
        minHeap[0] = minHeap[getSize() - 1];
        minHeap[getSize() - 1] = null;
        size--;
        heapifyDown(0);
    }

    private void heapifyDown(int index){
        int leftChild = 2*index + 1;
        int rightChild = 2*index + 2;

        int childIndex = -1;

        if(leftChild < getSize()) {
            childIndex = leftChild;
        }

        if(childIndex < 0){
            return;
        }

        if(rightChild < getSize()){
            if(minHeap[rightChild].getCount() < minHeap[leftChild].getCount()){
                childIndex = rightChild;
            }
        }

        if(minHeap[index].getCount() > minHeap[childIndex].getCount()){
            Node aux = minHeap[index];
            minHeap[index] = minHeap[childIndex];
            minHeap[childIndex] = aux;
            heapifyDown(childIndex);
        }
    }
}
