public class infixReadFactory implements CalculatorFactory{
    public ICalculator getCalculator(){
        return new infixRead();
    }
}
