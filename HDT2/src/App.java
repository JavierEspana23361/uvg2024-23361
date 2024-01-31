import java.util.Stack;

public class App {
    public static void main(String[] args) {
        // Crear una instancia de Stack
        Stack<Integer> stack = new Stack<>();

        // Agregar elementos a la pila
        stack.push(2);
        stack.push(5);
        stack.push(8);

        // Imprimir el elemento en la cima de la pila
        System.out.println("Elemento en la cima de la pila: " + stack.peek());

        // Imprimir la cantidad de elementos en la pila
        System.out.println("Cantidad de elementos en la pila: " + stack.size());

        // Imprimir si la pila está vacía
        System.out.println("¿La pila está vacía? " + stack.isEmpty());

        // Realizar operación pop para obtener y eliminar el elemento en la cima de la pila
        int poppedElement = stack.pop();
        System.out.println("Elemento obtenido mediante pop: " + poppedElement);

        // Imprimir la pila después de la operación pop
        System.out.println("Pila después de pop: " + stack);

        // Imprimir la cantidad de elementos en la pila después de la operación pop
        System.out.println("Cantidad de elementos en la pila: " + stack.size());
    }
}


