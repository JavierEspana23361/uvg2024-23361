public class Node implements Comparable<Node> {
    private char caracter; // El carácter almacenado en este nodo (solo para nodos hoja)
    private int frequency; // La frecuencia del carácter (solo para nodos hoja y padres)
    private Node left; // Referencia al hijo izquierdo
    private Node right; // Referencia al hijo derecho

    // Constructor para nodos hoja
    public Node(char caracter, int frecuencia) {
        this.caracter = caracter;
        this.frequency = frecuencia;
    }

    // Constructor para nodos internos
    public Node(int frecuencia, Node izquierdo, Node derecho) {
        this.frequency = frecuencia;
        this.left = izquierdo;
        this.right = derecho;
    }

    // Método para verificar si este nodo es una hoja
    public boolean isLeaf() {
        return left == null && right == null;
    }

    // Método para comparar nodos basado en frecuencia
    @Override
    public int compareTo(Node other) {
        return this.frequency - other.frequency;
    }

    // Getters y setters
    public char getChar() {
        return caracter;
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