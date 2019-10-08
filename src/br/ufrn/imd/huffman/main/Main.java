package br.ufrn.imd.huffman.main;


import br.ufrn.imd.huffman.entities.Compressor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    //TODO implementar ordem de execução

    public static void main(String args []) {
        String pathFile = "/home/rita_lino/teste.txt";
        Compressor compressor = new Compressor(pathFile);
        compressor.countLetter();

    }
}
