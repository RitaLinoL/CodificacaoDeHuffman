package br.ufrn.imd.huffman.entities;

import br.ufrn.imd.huffman.dataStructure.binaryTree.Node;
import br.ufrn.imd.huffman.dataStructure.heap.Heap;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class CompressorTest {

    Compressor compressor;

    @org.junit.Before
    public void setUp() throws Exception {
        compressor = new Compressor("C:/Users/Rita Lino/UFRN/CodificacaoDeHuffman/testes/teste1.txt");
    }

    @Test
    public void mustCountCharacters(){
        HashMap<String, Integer> map =   new HashMap<>();
        map.put("\n", 1);
        map.put("l",4);
        map.put("o", 3);
        map.put("a", 3);
        map.put("p", 1);
        map.put("z",1);
        HashMap<String, Integer> mapReturn =   compressor.countLetter();
        for (String key: mapReturn.keySet()) {
            assertEquals(map.get(key), mapReturn.get(key));
        }
    }

    @Test
    public void mustTurnLettersInNodes(){
        Heap h = new Heap(6);

        HashMap<String, Integer> map =   new HashMap<>();
        map.put("\n", 1);
        map.put("l",4);
        map.put("o", 3);
        map.put("a", 3);
        map.put("p", 1);
        map.put("z",1);

        for (String l: map.keySet()) {
            Node n = new Node(l.charAt(0));
            n.setCount(map.get(l));
            h.addNode(n);
        }

        Heap heap = compressor.turnLettersInNodes(map);
        assertArrayEquals(h.getMinHeap(), heap.getMinHeap());

    }
}