import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LispInterpreter{

    private Map<String, BiFunction<Double, Double, Double>> functions;
    private Map<String, Double> variables;


    public LispInterpreter() {
        functions = new HashMap<>();
        variables = new HashMap<>();
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
            } else if (isClause(element)) {
                stack.push(element.equals("True") ? "T" : "NIL");
            } else if (element.equals(")")) {
                ArrayList<Object> operands = new ArrayList<>();
                Object operator = null;

                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    Object top = stack.pop();

                    if (top instanceof Double) {
                        operands.add(0, top);
                    } else if (top instanceof String) {
                        if (top.equals("True") || top.equals("False")) {
                            operands.add(0, top.equals("True") ? "T" : "NIL");
                        } else if (functions.containsKey(top)) {
                            operator = top;
                        } else if (isOperator((String) top)) {
                            operator = top;
                        } else {
                            operands.add(top);
                        }
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

                } else if (operator.equals("QUOTE")) {
                    for (Object obj : operands) {
                        stack.push(obj);
                    }
                } else if (operator.equals("SETQ")) {
                    String variableName = (String) operands.get(0);
                    Object value = operands.get(1);
                    return SETQ(variableName, value);
                    
                } else if (operator.equals("LIST")) {
                    ArrayList<Object> additions = LIST(operands);
                    for (Object addition : additions) {
                        stack.push(addition);
                    }
                } else if (operator.equals("COND")) {
                    stack.push(COND(operands));
                } else {
                    Object result = performOperation(operands, operator);
                    if (result instanceof Double) {
                        stack.push(result);
                    } else if (result instanceof String) {
                        stack.push(result.equals("T") ? "True" : "False");
                    }
                }
            } else if (isVariable(element)) {
                stack.push(variables.get(element));
            } else
                stack.push(element);
        }

        if (stack.size() == 1 && stack.peek() instanceof Double) {
            return stack.pop();
        } else if (stack.size() == 1 && stack.peek() instanceof String) {
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
        return element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/") 
        || element.equals("QUOTE") || element.equals("DEFUN") || element.equals("SETQ") || element.equals("ATOM") 
        || element.equals("LIST") || element.equals("EQUAL") || element.equals("<") || element.equals(">") || element.equals("COND")
        || element.equals("ROOT") || element.equals("EXP");
    }

    private boolean isClause(String element) {
        return element.equals("True") || element.equals("False");
    }

    private boolean isVariable(String element) {
        return variables.containsKey(element);
    }

    public ArrayList<String> tokenize(String expression) {
        ArrayList<String> tokens = new ArrayList<>();
    
        // Patrón para identificar números, operadores, paréntesis, y strings entre comillas dobles
        Pattern pattern = Pattern.compile("\"[^\"]*\"|\\(|\\)|\\w+|[+\\-*/()<>=]|ROOT|EXP|EQUAL|ATOM|QUOTE|SETQ|LIST|COND");
        Matcher matcher = pattern.matcher(expression);
    
        // Agregar cada coincidencia al ArrayList de tokens
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
    
        return tokens;
    }
    

    private Object performOperation(ArrayList<Object> operands, Object operator) {
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
                case "ROOT":
                    return root(operands);
                case "EXP":
                    return exponentiation(operands);
                case "<":
                    return isMinor(operands).equals("T") ? "T" : "NIL";
                case ">":
                    return isMajor(operands).equals( "T") ? "T" : "NIL";
                case "EQUAL":
                    return isEqual(operands).equals("T") ? "T" : "NIL";
                default:
                    throw new IllegalArgumentException("Error: Operador no válido");
            }
        } else {
            throw new IllegalArgumentException("Error: Operador no válido");
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

    private double root(ArrayList<Object> operands) {
        if (operands.size() != 2) {
            throw new IllegalArgumentException("Error: Invalid operands for root");
        }
        double base = (double) operands.get(0);
        double exponent = (double) operands.get(1);
        return Math.pow(base, 1/exponent);
    }

    private double exponentiation(ArrayList<Object> operands) {
        if (operands.size() != 2) {
            throw new IllegalArgumentException("Error: Invalid operands for exponentiation");
        }
        double base = (double) operands.get(0);
        double exponent = (double) operands.get(1);
        return Math.pow(base, exponent);
    }

    private String isMajor(ArrayList<Object> operands) {
        if (operands.size() != 2) {
            throw new IllegalArgumentException("Error: Invalid operands for comparison");
        }
        double a = (double) operands.get(0);
        double b = (double) operands.get(1);
        if (a > b) {
            return "T";
        } else {
            return "NIL";
        }
    }

    private String isMinor(ArrayList<Object> operands) {
        if (operands.size() != 2) {
            throw new IllegalArgumentException("Error: Invalid operands for comparison");
        }
        double a = (double) operands.get(0);
        double b = (double) operands.get(1);
        if (a < b) {
            return "T";
        } else {
            return "NIL";
        }
    }

    private String isEqual(ArrayList<Object> operands) {
        if (operands.size() != 2) {
            throw new IllegalArgumentException("Error: Invalid operands for comparison");
        }
        double a = (double) operands.get(0);
        double b = (double) operands.get(1);
        if (a == b) {
            return "T";
        } else {
            return "NIL";
        }
    }

    private ArrayList<Object> QUOTE(ArrayList<Object> operands) {
        return operands;
    }

    private Object SETQ(String variableName, Object value) {
        variables.put(variableName, (Double) value);
        return value;
    }
    
    private ArrayList<Object> LIST(ArrayList<Object> elements) {
        ArrayList<Object> lispList = new ArrayList<Object>();
        for (Object element : elements) {
            lispList.add(element);
        }
        return lispList;
    }

    private String COND(ArrayList<Object> operands) {
        if (operands.size() != 3) {
            throw new IllegalArgumentException("Error: Invalid operands for cond");
        }
        String clause = (String) operands.get(0);
        String trueValue = (String) operands.get(1);
        String falseValue = (String) operands.get(2);
        if (clause.equals("T")) {
            return falseValue;
        } else {
            return trueValue;
        }
    }
}
