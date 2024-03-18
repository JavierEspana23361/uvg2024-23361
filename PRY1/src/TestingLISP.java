import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestingLISP {

    @Test
    public void testArithmeticOperations() throws Exception {
        LispInterpreter interpreter = new LispInterpreter();
        
        // Test addition
        ArrayList<String> additionExpr = interpreter.tokenize("(+ 2 3)");
        assertEquals(5.0, interpreter.eval(additionExpr));

        // Test subtraction
        ArrayList<String> subtractionExpr = interpreter.tokenize("(- 5 3)");
        assertEquals(2.0, interpreter.eval(subtractionExpr));

        // Test multiplication
        ArrayList<String> multiplicationExpr = interpreter.tokenize("(* 2 3)");
        assertEquals(6.0, interpreter.eval(multiplicationExpr));

        // Test division
        ArrayList<String> divisionExpr = interpreter.tokenize("(/ 6 2)");
        assertEquals(3.0, interpreter.eval(divisionExpr));
    }

    @Test
    public void testVariableAssignmentAndEvaluation() throws Exception {
        LispInterpreter interpreter = new LispInterpreter();

        // Assign variable x
        ArrayList<String> setqExpr = interpreter.tokenize("(SETQ x 5)");
        assertEquals("Variable set: x = 5.0", interpreter.eval(setqExpr));

        // Evaluate expression using variable x
        ArrayList<String> evalExpr = interpreter.tokenize("(* x 2)");
        assertEquals(10.0, interpreter.eval(evalExpr));
    }

    @Test
    public void testConditionalExpression() throws Exception {
        LispInterpreter interpreter = new LispInterpreter();

        // Test true condition
        ArrayList<String> trueCondExpr = interpreter.tokenize("(COND (T 1) (NIL 2))");
        assertEquals(1.0, interpreter.eval(trueCondExpr));

        // Test false condition
        ArrayList<String> falseCondExpr = interpreter.tokenize("(COND (NIL 1) (T 2))");
        assertEquals(2.0, interpreter.eval(falseCondExpr));
    }

    @Test
    public void testFunctionDefinitionAndEvaluation() throws Exception {
        LispInterpreter interpreter = new LispInterpreter();

        // Define a function
        ArrayList<String> defunExpr = interpreter.tokenize("(DEFUN double (x) (* x 2))");
        interpreter.eval(defunExpr);

        // Test function call
        ArrayList<String> functionCallExpr = interpreter.tokenize("(double 3)");
        assertEquals(6.0, interpreter.eval(functionCallExpr));
    }
}
