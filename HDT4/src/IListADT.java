/**
 * This interface represents a generic list data structure.
 * It provides methods to add elements, retrieve elements by index,
 * remove elements by index, get the size of the list, and check if the list is empty.
 *
 * @param <E> the type of elements stored in the list
 */
public interface IListADT<E> {
    void agregar(E elemento);
    E obtener(int indice);
    void eliminar(int indice);
    int size();
    boolean isEmpty();
}
