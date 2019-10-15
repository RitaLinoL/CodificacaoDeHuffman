package br.ufrn.imd.huffman.entities;

import br.ufrn.imd.huffman.dataStructure.binaryTree.Node;
import br.ufrn.imd.huffman.dataStructure.heap.Heap;

import java.io.*;
import java.util.HashMap;

public class Compressor {
    private String pathFile;

    public Compressor(String path){
        this.pathFile = path;
    }

    public HashMap<String, Integer> countLetter(){
        HashMap<String, Integer> map = new HashMap<>();

        try {
            FileReader fr = new FileReader(this.pathFile);
            BufferedReader br = new BufferedReader(fr);
            map.put("\n", 0);
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
                map.put("\n", map.get("\n") +1);
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
    public Heap turnLettersInNodes(HashMap<String, Integer> map){
        Heap minHeap = new Heap(map.size());
        for (String x : map.keySet()) {
            int aux = x.charAt(0);//transformo de caracter para um valor na tabela ascii

            Node node = new Node(aux);
            node.setCount(map.get(x));
            minHeap.addNode(node);
        }

        return minHeap;
    }

    //funcao para gerar uma arvore a partir da minHeap
    //retorna um nó, o último que sobra na minHeap
    public Node buildTreeCode(Heap minHeap){
        while(minHeap.getSize() > 1){
            Node a = minHeap.peek();
            minHeap.remove();

            Node b = minHeap.peek();
            minHeap.remove();

            Node father = new Node('!'); //coloquei um caracter qualquer pra representar, mas pode ser outro
            father.setCount(a.getCount() + b.getCount());
            father.setLeft(a);
            father.setRight(b);

            minHeap.addNode(father);
        }

        return minHeap.peek();
    }

    //método que gera e retorna um vetor de strings com os códigos
    public String[] buildCodeTable(Node root){
        String codeTable[] = new String[256]; //tamanho de 256 pq é o limite da tabela ascii, mas desperdiça um pouco de memória
        buildCode(codeTable, root, "");
        return codeTable;
    }

    //método auxiliar pra gerar os códigos
    private void buildCode(String table[], Node node, String s){
        if(node.isLeaf()){
            table[node.getLetter()] = (char)node.getLetter() + " " + s;
            return;
        }
        buildCode(table, node.getLeft(), s + '0');
        buildCode(table, node.getRight(), s + '1');
    }

    //retorna uma mapa com os valores letter e código do letter
    //OBS: esse método foi criado por orientação do professor no arquivo PDF, página 6, segundo parágrafo
    //OBS2: o método buildCodeTable funciona da mesma forma, mas esse deve ser mais conveniente pra gente
    public HashMap<String, String> getCodeMap(String tabela[]){
        HashMap<String, String> map = new HashMap<>();
        for (String s : tabela) {
            if(s != null){
                String aux[];
                aux = s.split(" ", 2);
                map.put(aux[0], aux[1]);
            }
        }

        return map;
    }

    //método para armazenar a tabela de códigos num arquivo
    public void storeCodeTable(String pathFile, HashMap<String, String> map) throws IOException {
        FileWriter arquivo = new FileWriter(pathFile);
        PrintWriter salvarEmArquivo = new PrintWriter(arquivo);

        for (String x : map.keySet()) {
            salvarEmArquivo.println(x + map.get(x));
        }
        arquivo.close();
    }

    //função para gerar um arquivo binário com a sequencia de bits do texto codificado de um arquivo de texto
    //OBS: não foi usado a classe BitSet que o professor recomendou, então possivelmente podem existir erros nessa função
    //OBS2: tbm não foi usado o EOF que o professor recomenda colocar, então pode ser que tenha erros
    public void storeCodeTextInFile(String inputFile, String outputFile, HashMap<String, String> map) throws IOException {
        //abre o arquivo de entrada, que contém os dados a serem lidos
        FileReader frInputFile = new FileReader(inputFile);
        BufferedReader brInputFile = new BufferedReader(frInputFile);

        //abre um arquivo de saída de dados, o qual receberá bits
        FileOutputStream arquivo = new FileOutputStream(outputFile);
        DataOutputStream gravarEmArquivo = new DataOutputStream(arquivo);

        while(brInputFile.ready()){
            String line = brInputFile.readLine();
            char characters[] = line.toCharArray();//transforma a linha lida do arquivo em um vetor de caracteres
            //percorre characters. Para cada letra no vetor, percorre o mapa inteiro e, se a letra for igual a uma das chaves do mapa, guarda o código da letra em arquivo binário
            for (char c : characters) {
                for (String s : map.keySet()) {
                    if(c == s.charAt(0)){
                        gravarEmArquivo.writeUTF(map.get(s));
                    }
                }
            }
        }

        arquivo.close();
        frInputFile.close();
        brInputFile.close();
    }

    //TODO utilizar todas as funções anteriores para criar uma função Compress, simplificando o uso do programa
    //OBS: assim que essa função for criada, é interessante alterar a visibilidade das outras para private

}
