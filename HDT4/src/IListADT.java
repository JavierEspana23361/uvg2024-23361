public interface IListADT<E> {
    void agregar(E elemento);
    E obtener(int indice);
    void eliminar(int indice);
    int size();
    boolean isEmpty();
}
