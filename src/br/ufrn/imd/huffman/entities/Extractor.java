package br.ufrn.imd.huffman.entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Extractor {

    //função para ler a tabela de um arquivo. Retorna um mapa<String, String>,
    //mas pode ser que precisemos mudar o tipo de retorno do mapa, dependendo de como avançarmos
    public HashMap<String, String> readTable(String codeTable) throws IOException {
        HashMap<String, String> map = new HashMap<>();

        FileReader fr = new FileReader(codeTable);
        BufferedReader br = new BufferedReader(fr);

        while(br.ready()){
            String line = br.readLine();

            String key = "" + line.charAt(0);
            String value = line.substring(1);

            map.put(key, value);
        }

        br.close();
        fr.close();

        return map;
    }


    public void extract(String encodedArq, String codeTable, String textArq){


    }
}
