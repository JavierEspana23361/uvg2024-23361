import java.io.IOException;

/**
 * This class represents a postfix calculator that implements the ICalculator interface.
 * It provides methods to perform arithmetic operations on postfix expressions.
 */
/**
 * The postfixCalc class implements the ICalculator interface and provides methods for performing postfix calculations.
 */
public class postfixCalc implements ICalculator {

    /**
     * Performs the postfix calculation using the given list of elements.
     * 
     * @param list the list of elements representing the postfix expression
     * @throws IOException if an I/O error occurs
     */
    public void doOperation(ListADT<String> list) throws IOException{
        // Convert the list to a postfix expression string
        String postfix = ListADTtoString(list);
        // Convert the postfix expression string to a list of elements
        ListADT<String> elements = postfixreader(postfix);
        // Solve the postfix expression and get the result
        int result = solvePostfix(elements);
        // Print the result
        System.out.println("El resultado de data.txt es: " + result);
    }

    /**
     * Adds two numbers.
     * 
     * @param n1 the first number
     * @param n2 the second number
     * @return the sum of the two numbers
     */
    public int add(int n1, int n2){
        return n1 + n2;
    }

    /**
     * Subtracts two numbers.
     * 
     * @param n1 the first number
     * @param n2 the second number
     * @return the difference between the two numbers
     */
    public int substract(int n1, int n2){
        return n1 - n2;
    }
    
    /**
     * Multiplies two numbers.
     * 
     * @param n1 the first number
     * @param n2 the second number
     * @return the product of the two numbers
     */
    public int multiplication(int n1, int n2){
        return n1 * n2;
    }
    
    /**
     * Divides two numbers.
     * 
     * @param n1 the first number
     * @param n2 the second number
     * @return the quotient of the two numbers
     */
    public int division(int n1, int n2){
        return n1 / n2;
    }
    
    /**
     * Calculates the residue of dividing two numbers.
     * 
     * @param n1 the first number
     * @param n2 the second number
     * @return the residue of dividing the two numbers
     */
    public int residue(int n1, int n2){
        return n1 % n2;
    }
    
    /**
     * Converts a ListADT to a string.
     * 
     * @param list the ListADT to convert
     * @return the string representation of the ListADT
     */
    public String ListADTtoString(ListADT<String> list){
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result += list.obtener(i) + " ";
        }
        return result;
    }

    /**
     * Converts a postfix expression string to a ListADT of elements.
     * 
     * @param pstfxexpression the postfix expression string
     * @return a ListADT containing the elements of the postfix expression
     */
    public ListADT<String> postfixreader(String pstfxexpression) {
        ListADT<String> elements = new ListADT<>();
        String[] words = pstfxexpression.split(" ");
        for (String word : words) {
            elements.agregar(word);
        }
        return elements;
    }

    /**
     * Checks if a string is an operand.
     * 
     * @param element the string to check
     * @return true if the string is an operand, false otherwise
     */
    public boolean isOperand(String element) {
        try {
            Integer.parseInt(element);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a string is an operator.
     * 
     * @param element the string to check
     * @return true if the string is an operator, false otherwise
     */
    public boolean isOperator(String element) {
        return element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/") || element.equals("%");
    }

    /**
     * Performs the specified operation on two numbers.
     * 
     * @param n1 the first number
     * @param n2 the second number
     * @param operator the operator to perform the operation
     * @return the result of the operation
     * @throws IllegalArgumentException if the operator is invalid
     */
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
    
    /**
     * Solves a postfix expression and returns the result.
     * 
     * @param elements a list of elements representing the postfix expression
     * @return the result of the postfix expression
     */
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
