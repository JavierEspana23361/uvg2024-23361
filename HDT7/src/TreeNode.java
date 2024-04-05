/**
 * Esta clase representa un nodo en un árbol binario de búsqueda.
 * Cada nodo contiene una clave y un valor asociado.
 * Además, cada nodo tiene referencias a su nodo padre, nodo izquierdo y nodo derecho.
 *
 * @param <K> el tipo de la clave
 * @param <V> el tipo del valor
 */
public class TreeNode<K, V> {
    
    private K key;
    private V value;
    private TreeNode<K,V> left;
    private TreeNode<K,V> right;
    private TreeNode<K,V> parent;

    /**
     * Crea un nuevo nodo con la clave y el valor especificados.
     *
     * @param key la clave del nodo
     * @param value el valor asociado al nodo
     */
    public TreeNode(K key, V value){
        this.key = key;
        this.value = value;
        parent = null;
        left = null;
        right = null;
    }

    /**
     * Obtiene la clave del nodo.
     *
     * @return la clave del nodo
     */
    public K getKey() {
        return key;
    }

    /**
     * Establece la clave del nodo.
     *
     * @param key la nueva clave del nodo
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Obtiene el valor asociado al nodo.
     *
     * @return el valor asociado al nodo
     */
    public V getValue() {
        return value;
    }

    /**
     * Establece el valor asociado al nodo.
     *
     * @param value el nuevo valor asociado al nodo
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Obtiene el nodo hijo izquierdo.
     *
     * @return el nodo hijo izquierdo
     */
    public TreeNode<K, V> getLeft() {
        return left;
    }

    /**
     * Establece el nodo hijo izquierdo.
     *
     * @param left el nuevo nodo hijo izquierdo
     */
    public void setLeft(TreeNode<K, V> left) {
        this.left = left;
    }

    /**
     * Obtiene el nodo hijo derecho.
     *
     * @return el nodo hijo derecho
     */
    public TreeNode<K, V> getRight() {
        return right;
    }

    /**
     * Establece el nodo hijo derecho.
     *
     * @param right el nuevo nodo hijo derecho
     */
    public void setRight(TreeNode<K, V> right) {
        this.right = right;
    }

    /**
     * Obtiene el nodo padre.
     *
     * @return el nodo padre
     */
    public TreeNode<K, V> getParent() {
        return parent;
    }

    /**
     * Establece el nodo padre.
     *
     * @param parent el nuevo nodo padre
     */
    public void setParent(TreeNode<K, V> parent) {
        this.parent = parent;
    }
}
