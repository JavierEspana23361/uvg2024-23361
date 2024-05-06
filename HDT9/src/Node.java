import java.io.Serializable;

public class Node implements Comparable<Node>, Serializable {
    private static final long serialVersionUID = 1L;
    
    private char character; 
    private int frequency; 
    private Node left; 
    private Node right; 

    // Constructor para nodos hoja
    public Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    // Constructor para nodos internos
    public Node(int frequency, Node left, Node right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    // Método para verificar si este nodo es una hoja
    public boolean isLeaf() {
        return left == null && right == null;
    }

    // Método para comparar nodos basado en frecuencia y carácter
    @Override
    public int compareTo(Node other) {
        // Si las frecuencias son iguales, compara por carácter
        if (this.frequency == other.frequency) {
            return this.character - other.character;
        }
        // Si las frecuencias son diferentes, compara por frecuencia
        return this.frequency - other.frequency;
    }

    // Getters y setters
    public char getCharacter() {
        return character;
    }

    public int getFrequency() {
        return frequency;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
