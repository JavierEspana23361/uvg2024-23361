import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LispInterpreter{

    private Map<String, BiFunction<Double, Double, Double>> functions;
    private Map<String, Object> variables;
    private Map<String, ArrayList<Object>> defunctions;



    public LispInterpreter() {
        functions = new HashMap<>();
        variables = new HashMap<>();
        defunctions = new HashMap<>();
    }

    public void setDEFUN(String functionName, ArrayList<Object> functionBody) {
        defunctions.put(functionName, functionBody);
    }

    public Object getDEFUN(String functionName, ArrayList<Object> arguments) {
        ArrayList<Object> functionBody = defunctions.get(functionName);
        if (functionBody != null) {
            try {
                Map<String, Object> localVariables = new HashMap<>();
                for (int i = 0; i < arguments.size(); i++) {
                    localVariables.put("ARG" + i, arguments.get(i));
                }
                return eval(functionBody, localVariables);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error al evaluar la función: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Error: Función no encontrada");
        }
    }        
    
    public Object eval(ArrayList<Object> elements, Map<String, Object> localVariables) throws Exception {
        try {
            Stack<Object> stack = new Stack<>();
    
            for (Object element : elements) {
                if (element instanceof String) {
                    String strElement = (String) element;
                    if (isVariable(strElement)) {
                        // Si es una variable, intenta obtener su valor de las variables locales primero,
                        // y luego de las variables globales si no se encuentra en las locales
                        stack.push(localVariables.getOrDefault(strElement, variables.get(strElement)));
                    } else if (isOperand(strElement)) {
                        // Si es un operando, simplemente agrégalo a la pila
                        stack.push(Double.parseDouble(strElement));
                    } else if (strElement.equals("(")) {
                        stack.push(strElement);
                    } else if (isOperator(strElement)) {
                        stack.push(strElement);
                    } else if (isClause(strElement)) {
                        stack.push(strElement.equals("True") ? "T" : "NIL");
                    } else if (strElement.equals(")")) {
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
                            throw new IllegalArgumentException("Error: Expresión inválida 1.0");
                        }
    
                        stack.pop();
    
                        if (operator == null) {
                            throw new IllegalArgumentException("Error: Operador no encontrado");
                        }
    
                        if (operator.equals("DEFUN")) {
                            if (operands.size() < 2) {
                                throw new IllegalArgumentException("Error: Sintaxis incorrecta para DEFUN");
                            }
                            String functionName = (String) operands.get(0);
                            ArrayList<Object> functionBody = new ArrayList<>();
                            for (int i = 1; i < operands.size(); i++) {
                                functionBody.add(operands.get(i));
                            }
                            setDEFUN(functionName, functionBody);
                        } else if (isFunction(strElement)) {
                            // Obtener el cuerpo de la función
                            ArrayList<Object> functionBody = defunctions.get(strElement);
    
                            // Crear una nueva instancia de LispInterpreter para evaluar el cuerpo de la función
                            LispInterpreter functionInterpreter = new LispInterpreter();
    
                            // Evaluar el cuerpo de la función usando la nueva instancia de LispInterpreter
                            Object result = functionInterpreter.eval(functionBody, localVariables);
    
                            // Empujar el resultado al stack
                            stack.push(result);
                        } else if (operator.equals("QUOTE")) {
                            QUOTE(operands);
                            for (Object obj : operands) {
                                stack.push(obj);
                            }
                        } else if (operator.equals("SETQ")) {
                            String variable = (String) operands.get(1);
                            Object value = operands.get(0);
                            SETQ(variable, value, localVariables);
                            stack.push("Variable set: " + variable + " = " + value);
    
                        } else if (operator.equals("LIST")) {
                            ArrayList<Object> additions = LIST(operands);
                            for (Object addition : additions) {
                                stack.push(addition);
                            }
                        } else if (operator.equals("COND")) {
                            stack.push(COND(operands));
                        } else if (operator.equals("ATOM")) {
                            stack.push(ATOM(operands));
                        } else {
                            // Llamada a una función incorporada
                            Object result = performOperation(operands, operator);
                            if (result instanceof Double) {
                                stack.push(result);
                            } else if (result instanceof String) {
                                stack.push(result.equals("T") ? "True" : "False");
                            }
                        }
    
                    } else {
                        stack.push(element);
                    }
                }
            }
    
            System.out.println("Tamaño de la pila stack: " + stack.size());
            System.out.println("Elemento en la cima de la pila stack: " + stack.peek());
    
            Object result = stack.pop();
            if (result instanceof Double) {
                return (Double) result;
            } else if (result instanceof String) {
                return (String) result;
            } else {
                throw new IllegalArgumentException("Error: Expresión inválida 2.0");
            }
    
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda surgir durante la evaluación
            throw new Exception("Error durante la evaluación: " + e.getMessage());
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

    private boolean isFunction(String element) {
        return defunctions.containsKey(element);
    }

    public ArrayList<Object> tokenize(String expression) {
        ArrayList<Object> tokens = new ArrayList<>();
    
        // Patrón para identificar números, operadores y paréntesis
        Pattern pattern = Pattern.compile("\\(|\\)|[+\\-*/()<>=]|\\d+|\\w+");
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

    private void SETQ(String variable, Object value, Map<String, Object> localVariables) {
        if (value instanceof Double) {
            localVariables.put(variable, (Double) value);
        } else if (value instanceof String) {
            if (value.equals("T")) {
                localVariables.put(variable, 1.0);
            } else if (value.equals("NIL")) {
                localVariables.put(variable, 0.0);
            } else {
                throw new IllegalArgumentException("Error: Valor no válido para SETQ");
            }
        } else {
            throw new IllegalArgumentException("Error: Valor no válido para SETQ");
        }
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

    private String ATOM(ArrayList<Object> operands) {
        if (operands.size() != 1) {
            return "NIL";
        } else {
            return "T";
        }
    }
}
