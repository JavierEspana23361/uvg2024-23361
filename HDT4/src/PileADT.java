import java.util.ArrayList;

/**
 * This class represents a generic implementation of a stack data structure.
 * It implements the IPileADT interface.
 * @param <E> the type of elements stored in the stack
 */
public class PileADT<E> implements IPileADT<E> {
    infixRead inread = new infixRead();
    postfixCalc ptcalc = new postfixCalc();
    ArrayList<E> elements;

    /**
     * Constructs an empty stack.
     */
    public PileADT() {
        elements = new ArrayList<>();
    }

    /**
     * Returns the number of elements in the stack.
     * @return the number of elements in the stack
     */
    @Override
    public int count() {
        return elements.size();
    }

    /**
     * Checks if the stack is empty.
     * @return true if the stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Pushes an element onto the top of the stack.
     * @param value the element to be pushed onto the stack
     */
    @Override
    public void push(E value) {
        elements.add(value);
    }

    /**
     * Removes and returns the element at the top of the stack.
     * @return the element at the top of the stack, or null if the stack is empty
     */
    @Override
    public E pop() {
        if (!isEmpty()) {
            return elements.remove(elements.size() - 1);
        }
        return null; 
    }

    /**
     * Returns the element at the top of the stack without removing it.
     * @return the element at the top of the stack, or null if the stack is empty
     */
    @Override
    public E peek() {
        if (!isEmpty()) {
            return elements.get(elements.size() - 1);
        }
        return null; 
    }
}
