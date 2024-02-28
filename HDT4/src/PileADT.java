import java.util.ArrayList;
import java.util.EmptyStackException;

public class PileADT<E> implements IPileADT<E> {
    protected ArrayList<E> elementos;

    public PileADT() {
        elementos = new ArrayList<>();
    }

    @Override
    public void push(E elemento) {
        elementos.add(elemento);
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elementos.remove(elementos.size() - 1);
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elementos.get(elementos.size() - 1);
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
