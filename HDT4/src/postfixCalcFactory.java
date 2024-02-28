public class postfixCalcFactory implements CalculatorFactory {
    public ICalculator getCalculator(){
        return new postfixCalc();
    }
} 