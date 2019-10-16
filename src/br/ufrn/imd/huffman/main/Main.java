package br.ufrn.imd.huffman.main;


import br.ufrn.imd.huffman.dataStructure.binaryTree.Node;
import br.ufrn.imd.huffman.dataStructure.binaryTree.Tree;
import br.ufrn.imd.huffman.dataStructure.heap.Heap;
import br.ufrn.imd.huffman.entities.Compressor;
import br.ufrn.imd.huffman.entities.Extractor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    //TODO implementar ordem de execução


    public static void main(String [] args) throws IOException {

        String option = args[0];

        if (option.equals("compress")){
            String fileIn = args[1];
            String fileCompress = args[2];
            String fileTable = args[3];

            Compressor compressor = new Compressor();
            compressor.compress(fileIn, fileCompress, fileTable);
//




            //TESTE QUE REALIZEI (LÚCIO)
//           String pathFile = "/home/zaraki/IdeaProjects/CodificacaoDeHuffman/testes/teste1.txt";
//           String codeTable = "/home/zaraki/IdeaProjects/CodificacaoDeHuffman/tabelaDeSimbolos.edt";
//           String outputFile = "/home/zaraki/IdeaProjects/CodificacaoDeHuffman/arquivoComprimido.edz";
//           Compressor compressor = new Compressor();
//           compressor.compress(pathFile, outputFile, codeTable);



           //Relatório de compressão
//            long lengthIn=1;
//            long lengthOut=0;
//
//           File fIn = new File(fileIn);
//           lengthIn = fIn.length();
//
//           File fOut = new File(fileCompress);
//           lengthOut = fOut.length();
//           System.out.println(lengthIn);
//           System.out.println(lengthOut);
//
//           System.out.println("O arquivo está "+ (lengthOut * 100)/lengthIn+"% comprimido");
//
//
//
        }else if (option.equals("extract") ){
            String fileCompress = args[1];
            String fileTable = args[2];
            String fileOut = args[3];
        }



    }
}
