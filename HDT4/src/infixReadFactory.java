/**
 * A factory class that creates an instance of the infixRead calculator.
 */
public class infixReadFactory implements CalculatorFactory {
    /**
     * Creates and returns an instance of the infixRead calculator.
     *
     * @return An instance of the infixRead calculator.
     */
    public ICalculator getCalculator() {
        return new infixRead();
    }
}

