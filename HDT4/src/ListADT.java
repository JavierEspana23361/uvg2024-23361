import java.util.ArrayList;

public class ListADT<E> implements IListADT<E> {
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
