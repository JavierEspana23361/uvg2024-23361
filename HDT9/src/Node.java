/**
 * Represents a node in a binary tree.
 */
public class Node implements Comparable<Node> {
    private final int frequency;
    private Node leftNode;
    private Node rightNode;

    /**
     * Constructs a node with the given left and right child nodes.
     *
     * @param leftNode  The left child node.
     * @param rightNode The right child node.
     */
    public Node(Node leftNode, Node rightNode) {
        this.frequency = leftNode.getFrequency() + rightNode.getFrequency();
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    /**
     * Constructs a node with the given frequency.
     *
     * @param frequency The frequency of the node.
     */
    public Node(int frequency) {
        this.frequency = frequency;
    }

    /**
     * Compares this node with the specified node for order.
     *
     * @param node The node to be compared.
     * @return A negative integer, zero, or a positive integer as this node is less than, equal to, or greater than the specified node.
     */
    @Override
    public int compareTo(Node node) {
        return Integer.compare(frequency, node.getFrequency());
    }

    /**
     * Returns the frequency of the node.
     *
     * @return The frequency of the node.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Returns the left child node.
     *
     * @return The left child node.
     */
    public Node getLeftNode() {
        return leftNode;
    }

    /**
     * Sets the left child node.
     *
     * @param leftNode The left child node to be set.
     */
    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    /**
     * Returns the right child node.
     *
     * @return The right child node.
     */
    public Node getRightNode() {
        return rightNode;
    }

    /**
     * Sets the right child node.
     *
     * @param rightNode The right child node to be set.
     */
    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
}
