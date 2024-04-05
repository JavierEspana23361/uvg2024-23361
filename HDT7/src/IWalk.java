public interface IWalk<V> {

    /**
     * Realiza una acción de caminar con el valor actual especificado.
     *
     * @param actualValue el valor actual para realizar la acción de caminar
     */
    void doWalk(V actualValue);
    
}