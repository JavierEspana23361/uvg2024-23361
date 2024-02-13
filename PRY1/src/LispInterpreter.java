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

    public Object eval(ArrayList<String> elements) throws Exception {
        Stack<Object> stack = new Stack<>();

        for (String element : elements) {
            if (isOperand(element)) {
                stack.push(Integer.parseInt(element));
            } else if (element.equals("(")) {
                stack.push(element);
            } else if (isOperator(element)) {
                stack.push(element);
            } else if (element.equals(")")) {
                ArrayList<Object> operands = new ArrayList<>();
                Object operator = null;

                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    Object top = stack.pop();

                    if (top instanceof Integer) {
                        operands.add(0, top);
                    } else if (top instanceof String) {
                        operator = top;
                    }
                }

                if (stack.isEmpty() || !stack.peek().equals("(")) {
                    throw new IllegalArgumentException("Error: Invalid expression");
                }

                stack.pop(); 

                if (operator == null) {
                    throw new IllegalArgumentException("Error: Operator not found");
                }

                Object result = performOperation(operands, operator);
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

    public ArrayList<String> tokenize(String expression) {
        ArrayList<String> tokens = new ArrayList<>();
        String[] elements = expression.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").split("\\s+");

        for (String element : elements) {
            if (!element.isEmpty()) {
                tokens.addAll(tokenizeElement(element));
            }
        }

        return tokens;
    }

    private ArrayList<String> tokenizeElement(String element) {
        ArrayList<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();

        for (char c : element.toCharArray()) {
            if (c == '(' || c == ')') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else {
                token.append(c);
            }
        }

        if (token.length() > 0) {
            tokens.add(token.toString());
        }

        return tokens;
    }


    private boolean isOperator(String element) {
        return element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/") ||
                element.equals("QUOTE") || element.equals("DEFUN") || element.equals("SETQ") || element.equals("ATOM") || element.equals("LIST")
                || element.equals("EQUAL") || element.equals("<") || element.equals(">") || element.equals("COND");
    }

    private Object performOperation(ArrayList<Object> operands, Object operator) {
        if (operator instanceof String) {
            String op = (String) operator;
            switch (op) {
                case "+":
                    return add(operands);
                case "-":
                    return substract(operands);
                case "*":
                    return multiplication(operands);
                case "/":
                    return division(operands);
                default:
                    throw new IllegalArgumentException("Error: Invalid operator");
            }
        } else {
            throw new IllegalArgumentException("Error: Invalid operator");
        }
    }

    private int add(ArrayList<Object> operands) {
        int sum = 0;
        for (Object operand : operands) {
            if (operand instanceof Integer) {
                sum += (int) operand;
            } else {
                throw new IllegalArgumentException("Error: Invalid operands for addition");
            }
        }
        return sum;
    }

    private int substract(ArrayList<Object> operands) {
        if (operands.size() < 2) {
            throw new IllegalArgumentException("Error: Invalid operands for subtraction");
        }
        int result = (int) operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            Object operand = operands.get(i);
            if (operand instanceof Integer) {
                result -= (int) operand;
            } else {
                throw new IllegalArgumentException("Error: Invalid operands for subtraction");
            }
        }
        return result;
    }
    

    private int multiplication(ArrayList<Object> operands) {
        int product = 1;
        for (Object operand : operands) {
            if (operand instanceof Integer) {
                product *= (int) operand;
            } else {
                throw new IllegalArgumentException("Error: Invalid operands for multiplication");
            }
        }
        return product;
    }

    private int division(ArrayList<Object> operands) {
        if (operands.size() < 2) {
            throw new IllegalArgumentException("Error: Invalid operands for division");
        }
        int result = (int) operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            Object operand = operands.get(i);
            if (operand instanceof Integer && (int) operand != 0) {
                result /= (int) operand;
            } else {
                throw new IllegalArgumentException("Error: Invalid operands for division");
            }
        }
        return result;
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
