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

                } else if (operator.equals("QUOTE")) {
                    return operands;

                } else if (operator.equals("SETQ")) {
                    String variableName = (String) operands.get(0);
                    Object value = operands.get(1);
                    return SETQ(variableName, value);
                    
                } else if (operator.equals("LIST")) {
                    ArrayList<Object> additions = LIST(operands);
                    for (Object addition : additions) {
                        stack.push(addition);
                    }
                }

                Object result = performOperation(operands, operator);
                if (result instanceof Double) {
                    stack.push(result);
                } else if (result instanceof String) {
                    stack.push(result.equals("T") ? "True" : "False");
                }
            } else if (isVariable(element)) {
                stack.push(variables.get(element));
            }
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

    private boolean isVariable(String element) {
        return variables.containsKey(element);
    }

    public ArrayList<String> tokenize(String expression) {
        ArrayList<String> tokens = new ArrayList<>();

        // Patrón para identificar números, operadores y paréntesis
        Pattern pattern = Pattern.compile("\\d+\\.\\d+|\\d+|[+\\-*/()<>=]|ROOT|EXP|EQUAL|ATOM|QUOTE|SETQ|LIST");
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
                //case "ATOM": return isAtom(operands).equals("T") ? "T" : "NIL";
                case "QUOTE":
                    return QUOTE(operands);
                case "SETQ":
                    if (operands.size() != 2) {
                        throw new IllegalArgumentException("Error: SETQ solo puede tener dos operandos");
                    }
                    if (operands.get(0) instanceof String) {
                        String variableName = (String) operands.get(0);
                        Object value = operands.get(1);
                        return SETQ(variableName, value);
                    } else {
                        throw new IllegalArgumentException("Error: Invalid operands for SETQ");
                    }
                case "LIST":
                    return LIST(operands);
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

    public Object SETQ(String variableName, Object value) {
        variables.put(variableName, (Double) value);
        return value;
    }
    
    public ArrayList<Object> LIST(ArrayList<Object> elements) {
        ArrayList<Object> lispList = new ArrayList<Object>();
        for (Object element : elements) {
            lispList.add(element);
        }
        return lispList;
    }

    /*  Aun no funciona ATOM
    private String isAtom(ArrayList<Object> operands) {
        if (operands.size() != 1) {
            throw new IllegalArgumentException("Error: Invalid operands for atom");
        }
        Object operand = operands.get(0);
        if (operand instanceof Double || operand instanceof String) {
            return "T";
        } else {
            return "NIL";
        }
    }
    */
}
