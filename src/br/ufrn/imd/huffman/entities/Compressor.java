package br.ufrn.imd.huffman.entities;

import br.ufrn.imd.huffman.dataStructure.binaryTree.Node;
import br.ufrn.imd.huffman.dataStructure.heap.Heap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Compressor {
    private String pathFile;

    public Compressor(String path){
        this.pathFile = path;
    }

    public HashMap<String, Integer> countLetter(){
        HashMap<String, Integer> map = new HashMap<>();

        try {
            FileReader fr = new FileReader(this.pathFile);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                String line = (String) br.readLine();
                for (char letter: line.toCharArray()){
                    String l =""+letter;

                    if (map.containsKey(l)){
                        map.put(l, map.get(l)+1);
                    }else{
                        map.put(l, 1);
                    }
                }
            }
            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    //recebe um mapa para transformar os seus caracteres armazenados em nós
    //retorna uma minHeap com os nós adicionados
    public Heap turnLettersInNodes(HashMap<String, Integer> map){
        Heap minHeap = new Heap(map.size());

        for (String x : map.keySet()) {
            int aux = x.charAt(0);

            Node node = new Node(aux);
            node.setCount(map.get(x));

            minHeap.addNode(node);
        }

        return minHeap;
    }

    //funcao para gerar uma arvore a partir da minHeap
    //retorna um nó, o último que sobra na minHeap
    public Node buildTreeCode(Heap minHeap){
        while(minHeap.getSize() > 1){
            Node a = minHeap.peek();
            minHeap.remove();

            Node b = minHeap.peek();
            minHeap.remove();

            Node father = new Node('!'); //coloquei um caracter qualquer pra representar, mas pode ser outro
            father.setCount(a.getCount() + b.getCount());
            father.setLeft(a);
            father.setRight(b);

            minHeap.addNode(father);
        }

        return minHeap.peek();
    }

    public void buildCodeTable(Node root){

    }
}
