/**
 * The Main class is the entry point of the program.
 * It creates an instance of ListADT, infixReadFactory, and performs an operation on the list.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ListADT<String> list = new ListADT<>();
        infixReadFactory factory = new infixReadFactory();
        factory.getCalculator().doOperation(list);

        
    }
}
