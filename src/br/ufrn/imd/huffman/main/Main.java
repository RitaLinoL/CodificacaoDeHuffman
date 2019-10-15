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

    public static void main(String args []) throws IOException {

        String pathFile = args[1];
        //String codeTable = "C:/Users/Lucimar/IdeaProjects/CodificacaoDeHuffman/tabelaDeSimbolos.edt";
        //String outputFile = "C:/Users/Lucimar/IdeaProjects/CodificacaoDeHuffman/arquivoComprimido.edz";
        Compressor compressor = new Compressor();
        //compressor.compress(pathFile, codeTable, outputFile);


    }
}
