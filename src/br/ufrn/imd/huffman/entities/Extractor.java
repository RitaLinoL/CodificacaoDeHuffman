package br.ufrn.imd.huffman.entities;

import java.io.*;
import java.util.BitSet;
import java.util.HashMap;

public class Extractor {

    //função para ler a tabela de um arquivo. Retorna um mapa<String, String>,
    //mas pode ser que precisemos mudar o tipo de retorno do mapa, dependendo de como avançarmos
    HashMap<String, String> readTable(String codeTable) throws IOException {
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

    private BitSet readTextFile(String encodedArq) {
        BitSet bitSet = new BitSet();
        File file = new File(encodedArq);


        InputStream inputstream;
        try {
            inputstream = new FileInputStream(file);
            int index =0;
            int bytes_int [] = new int[(int) file.length()];
            int data = inputstream.read();

            while (data != -1) {
                bytes_int[index ] = data;
                System.out.println( bytes_int[index ]);
                for (char b: Integer.toBinaryString(data).toCharArray()){
                    System.out.println(b);
                }
                data = inputstream.read();

            }
            inputstream.close();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return bitSet;
    }

    public byte[] getBytes(File file) {
        int             len     = (int)file.length();
        byte[]          sendBuf = new byte[len];
        FileInputStream inFile  = null;
        try {
            inFile = new FileInputStream(file);
            inFile.read(sendBuf, 0, len);

        } catch (FileNotFoundException fnfex) {

        } catch (IOException ioex) {

        }
        return sendBuf;
    }




    public void extract(String encodedArq, String codeTable, String textArq) throws IOException {

        HashMap<String, String> mapTable = readTable(codeTable);
//
//        for (String s : mapTable.keySet()){
//            System.out.println(s +" "+ mapTable.get(s));
//        }

        BitSet bitSet = readTextFile(encodedArq);


        byte [] b = getBytes(new File(encodedArq));
        for (int  i =0 ;i < bitSet.length() ; ++i){
            System.out.println(bitSet);
        }


    }

}
