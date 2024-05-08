import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Huffman {
    private Node root;
    private final String text;
    private Map<Character, Integer> charFrequencies;
    private final Map<Character, String> huffmanCodes;

    public Huffman(String text) {
        this.text = text;
        fillCharFrequenciesMap();
        huffmanCodes = new HashMap<>();
    }

    private void fillCharFrequenciesMap() {
        charFrequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            Integer integer = charFrequencies.get(c);
            charFrequencies.put(c, integer == null ? 1 : integer + 1);
        }
    }

    public String comprimir() {
        Queue<Node> priorityQueue = new PriorityQueue<>();
        charFrequencies.forEach((character, frequency) ->
            priorityQueue.add(new Leaf(character, frequency)));
        
        while (priorityQueue.size() > 1) {
            priorityQueue.add(new Node(priorityQueue.poll(), priorityQueue.poll()));
        }

        generateHuffmanCodes(root = priorityQueue.poll(), "");
        return getTextoComprimido();
    }

    private void generateHuffmanCodes(Node node, String code) {
        if (node instanceof Leaf) {
            huffmanCodes.put(((Leaf) node).getCharacter(), code);
            return;
        }
        generateHuffmanCodes(node.getLeftNode(), code + '0');
        generateHuffmanCodes(node.getRightNode(), code + '1');
    }

    private String getTextoComprimido() {
        StringBuilder compressedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            compressedText.append(huffmanCodes.get(c));
        }
        return compressedText.toString();
    }

    public String descomprimir(String textoComprimido) {
        StringBuilder decompressedText = new StringBuilder();
        Node current = root;
        for (char c : textoComprimido.toCharArray()) {
            current = c == '0' ? current.getLeftNode() : current.getRightNode();
            if (current instanceof Leaf) {
                decompressedText.append(((Leaf) current).getCharacter());
                current = root;
            }
        }
        return decompressedText.toString();
    }

    public void printCodes() {
        huffmanCodes.forEach((character, code) ->
            System.out.println(character + ": " + code));
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public String getText() {
        return text;
    }

    public Map<Character, Integer> getCharFrequencies() {
        return charFrequencies;
    }

    public void setCharFrequencies(Map<Character, Integer> charFrequencies) {
        this.charFrequencies = charFrequencies;
    }

    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    
}
