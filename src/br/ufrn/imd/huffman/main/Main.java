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

    public static void main(String [] args) {

        String option = args[0];
        System.out.println(option);

        if (option.equals("compress")){
            String fileIn = args[1];
            String fileCompress = args[2];
            String fileTable = args[3];

            System.out.println(fileIn);
            System.out.println(fileCompress);
            System.out.println(fileTable);



            /*String pathFile2 = "C:/Users/Lucimar/IdeaProjects/CodificacaoDeHuffman/tabelaDeSimbolos.edt";
            String outputFile = "C:/Users/Lucimar/IdeaProjects/CodificacaoDeHuffman/arquivoComprimido.edz";*/

            Compressor compressor = new Compressor(fileIn);
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
            String [] tabela = compressor.buildCodeTable(arvore.getRoot());

            for (String x : tabela) {
                if(x != null){
                    System.out.println(x);
                }
            }

            //testando a funçao getCodeMap
            /*
            HashMap<String, String> mapaDeCodigo = compressor.getCodeMap(tabela);
            for (String x : mapaDeCodigo.keySet()) {
                System.out.println(x + mapaDeCodigo.get(x));
            }*/

            //testando a função storeCodeTable
            //compressor.storeCodeTable(fileIn, );

            //testando a função storeCodeTextInFile
            //compressor.storeCodeTextInFile(fileIn, fileCompress, );



        }else if (option.equals("extract") ){
            String fileCompress = args[1];
            String fileTable = args[2];
            String fileOut = args[3];
        }

    }
}
