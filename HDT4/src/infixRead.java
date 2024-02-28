import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class infixRead implements ICalculator{
    
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
    
    public ArrayList<String> ListADTtoArraylist(ListADT<String> list){
        ArrayList<String> elements = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            elements.add(list.obtener(i));
        }
        return elements;
    }
    
    public ListADT<String> stringtoListADT(String str){
        ListADT<String> list = new ListADT<>();
        String[] words = str.split(" ");
        for (String word : words) {
            list.agregar(word);
        }
        return list;
    }


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

    public boolean isOperand(String element) {
        return Character.isLetterOrDigit(element.charAt(0));
    }

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
