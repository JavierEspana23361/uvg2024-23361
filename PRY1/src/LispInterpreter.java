import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//Prueba

public class LispInterpreter{

    public Map<String, BiFunction<Double, Double, Double>> functions;
    public Map<String, Double> variables;
    public Map<String, ArrayList<ArrayList<String>>> defunctions;

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
        int openParenthesisCount = 0;
        int countZero = 0;
        ArrayList<String> function = new ArrayList<>();
        ArrayList<String> nameVariables = new ArrayList<>();
        // Separar los nombres de las variables y la función
        for (String element: functionBody) {
            if (countZero == 1) {
                function.add(element);
            }
            if (element.equals("(")) {
                openParenthesisCount++;
            } else if (element.equals(")")) {
                openParenthesisCount--;
                if (openParenthesisCount == 0) {
                    countZero++;
                }
            } else {
                if (countZero == 0) {
                    nameVariables.add(element);
                }
            }
        }

        ArrayList<ArrayList<String>> functionList = new ArrayList<>();

        functionList.add(nameVariables);
        functionList.add(function);

        defunctions.put(functionName, functionList);
    }
 
    /**
     * Retrieves the body of a DEFUN function given its name.
     *
     * @param functionName the name of the DEFUN function
     * @return the body of the DEFUN function as an ArrayList of strings
     * @throws Exception 
     */
    public ArrayList<String> getDEFUN(String functionName, ArrayList<String> valVar) throws Exception {
        ArrayList<String> functionBody = new ArrayList<>();
        Map<String, String> variablesFun = new HashMap<>();
        if (valVar.size() == defunctions.get(functionName).get(0).size()) {
            for (int i = 0; i < valVar.size(); i++) {
                variablesFun.put(defunctions.get(functionName).get(0).get(i), valVar.get(i));
            }
        } else {
            throw new Exception("Error: Número de variables incorrecto");
        }
        
        for (String element: defunctions.get(functionName).get(1)) {
            for (Map.Entry<String, String> entry : variablesFun.entrySet()) {
                if (element.equals(entry.getKey())) {
                    functionBody.add(entry.getValue().toString());
                    break;
                } else if (!variablesFun.containsKey(element)) {
                    functionBody.add(element);
                    break;
                }
            }
        }

        return functionBody;
    }   

    /**
     * The root class in the Java class hierarchy. All classes in Java are subclasses of Object.
     * This class provides basic methods that are inherited by all other classes.
     */
    public Object eval(ArrayList<String> elements) throws Exception {
        Stack<Object> stack = new Stack<>();
        int brk = 0;
        int prk = 0;
        int list = 0;
        int count = 0;

        boolean found = true;
        while (found) {
            found = false;
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).equals("DEFUN")) {
                    stack.clear();
                    brk = 1;
                    String functionName = elements.get(elements.indexOf("DEFUN") + 1);
    
                    ArrayList<String> functionBody = new ArrayList<>();
    
                    for (int j = elements.indexOf("DEFUN") + 2; j < elements.size(); j++) {
                        if (elements.get(j).equals(")" ) && count == 0) {
                            break;
                        } else if (elements.get(j).equals("(")) {
                            count++;
                        } else if (elements.get(j).equals(")")) {
                            count--;
                        }
                        functionBody.add(elements.get(j));
                    }
                    elements.clear();
                    setDEFUN(functionName, functionBody);
                    count = 0;
                    break;
                } else if (isFunction(elements.get(i))) {
                    found = true;
                    String functionName = elements.get(i);
                    int indexFunction = i;
                    int openParenthesisCount = 0;
                    int countZero = 0;
                    int operation = 0;
                    int operands = 0;
                    ArrayList<String> valorVariables = new ArrayList<>();
    
                    
                    for (int j = indexFunction + 1; j < elements.size(); j++) {
                        if (elements.get(j).equals("(")) {
                            openParenthesisCount++;
                        } else if (elements.get(j).equals(")")) {
                            openParenthesisCount--;
                        } else if (!elements.get(j).equals("(") && !elements.get(j).equals(")")) {
                            if (isOperator(elements.get(j))) {
                                operation++;
                            } else if (isOperand(elements.get(j))) {
                                operands++;
                            }
                            valorVariables.add(elements.get(j));
                        }
    
                        if (openParenthesisCount == 0) {
                            break;
                        }
                    }
    
                    ArrayList<String> valueVariables = new ArrayList<>();
                    ArrayList<String> functionBody = new ArrayList<>();
    
                    if (operation > 0 && operands > 0) {
                        valorVariables.add(0, "(");
                        valorVariables.add(")");
                        int valueInt = (int) eval(valorVariables);
                        valueVariables.add(Integer.toString(valueInt));
                        functionBody = getDEFUN(functionName, valueVariables);
                        if (defunctions.get(functionName).get(0).size() > 1) {
                            for (int k = 0; k <= defunctions.get(functionName).get(0).size() + 2; k++) {
                                elements.remove(indexFunction);
                            }
                            for (int k = functionBody.size() - 1; k != -1; k--) {
                                elements.add(indexFunction ,functionBody.get(k)); 
                            }
                        } else {
                            if (valorVariables.size() == operation + operands) {
                                for (int k = 0; k <= valorVariables.size(); k++) {
                                    elements.remove(indexFunction);
                                }
                                for (int k = 0; k < functionBody.size(); k++) {
                                    elements.add(indexFunction, functionBody.get(k)); 
                                }
                            } else {
                                for (int k = 0; k <= valorVariables.size(); k++) {
                                    elements.remove(indexFunction);
                                }
                                for (int k = functionBody.size() - 1; k > -1; k--) {
                                    elements.add(indexFunction, functionBody.get(k)); 
                                }
                            }
                            
                        }
                    } else {
                        valueVariables = valorVariables;
                        functionBody = getDEFUN(functionName, valueVariables);

                        if (defunctions.get(functionName).get(0).size() > 1) {
                            for (int k = 0; k <= defunctions.get(functionName).get(0).size() + 2; k++) {
                                elements.remove(indexFunction);
                            }
                            for (int k = functionBody.size() - 1; k != -1; k--) {
                                elements.add(indexFunction ,functionBody.get(k)); 
                            }
                        } else {
                            for (int k = 0; k <= defunctions.get(functionName).get(0).size() + 2; k++) {
                                elements.remove(indexFunction);
                            }
                            for (int k = 0; k < functionBody.size(); k++) {
                                elements.add(functionBody.get(k)); 
                            }
                        }
                        functionBody.clear();  
                    }
                    break;
                } else if (elements.get(i).equals("COND")) { 
                    found = true;               
                    int openParenthesisCount = 0;
                    int countZero = 0;
                    int indexCond = i;
                    int countCaracters = 0;
    
                    Map<Integer, ArrayList<ArrayList<String>>> trueMap = new HashMap<>();
                    trueMap.clear();
    
                    ArrayList<String> clause = new ArrayList<>();
    
                    ArrayList<String> trueArrayList = new ArrayList<>();
    
                    ArrayList<String> elseArrayList = new ArrayList<>();
    
                    ArrayList<ArrayList<String>> clauseAndTrue = new ArrayList<>();
    
                    String trueorfalse;
    
                    // Ciclo para encontrar cantidad de paréntesis y caracteres
                    for (int j = indexCond + 1; j < elements.size(); j++) {
                        if (elements.get(j).equals("(")) {
                            openParenthesisCount++;
                            countCaracters++;
                        } else if (elements.get(j).equals(")")) {
                            openParenthesisCount--;
                            countCaracters++;
                            if (openParenthesisCount == 0) {
                                countZero++;
                            }
                            if (elements.get(j + 1).equals(")")) {
                                if (elements.get(indexCond - 1).equals("(")) {
                                    openParenthesisCount--;
                                }
                                break;
                            }
    
                        } else if (!elements.get(j).equals("(") && !elements.get(j).equals(")")) {
                            countCaracters++;
                        }
                    }
    
                    // Ciclo para encontrar las cláusulas
                    int countParenthesis = countZero + 1;
                    countZero = 0;
    
                    for (int j = indexCond + 1; j <= countCaracters + 1; j++) {
                        if (elements.get(j).equals("(")) {
                            openParenthesisCount++;
                            if (countZero % 2 == 0 && countZero == countParenthesis - 1) {
                                elseArrayList.add(elements.get(j));
                            } else if (countZero % 2 == 0) {
                                clause.add(elements.get(j));    
                            } else if (countZero % 2 != 0) {
                                trueArrayList.add(elements.get(j));
                            }
                        } else if (elements.get(j).equals(")")) {
                            openParenthesisCount--;
                            if (countZero % 2 == 0 && countZero == countParenthesis - 1) {
                                elseArrayList.add(elements.get(j));
                            } else if (countZero % 2 == 0) {
                                clause.add(elements.get(j));
                                clauseAndTrue.add(new ArrayList<>(clause));
                                clause.clear();
                            } else if (countZero % 2 != 0) {
                                trueArrayList.add(elements.get(j));
                                clauseAndTrue.add(new ArrayList<>(trueArrayList));
                                trueArrayList.clear();
                                trueMap.put(countZero, clauseAndTrue);
                            }
    
                            if (openParenthesisCount == 0) {
                                countZero++;
                            }
                        } else if (!elements.get(j).equals("(") && !elements.get(j).equals(")")) {
                            if (countZero % 2 == 0 && countZero == countParenthesis - 1) {
                                elseArrayList.add(elements.get(j));
                            } else if (countZero % 2 == 0) {
                                clause.add(elements.get(j));
                            } else if (countZero % 2 != 0) {
                                trueArrayList.add(elements.get(j));
                            }
                        }
                    }
                    
                    for (Map.Entry<Integer, ArrayList<ArrayList<String>>> entry : trueMap.entrySet()) {
                        trueorfalse = (String) eval(entry.getValue().get(0));
                        if (trueorfalse.equals("T")) {
                            for (int k = 0; k <= countCaracters + 2; k++) {
                                elements.remove(indexCond - 1);
                            }
                            for (int k = entry.getValue().get(1).size() - 2; k != -1; k--) {
                                String elemento = entry.getValue().get(1).get(k);
                                elements.add(indexCond - 1, elemento);
                            }
                            break;
                        } else if (trueorfalse.equals("NIL")) {
                            for (int k = 0; k <= countCaracters + 2; k++) {
                                elements.remove(indexCond - 1);
                            }
                            for (int k = elseArrayList.size() - 1; k != -1; k--) {
                                String elemento = elseArrayList.get(k);
                                elements.add(indexCond - 1, elemento);
                            }
                        }
                    }
    
                    clause.clear();
                    trueArrayList.clear();
                    elseArrayList.clear();
                    break;
                }
            }
        }
        
        boolean found2 = true;
        int index = 0;
        int indexOperator = 0;
        while (found2) {
            found2 = false;
            int stackSize = stack.size();
            index = 0;
            for (int i = 0; i < stackSize; i++) {
                stack.pop();
            }
            for (String element : elements) {
                if (isOperand(element)) {
                    stack.push(Double.parseDouble(element));
                }  else if (isVariable(element)) {
                    stack.push((Double) variables.get(element));
                } else if (element.equals("(")) {
                    stack.push(element);
                } else if (element.equals("SETQ")) {
                    prk = 1;
                    Object variable = elements.get(elements.indexOf("SETQ") + 1);
                    Double value = null;
                    ArrayList<String> valueList = new ArrayList<>();
                    for (int i = elements.indexOf("SETQ") + 2; i < elements.size(); i++) {
                        if (elements.get(i).equals(")" ) && count == 0) {
                            break;
                        } else if (elements.get(i).equals("(")) {
                            count++;
                        } else if (elements.get(i).equals(")")) {
                            count--;
                        }
                        valueList.add(elements.get(i));
                    }
                    for (String values: valueList) {
                        if (isVariable(values)) {
                            values = String.valueOf(variables.get(values));
                        } 
                    }
                    if (valueList.size() == 1) {
                        String val = valueList.get(0);
                        value = Double.parseDouble(val);
                        SETQ(variable, value);
                    } else if (valueList.size() > 1) {
                        value = (Double) eval(valueList);
                        SETQ(variable, value);
                    }
                    elements.clear();
                    stack.clear();
                    break;
                } else if (isOperator(element)) {
                    indexOperator = index;
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
                        String quote = QUOTE(operands);
                        stack.push(quote);
                    } else if (operator.equals("LIST")) {
                        ArrayList<Object> additions = LIST(operands);
                        for (Object addition : additions) {
                            stack.push(addition);
                        }
                        list = 1;
                    } else if (operator.equals("ATOM")) {
                        stack.push(ATOM(operands));
                    } else if (operator.equals(">") || operator.equals("<") || operator.equals("EQUAL")) {
                        String result = comparation(operands, operator);
                        for (int i = 0; i < operands.size() + 2; i++) {
                            elements.remove(indexOperator - 1);
                        }
                        for (int i = 0; i < elements.size(); i++) {
                            elements.add(indexOperator - 1, result);
                        }
                    } else {
                        Object result = performOperation(operands, operator);
                        for (int i = 0; i < operands.size() + 3; i++) {
                            elements.remove(indexOperator - 1);
                        }
                        Double resultDouble = (Double) result;
                        String resultString = Double.toString(resultDouble);
                        elements.add(indexOperator - 1, resultString);
                        found2 = true;
                        break;
                    }
                }
                index++;
            }
        }

        if (stack.size() == 1 && stack.peek() instanceof Double) {
            return stack.pop();
        } else if (stack.size() == 1 && stack.peek() instanceof String) {
            return stack.pop();
        } else if (brk == 1) {
           return "Función definida";
        } else if (prk == 1){
           return "Variable definida";
        } else if (list == 1) {
            ArrayList<String> listResult = new ArrayList<>();
            while (!stack.isEmpty()) {
                listResult.add((stack.pop()).toString());
            }
            return listResult;
        } else {
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
        Pattern pattern = Pattern.compile("\"[^\"]*\"|\\(|\\)|\\w+|[+\\-*/()<>=]|SQRT|EXPT|EQUAL|ATOM|QUOTE|SETQ|LIST|COND|DEFUN");
        Matcher matcher = pattern.matcher(expression);
    
        // Agregar cada coincidencia al ArrayList de tokens
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
    
        return tokens;
    }

    public String comparation(ArrayList<Object> operands, Object operator) {
        if (operator instanceof String) {
            String op = (String) operator;
            switch (op) {
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
     * Performs the specified operation on the given operands.
     *
     * @param operands The list of operands on which the operation is performed.
     * @param operator The operator that specifies the type of operation to be performed.
     * @return The result of the operation.
     * @throws IllegalArgumentException If the operator is not a valid string.
     */
    public Double performOperation(ArrayList<Object> operands, Object operator) {
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
                case "SQRT":
                    return root(operands);
                case "EXPT":
                    return exponentiation(operands);
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
    public Double add(ArrayList<Object> operands) {
        Double sum = 0.0;
        System.out.println(operands);
        for (Object operand : operands) {
            if (operand instanceof Double) {
                double operandDouble = (Double) operand;
                sum += operandDouble;
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
    public Double subtract(ArrayList<Object> operands) {
        if (operands.size() < 2) {
            throw new IllegalArgumentException("Error: Invalid operands for subtraction");
        }
        Double result = (Double) operands.get(0);

        for (int i = 1; i < operands.size(); i++) {
            Double operand = (Double) operands.get(i);
            if (operand instanceof Double) {
                Double operandInt = (Double) operand;
                result -= operandInt;
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
    public Double multiplication(ArrayList<Object> operands) {
        Double product = 1.0;
        for (Object operand : operands) {
            if (operand instanceof Double) {
                double operandDouble = (Double) operand;
                product *= operandDouble;
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
    public Double division(ArrayList<Object> operands) {
        if (operands.size() < 2) {
            throw new IllegalArgumentException("Error: Invalid operands for division");
        }
        Double result = (Double) operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            Double operand = (Double) operands.get(i);
            if (operand instanceof Double && (Double) operand != 0) {
                result /= operand;
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
    public Double root(ArrayList<Object> operands) {
        if (operands.size() != 2) {
            throw new IllegalArgumentException("Error: Invalid operands for root");
        }
        double base = (double) operands.get(0);
        double exponent = (double) operands.get(1);
        double result = Math.pow(base, 1/exponent);
        return result;
    }

    /**
     * Calculates the exponentiation of two numbers.
     *
     * @param operands An ArrayList containing the base and exponent.
     * @return The result of raising the base to the exponent.
     * @throws IllegalArgumentException if the number of operands is not 2.
     */
    public Double exponentiation(ArrayList<Object> operands) {
        if (operands.size() != 2) {
            throw new IllegalArgumentException("Error: Invalid operands for exponentiation");
        }
        Double base = (Double) operands.get(0);
        Double exponent = (Double) operands.get(1);
        Double result = Math.pow(base, exponent);
        return result;
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
    public String QUOTE(ArrayList<Object> operands) {
        String quote = "";
        for (int i = operands.size() - 1; i > -1; i--){
            quote += operands.get(i) + " ";
        }
        return quote;
    }

    /**
     * Sets the value of a variable in the LispInterpreter.
     * 
     * @param variable the name of the variable to set
     * @param value the value to assign to the variable
     * @throws IllegalArgumentException if the value is not a valid type for SETQ
     */
    public void SETQ(Object variable, Double value) {
        variables.put((String) variable, (Double) value);
        System.out.println(variables);
        
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
    /*public String COND(ArrayList<Object> operands) {
        if (operands.size() != 3) {
            throw new IllegalArgumentException("Error: Invalid operands for cond");
        }
        String clause = (String) operands.get(0);
        Object trueValue = (Object) operands.get(1);
        Object falseValue = (Object) operands.get(2);
        
        if (clause.equals("T")) {
            return trueValue.toString();
        } else if (clause.equals("NIL")) {
            return falseValue.toString();
        } else {
            throw new IllegalArgumentException("Error: Invalid operands for cond");
        }  
    }*/

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