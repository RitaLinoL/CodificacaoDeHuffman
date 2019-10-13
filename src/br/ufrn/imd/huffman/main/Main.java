package br.ufrn.imd.huffman.main;


import br.ufrn.imd.huffman.dataStructure.binaryTree.Node;
import br.ufrn.imd.huffman.dataStructure.binaryTree.Tree;
import br.ufrn.imd.huffman.dataStructure.heap.Heap;
import br.ufrn.imd.huffman.entities.Compressor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    //TODO implementar ordem de execução

    public static void main(String args []) {

        String pathFile = args[1];
        Compressor compressor = new Compressor(pathFile);
        HashMap<String, Integer> mapa = compressor.countLetter();
        Heap fila = new Heap(mapa.size());

        //testando a função turnLettersInNode()
        fila = compressor.turnLettersInNodes(mapa);

        for (Node x: fila.getMinHeap()){
            System.out.println(x.getLetter() + " - " + x.getCount());
        }

        //testando a função buildTreeCode
        Tree arvore = new Tree(compressor.buildTreeCode(fila));

        arvore.getRoot().accessPreOrder(arvore.getRoot());

//        for (Node n : fila.getMinHeap()) {
//            System.out.println(((char)n.getLetter()) + " - " + n.getCount());
//        }
    }
}
