/**
 * Represents a leaf node in a Huffman tree.
 * Each leaf node contains a character and its frequency.
 */
public class Leaf extends Node {
    private final char character;

    /**
     * Constructs a new Leaf object with the specified character and frequency.
     *
     * @param character the character associated with the leaf node
     * @param frequency the frequency of the character
     */
    public Leaf(char character, int frequency) {
        super(frequency);
        this.character = character;
    }

    /**
     * Returns the character associated with the leaf node.
     *
     * @return the character
     */
    public char getCharacter() {
        return character;
    }
}
