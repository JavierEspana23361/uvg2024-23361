import java.io.IOException;
import java.util.Stack;

public class postfixCalc implements ICalculator {

    public void doOperation(ListADT<String> list) throws IOException{
        String postfix = ListADTtoString(list);
        ListADT<String> elements = postfixreader(postfix);
        int result = solvePostfix(elements);
        System.out.println("El resultado de la operación obtenida de datos.txt es: " + result);
    }

    public int add(int n1, int n2){

        return n1 + n2;
    }

    public int substract(int n1, int n2){
        return n1 - n2;

    }
    
    public int multiplication(int n1, int n2){
        return n1 * n2;

    }
    
    public int division(int n1, int n2){
        return n1 / n2;

    }
    
    public int residue(int n1, int n2){
        return n1 % n2;

    }
    
    public String ListADTtoString(ListADT<String> list){
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result += list.obtener(i) + " ";
        }
        return result;
    }

    public ListADT<String> postfixreader(String pstfxexpression) {
        ListADT<String> elements = new ListADT<>();
        String[] words = pstfxexpression.split(" ");
        for (String word : words) {
            elements.agregar(word);
        }
        return elements;
    }

    public boolean isOperand(String element) {
        try {
            Integer.parseInt(element);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isOperator(String element) {
        return element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/") || element.equals("%");
    }

    private int performOperation(int n1, int n2, String operator) {
        switch (operator) {
            case "+":
                return add(n1, n2);
            case "-":
                return substract(n1, n2);
            case "*":
                return multiplication(n1, n2);
            case "/":
                return division(n1, n2);
            case "%":
                return residue(n1, n2);
            default:
                throw new IllegalArgumentException("Error: Invalid operator");
        }
    }
    
    public int solvePostfix(ListADT<String> elements) {
        PileADT<Integer> pile = new PileADT<>();

        for (int i = 0; i < elements.size(); i++) {
            String element = elements.obtener(i);
            if (isOperand(element)) {
                pile.push(Integer.parseInt(element));
            } else if (isOperator(element)) {
                int n2 = pile.pop();
                int n1 = pile.pop();
                int result = performOperation(n1, n2, element);
                pile.push(result);
            }
        }
        return pile.pop();
    }

}
