import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class infixRead implements ICalculator{
    
    
    public ListADT<String> read(String file) throws IOException {
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
        PileADT<Character> pile = new PileADT<>();

        for (int i = 0; i < infix.size(); i++) {
            String token = infix.obtener(i);

            if (isOperand(token.charAt(0))) {
                postfix.agregar(token);
            } else if (isOperator(token.charAt(0))) {
                while (!pile.isEmpty() && pile.peek() != '(' && hasHigherPrecedence(pile.peek(), token.charAt(0))) {
                    postfix.agregar(Character.toString(pile.pop()));
                }
                pile.push(token.charAt(0));
            } else if (token.equals("(")) {
                pile.push('(');
            } else if (token.equals(")")) {
                while (!pile.isEmpty() && pile.peek() != '(') {
                    postfix.agregar(Character.toString(pile.pop()));
                }
                pile.pop();
            }
        }

        while (!pile.isEmpty()) {
            postfix.agregar(Character.toString(pile.pop()));
        }

        return postfix;
    }

    private boolean isOperand(char c) {
        return Character.isLetterOrDigit(c);
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean hasHigherPrecedence(char op1, char op2) {
        return (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-');
    }

}
