import java.util.ArrayList;

/**
 * This class represents a generic list data structure that implements the IListADT interface.
 * It provides methods to add, retrieve, and remove elements from the list, as well as check its size and emptiness.
 * The list is implemented using an ArrayList.
 *
 * @param <E> the type of elements stored in the list
 */
public class ListADT<E> implements IListADT<E> {
    infixRead inread = new infixRead();
    postfixCalc ptcalc = new postfixCalc();
    protected ArrayList<E> elementos;

    public ListADT() {
        elementos = new ArrayList<>();
    }

    @Override
    public void agregar(E elemento) {
        elementos.add(elemento);
    }

    @Override
    public E obtener(int indice) {
        return elementos.get(indice);
    }

    @Override
    public void eliminar(int indice) {
        elementos.remove(indice);
    }

    @Override
    public int size() {
        return elementos.size();
    }

    @Override
    public boolean isEmpty() {
        return elementos.isEmpty();
    }
}
