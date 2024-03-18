import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestingLISP {
    @Test
    public void testTokenize() {
        LispInterpreter interpreter = new LispInterpreter();

        String expression1 = "(+ 2 3)";
        ArrayList<Object> expression = interpreter.tokenize(expression1);
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
        ArrayList<Object> expression = interpreter.tokenize(expression1);
        Double expected1 = 5.0;
        
        // Crear un mapa de variables locales vacío
        Map<String, Object> localVariables = new HashMap<>();
        
        // Llamar al método eval con la lista de elementos y el mapa de variables locales
        Object result1 = interpreter.eval(expression, localVariables);
        assertEquals(expected1, result1);
    }

}
