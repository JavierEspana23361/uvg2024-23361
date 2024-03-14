import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class LispInterpreterTest {
    @Test
    public void testTokenize() {
        LispInterpreter interpreter = new LispInterpreter();

        String expression1 = "(+ 2 3)";
        ArrayList<String> expression = interpreter.tokenize(expression1);
        ArrayList<String> expected1 = new ArrayList<String>();
        expected1.add("(");
        expected1.add("+");
        expected1.add("2");
        expected1.add("3");
        expected1.add(")");
        assertEquals(expected1, expression);
    }

    @Test
    public void testEval() throws Exception {
        LispInterpreter interpreter = new LispInterpreter();

        String expression1 = "(+ 2 3)";
        ArrayList<String> expression = interpreter.tokenize(expression1);
        Double expected1 = 5.0;
        Object result1 = interpreter.eval(expression);
        assertEquals(expected1, result1);
    }
}
