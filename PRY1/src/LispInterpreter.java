import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LispInterpreter{

    private Map<String, BiFunction<Double, Double, Double>> functions;

    public LispInterpreter() {
        functions = new HashMap<>();
        initializeFunctions(); 
    }

    private void initializeFunctions() {
        functions.put("QUOTE", (a, b) -> 0.0);
        functions.put("DEFUN", (a, b) -> 0.0);
        functions.put("SETQ", (a, b) -> 0.0);
        functions.put("ATOM", (a, b) -> 0.0);
        functions.put("LIST", (a, b) -> 0.0);
        functions.put("EQUAL", (a, b) -> 0.0);
        functions.put("<", (a, b) -> 0.0);
        functions.put(">", (a, b) -> 0.0);
        functions.put("COND", (a, b) -> 0.0);
    }

    public Object DEFUN(String functionName, BiFunction<Double, Double, Double> function) {
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
                stack.push(Double.parseDouble(element));
            } else if (element.equals("(")) {
                stack.push(element);
            } else if (isOperator(element)) {
                stack.push(element);
            } else if (element.equals(")")) {
                ArrayList<Object> operands = new ArrayList<>();
                Object operator = null;

                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    Object top = stack.pop();

                    if (top instanceof Double) {
                        operands.add(0, top);
                    } else if (top instanceof String) {
                        operator = top;
                    }
                }

                if (stack.isEmpty() || !stack.peek().equals("(")) {
                    throw new IllegalArgumentException("Error: Expresión inválida");
                }

                stack.pop();

                if (operator == null) {
                    throw new IllegalArgumentException("Error: Operador no encontrado");
                }

                if (operator.equals("DEFUN")) {
                    String functionName = (String) operands.get(0);
                    Object potentialFunction = operands.get(1);

                    if (potentialFunction instanceof BiFunction<?, ?, ?>) {
                        BiFunction<Double, Double, Double> function = (BiFunction<Double, Double, Double>) potentialFunction;
                        return DEFUN(functionName, function);
                    } else {
                        throw new IllegalArgumentException("Error: La función no es del tipo BiFunction<Double, Double, Double>");
                    }
                }

                Object result = performOperation(operands, operator);
                stack.push(result);
            }
        }

        if (stack.size() == 1 && stack.peek() instanceof Double) {
            return stack.pop();
        } else {
            throw new IllegalArgumentException("Error: Expresión inválida");
        }
    }

    private boolean isOperand(String element) {
        try {
            Double.parseDouble(element);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String element) {
        return element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/") || element.equals("QUOTE") || element.equals("DEFUN") || element.equals("SETQ") || element.equals("ATOM") || element.equals("LIST") || element.equals("EQUAL") || element.equals("<") || element.equals(">") || element.equals("COND");
    }

    public ArrayList<String> tokenize(String expression) {
        ArrayList<String> tokens = new ArrayList<>();

        // Patrón para identificar números, operadores y paréntesis
        Pattern pattern = Pattern.compile("\\d+\\.\\d+|\\d+|[+\\-*/()<>=]");
        Matcher matcher = pattern.matcher(expression);

        // Agregar cada coincidencia al ArrayList de tokens
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }

    private double performOperation(ArrayList<Object> operands, Object operator) {
        if (operator instanceof String) {
            String op = (String) operator;
            switch (op) {
                case "+":
                    return add(operands);
                case "-":
                    return subtract(operands);
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

    private double add(ArrayList<Object> operands) {
        double sum = 0;
        for (Object operand : operands) {
            if (operand instanceof Double) {
                sum += (double) operand;
            } else {
                throw new IllegalArgumentException("Error: Invalid operands for addition");
            }
        }
        return sum;
    }

    private double subtract(ArrayList<Object> operands) {
        if (operands.size() < 2) {
            throw new IllegalArgumentException("Error: Invalid operands for subtraction");
        }
        double result = (double) operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            Object operand = operands.get(i);
            if (operand instanceof Double) {
                result -= (double) operand;
            } else {
                throw new IllegalArgumentException("Error: Invalid operands for subtraction");
            }
        }
        return result;
    }

    private double multiplication(ArrayList<Object> operands) {
        double product = 1;
        for (Object operand : operands) {
            if (operand instanceof Double) {
                product *= (double) operand;
            } else {
                throw new IllegalArgumentException("Error: Invalid operands for multiplication");
            }
        }
        return product;
    }

    private double division(ArrayList<Object> operands) {
        if (operands.size() < 2) {
            throw new IllegalArgumentException("Error: Invalid operands for division");
        }
        double result = (double) operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            Object operand = operands.get(i);
            if (operand instanceof Double && (double) operand != 0) {
                result /= (double) operand;
            } else {
                throw new IllegalArgumentException("Error: Invalid operands for division");
            }
        }
        return result;
    }
}