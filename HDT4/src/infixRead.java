import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class infixRead implements ICalculator{
    
    /**
     * Performs the operation on the given list.
     * This method reads an infix expression from a file, converts it to postfix notation,
     * and then performs the operation using a postfix calculator.
     *
     * @param list the list to perform the operation on
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void doOperation(ListADT<String> list) throws IOException{
        postfixCalcFactory factory = new postfixCalcFactory();
        ListADT<String> postList = new ListADT<>();
        ListADT<String> infix = new ListADT<>();
        ArrayList<String> arrinfix = new ArrayList<>();
        String file = "datos.txt";
        infix = read(file);
        arrinfix = ListADTtoArraylist(infix);
        String postfix = infixToPostfix(arrinfix);
        postList = stringtoListADT(postfix);
        factory.getCalculator().doOperation(postList);
    }
    
    /**
     * Reads the contents of a file and returns a list of strings.
     * Each string represents an element read from the file.
     *
     * @param file the path of the file to be read
     * @return a list of strings representing the elements read from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public ListADT<String> read(String file) throws IOException{
        ListADT<String> infix = new ListADT<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split("");
                for (String element : elements) {
                    infix.agregar(element);
                }
            }
        }
        return infix;
    }
    
    /**
     * Converts a ListADT to an ArrayList.
     * 
     * @param list the ListADT to be converted
     * @return an ArrayList containing the elements of the ListADT
     */
    public ArrayList<String> ListADTtoArraylist(ListADT<String> list){
        ArrayList<String> elements = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            elements.add(list.obtener(i));
        }
        return elements;
    }
    
    /**
     * Converts a string into a ListADT<String> object.
     * Each word in the string is added as an element to the list.
     * 
     * @param str the string to be converted
     * @return a ListADT<String> object containing the words from the string
     */
    public ListADT<String> stringtoListADT(String str){
        ListADT<String> list = new ListADT<>();
        String[] words = str.split(" ");
        for (String word : words) {
            list.agregar(word);
        }
        return list;
    }


    /**
     * Converts an infix expression to a postfix expression.
     * 
     * @param elements the list of elements in the infix expression
     * @return the postfix expression as a string
     * @throws IllegalArgumentException if the parentheses in the infix expression are unbalanced
     */
    public String infixToPostfix(ArrayList<String> elements) {
        PileADT<String> pile = new PileADT<>();
        StringBuilder postfix = new StringBuilder();
    
        for (String element : elements) {
            if (isOperand(element)) {
                postfix.append(element).append(" ");
            } else if (isOperator(element)) {
                while (!pile.isEmpty() && !pile.peek().equals("(") && hasHigherPrecedence(pile.peek(), element)) {
                    postfix.append(pile.pop()).append(" ");
                }
                pile.push(element);
            } else if (element.equals("(")) {
                pile.push("(");
            } else if (element.equals(")")) {
                while (!pile.isEmpty() && !pile.peek().equals("(")) {
                    postfix.append(pile.pop()).append(" ");
                }
                if (!pile.isEmpty() && pile.peek().equals("(")) {
                    pile.pop(); 
                } else {
                    throw new IllegalArgumentException("Paréntesis desbalanceados en la expresión infix");
                }
            }
        }
    
        while (!pile.isEmpty()) {
            if (pile.peek().equals("(")) {
                throw new IllegalArgumentException("Paréntesis desbalanceados en la expresión infix");
            }
            postfix.append(pile.pop()).append(" ");
        }
    
        String postfixExpression = postfix.toString().trim();
        return postfixExpression;
    }

    /**
     * Checks if the given element is an operand.
     * An operand can be a letter or a digit.
     *
     * @param element the element to be checked
     * @return true if the element is an operand, false otherwise
     */
    public boolean isOperand(String element) {
        return Character.isLetterOrDigit(element.charAt(0));
    }

    /**
     * Checks if the given element is an operator.
     * 
     * @param element the element to be checked
     * @return true if the element is an operator, false otherwise
     */
    public boolean isOperator(String element) {
        return element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/");
    }

    /**
     * Checks if the first operator has a higher precedence than the second operator.
     * 
     * @param op1 the first operator
     * @param op2 the second operator
     * @return true if op1 has higher precedence than op2, false otherwise
     */
    private boolean hasHigherPrecedence(String op1, String op2) {
        return (op1.equals("*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-"));
    }

}
