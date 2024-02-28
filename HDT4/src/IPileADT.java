public interface IPileADT<E> {

    int count();
       
    boolean isEmpty();
    
    void push(E value);
    
    E pop();
    
    E peek();
    
}
