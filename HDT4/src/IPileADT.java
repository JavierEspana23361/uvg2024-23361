/**
 * This interface represents a generic stack data structure.
 * It provides methods to manipulate the stack such as push, pop, and peek.
 * 
 * @param <E> the type of elements stored in the stack
 */
public interface IPileADT<E> {

    /**
     * Returns the number of elements in the stack.
     * 
     * @return the number of elements in the stack
     */
    int count();
       
    /**
     * Checks if the stack is empty.
     * 
     * @return true if the stack is empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Adds an element to the top of the stack.
     * 
     * @param value the element to be added to the stack
     */
    void push(E value);
    
    /**
     * Removes and returns the element at the top of the stack.
     * 
     * @return the element at the top of the stack
     */
    E pop();
    
    /**
     * Returns the element at the top of the stack without removing it.
     * 
     * @return the element at the top of the stack
     */
    E peek();
    
}
