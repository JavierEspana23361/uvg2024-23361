import java.util.ArrayList;

public class postfixCalc implements ICalculator {
    infixRead infix = new infixRead();

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
    
    public ListADT<String> postfixreader(String pstfxexpression) {
        ListADT<String> elements = new ListADT<>();
        String[] words = pstfxexpression.split(" ");
        for (String word : words) {
            elements.agregar(word);
        }
        return elements;
    }

    private boolean isOperand(String element) {
        try {
            Integer.parseInt(element);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String element) {
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


    
    public int solve(ArrayList<String> elements) throws Exception {
        PileADT<Integer> pile = new PileADT<>();

        for (String element : elements) {
            if (isOperand(element)) {
                pile.push(Integer.parseInt(element));
            } else if (isOperator(element)) {
                if (pile.count() < 2) {
                    throw new IllegalArgumentException("Error: Insufficient operands");
                }
                int n2 = pile.pop();
                int n1 = pile.pop();
                int result = performOperation(n1, n2, element);
                pile.push(result);
            }
        }

        if (pile.count() != 1) {
            throw new IllegalArgumentException("Error: Invalid expression");
        }

        int finalResult = pile.pop();
        System.out.println("Resultado: " + finalResult);
        return finalResult;
    }
}
