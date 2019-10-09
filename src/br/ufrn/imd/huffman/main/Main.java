package br.ufrn.imd.huffman.main;


import br.ufrn.imd.huffman.dataStructure.binaryTree.Node;
import br.ufrn.imd.huffman.dataStructure.heap.Heap;
import br.ufrn.imd.huffman.entities.Compressor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    //TODO implementar ordem de execução

    public static void main(String args []) {
        Heap fila = new Heap();
        String pathFile = "/home/rita_lino/teste.txt";
        Compressor compressor = new Compressor(pathFile);
        //compressor.countLetter();

        //testando a função turnLettersInNode()
        fila = compressor.turnLettersInNodes(compressor.countLetter());

        for (Node n : fila.getMinHeap()) {
            System.out.println(n.getLetter() + " - " + n.getCount());
        }
    }
}
