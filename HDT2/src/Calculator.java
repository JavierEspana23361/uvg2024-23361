import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;



/**
 * La clase Calculator implementa la interfaz ICalculator y proporciona métodos para realizar operaciones aritméticas básicas.
 */

public class Calculator implements ICalculator {

    
    /** 
     * @param n1
     * @param n2
     * @return int
     */
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
    
    public ArrayList<String> read(String file) throws Exception{
        
        ArrayList<String> elements = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");
            for (String word : words) {
                elements.add(word);
            }
        }
        reader.close();
        return elements;
    }

    public int solve(ArrayList<String> elements) throws Exception {
        Stack<Integer> stack = new Stack<>();

        for (String element : elements) {
            if (isOperand(element)) {
                stack.push(Integer.parseInt(element));
            } else if (isOperator(element)) {
                if (stack.count() < 2) {
                    throw new IllegalArgumentException("Error: Insufficient operands");
                }
                int n2 = stack.pop();
                int n1 = stack.pop();
                int result = performOperation(n1, n2, element);
                stack.push(result);
            }
        }

        if (stack.count() != 1) {
            throw new IllegalArgumentException("Error: Invalid expression");
        }

        int finalResult = stack.pop();
        System.out.println("Resultado: " + finalResult);
        return finalResult;
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
}
