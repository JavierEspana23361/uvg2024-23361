/**
 * The CalculatorFactory interface represents a factory for creating instances of ICalculator.
 */
public interface CalculatorFactory {
    /**
     * Returns an instance of ICalculator.
     *
     * @return an instance of ICalculator
     */
    public ICalculator getCalculator();
}
