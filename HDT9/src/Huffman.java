import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * The Huffman class represents a Huffman encoding algorithm implementation.
 * It provides methods to compress and decompress text using Huffman coding.
 */
public class Huffman {
    private Node root;
    private final String text;
    private Map<Character, Integer> charFrequencies;
    private final Map<Character, String> huffmanCodes;

    /**
     * Constructs a Huffman object with the given text.
     *
     * @param text the text to be compressed and decompressed
     */
    public Huffman(String text) {
        this.text = text;
        fillCharFrequenciesMap();
        huffmanCodes = new HashMap<>();
    }

    /**
     * Fills the character frequencies map based on the text.
     */
    private void fillCharFrequenciesMap() {
        charFrequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            Integer integer = charFrequencies.get(c);
            charFrequencies.put(c, integer == null ? 1 : integer + 1);
        }
    }

    /**
     * Compresses the text using Huffman coding.
     *
     * @return the compressed text
     */
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

    /**
     * Generates Huffman codes for each character in the Huffman tree.
     *
     * @param node the current node in the Huffman tree
     * @param code the code generated so far
     */
    private void generateHuffmanCodes(Node node, String code) {
        if (node instanceof Leaf) {
            huffmanCodes.put(((Leaf) node).getCharacter(), code);
            return;
        }
        generateHuffmanCodes(node.getLeftNode(), code + '0');
        generateHuffmanCodes(node.getRightNode(), code + '1');
    }

    /**
     * Returns the compressed text.
     *
     * @return the compressed text
     */
    private String getTextoComprimido() {
        StringBuilder compressedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            compressedText.append(huffmanCodes.get(c));
        }
        return compressedText.toString();
    }

    /**
     * Decompresses the given compressed text.
     *
     * @param textoComprimido the compressed text to be decompressed
     * @return the decompressed text
     */
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

    /**
     * Prints the Huffman codes for each character.
     */
    public void printCodes() {
        huffmanCodes.forEach((character, code) ->
            System.out.println(character + ": " + code));
    }

    /**
     * Returns the root node of the Huffman tree.
     *
     * @return the root node of the Huffman tree
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Sets the root node of the Huffman tree.
     *
     * @param root the root node of the Huffman tree
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     * Returns the original text.
     *
     * @return the original text
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the character frequencies map.
     *
     * @return the character frequencies map
     */
    public Map<Character, Integer> getCharFrequencies() {
        return charFrequencies;
    }

    /**
     * Sets the character frequencies map.
     *
     * @param charFrequencies the character frequencies map
     */
    public void setCharFrequencies(Map<Character, Integer> charFrequencies) {
        this.charFrequencies = charFrequencies;
    }

    /**
     * Returns the Huffman codes map.
     *
     * @return the Huffman codes map
     */
    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    /**
     * Returns the Huffman tree.
     *
     * @return the Huffman tree
     */
    public Node getArbol() {
        Queue<Node> priorityQueue = new PriorityQueue<>();
        charFrequencies.forEach((character, frequency) ->
            priorityQueue.add(new Leaf(character, frequency)));

        while (priorityQueue.size() > 1) {
            priorityQueue.add(new Node(priorityQueue.poll(), priorityQueue.poll()));
        }

        return priorityQueue.poll();
    }
}
