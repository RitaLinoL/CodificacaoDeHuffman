package br.ufrn.imd.huffman.entities;

import br.ufrn.imd.huffman.dataStructure.binaryTree.Node;
import br.ufrn.imd.huffman.dataStructure.binaryTree.Tree;
import br.ufrn.imd.huffman.dataStructure.heap.Heap;

import java.io.*;
import java.util.BitSet;
import java.util.HashMap;

public class Compressor {
    private String pathFile;

    public Compressor(){}

    public Compressor(String path){
        this.pathFile = path;
    }

    HashMap<String, Integer> countLetter(){
        HashMap<String, Integer> map = new HashMap<>();

        try {
            FileReader fr = new FileReader(this.pathFile);
            BufferedReader br = new BufferedReader(fr);
            //map.put("\n", 0); //O uso dessa instrução deixa a tabela gerada esquisita, o que faz causar erros na hora da leitura para a função extract
            while (br.ready()) {
                String line = (String) br.readLine();
                for (char letter: line.toCharArray()){
                    String l =""+letter;
                    if (map.containsKey(l)){
                        map.put(l, map.get(l)+1);
                    }else{
                        map.put(l, 1);
                    }
                }
                //map.put("\n", map.get("\n") +1);
            }
            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    //recebe um mapa para transformar os seus caracteres armazenados em nós
    //retorna uma minHeap com os nós adicionados
    Heap turnLettersInNodes(HashMap<String, Integer> map){
        Heap minHeap = new Heap(map.size() + 1);
        for (String x : map.keySet()) {
            int aux = x.charAt(0);//transformo de caracter para um valor na tabela ascii

            Node node = new Node(aux);
            node.setCount(map.get(x));
            minHeap.addNode(node);
        }

        //adição do nó que representará o EOF
        Node eof = new Node(259);
        eof.setCount(1);
        minHeap.addNode(eof);

        return minHeap;
    }

    //funcao para gerar uma arvore a partir da minHeap
    //retorna um nó, o último que sobra na minHeap
    Node buildTreeCode(Heap minHeap){
        while(minHeap.getSize() > 1){
            Node a = minHeap.peek();
            minHeap.remove();

            Node b = minHeap.peek();
            minHeap.remove();

            Node father = new Node(0); //coloquei um caracter qualquer pra representar, mas pode ser outro
            father.setCount(a.getCount() + b.getCount());
            father.setLeft(a);
            father.setRight(b);

            minHeap.addNode(father);
        }

        return minHeap.peek();
    }

    //método que gera e retorna um vetor de strings com os códigos
    String[] buildCodeTable(Node root){
        String codeTable[] = new String[260]; //tamanho de 256 pq é o limite da tabela ascii, mas desperdiça um pouco de memória
        buildCode(codeTable, root, "");
        return codeTable;
    }

    //método auxiliar pra gerar os códigos
    void buildCode(String table[], Node node, String s){
        if(node.isLeaf()){
            //Esse if faz com que o caracter EOF seja mostrado no arquivo em que ficará a tabela
            //seu código em binário ainda é gerado e adicionado a um mapa
            //ATENÇAO: PARA FAZER O 'A' COM BARQUINHO APARECER, DEIXE AS LINHAS 105 À 108 COMENTADAS :D
//            if(node.getLetter() == 259){
//                table[node.getLetter()] = "EOF" + s;
//                return;
//            }

            table[node.getLetter()] = (char)node.getLetter() + s;
            return;
        }
        buildCode(table, node.getLeft(), s + '0');
        buildCode(table, node.getRight(), s + '1');
    }

    //retorna uma mapa com os valores letter e código do letter
    //OBS: esse método foi criado por orientação do professor no arquivo PDF, página 6, segundo parágrafo
    //OBS2: o método buildCodeTable funciona da mesma forma, mas esse deve ser mais conveniente pra gente
    HashMap<String, String> getCodeMap(String tabela[]){
        HashMap<String, String> map = new HashMap<>();
        for (String s : tabela) {
            if(s != null){
                String key = "" + s.charAt(0);
                String value = s.substring(1);
                map.put(key, value);

            }
        }

        return map;
    }

    //método para armazenar a tabela de códigos num arquivo
    void storeCodeTable(String pathFile, HashMap<String, String> map) throws IOException {
        FileWriter arquivo = new FileWriter(pathFile);
        PrintWriter salvarEmArquivo = new PrintWriter(arquivo);

        for (String x : map.keySet()) {

            salvarEmArquivo.println(x + map.get(x));

        }
        arquivo.close();
    }

    //função para gerar um arquivo binário com a sequencia de bits do texto codificado de um arquivo de texto
    //OBS: não foi usado a classe BitSet que o professor recomendou, então possivelmente podem existir erros nessa função
    //TODO adicionar o EOF ao código binário gerado
    void storeCodeTextInFile(String inputFile, String outputFile, HashMap<String, String> map) throws IOException {
        //abre o arquivo de entrada, que contém os dados a serem lidos
        FileReader frInputFile = new FileReader(inputFile);
        BufferedReader brInputFile = new BufferedReader(frInputFile);

        //abre um arquivo de saída de dados, o qual receberá bits
        FileOutputStream arquivo = new FileOutputStream(outputFile);
        DataOutputStream gravarEmArquivo = new DataOutputStream(arquivo);

        BitSet bitSet = new BitSet();
        int count =0;

        while(brInputFile.ready()){
            String line = brInputFile.readLine();
            char characters[] = line.toCharArray();//transforma a linha lida do arquivo em um vetor de caracteres
            //percorre characters. Para cada letra no vetor, percorre o mapa inteiro e, se a letra for igual a uma das chaves do mapa, guarda o código da letra em arquivo binário
            for (char c : characters) {
                for (String s : map.keySet()) {
                    if(c == s.charAt(0)){
                        //TESTE PARA GERAÇÃO DE BYTES
                        for (char i: map.get(s).toCharArray()){
                            if (i == '0') {
                                bitSet.set(count, false);
                            }else{
                                bitSet.set(count, true);
                            }
                            count++;
                        }

                    }
                }
            }
        }
        for (char c: map.get("ă").toCharArray()){
            if (c == '0') {
                bitSet.set(count, false);
            }else{
                bitSet.set(count, true);
            }
            count++;
        }

        bitSet.set(count, true);

        arquivo.write(bitSet.toByteArray());


        arquivo.close();
        frInputFile.close();
        brInputFile.close();
    }

    //OBS: assim que essa função for criada, é interessante alterar a visibilidade das outras para private
    //função que recebe um arquivo de texto para comprimir e gerar tabela de códigos
    public void compress(String arqText, String arqEncoded, String codeTable) throws IOException {
        Compressor compressor = new Compressor(arqText);

        HashMap<String, Integer> mapCounter = compressor.countLetter();

        Heap minQueue = new Heap(mapCounter.size());
        minQueue = compressor.turnLettersInNodes(mapCounter);

        Tree tree = new Tree(compressor.buildTreeCode (minQueue));

        String table[] = compressor.buildCodeTable(tree.getRoot());

        HashMap<String, String> codeMap = compressor.getCodeMap(table);

        compressor.storeCodeTable(codeTable, codeMap);

        compressor.storeCodeTextInFile(arqText, arqEncoded, codeMap);

    }
}