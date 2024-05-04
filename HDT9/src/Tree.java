import java.util.PriorityQueue;

public class Tree {
    private Node root;

    public Tree(int[] frecuencias) {
        buildTree(frecuencias);
    }

    private void buildTree(int[] frecuencias) {
        PriorityQueue<Node> priqueue = new PriorityQueue<>();

        for (int i = 0; i < frecuencias.length; i++) {
            if (frecuencias[i] > 0) {
                priqueue.offer(new Node((char) i, frecuencias[i]));
            }
        }

        while (priqueue.size() > 1) {
            Node left = priqueue.poll();
            Node right = priqueue.poll();
            int totalFrequency = left.getFrequency() + right.getFrequency();
            priqueue.offer(new Node(totalFrequency, left, right));
        }

  
        root = priqueue.poll();
    }


    public Node getRoot() {
        return root;
    }
}
