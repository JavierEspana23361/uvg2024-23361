import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.io.IOException;

public class Testing {
    
    @Test
    public void testInfixToPostfix() {        
        
        ListADT<String> expected = new ListADT<>();
        expected.agregar("(1+2)*9");
        
        ListADT<String> result = new ListADT<>();
        result.agregar("1 2 + 9 *");

        assertEquals(expected, result);
    }

    @Test
    public void testDoOperation() throws IOException {
    infixRead calculator = new infixRead();
    ListADT<String> expected = new ListADT<>();
    expected.agregar("1 2 + 9 *");
    
    calculator.doOperation(new ListADT<>());
    
    ListADT<String> result = calculator.stringtoListADT(calculator.infixToPostfix(calculator.ListADTtoArraylist(calculator.read("datos.txt"))));
    
    assertEquals(expected, result);
}

    @Test
    public void testAdd() {
        postfixCalc calculator = new postfixCalc();
        int result = calculator.add(2, 3);
        assertEquals(5, result);
    }

    @Test
    public void testSubstract() {
        postfixCalc calculator = new postfixCalc();
        int result = calculator.substract(5, 2);
        assertEquals(3, result);
    }

    @Test
    public void testMultiplication() {
        postfixCalc calculator = new postfixCalc();
        int result = calculator.multiplication(4, 3);
        assertEquals(12, result);
    }

    @Test
    public void testDivision() {
        postfixCalc calculator = new postfixCalc();
        int result = calculator.division(10, 2);
        assertEquals(5, result);
    }

    @Test
    public void testResidue() {
        postfixCalc calculator = new postfixCalc();
        int result = calculator.residue(10, 3);
        assertEquals(1, result);
    }

    @Test
    public void testListADTtoString() {
        postfixCalc calculator = new postfixCalc();
        ListADT<String> list = new ListADT<>();
        list.agregar("1");
        list.agregar("2");
        list.agregar("+");
        String result = calculator.ListADTtoString(list);
        assertEquals("1 2 + ", result);
    }

    @Test
    public void testPostfixreader() {
        postfixCalc calculator = new postfixCalc();
        ListADT<String> result = calculator.postfixreader("1 2 +");
        ListADT<String> expected = new ListADT<>();
        expected.agregar("1");
        expected.agregar("2");
        expected.agregar("+");
        assertEquals(expected, result);
    }

    @Test
    public void testIsOperand() {
        postfixCalc calculator = new postfixCalc();
        boolean result = calculator.isOperand("5");
        assertEquals(true, result);
    }

    @Test
    public void testIsOperator() {
        postfixCalc calculator = new postfixCalc();
        boolean result = calculator.isOperator("+");
        assertEquals(true, result);
    }

    @Test
    public void testSolvePostfix() {
        postfixCalc calculator = new postfixCalc();
        ListADT<String> elements = new ListADT<>();
        elements.agregar("1");
        elements.agregar("2");
        elements.agregar("+");
        int result = calculator.solvePostfix(elements);
        assertEquals(3, result);
    }
}