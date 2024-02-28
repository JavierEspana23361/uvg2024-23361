import java.io.IOException;

public interface ICalculator {
    public boolean isOperand(String element);
    public boolean isOperator(String element);
    public void doOperation(ListADT<String> list) throws IOException;
} 