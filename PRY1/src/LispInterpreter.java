import java.util.ArrayList;

public class LispInterpreter {
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

    public int QUOTE(int n1){
        return n1;
    }

    public int DEFUN(int n1, int n2){
        return n1;
    }

    public int SETQ(int n1, int n2){
        return n1;
    }

    public int ATOM(int n1){
        return n1;
    }

    public int LIST(int n1){
        return n1;
    }

    public int EQUAL(int n1, int n2){
        return n1;
    }

    public String LESS(int n1, int n2){
        String X = "True";
        String Y = "False";
        if (n1 < n2){
        
            return X;
        }
        else{
            return Y;
        }
    }

    public String GREATER(int n1, int n2){
        String X = "True";
        String Y = "False";
        if (n1 > n2){
        
            return X;
        }
        else{
            return Y;
    }
    public int COND(int n1, int n2){
        return n1;
    
    }
    
       

    public ArrayList<String> {
            
        ArrayList<String> elements = new ArrayList<>();
        //Añadir el lector de LISP
        
           
        return elements;
    }


    //Hay que adaptarlo, para que lea LISP
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
        return element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/") || 
        element.equals("QUOTE") || element.equals("DEFUN") || element.equals("SETQ") || element.equals("ATOM") || element.equals("LIST")
        || element.equals("EQUAL") || element.equals("<") || element.equals(">") || element.equals("COND");
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
            case "QUOTE":
                return QUOTE(n1);
            case "DEFUN":
                return DEFUN(n1, n2);
            case "SETQ":
                return SETQ(n1, n2);
            case "ATOM":
                return ATOM(n1);
            case "LIST":   
                return LIST(n1);
            case "EQUAL":
                return EQUAL(n1, n2);
            case "<":
                return LESS(n1, n2);
            case ">":
                return GREATER(n1, n2);
            case "COND":
                return COND(n1, n2);
            
            default:
                throw new IllegalArgumentException("Error: Invalid operator");
        }
    }
}
     
}