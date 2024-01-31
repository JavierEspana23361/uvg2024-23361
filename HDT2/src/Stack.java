import java.util.ArrayList;

public class Stack<T> implements IStack<T> {

    Calculator calc = new Calculator();
    private ArrayList<T> stack;

    public Stack() {
        stack = new ArrayList<>();
    }
    

    @Override
    public int count() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public void push(T value) {
        stack.add(value);
    }

    @Override
    public T pop() {
        if (!isEmpty()) {
            return stack.remove(stack.size() - 1);
        } else {
            throw new IllegalStateException("ERROR: Stack Vacio");
        }
    }

    @Override
    public T peek() {
        if (!isEmpty()) {
            return stack.get(stack.size() - 1);
        } else {
            throw new IllegalStateException("ERROR: Stack Vacio");
        }
    }
}
