import java.io.IOException;

/**
 * The ICalculator interface represents a calculator that can perform operations on a list of elements.
 */
public interface ICalculator {
    /**
     * Checks if the given element is an operand.
     *
     * @param element the element to check
     * @return true if the element is an operand, false otherwise
     */
    public boolean isOperand(String element);

    /**
     * Checks if the given element is an operator.
     *
     * @param element the element to check
     * @return true if the element is an operator, false otherwise
     */
    public boolean isOperator(String element);

    /**
     * Performs an operation on the given list of elements.
     *
     * @param list the list of elements
     * @throws IOException if an I/O error occurs while performing the operation
     */
    public void doOperation(ListADT<String> list) throws IOException;
}
