
public interface IStack<T>{

    int count();
    
    boolean isEmpty();
    
    void push(T value);
    
    T pop();
    
    T peek();
    
}

