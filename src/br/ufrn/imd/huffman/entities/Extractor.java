package br.ufrn.imd.huffman.entities;

import java.io.*;
import java.util.BitSet;
import java.util.HashMap;

public class Extractor {

    //função para ler a tabela de um arquivo. Retorna um mapa<String, String>,
    //mas pode ser que precisemos mudar o tipo de retorno do mapa, dependendo de como avançarmos
    HashMap<Integer, String> readTable(String codeTable) throws IOException {
        HashMap<Integer, String> map = new HashMap<>();
        FileReader fr = new FileReader(codeTable);
        while (fr.ready()){ //lendo o arquivo char a char para pegar o \n como
            int k = fr.read();
            String key =""+ (char) k;
            String value="";
            String c =""+ (char) fr.read();
            do{
                value +=c;
                c =""+ (char) fr.read();
            }while(!(c.equals("\n")) && ((int)c.charAt(0) < 260));

            map.put(k, value);

        }

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

            /*passar de byte para um bitset invertendo os bytes (ex: 00000001 = 1000000)
            porque na hora de escrever no arquivo é invertido*/
            index = 0;

            int index_bitSet =0;
            for (String byte_: binarios){
                String b = "";
                for(int i= 8 - byte_.length(); i>0; --i){
                    b+="0";
                }
                StringBuffer stringBuffer= new StringBuffer();
                stringBuffer.append(b+byte_);
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
    String decodeText(BitSet bitSet, HashMap<Integer, String> table, String textArq) throws IOException {
        String text = "";
        String letter_bit = "";
        char bit ='0';

        //abre um arquivo de saída de dados, o qual receberá bits
        FileOutputStream fileOutputStream = new FileOutputStream(textArq);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

        for (int  i =0 ;i < bitSet.length() ; ++i){
            if (bitSet.get(i)){
                bit='1';
            }else{
                bit = '0';
            }

            letter_bit+=bit;

            for (Integer key: table.keySet()){

                if (letter_bit.equals(table.get(key))) {//procura um codigo válido
                    if (key.equals(259)) {
                        break;
                    }
                    text += key;
                    outputStreamWriter.write(key);
                    letter_bit = "";
                }
            }
        }
        outputStreamWriter.close();
        fileOutputStream.close();

        return text;
    }




    public void extract(String encodedArq, String codeTable, String textArq) throws IOException {

        HashMap<Integer, String> mapTable = readTable(codeTable);


        BitSet bitSet = readTextFile(encodedArq);

        decodeText(bitSet, mapTable, textArq);

    }

}
