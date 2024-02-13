import java.util.ArrayList;

/**
 * Esta clase representa una pila genérica.
 * Implementa la interfaz IStack<T>.
 * Permite almacenar elementos de tipo T en una estructura de pila.
 */
public class Stack<T> implements IStack<T> {

    //Calculator calc = new Calculator();
    private ArrayList<T> stack;

    /**
     * Constructor de la clase Stack.
     * Inicializa una pila vacía.
     */
    public Stack() {
        stack = new ArrayList<>();
    }
    
    /**
     * Retorna la cantidad de elementos en la pila.
     * @return La cantidad de elementos en la pila.
     */
    @Override
    public int count() {
        return stack.size();
    }

    /**
     * Verifica si la pila está vacía.
     * @return true si la pila está vacía, false de lo contrario.
     */
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * Agrega un elemento a la pila.
     * @param value El elemento a agregar.
     */
    @Override
    public void push(T value) {
        stack.add(value);
    }

    /**
     * Remueve y retorna el elemento en la cima de la pila.
     * @return El elemento en la cima de la pila.
     * @throws IllegalStateException si la pila está vacía.
     */
    @Override
    public T pop() {
        if (!isEmpty()) {
            return stack.remove(stack.size() - 1);
        } else {
            throw new IllegalStateException("ERROR: Stack Vacio");
        }
    }

    /**
     * Retorna el elemento en la cima de la pila sin removerlo.
     * @return El elemento en la cima de la pila.
     * @throws IllegalStateException si la pila está vacía.
     */
    @Override
    public T peek() {
        if (!isEmpty()) {
            return stack.get(stack.size() - 1);
        } else {
            throw new IllegalStateException("ERROR: Stack Vacio");
        }
    }
}
