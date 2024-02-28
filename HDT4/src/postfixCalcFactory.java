/**
 * A factory class that creates instances of the postfixCalc class.
 * Implements the CalculatorFactory interface.
 */
public class postfixCalcFactory implements CalculatorFactory {
    /**
     * Creates and returns a new instance of the postfixCalc class.
     *
     * @return A new instance of the postfixCalc class.
     */
    public ICalculator getCalculator(){
        return new postfixCalc();
    }
}
