import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class infixRead implements ICalculator{
    
    public void doOperation(ListADT<String> list) throws IOException{
        postfixCalcFactory factory = new postfixCalcFactory();
        ListADT<String> postfix = new ListADT<>();
        String file = "datos.txt";
        postfix = infixToPostfix(read(file));
        factory.getCalculator().doOperation(postfix);
    }
    
    public ListADT<String> read(String file) throws IOException{
        ListADT<String> infix = new ListADT<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                infix.agregar(line);
            }
        }
        return infix;
    }

    public ListADT<String> infixToPostfix(ListADT<String> infix) {
        ListADT<String> postfix = new ListADT<>();
        PileADT<String> pile = new PileADT<>();

        for (int i = 0; i < infix.size(); i++) {
            String token = infix.obtener(i);

            if (isOperand(token)) {
                postfix.agregar(token);
            } else if (isOperator(token)) {
                while (!pile.isEmpty() && !pile.peek().equals("(") && hasHigherPrecedence(pile.peek(), token)) {
                    postfix.agregar(pile.pop());
                }
                pile.push(token);
            } else if (token.equals("(")) {
                pile.push("(");
            } else if (token.equals(")")) {
                while (!pile.isEmpty() && !pile.peek().equals("(")) {
                    postfix.agregar(pile.pop());
                }
                pile.pop();
            }
        }

        while (!pile.isEmpty()) {
            postfix.agregar(pile.pop());
        }

        return postfix;
    }

    public boolean isOperand(String element) {
        return Character.isLetterOrDigit(element.charAt(0));
    }

    public boolean isOperator(String element) {
        return element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/");
    }

    private boolean hasHigherPrecedence(String op1, String op2) {
        return (op1.equals("*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-"));
    }

}
