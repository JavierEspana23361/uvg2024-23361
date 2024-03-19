import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class LispInterpreterTest {
    
    @Test
    public void testSetDEFUN() {
        LispInterpreter interpreter = new LispInterpreter();
        
        // Create a sample function body
        ArrayList<String> functionBody = new ArrayList<>();
        functionBody.add("(+ 1 2)");
        functionBody.add("(* 3 4)");
        
        // Set the DEFUN
        interpreter.setDEFUN("myFunction", functionBody);
        
        // Verify that the function was set correctly
        assertEquals(functionBody, interpreter.defunctions.get("myFunction"));
    }
    @Test
    public void testGetDEFUN() {
        LispInterpreter interpreter = new LispInterpreter();
        
        // Create a sample function body
        ArrayList<String> functionBody = new ArrayList<>();
        functionBody.add("(+ 1 2)");
        functionBody.add("(* 3 4)");
        
        // Set the DEFUN
        interpreter.setDEFUN("myFunction", functionBody);
        
        // Get the DEFUN
        ArrayList<String> retrievedFunctionBody = interpreter.getDEFUN("myFunction");
        
        // Verify that the retrieved function body is equal to the original function body
        assertEquals(functionBody, retrievedFunctionBody);
    }

    @Test
    public void testEval_Variable() throws Exception {
        LispInterpreter interpreter = new LispInterpreter();
        interpreter.SETQ("x", 5.0);
        ArrayList<String> elements = new ArrayList<>();
        elements.add("x");
        Object result = interpreter.eval(elements);
        assertEquals(5.0, result);
    }




    @Test
    public void testIsOperand_ValidOperand() {
        LispInterpreter interpreter = new LispInterpreter();
        boolean result = interpreter.isOperand("123.45");
        assertTrue(result);
    }

    @Test
    public void testIsOperand_InvalidOperand() {
        LispInterpreter interpreter = new LispInterpreter();
        boolean result = interpreter.isOperand("abc");
        assertFalse(result);
    }

    
    @Test
    public void testIsOperator_ValidOperator() {
        LispInterpreter interpreter = new LispInterpreter();
        boolean result = interpreter.isOperator("+");
        assertTrue(result);
    }

    @Test
    public void testIsOperator_InvalidOperator() {
        LispInterpreter interpreter = new LispInterpreter();
        boolean result = interpreter.isOperator("abc");
        assertFalse(result);
    }

   @Test
    public void testIsClause_ValidClause() {
        LispInterpreter interpreter = new LispInterpreter();
        boolean result = interpreter.isClause("True");
        assertTrue(result);
    }

    @Test
    public void testIsClause_InvalidClause() {
        LispInterpreter interpreter = new LispInterpreter();
        boolean result = interpreter.isClause("abc");
        assertFalse(result);
    }

   @Test
    public void testIsVariable_ValidVariable() {
        LispInterpreter interpreter = new LispInterpreter();
        interpreter.SETQ("x", 5.0);
        boolean result = interpreter.isVariable("x");
        assertTrue(result);
    }

    @Test
    public void testIsVariable_InvalidVariable() {
        LispInterpreter interpreter = new LispInterpreter();
        boolean result = interpreter.isVariable("y");
        assertFalse(result);
    }

    @Test
    public void testIsVariable_NullVariable() {
        LispInterpreter interpreter = new LispInterpreter();
        boolean result = interpreter.isVariable(null);
        assertFalse(result);
    }@Test
    public void testIsFunction_ValidFunction() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<String> functionBody = new ArrayList<>();
        functionBody.add("(+ 1 2)");
        functionBody.add("(* 3 4)");
        interpreter.setDEFUN("myFunction", functionBody);
        boolean result = interpreter.isFunction("myFunction");
        assertTrue(result);
    }

    @Test
    public void testIsFunction_InvalidFunction() {
        LispInterpreter interpreter = new LispInterpreter();
        boolean result = interpreter.isFunction("myFunction");
        assertFalse(result);
    }

    @Test
    public void testIsFunction_NullFunction() {
        LispInterpreter interpreter = new LispInterpreter();
        boolean result = interpreter.isFunction(null);
    assertFalse(result);
    }
    @Test
    public void testTokenize_ValidExpression() {
        LispInterpreter interpreter = new LispInterpreter();
        String expression = "(+ 1 2)";
        ArrayList<String> expectedTokens = new ArrayList<>();
        expectedTokens.add("(");
        expectedTokens.add("+");
        expectedTokens.add("1");
        expectedTokens.add("2");
        expectedTokens.add(")");
        ArrayList<String> actualTokens = interpreter.tokenize(expression);
        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testTokenize_EmptyExpression() {
        LispInterpreter interpreter = new LispInterpreter();
        String expression = "";
        ArrayList<String> expectedTokens = new ArrayList<>();
        ArrayList<String> actualTokens = interpreter.tokenize(expression);
        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testTokenize_ExpressionWithQuotes() {
        LispInterpreter interpreter = new LispInterpreter();
        String expression = "(PRINT \"Hello, World!\")";
        ArrayList<String> expectedTokens = new ArrayList<>();
        expectedTokens.add("(");
        expectedTokens.add("PRINT");
        expectedTokens.add("\"Hello, World!\"");
        expectedTokens.add(")");
        ArrayList<String> actualTokens = interpreter.tokenize(expression);
        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    public void testTokenize_ExpressionWithSpecialCharacters() {
        LispInterpreter interpreter = new LispInterpreter();
        String expression = "(SQTR (+ 4 9))";
        ArrayList<String> expectedTokens = new ArrayList<>();
        expectedTokens.add("(");
        expectedTokens.add("SQTR");
        expectedTokens.add("(");
        expectedTokens.add("+");
        expectedTokens.add("4");
        expectedTokens.add("9");
        expectedTokens.add(")");
        expectedTokens.add(")");
        ArrayList<String> actualTokens = interpreter.tokenize(expression);
        assertEquals(expectedTokens, actualTokens);
    }


    @Test
    public void testPerformOperation_Addition() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(1.0);
        operands.add(2.0);
        Object result = interpreter.performOperation(operands, "+");
        assertEquals(3.0, result);
    }

    @Test
    public void testPerformOperation_Subtraction() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(5.0);
        operands.add(3.0);
        Object result = interpreter.performOperation(operands, "-");
        assertEquals(2.0, result);
    }

    @Test
    public void testPerformOperation_Multiplication() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        operands.add(3.0);
        Object result = interpreter.performOperation(operands, "*");
        assertEquals(6.0, result);
    }

    @Test
    public void testPerformOperation_Division() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(10.0);
        operands.add(2.0);
        Object result = interpreter.performOperation(operands, "/");
        assertEquals(5.0, result);
    }

    
    @Test
    public void testPerformOperation_Exponentiation() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        operands.add(3.0);
        Object result = interpreter.performOperation(operands, "EXPT");
        assertEquals(8.0, result);
    }

    @Test
    public void testPerformOperation_LessThan() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        operands.add(3.0);
        Object result = interpreter.performOperation(operands, "<");
        assertEquals("T", result);
    }

    @Test
    public void testPerformOperation_GreaterThan() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(5.0);
        operands.add(3.0);
        Object result = interpreter.performOperation(operands, ">");
        assertEquals("T", result);
    }

    @Test
    public void testPerformOperation_Equality() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(5.0);
        operands.add(5.0);
        Object result = interpreter.performOperation(operands, "EQUAL");
        assertEquals("T", result);
    }

    @Test
    public void testPerformOperation_InvalidOperator() {
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        operands.add(3.0);
        try {
        } catch (IllegalArgumentException e) {
            assertEquals("Error: Operador no válido", e.getMessage());
        }
    }

    @Test
    public void testPerformOperation_InvalidOperandType() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        operands.add("3.0");
        try {
        } catch (IllegalArgumentException e) {
            assertEquals("Error: Operador no válido", e.getMessage());
        }
    }

    @Test
    public void testAdd_ValidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(1.0);
        operands.add(2.0);
        double result = interpreter.add(operands);
        assertEquals(3.0, result);
    }

    @Test
    public void testAdd_InvalidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(1.0);
        operands.add("2.0");
        assertThrows(IllegalArgumentException.class, () -> {
            interpreter.add(operands);
        });
    }
    @Test
    public void testSubtract_ValidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(5.0);
        operands.add(2.0);
        double result = interpreter.subtract(operands);
        assertEquals(3.0, result);
    }

    @Test
    public void testSubtract_InvalidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(5.0);
        Exception result = assertThrows(IllegalArgumentException.class, () -> {
            interpreter.subtract(operands);
        });
        assertEquals("Error: Invalid operands for subtraction", result.getMessage());
    }

    @Test
    public void testSubtract_MultipleOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(10.0);
        operands.add(2.0);
        operands.add(3.0);
        double result = interpreter.subtract(operands);
        assertEquals(5.0, result);
    }

    @Test
    public void testMultiplication_ValidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        operands.add(3.0);
        operands.add(4.0);
        double expectedProduct = 2.0 * 3.0 * 4.0;
        double actualProduct = interpreter.multiplication(operands);
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void testMultiplication_InvalidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        operands.add("3.0");
        operands.add(4.0);
        assertThrows(IllegalArgumentException.class, () -> {
            interpreter.multiplication(operands);
        });
    }

    @Test
    public void testDivision_ValidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(10.0);
        operands.add(2.0);
        double result = interpreter.division(operands);
        assertEquals(5.0, result);
    }

    @Test
    public void testDivision_InvalidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(10.0);
        operands.add(0.0);
        assertThrows(IllegalArgumentException.class, () -> {
            interpreter.division(operands);
        });
    }

    @Test
    public void testDivision_LessThanTwoOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(10.0);
        assertThrows(IllegalArgumentException.class, () -> {
            interpreter.division(operands);
        });
    }
    @Test
    public void testRoot_ValidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(4.0);
        operands.add(2.0);
        double result = interpreter.root(operands);
        assertEquals(2.0, result);
    }

    @Test
    public void testRoot_InvalidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(4.0);
        assertThrows(IllegalArgumentException.class, () -> {
            interpreter.root(operands);
        });
    }

   

    @Test
    public void testIsMajor_ValidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(5.0);
        operands.add(3.0);
        String result = interpreter.isMajor(operands);
        assertEquals("T", result);
    }

    @Test
    public void testIsMajor_InvalidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(5.0);
        String errorMessage = "Error: Invalid operands for comparison";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            interpreter.isMajor(operands);
        });
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testIsMinor_ValidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        operands.add(5.0);
        String result = interpreter.isMinor(operands);
        assertEquals("T", result);
    }

    @Test
    public void testIsMinor_InvalidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        String errorMessage = "Error: Invalid operands for comparison";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            interpreter.isMinor(operands);
        });
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testIsEqual_EqualOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(3.0);
        operands.add(3.0);
        String result = interpreter.isEqual(operands);
        assertEquals("T", result);
    }

    @Test
    public void testIsEqual_NotEqualOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        operands.add(3.0);
        String result = interpreter.isEqual(operands);
        assertEquals("NIL", result);
    }

    @Test
    public void testIsEqual_InvalidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(2.0);
        String errorMessage = "Error: Invalid operands for comparison";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            interpreter.isEqual(operands);
        });
        assertEquals(errorMessage, exception.getMessage());
    }
  

    @Test
    public void testSETQ_DoubleValue() {
        LispInterpreter interpreter = new LispInterpreter();
        interpreter.SETQ("x", 5.0);
        assertEquals(5.0, interpreter.variables.get("x"));
    }

    @Test
    public void testSETQ_TValue() {
        LispInterpreter interpreter = new LispInterpreter();
        interpreter.SETQ("x", "T");
        assertEquals(1.0, interpreter.variables.get("x"));
    }

    @Test
    public void testSETQ_NILValue() {
        LispInterpreter interpreter = new LispInterpreter();
        interpreter.SETQ("x", "NIL");
        assertEquals(0.0, interpreter.variables.get("x"));
    }

    @Test
    public void testSETQ_InvalidValue() {
        LispInterpreter interpreter = new LispInterpreter();
        try {
            interpreter.SETQ("x", true);
        } catch (IllegalArgumentException e) {
            assertEquals("Error: Valor no válido para SETQ", e.getMessage());
        }
    }
    @Test
    public void testLIST() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> elements = new ArrayList<>();
        elements.add(1);
        elements.add(2);
        elements.add(3);
        ArrayList<Object> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(2);
        expectedList.add(3);
        ArrayList<Object> resultList = interpreter.LIST(elements);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void testLIST_EmptyList() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> elements = new ArrayList<>();
        ArrayList<Object> expectedList = new ArrayList<>();
        ArrayList<Object> resultList = interpreter.LIST(elements);
        assertEquals(expectedList, resultList);
    }


    @Test
    public void testCOND_ValidClause() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add("T");
        operands.add("True Value");
        operands.add("False Value");
        String result = interpreter.COND(operands);
        assertEquals("False Value", result);
    }


    @Test
    public void testCOND_InvalidOperands() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add("T");
        operands.add("True Value");
        assertThrows(IllegalArgumentException.class, () -> {
            interpreter.COND(operands);
        });
    }
    @Test
    public void testATOM_ValidOperand() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(123.45);
        String result = interpreter.ATOM(operands);
        assertEquals("T", result);
    }

    @Test
    public void testATOM_InvalidOperand() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        operands.add(1.0);
        operands.add(2.0);
        String result = interpreter.ATOM(operands);
        assertEquals("NIL", result);
    }

    @Test
    public void testATOM_EmptyOperand() {
        LispInterpreter interpreter = new LispInterpreter();
        ArrayList<Object> operands = new ArrayList<>();
        String result = interpreter.ATOM(operands);
        assertEquals("NIL", result);
    }
}