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
    /**
     * A função readTextFile ler os bytes do arquivo comprimido e os transforma em um bitset
     * */
    private BitSet readTextFile(String encodedArq) {
        BitSet bitSet = new BitSet();
        File file = new File(encodedArq);
        InputStream inputstream;
        try {
            //leitura dos binários
            inputstream = new FileInputStream(file);
            int index =0;
            String[] binarios = new String[(int)file.length()];//vetor que armazena cada byte
            int data = inputstream.read();
            while (data != -1) {
                binarios[index] = Integer.toBinaryString(data);
                index++;
                data = inputstream.read();
            }
            inputstream.close();

            //passar de byte para um bitset invertendo os bytes (ex: 00000001 = 1000000) porque na hora de escrever no arquivo é invertido
            index = 0;
            int index_bitSet =0;
            for (String byte_: binarios){

                StringBuffer stringBuffer= new StringBuffer();
                stringBuffer.append(byte_);
                stringBuffer.reverse();

                for(char bit : stringBuffer.toString().toCharArray()){
                    if (bit =='1' ) {
                        bitSet.set(index_bitSet, true);
                    }else {
                        bitSet.set(index_bitSet, false);
                    }
                    index_bitSet++;
                }
                if (stringBuffer.length() <8) {
                    bitSet.set(index_bitSet, false);
                    index_bitSet ++;
                }
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitSet;
    }

    /**
     * Função que a partir de um bitset e da tabela escreve no arquivo o conteudo comprimido
     * */
    String decodeText(BitSet bitSet, HashMap<String, String> table, String textArq) throws IOException {
        String text = "";
        String letter_bit = "";
        char bit ='0';

        //abre um arquivo de saída de dados, o qual receberá bits
        FileOutputStream fileOutputStream = new FileOutputStream(textArq);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        for (int  i =0 ;i < bitSet.length() ; ++i){
            if (bitSet.get(i)){
                bit='1';
            }else{
                bit = '0';
            }

            letter_bit+=bit;

            for (String key: table.keySet()){
                if (letter_bit.equals(table.get(key))){//procura um codigo válido
                    text+=key;
                    dataOutputStream.writeChars(key);
                    letter_bit="";
                }
            }
        }
        dataOutputStream.close();
        fileOutputStream.close();

        return text;
    }



    public void extract(String encodedArq, String codeTable, String textArq) throws IOException {

        HashMap<String, String> mapTable = readTable(codeTable);
/*
       for (String s : mapTable.keySet()){
           System.out.println(s +" "+ mapTable.get(s));
        }
*/
        BitSet bitSet = readTextFile(encodedArq);

        System.out.println(decodeText(bitSet, mapTable, textArq));

/*
        for (int  i =0 ;i < bitSet.length() ; ++i){
            if (bitSet.get(i)){

            }else{
                System.out.print("0");
            }
        }
        System.out.println();
        System.out.println(bitSet.length());

*/
    }

}
