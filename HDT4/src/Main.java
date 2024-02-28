public class Main {
    public static void main(String[] args) throws Exception {
        ListADT<String> list = new ListADT<>();
        infixReadFactory factory = new infixReadFactory();
        factory.getCalculator().doOperation(list);

        
    }
}
