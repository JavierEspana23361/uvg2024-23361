/**
 * Interfaz que define las operaciones básicas de un árbol.
 *
 * @param <K> el tipo de la clave utilizada en el árbol
 * @param <V> el tipo del valor asociado a la clave en el árbol
 */
public interface ITree<K, V>{

    /**
     * Inserta un par clave-valor en el árbol.
     *
     * @param key la clave a insertar
     * @param value el valor asociado a la clave
     */
    void insert(K key, V value);

    /**
     * Busca y devuelve el valor asociado a una clave en el árbol.
     *
     * @param keyToFind la clave a buscar
     * @return el valor asociado a la clave, o null si la clave no se encuentra en el árbol
     */
    V find(K keyToFind);

    /**
     * Devuelve la cantidad de pares clave-valor en el árbol.
     *
     * @return la cantidad de pares clave-valor en el árbol
     */
    int count();

    /**
     * Verifica si el árbol está vacío.
     *
     * @return true si el árbol está vacío, false de lo contrario
     */
    boolean isEmpty();

    /**
     * Elimina un par clave-valor del árbol.
     *
     * @param key la clave del par a eliminar
     * @return el valor asociado a la clave eliminada, o null si la clave no se encuentra en el árbol
     */
    V remove(K key);

    /**
     * Realiza un recorrido en orden sobre los pares clave-valor del árbol.
     *
     * @param walk el objeto que realiza la acción durante el recorrido
     */
    void InOrderWalk(IWalk<V> walk);

    /**
     * Realiza un recorrido en preorden sobre los pares clave-valor del árbol.
     *
     * @param walk el objeto que realiza la acción durante el recorrido
     */
    void PreOrderWalk(IWalk<V> walk);

    /**
     * Realiza un recorrido en postorden sobre los pares clave-valor del árbol.
     *
     * @param walk el objeto que realiza la acción durante el recorrido
     */
    void PostOrderWalk(IWalk<V> walk);

    /**
     * Imprime el árbol en la consola.
     */
    void printTree();
}