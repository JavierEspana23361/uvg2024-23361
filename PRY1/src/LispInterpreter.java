import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LispInterpreter{

    public Map<String, BiFunction<Double, Double, Double>> functions;
    public Map<String, Object> variables;
    public Map<String, ArrayList<String>> defunctions;

    /**
     * The LispInterpreter class represents an interpreter for the Lisp programming language.
     * It provides functionality to define functions, variables, and defunctions.
     */
    public LispInterpreter() {
        functions = new HashMap<>();
        variables = new HashMap<>();
        defunctions = new HashMap<>();
    }

    /**
     * Sets a new function definition in the Lisp interpreter.
     * 
     * @param functionName the name of the function
     * @param functionBody the body of the function as an ArrayList of strings
     */
    public void setDEFUN(String functionName, ArrayList<String> functionBody) {
        defunctions.put(functionName, functionBody);
    }
 
    /**
     * Retrieves the body of a DEFUN function given its name.
     *
     * @param functionName the name of the DEFUN function
     * @return the body of the DEFUN function as an ArrayList of strings
     */
    public ArrayList<String> getDEFUN(String functionName) {
        ArrayList<String> functionBody = defunctions.get(functionName);
        return functionBody;
    }   
    
    /**
     * The root class in the Java class hierarchy. All classes in Java are subclasses of Object.
     * This class provides basic methods that are inherited by all other classes.
     */
    public Object eval(ArrayList<String> elements) throws Exception {
        Stack<Object> stack = new Stack<>();
        int brk = 0;
        int list = 0;
        for (String element : elements) {
            if (isOperand(element)) {
                stack.push(Double.parseDouble(element));
            } else if (isFunction(element)){
                ArrayList<String> functions = getDEFUN(element);
                int index = elements.indexOf(element);
                elements.remove(index);
                for (String function : functions) {
                    elements.add(index, function);
                    index++;
                }
                stack.clear();
                return eval(elements);
            } else if (isVariable(element)) {
                stack.push(variables.get(element));
            } else if (element.equals("(")) {
                stack.push(element);
            } else if (element.equals("DEFUN")) {
                stack.clear();
                brk = 1;
                String functionName = elements.get(elements.indexOf("DEFUN") + 1);
                ArrayList<String> functionBody = new ArrayList<>();
                for (int i = elements.indexOf("DEFUN") + 2; i < elements.size(); i++) {
                    if (elements.get(i).equals(")")) {
                        functionBody.add(")");
                        break;
                    }
                    functionBody.add(elements.get(i));
                }
                elements.clear();
                setDEFUN(functionName, functionBody);
                break;
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
                    
                } else if (operator.equals("QUOTE")) {
                    QUOTE(operands);
                    for (Object obj : operands) {
                        stack.push(obj);
                    }
                } else if (operator.equals("SETQ")) {
                    String variable = (String) operands.get(1);
                    Object value = operands.get(0);
                    SETQ(variable, value);
                    stack.push("Variable set: " + variable +  " = " + value);
                } else if (operator.equals("LIST")) {
                    ArrayList<Object> additions = LIST(operands);
                    for (Object addition : additions) {
                        stack.push(addition);
                    }
                    list = 1;
                } else if (operator.equals("COND")) {
                    stack.push(COND(operands));
                } else if (operator.equals("ATOM")) {
                    stack.push(ATOM(operands));
                } else {
                    Object result = performOperation(operands, operator);
                    if (result instanceof Double) {
                        stack.push(result);
                    } else if (result instanceof String) {
                        stack.push(result.equals("T") ? "True" : "False");
                    }
                }
            } else
                stack.push(element);
        }

        if (stack.size() == 1 && stack.peek() instanceof Double) {
            return stack.pop();
        } else if (stack.size() == 1 && stack.peek() instanceof String) {
            return stack.pop();
        } else if (brk == 1) {
           return "Funcion definida ";
        } else if (list == 1) {
            ArrayList<String> listResult = new ArrayList<>();
            while (!stack.isEmpty()) {
                listResult.add((stack.pop()).toString());
            }
            return listResult;
        }
        else {
            throw new IllegalArgumentException("Error: Expresión inválida");
        }
    }

    /**
     * Checks if the given element is an operand.
     * An operand is a numeric value that can be parsed as a double.
     *
     * @param element the element to check
     * @return true if the element is an operand, false otherwise
     */
    public boolean isOperand(String element) {
        try {
            Double.parseDouble(element);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the given element is an operator.
     *
     * @param element the element to check
     * @return true if the element is an operator, false otherwise
     */
    public boolean isOperator(String element) {
        return element.equals("+") || element.equals("-") || element.equals("*") || element.equals("/") 
        || element.equals("QUOTE") || element.equals("DEFUN") || element.equals("SETQ") || element.equals("ATOM") 
        || element.equals("LIST") || element.equals("EQUAL") || element.equals("<") || element.equals(">") || element.equals("COND")
        || element.equals("SQRT") || element.equals("EXPT");
    }

    /**
     * Checks if the given element is a clause.
     *
     * @param element the element to check
     * @return true if the element is "True" or "False", false otherwise
     */
    public boolean isClause(String element) {
        return element.equals("True") || element.equals("False");
    }

    /**
     * Checks if the given element is a variable.
     *
     * @param element the element to check
     * @return true if the element is a variable, false otherwise
     */
    public boolean isVariable(String element) {
        return variables.containsKey(element);
    }
    
    /**
     * Checks if the given element is a function.
     *
     * @param element the element to check
     * @return true if the element is a function, false otherwise
     */
    public boolean isFunction(String element) {
        return defunctions.containsKey(element);
    }

    /**
     * Tokenizes the given expression into a list of tokens.
     *
     * @param expression the expression to tokenize
     * @return an ArrayList containing the tokens
     */
    public ArrayList<String> tokenize(String expression) {
        ArrayList<String> tokens = new ArrayList<>();
    
        // Patrón para identificar números, operadores, paréntesis, y strings entre comillas dobles
        Pattern pattern = Pattern.compile("\"[^\"]*\"|\\(|\\)|\\w+|[+\\-*/()<>=]|SQTR|EXPT|EQUAL|ATOM|QUOTE|SETQ|LIST|COND|DEFUN");
        Matcher matcher = pattern.matcher(expression);
    
        // Agregar cada coincidencia al ArrayList de tokens
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
    
        return tokens;
    }
    
    /**
     * Performs the specified operation on the given operands.
     *
     * @param operands The list of operands on which the operation is performed.
     * @param operator The operator that specifies the type of operation to be performed.
     * @return The result of the operation.
     * @throws IllegalArgumentException If the operator is not a valid string.
     */
    public Object performOperation(ArrayList<Object> operands, Object operator) {
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
                case "SQTR":
                    return root(operands);
                case "EXPT":
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

    /**
     * Adds up all the elements in the given list of operands.
     * 
     * @param operands the list of operands to be added
     * @return the sum of all the operands
     * @throws IllegalArgumentException if any of the operands is not a Double
     */
    public double add(ArrayList<Object> operands) {
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

    /**
     * Subtracts the elements in the given ArrayList of operands.
     * 
     * @param operands the ArrayList of operands to subtract
     * @return the result of subtracting the operands
     * @throws IllegalArgumentException if the number of operands is less than 2 or if any operand is not a Double
     */
    public double subtract(ArrayList<Object> operands) {
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

    /**
     * Calculates the multiplication of a list of operands.
     * 
     * @param operands The list of operands to be multiplied.
     * @return The product of the multiplication.
     * @throws IllegalArgumentException if any of the operands is not a double.
     */
    public double multiplication(ArrayList<Object> operands) {
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

    /**
     * Performs division on a list of operands.
     *
     * @param operands The list of operands to be divided.
     * @return The result of the division operation.
     * @throws IllegalArgumentException If the number of operands is less than 2 or if any operand is zero.
     */
    public double division(ArrayList<Object> operands) {
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

    /**
     * Calculates the root of a number.
     * 
     * @param operands An ArrayList containing the base and exponent of the root.
     * @return The result of the root calculation.
     * @throws IllegalArgumentException if the number of operands is not equal to 2.
     */
    public double root(ArrayList<Object> operands) {
        if (operands.size() != 2) {
            throw new IllegalArgumentException("Error: Invalid operands for root");
        }
        double base = (double) operands.get(0);
        double exponent = (double) operands.get(1);
        return Math.pow(base, 1/exponent);
    }

    /**
     * Calculates the exponentiation of two numbers.
     *
     * @param operands An ArrayList containing the base and exponent.
     * @return The result of raising the base to the exponent.
     * @throws IllegalArgumentException if the number of operands is not 2.
     */
    public double exponentiation(ArrayList<Object> operands) {
        if (operands.size() != 2) {
            throw new IllegalArgumentException("Error: Invalid operands for exponentiation");
        }
        double base = (double) operands.get(0);
        double exponent = (double) operands.get(1);
        return Math.pow(base, exponent);
    }

    /**
     * Determines if the first operand is greater than the second operand.
     *
     * @param operands the list of operands to compare
     * @return "T" if the first operand is greater than the second operand, "NIL" otherwise
     * @throws IllegalArgumentException if the number of operands is not equal to 2
     */
    public String isMajor(ArrayList<Object> operands) {
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

    /**
     * Determines if the first operand is less than the second operand.
     * 
     * @param operands the list of operands to compare
     * @return "T" if the first operand is less than the second operand, "NIL" otherwise
     * @throws IllegalArgumentException if the number of operands is not equal to 2
     */
    public String isMinor(ArrayList<Object> operands) {
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

    /**
     * Checks if two operands are equal.
     *
     * @param operands The list of operands to compare.
     * @return "T" if the operands are equal, "NIL" otherwise.
     * @throws IllegalArgumentException if the number of operands is not 2.
     */
    public String isEqual(ArrayList<Object> operands) {
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

    /**
     * Returns the input operands as is, without evaluating them.
     *
     * @param operands the list of operands to be quoted
     * @return the input operands without any evaluation
     */
    public ArrayList<Object> QUOTE(ArrayList<Object> operands) {
        return null;
    }

    /**
     * Sets the value of a variable in the LispInterpreter.
     * 
     * @param variable the name of the variable to set
     * @param value the value to assign to the variable
     * @throws IllegalArgumentException if the value is not a valid type for SETQ
     */
    public void SETQ(String variable, Object value) {
        if (value instanceof Double) {
            variables.put(variable, (Double) value);
        } else if (value instanceof String) {
            if (value.equals("T")) {
                variables.put(variable, 1.0);
            } else if (value.equals("NIL")) {
                variables.put(variable, 0.0);
            } else {
                throw new IllegalArgumentException("Error: Valor no válido para SETQ");
            }
        } else {
            throw new IllegalArgumentException("Error: Valor no válido para SETQ");
        }
    }
    
    /**
     * Creates a new Lisp list from the given elements.
     *
     * @param elements the elements to be added to the list
     * @return a new ArrayList containing the elements
     */
    public ArrayList<Object> LIST(ArrayList<Object> elements) {
        ArrayList<Object> lispList = new ArrayList<Object>();
        for (Object element : elements) {
            lispList.add(element);
        }
        return lispList;
    }

    /**
     * The String class represents a sequence of characters. In this context, it is used to store and manipulate textual data.
     */
    public String COND(ArrayList<Object> operands) {
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

    /**
     * Checks if the given operands represent an atomic value.
     *
     * @param operands the list of operands to check
     * @return "T" if the operands represent an atomic value, "NIL" otherwise
     */
    public String ATOM(ArrayList<Object> operands) {
        if (operands.size() != 1) {
            return "NIL";
        } else {
            return "T";
        }
    }
}

