import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;

public class LispInterpreter {

    //DEFUN
    private Map<String, BiFunction<Integer, Integer, Integer>> functions;

    public LispInterpreter() {
        functions = new HashMap<>();
    }

    public Object DEFUN(String functionName, BiFunction<Integer, Integer, Integer> function) {
        System.out.println("Definiendo función: " + functionName);

        if (functions.containsKey(functionName)) {
            return "Error: La función '" + functionName + "' ya está definida.";
        }

        functions.put(functionName, function);

        return "Función definida: " + functionName;
    }

    public Integer executeFunction(String functionName, int n1, int n2) {
        BiFunction<Integer, Integer, Integer> function = functions.get(functionName);

        if (function != null) {
            return function.apply(n1, n2);
        } else {
            System.out.println("Error: La función '" + functionName + "' no está definida.");
            return null;
        }
    }
    //.....................................................

    public Object eval(ArrayList<String> elements) throws Exception {
        Stack<Object> stack = new Stack<>();

        for (String element : elements) {
            if (isOperand(element)) {
                stack.push(Integer.parseInt(element));
            } else if (isOperator(element)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Error: Insufficient operands");
                }
                Object n2 = stack.pop();
                Object n1 = stack.pop();
                Object result = performOperation(n1, n2, element);
                stack.push(result);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Error: Invalid expression");
        }

        Object finalResult = stack.pop();
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

    private Object performOperation(Object n1, Object n2, String operator) {
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

    private int add(Object n1, Object n2) {
        if (n1 instanceof Integer && n2 instanceof Integer) {
            return (int) n1 + (int) n2;
        } else {
            throw new IllegalArgumentException("Error: Invalid operands for addition");
        }
    }

    private int substract(Object n1, Object n2) {
        if (n1 instanceof Integer && n2 instanceof Integer) {
            return (int) n1 - (int) n2;
        } else {
            throw new IllegalArgumentException("Error: Invalid operands for subtraction");
        }
    }

    private int multiplication(Object n1, Object n2) {
        if (n1 instanceof Integer && n2 instanceof Integer) {
            return (int) n1 * (int) n2;
        } else {
            throw new IllegalArgumentException("Error: Invalid operands for multiplication");
        }
    }

    private int division(Object n1, Object n2) {
        if (n1 instanceof Integer && n2 instanceof Integer) {
            return (int) n1 / (int) n2;
        } else {
            throw new IllegalArgumentException("Error: Invalid operands for division");
        }
    }

    private Object QUOTE(Object n1) {
        return n1;
    }

    private Object DEFUN(Object n1, Object n2) {
        return n1;
    }

    private Object SETQ(Object n1, Object n2) {
        return n1;
    }

    private Object ATOM(Object n1) {
        return n1;
    }

    private Object LIST(Object n1) {
        return n1;
    }

    private Object EQUAL(Object n1, Object n2) {
        return n1;
    }

    private String LESS(Object n1, Object n2) {
        String X = "True";
        String Y = "False";
        if ((int) n1 < (int) n2) {
            return X;
        } else {
            return Y;
        }
    }

    private String GREATER(Object n1, Object n2) {
        String X = "True";
        String Y = "False";
        if ((int) n1 > (int) n2) {
            return X;
        } else {
            return Y;
        }
    }

    private Object COND(Object n1, Object n2) {
        return n1;
    }
}
