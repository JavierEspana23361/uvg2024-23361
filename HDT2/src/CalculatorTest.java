import java.util.ArrayList;

public class CalculatorTests {

    public static void main(String[] args) {
        CalculatorTests calculatorTests = new CalculatorTests();
        calculatorTests.testAdd();
        calculatorTests.testSubtract();
        calculatorTests.testMultiplication();
        calculatorTests.testDivision();
        calculatorTests.testResidue();
        calculatorTests.testSolve();
    }

    public void testAdd() {
        Calculator calculator = new Calculator();
        int result = calculator.add(5, 3);
        assert result == 8 : "Addition test failed";
    }

    public void testSubtract() {
        Calculator calculator = new Calculator();
        int result = calculator.subtract(10, 4);
        assert result == 6 : "Subtraction test failed";
    }

    public void testMultiplication() {
        Calculator calculator = new Calculator();
        int result = calculator.multiplication(7, 2);
        assert result == 14 : "Multiplication test failed";
    }

    public void testDivision() {
        Calculator calculator = new Calculator();
        int result = calculator.division(15, 3);
        assert result == 5 : "Division test failed";
    }

    public void testResidue() {
        Calculator calculator = new Calculator();
        int result = calculator.residue(17, 4);
        assert result == 1 : "Residue test failed";
    }

    public void testSolve() throws Exception {
        Calculator calculator = new Calculator();
        ArrayList<String> elements = new ArrayList<>();
        elements.add("5");
        elements.add("3");
        elements.add("+");
        int result = calculator.solve(elements);
        assert result == 8 : "Solve test failed";
    }
}