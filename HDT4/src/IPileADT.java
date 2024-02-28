public interface IPileADT<E> {
    void push(E elemento);
    E pop();
    E peek();
    int size();
    boolean isEmpty();
} 