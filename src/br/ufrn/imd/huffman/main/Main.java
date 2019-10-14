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

        //arvore.getRoot().accessPreOrder(arvore.getRoot());

        //testando a função buildCodeTable
        String tabela[] = compressor.buildCodeTable(arvore.getRoot());

//        for (String x : tabela) {
//            if(x != null){
//                System.out.println(x);
//            }
//
//        }

        //testando a funçao getCodeMap
        /*
        HashMap<String, String> mapaDeCodigo = compressor.getCodeMap(tabela);
        for (String x : mapaDeCodigo.keySet()) {
            System.out.println(x + mapaDeCodigo.get(x));
        }*/
    }
}
